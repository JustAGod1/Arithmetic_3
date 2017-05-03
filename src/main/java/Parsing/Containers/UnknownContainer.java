package Parsing.Containers;


import Stuff.Elments.Element;
import Stuff.Elments.MonomialElement;
import Stuff.Elments.UnknownElement;
import Stuff.Enums.Mark;

/**
 * Created by Yuri on 23.10.16.
 */
public class UnknownContainer extends Exponentable implements Container {

    private char letter;
    private Mark mark;

    public UnknownContainer(char letter, Mark mark) {

        this.letter = letter;
        this.mark = mark;
    }

    @Override
    public Element toElement() {
        UnknownElement ue = new UnknownElement(mark, letter);
        if (!(getExponent().equals(new FloatContainer(1, Mark.Plus)))) {
            ue.setExponent(getExponent().toElement());
        }
        return (new MonomialElement(ue));
    }
}
