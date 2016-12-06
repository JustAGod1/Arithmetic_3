package Containers;

import Enums.MultMark;
import Enums.RootIndex;

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
