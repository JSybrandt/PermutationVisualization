package controller;

import controller.option.PermVisOption;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
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
    public PermDetailController(TabDetailPane tabDetailPane, Permutation perm) {
        super(tabDetailPane);
        this.tabDetailPane = tabDetailPane;
        matrixCanvas = new Canvas(200,200);
        graphCanvas = new Canvas(200,200);
        matrixController = new PermDisplayController(matrixCanvas);
        graphController = new PermDisplayController(graphCanvas);
        this.permutation = perm;
        this.run();
    }

    @Override
    public void run() {
        matrixController.drawPermutation(permutation, PermVisOption.GRID);
        graphController.drawPermutation(permutation,PermVisOption.GRAPH);
        tabDetailPane.addTab("Matrix",matrixCanvas);
        tabDetailPane.addTab("Graph",graphCanvas);
        tabDetailPane.addDetail("Permutation",permutation.toString());
        tabDetailPane.addDetail("Cyclic Notation",permutation.toCyclicNotation());
        tabDetailPane.addDetail("Factoradic Number",Integer.toString(permutation.getFactoradic()));
    }

}
