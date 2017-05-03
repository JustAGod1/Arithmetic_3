package Parsing.Containers;


import Stuff.Elments.BracketsElement;
import Stuff.Elments.Element;
import Stuff.Enums.Mark;
import Stuff.Enums.MultMark;
import Stuff.Enums.RootIndex;
import Stuff.Readers.LinearReader;

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
    public Element toElement() {
        return new BracketsElement(new LinearReader(containers.toArray()), mark);
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
