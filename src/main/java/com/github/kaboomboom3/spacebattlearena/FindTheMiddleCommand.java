package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.util.Vector;

/**
 * Created by Kaboomboom3 on 5/16/2017.
 * This is the class used for find the middle.
 */
public class FindTheMiddleCommand {

    private static double BREAKING_DISTANCE = 10;

    public static ShipCommand getNextCommand(BasicEnvironment environment) {
        ObjectStatus ourShip = environment.getShipStatus();

        Point middlePos = new Point(Ship.getWorldWidth() / 2.0, Ship.getWorldHeight() / 2.0);
        int angularDisplacement = ourShip.getPosition().getAngleTo(middlePos) - ourShip.getOrientation();

        if (angularDisplacement != 0 /*&& ourShip.getPosition().getDistanceTo(middlePos) <= BREAKING_DISTANCE*/) {
            return new RotateCommand(angularDisplacement);
        } else {
            return moveToPoint(ourShip, middlePos);
        }
    }

    private static ShipCommand moveToPoint(ObjectStatus ship, Point destination) {
//        Vector2D velocity = new Vector2D(ship.getSpeed() * Math.cos(Math.toRadians(ship.getOrientation() - ship.getMovementDirection())),
//                ship.getSpeed() * Math.sin(Math.toRadians(ship.getOrientation() - ship.getMovementDirection())));

        Vector2D velocity = new Vector2D(ship.getSpeed() * Math.cos(Math.toRadians(ship.getMovementDirection())),
                -ship.getSpeed() * Math.sin(Math.toRadians(ship.getMovementDirection())));

        System.out.println("Velocity is " + velocity);

        if(velocity.dX > 20.0) {
            return new ThrustCommand('L', 1, 0.5, false);
        } else if (velocity.dX < -20.0) {
            return new ThrustCommand('R', 1, 0.5, false);
        }

        if(ship.getPosition().getDistanceTo(destination) > BREAKING_DISTANCE) {
            return new ThrustCommand('B', 1, 1, false);
        } else {
            if (ship.getSpeed() > 40) {
                return new ThrustCommand('F', 1, 1, false);
            }
            System.out.println("Breaking");
            return new BrakeCommand(0);
        }
    }
}
