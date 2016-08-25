package ai.tictactoe.game;

import ai.fw.game.def.GameStateSerializationException;
import ai.fw.game.def.GameStateSerializer;
import ai.fw.game.player.GamePlayer;

/**
 * Created by KateCarey on 8/25/16.
 */
public class TicTacToeStateSerializer implements GameStateSerializer<TicTacToeState> {

    private static final char EMPTY_SPACE_CHAR = '-';

    @Override
    public String serialize(TicTacToeState gameState) {
        StringBuilder str = new StringBuilder(40);

        for (int i = 0; i < gameState.getDim(); i++) {
            for (int j = 0; j < gameState.getDim(); j++) {
                GamePlayer player = gameState.getPlayerAt(i, j);
                str.append(player != null ? player.getId() : EMPTY_SPACE_CHAR);

                if (j < gameState.getDim() - 1) {
                    str.append(' ');
                }
            }
            str.append('\n');
        }

        str.append('\n')
            .append("It is ")
            .append(gameState.getCurrentPlayer().getId())
            .append("'s turn")
            .append('\n');

        return str.toString();
    }

    @Override
    public TicTacToeState deserialize(String gameState) throws GameStateSerializationException {
        //TODO do this later. Will need to have some way to deserialize players
        return null;
    }

}
