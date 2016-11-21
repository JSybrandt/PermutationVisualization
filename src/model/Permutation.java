package model;

import java.util.*;

import static util.FactorialMath.factorial;

/*
Justin Sybrandt

Purpose:
This stores permutation data and makes permutation logic easy.
Written to make permutation objects lightweight and immutable
Permutation manipulations instead generate more permutation elements

Important Values:
Data - an array with permutation elements in order
fromFactoradic - makes a permutation given a size and factoradic number
toFactoradic - gets Factoradic number from permutation
getInversionVecFromFact - gets a series of inversions corresponding to a factoradic number
 */
public class Permutation {

    private int[] data;
    public int getLegnth(){return data.length;}
    public int get(int index){return data[index];}

    //alternate constructor (named differently to not conflict with
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

    //returns the inversion vector corresponding to a particular factoradic number
    public static int[] getInversionVecFromFact(int val, int size){
        int[] code = new int[size];
        for(int radixBase = 1; radixBase <= size; radixBase++){
            code[size - radixBase] = val % radixBase;
            val /= radixBase;
        }
        return code;
    }

    //returns inversion vector corresponding to this permutation
    //runs left-right comparing out of order pairs
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

    //these constructors make testing really really easy
    public Permutation(int a){this(new int[]{a});}
    public Permutation(int a, int b){this(new int[]{a,b});}
    public Permutation(int a, int b, int c){this(new int[]{a,b,c});}
    public Permutation(int a, int b, int c, int d){this(new int[]{a,b,c,d});}
    public Permutation(int a, int b, int c, int d, int e){this(new int[]{a,b,c,d,e});}
    public Permutation(int a, int b, int c, int d, int e, int f){this(new int[]{a,b,c,d,e,f});}
    public Permutation(int a, int b, int c, int d, int e, int f, int g){this(new int[]{a,b,c,d,e,f,g});}

    //main contstuctor
    public Permutation(int[] data){
        if(isPermutation(data)){
            this.data = Arrays.copyOf(data,data.length);
        }
        else
            throw new IllegalArgumentException("Data provided is not a permutation.");
    }

    //used for printing
    private String arrayToString(int[] arr){
        String res = "";
        for(int val : arr){
            res += val + " ";
        }
        return res.trim();
    }

    public Permutation getInverse(){
        int[] newData = new int[data.length];
        for(int val = 1; val <= data.length; val++){
            newData[data[val-1]-1] = val;
        }
        return new Permutation(newData);
    }

    //generates a cycle graph, shows how data changed from identity
    public Map<Integer,Integer> toGraph(){
      Map<Integer,Integer> graph = new HashMap<>();
        for(int i = 1; i <= data.length; i++){
            graph.put(i,data[i-1]);
        }
        return graph;
    }

    public List<List<Integer>> getCycles(){
        List<List<Integer>> cycles = new ArrayList<>();

        Set<Integer> seen = new HashSet<>();

        //gets permutation graph
        Map<Integer,Integer> graph = toGraph();

        //performs a DFS-like traversal looking for cycles in permutation graph
        for(int i = 1; i <= data.length; i++){
            if(!seen.contains(i)){
                int currIndex = i;
                List<Integer> cycle = new ArrayList<>();
                while(!seen.contains(currIndex)){
                    cycle.add(currIndex);
                    seen.add(currIndex);
                    currIndex = graph.get(currIndex);
                }
                cycles.add(cycle);
            }
        }
        return cycles;
    }

    public String toCyclicNotation(){
        List<List<Integer>> cycles = getCycles();
        String res = "";
        for(List<Integer> cycle : cycles){
            String subString = "";
            for(int val : cycle)
                subString += val + " ";
            subString = subString.trim();
            if(cycle.size() > 1)
                subString = "(" + subString + ")";
            res += subString + " ";
        }
        return res.trim();
    }

    //performs elementary adjacent swaps to find all adjacent permutations
    public List<Permutation> getAdjacentPerms(){
        int[] data = this.getData();
        List<Permutation> adjPerms = new ArrayList<>();
        for(int i  = 1 ; i < data.length; i++){
            int[] cp = Arrays.copyOf(data,data.length);
            int tmp = cp[i-1];
            cp[i-1] = cp[i];
            cp[i] = tmp;
            adjPerms.add(new Permutation(cp));
        }
        return adjPerms;
    }

    //returns number of elements shared with the identity
    public int getNumFixed(){
        int count = 0;
        for(int i = 0 ; i < data.length; i++)
            if(i+1 == data[i])
                count++;
        return count;
    }

    public int[] getData(){return data;}

    @Override
    public String toString() {

        return arrayToString(data);
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
