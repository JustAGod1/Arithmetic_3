package Utility;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Yuri on 22.10.16.
 */
public class ParsingPrintStream extends PrintStream{


    public ParsingPrintStream(OutputStream out) {
        super(out);
    }

    @Override
    public void print(String s) {
        super.print("[Parsing]  " + s);
    }
}
