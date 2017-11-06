import java.util.Arrays;
import java.util.stream.*;
public class ScoreBoard {
	public String getScoresString(int[] arr) {
		String[] possibleChoices = new String[] {"Ones","Twos","Threes","Fours","Fives","Sixes","3oKind", "4oKind", "FHouse","SStraight","LStraight","Chance","Yahtzee"};
		int[] numericOptions = this.checkNumericOptions(arr);
		int[] specialDetails = getSpecialDetails(numericOptions, arr);
		int[] fullHouse = checkFullHouse(specialDetails);
		int[] straights = checkStraight(arr);
		String returnString = "\n////////////////// --Scoredboard-- ////////////////// \n";
		for (int i = 0; i < possibleChoices.length; i ++) {
			returnString += String.format("%-10s", possibleChoices[i]);
		}
		returnString += String.format("%n", "");
		for (int val : numericOptions) {
			returnString += String.format("%-10s", val);
		}
		returnString += String.format("%-10s", specialDetails[0]);
		returnString += String.format("%-10s", specialDetails[1]);
		returnString += String.format("%-10s", fullHouse[0]);
		returnString += String.format("%-10s", straights[0]);
		returnString += String.format("%-10s", straights[1]);
		returnString += String.format("%-10s", specialDetails[4]);
		returnString += String.format("%-10s", specialDetails[3]);
		returnString += "\n///////////////////////////////////////////////\n ";
		return returnString;	
	};
	//returns [1's points - 6's points]
	public int[] checkNumericOptions(int[] arr) {
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
	public int[] getSpecialDetails(int[] numericPossiblities, int[] arr) {
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
	public int[] checkFullHouse(int[] ofAKindPossibilities) {
		if (ofAKindPossibilities[0] > 0 && ofAKindPossibilities[2] > 0) {
			return new int[] {25};
		}
		return new int[] {0};
	}
	//returns [small straight points, large straight points]
	public int[] checkStraight(int[] arr) {
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

