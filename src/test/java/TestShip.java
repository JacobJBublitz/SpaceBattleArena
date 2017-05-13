import ihs.apcs.spacebattle.*;
import ihs.apcs.spacebattle.commands.IdleCommand;
import ihs.apcs.spacebattle.commands.RotateCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;
import ihs.apcs.spacebattle.commands.ThrustCommand;

import java.awt.*;

/**
 * This ship is purely for example.
 * Right now, this test ship will thrust to gain speed and maintain that constant speed.
 */
public class TestShip extends BasicSpaceship {

	public int count = 0;

	public static void main(String[] args) {
		TextClient.run("localhost", new TestShip());
	}

	@Override
	public RegistrationData registerShip(int i, int i1, int i2) {
		return new RegistrationData("Test Ship", new Color(1.0f, 1.0f, 1.0f), 0);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {

		logInfo(basicEnvironment.getShipStatus());

		if(count != 5) {
			count ++;
			return new ThrustCommand('B', 1, 1);
		}
		else
			return new RotateCommand(5);
	}

	private void logInfo(ObjectStatus ourShip) {
		System.out.println("Speed is " + ourShip.getSpeed());
		System.out.println("Direction is " + ourShip.getMovementDirection());
		System.out.println("-----------------------END--------------------------------");
		System.out.println();
	}
}
