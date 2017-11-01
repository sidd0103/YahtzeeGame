import java.util.Arrays;
import java.util.stream.*;
public class YahtzeeGame {
	private ScoreBoard gameScoreBoard = new ScoreBoard();
	private YahtzeeDice gameDice = new YahtzeeDice();
	private int gameScore; 
	private int turn = 1;
	
	
	public int getScore() {
		return this.gameScore;
	}
	public void run() {
		System.out.println("Welcome to Yahtzee! ");
		System.out.println("This is a dice game where you can roll dice or take them");
		System.out.println("To get specific combinations of di!");
		System.out.println("---------");
		boolean scoreNotFull = true;
		while (scoreNotFull) {
			//This while loop keeps track of the turns taking place
			//these are turn based variables. 
			gameDice.setNumDie(5);
			int[] storedDiValues = new int[5];
			int indexToAddToStoredValues = 0;
			
			
			System.out.println("");
			System.out.println("Turn # " + turn);
			for (int i = 3; i > 0; i --) {
				System.out.println("");
				System.out.println("Roll # " + (4-i));
				if (i > 2) {
					String choiceRoll = this.getChoiceRoll();
					doRoll();	
					System.out.println("Stored values are: " + Arrays.toString(storedValuesFormatted(storedDiValues)));
				}
				else {
					String choiceRollorTake = getChoiceRollOrTake();
					if (choiceRollorTake.equals("store")) {
						System.out.println("Which di would you like to take out? ");
						int[] diVals = gameDice.getDiceValues();
						int[] diNumberRepresentations = IntStream.rangeClosed(1, diVals.length).toArray();
						System.out.println("These are the values of the di: " + Arrays.toString(diVals));
						System.out.println("Use these numbers " + Arrays.toString(diNumberRepresentations) + " to choose a di");
						System.out.println("If you want to choose more than one, enter choices like this: 0 1");
						String chosenDi = "";
						while (true) {
							//get the choice the user wants to use. 
							System.out.print("Enter choice here: ");
							chosenDi = TextIO.getlnString();
							chosenDi = chosenDi.replaceAll("\\s+","");
							int[] payload = new int[chosenDi.length()];
							
							//this part creates a pay load of values chosen 
							for (int j = 0; j < chosenDi.length(); j ++) {
								//make sure that we aren't choosing indexes that don't have a corresponding die
								if (j > gameDice.getNumDie() - 1) {
									break;
								}
								else {
									int integerAtIndex = Character.getNumericValue((chosenDi.charAt(j))) - 1;
									if (integerAtIndex >= 0 && integerAtIndex < diVals.length) {
										//save the values at the locations chosen to a pay load array
										payload[j] = diVals[integerAtIndex];
									}
								}
							}
							//this part iterates through the pay load and saves values.  
							for (int value : payload) {
								storedDiValues[indexToAddToStoredValues] = value;
								indexToAddToStoredValues ++;
							}
							System.out.println("Stored values are: " + Arrays.toString(storedValuesFormatted(storedDiValues)));
							gameDice.setNumDie(gameDice.getNumDie() - payload.length);
							break;
						}
		
					}
					else if (choiceRollorTake.equals("roll")) {
						doRoll();
						System.out.println("Stored values are: " + Arrays.toString(storedValuesFormatted(storedDiValues)));
					}
				}
				System.out.println("Roll completed");
			}
			this.turn ++;
		}
	}
	private String getChoiceRoll() {
		boolean goodChoice = false;
		String choiceRoll = "";
		while (goodChoice == false) {
			System.out.print("Type roll to roll the di: ");
			choiceRoll = TextIO.getlnString();
			if (choiceRoll.toLowerCase().equals("roll")) {
				goodChoice = true;
			}
		}
		return choiceRoll;
	}
	private String getChoiceRollOrTake() {
		boolean goodChoice = false;
		String choiceRoll = "";
		while (goodChoice == false) {
			System.out.print("Type roll to roll the di or store to store the die: ");
			choiceRoll = TextIO.getlnString();
			if (choiceRoll.toLowerCase().equals("roll") || choiceRoll.toLowerCase().equals("store")) {
				goodChoice = true;
			}
		}
		return choiceRoll;
	}
	private void doRoll() {
		System.out.println("Rolling...");
		int rollValue = gameDice.roll();
		int[] diVals = gameDice.getDiceValues();
		System.out.println("These are the values of the di: " + Arrays.toString(diVals));
	}
	private int[] storedValuesFormatted(int[] storedDiValues) {
		int length = 0;
		for (int value : storedDiValues) {
			if (value != 0) {
				length ++;
			}
		}
		int counter = 0;
		int[] returnArray = new int[length];
		for (int value : storedDiValues) {
			if (value != 0) {
				returnArray[counter] = value;
				counter ++;
			}
		}
		return returnArray;
	}
}
