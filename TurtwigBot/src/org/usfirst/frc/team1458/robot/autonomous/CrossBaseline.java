package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This autonomous program will make the robot cross the baseline and nothing else.
 * The robot must start against the alliance station wall, facing the airship.
 *
 * @author asinghani
 */
public class CrossBaseline extends SampleAutoMode {
	public CrossBaseline(TankDrive chassis, Logger logger, TurtleNavX navX) {
		super(chassis, logger, navX.getYawAxis());
	}
    // places middle gear
	/**
	 * Runs the autonomous program.
	 *
	 * This will drive the robot forward 9.5 feet at 0.8 speed and then 2 feet at 0.3 speed
	 */
	@Override
	public void auto(){
		Timer t = new Timer();
		t.start();
		while (t.get() < 2 && RobotState.isAutonomous() && RobotState.isEnabled()) {
			chassis.tankDrive(new MotorValue(-.28), new MotorValue(-.28));
			System.out.println(t.get() + " " + chassis.getLeftDistance().getInches() + " " +
			chassis.getRightDistance().getInches() + " ");
		}
		while (t.get() < 3.5 && RobotState.isAutonomous() && RobotState.isEnabled()) {
			chassis.tankDrive(new MotorValue(-.15),new MotorValue(-.15));
		}
		while (t.get() < 4.0 && RobotState.isAutonomous() && RobotState.isEnabled()) {
			chassis.tankDrive(new MotorValue(0),new MotorValue(0));
		}
		while (t.get() < 4.30 && RobotState.isAutonomous() && RobotState.isEnabled()) {//icnreased from 4.1 to 4.3
			chassis.tankDrive(new MotorValue(.15),new MotorValue(.15));
		}
		chassis.stop();
	}
}