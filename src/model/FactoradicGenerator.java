package model;

import java.util.ArrayList;
import java.util.List;

import static util.FactorialMath.factorial;

/*
Justin Sybrandt

Purpose:
Generates permutations in factoradic order
Uses Permutation.fromFactoradic to do this easily


 */
public class FactoradicGenerator extends PermutationGenerator {

    public FactoradicGenerator(int size){
        super(size);
    }

    @Override
    public List<Permutation> generate() {
        List<Permutation> res = new ArrayList<>();
        for(int fact = 0; fact < getFactoradicBounds(permutationSize); fact++){
            res.add(Permutation.fromFactoradic(fact,permutationSize));
        }
        return res;
    }

    private static int getFactoradicBounds(int size){
        int res = 1;
        for(int i = 0; i < size; i++){
            res += i * factorial(i);
        }
        return res;
    }
}
