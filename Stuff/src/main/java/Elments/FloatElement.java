package Elments;

import Enums.Mark;

/**
 * Created by Yuri on 06.12.16.
 */
public class FloatElement implements IElement {
    private float value;
    private Mark mark = Mark.Plus;

    public FloatElement(float value) {
        if (value < 0) mark = Mark.Minus;
        value = Math.abs(value);
    }

    public FloatElement(float value, Mark mark) {

        this.value = value;
        this.mark = mark;
    }
}
