package be.ac.umons.bsp;

import java.awt.*;
import java.awt.geom.Arc2D;

/**
 * Created by clement on 2/20/16.
 */
public class Segment {
    //TODO retirer la couleur string et choper le switch au chargement et pas a la création
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private Color color;


    public Segment(double x1, double y1, double x2, double y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    /**
     * Computes the line joining the two points
     *
     * @return the line in ax + by + c = 0 with a,b,c in a double array
     */
    public double[] computeLine() {

        double[] line = new double[3];

        //If x2-x1 == 0 the line is vertical, its equation is 1*x + 0*y - x1
        if (x2 - x1 == 0) {
            line[0] = 1;
            line[1] = 0;
            line[2] = -x1;
            return line;
        } else {
            line[0] = (y2 - y1) / (x2 - x1);
            line[1] = -1;
            line[2] = y1 - (line[0] * x1);
            return line;
        }

    }

    public double[] computeTest(double[] line, Segment segment){
        double[] lineBis = this.computeLine();
        double[] intersectionPoint = new double[2];

            //If the point (x1,y1) from this segment is right of the line, return [+inf,+inf]
            if((segment.getX2() - segment.getX1())*(y2 - segment.getY1()) - (segment.getY2() - segment.getY1())*(x2 - segment.getX1()) < 0) {
                intersectionPoint[0] = Double.POSITIVE_INFINITY;
                intersectionPoint[1] = Double.POSITIVE_INFINITY;
                // System.out.println("Droite de la droite");
                return intersectionPoint;
            }
            else if((segment.getX2() - segment.getX1())*(y2 - segment.getY1()) - (segment.getY2() - segment.getY1())*(x2 - segment.getX1()) == 0) {
                System.out.println("FATAL ERROR FAILURE BOUM1");
                return intersectionPoint;
            }

            //If the point (x1,y1) from this segment is left of the line, return [-inf,-inf]
            else {
                intersectionPoint[0] = Double.NaN;
                intersectionPoint[1] = Double.NaN;
                //System.out.println("Gauche de la droite");
                return intersectionPoint;
            }
        }

    public double getSide(double[] line, double x, double y) {

        if((line[0] == 1) && (line[1] == 0)) {
            return -(line[0]*x + line[1]*y +line[2]);
        }

        return (line[0]*x + line[1]*y +line[2]);
    }

    public double[] computePosition(double[] line, Segment segment) {

        double[] lineBis = this.computeLine();
        double[] intersectionPoint = new double[2];

        //If the slopes are equal, there is no intersection
        if(line[0] == lineBis[0]) {
            //If the point (x1,y1) from this segment is right of the line, return [+inf,+inf]
            if(getSide(line, x2, y2) > 0) {
                intersectionPoint[0] = Double.POSITIVE_INFINITY;
                intersectionPoint[1] = Double.POSITIVE_INFINITY;
               // System.out.println("Droite de la droite");
                return intersectionPoint;
            }
            else if(getSide(line, x2, y2) == 0) {
                System.out.println("FATAL ERROR FAILURE BOUM2");
                return intersectionPoint;
            }
            //If the point (x1,y1) from this segment is left of the line, return [-inf,-inf]
            else {
                intersectionPoint[0] = Double.NaN;
                intersectionPoint[1] = Double.NaN;
                //System.out.println("Gauche de la droite");
                return intersectionPoint;
            }
        }

        //If the slopes are not equal, we compute the intersection between the two lines
        else {
           // System.out.println("debug "+line[0]+" "+line[1]+" "+line[2]);
           // System.out.println("debug "+lineBis[0]+" "+lineBis[1]+" "+lineBis[2]);
            if((line[0]==1 || line[0]==-1)) {

                intersectionPoint[0] = line[2]/(-line[0]);
                intersectionPoint[1] = (intersectionPoint[0] * lineBis[0]) + lineBis[2];
            }
            else if ((lineBis[0]==1 || lineBis[0]==-1)){
                intersectionPoint[0] = lineBis[2]/(-lineBis[0]);
                intersectionPoint[1] = (intersectionPoint[0] * line[0]) + line[2];

            }
            else {
                intersectionPoint[0] = (double) ((lineBis[2] - line[2]) / (line[0] - lineBis[0]));
                intersectionPoint[1] = (intersectionPoint[0] * line[0]) + line[2];
            }
           System.out.println("Point d'intersection"+intersectionPoint[0]+ ";"+intersectionPoint[1]);
            //System.out.println("Intervalle du segment ["+x1+";"+x2+"] ["+y1+";"+y2+"]");
            //if point is inside this segment return the intersection
            if( ((((x1 <= intersectionPoint[0]) && (intersectionPoint[0]<= x2)) || ((x2 <= intersectionPoint[0]) && (intersectionPoint[0]<= x1))) && (((y1 <= intersectionPoint[1]) && (intersectionPoint[1]<= y2)) || ((y2 <= intersectionPoint[1]) && (intersectionPoint[1]<= y1))))
                    && !(intersectionPoint[0] == x1 && intersectionPoint[1]==y1) && !(intersectionPoint[0] == x2 && intersectionPoint[1]==y2) ) {
               // System.out.println("Intersection dans segment");
                //doit pas être this.gauche oou this.poitndroite
                return intersectionPoint;
            }
            else if((line[0]*x2 + line[1]*y2 +line[2]) == 0) {
                System.out.println("FATAL ERROR FAILURE BOUM3");
                //tester this.gauche ou this.droite car egalité si point sur segment
                return null;
            }
            //If the point (x1,y1) from this segment is right of the line, return [+inf,+inf]
            else if (((getSide(line, x2, y2)> 0) || (getSide(line, x1, y1) > 0))) {
                intersectionPoint[0] = Double.POSITIVE_INFINITY;
                intersectionPoint[1] = Double.POSITIVE_INFINITY;
                //System.out.println("Droite de la droite");
                return intersectionPoint;
            }
            //If the point (x1,y1) from this segment is left of the line, return [-inf,-inf]
            else {
               // System.out.println("Gauche de la droite");
                intersectionPoint[0] = Double.NaN;
                intersectionPoint[1] = Double.NaN;
                return intersectionPoint;
            }
        }
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

}
