package com.github.kaboomboom3.spacebattlearena;


import ihs.apcs.spacebattle.Point;

/**
 * Created by nerdares on 5/12/2017.
 * Vector class that represents a two-dimensional vector
 */
public class Vector2D {

    //Vector components
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getAngle() {
        return Math.atan(y/x);
    }

    public Point toPoint() {
        return new Point(this.x, this.y);
    }

    public static double angleBetween(Vector2D v1, Vector2D v2) {
        return Math.acos(dotProduct(v1, v2) / (v1.getMagnitude() * v2.getMagnitude()));
    }

    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public static Vector2D add(Vector2D v1, Vector2D v2)  {
        return new Vector2D(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2D add(Point p1, Point p2)  {
        return new Vector2D(p1.getX() + p2.getX(), p1.getY() + p2.getY());
    }

    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector2D subtract(Point p1, Point p2)  {
        return new Vector2D(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }

    public static Vector2D scalarMultiply(Vector2D v1, double scalar) {
        return new Vector2D(scalar * v1.x, scalar * v1.y);
    }

}
