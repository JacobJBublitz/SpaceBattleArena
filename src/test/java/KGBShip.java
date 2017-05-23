import com.github.kaboomboom3.spacebattlearena.*;
import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.IdleCommand;
import ihs.apcs.spacebattle.commands.RotateCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;
import ihs.apcs.spacebattle.commands.ThrustCommand;

import java.awt.*;

/**
 * Created by nerdares on 5/23/2017.
 */
public class KGBShip extends BasicSpaceship{


    //<editor-fold desc="World properties">

    private static int worldWidth = 0;
    private static int worldHeight = 0;

    //</editor-fold>

    private static Gamemodes gamemodes = Gamemodes.DEATHMATCH;

    private static int speedIncrementCounter;

    private final int id;



    public KGBShip(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            final int id = i;
            new Thread(() -> TextClient.run("10.51.4.70", new KGBShip(id))).start();
        }
    }


    //<editor-fold desc="Main ship operations">

    @Override
    public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
        KGBShip.worldWidth = worldWidth;
        KGBShip.worldHeight = worldHeight;

        return new RegistrationData("KGB OFFICER #" + id, new Color(1.0f, 0.0f, 0.0f), 3);
    }

    @Override
    public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
        switch (gamemodes) {
            case FIND_THE_MIDDLE:
                return FindTheMiddleCommand.getNextCommand(basicEnvironment);
            case DEATHMATCH:
                return DeathmatchCommand.attackShip(basicEnvironment, false);
            case BAUBLE:
                return BaubleCommand.baubleCommand(basicEnvironment);
            default:
                return new IdleCommand(0.1);
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
