package Deciding;

import Parsing.Parser;
import Stuff.Exceptions.ParsingException;
import Stuff.Readers.Equation;
import Stuff.Utilities.DebugStepper;
import Stuff.Utilities.DecideUtil;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 10.02.17.
 */
public class LinearDecider {

    public static Stepper decide(Equation equation) {
        //Thread.setDefaultUncaughtExceptionHandler((t, e) -> Stepper.saveAll());
        Stepper stepper = new DebugStepper(equation);
        DecideUtil.decideEquation(equation, stepper);
        return stepper;
    }

    public static Stepper decide(String equation) throws ParsingException {
        Equation equation1 = (new Parser(equation)).parse();
        return decide(equation1);
    }
}
