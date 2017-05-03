package Stuff.Elments;


import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Enums.MultMark;
import Stuff.Utilities.Stepper;

import java.util.ArrayList;

/**
 * Created by Yuri on 06.12.16.
 */
public class MultElement extends Element {
    private final ArrayList<Element> elements;
    private final ArrayList<MultMark> marks;
    private final int PRIORITY = 1;
    public static final EquationSide PREFERED_SIDE = EquationSide.NEVER_MIND;

    public MultElement(ArrayList<Element> elements, ArrayList<MultMark> marks) {

        this.elements = elements;
        this.marks = marks;
    }

    public void setExponent(Element exponent) {

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
        return new ErrorElement("Элемент не слагаем со мной - Mult Element");
    }

    @Override
    public Element[] update(Stepper stepper) {
        Element element = elements.get(0);

        for (int i = 1; i < elements.size(); i++) {
            element = marks.get(i - 1) == MultMark.Mult ? element.multBy(elements.get(i), stepper) : element.divideBy(elements.get(i), stepper);
        }

        return toArray(element);
    }

    @Override
    public EquationSide getPreferredSide() {
        return PREFERED_SIDE;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        StringBuilder result = new StringBuilder();

        result.append(elements.get(0).rendToString(false, needMark));

        for (int i = 1; i < elements.size(); i++) {
            result.append(' ').append(marks.get(i - 1).toString()).append(' ');
            result.append(elements.get(i).rendToString(true, false));
        }

        return result.toString().trim();
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        addMultiplier(multiplier, MultMark.Mult);
        return this.clone();
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        if (divider.equals(new FloatElement(1))) return this;
        marks.add(MultMark.Div);
        elements.add(divider);
        return this;
    }

    @Override
    public Element clone() {
        return new MultElement((ArrayList<Element>) elements.clone(), (ArrayList<MultMark>) marks.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MultElement) {
            MultElement element = (MultElement) obj;

            return element.elements.equals(elements) && element.marks.equals(marks);
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
        return String.format("%s", rendToString(needBrackets, needMark));
    }

    private void addMultiplier(Element element, MultMark multMark) {
        marks.add(multMark);
        elements.add(element);
    }
}
