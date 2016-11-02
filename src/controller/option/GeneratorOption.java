package controller.option;

public enum GeneratorOption {
    FACTORADIC(0),HEAP(1),INSERT(2);
    private final int value;
    GeneratorOption(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (value){
            case 0:
                return "Factoradic";
            case 1:
                return "Heap";
            case 2:
                return "Insert";
        }
        return super.toString();
    }
    public static GeneratorOption fromInt(int i) {
        switch (i) {
            case 0:
                return FACTORADIC;
            case 1:
                return HEAP;
            case 2:
                return INSERT;
        }
        return null;
    }
}
