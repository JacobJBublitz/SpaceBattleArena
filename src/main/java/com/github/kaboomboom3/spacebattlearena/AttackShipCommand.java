package com.github.kaboomboom3.spacebattlearena;

import ihs.apcs.spacebattle.ObjectStatus;
import ihs.apcs.spacebattle.Point;
import ihs.apcs.spacebattle.commands.FireTorpedoCommand;
import ihs.apcs.spacebattle.commands.RotateCommand;
import ihs.apcs.spacebattle.commands.ShipCommand;
import ihs.apcs.spacebattle.commands.SteerCommand;

public class AttackShipCommand {

	public static double ANGLE_TOLERANCE = 10;

	private static Point getFuturePosition(ObjectStatus ourShip, ObjectStatus targetShip) {
		double futureX = targetShip.getPosition().getX() + targetShip.getSpeed() * Math.cos(Math.toRadians(targetShip.getMovementDirection()));
		double futureY = targetShip.getPosition().getY() - targetShip.getSpeed() * Math.sin(Math.toRadians(targetShip.getMovementDirection()));

		return new Point(futureX, futureY);
	}

	public static ShipCommand attackShip(ObjectStatus ourShip, ObjectStatus targetShip) {
		Point futurePos = getFuturePosition(ourShip, targetShip);

		System.out.printf("Angle to closest enemy: %s%n", ourShip.getPosition().getAngleTo(futurePos) - ourShip.getOrientation());

		int relativeAngle = ourShip.getPosition().getAngleTo(futurePos) - ourShip.getOrientation();

		System.out.println(Math.abs(relativeAngle) - ANGLE_TOLERANCE);

		if (Math.abs(relativeAngle) - ANGLE_TOLERANCE < 0) {
			return new FireTorpedoCommand('F');
		} else {
			return new RotateCommand(relativeAngle % 90);
		}
	}
}
