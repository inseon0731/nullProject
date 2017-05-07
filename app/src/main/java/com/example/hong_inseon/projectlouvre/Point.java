package com.example.hong_inseon.projectlouvre;

/**
 * Created by 박명준 on 2017-02-05.
 */

public class Point {
    private double x;
    private double y;

    public Point(double a, double b) {
        x = a; y = b;
    }

    public double getPx() {
        return x;
    }

    public double getPy() {
        return y;
    }

    public static double getD(Point a, Point b) {
        return Math.sqrt((a.getPx()-b.getPx())*(a.getPx()-b.getPx()) + (a.getPy() - b.getPy())*(a.getPy() - b.getPy()));
    }

    public void setD(double x, double y) {
        this.x = x; this.y = y;
    }
}
