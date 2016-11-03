package model;

import java.util.List;

/**
 * Created by jsybran on 11/2/16.
 */
public abstract class PermutationGenerator {
    public int getPermutationSize() {
        return permutationSize;
    }

    protected int permutationSize;
    public PermutationGenerator(int size){
        permutationSize = size;
    }
    public abstract List<Permutation> generate();
}
