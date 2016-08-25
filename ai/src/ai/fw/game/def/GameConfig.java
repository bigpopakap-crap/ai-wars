package ai.fw.game.def;

import ai.fw.game.player.GamePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by bigpopakap on 8/25/16.
 */
public interface GameConfig<T extends GameState> {

    GamePlayers getPlayers();
    T getInitialGameState();

    default long getTotalTurnTimeMillis() {
        return 60 * 1000;
    }
    default long getAddedTurnTimeMillis() {
        return 2 * 1000;
    }

    GameStateSerializer<T> getGameStateSerializer();

    /**
     * Created by KateCarey on 8/25/16.
     */
    class GamePlayers {

        private final List<GamePlayer> players;
        private final Map<Character, GamePlayer> playersById;

        public GamePlayers(List<GamePlayer> players) {
            this.players = new ArrayList<>(players); // make sure it's an array so indexing is fast
            this.playersById = players.stream().collect(Collectors.toMap(GamePlayer::getId, Function.identity()));
        }

        public List<GamePlayer> getPlayers() {
            return players;
        }

        public int getNumPlayers() {
            return players.size();
        }

        public GamePlayer getPlayer(int idx) {
            return players.get(idx);
        }

        public GamePlayer getPlayer(char id) {
            return playersById.get(id);
        }

        public GamePlayer getPlayerAfter(GamePlayer player) {
            int idx = players.indexOf(player);
            return getPlayer((idx + 1) % getNumPlayers());
        }

    }
}
