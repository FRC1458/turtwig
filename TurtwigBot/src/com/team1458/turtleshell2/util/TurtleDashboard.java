package com.team1458.turtleshell2.util;

import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.input.AnalogInput;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Sends important data to SmartDashboard
 *
 * @author asinghani
 */
public class TurtleDashboard {

	private static AutoModeHolder autoModeHolder = null;

	/**
	 * Cannot be instantiated
	 */
	private TurtleDashboard() {}

	/**
	 * Setup Match
	 */
	public static void setup() {
		DriverStation driverStation = DriverStation.getInstance();

		// Reset all variables
		SmartDashboard.putBoolean("BrownOut", false);
		SmartDashboard.putString("RobotState", "DISABLED");
		SmartDashboard.putNumber("LeftAxis", 0.0);
		SmartDashboard.putNumber("RightAxis", 0.0);


		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if(driverStation.isBrownedOut()){
					// Something really, really bad has happened
					SmartDashboard.putBoolean("BrownOut", true);
				} else {
					SmartDashboard.putBoolean("BrownOut", false);
				}

				// Show alliance
				DriverStation.Alliance alliance = driverStation.getAlliance();
				if(alliance == DriverStation.Alliance.Blue) SmartDashboard.putString("Alliance", "BLUE");
				else if(alliance == DriverStation.Alliance.Red) SmartDashboard.putString("Alliance", "RED");
				else SmartDashboard.putString("Alliance", "NONE");

				// Show DS Number
				SmartDashboard.putNumber("Location", driverStation.getLocation());

				// Show Timer and Battery
				SmartDashboard.putNumber("Time", driverStation.getMatchTime());
				SmartDashboard.putNumber("Battery", driverStation.getBatteryVoltage());
			}
		}, 0, 100);
	}

	public static void setAutoModeHolder(AutoModeHolder autoModeHolder) {
		if(TurtleDashboard.autoModeHolder != null) return;

		TurtleDashboard.autoModeHolder = autoModeHolder;

		SmartDashboard.putString("AutoModes", autoModeHolder.getAutoModes().toString());
		SmartDashboard.putNumber("SelectedAutoMode", autoModeHolder.getSelectedAutoModeIndex());

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				int newIndex = (int) SmartDashboard.getNumber("SelectedAutoMode", autoModeHolder.getSelectedAutoModeIndex());
				autoModeHolder.setSelectedAutoModeIndex(newIndex);
			}
		}, 0, 100);
	}

	/**
	 * Call when entering autonomous mode
	 */
	public static void autonomous() {
		SmartDashboard.putString("RobotState", "AUTONOMOUS");
	}

	/**
	 * Call when entering teleop mode
	 */
	public static void teleop() {
		SmartDashboard.putString("RobotState", "TELEOP");
	}

	/**
	 * Call when entering test mode
	 */
	public static void test() {
		SmartDashboard.putString("RobotState", "TEST");
	}

	/**
	 * Call when robot disabled
	 */
	public static void disabled() {
		SmartDashboard.putString("RobotState", "DISABLED");
	}

	/**
	 * Starts logging two axes to SmartDashboard
	 */
	public static void logAxis(AnalogInput left, AnalogInput right) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				SmartDashboard.putNumber("LeftAxis", left.get());
				SmartDashboard.putNumber("RightAxis", right.get());
			}
		}, 100, 100);
	}

	/**
	 * Starts logging rotation sensor to SmartDashboard
	 */
	public static void logRotation(TurtleRotationSensor rotationSensor, String name, boolean radians) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Angle rotation = rotationSensor.getRotation();
				SmartDashboard.putNumber(name, radians ? rotation.getRadians() : rotation.getDegrees());
			}
		}, 100, 100);
	}

	/**
	 * Starts logging distance sensor to SmartDashboard
	 */
	public static void logDistance(TurtleDistanceSensor distanceSensor, String name, boolean metres) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Distance rotation = distanceSensor.getDistance();
				SmartDashboard.putNumber(name, metres ? rotation.getMetres() : rotation.getCentimetres() );
			}
		}, 100, 100);
	}

	/**
	 * Allows tuning PID Constants (for use with custom dashboard)
	 */
	public static void enablePidTuning(PIDConstants defaultConstants, String name) {
		SmartDashboard.putString(name+"_PID_DEF", "PID");
		SmartDashboard.putNumber(name+"_PID_kP", defaultConstants.kP);
		SmartDashboard.putNumber(name+"_PID_kI", defaultConstants.kI);
		SmartDashboard.putNumber(name+"_PID_kD", defaultConstants.kD);
	}

	/**
	 * Get PID Constants with specific name
	 */
	public static PIDConstants getPidConstants(String name) {
		return new PIDConstants(
				SmartDashboard.getNumber(name+"_PID_kP", 0.0),
				SmartDashboard.getNumber(name+"_PID_kI", 0.0),
				SmartDashboard.getNumber(name+"_PID_kD", 0.0)
		);
	}
}
