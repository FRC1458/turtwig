
/**
 * A class representing a unit per time
 * @author mehnadnerd
 *
 * @param <T> The Unit this is a rate of, this will have T/s as its units
 */
public final class Rate<T extends Unit> implements Unit {
	private final double value;
	@Override
	/**
	 * Get the value in the units T/second
	 */
	public double getValue() {
		return value;
	}
	
	public Rate(T numerator, Time denominator) {
		value=(numerator.getValue()/denominator.getValue());
	}
	
	/**
	 * Constructor with value already set
	 * @param value
	 */
	public Rate(double value) {
		this.value=value;
	}
}
