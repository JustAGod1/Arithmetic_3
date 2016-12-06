package Readers;

import Elments.IElement;
import Enums.EquationMark;

import java.util.ArrayList;

/**
 * Created by Yuri on 06.12.16.
 */
public class Equation {
    private ArrayList<IElement> leftSide;
    private EquationMark mark;
    private ArrayList<IElement> rightSide;

    public Equation(ArrayList<IElement> leftSide, EquationMark mark, ArrayList<IElement> rightSide) {

        this.leftSide = leftSide;
        this.mark = mark;
        this.rightSide = rightSide;
    }
}
