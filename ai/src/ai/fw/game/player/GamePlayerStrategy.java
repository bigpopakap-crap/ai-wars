package ai.fw.game.player;

import ai.fw.game.def.GameState;

import java.util.List;

/**
 * Created by KateCarey on 8/25/16.
 */
public interface GamePlayerStrategy<T extends GameState> {

    T makeMove(long timeToChooseMillis, List<T> possibleMoves);

}
