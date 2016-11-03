package model;

import com.sun.deploy.util.ArrayUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static util.FactorialMath.factorial;

/**
 * Created by jsybran on 11/2/16.
 */
public class InsertionGenerator extends PermutationGenerator {

    public InsertionGenerator(int size){
        super(size);
    }

    @Override
    public List<Permutation> generate() {
        List<Permutation> res = new ArrayList<>();
        LinkedList<List<Integer>> tempData = new LinkedList<>();
        tempData.add(new ArrayList<>());
        tempData.get(0).add(1);
        while(!tempData.isEmpty()){
            List<Integer> currData = tempData.pollFirst();
            if(currData.size() == permutationSize){
                res.add(list2Perm(currData));
            }else if (currData.size()<permutationSize) {
                int newVal = currData.size() + 1;
                for(int i = 0; i <= currData.size(); i++){
                    List<Integer> cp = deepCopy(currData);
                    cp.add(i,newVal);
                    tempData.add(cp);
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
