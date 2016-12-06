package Enums;

/**
 * Created by Yuri on 06.12.16.
 */
public enum Mark {Plus, Minus;

    public static Mark parse(char letter) {
        switch (letter) {
            case '+': return Plus;
            case '-': return Minus;
            default: return null;
        }
    }
}
