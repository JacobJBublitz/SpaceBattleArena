package com.github.kaboomboom3.spacebattlearena;


import ihs.apcs.spacebattle.Point;

/**
 * Created by nerdares on 5/12/2017.
 * Vector class that represents a two-dimensional vector
 */
public class Vector2D {

    //Vector components
    public double dX;
    public double dY;


    //<editor-fold desc="Constructors">

    /**
     * Default constructor that will create a vector with an X and Y component of 0.
     */
    public Vector2D() {
        dX = dY = 0.0;
    }

    /**
     * Alternate Vector2D constructor that will create a vector based off the specified components.
     *
     * @param xComponent The X component of this vector.
     * @param yComponent The Y component of this vector
     */
    public Vector2D(double xComponent, double yComponent) {
        this.dX = xComponent;
        this.dY = yComponent;
    }

    /**
     * Another constructor that creates a vector using the Head Minus Tail rule of two points.
     *
     * @param tail The tail point.
     * @param head The head point.
     */
    public Vector2D(Point tail, Point head) {
        this.dX = head.getX() - tail.getX();
        this.dY = head.getY() - tail.getY();
    }

    /**
     * Another constructor that converts a point to a vector, assuming that you are creating a vector
     * such that the iniial point is at (0,0).
     *
     * @param p1
     */
    public Vector2D(Point p1) {
        this.dX = p1.getX();
        this.dY = p1.getY();
    }

    //</editor-fold>


    //<editor-fold desc="Instance methods">

    /**
     * Get's the magnitude of this vector
     *
     * @return Returns a single number representation of the magnitude of this vector.
     */
    public double magnitude() {
        return Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
    }

    /**
     * Get the angle that the vector is facing IN RADIANS.
     *
     * @return A number representing the angle that this vector is facing.
     */
    public double getRadianAngle() {
        return Math.atan(dY / dX);
    }

    /**
     * Convert's a Vector2D to a Point, assuming the tail is at (0,0).
     *
     * @return A point from this vector.
     */
    public Point toPoint() {
        return new Point(this.dX, this.dY);
    }

    //</editor-fold>


    //<editor-fold desc="Static methods">

    /**
     * Gets the angle between two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return Returns the angle between the two vectors IN RADIANS.
     */
    public static double angleBetween(Vector2D v1, Vector2D v2) {
        return Math.acos(dotProduct(v1, v2) / (v1.magnitude() * v2.magnitude()));
    }

    /**
     * Gets the dot product between two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return Returns a single number which is the dot product of the two vectors.
     */
    public static double dotProduct(Vector2D v1, Vector2D v2) {
        return (v1.dX * v2.dX) + (v1.dY * v2.dY);
    }

    /**
     * Gets the vector projection of U onto V
     *
     * @param u The U vector (usually NOT IN the subscript for projection notation).
     * @param v The V vector (usually IN the subscript for projection notation).
     * @return Return's a new vector that is the projection of U onto V.
     */
    public static Vector2D projectUonV(Vector2D u, Vector2D v) {
        double num = dotProduct(u, v) / Math.pow(v.magnitude(), 2);
        return new Vector2D(v.dX * num, v.dY * num);
    }

    /**
     * Normalizes a vector.
     *
     * @param v A vector.
     * @return Returns a new vector who's magnitude is equal to 1.
     */
    public static Vector2D toUnitVector(Vector2D v) {
        return new Vector2D(v.dX / v.magnitude(), v.dY / v.magnitude());
    }

    /**
     * Adds two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return A new vector that is the sum of two vectors inputted.
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.dX + v2.dX, v1.dY + v2.dY);
    }

    /**
     * Subtracts two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return A new vector that is the difference of two vectors inputted.
     */
    public static Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.dX - v2.dX, v1.dY - v2.dY);
    }

    /**
     * Scale's a vector by a scalar
     *
     * @param v1     A vector to be scaled.
     * @param scalar The scalar multiplier to scale the vector by.
     * @return Returns a new vector, whos components have been scaled by the scalar inputted.
     */
    public static Vector2D scale(Vector2D v1, double scalar) {
        return new Vector2D(scalar * v1.dX, scalar * v1.dY);
    }

    public static Vector2D copy(Vector2D v) {
        return new Vector2D(v.dX, v.dY);
    }

    //</editor-fold>

    /**
     * Output's a string representation of this vector.
     *
     * @return A string representation in the form of <dX, dY>.
     */
    @Override
    public String toString() {
        return String.format("<%.2f, %.2f>", this.dX, this.dY);
    }

}
