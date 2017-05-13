import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.BasicSpaceship;
import ihs.apcs.spacebattle.RegistrationData;
import ihs.apcs.spacebattle.TextClient;
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

		System.out.println("Speed is " + basicEnvironment.getShipStatus().getSpeed());
		if(count != 5) {
			count ++;
			return new ThrustCommand('B', 1, 1);
		}
		else
			return new RotateCommand(5);
	}
}
