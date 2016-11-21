package controller;

import controller.option.PermVisOption;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import model.Permutation;
import view.DetailPane;

/*
Justin Sybrandt

Purpose:
The perm detail controller shows information on the right side for a specific permutation
This controller contacts the PermGraphicController to draw grid and graph on right side.

Important Values:
matrixController
graphController
 */
public class PermDetailController extends Controller {
    DetailPane detailPane;
    Permutation permutation;
    Canvas matrixCanvas;
    Canvas graphCanvas;
    PermGraphicController matrixController;
    PermGraphicController graphController;

    Label permValue;
    Label permCycle;
    Label permFactoradic;

    public PermDetailController(DetailPane detailPane) {
        super(detailPane);
        this.detailPane = detailPane;
        matrixCanvas = new Canvas(200,200);
        graphCanvas = new Canvas(200,200);
        matrixController = new PermGraphicController(matrixCanvas);
        graphController = new PermGraphicController(graphCanvas);
    }

    @Override
    public void run() {
        detailPane.addMainDetail("Matrix").getChildren().add(matrixCanvas);
        detailPane.addMainDetail("Graph").getChildren().add(graphCanvas);
        permValue = detailPane.addMinorDetail("Permutation");
        permCycle = detailPane.addMinorDetail("Cyclic Notation");
        permFactoradic = detailPane.addMinorDetail("Factoradic Number");
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
