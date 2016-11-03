package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsybran on 11/3/2016.
 */
public class SwapGenerator extends PermutationGenerator {
    public SwapGenerator(int size) {
        super(size);
    }

    @Override
    public List<Permutation> generate() {
        int[] initData = new int[permutationSize];
        for(int i = 1; i <= permutationSize; i++)
            initData[i-1] = i;
        return generationHelper(permutationSize, initData);
    }

    private List<Permutation> generationHelper(int n, int[] data){
        if(n == 1) {
            List<Permutation> res = new ArrayList<>();
            res.add(new Permutation(deepCopy(data)));
            return res;
        } else {
            List<Permutation> res = new ArrayList<>();
            for(int i = 0 ; i < n - 1; i++){
                res.addAll(generationHelper(n - 1, data));
                if(n%2==0){//n is even
                    swap(i,n-1,data);
                }else{//n is odd
                    swap(0, n-1, data);
                }
            }
            res.addAll(generationHelper(n-1,data));
            return res;
        }
    }

    private void swap(int i, int j, int[] data){
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    private int[] deepCopy(int[] data){
        int[] cp = new int[data.length];
        System.arraycopy(data, 0, cp, 0, data.length);
        return cp;
    }
}
