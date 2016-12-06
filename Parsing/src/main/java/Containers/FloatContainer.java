package Containers;


import Elments.FloatElement;
import Elments.IElement;
import Enums.Mark;

/**
 * Created by Yuri on 21.10.16.
 */
public class FloatContainer extends Exponentable implements Container  {

    float value;

    Mark mark;

    public FloatContainer(float value, Mark mark) {

        this.value = value;
        this.mark = mark;
    }

    @Override
    public IElement toElement() {
        FloatElement fe = new FloatElement(value, mark);
        if (!(getExponent().equals(new FloatContainer(1, Mark.Plus)))) {
            fe.setExponent(getExponent().toElement());
        }
        return (fe);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }
}
