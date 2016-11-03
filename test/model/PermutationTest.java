package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by jsybran on 11/2/2016.
 */
public class PermutationTest {

    Permutation perm1;
    Permutation perm2;
    Permutation permLong;
    @Before
    public void setUp() throws Exception {

        perm1 = new Permutation(4, 2, 3, 1);
        perm2 = new Permutation(2, 3, 1);
        int[] data = {5,9,1,8,2,6,4,7,3};
        permLong = new Permutation(data);

    }

    @Test
    public void fromFactoradic() throws Exception {
        Permutation perm1Test = Permutation.fromFactoradic(21,4);
        assert perm1.equals(perm1Test);
        Permutation permLongTest = Permutation.fromFactoradic(permLong.getFactoradic(),permLong.getLegnth());
        assert permLong.equals(permLongTest);
    }

    @Test
    public void getFactoradic() throws Exception {
        assert perm1.getFactoradic() == 21;
        assert perm2.getFactoradic() == 3;
    }

    @Test
    public void getLehmerCode() throws Exception {
        int[] longCode = permLong.getLehmerCode();
        int[] longCorrect = {2, 3, 6, 4, 0, 2, 2, 1, 0};
        int[] longOptional = {0, 0, 0, 1, 4, 2, 1, 5, 7};
        assert Arrays.equals(longCode,longOptional);

        int[] perm1Code = perm1.getLehmerCode();
        int[] perm1Correct = {0, 1, 1, 3};
        assert Arrays.equals(perm1Code,perm1Correct);

    }

    @Test
    public void getIndexOf() throws Exception {
        assert perm1.getIndexOf(4) == 0;
        assert perm2.getIndexOf(1) == 2;
    }

    @Test
    public void isPermutation() throws Exception {
        assert !Permutation.isPermutation(new int[]{2,4,1,6});
        assert Permutation.isPermutation(new int[]{2,4,1,3});
    }

}