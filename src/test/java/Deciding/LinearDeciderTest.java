package Deciding;

import Stuff.Exceptions.ParsingException;
import Stuff.Utilities.Stepper;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Yuri on 10.02.17.
 */
public class LinearDeciderTest {

    @Test
    public void decideHardEquation1() throws ParsingException {
        decideEquation("54 * x + (9 + 9) : (5 + 8 / 3) = 90");
    }

    @Test
    public void decideHardEquation2() throws ParsingException {
        decideEquation("(x + 3) : 6 = (3x - 2) : 5");
    }

    private void decideEquation(String equation) throws ParsingException {
        Thread exitThread = new Thread(() -> {
            Stepper.saveAll();
            Thread.currentThread().interrupt();
        });
        exitThread.setDaemon(true);
        exitThread.setName("Exit thread");
        Runtime.getRuntime().addShutdownHook(exitThread);

        Stepper steps = LinearDecider.decide(equation);
        steps.writeHTMLToFile();
        assertNotNull(steps);
    }

}