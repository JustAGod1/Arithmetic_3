package Containers;


import Elments.IElement;
import Enums.Mark;
import Enums.MultMark;
import Enums.RootIndex;

import java.util.ArrayList;

/**
 * Created by Yuri on 21.10.16.
 */
public class ArrayContainer implements ComplicatedContainer {

    ArrayList<Container> containers = new ArrayList<>();
    MultMark mark = null;
    RootIndex rootIndex = null;
    boolean needMult = false;
    boolean needExponent = false;
    boolean needRoot = false;


    @Override
    public IElement toElement() {
        return null;
    }


    @Override
    public void addContainer(Container element) {
        if (needMult) {
            if (containers.get(containers.size() - 1) instanceof MultiplyingContainer) {
                ((MultiplyingContainer) containers.get(containers.size() - 1)).addContainer(mark, element);
            } else {
                MultiplyingContainer mc = new MultiplyingContainer(containers.get(containers.size() - 1), mark, element);
                containers.set(containers.size() - 1, mc);
            }
            mark = null;
            needMult = false;
        } else if (needExponent) {
            ((Exponentable) containers.get(containers.size() - 1)).setExponent(element);
            needExponent = false;
        } else if (needRoot) {
            addRoot(element);
        } else {
            containers.add(element);
        }
    }

    @Override
    public ComplicatedContainer getPreviousContainer() {
        return null;
    }

    @Override
    public void addMult(MultMark mark) {
        System.out.println(String.format("We're trying to add multiplying with mark \"%s\"", mark));
        if (containers.size() <= 0) {
            addContainer(new FloatContainer(1, Mark.Plus));
        }
        if (!needMult) {
            needMult = true;
            this.mark = mark;
        }


    }

    @Override
    public void addExponent() {
        needExponent = true;
    }

    @Override
    public void addRoot(RootIndex index) {
        needRoot = true;
        rootIndex = index;
    }

    private void addRoot(Container base) {
        Container lastC = containers.get(containers.size() - 1);
        needRoot = false;
        switch (rootIndex) {
            case Previous: {
                if (lastC instanceof MultiplyingContainer) {
                    ((MultiplyingContainer) lastC).setRootToLastPosition(base);
                }
                else {
                    containers.set(containers.lastIndexOf(lastC), new RootContainer(lastC, base));
                }
            }

            case Two: {
                addContainer(new RootContainer(new FloatContainer(2, Mark.Plus), base));
            }
        }
    }

    public ArrayList<IElement> toArray() {
        ArrayList<IElement> res = new ArrayList<>();

        for (Container container : containers) {
            res.add(container.toElement());
        }

        return res;
    }


}
