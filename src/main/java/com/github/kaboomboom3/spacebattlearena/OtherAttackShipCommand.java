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

    public static Vector2D findInterceptVector(ObjectStatus ourShip, ObjectStatus enemyShip) {


        //<editor-fold desc="Initial values">

        Vector2D shotOrigin = new Vector2D(new Point(0,0), ourShip.getPosition());
        Vector2D targetOrigin = new Vector2D(new Point (0,0), enemyShip.getPosition());

        Vector2D targetVel = new Vector2D(enemyShip.getSpeed() * Math.cos(enemyShip.getMovementDirection()),
                enemyShip.getSpeed() * Math.cos(enemyShip.getMovementDirection()));

        double targetRadius = enemyShip.getHitRadius();

        //</editor-fold>


        Vector2D dirToTarget = Vector2D.toUnitVector(new Vector2D(ourShip.getPosition(), enemyShip.getPosition()));

        Vector2D targetVelOrth = Vector2D.scale(dirToTarget, Vector2D.dotProduct(targetVel, dirToTarget));
        Vector2D targetVelTan = Vector2D.subtract(targetVel, targetVelOrth);

        Vector2D shotVelTan = Vector2D.copy(targetVelTan);

        double shotVelSpeed = shotVelTan.magnitude();


        if(shotVelSpeed > TORPEDO_SPEED) {
            return Vector2D.scale(Vector2D.toUnitVector(targetVel), TORPEDO_SPEED);
        }
        else {
            double shotSpeedOrth = Math.sqrt(Math.pow(TORPEDO_SPEED, 2)) - Math.pow(shotVelSpeed, 2);
            Vector2D shotVelOrth = Vector2D.scale(dirToTarget, shotSpeedOrth);
            return Vector2D.add(shotVelOrth, shotVelTan);
        }

    }

    /**
     * Attacks the enemy ship by shooting at a predicted position.
     * @param ourShip Our ship.
     * @param enemyShip The enemy ship to target.
     * @return Returns an Attack Command if our ship is in a valid rotation and position. Otherwise, returns a
     * rotate command to fix angular position.
     */
    public static ShipCommand attackShip(ObjectStatus ourShip, ObjectStatus enemyShip) {
        Point finalPosition = enemyShip.getPosition();

        int relativeAngle = ourShip.getPosition().getAngleTo(finalPosition) - ourShip.getOrientation();

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
