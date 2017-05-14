package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;

/**
 * Created by nerdares on 5/14/2017.
 */
public class OtherAttackShipCommand {

    public static final double TORPEDO_SPEED = 250;
    public static final double ANGLE_TOLERANCE = 5;

    public static Point finalPosition(ObjectStatus ship, ObjectStatus target) {

        //<editor-fold desc="Algorithm explanation">
        /**
         * Algorithm explanation :
         * In order to calulate the point of interception to get the target, we need to split
         * this problem into vectors. To do this :
         * 1) We must first do everything relative to the ship.
         *
         * 2) We need to find a displacement vector from the ship to the target.
         *
         * 3) Then we need to find a vector which will be the target's displacement from the targets final position to
         * the target's initial position. We will need to use the velocity of the target for this.
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

        //Get the displacement vector from the ship to the target.
        Vector2D displacement = new Vector2D(ship.getPosition(), target.getPosition());

        //Get the velocity vector of the target. This will be scaled with time to represent the displacement vector
        //between the target's initial position and the target's final position
        Vector2D targetVelocity = new Vector2D(target.getSpeed() * Math.cos(target.getMovementDirection()),
                target.getSpeed() * Math.sin(target.getMovementDirection()));


        //<editor-fold desc="End result notes">
        /**
         * End result will be
         * World space translation of :
         *      Vector sum of :
         *          Displacement of ship to target + Displacement of ship's initial position to final position.
         */
        //</editor-fold>
        return  null;
    }


}
