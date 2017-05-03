package Stuff.Elments;


import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 06.12.16.
 */
public class UnknownElement extends Element {
    private final Mark mark;
    private final char letter;
    private final int PRIORITY = -1;

    public UnknownElement(Mark mark, char letter) {
        this.mark = mark;
        this.letter = letter;
    }

    public void setExponent(Element exponent) {

    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isDecided() {
        return true;
    }

    @Override
    public Mark getMark() {
        return mark;
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
        return null;
    }

    @Override
    public Element[] update(Stepper stepper) {
        return null;
    }

    @Override
    public EquationSide getPreferredSide() {
        return null;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        return "" + letter;
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        return null;
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        return null;
    }

    @Override
    public Element clone() {
        return new UnknownElement(mark, letter);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnknownElement) {
            UnknownElement element = (UnknownElement) obj;

            return element.letter == letter && element.mark == mark;
        } else {
            return false;
        }
    }

}
