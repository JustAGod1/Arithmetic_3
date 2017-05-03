package Stuff.Enums;

/**
 * Created by Yuri on 06.12.16.
 */
public enum EquationMark {Equal, More, Less, Equal_Or_More, Equal_Or_Less, Not_Equal;

    public static EquationMark parse(char mark) {
        switch (mark) {
            case '=': return Equal;
            case '≠': return Not_Equal;
            case '≥': return Equal_Or_More;
            case '≤': return Equal_Or_Less;
            case '>': return More;
            case '<': return Less;
            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (name()) {
            case "Equal": return "=";
            default: return "";
        }
    }
}
