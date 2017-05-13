package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;
/**
 * Created by nerdares on 5/12/2017.
 */
public class OtherAttackShipCommand {

    public static final double TORPEDO_SPEED = 250;
    public static final int ANGLE_TOLERANCE = 5;

    public static Point getFuturePoint(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point relativePosition = addPoints(ourShip.getPosition(), enemyShip.getPosition(), "Subtract");

        double aValue = Math.pow(enemyShip.getSpeed(), 2);
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

    public static double getMagnitude(Point p1) {
        return Math.sqrt(Math.pow(p1.getX(), 2) + Math.pow(p1.getY(), 2));
    }

    public static ShipCommand attackShip(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point finalPosition = getFuturePoint(ourShip, enemyShip);

        int relativeAngle = ourShip.getPosition().getAngleTo(finalPosition) - ourShip.getOrientation();

        System.out.println("Our position is : " + ourShip.getPosition() +
                "while the enemies future position shall be : " + finalPosition);

        if (Math.abs(relativeAngle) - ANGLE_TOLERANCE < 0) {
            return new FireTorpedoCommand('F');
        } else {
            return new RotateCommand(relativeAngle % 90);
        }
    }

}
