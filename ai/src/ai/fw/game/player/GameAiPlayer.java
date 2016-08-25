package ai.fw.game.player;

import ai.fw.game.def.GameState;

/**
 * Created by KateCarey on 8/25/16.
 */
public class GameAiPlayer<T extends GameState<T>> extends BaseGamePlayer {

    private final char id;
    private final GamePlayerStrategy strategy;

    public GameAiPlayer(char id, GamePlayerStrategy strategy) {
        this.id = id;
        this.strategy = strategy;
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
