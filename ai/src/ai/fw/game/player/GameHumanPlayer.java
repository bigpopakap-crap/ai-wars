package ai.fw.game.player;

import ai.fw.game.def.GameState;
import ai.fw.game.def.GameStateSerializer;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Created by KateCarey on 8/25/16.
 */
public class GameHumanPlayer<T extends GameState<T>> extends BaseGamePlayer {

    private final char id;
    private final GamePlayerStrategy<T> strategy;

    public GameHumanPlayer(char id, GameStateSerializer<T> stateSerializer, InputStream in, PrintStream out) {
        this.id = id;
        this.strategy = new HumanPlayerStrategy(id, stateSerializer, in, out);
    }

    public GameHumanPlayer(char id, GameStateSerializer<T> stateSerializer) {
        this(id, stateSerializer, System.in, System.out);
    }

    @Override
    public char getId() {
        return id;
    }

    @Override
    public GamePlayerStrategy getStrategy() {
        return strategy;
    }

}
