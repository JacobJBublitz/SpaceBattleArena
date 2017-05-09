package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.BasicEnvironment;
import ihs.apcs.spacebattle.BasicSpaceship;
import ihs.apcs.spacebattle.RegistrationData;
import ihs.apcs.spacebattle.TextClient;
import ihs.apcs.spacebattle.commands.IdleCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;

import java.awt.*;

public class Ship extends BasicSpaceship {

	public static void main(String[] args) {
		TextClient.run("localhost", new Ship());
	}

	@Override
	public RegistrationData registerShip(int numImages, int worldWidth, int worldHeight) {
		return new RegistrationData("Ship", new Color(0.3f, 0.1f, 0.5f), 10);
	}

	@Override
	public ShipCommand getNextCommand(BasicEnvironment basicEnvironment) {


		return new IdleCommand(0.5);
	}
}
