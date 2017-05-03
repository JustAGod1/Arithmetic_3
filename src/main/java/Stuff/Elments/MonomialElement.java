package Stuff.Elments;

import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Utilities.Stepper;

import java.util.ArrayList;

/**
 * Created by Yuri on 06.12.16.
 */
public class MonomialElement extends Element {
    private Element index;
    private ArrayList<UnknownElement> ues = new ArrayList<>();
    private static final int PRIORITY = 0;
    public static final EquationSide PREFERED_SIDE = EquationSide.LEFT;

    public MonomialElement(UnknownElement ue) {

        this.ues.add(ue);
        index = new FloatElement(1);
        index.setMark(ue.getMark());
    }

    public MonomialElement(Element index, ArrayList<UnknownElement> ues) {
        this.index = index;
        this.ues = ues;
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
        return index.getMark();
    }

    @Override
    public void setMark(Mark mark) {
        index.setMark(mark);
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
        stepper.getEquation().getRight().executeDividing(stepper, index);
        index = new FloatElement(1);
        return new Element[] {this};
    }

    @Override
    public EquationSide getPreferredSide() {
        return PREFERED_SIDE;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        StringBuilder result = new StringBuilder();


        result.append(needMark || getMark() == Mark.Minus ? index.getMark() : "").append(needMark ? ' ':"");

        if (!(index.equals(new FloatElement(1))))
        result.append(index.rendToString(false, false));

        for (UnknownElement ue : ues) {
            result.append(ue.rendToString(false, true));
        }

        return result.toString();
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        if (multiplier instanceof FloatElement) {
            index = index.multBy(multiplier, stepper);
            return this.clone();
        }
        if (multiplier instanceof MonomialElement) {
            multByMonomial((MonomialElement) multiplier, stepper);
            return this;
        }

        return multiplier.multBy(this, stepper);

        //return new ErrorElement(String.format("Умножение на %s не поддерживается я - %s", multiplier.getClass().getSimpleName(), getClass().getSimpleName()));
    }

    private void multByMonomial(MonomialElement monomial, Stepper stepper) {
        index = index.multBy(monomial.index, stepper);
        for (UnknownElement ue : monomial.ues) {
            addUnknown(ue);
        }
    }

    private void addUnknown(UnknownElement ue) {
        for (UnknownElement unknownElement : ues) {
            if (unknownElement.getMark().equals(ue.getMark())) {
                unknownElement.getExponent().addLinearReader(ue.getExponent());
                return;
            }
        }
        ues.add(ue);
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        return new FractionElement(Mark.Plus, this, divider);
    }

    @Override
    public Element clone() {
        return new MonomialElement(index, (ArrayList<UnknownElement>) ues.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MonomialElement) {
            MonomialElement element = (MonomialElement) obj;
            boolean res = element.index.equals(index);
            res &= element.ues.equals(ues);
            return  res;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return rendToString(false, false);
    }

    @Override
    public String rendToHTML(boolean needBrackets, boolean needMark, boolean needTextTag) {
        return rendToString(needBrackets, needMark);
    }
}
