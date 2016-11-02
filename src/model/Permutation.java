package model;

/**
 * Created by jsybran on 11/2/16.
 */
public class Permutation {

    private int[] data;
    public int getLegnth(){return data.length;}
    public int get(int index){return data[index];}
    public int getFactoradic(){
        int[] inversions = getInversions();
        int result = 0;
        for(int radix = 1; radix <= inversions.length; radix++){
            result += inversions[inversions.length - radix] * factorial(radix);
        }
        return result;
    }

    private int factorial(int n){
        if(n == 0) return 1;

        int result = n;
        n--;
        while(n > 0){
            result *= n;
            n--;
        }
        return  result;
    }

    public int[] getInversions(){
        //defaults to 0
        int[] inversions = new int[data.length];
        for(int permValue = 1; permValue <= data.length; permValue++){
            for(int indexBelowValue = 1; indexBelowValue < getIndexOf(permValue);indexBelowValue++){
                if(permValue < data[indexBelowValue]) inversions[permValue-1]++;
            }
        }
        return  inversions;
    }

    public int getIndexOf(int value){
        if(value > getLegnth() || value <= 0) throw new IndexOutOfBoundsException("Requested Bad Value");
        for(int i = 0; i< data.length;i++)
            if(data[i]==value) return i;
        throw new IndexOutOfBoundsException("Requested Bad Value");
    }

    public boolean isPermutation(int[] data){
        //defaults to false
        boolean[] seen = new boolean[data.length];
        for(int d : data)
            if(d > 0 && d <= data.length)
                seen[d-1]=true;
        for(boolean b : seen)
            if(!b) return false;
        return true;
    }

    public Permutation(int a){this(new int[]{a});}
    public Permutation(int a, int b){this(new int[]{a,b});}
    public Permutation(int a, int b, int c){this(new int[]{a,b,c});}
    public Permutation(int a, int b, int c, int d){this(new int[]{a,b,c,d});}
    public Permutation(int a, int b, int c, int d, int e){this(new int[]{a,b,c,d,e});}
    public Permutation(int a, int b, int c, int d, int e, int f){this(new int[]{a,b,c,d,e,f});}
    public Permutation(int a, int b, int c, int d, int e, int f, int g){this(new int[]{a,b,c,d,e,f,g});}
    public Permutation(int[] data){
        if(isPermutation(data)){
            this.data = data;
        }
        else
            throw new IllegalArgumentException("Data provided is not a permutation.");
    }


}
