package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.util.List;

/**
 * Created by nerdares on 5/16/2017.
 * This is the class used for death match.
 */
public class DeathmatchCommand {

    public static final double TORPEDO_SPEED = 250;
    public static final double ANGLE_TOLERANCE = 5;
    public static final double ANGULAR_VELOCITY = 114;

    //<editor-fold desc="Ship commands">

    /**
     * Command that should be called when the game mode is set to death match. This command finds the closest ship
     * and shoots it.
     * @param basicEnvironment The basic environment of the game.
     * @param simpleAttack True for a simple, "shoot target" command. False for "torpedo prediction".
     * @return Returns a new rotate/fire torpedo command if the target is within valid range. Else, returns new idle command.
     */
    public static ShipCommand attackShip(BasicEnvironment basicEnvironment, boolean simpleAttack) {
        ObjectStatus ship = basicEnvironment.getShipStatus();
        RadarResults radarResults = basicEnvironment.getRadar();

        if(radarResults == null) {
            return new RadarCommand(5);
        } else {
            if(radarResults.getByType("Ship").size() > 0) {
                ObjectStatus closestEnemy = getClosestObject(ship, radarResults.getByType("Ship"));
                ShipCommand attackCommand = (simpleAttack)  ? easyAttackShip(ship, closestEnemy) : advancedAttackShip(ship, closestEnemy);
                return (attackCommand != null) ? attackCommand : new IdleCommand(0.1);

            }
        }
        return new IdleCommand(0.1);
    }

    /**
     * Operates the easy attack procedure for targeting enemy ships.
     * @param ship Our ship.
     * @param target Enemy ship.
     * @return
     */
    private static ShipCommand easyAttackShip(ObjectStatus ship, ObjectStatus target) {
        Point futurePos = easyFuturePosition(ship, target);

        int relativeAngle = ship.getPosition().getAngleTo(futurePos) - ship.getOrientation();

        if (Math.abs(relativeAngle) - ANGLE_TOLERANCE < 0) {
            return new FireTorpedoCommand('F');
        } else {
            return new RotateCommand(relativeAngle);
        }
    }

    /**
     * Operates advanced bullet prediction procedure for targeting enemy ships.
     * @param ship Our ship.
     * @param target Enemy ship.
     * @return
     */
    private static ShipCommand advancedAttackShip(ObjectStatus ship, ObjectStatus target) {
        Point interceptPoint = advancedFinalPosition(ship, target);

        System.out.printf("Target location %s \t Interception %s\n ", target.getPosition(), interceptPoint);


        double angleDifference = ship.getPosition().getAngleTo(interceptPoint) - ship.getOrientation();

        if (Math.abs(angleDifference) - ANGLE_TOLERANCE < 0) {
            return new FireTorpedoCommand('F');
        } else {
            return new RotateCommand((int)Math.round(angleDifference));
        }
    }



    //</editor-fold>


    //<editor-fold desc="Interception calculation methods">

    private static Point easyFuturePosition(ObjectStatus ship, ObjectStatus target) {

        System.out.println("Target speed is " + target.getSpeed());
        double futureX = target.getPosition().getX() + target.getSpeed() * Math.cos(Math.toRadians(target.getMovementDirection()));
        double futureY = target.getPosition().getY() - target.getSpeed() * Math.sin(Math.toRadians(target.getMovementDirection()));

        return new Point(futureX, futureY);
    }

    private static Point advancedFinalPosition(ObjectStatus ship, ObjectStatus target) {
        double translationalTime = calculateTranslationalTime(ship, target);
        //Target's velocity
        Vector2D targetVelocity = new Vector2D(target.getSpeed() * Math.cos(Math.toRadians(target.getMovementDirection())),
                target.getSpeed() * Math.sin(Math.toRadians(target.getMovementDirection())));

        //Get the final position such that we can rotate instantly
        Point almostFinalPosition = Vector2D.add(new Vector2D(target.getPosition()), Vector2D.scale(targetVelocity, translationalTime)).toPoint();
        double angularDisplacement = ship.getPosition().getAngleTo(almostFinalPosition) - ship.getOrientation();
        double angularTime = angularDisplacement / ANGULAR_VELOCITY;
        double finalTime = translationalTime + angularTime;

        //Now return the final position with account for angular motion
        return Vector2D.add(new Vector2D(target.getPosition()), Vector2D.scale(targetVelocity, finalTime)).toPoint();

    }

    private static double calculateTranslationalTime(ObjectStatus ship, ObjectStatus target) {
        Vector2D targetInitialPosition = new Vector2D(target.getPosition());

        Vector2D targetVelocity = new Vector2D(target.getSpeed() * Math.cos(Math.toRadians(target.getMovementDirection())),
                target.getSpeed() * Math.sin(Math.toRadians(target.getMovementDirection())));

        //Quadratic formula process :

        //Equivalent to the dot product of targetVelocity amongst iteself (then squared),  minus
        // the dot product of the torpedo's speed amongst iteself (then squared).
        double aValue = (Math.pow(target.getSpeed(), 2) - Math.pow(TORPEDO_SPEED, 2));

        //Self explanatory
        double bValue = 2*Vector2D.dotProduct(targetInitialPosition, targetVelocity);

        //Dot product of itself is equivalent
        double cValue = Math.pow(targetInitialPosition.magnitude(), 2);


        double discriminant = Math.sqrt(Math.pow(bValue, 2) - (4*aValue*cValue));
        return (-(bValue + discriminant) / (2*aValue));
    }

    //</editor-fold>


    //<editor-fold desc="Navigation assistance">

    /**
     * Return's the closest object within the ship's vicinity.
     * @param ship Our ship.
     * @param enemyShipList A list of radar results specifically of type "Ship".
     * @return Returns the closest object to our ship.
     */
    private static ObjectStatus getClosestObject(ObjectStatus ship, List<ObjectStatus> enemyShipList) {

        if(enemyShipList.size() == 0) {
            return null;
        }

        ObjectStatus lastClosestObject = enemyShipList.get(0);

        for (ObjectStatus currentObject : enemyShipList) {
            double lastDistance = ship.getPosition().getDistanceTo(lastClosestObject.getPosition());
            double currentObjectsDistance = ship.getPosition().getDistanceTo(currentObject.getPosition());

            if(lastDistance < currentObjectsDistance) {
                lastClosestObject = currentObject;
            }
        }

        return lastClosestObject;
    }

    //</editor-fold>


}
