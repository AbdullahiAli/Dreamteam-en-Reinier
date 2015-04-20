package Interfaces;

public class ColorEvent implements RobotEvent {
	
	private boolean isRed;
	
	public ColorEvent(boolean isRed)
	{
		this.isRed = isRed;
	}
	
	public boolean isRed() {
		return isRed;
	}
	
	@Override
	public String getName() {
		return "Color Changed Event";
	}
	
}
