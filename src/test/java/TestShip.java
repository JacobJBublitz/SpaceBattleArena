import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;

/**
 * This ship is purely for example.
 * Right now, this test ship will thrust to gain speed and maintain that constant speed.
 */
public class TestShip extends BasicSpaceship {

    //Thrust incrementer
    private int speedIncrementCounter = 0;


    //<editor-fold desc="Experimental test variables">

    //elapsed time calculations
    private long startTime = 0;
    private long endTime = 0;
    private boolean done = false;

    //Origin (top-left of window)
    private Point origin = new Point(0, 0);


    //Angular motion variables
    private int angularDisplacement = 0;

    //</editor-fold>


    //<editor-fold desc="Ship spawning variables">

    //Ship ID for multiple spawning ships
    private static int id = 0;

    //Ship speedIncrementCounter for amount of test ships spawned.
    private static int numberOfShips = 3;

    //</editor-fold>


    public static void main(String[] args) {
        for (int i = 0; i < numberOfShips; i++) {
            new Thread(() -> TextClient.run("10.51.4.70", new TestShip())).start();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //<editor-fold desc="Main ship operations">

    @Override
    public RegistrationData registerShip(int i, int i1, int i2) {
        return new RegistrationData("PUTIN'S SIDEKICKS #" + ++id, new Color(1.0f, 0.0f, 0.0f), 3);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
        ObjectStatus ship = basicEnvironment.getShipStatus();

        //Angular displacement vs time test
        //return collectAngularMotionData(basicEnvironment);

        //Movement handling
        return moveToConstantSpeed(basicEnvironment, 5);

    }

    //</editor-fold>


    //<editor-fold desc="Experimental ship commands">

    /**
     * Makes the test ship thurst a given amount of times.
     * @param basicEnvironment The basic environment.
     * @param thrustIterationLimit The amount of times to thrust.
     * @return A new thurst command if the thrustIterationLimit hasn't been met. Otherwise, returns a new Idle command.
     */
    private ShipCommand moveToConstantSpeed(BasicEnvironment basicEnvironment, int thrustIterationLimit) {
        ObjectStatus ship = basicEnvironment.getShipStatus();

        if (speedIncrementCounter != thrustIterationLimit) {
            speedIncrementCounter++;
            return new ThrustCommand('B', 1, 1);
        } else {
            return new RotateCommand(5);
        }
    }

    //</editor-fold>


    //<editor-fold desc="Information logging">

    private void logInfo(BasicEnvironment basicEnvironment, RadarResults results) {
        System.out.println("Our position is currently" + basicEnvironment.getShipStatus().getPosition());
        System.out.println();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private ShipCommand collectAngularMotionData(BasicEnvironment basicEnvironment) {
        ObjectStatus ship = basicEnvironment.getShipStatus();

        if (ship.getPosition().getAngleTo(origin) - ship.getOrientation() != 0) {
            angularDisplacement = ship.getPosition().getAngleTo(origin) - ship.getOrientation();
            System.out.println("Angular displacement is " + angularDisplacement);
            startTime = System.currentTimeMillis();
            return new RotateCommand(angularDisplacement);
        } else {
            if (!done) {
                done = true;
                endTime = System.currentTimeMillis();
                System.out.println("Elapsed time was : " + (endTime - startTime));
                return new IdleCommand(1);
            } else {
                return new IdleCommand(1);
            }
        }

    }
    //</editor-fold>


}
