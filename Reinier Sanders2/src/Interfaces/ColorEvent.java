package Interfaces;

public class ColorEvent implements RobotEvent {

	private boolean isRed;
	private float value;

	public ColorEvent(boolean isRed, float newValue) {
		this.isRed = isRed;
		this.value = newValue;
	}

	public boolean isRed() {
		return isRed;
	}

	@Override
	public String getName() {
		return "Color Changed Event";
	}

	public float getValue() {
		return value;
	}

}
