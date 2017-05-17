package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

/**
 * Created by Kaboomboom3 on 5/16/2017.
 * This is the class used for find the middle.
 */
public class FindTheMiddleCommand {

    private static double BREAKING_DISTANCE = 250;

    public static ShipCommand getNextCommand(BasicEnvironment environment) {
        ObjectStatus ourShip = environment.getShipStatus();

        Point middlePos = new Point(Ship.getWorldWidth() / 2.0, Ship.getWorldHeight() / 2.0);

        int angleToMiddle = ourShip.getPosition().getAngleTo(middlePos);
        if (angleToMiddle - ourShip.getOrientation() != 0) {
            return new RotateCommand(angleToMiddle - ourShip.getOrientation());
        } else {
            double distanceToMiddle = ourShip.getPosition().getDistanceTo(middlePos);

            Vector2D velocity = new Vector2D(ourShip.getSpeed() * Math.cos(Math.toRadians(ourShip.getOrientation() - ourShip.getMovementDirection())),
                    ourShip.getSpeed() * Math.sin(Math.toRadians(ourShip.getOrientation() - ourShip.getMovementDirection())));

            System.out.println("Velocity is " + velocity);

            if (velocity.dY > 20.0) {
                return new ThrustCommand('R', 1, 0.5, false);
            } else if (velocity.dY < -20.0) {
                return new ThrustCommand('L', 1, 0.5, false);
            }

            if (distanceToMiddle > BREAKING_DISTANCE) {
                return new ThrustCommand('B', 1, 1, false);
            } else {
                if (ourShip.getSpeed() > 40) {
                    return new ThrustCommand('F', 1, 1, false);
                }
                return new BrakeCommand(0);
            }
        }
    }
}
