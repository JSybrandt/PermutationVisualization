package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Permutation {

    private int[] data;
    public int getLegnth(){return data.length;}
    public int get(int index){return data[index];}

    public static Permutation fromFactoradic(int factoradicNumber, int size){
        int[] code = getInversionVecFromFact(factoradicNumber,size);
        List<Integer> tempData = new LinkedList<>();
        for(int val = size; val > 0; val--){
            tempData.add(code[val-1],val);
        }
        int[] data = new int[size];
        for(int i = 0 ; i < size; i++){
            data[i] = tempData.get(i);
        }
        return new Permutation(data);
    }

    public int getFactoradic(){
        int[] code = getInversionVector();
        int result = 0;
        for(int radix = 0; radix < code.length; radix++){
            result += code[code.length-radix-1] * factorial(radix);
        }
        return result;
    }

    public static int factorial(int n){
        if(n == 0) return 1;

        int result = n;
        n--;
        while(n > 0){
            result *= n;
            n--;
        }
        return  result;
    }

    public static int[] getInversionVecFromFact(int val, int size){
        int[] code = new int[size];
        for(int radixBase = 1; radixBase <= size; radixBase++){
            code[size - radixBase] = val % radixBase;
            val /= radixBase;
        }
        return code;
    }

    public int[] getInversionVector(){
        //defaults to 0
        int[] code = new int[data.length];
        for(int i = 1; i <= data.length;i++){
            for(int j = 0; j < getIndexOf(i); j++){
                if(i < data[j])
                    code[i-1]++;
            }
        }
        return code;
    }

    public int getIndexOf(int value){
        if(value > getLegnth() || value <= 0) throw new IndexOutOfBoundsException("Requested Bad Value");
        for(int i = 0; i< data.length;i++)
            if(data[i]==value) return i;
        throw new IndexOutOfBoundsException("Requested Bad Value");
    }

    public static boolean isPermutation(int[] data){
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


    private String arrayToString(int[] arr){
        String res = "";
        for(int val : arr){
            res += val + " ";
        }
        return res.trim();
    }

    @Override
    public String toString() {

        return arrayToString(data)
                +"\n\t" + arrayToString(getInversionVector())
                +"\n\t" + getFactoradic();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permutation that = (Permutation) o;

        return Arrays.equals(data, that.data);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
