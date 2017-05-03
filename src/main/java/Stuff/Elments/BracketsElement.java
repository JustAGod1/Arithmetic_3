package Stuff.Elments;


import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.DecideUtil;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 06.12.16.
 */
public class BracketsElement extends Element {
    private LinearReader elements;
    private Mark mark;
    private static final int PRIORITY = 1;
    public static final EquationSide PREFERED_SIDE = EquationSide.NEVER_MIND;

    public BracketsElement(LinearReader elements, Mark mark) {

        this.elements = elements.clone();
        this.mark = mark;
    }

    public void setExponent(Element exponent) {

    }

    @Override
    public String rendToHTML(boolean needBrackets, boolean needMark, boolean needTextTag) {
        return String.format("%s(%s)", needMark ? mark.toString() : "", elements.toHTML(needTextTag));
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isDecided() {
        return false;
    }

    @Override
    public Mark getMark() {
        return mark;
    }

    @Override
    public void setMark(Mark mark) {
        this.mark = mark;
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
        DecideUtil.decideLinearReader(elements, stepper);
        if (mark == Mark.Minus)
            elements.executeMultiplying(stepper, new FloatElement(-1));
        return elements.toArray();
    }

    @Override
    public EquationSide getPreferredSide() {
        return PREFERED_SIDE;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        StringBuilder builder = new StringBuilder();

        builder.append(getMark() == Mark.Minus || needMark ? getMark() : "").append(needMark ? " " : "").append('(').append(elements.rendToString(false)).append(')');

        return builder.toString();
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        DecideUtil.decideLinearReader(elements, stepper);
        elements.executeMultiplying(stepper, multiplier);
        stepper.step();
        return this;
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        DecideUtil.decideLinearReader(elements, stepper);
        elements.executeDividing(stepper, divider);
        return this;
    }

    @Override
    public BracketsElement clone() {

        return new BracketsElement(elements.clone(), mark);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof BracketsElement) {
            BracketsElement element = (BracketsElement) obj;

            return element.elements.equals(elements) && element.mark.equals(mark);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return rendToString(false, true);
    }
}
