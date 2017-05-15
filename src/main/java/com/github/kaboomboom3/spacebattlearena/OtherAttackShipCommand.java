package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

/**
 * Created by nerdares on 5/14/2017.
 */
public class OtherAttackShipCommand {


    //<editor-fold desc="Algorithm explanation">
    /**
     * Algorithm explanation :
     * In order to calculate the point of interception to get the target, we need to split
     * this problem into vectors. To do this :
     * 1) We must first do everything relative to the ship.
     *
     * 2) We need to find a displacement vector from the ship to the target.
     *
     * 3) Then we need to find a vector which will be the target's displacement from the targets initial position to
     * the target's final position. We will need to use the velocity of the target for this.
     *
     * 4) Then, we need to calculate the resultant vector which will be the
     * the ships displacement + the target's displacement.
     *
     * 5) Then we will translate that resultant vector into world space and make our ship shoot to that point.
     *
     * The end result is to return a new point such that we take the relative x component and y component of the
     * resultant vector into world space
     *
     * HOW TO SOLVE:
     *
     * The displacement vector is easy to calculate. The target's velocity vector is the problem. To calculate final
     * position, we need to model it with simple physics equations. That is :
     * Final position = Initial position + Velocity * time
     * Notice this is a vector representation of the equation, which means this equation takes into account of both
     * components. We use the Vector2D class to help us with that.
     *
     * Essentially, we want to do the initial position (as a vector) and add it to the target's velocity - that
     * velocity vector must be scaled with time!
     *
     *
     */
    //</editor-fold>


    public static final double TORPEDO_SPEED = 250;
    public static final double ANGLE_TOLERANCE = 5;
    public static final Point RELATIVE_ORIGIN = new Point(0, 600);

    public static Point finalPosition(ObjectStatus ship, ObjectStatus target) {

        //FIX TO RELATIVE ORIGIN
        //Get the displacement vector from the ship to the target. WARNING : the y coordinate axis is different!
        //We will deal with it by ignoring it for now, but consider the relative origin.
        Vector2D displacement = new Vector2D(ship.getPosition(), target.getPosition());

        Vector2D targetVelocity = new Vector2D(target.getSpeed() * Math.cos(target.getMovementDirection()),
                target.getSpeed() * Math.cos(target.getMovementDirection()));

        //FIX TO RELATIVE ORIGIN
        Vector2D targetDisplacement = Vector2D.add(new Vector2D(target.getPosition()),
                Vector2D.scale(targetVelocity, calculateTime(ship, target)));

        //<editor-fold desc="End result notes">
        /**
         * End result will be
         * World space translation of :
         *      Vector sum of :
         *          Displacement of ship to target + Displacement of ship's initial position to final position.
         */
        //</editor-fold>
        return  Vector2D.add(displacement, targetDisplacement).toPoint();

    }

    public static double calculateTime(ObjectStatus ship, ObjectStatus target) {

        //FIX TO RELATIVE ORIGIN
        Vector2D targetInitalPosition = new Vector2D(new Point(0,0) , target.getPosition());

        Vector2D torpedoVelocity = new Vector2D(TORPEDO_SPEED * Math.cos(ship.getOrientation()),
                TORPEDO_SPEED * Math.sin(ship.getOrientation()));

        Vector2D targetVelocity = new Vector2D(target.getSpeed() * Math.cos(target.getMovementDirection()),
                target.getSpeed() * Math.cos(target.getMovementDirection()));

        Vector2D denominator = Vector2D.subtract(torpedoVelocity, targetVelocity);

        Vector2D flippedDenominator = new Vector2D(1.0 / denominator.dX, 1.0 / denominator.dY);

        return Vector2D.dotProduct(targetInitalPosition, flippedDenominator);

    }

    public ShipCommand attackShip(ObjectStatus ship, ObjectStatus target) {
        Point intercept = finalPosition(ship, target);

        int relativeAngle = ship.getPosition().getAngleTo(intercept) - ship.getOrientation();

        if (Math.abs(relativeAngle) - ANGLE_TOLERANCE < 0) {
            return new FireTorpedoCommand('F');
        } else {
            return new RotateCommand(relativeAngle);
        }
    }
}
