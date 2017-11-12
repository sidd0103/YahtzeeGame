import java.util.Arrays;
import java.util.stream.*;
public class ScoreBoard {
	private String[] possibleChoices = new String[] {"Ones","Twos","Threes","Fours","Fives","Sixes","3oKind", "4oKind", "FHouse","SStraight","LStraight","Chance","Yahtzee"};
	private int[] saveComboArray = new int[13];
	private int[] scoresArray = new int[13];
	public String[] getPossibleChoicesArr() {
		return possibleChoices;
	}
	public boolean saveCombo(String choice) {
		int index = 0;
		int points = 0;
		for (int i = 0; i < possibleChoices.length; i ++) {
			if (possibleChoices[i].equals(choice)) {
				index = i;
				break;
			}
		}
		if (saveComboArray[index] != 0) {
			return false;
		}
		points = this.scoresArray[index];
		saveComboArray[index] = points;
		return true;
	}
	public void compileScores(int[] arr) {
		int[] scoresArray = new int[13];
		int index = 0;
		int[] numericOptions = this.checkNumericOptions(arr);
		int[] specialDetails = getSpecialDetails(numericOptions, arr);
		int[] fullHouse = checkFullHouse(specialDetails);
		int[] straights = checkStraight(arr);
		for (int val : numericOptions) {
			scoresArray[index] = val;
			index ++;
		}
		scoresArray[index] = specialDetails[0];
		index ++;
		scoresArray[index] = specialDetails[1];
		index ++;
		scoresArray[index] = fullHouse[0];
		index ++;
		scoresArray[index] = straights[0];
		index ++;
		scoresArray[index] = straights[1];
		index ++;
		scoresArray[index] = specialDetails[4];
		index ++;
		scoresArray[index] = specialDetails[3];
		//handle looking for any stored points
		for (int i = 0; i < saveComboArray.length; i ++) {
			if (saveComboArray[i] != 0) {
				scoresArray[i] = saveComboArray[i];
			}
		}
		
		this.scoresArray = scoresArray;
	}
	public String getScoresString() {
		int[] scoresArray = this.scoresArray;
		String returnString = "\n////////////////// --Scoredboard-- ////////////////// \n";
		for (int i = 0; i < possibleChoices.length; i ++) {
			returnString += String.format("%-10s", possibleChoices[i]);
		}
		returnString += String.format("%n", "");
		for (int i = 0; i < scoresArray.length; i ++) {
			if (saveComboArray[i] != 0) {
				returnString += String.format("%-10s", "**" + scoresArray[i]);
			}
			else {
				returnString += String.format("%-10s", scoresArray[i]);
			}
		}
		returnString += "\n///////////////////////////////////////////////\n ";
		return returnString;	
	};
	//returns [1's points - 6's points]
	private int[] checkNumericOptions(int[] arr) {
		int[] numericPossibilities = new int[6];
		for (int i = 0; i < numericPossibilities.length; i ++) {
			int numberToLookFor = i + 1;
			for (int val : arr) {
				if (val == numberToLookFor) {
					numericPossibilities[i] += val;
				}
			}
		}
		return numericPossibilities;
	}	
	//returns [three of a kind points, four of a kind points, INT 1 IF there is a 2 of a kind and not a 3 or 4 of a kind, YAHTZEE points, CHANCE Points]
	private int[] getSpecialDetails(int[] numericPossiblities, int[] arr) {
		int sum = IntStream.of(arr).sum();
		int[] returnArray = new int[5];
		//set the chance value.
		returnArray[4] = sum;
		for (int i = 0; i < numericPossiblities.length; i ++) {
			int counter = 0;
			for (int j = 0; j < 2; j ++) {
				int kindWeAreLookingFor = j + 3;
				if (numericPossiblities[i] / kindWeAreLookingFor == i + 1) {
					returnArray[j] = sum;
					counter += 2;
				}
			}
			if (numericPossiblities[i] % 2 == 0 && numericPossiblities[i] / 2 >= 2) {
				counter ++;
			}
			//if this is true, this is a 2 of a kind and NOT a 3 or 4 of a kind. 
			if (counter == 1) {
				returnArray[2] = 1;
			}
			//if there are five of the same numeric possibliites, set YAHTZEE points
			if (numericPossiblities[i] / 5 == (i + 1)) {
				returnArray[3] = 50;
			}
			
		}
		return returnArray;
	}
	//returns [full house points]
	private int[] checkFullHouse(int[] ofAKindPossibilities) {
		if (ofAKindPossibilities[0] > 0 && ofAKindPossibilities[2] > 0) {
			return new int[] {25};
		}
		return new int[] {0};
	}
	//returns [small straight points, large straight points]
	private int[] checkStraight(int[] arr) {
		int[] returnArray = new int[2];
		Arrays.sort(arr);
		int correctIncramentsCounter = 0;
		for (int i = 0; i < arr.length; i ++) {
			if (i > 0 && arr[i] == (arr[i - 1] + 1)) {
				correctIncramentsCounter ++;
			}
		}
		if (correctIncramentsCounter == 3) {
			returnArray[0] = 30;
		}
		if (correctIncramentsCounter == 4) {
			returnArray[1] = 40;
		}
		return returnArray;
	}
}

