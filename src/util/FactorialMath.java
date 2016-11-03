package util;

public class FactorialMath {
    private static FactorialMath ourInstance = new FactorialMath();

    public static FactorialMath getInstance() {
        return ourInstance;
    }

    private FactorialMath() {
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
}
