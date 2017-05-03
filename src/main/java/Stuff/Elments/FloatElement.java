package Stuff.Elments;


import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.DecideUtil;
import Stuff.Utilities.Stepper;

import static Stuff.Enums.Mark.Plus;

/**
 * Created by Yuri on 06.12.16.
 */
public class FloatElement extends Element {
    private float value;
    private Mark mark = Plus;
    private static final int PRIORITY = -1;
    public static final EquationSide PREFERED_SIDE = EquationSide.RIGHT;

    public FloatElement(float value) {
        if (value < 0) mark = Mark.Minus;
        this.value = Math.abs(value);
    }

    public FloatElement(float value, Mark mark) {

        this.value = Math.abs(value);
        this.mark = mark;
    }



    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isDecided() {
        return true;
    }

    @Override
    public Mark getMark() {
        return mark;
    }

    @Override
    public void setMark(Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean isSimilar(Element element) {
        return element instanceof Index;
    }

    @Override
    public Element add(Element element, Stepper stepper) {
        if (element instanceof FloatElement) {
            return new FloatElement(((FloatElement) element).getFloatValue() + getFloatValue());
        }

        if (element instanceof FractionElement) {
            FractionElement fraction = (FractionElement) element;
            LinearReader reader = new LinearReader();
            reader.add(this.clone());

            BracketsElement brackets = new BracketsElement(fraction.getDown().clone(), Plus);
            reader.executeMultiplying(stepper, brackets);

            BracketsElement result = new BracketsElement(reader.clone(), Plus);

            fraction.getUp().add(result);

            return fraction.clone();
        }
        return new ErrorElement("Элемент не слагаем со мной(я - FloatElement он - " + element.getClass().getSimpleName() + ')');
    }

    @Override
    public Element[] update(Stepper stepper) {
        return toArray(new ErrorElement("Я не обновляем - Float Element"));
    }

    @Override
    public EquationSide getPreferredSide() {
        return PREFERED_SIDE;
    }

    @Override
    public String toString() {
        return mark.toString() + value;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {
        StringBuilder builder = new StringBuilder();
        if (needBrackets && (mark == Mark.Minus)) builder.append('(');
        if (needMark || (mark == Mark.Minus)) builder.append(mark.toString());
        if (needMark) builder.append(' ');
        if ((value % 1.0) == 0) builder.append((int) value); else builder.append(value);
        if (needBrackets && (mark == Mark.Minus)) builder.append(')');
        return builder.toString();
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {

        if (multiplier instanceof FloatElement) {
            return new FloatElement(getFloatValue() * ((FloatElement) multiplier).getFloatValue());
        }
        if (multiplier instanceof MonomialElement) {
            return multiplier.multBy(this, stepper).clone();
        }
        if (multiplier instanceof BracketsElement) {
            return multiplier.multBy(this, stepper).clone();
        }
        if (multiplier instanceof FractionElement) {
            ((FractionElement) multiplier).getUp().executeMultiplying(stepper, this);
            return multiplier.clone();
        }
        return new ErrorElement(String.format("Умножение на %s не поддерживается - %s", multiplier.getClass().getSimpleName(), getClass().getSimpleName()));
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        if (divider instanceof BracketsElement) {
            return new FractionElement(mark, new FloatElement(value, Plus), divider);
        }
        if (divider instanceof FloatElement) {
            if (DecideUtil.isGoodDivision(value, ((FloatElement) divider).getFloatValue())) return new FloatElement(value / ((FloatElement) divider).getFloatValue());
            else return new FractionElement(Plus, this.clone(), divider.clone());

        }
        if (divider instanceof FractionElement) {
            FractionElement fraction = (FractionElement) divider;
            return multBy(fraction.swapped(), stepper);
        }
        return new ErrorElement(String.format("Деление на %s не поддерживается - %s", divider.getClass().getSimpleName(), getClass().getSimpleName()));
    }

    @Override
    public Element clone() {
        return new FloatElement(getFloatValue());
    }

    public float getFloatValue() {
        float value = this.value;
        if (mark == Mark.Minus) value *= -1;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FloatElement that = (FloatElement) o;

        return Float.compare(that.value, value) == 0 && mark == that.mark;
    }

    @Override
    public int hashCode() {
        int result = (value != +0.0f ? Float.floatToIntBits(value) : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        return result;
    }

    @Override
    public String rendToHTML(boolean needBrackets, boolean needMark, boolean needTextTag) {
        StringBuilder builder = new StringBuilder();

        builder.append(mark == Mark.Minus || needMark ? mark : "");
        builder.append(needMark ? " " : "");
        if ((value % 1.0) == 0) builder
                .append((int) value);
        else builder
                .append(value);
        if (needTextTag) {
            return String.format("<text>%s</text>", builder.toString());
        } else {
            return builder.toString();
        }

    }


    public void setFloat(float value) {
        if (value < 0) mark = Mark.Minus;
        this.value = Math.abs(value);
    }

    public boolean hasFraction() {
        return getFloatValue() % 1.0f != 0;
    }

    public Element convertToFraction() {
        if (!hasFraction()) return this.clone();

        float fractionPart = getFloatValue() % 1.0f;
        int powerOfTen = String.valueOf(fractionPart).length() - 2;

        float fractionUp = fractionPart * (float) Math.pow(10, powerOfTen);
        float fractionDown = (float) Math.pow(10, powerOfTen);

        return new FractionElement(Plus, new FloatElement(fractionUp), new FloatElement(fractionDown));
    }
}
