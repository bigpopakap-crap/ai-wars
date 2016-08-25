package ai.fw.game.player;

import ai.fw.game.def.GameState;

import java.util.List;

/**
 * Created by KateCarey on 8/25/16.
 */
public interface GamePlayer {

    char getId(); // TODO should this be something else? like an int?
    GamePlayerStrategy getStrategy();

}
