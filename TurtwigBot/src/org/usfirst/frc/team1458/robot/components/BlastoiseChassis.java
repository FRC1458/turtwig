package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.movement.*;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeRotationEncoder;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import org.usfirst.frc.team1458.robot.Constants;
import org.usfirst.frc.team1458.robot.Robot;

/**
 * New Robot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis {

	/**
	 * Drive train
	 */
	private TankDriveChassis tankDrive;

	// Sensors
	private TurtleNavX navX = null;

	private Logger logger;

	public BlastoiseChassis(TurtleNavX navX, Logger logger) {
		this.navX = navX;


		this.logger = logger;

		setupDrivetrain();
		TurtleDashboard.enablePidTuning(Constants.TurnPID.PID_CONSTANTS, "TurnPID");
	}

	PID gearAlignPID = new PID(Constants.TurnPID.PID_CONSTANTS, Constants.DriverVision.CAMERA_WIDTH, 0);

	/**
	 * Chassis Specific Initialization
	 * Called after constructor
	 */
	private void setupDrivetrain() {
		if(!Robot.isReal()) {
			// Simulation
			tankDrive = new TankDriveChassis(
					new MotorSet(
							new TurtleTalonSR(1),
							new TurtleTalonSR(2)
					),
					new MotorSet(
							new TurtleTalonSR(3),
							new TurtleTalonSR(4)
					),
					new TurtleFakeRotationEncoder()
			);
					navX.getYawAxis()
		} else {
			// Blastoise Chassis
			tankDrive = new TankDriveChassis(
				new FollowerMotorSet(
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR1),
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR2),
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR3)
				),
				new FollowerMotorSet(
						new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR1, true),
						new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR2, true),
							new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR3, true)
				),
				navX.getYawAxis()
			);
		}
		logger.info("Chassis initialised.");
	}

	public Distance getLeftDistance() {
		return tankDrive.getLeftDistance();
	}

	public Distance getRightDistance() {
		return tankDrive.getRightDistance();
	}

	public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
		tankDrive.updateMotors(leftPower, rightPower);
	}

	public void stopMotors() {
		tankDrive.stopMotors();
	}
	
	public TankDriveChassis getDriveTrain() {
		return tankDrive;
	}

	public void driveStraight(MotorValue speed, PIDConstants constants) {
		tankDrive.driveStraight(speed, constants);
	}

	public void stopDrivingStraight() {
		tankDrive.stopDrivingStraight();
	}

	public void turn(Angle angle, MotorValue speed, PIDConstants constants) {
		tankDrive.turn(angle, speed, constants);
	}

	public void stopTurn() {
		tankDrive.stopTurn();
	}

	public boolean isTurning() {
		return tankDrive.isTurning();
	}

	public void resetEncoders() {
		tankDrive.resetEncoders();
	}
}
