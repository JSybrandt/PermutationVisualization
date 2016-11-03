package model;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by jsybran on 11/2/2016.
 */
public class PermutationTest {
    @Test
    public void getInverse() throws Exception {
        assert permLong.equals(permLong.getInverse().getInverse());
        assert permLong.getInverse().equals(permLong.getInverse().getInverse().getInverse());
    }


    @Test
    public void getInversionVecFromFact() throws Exception {
        int[] longCode = permLong.getInversionVector();
        int[] genCode = Permutation.getInversionVecFromFact(permLong.getFactoradic(),permLong.getLegnth());
        assert Arrays.equals(longCode,genCode);

        Permutation testPerm2 = new Permutation(3, 4, 2 ,1);
        int[] testCode2 = {3, 2, 0, 0};
        int testFact2 = 22;
        assert Arrays.equals(Permutation.getInversionVecFromFact(testFact2,4), testCode2);

    }

    @Test
    public void equals() throws Exception {

    }


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
        Permutation permLongTest = Permutation.fromFactoradic(permLong.getFactoradic(),permLong.getLegnth());
        assert permLong.equals(permLongTest);
        Permutation perm1Test = Permutation.fromFactoradic(21,4);
        assert perm1.equals(perm1Test);

    }

    @Test
    public void getFactoradic() throws Exception {
        assert permLong.getFactoradic() == 100577;
        assert perm1.getFactoradic() == 21;
        assert perm2.getFactoradic() == 4;

    }

    @Test
    public void getInversionVector() throws Exception {
        int[] longCode = permLong.getInversionVector();
        int[] longCorrect = {2, 3, 6, 4, 0, 2, 2, 1, 0};
        int[] longOptional = {0, 0, 0, 1, 4, 2, 1, 5, 7};
        assert Arrays.equals(longCode,longCorrect);

        int[] perm1Code = perm1.getInversionVector();
        int[] perm1Correct = {3, 1, 1, 0};
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