import java.util.Arrays;
import java.util.stream.*;
public class YahtzeeGame {
	private ScoreBoard gameScoreBoard = new ScoreBoard();
	private YahtzeeDice liveDice = new YahtzeeDice();
	private int gameScore; 
	private int turn = 1;
	String[] choices = new String[] {"Ones","Twos","Threes","Fours","Fives","Sixes","3oKind", "4oKind", "FHouse","SStraight","LStraight","Chance","Yahtzee"};
	int userPoints = 0;

	public int getScore() {
		return this.gameScore;
	}
	public void run() {
		System.out.println("Welcome to Yahtzee! ");
		System.out.println("This is a dice game where you can roll dice or store them");
		System.out.println("to get specific combinations of di!");
		System.out.println("---------");
		boolean scoreNotFull = true;
		while (scoreNotFull) {
			//This while loop keeps track of the turns taking place
			//these are turn based variables. 
			liveDice.setNumDie(5);
			int[] storedDiValues = new int[5];
			int indexToAddToStoredValues = 0;


			System.out.println("");
			System.out.println("Turn # " + turn);
			for (int i = 3; i > 0; i --) {
				System.out.println("");
				System.out.println("Roll # " + (4-i));
				if (i > 2) {
					this.getValidChoice("roll");
					doRoll();	
					int[] diVals = liveDice.getDiceValues();
					int[] storedVals = getStoredValuesFormatted(storedDiValues);
					int[] hand = getHand(diVals, storedVals);
					System.out.println("These are the values of the di: " + Arrays.toString(diVals));
					System.out.println("Your hand is: " + Arrays.toString(hand));
					gameScoreBoard.compileScores(hand);
					System.out.println(gameScoreBoard.getScoresString());
					System.out.println("--Your total points are " + gameScoreBoard.getTotalPoints());
				}
				else {
					while (true) {
						String choiceRollorTake = this.getValidChoice("roll","hold");
						if (choiceRollorTake.equals("hold")) {
							System.out.println("Which die would you like to hold? ");
							int[] diVals = liveDice.getDiceValues();
							int[] diNumberRepresentations = IntStream.rangeClosed(1, diVals.length).toArray();
							if (diVals.length == 0) {
								System.out.println("--You dont have any live die out currently, so you cant store anything.");
								continue;
							}
							System.out.println("These are the values of the di: " + Arrays.toString(diVals));
							System.out.println("Use these numbers " + Arrays.toString(diNumberRepresentations) + " to choose a di");
							System.out.println("If you want to choose more than one, enter choices like this: 1 2");
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
									if (j > liveDice.getNumDie() - 1) {
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
								int[] storedVals = getStoredValuesFormatted(storedDiValues);
								System.out.println("Stored values are: " + Arrays.toString(storedVals));
								liveDice.setNumDie(liveDice.getNumDie() - payload.length);
								break;
							}

						}
						else if (choiceRollorTake.equals("roll")) {
							doRoll();
							int[] diVals = liveDice.getDiceValues();
							int[] storedVals = getStoredValuesFormatted(storedDiValues);
							int[] hand = getHand(diVals, storedVals);
							System.out.println("These are the values of the di: " + Arrays.toString(diVals));
							System.out.println("Stored values are: " + Arrays.toString(storedVals));
							System.out.println("Your hand is: " + Arrays.toString(hand));
							gameScoreBoard.compileScores(hand);
							System.out.println(gameScoreBoard.getScoresString());
							System.out.println("--Your total points are " + gameScoreBoard.getTotalPoints());
							break;
						}
					}
				}
				System.out.println("Roll completed");
				String userChoice;
				do {
					System.out.print("Do you want to store points? Type 'yes' or 'no':  ");
					userChoice = TextIO.getln();
				}while(!userChoice.equals("yes") && !userChoice.equals("no"));
				if(userChoice.equals("yes")) {
					System.out.print("Type the name of one of the above options to store those points:  ");
					String[] possibleEntries = gameScoreBoard.getPossibleChoicesArr();
					boolean foundMatch = false;
					String userMatchSelection = "";
					while (foundMatch == false) {
						userMatchSelection = TextIO.getln();
						for (int index = 0; index < possibleEntries.length; index++) {
							if (possibleEntries[index].equals(userMatchSelection)) {
								foundMatch = true;
								if (gameScoreBoard.saveCombo(userMatchSelection) == false) {
									foundMatch = false;
								}
							}
						}
						if (foundMatch == false) {
							System.out.print("You didn't enter a choice in correctly!  Enter again: ");
						}
					}
					System.out.println("Saved points for " + userMatchSelection);
					break;
				}
			}
			if (gameScoreBoard.gameOver()) {
				break;
			}
			this.turn ++;
		}
		System.out.println("Game Over! You have saved all the possible combinations!");
		System.out.println("Your total points were " + gameScoreBoard.getTotalPoints());
	}

	//rolls the Yahtzee die object
	private void doRoll() {
		System.out.println("Rolling...");
		liveDice.roll();
	}
	//get a valid choice
	private String getValidChoice(String arg1) {
		boolean goodChoice = false;
		String choiceRoll = "";
		while (goodChoice == false) {
			System.out.print("Type "+arg1+" to "+arg1+" the di: ");
			choiceRoll = TextIO.getlnString();
			if (choiceRoll.toLowerCase().equals(arg1)) {
				goodChoice = true;
			}
		}
		return choiceRoll;
	}
	private String getValidChoice(String arg1, String arg2) {
		boolean goodChoice = false;
		String choiceRoll = "";
		while (goodChoice == false) {
			System.out.print("Type "+arg1+" to "+arg1+" the di or "+arg2+" to "+arg2+" the die: ");
			choiceRoll = TextIO.getlnString();
			if (choiceRoll.toLowerCase().equals(arg1) || choiceRoll.toLowerCase().equals(arg2)) {
				goodChoice = true;
			}
		}
		return choiceRoll;
	}
	//formats the stored values from this object by getting rid of all the 0's
	private int[] getStoredValuesFormatted(int[] storedDiValues) {
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
	//combines the live dice values and the stored values to return the current hand
	private int[] getHand(int[] rollValues, int[] formattedStoredDiValues) {
		int[] returnArray = new int[rollValues.length + formattedStoredDiValues.length];
		int index = 0;
		for (int val : rollValues) {
			returnArray[index] = val;
			index ++;
		}
		for (int val : formattedStoredDiValues) {
			returnArray[index] = val;
			index ++;
		}
		return returnArray;
	}
}
