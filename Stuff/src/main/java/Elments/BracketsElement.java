package Elments;

import Enums.Mark;

import java.util.ArrayList;

/**
 * Created by Yuri on 06.12.16.
 */
public class BracketsElement implements IElement {
    private ArrayList<IElement> elements;
    private Mark mark;

    public BracketsElement(ArrayList<IElement> elements, Mark mark) {

        this.elements = elements;
        this.mark = mark;
    }
}
