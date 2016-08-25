package ai.fw.game.runner;

import ai.fw.game.def.GameConfig;
import ai.fw.game.def.GameState;
import ai.fw.game.def.GameStateSerializer;
import ai.fw.game.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by KateCarey on 8/25/16.
 */
public class GameRunner implements Runnable {

    private GameState currentState;
    private final List<GameState> gameStateHistory;
    private final TurnTimeTracker timeTracker;

    // TODO get rid of this
    private final GameStateSerializer stateSerializer;

    public GameRunner(GameConfig gameConfig) {
        currentState = gameConfig.getInitialGameState();
        gameStateHistory = new ArrayList<>();
        timeTracker = new TurnTimeTracker(gameConfig);
        stateSerializer = gameConfig.getGameStateSerializer();
    }

    @Override
    public void run() {
        while (true) {
            gameStateHistory.add(currentState);

            if (currentState.isTerminal()) {
                break;
            }

            GamePlayer currentPlayer = currentState.getCurrentPlayer();
            List<GameState> possibleMoves = currentState.getPossibleMoves();

            // add turn time, get their move, and subtract the time taken
            timeTracker.addTurnTime(currentPlayer);
            long turnStartTime = System.currentTimeMillis();
            GameState newState = currentPlayer.getStrategy()
                                    .makeMove(timeTracker.getTimeLeft(currentPlayer), possibleMoves);
            long turnDuration = System.currentTimeMillis() - turnStartTime;
            timeTracker.subtractTime(currentPlayer, turnDuration);

            //validate the time they have left
            if (timeTracker.getTimeLeft(currentPlayer) < 0) {
                //TODO how to handle this? grace period? they automatically lose? are docked points?
                throw new OutOfTurnTimeException();
            }

            //validate that the move we got back is actually valid
            if (!possibleMoves.contains(newState)) {
                // TODO give them a chance to pick again until time runs out?
                // TODO they should automatically lose
                throw new InvalidGameMoveException();
            }

            currentState = newState;
        }

        // TODO print something at the end of the game
        System.out.println("\n\n\n------------------------");
        System.out.println("GAME OVER");
        System.out.println(stateSerializer.serialize(currentState));
        System.out.println();
        currentState.getResults().entrySet().stream()
                .sorted((o1, o2) -> {
                    Map.Entry<GamePlayer, Long> e1 = (Map.Entry<GamePlayer, Long>) o1;
                    Map.Entry<GamePlayer, Long> e2 = (Map.Entry<GamePlayer, Long>) o2;
                    return (int) (e1.getValue() - e2.getValue());
                })
                .forEach(obj -> {
                    Map.Entry<GamePlayer, Long> entry = (Map.Entry<GamePlayer, Long>) obj;
                    System.out.println(String.format("Player %s gets %d points", entry.getKey(), entry.getValue()));
                });
    }
}
