package controller.option;

public enum PermVisOption {
    FACT_NUMBER(0), CYCLIC(1), GRID(2), GRAPH(3), FACT_COLOR(4);
    private final int value;
    PermVisOption(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (value){
            case 0:
                return "Factoradic Number";
            case 1:
                return "Cyclic Notation";
            case 2:
                return "Grid Representation";
            case 3:
                return "Graph Representation";
            case 4:
                return "Factoradic Color";
        }
        return super.toString();
    }
    public static PermVisOption fromInt(int i) {
        switch (i) {
            case 0:
                return FACT_NUMBER;
            case 1:
                return CYCLIC;
            case 2:
                return GRID;
            case 3:
                return GRAPH;
            case 4:
                return FACT_COLOR;
        }
        return null;
    }
}
