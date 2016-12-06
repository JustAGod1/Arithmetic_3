import Containers.FloatContainer;
import Containers.UnknownContainer;
import Elments.IElement;
import Enums.EquationMark;
import Enums.Mark;
import Enums.MultMark;
import Enums.RootIndex;
import Exceptions.ParsingException;
import Readers.Equation;
import Utility.ParsingErrorStream;
import Utility.ParsingPrintStream;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Created by Yuri on 21.10.16.
 */
public class Parser {

    private ContainersHelper helper = new ContainersHelper();
    private ElementInfo info = new ElementInfo();
    private ArrayList<IElement> leftSide;
    private ArrayList<IElement> rightSide;
    private EquationMark mark;
    private char[] target;

    public Parser(String target) {
        this.target = target.toCharArray();
    }

    public Equation parse() throws ParsingException {
        int i = 0;
        PrintStream oldOut = System.out;
        System.setOut(new ParsingPrintStream(oldOut));

        PrintStream oldErr = System.err;
        System.setErr(new ParsingErrorStream(oldErr));

        System.out.println(String.format("It's was like - \"%s\"", String.copyValueOf(target)));

        while (target.length > i) {
            char letter = target[i];
            switch (letter) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9': {
                    info.stringedFloat += letter;
                    break;
                }

                case ',':
                case '.': {
                    if (!info.isIntFound()) {
                        throw new ParsingException();
                    }
                    info.stringedFloat += '.';
                    break;
                }

                case '+':
                case '-': {
                    if (info.isIntFound()) {
                        addContainer();
                    }
                    info.markProcessing(Mark.parse(letter));
                    break;
                }

                case '≤':
                case '≥':
                case '=':
                case '<':
                case '>': {
                    addContainer();
                    equalsProcessing(letter);
                    break;
                }

                case ' ': {
                    if (info.isIntFound()) {
                        addContainer();
                    }
                    break;
                }

                case '*':
                case '/':
                case ':':
                case '\\':{
                    addContainer();
                    helper.addMulting(MultMark.parse(letter));
                    break;
                }

                case '(': {
                    addContainer();
                    helper.openBrackets(info.mark);
                    resetInfo();
                    break;
                }
                case ')': {
                    addContainer();
                    helper.closeBrackets();
                    break;
                }

                case 'x':
                case 'y':
                case 'w':
                case 'z': {
                    addUnknown(letter);
                    break;
                }

                case '^': {
                    addContainer();
                    helper.addExponent();
                    break;
                }

                case '√': {
                    addContainer();
                    addRoot();
                    break;
                }


            }
            i++;
        }

        addContainer();

        rightSide = helper.toArray();

        System.setOut(oldOut);
        System.setErr(oldErr);
        return new Equation(leftSide, mark, rightSide);

    }

    private void resetInfo() {
        info = new ElementInfo();
    }

    private void addUnknown(char mark) {
        UnknownContainer uc = new UnknownContainer(mark, info.mark);

        helper.addContainer(uc);

        /*else {
            if (info.isIntFound()) {
                addContainer();
            }
            helper.addMulting(MultMark.Mult);
            helper.addContainer(uc);
        }*/
    }

    private void addRoot() {
        if (info.isMarkFound()) {
            if (info.isIntFound()) {
                addContainer();
                helper.addRoot(RootIndex.Previous);
            }
            else {
                helper.addRoot(RootIndex.Two);
            }
        } else {
            addContainer();
            helper.addRoot(RootIndex.Previous);
        }
    }

    private void equalsProcessing(char mark) {
        leftSide = helper.toArray();

        this.mark = EquationMark.parse(mark);

        helper = new ContainersHelper();
    }

    private void addContainer() {
        if (!info.stringedFloat.equals("")) {
            float value = info.getFloat();
            FloatContainer container = new FloatContainer(value, info.mark);
            helper.addContainer(container);
            info = new ElementInfo();
        }



    }
}
