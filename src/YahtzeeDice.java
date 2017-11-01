
public class YahtzeeDice {
	private int numDi = 5;
	private int numFaces;
	private int currentValue = 0;
	private Die[] di = new Die[numDi];
	
	public YahtzeeDice() {
		this.numFaces = 6;
		this.di = generateDi();
		roll();
	}
	public YahtzeeDice(int numFaces) {
		this.numFaces = numFaces;
		this.di = generateDi();
		roll();
	}
	public Die[] generateDi() {
		Die[] totalDi = new Die[this.numDi];
		for (int i = 0; i < totalDi.length; i ++) {
			totalDi[i] = new Die(this.numFaces);
		}
		return totalDi;
	}
	public int getCurrentvalue() {
		return this.currentValue;
	}
	public int roll() {
		int totalVal = 0;
		for (int i = 0; i < this.numDi; i ++) {
			int val = di[i].roll();
			di[i].setCurrentValue(val);
			totalVal += val;
		}
		this.currentValue = totalVal;
		return currentValue;
	}
	
	public void setNumDie(int numDie) {
		this.numDi = numDie;
	}
	
	public Die[] getDice() {
		return this.di;
	}
	public int[] getDiceValues() {
		int[] returnArray = new int[this.numDi];
		for (int i = 0; i < this.numDi; i ++) {
			returnArray[i] = di[i].getCurrentValue();
		}
		return returnArray;
	}
	public String toString() {
		return "The total number of faces each di has is: " + this.numFaces + " The current random value of the di is: " + this.currentValue;
	}
	
}
