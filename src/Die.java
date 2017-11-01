
public class Die {
	private int numSides;
	private int currentValue;
	
	public Die() {
		this.numSides = 6;
		this.currentValue = this.roll();
	}
	public Die(int numSides, int value) {
		this.numSides = numSides;
		this.currentValue = value;
	}
	public Die(int numSides) {
		this.numSides = numSides;
		this.currentValue = this.roll();
	}
	
	public int roll() {
		this.currentValue = (int)(Math.random() * this.numSides + 1);
		return this.currentValue;
	}
	
	public String toString() {
		return "Num sides " + this.numSides + " Current value " + this.currentValue;
	}
	
	public int getCurrentValue() {
		return this.currentValue;
	}
	public int getNumSides() {
		return this.numSides;
	}
	public void setCurrentValue(int value) {
		this.currentValue = value;
	}
}
