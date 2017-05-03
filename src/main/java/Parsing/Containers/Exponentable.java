package Parsing.Containers;


import Stuff.Enums.Mark;

/**
 * Created by Yuri on 21.10.16.
 */
public abstract class Exponentable {
    private Container exponent;

    public Container getExponent() {

        if (exponent != null) {
            return exponent;
        }
        else {
            exponent = new FloatContainer(1, Mark.Plus);
            return exponent;
        }
    }

    public void setExponent(Container exponent) {
        this.exponent = exponent;
    }

}
