package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.RadarResults;
import ihs.apcs.spacebattle.commands.*;

import java.util.List;

/**
 * Created by 246673 on 5/22/2017.
 */
public class BaubleCommand {

    private static final int ANGLE_TOLERANCE = 5;

    public static ShipCommand baubleCommand(BasicEnvironment basicEnvironment) {
        ObjectStatus ship = basicEnvironment.getShipStatus();
        RadarResults radarResults = basicEnvironment.getRadar();

        if (radarResults == null) {
            return new RadarCommand(5);
        }

        List<ObjectStatus> baubleList = radarResults.getByType("Bauble");
        List<ObjectStatus> enemyShips = radarResults.getByType("Ship");

        ObjectStatus closestBauble = getClosestObject(ship, baubleList);
        ObjectStatus closestEnemy = getClosestObject(ship, enemyShips);

        double distanceToBauble = (closestBauble != null) ? ship.getPosition().getDistanceTo(closestBauble.getPosition())
                : Double.MAX_VALUE;

        double distanceToShip = (closestEnemy != null) ? ship.getPosition().getDistanceTo(closestBauble.getPosition())
                : Double.MAX_VALUE;

        if (closestBauble != null && distanceToBauble < distanceToShip) {
            return gotoPoint(ship, closestBauble.getPosition());
        } else if (closestEnemy != null) {
            return DeathmatchCommand.attackShip(basicEnvironment, false);
        } else {
            return new IdleCommand(0.5);
        }

    }

    //<editor-fold desc="Navigation assistance">

    /**
     * Return's the closest object within the ship's vicinity.
     *
     * @param ship         Our ship.
     * @param otherObjects A list of radar results specifically of type "Ship".
     * @return Returns the closest object to our ship.
     */
    private static ObjectStatus getClosestObject(ObjectStatus ship, List<ObjectStatus> otherObjects) {

        if (otherObjects.size() == 0) {
            return null;
        }

        ObjectStatus lastClosestObject = otherObjects.get(0);

        for (ObjectStatus currentObject : otherObjects) {
            double lastDistance = ship.getPosition().getDistanceTo(lastClosestObject.getPosition());
            double currentObjectsDistance = ship.getPosition().getDistanceTo(currentObject.getPosition());

            if (currentObjectsDistance < lastDistance) {
                lastClosestObject = currentObject;
            }
        }

        return lastClosestObject;
    }

    /**
     * Makes ship go to point.
     * @param ship Our ship.
     * @param point Point to go to.
     * @return Commands.
     */
    private static ShipCommand gotoPoint(ObjectStatus ship, Point point) {
        if (ship.getPosition().getDistanceTo(point) <= 5) {
            return null;
        }

        int angleDifference = ship.getPosition().getAngleTo(point) - ship.getOrientation();

        if (Math.abs(angleDifference) - ANGLE_TOLERANCE < 0) {
            return new ThrustCommand('B', 1, 0.5);
        } else {
            return new RotateCommand((int) Math.floor(angleDifference));
        }
    }

    //</editor-fold>

}
