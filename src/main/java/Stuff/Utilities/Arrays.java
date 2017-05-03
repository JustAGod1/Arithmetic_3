package Stuff.Utilities;

import java.util.List;

/**
 * Created by Yuri on 01.03.17.
 */
public final class Arrays {

    public static final void addAll(List<Float> floats, float[] arr) {
        for (float v : arr) {
            floats.add(v);
        }
    }
}
