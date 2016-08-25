package ai.fw.game.player;

import ai.fw.game.def.GameState;
import ai.fw.game.def.GameStateSerializer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by KateCarey on 8/25/16.
 */
public class HumanPlayerStrategy<T extends GameState> implements GamePlayerStrategy<T> {

    private final char playerId;
    private final GameStateSerializer<T> stateSerializer;
    private final InputStream in;
    private final PrintStream out;

    public HumanPlayerStrategy(char playerId, GameStateSerializer<T> stateSerializer, InputStream in, PrintStream out) {
        this.playerId = playerId;
        this.stateSerializer = stateSerializer;
        this.in = in;
        this.out = out;
    }

    @Override
    public T makeMove(long timeToChooseMillis, List<T> possibleMoves) {
        out.println("\n\n\n--------------------------");
        out.println("It is your turn, " + playerId);
        out.println("You have " + (timeToChooseMillis / 1000.0) + " seconds to choose");
        out.println("Here are your choices: ");

        int i = 0;
        for (T state : possibleMoves) {
            out.println("\nCHOICE " + i);
            out.println(stateSerializer.serialize(state));

            i++;
        }

        Scanner inScanner = new Scanner(in);
        int choice = inScanner.nextInt();
        return possibleMoves.get(choice);
    }

}
