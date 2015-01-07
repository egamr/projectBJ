package Model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements buttonInterface {

	/**
	 * Game's score
	 */
	private int score=100;
	/**
	 * Game's round counter
	 */
	private int round=0;
	/**
	 * Deck
	 */
	private Deck deck = new Deck();
	/**
	 * player Hand
	 */
	private Hand playerHand=new Hand();
	/**
	 * dealer hand
	 */
	private Hand dealerHand=new Hand();
	/**
	 * deck index
	 */
	private int deckIndex = 0;
	/**
	 * flag - helper
	 */
	private int flag=0;
	/**
	 * the player sum, the value for calculating the score
	 */
	private int value;
	/**
	 * This method shuffle the deck cards by Swapping card's places 
	 * @param deck
	 */
	public void shuffle(Deck deck) {
		Random rnd = new Random();
		for (int i = deck.getDeck().size() - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
		
			// Swap
			String a = deck.getDeck().get(index);
			deck.getDeck().set(index, deck.getDeck().get(i));
			deck.setDeck(i, a);
		}
	}

	/**
	 * This method update the player hand
	 * call set sum to update the player sum
	 * @param x
	 */
	public void updateHandPlayer(int x ) {
		//if the player gets his first Ace, count as 11
		if(x==1 && flag==0){
			playerHand.setSum(11);
			flag++;
		}
		//the player gets another Ace it count as 1
		else if(x==1 && flag!=0){
			playerHand.setSum(1);
		}
		else if(x==10 || x==11 || x==12 || x==13){
			playerHand.setSum(10);
		}
		else{
			playerHand.setSum(x);
		}
	}
	/**
	 * This method update the dealer hand,
	 * call dealer hand set sum and calculate it as the card he gets
	 * @param x
	 */
	public void updateHandDealer(int x ) {
		//all of J,Q,K count as 10
		if(x==11 || x==12 || x==13){
			 dealerHand.setSum(10);
		}
		//if the Ace will make the dealer loss then it count as 1
		else if(x==1){
			if(dealerHand.getSum()+11 >21){
				dealerHand.setSum(1);
			}
			else{
				dealerHand.setSum(11);
			}
		}
		else 
	      dealerHand.setSum(x);
		}

	/**
	 * This method give two cards for the dealer and player in the beginning of the round
	 * call shuffle method
	 * call updateHandDealer & updatePlayerHand
	 * set the deckIndex
	 * @return cards array
	 */
	public String[] createDealLogic() {
		deckIndex = 0;
		playerHand=new Hand();
		dealerHand=new Hand();
		flag=0;
		shuffle(this.deck);
		String cards[] = new String[4];
		int i;
		//giving two cards for the player
		for (i = 0; i < 2; ++i) {
			cards[i]=deck.getDeck().get(deckIndex);
			playerHand.setCards(cards[i]);
			int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
			updateHandPlayer(x);
			setdeckIndex(deckIndex+1);
		}
		//giving next two cards for the dealer 
		for (i = 2; i < 4; ++i) {
			cards[i]=deck.getDeck().get(deckIndex);
			dealerHand.setCards(cards[i]);
			int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
			updateHandDealer(x);
			setdeckIndex(deckIndex+1);
		}
		//update the round counter
		setRound(round+1);
		return cards;

	}

	/**
	 * This method gives card for the player
	 * call updateHandPlayer (with the integer card value )
	 * set the deckIndex
	 * @return one card
	 */
	public String createHitLogic() {
		String card;
		card = deck.getDeck().get(deckIndex);
		int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
		updateHandPlayer(x);
		playerHand.setCards(card);
		setdeckIndex(deckIndex+1);
     return card;
	}

	/**
	 * when pressing in stand button the dealer get card till the sum is less than 17
	 * updateHandeDealer.
	 * set the deckIndex
	 * set the dealer cards
	 */
	public void createStandLogic() {
		
		List<String> cards = new ArrayList<String>();
		while(dealerHand.getSum()<17){
			int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
			updateHandDealer(x);
			String card=deck.getDeck().get(deckIndex);
			cards.add(card);
			dealerHand.setCards(card);
			setdeckIndex(deckIndex+1);
		}
	}
	/**
	 * 
	 * @return Integer, game's score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * set the score
	 * @param score
	 */
	public void setScore(int score) {
		this.score += score;
	}

	/**
	 * 
	 * @return integer, round 
	 */
	public int getRound() {
		return round;
	}

	/**
	 * set round value-static
	 * @param Round
	 */
	public void setRound(int Round) {
		round = Round;
	}

	/**
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * @param deck
	 *            the deck to set
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	/**
	 * @return the playerHand
	 */
	public Hand getPlayerHand() {
		return playerHand;
	}

	/**
	 * @param playerHand
	 * the playerHand to set
	 */
	public void setPlayerHand(Hand playerHand) {
		this.playerHand = playerHand;
	}

	/**
	 * @return the dealerHand
	 */
	public Hand getDealerHand() {
		return dealerHand;
	}

	/**
	 * @param dealerHand
	 *            the dealerHand to set
	 */
	public void setDealerHand(Hand dealerHand) {
		this.dealerHand = dealerHand;
	}
	
	/**
	 * set deckIndex
	 * @param x
	 */
	public void setdeckIndex(int x)
	{ 
		if(x>=0)
			if(x<52)
		this.deckIndex=x;
			else x=0;
	}
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	/**
	 * calculate the score for the player as the game rules
	 * if the round is odd multiple by 2
	 * if the round is even multiple by 3
	 * @param winner
	 */
	public void calculateScore(String winner){
		if(round%2==0){
			if(winner.equals("d")){
				setScore(-value*3);
			}
			else{
				if(winner.equals("p")){
					setScore(value*3);
				}
			}
		}
		else{
			if(round%2==1){
				if(winner.equals("d")){
					setScore(-value*2);
				}
				else{
					if(winner.equals("p")){
						setScore(value*2);
					}
				}
			}
		}
	}
}
