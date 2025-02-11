package com.team1458.turtleshell2.util;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

/**
 * A class holding helpful static methods for maths-related things.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleMaths {
	/**
	 * Fit the double to a specified range. Equivalent to: (toFit > max ? max :
	 * toFit < min ? min: toFit)
	 * 
	 * @param toFit
	 *            number to fit in range
	 * @param min
	 *            minimum value for toFit
	 * @param max
	 *            Maximum value for toFit
	 * @return
	 */
	public static double fitRange(double toFit, double min, double max) {
		if (toFit > max) {
			return max;
		}
		if (toFit < min) {
			return min;
		}
		return toFit;
	}

	/**
	 * Returns the absolute difference between the two numbers
	 * 
	 * @param a
	 *            1st value
	 * @param b
	 *            2nd value
	 * @return The absolute difference of the two, equal to Math.abs(a-b)
	 */
	public static double absDiff(double a, double b) {
		return Math.abs(a - b);
	}

	/**
	 * Returns the bigger of the two double values. Equivalent to: (a > b ? a :
	 * b)
	 * 
	 * @param a
	 * @param b
	 * @return The larger of a and b
	 */
	public static double biggerOf(double a, double b) {
		return (a > b ? a : b);
	}

	/**
	 * Returns the bigger of the two int values
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int biggerOf(int a, int b) {
		return (a > b ? a : b);
	}

	/**
	 * A class to help with moving values between different ranges, i.e. 0-100
	 * to 3-7
	 * 
	 * @author mehnadnerd
	 *
	 */
	public static class RangeShifter {
		private final double minA;
		private final double minB;
		private final double rngA;
		private final double rngB;

		/**
		 * Construct a new RangeShifter
		 * 
		 * @param minA
		 *            Minimum point of first range
		 * @param maxA
		 *            Maximum point of first range
		 * @param minB
		 *            Minimum point of second range
		 * @param maxB
		 *            Maximum point of second range
		 */
		public RangeShifter(double minA, double maxA, double minB, double maxB) {
			this.minA = minA;
			this.rngA = maxA - minA;
			this.minB = minB;
			this.rngB = maxB - minB;
		}

		/**
		 * Use the RangeShifter to actually shift numbers.
		 * 
		 * @param toShift
		 *            The number to shift
		 * @return The shifted value.
		 */
		public double shift(double toShift) {
			return minB + (rngB / rngA) * (toShift - minA);
		}
	}

	/**
	 * A class for shifting with more advanced ranges. This features two arrays,
	 * an input and output, where the values correspond to each other. This
	 * class then linearly interpolates between the points. <br />
	 * 
	 * Beyond the defined region, it continues the slope from the outermost
	 * areas. The function shift() is G0 continuous, with its derivatives
	 * undefined at each of the control points.
	 * 
	 * @author mehnadnerd
	 *
	 */
	public static class AdvancedRangeShifter extends RangeShifter {
		private final RangeShifter[] rs;
		private final double[] sp;

		public AdvancedRangeShifter(double[] from, double[] to) {
			super(0, 0, 0, 0);
			if (from.length != to.length) {
				throw new IllegalArgumentException("From Array and To Array must be same size");
			}
			rs = new RangeShifter[from.length];
			sp = from;
			for (int i = 0; i < from.length - 2; i++) {
				rs[i] = new RangeShifter(from[i], from[i + 1], to[i], to[i + 1]);
			}
		}

		@Override
		public double shift(double d) {
			if (d < sp[0]) {
				return rs[0].shift(d);
			}
			for (int i = 0; i < rs.length - 2; i++) {
				if (d >= sp[i]) {
					return rs[i].shift(d);
				}
			}
			return rs[rs.length - 1].shift(d);
		}

	}

	/**
	 * Quickly shift numbers using RangeShifter
	 * 
	 * @return The shifted value.
	 */
	public static double shift(double value, double minA, double maxA, double minB, double maxB) {
		return new RangeShifter(minA, maxA, minB, maxB).shift(value);
	}

	/**
	 * Rounds a double to a certain number of places past the decimal point
	 * 
	 * @param toRound
	 *            The double to round
	 * @param decimalPlaces
	 *            The number of digits past the decimal point to keep, negative
	 *            numbers are supported.
	 * @return
	 */
	public static double round(double toRound, int decimalPlaces) {
		toRound *= Math.pow(10, decimalPlaces);
		toRound = Math.round(Math.round(toRound));
		toRound /= Math.pow(10, decimalPlaces);
		return toRound;
	}

	/**
	 * Returns the smallest of two double values
	 * 
	 * @param a
	 * @param b
	 * @return The smaller of the two values, or b if they are equal or such
	 */
	public static double smallerOf(double a, double b) {
		return (a < b ? a : b);
	}

	/**
	 * Returns the smaller of the two int values
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int smallerOf(int a, int b) {
		return (a < b ? a : b);
	}

	/**
	 * Normalises a slope to between zero and one. Used in arcade chassis code
	 * 
	 * @param m
	 *            The slope to normalise
	 * @return The slope normalised to (0, 1], it will be absolute valued and
	 *         recipocaled as nessecary
	 */
	public static double normaliseM(double m) {
		m = Math.abs(m);
		if (m > 1) {
			m = 1 / m;
		}
		if (Double.isNaN(m) || Double.isInfinite(m)) {
			m = 0;
		}
		return m;
	}

	/**
	 * Calculates percent Error
	 * 
	 * @param actual
	 *            The ideal or "correct" value
	 * @param measured
	 *            The measured value
	 * @return
	 */
	public static double percentError(double actual, double measured) {
		return TurtleMaths.absDiff(actual, measured) / actual;
	}

	/**
	 * Mathematical way for joystick deadband, if |input| < deadband, returns
	 * zero, otherwise returns input
	 * 
	 * @param input
	 *            number to check against deadband
	 * @param deadbandRange
	 *            Range at which should round to zero
	 * @return Input or zero
	 */
	public static double deadband(double input, double deadbandRange) {
		if (Math.abs(input) < deadbandRange) {
			return 0;
		}

		return input;

	}

	/**
	 * Average a series of doubles
	 * 
	 * @param num
	 *            The doubles to average
	 * @return The arithmetic mean of the numbers
	 */
	public static double avg(double... num) {
		double sum = 0;
		for (double d : num) {
			sum += d;
		}
		return sum / num.length;
	}

	public static double avg(ArrayList<Double> num) {
		double sum = 0;
		for (double d : num) {
			sum += d;
		}
		return sum / num.size();
	}

	/**
	 * Scales a value quadratically while preserving signs, i.e 1 -> 1, .5 ->
	 * .25, and -1 -> -1
	 * 
	 * @param toScale
	 *            The value to be scaled
	 * @return The scaled value
	 */
	public static double quadraticScale(double toScale) {
		return toScale * Math.abs(toScale);
	}

	/**
	 * Scales a value in a logistic step format, the exact function approximates
	 * a linear function but has logistic-like steps in every interval of 1/2
	 *
	 * Function is: y = x - sin(4pi*x)/4pi
	 * 
	 * @param toScale
	 *            The number to be scaled
	 * @return The scaled number
	 */
	public static double logisticStepScale(double toScale) {
		return toScale - (Math.sin(4 * Math.PI * toScale) / (4 * Math.PI));
	}

	/**
	 * Convenience method that yields -1 if b is true, 1 otherwise
	 * 
	 * @return
	 */
	public static int reverseBool(boolean isReversed) {
		return isReversed ? -1 : 1;
	}

	/**
	 * Class for modelling input functions, like the quadratic and logistic step
	 * scale
	 * 
	 * @author mehnadnerd
	 *
	 */
	public static class InputFunction implements UnaryOperator<Double> {
		public static final InputFunction identity = new InputFunction(UnaryOperator.identity());
		public static final InputFunction logisticStep = new InputFunction(
				x -> x - (Math.sin(4 * Math.PI * x) / (4 * Math.PI)));
		public static final InputFunction quadratic = new InputFunction(x -> x * Math.abs(x));

		private final UnaryOperator<Double> op;

		public InputFunction(UnaryOperator<Double> op) {
			this.op = op;
		}

		@Override
		public Double apply(Double t) {
			return op.apply(t);
		}
	}

	public static class InputSmoother extends InputFunction {
		private ArrayList<Double> store;
		private final int samplesToAverage;

		public InputSmoother(int samplesToAverage) {
			super(InputFunction.identity);
			store = new ArrayList<>(samplesToAverage);
			this.samplesToAverage = samplesToAverage;
		}

		@Override
		public Double apply(Double t) {
			store.add(t);
			if (store.size() > samplesToAverage) {
				store.remove(0);
			}

			return TurtleMaths.avg(store);
		}

	}

	/**
	 * Hiding the constructor so cannot be initialised
	 */
	private TurtleMaths() {
	}

}