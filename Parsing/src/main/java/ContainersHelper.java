import Containers.ArrayContainer;
import Containers.BracketsContainer;
import Containers.ComplicatedContainer;
import Containers.Container;
import Elments.IElement;
import Enums.Mark;
import Enums.MultMark;
import Enums.RootIndex;

import java.util.ArrayList;


/**
 * Created by Yuri on 21.10.16.
 */
public class ContainersHelper {
    ComplicatedContainer elements = new ArrayContainer();

    public void addContainer(Container container) {
        elements.addContainer(container);
    }

    public ArrayList<IElement> toArray() {
        return ((ArrayContainer)elements).toArray();
    }

    public void openBrackets(Mark mark) {
        System.out.println("We're opening brackets");
        BracketsContainer bc = new BracketsContainer(elements, mark);
        elements.addContainer(bc);
        elements = bc;
    }

    public void closeBrackets() {
        System.out.println("We're closing brackets");
        elements = elements.getPreviousContainer();
    }

    public void addMulting(MultMark mark) {

        elements.addMult(mark);
    }

    public void addExponent() {
        System.out.println("We're trying to add exponent");
        elements.addExponent();
    }

    public void addRoot(RootIndex index) {
        elements.addRoot(index);
    }


}
