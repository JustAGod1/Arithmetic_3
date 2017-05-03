package Stuff.Elments;

import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.Arrays;
import Stuff.Utilities.DecideUtil;
import Stuff.Utilities.Stepper;

import java.util.ArrayList;

import static Stuff.Enums.Mark.Minus;
import static Stuff.Enums.Mark.Plus;

/**
 * Created by Yuri on 23.12.16.
 */
public class FractionElement extends Element{

    public static final EquationSide PREFERRED_SIDE = EquationSide.RIGHT;
    private static final int PRIORITY = 1;
    private Mark mark;
    private LinearReader up = new LinearReader();
    private LinearReader down = new LinearReader();
    //private Index prefix = new FloatElement(0);

    public FractionElement(Mark mark, LinearReader up, LinearReader down) {
        this.mark = mark;
        this.up = up;
        this.down = down;
    }

    public FractionElement(Mark mark, Element up, Element down) {
        this.mark = mark;
        this.up.add(up);
        this.down.add(down);
    }

    @Override
    public void setExponent(LinearReader exponent) {

    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public boolean isDecided() {
        if (!up.isAlreadyDecided()) return false;
        if (!down.isAlreadyDecided()) return false;
        if (up.size() > 1) return true;
        if (down.size() > 1) return true;
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
        if (element instanceof FractionElement) {
            FractionElement fraction = (FractionElement) element;

            fraction.getUp().executeMultiplying(stepper, new BracketsElement(getDown(), Plus));
            getUp().executeMultiplying(stepper, new BracketsElement(fraction.getDown(), Plus));

            LinearReader newUp = getUp();
            newUp.add(new BracketsElement(fraction.getUp(), Plus));

            LinearReader newDown = getDown();
            newDown.executeMultiplying(stepper, new BracketsElement(fraction.getDown(), Plus));

            return new FractionElement(Plus, newUp, newDown);
        }

        if (element instanceof FloatElement) {
            return element.add(this, stepper);
        }

        return new ErrorElement("Сложение с " + element.getClass().getSimpleName() + " не поддерживается (FractionElement)");
    }

    @Override
    public Element[] update(Stepper stepper) {
        DecideUtil.decideLinearReader(up, stepper);
        DecideUtil.decideLinearReader(down, stepper);

        for (Element element : up) {
            if ((element instanceof FloatElement) && (((FloatElement) element).hasFraction())) {
                up.replace(element, ((FloatElement) element).convertToFraction());
            }
        }

        for (Element element : down) {
            if ((element instanceof FloatElement) && (((FloatElement) element).hasFraction())) {
                down.replace(element, ((FloatElement) element).convertToFraction());
            }
        }

        //FIXME: simplify(stepper);
        //extractNaturePart();

        if (up.isAlreadyDecided() && down.isAlreadyDecided() && up.size() == 1 && down.size() == 1) {
            /*if (!prefix.equals(new FloatElement(0))) {
                LinearReader res = new LinearReader();
                res.add(getUp().get(0).divideBy(down.get(0), stepper));
                res.add(prefix);
                res.executeAddition(Stepper.getVoidStepper(stepper.getEquation()));
                return res.toArray();
            }*/
            stepper.setComment("Деление дроби", true);
            Element res = getUp().get(0).divideBy(down.get(0), stepper);
            return toArray(res);
        }


        return toArray(this);
    }

    @Override
    public EquationSide getPreferredSide() {
        return PREFERRED_SIDE;
    }

    @Override
    public String rendToString(boolean needBrackets, boolean needMark) {

        return String.format(
                "%s%s|(%s):(%s)|",
                (needMark || mark == Mark.Minus) ? mark : "",
                needMark ? " " : "",
                up.toString(),
                down.toString());
    }

    @Override
    public Element multBy(Element multiplier, Stepper stepper) {
        up.executeMultiplying(stepper, multiplier);
        return this;
    }

    @Override
    public Element divideBy(Element divider, Stepper stepper) {
        down.executeMultiplying(stepper, divider);

        return this.clone();
    }

    @Override
    public Element clone() {
        return new FractionElement(mark, up.clone(), down.clone());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FractionElement) {
            FractionElement element = (FractionElement) obj;

            return element.down.equals(down) && element.up.equals(up) && element.mark.equals(mark);
        } else {
            return false;
        }
    }

    public void extractNaturePart() {
        // FIXME: 05.03.17 Исправить выделение целой части
        if (up.isAlreadyDecided() && down.isAlreadyDecided() && up.size() == 1 && down.size() == 1) {
            FloatElement upF = (FloatElement) up.get(0);
            FloatElement downF = (FloatElement) down.get(0);

            if (upF.hasFraction() || downF.hasFraction()) return;

            int part = ((int) upF.getFloatValue()) / ((int) downF.getFloatValue());
            int balance = (int) (upF.getFloatValue() - ((int) downF.getFloatValue()) * part);
            up.clear();
            up.add(new FloatElement(balance));
            //prefix = new FloatElement(part);
        }
    }

    @Override
    public String rendToHTML(boolean needBrackets, boolean needMark, boolean needTextTag) {
        StringBuilder builder = new StringBuilder();

        if (needTextTag) builder.append("<text>");
        builder.append(needMark || mark == Mark.Minus ? mark : "");
        builder.append(needMark ? " " : "");
        if (needTextTag) builder.append("</text>");

        //builder.append(prefix.rendToHTML(false, needMark, true));
        builder
                .append("<math>" + "<mfrac>" + "<mi>")
                .append(up.toHTML(false))
                .append("</mi>").append("<mi>")
                .append(down.toHTML(false))
                .append("</mi>").append("</mfrac>")
                .append("</math>");
        return builder.toString();
    }

    public LinearReader getDown() {
        return down;
    }

    public LinearReader getUp() {
        LinearReader res = up;
        if (getMark() == Minus) {
            BracketsElement brackets = new BracketsElement(res.clone(), Minus);
            res.clear();
            res.add(brackets);
        }
        return res;
    }

    @Override
    public String toString() {
        return rendToString(false, true);
    }

    public FractionElement swapped() {
        return new FractionElement(mark, down, up);
    }

    private void simplify(Stepper stepper) {
        if (up.size() == 1 && up.isHas(FloatElement.class) && down.size() == 1 && down.isHas(FloatElement.class)) {
            stepper.step();
            FloatElement upF = (FloatElement) up.get(0);
            FloatElement downF = (FloatElement) down.get(0);

            simplify(upF, downF);

            up.set(0, upF);
            down.set(0, downF);
            stepper.step();
        }
    }

    private void simplify(FloatElement first, FloatElement second) {

        float[] firstFactorsRaw = DecideUtil.toFactors(first.getFloatValue());
        float[] secondFactorsRaw = DecideUtil.toFactors(second.getFloatValue());

        ArrayList<Float> firstFactors = new ArrayList<>();
        ArrayList<Float> secondFactors = new ArrayList<>();

        Arrays.addAll(firstFactors, firstFactorsRaw);
        Arrays.addAll(secondFactors, secondFactorsRaw);

        float firstRaw = first.getFloatValue();
        float secondRaw = second.getFloatValue();

        float same = getEqual(firstFactors, secondFactors);

        while (same != 0) {
            firstRaw /= same;
            secondRaw /= same;

            firstFactors.remove(same);
            secondFactors.remove(same);

            same = getEqual(firstFactors, secondFactors);
        }

        first.setFloat(firstRaw);
        second.setFloat(secondRaw);


    }

    private float getEqual(ArrayList<Float> first, ArrayList<Float> second) {
        for (Float f : first) {
            if (second.contains(f)) return f;
        }
        return 0;
    }

}
