
public class ScoreBoard {
	private String[] possibleChoices = new String[] {"Ones","Twos","Threes","Fours","Fives","Sixes","Three of a kind", "Four of a kind", "Full House","Small Straight","Large Straight","Chance","Yahtzee"};
	private int[] scores = new int[13];
	
	public void displayChoices() {
		System.out.println("Possible choices are: ");
		for (int i = 0; i < scores.length; i ++) {
			if (scores[i] == 0) {
				System.out.println(possibleChoices[i]);
			}
		}
	}
	public void chooseChoice(int val) {
		int chosenIndex = TextIO.getlnInt();
		while(this.scores[chosenIndex] != 0) {
			System.out.println("You didnt choose an available slot!");
			System.out.print("Please select a valid choice!  ");
			chosenIndex = TextIO.getlnInt();
		}
		this.scores[chosenIndex] = val;
	}
}
