package Stuff.Utilities;


import Stuff.Readers.Equation;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 21.12.16.
 */
public class DebugStepper extends Stepper {

    public DebugStepper(Equation equation) {
        super(equation);
    }

    @Override
    public boolean step() {
        if (super.step()) {
            System.out.printf(
                    "Right side size = %d\nLeft side size = %d\nEquation hash code = %d\n[%s]%s\n",
                    equation.getRight().size(),
                    equation.getLeft().size(),
                    equation.hashCode(),
                    getClass().getSimpleName(),
                    equation.toString());
            return true;
        }
        return false;
    }
}
