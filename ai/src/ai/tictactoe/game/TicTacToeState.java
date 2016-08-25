package ai.tictactoe.game;

import ai.fw.game.def.GameConfig;
import ai.fw.game.def.GameState;
import ai.fw.game.player.GamePlayer;
import ai.util.Pair;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Created by KateCarey on 8/25/16.
 */
public class TicTacToeState implements GameState<TicTacToeState> {

    private static final int DIM = 3; // TODO make this a variable!

    public static TicTacToeState initialState(GameConfig.GamePlayers players) {
        GamePlayer firstPlayer = players.getPlayer(0); // TODO alternate who starts
        return new TicTacToeState(players, firstPlayer, new GamePlayer[DIM][DIM]);
    }

    private final GameConfig.GamePlayers players;
    private final GamePlayer currentPlayer;
    private final GamePlayer[][] board;

    public TicTacToeState(GameConfig.GamePlayers players, GamePlayer currentPlayer, GamePlayer[][] board) {
        this.players = players;
        this.currentPlayer = currentPlayer;
        this.board = board;
    }

    @Override
    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public int getDim() {
        return DIM;
    }

    public GamePlayer getPlayerAt(int i, int j) {
        return board[i][j];
    }

    @Override
    public List<TicTacToeState> getPossibleMoves() {
        List<TicTacToeState> moves = new ArrayList<>();

        for (int i = 0; i < getDim(); i++) {
            for (int j = 0; j < getDim(); j++) {
                if (getPlayerAt(i, j) == null) {
                    GamePlayer[][] newBoard = copyBoard(board);
                    newBoard[i][j] = currentPlayer;
                    moves.add(new TicTacToeState(players, nextPlayer(), newBoard));
                }
            }
        }

        return moves;
    }

    @Override
    public boolean isTerminal() {
        return getPossibleMoves().isEmpty() ||
                getResults().entrySet().stream()
                    .anyMatch(gamePlayerLongEntry -> gamePlayerLongEntry.getValue() != 0);
    }

    @Override
    public Map<GamePlayer, Long> getResults() {
        Map<GamePlayer, Long> results = new HashMap<>();

        // check the rows, and then the cols
        addMapValues(results, resultsForRowOrCol(false, (i, j) -> getPlayerAt(i, j)));
        addMapValues(results, resultsForRowOrCol(false, (i, j) -> getPlayerAt(j, i)));

        // and then the diagonals
        addMapValues(results, resultsForRowOrCol(true, (i, j) -> getPlayerAt(j, j)));
        addMapValues(results, resultsForRowOrCol(true, (i, j) -> getPlayerAt(getDim() - j - 1, j)));

        return results;
    }

    private Map<GamePlayer, Long> resultsForRowOrCol(boolean iLoopOnlyOnce, BiFunction<Integer, Integer, GamePlayer> lookupFn) {
        Map<GamePlayer, Long> results = new HashMap<>();

        int iLoopBounds = iLoopOnlyOnce ? 1 : getDim();

        for (int i = 0; i < iLoopBounds; i++) {
            Map<GamePlayer, Integer> countByPlayer = new HashMap<>();

            for (int j = 0; j < getDim(); j++) {
                GamePlayer playerAtLocation = lookupFn.apply(i, j);
                if (playerAtLocation != null) {
                    if (!countByPlayer.containsKey(playerAtLocation)) {
                        countByPlayer.put(playerAtLocation, 0);
                    }

                    countByPlayer.put(playerAtLocation, countByPlayer.get(playerAtLocation) + 1);
                }
            }

            //if anyone had a full count on this row or col, add that to the results
            Map<GamePlayer, Long> pointsByPlayer = new HashMap<>();
            countByPlayer.entrySet().stream().forEach(entry -> {
                if (entry.getValue() >= getDim()) {
                    if (!pointsByPlayer.containsKey(entry.getKey())) {
                        pointsByPlayer.put(entry.getKey(), 0L);
                    }

                    pointsByPlayer.put(entry.getKey(), pointsByPlayer.get(entry.getKey()) + 1);
                }
            });
            addMapValues(results, pointsByPlayer);
        }

        return results;
    }

    private void addMapValues(Map<GamePlayer, Long> base, Map<GamePlayer, Long> extra) {
        extra.forEach((gamePlayer, count) -> {
            if (!base.containsKey(gamePlayer)) {
                base.put(gamePlayer, 0L);
            }

            base.put(gamePlayer, base.get(gamePlayer) + count);
        });
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TicTacToeState)) {
            return false;
        }

        TicTacToeState otherState = (TicTacToeState) obj;
        return Objects.equals(currentPlayer, otherState.currentPlayer) &&
                Objects.equals(board, otherState.board);

    }

    @Override
    public int hashCode() {
        return new TicTacToeStateSerializer().serialize(this).hashCode();
    }

    private GamePlayer[][] copyBoard(GamePlayer[][] board) {
        // TODO do this more efficiently?
        GamePlayer[][] newBoard = new GamePlayer[getDim()][getDim()];

        for (int i = 0; i < getDim(); i++) {
            for (int j = 0; j < getDim(); j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        return newBoard;
    }

    private GamePlayer nextPlayer() {
        return players.getPlayerAfter(getCurrentPlayer());
    }

}
