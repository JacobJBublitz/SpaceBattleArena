import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.BasicSpaceship;
import ihs.apcs.spacebattle.RegistrationData;
import ihs.apcs.spacebattle.TextClient;
import ihs.apcs.spacebattle.commands.ShipCommand;
import ihs.apcs.spacebattle.commands.ThrustCommand;

import java.awt.*;

/**
 * This ship is purely for example. It does nothing.
 */
public class TestShip extends BasicSpaceship {

	public static void main(String[] args) {
		TextClient.run("localhost", new TestShip());
	}

	@Override
	public RegistrationData registerShip(int i, int i1, int i2) {
		return new RegistrationData("Test Ship", new Color(1.0f, 1.0f, 1.0f), 0);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {
		return new ThrustCommand('B', 0.5, 1);
	}
}
