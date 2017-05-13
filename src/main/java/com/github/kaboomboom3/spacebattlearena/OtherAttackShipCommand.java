package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

/**
 * Created by nerdares on 5/12/2017.
 * An attack command that should correctly determine a good position to shoot the enemy ship.
 */
public class OtherAttackShipCommand {

    public static final double TORPEDO_SPEED = 250;
    public static final int ANGLE_TOLERANCE = 10;

    /**
     * Get's the future position of the enemy ship using Law of Cosines, Basic physics with final position calculation
     * and quadratics.
     * TODO: Needs to account for acceleration due to nearby planet's gravitational acceleration.
     * @param ourShip Our ship.
     * @param enemyShip The enemy ship to target.
     * @return Returns the predicted final position of the enemy ship.
     */
    public static Point getFuturePoint(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point relativePosition = addPoints(ourShip.getPosition(), enemyShip.getPosition(), "Subtract");

        double aValue = Math.pow(enemyShip.getSpeed(), 2) - Math.pow(TORPEDO_SPEED, 2);
        double bValue = -2 * Math.cos(Math.toRadians(enemyShip.getMovementDirection())) *
                getMagnitude(relativePosition) * enemyShip.getSpeed();
        double cValue = Math.pow(getMagnitude(relativePosition), 2);

        //Check discriminant if necessary
        double delta = Math.sqrt(Math.pow(bValue,2) - (4 * aValue * cValue));
        double time = -(bValue + delta) / (2*aValue);

        double finalX = enemyShip.getPosition().getX() + enemyShip.getSpeed() * Math.cos(Math.toRadians(enemyShip.getMovementDirection()));
        double finalY = enemyShip.getPosition().getX() + enemyShip.getSpeed() * Math.sin(Math.toRadians(enemyShip.getMovementDirection()));

        Point finalPosition = new Point(finalX, finalY);

        System.out.println("Final position is : " + finalPosition);

        return finalPosition;
    }

    /**
     * Adds or subtracts 2 points together (like vectors).
     * @param p1 A point.
     * @param p2 Another point.
     * @param operation Specify operation. "Add" to add, "Subtract" to subtract.
     * @return Returns the sum of the two points.
     */
    public static Point addPoints(Point p1, Point p2, String operation) {
        if(operation.equals("Add")) {
            return new Point(p1.getX() + p2.getX(), p1.getY() + p2.getY());
        }
        else if (operation.equals("Subtract")) {
            return new Point(p1.getX() - p2.getX(), p1.getY() - p2.getY());
        }
        else {
            return null;
        }
    }

    /**
     * Gets the magnitude of a point (like a vector).
     * @param p1 A point.
     * @return A single number representation of the magnitude of p1.
     */
    public static double getMagnitude(Point p1) {
        return Math.sqrt(Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2));
    }

    /**
     * Attacks the enemy ship by shooting at a predicted position.
     * @param ourShip Our ship.
     * @param enemyShip The enemy ship to target.
     * @return Returns an Attack Command if our ship is in a valid rotation and position. Otherwise, returns a
     * rotate command to fix angular position.
     */
    public static ShipCommand attackShip(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point finalPosition = getFuturePoint(ourShip, enemyShip);

        int relativeAngle = ourShip.getPosition().getAngleTo(finalPosition) - ourShip.getOrientation();

        LogMessage(ourShip,enemyShip,relativeAngle,finalPosition);

        if (Math.abs(relativeAngle) - ANGLE_TOLERANCE < 0) {
            return new FireTorpedoCommand('F');
        } else {
            return new RotateCommand(relativeAngle);
        }
    }

    /**
     * Logs necessary information of our ship and the enemy ship.
     * @param ourShip Our ship.
     * @param enemyShip The enemy ship to target.
     * @param relativeAngle The relative angle from our ship (as the origin) to the enemy ship.
     * @param finalPosition The predicted final position of the enemy ship.
     */
    public static void LogMessage(ObjectStatus ourShip, ObjectStatus enemyShip, int relativeAngle, Point finalPosition) {
        System.out.println("Relative angle = " + relativeAngle);
        System.out.println("Our position is : " + ourShip.getPosition());
        System.out.println("Enemy position is : " + enemyShip.getPosition());
        System.out.println("Enemy final position is : " + finalPosition);
        System.out.println("Enemy speed is " + enemyShip.getSpeed());
        System.out.println("----------------------END-----------------------");
    }

}
