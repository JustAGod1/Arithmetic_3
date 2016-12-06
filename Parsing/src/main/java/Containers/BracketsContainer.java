package Containers;


import Elments.BracketsElement;
import Elments.IElement;
import Enums.Mark;
import Enums.MultMark;
import Enums.RootIndex;

/**
 * Created by Yuri on 22.10.16.
 */
public class BracketsContainer extends Exponentable implements ComplicatedContainer {
    ArrayContainer containers = new ArrayContainer();
    ComplicatedContainer previousContainer;
    Mark mark;

    public BracketsContainer(ComplicatedContainer previousContainer, Mark mark) {
        this.previousContainer = previousContainer;

        this.mark = mark;
    }

    @Override
    public IElement toElement() {
        return new BracketsElement(containers.toArray(), mark);
    }

    @Override
    public void addContainer(Container element) {
        containers.addContainer(element);
    }

    @Override
    public ComplicatedContainer getPreviousContainer() {
        return previousContainer;
    }

    @Override
    public void addMult(MultMark mark) {
        containers.addMult(mark);
    }

    @Override
    public void addExponent() {
        containers.addExponent();
    }

    @Override
    public void addRoot(RootIndex index) {
        containers.addRoot(index);
    }


}
