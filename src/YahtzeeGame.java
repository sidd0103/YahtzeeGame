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
			gameDice.setNumDie(5);
			int numDieUsing = 5;
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
					System.out.println("Stored values are: " + Arrays.toString(storedDiValues));
				}
				else {
					String choiceRollorTake = getChoiceRollOrTake();
					if (choiceRollorTake.equals("take")) {
						System.out.println("Which di would you like to take out? ");
						int[] diVals = gameDice.getDiceValues();
						int[] diNumberRepresentations = IntStream.rangeClosed(0, diVals.length - 1).toArray();
						System.out.println("These are the values of the di: " + Arrays.toString(diVals));
						System.out.println("Use these numbers " + Arrays.toString(diNumberRepresentations) + " to choose a di");
						System.out.println("If you want to choose more than one, enter choices like this: 0 1");
						String chosenDi = "";
						while (true) {
							System.out.print("Enter choice here: ");
							chosenDi = TextIO.getlnString();
							chosenDi = chosenDi.replaceAll("\\s+","");
							int[] payload = new int[chosenDi.length()];
							for (int j = 0; j < chosenDi.length(); j ++) {
								int integerAtIndex = Character.getNumericValue((chosenDi.charAt(j)));
								if (integerAtIndex >= 0 && integerAtIndex < diVals.length) {
									payload[j] = diVals[integerAtIndex];
								}
							}
							if (Arrays.asList(payload).contains(0)) {
								continue;
							}
							else {
								for (int value : payload) {
									storedDiValues[indexToAddToStoredValues] = value;
									System.out.println("Removed di with value " + value);
									System.out.println("Stored values are: " + Arrays.toString(storedDiValues));
									indexToAddToStoredValues ++;
								}
								gameDice.setNumDie(numDieUsing - payload.length);
								break;
							}
						}
		
					}
					else if (choiceRollorTake.equals("roll")) {
						doRoll();
						System.out.println("Stored values are: " + Arrays.toString(storedDiValues));
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
			System.out.print("Type roll to roll the di or take to take the die: ");
			choiceRoll = TextIO.getlnString();
			if (choiceRoll.toLowerCase().equals("roll") || choiceRoll.toLowerCase().equals("take")) {
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
		System.out.println("The total score of your di is " + rollValue);
	}
}
