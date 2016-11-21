package model;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static util.FactorialMath.factorial;

/*
Justin Sybrandt
Purpose:
Generates permutations by iteratively inserting different values in varying locations.
 */
public class InsertionGenerator extends PermutationGenerator {

    public InsertionGenerator(int size){
        super(size);
    }

    @Override
    public List<Permutation> generate() {
        List<Permutation> res = new ArrayList<>();

        //stored as a very mutable datastructure durring construction to make insertions easy
        LinkedList<List<Integer>> permConstructionList = new LinkedList<>();
        //start with empty list
        permConstructionList.add(new ArrayList<>());
        //adds a single 1 to initial list
        permConstructionList.get(0).add(1);

        //permConstructionList is only going to contain less than full perms
        //this code is practically a BFS through permutation space
        while(!permConstructionList.isEmpty()){

            List<Integer> current = permConstructionList.pollFirst();

            //if we have a full permutation
            if(current.size() == permutationSize){
                res.add(list2Perm(current));
            }else if (current.size()<permutationSize) {
                //add an unused element in all possible spots
                int newVal = current.size() + 1;
                for(int i = 0; i <= current.size(); i++){
                    List<Integer> cp = deepCopy(current);
                    cp.add(i,newVal);
                    permConstructionList.add(cp);
                }
            }
        }
        return res;
    }

    private Permutation list2Perm(List<Integer> data){
        return new Permutation(data.stream().mapToInt(i->i).toArray());
    }

    private List<Integer> deepCopy(List<Integer> source){
        List<Integer> res = new ArrayList<>(source.size());
        for(int i : source){
            res.add(i);
        }
        return res;
    }
}
