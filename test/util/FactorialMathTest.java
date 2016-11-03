package util;

import org.junit.Test;

import static org.junit.Assert.*;
import static util.FactorialMath.factorial;

/**
 * Created by jsybran on 11/3/2016.
 */
public class FactorialMathTest {
    @Test
    public void factorialTest() throws Exception {
        assert factorial(0) == 1;
        assert factorial(1) == 1;
        assert factorial(2) == 2;
        assert factorial(3) == 6;
        assert factorial(4) == 24;
        assert factorial(5) == 120;
        assert factorial(6) == 720;
        assert factorial(7) == 5040;
        assert factorial(8) == 40320;
        assert factorial(9) == 362880;
    }

}