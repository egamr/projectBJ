package Model;
import java.util.ArrayList;

public class Deck {

	/**
	 * Array for the deck cards
	 */
	private ArrayList<String> deck = new ArrayList<String>();
	/**
	 * Deck's Constructor
	 * initiate the cards in the deck
	 */
	public Deck() {
		int i;
		int j=1;
		for (i = 0; i < 13; ++i) {
			this.deck.add(i, j + "H");
			j++;
		}
		j = 1;
		for (i = 13; i < 26; ++i) {
			this.deck.add(i, j + "C");
			j++;
		}
		j = 1;
		for (i = 26; i < 39; ++i) {
			this.deck.add(i, j + "D");
			j++;
		}
		j = 1;
		for (i = 39; i < 52; ++i) {
			this.deck.add(i, j + "S");
			j++;
		}

	}

	/**
	 * @return deck-array
	 */
	public ArrayList<String> getDeck() {
		return deck;
	}

	/**
	 * @param index
	 * @param card
	 */
	public void setDeck(int index,String card) {
		this.deck.set(index, card);
	}

	}
