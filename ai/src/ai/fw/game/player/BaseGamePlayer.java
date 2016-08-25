package ai.fw.game.player;

/**
 * Created by KateCarey on 8/25/16.
 */
public abstract class BaseGamePlayer implements GamePlayer {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GamePlayer)) {
            return false;
        }

        GamePlayer otherPlayer = (GamePlayer) obj;
        return getId() == otherPlayer.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "" + getId();
    }
}
