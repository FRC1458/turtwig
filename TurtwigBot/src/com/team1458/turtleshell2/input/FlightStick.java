package com.team1458.turtleshell2.input;

import edu.wpi.first.wpilibj.Joystick;

public class FlightStick {
	private Joystick j;

	public FlightStick(int usbport) {
		j = new Joystick(usbport);
	}
	
	
	/**
	 * Enum holding mappings from axis names to numbers
	 * @author mehnadnerd
	 */
	public static enum FlightAxis {
		ROLL(0), PITCH(1), THROTTLE(2), YAW(3), FOUR(4), FIVE(5);
		public final int val;

		FlightAxis(int i) {
			val = i;
		}
	}

	public static enum FlightButton {
		TRIGGER(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(
				11), TWELVE(12);
		public final int val;

		FlightButton(int i) {
			val = i;
		}
	}

	public JoystickAxis getAxis(FlightAxis a) {
		if (a == FlightAxis.PITCH) {
			return new JoystickAxis(j, a.val, true);
		}
		return new JoystickAxis(j, a.val, false);
	}

	public double getRawAxis(FlightAxis a) {
		return j.getRawAxis(a.val);
	}

	public TurtleJoystickButton getButton(FlightButton b) {
		return new TurtleJoystickButton(j, b.val);
	}

	public boolean getRawButton(FlightButton b) {
		return j.getRawButton(b.val);
	}

	public TurtleJoystickPOVSwitch getPOVSwitch() {
		return new TurtleJoystickPOVSwitch(j, 0);
	}
}
