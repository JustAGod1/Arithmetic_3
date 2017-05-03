package Stuff.Elments;

import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 01.03.17.
 */
public abstract class SimpleElement extends Element {

    public abstract Index getIndex();

    public abstract void setIndex(Index index, Stepper stepper);
}
