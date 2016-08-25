package ai.tictactoe.game;

import ai.fw.game.def.GameConfig;
import ai.fw.game.def.GameStateSerializer;
import ai.fw.game.player.GameHumanPlayer;
import ai.fw.game.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KateCarey on 8/25/16.
 */
public class TicTacToeConfig implements GameConfig<TicTacToeState> {

    private final GamePlayers players;
    private final TicTacToeStateSerializer stateSerializer;

    public TicTacToeConfig() {
        stateSerializer = new TicTacToeStateSerializer();

        List<GamePlayer> players = new ArrayList<>();
        players.add(new GameHumanPlayer('X', stateSerializer));
        players.add(new GameHumanPlayer('O', stateSerializer));
        this.players = new GamePlayers(players);
    }

    @Override
    public GamePlayers getPlayers() {
        return players;
    }

    @Override
    public TicTacToeState getInitialGameState() {
        return TicTacToeState.initialState(players);
    }

    @Override
    public GameStateSerializer<TicTacToeState> getGameStateSerializer() {
        return stateSerializer;
    }

}
