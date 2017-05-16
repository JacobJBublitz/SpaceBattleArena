package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.BrakeCommand;
import ihs.apcs.spacebattle.commands.RotateCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;
import ihs.apcs.spacebattle.commands.ThrustCommand;

/**
 * Created by 246673 on 5/16/2017.
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
