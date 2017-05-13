package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


/**
 * Created by nerdares on 5/12/2017.
 * An attack command that should correctly determine a good position to shoot the enemy ship.
 */
public class OtherAttackShipCommand {

    public static final double TORPEDO_SPEED = 250;
    public static final double ENEMY_SHIP_TEST_SPEED = 34; //34.3
    public static final double ENEMY_SHIP_TEST_ANGLE = 266;
    public static final int ANGLE_TOLERANCE = 10;

    public static Point getFuturePosition(ObjectStatus ourShip, ObjectStatus enemyShip) {

//        Vector2D distanceApartVector = Vector2D.subtract(ourShip.getPosition(), enemyShip.getPosition());
//
//        Vector2D enemyShipVelocityVector = new Vector2D(ENEMY_SHIP_TEST_SPEED * Math.cos(ENEMY_SHIP_TEST_ANGLE),
//                ENEMY_SHIP_TEST_SPEED * Math.sin(ENEMY_SHIP_TEST_ANGLE));
//
//        double theta = Vector2D.angleBetween(distanceApartVector, enemyShipVelocityVector);
//
//        double aValue = Math.pow(ENEMY_SHIP_TEST_SPEED, 2) - Math.pow(TORPEDO_SPEED, 2);
//        double bValue = -2 * Math.cos(theta) * distanceApartVector.getMagnitude() * ENEMY_SHIP_TEST_SPEED;
//        double cValue = Math.pow(distanceApartVector.getMagnitude(), 2);
//
//        double discriminant = Math.sqrt(Math.pow(bValue, 2) - (4 * aValue * cValue));
//        double time = -(bValue + discriminant) / (2*aValue);
//        return Vector2D.add(enemyShip.getPosition(), Vector2D.scalarMultiply(enemyShipVelocityVector, time).toPoint()).toPoint();

        throw new NotImplementedException();

    }

    public static Vector2D findInterceptVector(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Vector2D dirToTarget = Vector2D.normalize(new Vector2D(ourShip.getPosition(), enemyShip.getPosition()));
        System.out.println(dirToTarget.toString());
        return null;
    }

    /**
     * Attacks the enemy ship by shooting at a predicted position.
     * @param ourShip Our ship.
     * @param enemyShip The enemy ship to target.
     * @return Returns an Attack Command if our ship is in a valid rotation and position. Otherwise, returns a
     * rotate command to fix angular position.
     */
    public static ShipCommand attackShip(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point finalPosition = getFuturePosition(ourShip, enemyShip);


        System.out.println("Enemy coordinates : " + enemyShip.getPosition() + " Final coordinates : " + finalPosition);

        int relativeAngle = ourShip.getPosition().getAngleTo(finalPosition) - ourShip.getOrientation();

        //LogMessage(ourShip,enemyShip,relativeAngle,finalPosition);

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
