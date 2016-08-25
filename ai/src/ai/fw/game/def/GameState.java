package ai.fw.game.def;

import ai.fw.game.player.GamePlayer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by KateCarey on 8/25/16.
 */
public interface GameState<T extends GameState> {

    GamePlayer getCurrentPlayer(); // TODO should the game state know about the current player?
    List<T> getPossibleMoves();

    boolean isTerminal();
    Map<GamePlayer, Long> getResults();

    boolean equals(Object obj);
    int hashCode();

}
