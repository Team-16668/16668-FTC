package org.firstinspires.ftc.teamcode.purePursuit;

import org.firstinspires.ftc.teamcode.OpenCVNecessaryLibraries.Point;

import java.util.ArrayList;

import static java.lang.Math.*;

public class mathFunctions {
    /**
     * Make sure angle is within -180 to 180 degrees
     * @param angle
     * @return
     */
    public static double AngleWrap(double angle) {
       while(angle < -PI) {
           angle += 2 * PI;
       }
       while(angle > PI) {
           angle -= 2 * PI;
       }
       return angle;
    }

    /**
     *
     * @param circleCenter
     * @param radius
     * @param linePoint1
     * @param linePoint2
     * @return
     */
    public static ArrayList<Point> lineCircleIntersection(Point circleCenter, double radius,
                                                          Point linePoint1, Point linePoint2) {
        if(Math.abs(linePoint1.y-linePoint2.y) < 0.003) {
            linePoint1.y = linePoint2.y + 0.003;
        }
        if(Math.abs(linePoint1.x-linePoint2.x) < 0.003) {
            linePoint1.x = linePoint2.x = 0.003;
        }

        double m1 = (linePoint2.y-linePoint1.y)/(linePoint2.x-linePoint1.x);
        double quadraticA = 1.0 + pow(m1, 2);

        double x1 = linePoint1.x - circleCenter.x;
        double y1 = linePoint1.y - circleCenter.y;

        double quadraticB = (2.0 * m1 * y1) - (2.0 * pow(m1, 2) * x1);

        double quadraticC = (pow(m1, 2) * pow(x1,2)) - (2.0*y1*m1*x1) + pow(y1,2) -pow(radius, 2);

        ArrayList<Point> allPoints = new ArrayList<>();

        try{
            double xRoot1 = (-quadraticB + sqrt(pow(quadraticB, 2) - (4.0*quadraticA * quadraticC)))/(2.0*quadraticA);

            double yRoot1 = m1 * (xRoot1 -x1) + y1;

            //put back the offset
            xRoot1 += circleCenter.x;
            yRoot1 += circleCenter.y;

            double minX = linePoint1.x < linePoint2.x ? linePoint1.x : linePoint2.x;
            double maxX = linePoint1.x > linePoint2.x ? linePoint1.x : linePoint2.x;

            if(xRoot1 > minX && xRoot1 < maxX) {
                allPoints.add(new Point(xRoot1, yRoot1));
            }

            double xRoot2 = (-quadraticB - sqrt(pow(quadraticB, 2) - (4.0*quadraticA * quadraticC)))/(2.0*quadraticA);
            double yRoot2 = m1 * (xRoot2 - x1) + y1;


            xRoot2 += circleCenter.x;
            yRoot2 += circleCenter.y;

            if(xRoot2 > minX && xRoot2 < maxX) {
                allPoints.add(new Point(xRoot2, yRoot2));
            }

        }catch(Exception e) {

        }
        return allPoints;

    }

    public static double scaleTo(double value, double from, double to) {

        double factor = value/from;
        double newValue = factor*to;
        return newValue;
    }
    public static boolean isWithin(double value, double min, double max) {
        if(value > max) {
            return false;
        } else if (value < min) {
            return false;
        } else {
            return true;
        }
    }

    public static double interpretAngle(double Orientation) {
        if(isWithin(Orientation, -180, 0)) {
            return Math.abs(Orientation);
        } else if (isWithin(Orientation, -360, -181)) {
            double newOrientation = -1 *(Orientation +360);
            return newOrientation;
        } else {
            return Orientation;
        }
    }



}
