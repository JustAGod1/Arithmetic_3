package Parsing.Utility;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by Yuri on 23.10.16.
 */
public class ParsingErrorStream extends PrintStream {

    public ParsingErrorStream(OutputStream out) {
        super(out);
    }

    @Override
    public void print(String s) {
        super.print("[Parsing Error]  " + s);
    }
}
