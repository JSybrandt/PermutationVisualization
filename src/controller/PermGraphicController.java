package controller;

import controller.option.PermVisOption;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import model.Permutation;
import util.Vec2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Justin Sybrandt
Purpose:
The PermGraphicController displays a graphic for a specific permutation.
Based on the options sent to drawPermutation, a different graphic can be displayed

 Important Values:
 gc - canvas graphics context, used to draw shapes
 */
public class PermGraphicController extends Controller {

    private Canvas canvas;

    private final Paint LINE_COLOR = Color.BLACK;
    private final Paint CELL_COLOR = Color.RED;
    private final Paint DOT_COLOR = Color.GREY;
    private final Paint ARROW_COLOR = Color.GREEN;
    private final Paint TEXT_COLOR = Color.WHITE;

    private GraphicsContext gc;
    private Permutation perm;
    private PermVisOption option;

    @Override
    public void run() {
        if(perm != null && option != null)
            redraw();
    }



    public PermGraphicController(Canvas canvas) {
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
        gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
        if(perm == null) return;

        switch (option) {
            case GRID:
                redrawMatrix();
                break;
            case GRAPH:
                redrawGraph();
                break;
        }
    }

    //draws a cycle graph
    private void redrawGraph(){

        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double radius = Math.min(width,height) /3;

        Vec2 center = new Vec2(width/2,height/2);
        Vec2 radVec = new Vec2(radius,0);

        double angle = 2*Math.PI / perm.getLegnth();
        double dotRadius = Math.min(width,height) / 16;
        Vec2 dotSize = new Vec2(dotRadius,dotRadius);

        Map<Integer,Vec2> positions = new HashMap<>();

        //font used to label values
        gc.setFont(new Font(dotRadius*2));

        for(int i = 0; i < perm.getLegnth(); i++){
            positions.put(i+1,radVec.rotate(angle*i).plus(center));
        }

        //draw dots with text
        for(int i = 1; i <= perm.getLegnth(); i++){
            Vec2 corner = positions.get(i).minus(dotSize);
            gc.setFill(DOT_COLOR);
            gc.fillOval(corner.X(), corner.Y(), dotRadius*2, dotRadius*2);
            gc.setFill(TEXT_COLOR);
            gc.fillText(Integer.toString(i),corner.X()+dotRadius/2,corner.Y()+dotRadius*1.75);
        }

        //colors for arrows
        gc.setStroke(ARROW_COLOR);
        gc.setFill(ARROW_COLOR);

        //draw arrows between points
        for(int i = 0; i < perm.getLegnth(); i++){
            Vec2 diff = positions.get(i+1).minus(positions.get(perm.get(i)));
            diff = diff.unit().scale(dotRadius);
            //adjust arrow size so they don't overlap
            drawArrow(positions.get(i+1).minus(diff),positions.get(perm.get(i)).plus(diff));
        }
    }

    //draws matrix with cycle arrows
    private void redrawMatrix(){

        double width = canvas.getWidth();
        double height = canvas.getHeight();

        double cellWidth = width / perm.getLegnth();
        double cellHeight = height / perm.getLegnth();

        double dotRadius = Math.min(cellWidth,cellHeight) / 8;

        gc.setFill(CELL_COLOR);

        //draw cells corresponding to permutation values
        for(int i = 0; i < perm.getLegnth(); i++){
            double top = i * cellHeight;
            double left = (perm.get(i)-1) * cellWidth;
            gc.fillRect(left,top,cellWidth,cellHeight);
        }

        gc.setStroke(LINE_COLOR);

        //draw grid lines
        for(int i = 0; i <= perm.getLegnth(); i++){
            gc.strokeLine(0,i*cellHeight,width, i*cellHeight);
            gc.strokeLine(i*cellWidth, 0, i*cellWidth, height);
        }

        gc.setFill(DOT_COLOR);

        //draw dots along the diagonal
        for(int i = 0; i < perm.getLegnth(); i++){
            double centerX = i*cellWidth + cellWidth/2;
            double centerY = i*cellHeight + cellHeight/2;
            gc.fillOval(centerX-dotRadius, centerY-dotRadius, dotRadius*2, dotRadius*2);
        }

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);

        //draw cycles
        for(List<Integer> cycle : perm.getCycles()){
            //for each pair in the cycle
            for(int i = 0; i < cycle.size(); i++){
                int source = cycle.get(i)-1;
                int target = cycle.get((i+1) % cycle.size())-1;

                //if not a 1 cycle
                if(source != target) {
                    //draw arrow cycle
                    double sX = source * cellWidth + cellWidth / 2;
                    double sY = source * cellHeight + cellHeight / 2;
                    double tX = target * cellWidth + cellWidth / 2;
                    double tY = target * cellHeight + cellHeight / 2;
                    gc.strokeLine(sX,sY,tX,sY);
                    drawArrow(new Vec2(tX,sY), new Vec2(tX,tY));
                }
            }
        }
    }

    //encapsulates vector math needed to draw a rotated arrow
    private void drawArrow(Vec2 start, Vec2 end){
        Vec2 arrowLength = end.minus(start);
        Vec2[] arrowHeadPoly = {
                new Vec2(-10,-10), new Vec2(10,-10), new Vec2(0,0)
        };
        double rotation = arrowLength.angle(new Vec2(0,1));
        if(start.X()<end.X())
            rotation *= -1;

        //need to put in these arrays for use in polygon data structure
        double[] rotatedTriX = new double[arrowHeadPoly.length];
        double[] rotatedTriY = new double[arrowHeadPoly.length];

        //rotate and translate each point in the arrowHeadPoly
        for(int i = 0 ; i < arrowHeadPoly.length; i++){
            Vec2 rotVec = arrowHeadPoly[i].rotate(rotation).plus(end);
            rotatedTriX[i] = rotVec.X();
            rotatedTriY[i] = rotVec.Y();
        }

        //draw
        gc.strokeLine(start.X(),start.Y(),end.X(),end.Y());
        gc.fillPolygon(rotatedTriX,rotatedTriY,arrowHeadPoly.length);
    }
}
