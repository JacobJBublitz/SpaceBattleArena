package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;
import java.util.List;

public class Ship extends BasicSpaceship {

    private static final int MIN_ANGLE_BUFFER = 10;
    private static final int MAX_ANGLE_BUFFER = 10;

    private List<ObjectStatus> nearbyEnemyShips;
    private List<ObjectStatus> nearbyAsteroids;
    private List<ObjectStatus> nearbyPlanets;

    private Point origin;

	public static void main(String[] args) {
		TextClient.run("localhost", new Ship());
	}

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
	    origin = new Point(worldWidth/2, worldHeight/2);
	    //System.out.println(worldWidth); = 800
	    //System.out.println(worldHeight); = 600
		return new RegistrationData("The normandy", new Color(0.3f, 0.1f, 0.5f), 10);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
		//Start by using the radar to collect data on objects around us
        ObjectStatus ourShip = basicEnvironment.getShipStatus();
        RadarResults radarResults = basicEnvironment.getRadar();

        if(radarResults == null) {
            return new RadarCommand(4);
        }
        else {
            //Report to the user about the objects around the ship's vicinity
            //logInformation(radarResults);
            testInformation(ourShip);

            //Filter the radar entries into organized sets
            nearbyEnemyShips = radarResults.getByType("Ship");
            nearbyAsteroids = radarResults.getByType("Asteroid");
            nearbyPlanets = radarResults.getByType("Planet");

            return new ThrustCommand('B', 0.5, 1);
            //return new RotateCommand(5);
        }
	}

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
     * Checks if our ship is within the optimal angle to fire.
     * @param enemyShip The enemy ship to be attacked.
     * @param ship Our environment to access our ship's orientation.
     * @return True if our ship is within the optimal angle. False if it is out of range.
     */
    public boolean isFacingEnemy(ObjectStatus enemyShip, ObjectStatus ship){
        //Need to represent the front of a ship as a vector?
        return false;
    }

    /**
     * Gets the angle of the object relative to the origin.
     * @param someObject The object to identify.
     * @return The object's angle in degrees.
     */
    private double getAngle(ObjectStatus someObject) {

        return  someObject.getPosition().getAngleTo(origin);
    }

    /**
     * Gets the position of the object relative to the origin.
     * @param someObject The object to identify.
     * @return The object's position.
     */
    private Point getPosition(ObjectStatus someObject) {

        return someObject.getPosition();
    }

    /**
     * Gets the distance between two objects.
     * @param object1 Some object to get the position of.
     * @param object2 Some other object to get the position of.
     * @return Returns the distance between the two objects.
     */
    private double distanceBetween(ObjectStatus object1, ObjectStatus object2) {
        return object1.getPosition().getDistanceTo(object2.getPosition());
    }

    /**
     * Return's the closest object within the ship's vicinity.
     * @param ship Our ship.
     * @param radarEntries The radar results.
     * @return Returns the closest object to our ship.
     */
    private ObjectStatus getClosestObject(ObjectStatus ship, RadarResults radarEntries) {

        if(radarEntries.size() == 0) {
            return null;
        }

        ObjectStatus closestObject = radarEntries.get(0);

        for (ObjectStatus currentObject : radarEntries) {
            if(distanceBetween(ship,currentObject) < distanceBetween(ship,closestObject)) {
                closestObject = currentObject;
            }
        }

        return closestObject;
    }

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
    private void testInformation(ObjectStatus entry) {
        System.out.println(getAngle(entry));
    }
}
