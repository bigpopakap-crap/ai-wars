package ai.tictactoe.game;

import ai.fw.game.runner.GameRunner;

/**
 * Created by KateCarey on 8/25/16.
 */
public class TicTacToeMain {

    public static void main(String[] args) {
        TicTacToeConfig ticTacToeConfig = new TicTacToeConfig();
        GameRunner runner = new GameRunner(ticTacToeConfig);

        runner.run();
    }

}
