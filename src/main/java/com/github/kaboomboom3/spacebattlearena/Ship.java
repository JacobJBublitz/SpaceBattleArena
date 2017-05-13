package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;
import java.util.List;

/**
 * The actual ship that we will be flying
 * TODO: MAJOR PRIORITY : Need to find out why radarResults returns 0 or null for every enemy ship data other than position!
 */
public class Ship extends BasicSpaceship {


    //<editor-fold desc="Ship variables">

    private static final int MIN_ANGLE_BUFFER = -7;
    private static final int MAX_ANGLE_BUFFER = 7;
    private static final int ROTATION_BIAS = 0;
    private static final int OPTIMAL_FIRING_RANGE = 300;

    private List<ObjectStatus> nearbyEnemyShips;
    private List<ObjectStatus> nearbyAsteroids;
    private List<ObjectStatus> nearbyPlanets;

    //</editor-fold>

	public static void main(String[] args) {
		TextClient.run("localhost", new Ship());
	}


    //<editor-fold desc="Main ship operations">

    @Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		return new RegistrationData("The normandy", new Color(0.3f, 0.1f, 0.5f), 10);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
		{
			ShipCommand repairCommand = repairShip(basicEnvironment.getShipStatus());
			if (repairCommand != null) {
				return repairCommand;
			}
		}

		//Start by using the radar to collect data on objects around us
        ObjectStatus ourShip = basicEnvironment.getShipStatus();
        RadarResults radarResults = basicEnvironment.getRadar();

        if(radarResults == null) {
            return new RadarCommand(4);
        }
        else {

            //Filter the radar entries into organized sets
            nearbyEnemyShips = radarResults.getByType("Ship");
            nearbyAsteroids = radarResults.getByType("Asteroid");
            nearbyPlanets = radarResults.getByType("Planet");

            if(nearbyEnemyShips.size() != 0 ) {

                ObjectStatus closestEnemy = getClosestObject(ourShip, nearbyEnemyShips);
                OtherAttackShipCommand.findInterceptVector(ourShip, closestEnemy);
                System.out.println(closestEnemy);
                System.out.println("----------------------------END----------------------------");


                //<editor-fold desc="Cannot use until you find out why speed and direction is 0 for enemy">

                //ShipCommand attackCommand = AttackShipCommand.attackShip(ourShip, closestEnemy);
                //ShipCommand attackCommand = OtherAttackShipCommand.attackShip(ourShip, closestEnemy);
                //if (attackCommand != null) {
                //	return attackCommand;
                //}

                //</editor-fold>
            }
        }

        return new IdleCommand(0.1);
	}

    //</editor-fold>


    //<editor-fold desc="Ship commands">

    /**
     * Repair's the ship if the health is less than 10%.
     * @param ship The ship.
     * @return Returns a repair command.
     */
	public ShipCommand repairShip(ObjectStatus ship) {
	    if(ship.getHealth() < 10) {
            System.out.println("Repairing ship...");
            return new RepairCommand(50);
        }
        else if(ship.getHealth() < 50) {
            System.out.println("WARNING: SHIP HEALTH IS BELOW 50%");
            return null;
        }
        else {
            return  null;
        }
    }

    /**
     * Forces our ship to rotate towards a point
     * @param point A point to rotate towards.
     * @return Returns a new Rotate Command.
     */
    private ShipCommand rotateTowards(ObjectStatus ourShip, Point point) {
        System.out.println("Rotating...");
        return new RotateCommand(getAngleBetween(ourShip, point) + ROTATION_BIAS);
    }

    //</editor-fold>


    //<editor-fold desc="Navigation assistance">
    
    /**
     * Get's the angle between the head of our ship and a point
     * @param ourShip Our ship.
     * @param point A point to rotate towards.
     * @return Returns the angle between our ship and the point.
     */
    private int getAngleBetween(ObjectStatus ourShip, Point point) {
        return ourShip.getPosition().getAngleTo(point) - ourShip.getOrientation();
    }

    /**
     * Checks if our ship is within the optimal angle to fire.
     * @param ourShip The enemy ship to be attacked.
     * @param enemyShipPoint Point of the ourShip to access our ship's orientation.
     * @return True if our ship is within the optimal angle. False if it is out of range.
     */
    public boolean isFacingEnemy(ObjectStatus ourShip, Point enemyShipPoint) {
        int angleBetween = getAngleBetween(ourShip, enemyShipPoint);
        return  MIN_ANGLE_BUFFER <= angleBetween && angleBetween <= MAX_ANGLE_BUFFER;
    }

    /**
     * Gets the distance between two objects.
     * @param ourShip Some object to get the position of.
     * @param otherObject Some other object to get the position of.
     * @return Returns the distance between the two objects.
     */
    private double getDistanceBetween(ObjectStatus ourShip, Point otherObject) {
        return ourShip.getPosition().getDistanceTo(otherObject);
    }

    /**
     * Return's the closest object within the ship's vicinity.
     * @param ship Our ship.
     * @param radarEntries The radar results.
     * @return Returns the closest object to our ship.
     */
    private ObjectStatus getClosestObject(ObjectStatus ship, List<ObjectStatus> enemyShipList) {

        if(enemyShipList.size() == 0) {
            return null;
        }

        ObjectStatus closestObject = enemyShipList.get(0);

        for (ObjectStatus currentObject : enemyShipList) {
            double lastDistance = getDistanceBetween(ship, closestObject.getPosition());
            double currentObjectsDistance = getDistanceBetween(ship,currentObject.getPosition());

            if(lastDistance < currentObjectsDistance) {
                closestObject = currentObject;
            }
        }

        return closestObject;
    }

    //</editor-fold>


    //<editor-fold desc="Information logging">

    /**
     * Logs information on all objects within our ship's vicinity.
     * @param radarEntries The radar entries collected
     */
    private void logInformation(List<ObjectStatus> radarEntries) {
        for(ObjectStatus entry : radarEntries) {
            System.out.println(String.format("Name: %s \t Type of object: %s", entry.getName(), entry.getType()));
        }
        System.out.println("-----------------------END LOG-------------------------");
    }

    /**
     * Reports information about a single, certain object.
     * Used for testing.
     * @param entry Object to observe.
     */
    private void testInformation(ObjectStatus ourShip, Point point) {
        double angle = ourShip.getPosition().getAngleTo(point);
    }

    //</editor-fold>

}
