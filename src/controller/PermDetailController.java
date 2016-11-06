package controller;

import controller.option.PermVisOption;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import model.Permutation;
import view.TabDetailPane;

/**
 * Created by jsybran on 11/5/2016.
 */
public class PermDetailController extends Controller {
    TabDetailPane tabDetailPane;
    Permutation permutation;
    Canvas matrixCanvas;
    Canvas graphCanvas;
    PermDisplayController matrixController;
    PermDisplayController graphController;

    Label permValue;
    Label permCycle;
    Label permFactoradic;

    public PermDetailController(TabDetailPane tabDetailPane) {
        super(tabDetailPane);
        this.tabDetailPane = tabDetailPane;
        matrixCanvas = new Canvas(200,200);
        graphCanvas = new Canvas(200,200);
        matrixController = new PermDisplayController(matrixCanvas);
        graphController = new PermDisplayController(graphCanvas);
    }

    @Override
    public void run() {
        tabDetailPane.addTab("Matrix").setContent(matrixCanvas);
        tabDetailPane.addTab("Graph").setContent(graphCanvas);
        permValue = tabDetailPane.addDetail("Permutation");
        permCycle = tabDetailPane.addDetail("Cyclic Notation");
        permFactoradic = tabDetailPane.addDetail("Factoradic Number");
    }

    public void setPermutation(Permutation permutation){
        this.permutation = permutation;
        permValue.setText(permutation.toString());
        permCycle.setText(permutation.toCyclicNotation());
        permFactoradic.setText(Integer.toString(permutation.getFactoradic()));
        matrixController.drawPermutation(permutation, PermVisOption.GRID);
        graphController.drawPermutation(permutation,PermVisOption.GRAPH);
    }

}
