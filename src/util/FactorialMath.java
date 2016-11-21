package util;

/*
Justin Sybrandt

Purpose:
I needed a place to put a facotrial function that all classes could see.
This is a singleton value like Math
 */
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
