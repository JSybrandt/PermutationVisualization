package controller;

import controller.option.PermVisOption;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import model.Permutation;
import util.Vec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jsybran on 11/3/16.
 */
public class PermMatrixController extends Controller {

    Canvas canvas;

    Paint lineColor = Color.BLACK;
    Paint cellColor = Color.RED;
    Paint dotColor = Color.GREY;
    Paint arrowColor = Color.GREEN;

    public void run() {

    }

    GraphicsContext gc;
    Permutation perm;
    PermVisOption option;

    public PermMatrixController(Canvas canvas) {
        super(canvas);
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());
    }

    public void drawPermutation(Permutation perm, PermVisOption opt){
        this.perm = perm;
        this.option = opt;
        redraw();
    }

    private void redraw(){
        if(perm == null) return;

        switch (option) {
            case FACT_NUMBER:
                break;
            case CYCLIC:
                break;
            case GRID:
                redrawMatrix();
                break;
            case GRAPH:
                redrawGraph();
                break;
            case FACT_COLOR:
                break;
        }
    }

    private void redrawGraph(){
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double radius = Math.min(width,height) /3;
        Vec2 center = new Vec2(width/2,height/2);
        Vec2 disp = new Vec2(radius,0);
        double angle = 2*Math.PI / perm.getLegnth();
        double dotRadius = Math.min(width,height) / 16;
        Map<Integer,Vec2> positions = new HashMap<>();
        for(int i = 0; i < perm.getLegnth(); i++){
            positions.put(i+1,disp.rotate(angle*i).plus(center));
        }

        gc.setFill(dotColor);
        Vec2 dotDisp = new Vec2(dotRadius,dotRadius);
        for(Vec2 vec : positions.values()){
            Vec2 corner = vec.minus(dotDisp);
            gc.fillOval(corner.X(), corner.Y(), dotRadius*2, dotRadius*2);
        }
        gc.setStroke(arrowColor);
        gc.setFill(arrowColor);
        for(int i = 0; i < perm.getLegnth(); i++){
            drawArrow(positions.get(i+1),positions.get(perm.get(i)));
        }
    }

    private void redrawMatrix(){
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double cellWidth = width / perm.getLegnth();
        double cellHeight = height / perm.getLegnth();
        double dotRadius = Math.min(cellWidth,cellHeight) / 8;

        gc.clearRect(0,0,width,height);


        gc.setFill(cellColor);
        for(int i = 0; i < perm.getLegnth(); i++){
            double top = i * cellHeight;
            double left = (perm.get(i)-1) * cellWidth;
            gc.fillRect(left,top,cellWidth,cellHeight);
        }

        gc.setStroke(lineColor);
        for(int i = 0; i <= perm.getLegnth(); i++){
            gc.strokeLine(0,i*cellHeight,width, i*cellHeight);
            gc.strokeLine(i*cellWidth, 0, i*cellWidth, height);
        }

        gc.setFill(dotColor);
        for(int i = 0; i < perm.getLegnth(); i++){
            double centerX = i*cellWidth + cellWidth/2;
            double centerY = i*cellHeight + cellHeight/2;
            gc.fillOval(centerX-dotRadius, centerY-dotRadius, dotRadius*2, dotRadius*2);
        }
    }

    private void drawArrow(Vec2 start, Vec2 end){
        Vec2 vec = end.minus(start);
        Vec2[] defaultArrowHead = {
                new Vec2(-10,-10), new Vec2(10,-10), new Vec2(0,0)
        };
        double rotation = vec.angle(new Vec2(0,1));
        double[] rotatedTriX = new double[defaultArrowHead.length];
        double[] rotatedTriY = new double[defaultArrowHead.length];

        for(int i = 0 ; i < defaultArrowHead.length; i++){
            Vec2 rotVec = defaultArrowHead[i].rotate(rotation).plus(end);
            rotatedTriX[i] = rotVec.X();
            rotatedTriY[i] = rotVec.Y();
        }

        gc.strokeLine(start.X(),start.Y(),end.X(),end.Y());
        gc.fillPolygon(rotatedTriX,rotatedTriY,defaultArrowHead.length);
    }
}
