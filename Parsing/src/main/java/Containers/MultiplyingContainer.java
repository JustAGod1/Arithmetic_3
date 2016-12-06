package Containers;


import Elments.IElement;
import Enums.MultMark;

import java.util.ArrayList;

/**
 * Created by Yuri on 23.10.16.
 */
public class MultiplyingContainer extends Exponentable implements Container {

    ArrayList<Container> mults = new ArrayList<>();

    ArrayList<MultMark> marks = new ArrayList<>();

    public MultiplyingContainer (Container first, MultMark mark, Container second) {
        mults.add(first);
        marks.add(mark);
        mults.add(second);
    }

    @Override
    public IElement toElement() {
        ArrayList<IElement> elements = new ArrayList<>();

        for (Container mult : mults) {
            elements.add(mult.toElement());
        }

        return new MultElement(elements, marks);
    }

    public void addContainer(MultMark mark, Container container) {
        marks.add(mark);
        mults.add(container);
    }

    @Override
    public void setExponent(Container exponent) {
        ((Exponentable) mults.get(mults.size() - 1)).setExponent(exponent);
    }

    public void setRootToLastPosition(Container index) {
        Container lastC = mults.get(mults.size() - 1);
        mults.set(mults.size() - 1, new RootContainer(index, lastC));
    }
}
