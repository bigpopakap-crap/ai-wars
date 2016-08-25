package ai.fw.game.runner;

import ai.fw.game.def.GameConfig;
import ai.fw.game.player.GamePlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KateCarey on 8/25/16.
 */
class TurnTimeTracker {

    private final GameConfig gameConfig;
    private final Map<GamePlayer, Long> timeLeftByPlayer;

    public TurnTimeTracker(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        timeLeftByPlayer = new HashMap<>();
    }

    public long getTimeLeft(GamePlayer player) {
        initPlayerIfUnseen(player);
        return timeLeftByPlayer.get(player);
    }

    public void addTurnTime(GamePlayer player) {
        initPlayerIfUnseen(player);
        timeLeftByPlayer.put(player, getTimeLeft(player) + gameConfig.getAddedTurnTimeMillis());
    }

    public void subtractTime(GamePlayer player, long time) {
        initPlayerIfUnseen(player);
        timeLeftByPlayer.put(player, getTimeLeft(player) - time);
    }

    private void initPlayerIfUnseen(GamePlayer player) {
        if (!timeLeftByPlayer.containsKey(player)) {
            timeLeftByPlayer.put(player, gameConfig.getTotalTurnTimeMillis());
        }
    }

}
