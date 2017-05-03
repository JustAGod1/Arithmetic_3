package Parsing.Containers;


import Stuff.Enums.MultMark;
import Stuff.Enums.RootIndex;

/**
 * Created by Yuri on 21.10.16.
 */
public interface ComplicatedContainer extends Container {

    void addContainer(Container element);

    ComplicatedContainer getPreviousContainer();

    void addMult(MultMark mark);

    void addExponent();

    void addRoot(RootIndex index);

}
