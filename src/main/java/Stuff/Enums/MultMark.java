package Stuff.Enums;

/**
 * Created by Yuri on 06.12.16.
 */
public enum  MultMark {Mult, Div;

    public static MultMark parse(char letter) {
        switch (letter) {
            case '*': return Mult;
            case '/':
            case '\\':
            case ':':
                return Div;

            default: return null;
        }
    }

    @Override
    public String toString() {
        switch (name()) {
            case "Mult": return "*";
            case "Div": return ":";
            default: return "";
        }
    }
}
