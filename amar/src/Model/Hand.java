package Model;
import java.util.ArrayList;
import java.util.List;

public class Hand {

	/**
	 * hand's sum
	 */
	private int sum;
	/**
	 * hand cards- array
	 */
	private List<String> cards;
	/**
	 * hand constructor
	 */
    public Hand() {
     this.sum = 0;
     this.cards = new ArrayList<String>() ;
    }
    /**
     * @return integer , hand sum
     */
	public int getSum() {
		return sum;
	}
	/**
	 * the sum to set
	 * @param sum
	 */
	public void setSum(int sum) {
		 this.sum+=sum;
	}

	/**
	 * 
	 * @return List,cards
	 */
	public List<String> getCards() {
		return cards;
	}

	/**
	 * array cards to set
	 * @param cards
	 */
	public void setCards(String cards) {
		this.cards.add(cards);
	}
}
