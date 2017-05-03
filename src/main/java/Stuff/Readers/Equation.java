package Stuff.Readers;


import Stuff.Elments.Element;
import Stuff.Elments.FloatElement;
import Stuff.Elments.Index;
import Stuff.Enums.EquationMark;
import Stuff.Enums.EquationSide;
import Stuff.Utilities.Stepper;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Yuri on 06.12.16.
 */
public class Equation implements Cloneable {
    private ArrayList<Element> leftSide;
    private EquationMark mark;
    private ArrayList<Element> rightSide;

    public Equation(ArrayList<Element> leftSide, EquationMark mark, ArrayList<Element> rightSide) {

        this.leftSide = leftSide;
        this.mark = mark;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(getLeft().toString());
        builder.append(' ').append(mark.toString()).append(' ');
        builder.append(getRight().toString());

        return builder.toString();
    }

    public boolean sortElements(Stepper stepper) {
        LinearReader right = getRight();
        LinearReader left = getLeft();
        boolean res = false;

        while (right.hasNext(EquationSide.LEFT)) {
            Element element = right.getNext(EquationSide.LEFT);

            element.swapMark();

            right.remove(element);
            left.add(element);
            stepper.step();
            res = true;
        }

        while (left.hasNext(EquationSide.RIGHT)) {
            Element element = left.getNext(EquationSide.RIGHT);

            element.swapMark();

            left.remove(element);
            right.add(element);
            stepper.step();
            res = true;
        }
        return res;
    }

    public boolean executeUpdate(Stepper stepper) {
        return getLeft().executeUpdate(stepper) | getRight().executeUpdate(stepper);
    }

    public boolean executeAddition(Stepper stepper) {
        return getRight().executeAddition(stepper) | getLeft().executeAddition(stepper);
    }
    public LinearReader getRight() {
        return new LinearReader(rightSide);
    }

    public LinearReader getLeft() {
        return new LinearReader(leftSide);
    }

    public Equation clone() {
        return new Equation((ArrayList<Element>) leftSide.clone(), mark, (ArrayList<Element>) rightSide.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Equation)) return false;
        Equation equation = (Equation) obj;
        boolean res;

        res = equation.leftSide.equals(leftSide) && equation.rightSide.equals(rightSide);
        return res;
    }

    public String toHTML() {
        StringBuilder builder = new StringBuilder();

        builder.append(getLeft().toHTML(true));
        builder.append(' ').append(mark.toString()).append(' ');
        builder.append(getRight().toHTML(true));

        return builder.toString();
    }

    public void replace(Element before, Element after) {
        if (leftSide.contains(before)) {
            int index = leftSide.indexOf(before);
            leftSide.remove(index);
            leftSide.add(index, after);
        }
        if (rightSide.contains(before)) {
            int index = rightSide.indexOf(before);
            rightSide.remove(index);
            rightSide.add(index, after);
        }
    }
}
