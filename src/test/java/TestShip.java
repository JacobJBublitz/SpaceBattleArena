import com.github.kaboomboom3.spacebattlearena.Vector2D;

import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.*;

import java.awt.*;
import java.util.*;

/**
 * This ship is purely for example.
 * Right now, this test ship will thrust to gain speed and maintain that constant speed.
 */
public class TestShip extends BasicSpaceship {

	public int count = 0;


	public Point origin = new Point(0,0);
	public long startTime = 0;
	public long endTime = 0;
	public int angularDisplacement = 0;

	public static void main(String[] args) {
		TextClient.run("localhost", new TestShip());
	}


	//<editor-fold desc="Main ship operations">

	@Override
	public RegistrationData registerShip(int i, int i1, int i2) {
		return new RegistrationData("Test Ship", new Color(1.0f, 1.0f, 1.0f), 0);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
		ObjectStatus ship = basicEnvironment.getShipStatus();

		//<editor-fold desc="Angular displacement vs time test">
//		if(ship.getPosition().getAngleTo(origin) - ship.getOrientation() != 0) {
//			angularDisplacement = ship.getPosition().getAngleTo(origin) - ship.getOrientation();
//			System.out.println("Angular displacement is " + angularDisplacement);
//			startTime = System.currentTimeMillis();
//			return new RotateCommand(angularDisplacement);
//		}
//		else
//		{
//			count++;
//
//			if(count <= 1) {
//				endTime = System.currentTimeMillis();
//				System.out.println("Elapsed time was : " + (endTime - startTime));
//				return new IdleCommand(1);
//			}
//			else
//			{
//				return new IdleCommand(1);
//			}
//		}
		//</editor-fold>

		//<editor-fold desc="Handle movement">
		if(count != 5) {
			count ++;
			return new ThrustCommand('B', 1, 1);
		}
		else {
			return new RotateCommand(5);
		}
		//</editor-fold>
	}

	//</editor-fold>


	//<editor-fold desc="Information logging">

	private void logInfo(BasicEnvironment environ, RadarResults results) {
		System.out.println("Our position is currently" + environ.getShipStatus().getPosition());
		System.out.println();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//</editor-fold>





}
