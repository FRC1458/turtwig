package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;

/**
 * Time-based autonomous test program
 *
 * Drives the robot forward for 4 feet,
 * then turns right for one second,
 * then drives backward for 4 feet,
 * then turns left for one second
 *
 * @author asinghani
 */
public class BlastoiseTestDistanceAutonomous extends BlastoiseAutoMode {

	private static double SPEED = 0.1;

	public BlastoiseTestDistanceAutonomous(BlastoiseChassis chassis, TurtleLogger logger) {
		super(chassis, logger);
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		moveDistance(48, SPEED);
		turnMillis(1000, SPEED);
		moveDistance(-48, SPEED);
		turnMillis(1000, -SPEED);
	}
}
