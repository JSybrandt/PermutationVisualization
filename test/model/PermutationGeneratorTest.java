package model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static util.FactorialMath.factorial;

public class PermutationGeneratorTest {
    @Test
    public void InsertionGenTest() throws Exception {
        testGen(new InsertionGenerator(3));
        testGen(new InsertionGenerator(4));
        testGen(new InsertionGenerator(7));
    }

    @Test
    public void FactoradicGenTest() throws Exception {
        testGen(new FactoradicGenerator(3));
        testGen(new FactoradicGenerator(4));
        testGen(new FactoradicGenerator(7));
    }

    @Test
    public void SwapGenTest() throws Exception {
        testGen(new SwapGenerator(3));
        testGen(new SwapGenerator(4));
        testGen(new SwapGenerator(7));
    }

    public void testGen(PermutationGenerator gen) throws Exception{
        Set<Permutation> seen = new HashSet<>();
        for(Permutation p : gen.generate()){
            assert !seen.contains(p);
            seen.add(p);
        }
        //number of permutations of n perm n things
        assert seen.size() == factorial(gen.getPermutationSize());
    }
}