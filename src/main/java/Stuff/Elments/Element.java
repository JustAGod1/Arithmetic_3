package Stuff.Elments;


import Stuff.Elments.GostlyElements.ErrorElement;
import Stuff.Enums.EquationSide;
import Stuff.Enums.Mark;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.DecideUtil;
import Stuff.Utilities.Stepper;

/**
 * Created by Yuri on 06.12.16.
 */
public abstract class Element implements Cloneable {

    //Пока что поддерживается только натуральная степень

    private LinearReader exponent;


    public abstract int getPriority();

    public abstract boolean isDecided();

    public abstract Mark getMark();

    public abstract void setMark(Mark mark);

    /**
     * Похожими элементами считаются элементы которые можно сложить.
     * @param element ну-с тут все понятно.
     * @return Похож ли этот элемент на входящий параметр.
     */

    public abstract boolean isSimilar(Element element);

    /**
     * Сложение похожих элементов.
     * @param element похожий элемент.
     * @param stepper он умеет записывать шаги.
     * @see Stepper
     * @return элемнт который получился в результате сложения.
     */

    public abstract Element add(Element element, Stepper stepper);

    /**
     * Обновление интерфейса для решения внутрених проблем. Например: сокращение дроби или открытие скобок.
     * @param stepper он умеет записывать шаги.
     * @see Stepper
     * @return возвращает елемент, который получился после обновления.
     */
    public abstract Element[] update(Stepper stepper);


    /**
     * @return сторону уравнения которая наиболее подходит этому элементу(так например х всегда стремится к левой(LEFT) стороне уравнения)
     */
    public abstract EquationSide getPreferredSide();

    /**
     * Красиво вырисовывает себя в строчку
     * @param needBrackets Нужно ли элементу заключать себя в строчку
     *                     Пример: 5 * -9 - не хорошо (needBrackets => false)
     *                             5 * (-9) - хорошо (needBrackets => true)
     * @param needMark Нужно ли элементу показывать свой знак(минус показывается в любом случае)
     *                 Пример: -6 7 - не хорошо (needMark => false)
     *                         -6 + 7 - хорошо (needMark => true)
     * @return Строчку в которой изображен элемент в соответсвии со всеми параметрами
     */
    public abstract String rendToString(boolean needBrackets, boolean needMark);

    /**
     * @param needBrackets Нужны ли скобки
     * @param needMark Нужен ли знак(плюс или минус) перед элементом
     * @param needTextTag нужен ли html таг <code><text><text></text></code>
     * @return html-ое представление объекта
     */
    public String rendToHTML(boolean needBrackets, boolean needMark, boolean needTextTag) {
        return (needTextTag ? "<text>":"") + "Ha-ha-ha this Element(" + getClass().getSimpleName() + ")does not support HTML Layout" + (needTextTag ? "</text>":"");
    }

    /**
     * Вызывается при умножении двух элемнтов
     * @param multiplier Множитель
     * @param stepper он умеет запичывать шаги
     * @return элемент который получился в результате умножения(лучше использовать clone())
     */
    public abstract Element multBy(Element multiplier, Stepper stepper);

    /**
     * Вызывается при делении двух элемнтов
     * @param divider Делитель
     * @param stepper он умеет запичывать шаги
     * @return элемент который получился в результате деления(лучше использовать clone())
     */
    public abstract Element divideBy(Element divider, Stepper stepper);

    /**
     * @param exponent Новая степень
     */
    public void setExponent(LinearReader exponent) {
        this.exponent = exponent;
    }

    /**
     * @return Степень
     */
    public LinearReader getExponent() {
        return exponent;
    }

    /**
     * @param stepper Он умеет записывать шаги
     * @see Stepper
     * @return Возможно ли возведение этого элемента в текущюю степень
     */
    public boolean canRaiseToExponent(Stepper stepper) {
        DecideUtil.decideLinearReader(exponent, stepper);



        Element tmp = exponent.get(0);
        // TODO: Ввести степени существенного типа
        if (tmp instanceof FloatElement && exponent.size() == 1) {
            if (!((FloatElement) tmp).hasFraction()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param stepper Он умеет записывать шаги
     * @see Stepper
     * @return Элемент, который получился в результате возведения в степень
     */
    public Element raiseToExponent(Stepper stepper) {
        Element result = this.clone();

        if (!canRaiseToExponent(stepper)) {
            return new ErrorElement("Не могу возвестись в степень " + exponent.toHTML(true));
        }

        for (int i = 1; i < ((FloatElement) exponent.get(0)).getFloatValue(); i++) {
            result.multBy(this, stepper);
        }

        return result;
    }

    public abstract Element clone();

    @Override
    public abstract boolean equals(Object obj);

    public Element[] toArray(Element... elements) {
        return elements;
    }

    public void swapMark() {
        if (getMark() == Mark.Minus) setMark(Mark.Plus);
        else setMark(Mark.Minus);
    }
}
