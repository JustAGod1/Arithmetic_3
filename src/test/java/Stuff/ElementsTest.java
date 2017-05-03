package Stuff;

import Stuff.Elments.Element;
import Stuff.Elments.FloatElement;
import Stuff.Readers.Equation;
import Stuff.Readers.LinearReader;
import Stuff.Utilities.Stepper;
import org.junit.Test;

/**
 * Created by Yuri on 02.04.17.
 */
public class ElementsTest {

    @Test
    public void exponentTest() {
        Element element = new FloatElement(2);
        element.setExponent(new LinearReader(new FloatElement(5)));
        element = element.raiseToExponent(new Stepper(new Equation(null, null, null)));

        System.out.println(element);
    }
}
