package Stuff.Elments.GostlyElements;


import Stuff.Elments.Element;
import Stuff.Elments.FractionElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 06.12.16.
 */
public class ErrorElement extends Element {

    private String message = "ERROR";

    public ErrorElement(String message) {
        this.message = message;
    }

    @Override
    public void setExponent(LinearReader exponent) {

    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public boolean isDecided() {
        return true;
    }

    @Override
    public Mark getMark() {
        return null;
    }

    @Override
    public void setMark(Mark mark) {

    }

    @Override
    public boolean isSimilar(Element element) {
        return false;
    }

    @Override
    public Element add(Element element, Stepper stepper) {
        return this;
    }

    @Override
    public Element[] update(Stepper stepper) {
        return toArray(this);
    }

    @Override
    public EquationSide getPreferredSide() {
        return EquationSide.NEVER_MIND;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        return message;
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        return this;
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        return this;
    }

    @Override
    public Element clone() {
        return new ErrorElement(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorElement that = (ErrorElement) o;

        return message != null ? message.equals(that.message) : that.message == null;
    }

    @Override
    public int hashCode() {
        return message != null ? message.hashCode() : 0;
    }
}
