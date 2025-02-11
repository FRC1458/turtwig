package org.usfirst.frc.team1458.robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {
	private LIDARLite l;
	
	private Joystick j = new Joystick(0);
	private Joystick j1 = new Joystick(1);

	private CANTalon talon1 = new CANTalon(18);
	private CANTalon talon2 = new CANTalon(19);

	private CANTalon agitator = new CANTalon(13);
	
	public Robot() {

	}

	@Override
	public void robotInit() {
		l = new LIDARLite(SerialPort.Port.kMXP);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {

	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		
		PowerDistributionPanel panel = new PowerDistributionPanel();

		while (isOperatorControl() && isEnabled()) {
			talon1.set(-j.getAxis(AxisType.kZ));
			talon2.set(j.getAxis(AxisType.kZ));
			
			SmartDashboard.putNumber("JoystickValue", j.getAxis(AxisType.kZ));
			
			SmartDashboard.putNumber("Talon1", talon1.getOutputCurrent());
			SmartDashboard.putNumber("Talon2", talon2.getOutputCurrent());

			SmartDashboard.putNumber("Talon1 PDP", panel.getCurrent(11));
			SmartDashboard.putNumber("Talon2 PDP", panel.getCurrent(4));
			
			agitator.set(j1.getAxis(AxisType.kZ));
			SmartDashboard.putNumber("Joystick2Value", j1.getAxis(AxisType.kZ));
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
