package Elments;

import Enums.Mark;

/**
 * Created by Yuri on 06.12.16.
 */
public class UnknownElement implements IElement {
    private final Mark mark;
    private final char letter;

    public UnknownElement(Mark mark, char letter) {
        this.mark = mark;
        this.letter = letter;
    }
}
