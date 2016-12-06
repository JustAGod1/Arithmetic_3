

import Enums.Mark;

/**
 * Created by Yuri on 21.10.16.
 */
public class ElementInfo {
    public Mark mark = Mark.Plus;
    public String stringedFloat = "";
    private boolean isMarkFound = false;

    public boolean isIntFound() {
        return  (!stringedFloat.equals(""));
    }

    public boolean isDotFound() {
        return stringedFloat.contains(".");
    }

    public void markProcessing(Mark mark) {
        if ((this.mark == Mark.Minus) && (mark == Mark.Minus)) this.mark = Mark.Plus;
        if ((this.mark == Mark.Plus) && (mark == Mark.Minus)) this.mark = Mark.Minus;

        isMarkFound = true;
    }

    public boolean isMarkFound() {
        return isMarkFound;
    }

    public float getFloat() {
        float res = Float.parseFloat(stringedFloat);

        return res;
    }
}
