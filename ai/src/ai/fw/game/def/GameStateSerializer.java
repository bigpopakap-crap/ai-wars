package ai.fw.game.def;

/**
 * Created by KateCarey on 8/25/16.
 */
public interface GameStateSerializer<T extends GameState> {

    String serialize(T gameState);
    T deserialize(String gameState) throws GameStateSerializationException;

}
