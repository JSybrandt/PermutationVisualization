package controller.option;

public enum SetVisOption {
    FHEATMAP(0), ADJ_GRAPH(1),INSERT(2);
    private final int value;
    SetVisOption(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (value){
            case 0:
                return "Factoradic Heatmap";
            case 1:
                return "Heap";
            case 2:
                return "Insert";
        }
        return super.toString();
    }
    public static SetVisOption fromInt(int i) {
        switch (i) {
            case 0:
                return FHEATMAP;
            case 1:
                return ADJ_GRAPH;
            case 2:
                return INSERT;
        }
        return null;
    }
}
