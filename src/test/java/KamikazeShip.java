import com.github.kaboomboom3.spacebattlearena.*;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;
import java.util.List;

/**
 * Created by nerdares on 5/23/2017.
 */
public class KamikazeShip extends BasicSpaceship{


    //<editor-fold desc="World properties">

    private static int worldWidth = 0;
    private static int worldHeight = 0;

    //</editor-fold>

    private static Gamemodes gamemodes = Gamemodes.BAUBLE;

    private static int speedIncrementCounter;

    private static int ANGLE_TOLERANCE = 5;

    private final int id;



    public KamikazeShip(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final int id = i;
            new Thread(() -> TextClient.run("10.51.4.70", new KamikazeShip(id))).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //<editor-fold desc="Main ship operations">

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
        KamikazeShip.worldWidth = worldWidth;
        KamikazeShip.worldHeight = worldHeight;

        return new RegistrationData("THE RED ARMY #" + id, new Color(1.0f, 0.0f, 0.0f), 3);
    }

    private boolean placeMine = false;

    @Override
    public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
        placeMine = !placeMine;

        if (placeMine) {
            return new DeploySpaceMineCommand(1);
        } else {
            return new ThrustCommand('B', 1, 1);
        }
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

    /**
     * Makes ship go to point.
     * @param ship Our ship.
     * @param point Point to go to.
     * @return Commands.
     */
    private static ShipCommand gotoPoint(ObjectStatus ship, Point point) {
        int angleDifference = ship.getPosition().getAngleTo(point) - ship.getOrientation();

        Vector2D velocity = new Vector2D(ship.getSpeed() * Math.cos(Math.toRadians(ship.getOrientation() - ship.getMovementDirection())),
                ship.getSpeed() * Math.sin(Math.toRadians(ship.getOrientation() - ship.getMovementDirection())));

        System.out.println("Velocity is " + velocity);

        if (velocity.dY > 10.0) {
            return new ThrustCommand('R', 1, 0.5, false);
        } else if (velocity.dY < -10.0) {
            return new ThrustCommand('L', 1, 0.5, false);
        }

        if (Math.abs(angleDifference) - ANGLE_TOLERANCE < 0) {
            return new ThrustCommand('B', 1, 0.5);
        } else {
            return new RotateCommand((int)Math.rint(angleDifference));
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

    //</editor-fold>


}
