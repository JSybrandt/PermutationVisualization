package model;

import java.util.List;

/*
Justin Sybrandt

Purpose:
Provides a common interface for all permutation generators
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
