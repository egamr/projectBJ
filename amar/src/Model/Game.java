package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements buttonInterface {

	private int score;
	private static int round=0;
	private Deck deck = new Deck();
	private Hand playerHand=new Hand();
	private Hand dealerHand=new Hand();
	private int deckIndex = 0;
	private int flag=0;
	
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

	public void updateHandPlayer(int x ) {
		if(x==1 && flag==0){
			playerHand.setSum(11);
			flag++;
		}
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
	public void updateHandDealer(int x ) {
		if(x==11 || x==12 || x==13){
			 dealerHand.setSum(10);
		}
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

	public String[] createDealLogic() {
		playerHand=new Hand();
		
		dealerHand=new Hand();
		flag=0;
		shuffle(this.deck);
		String cards[] = new String[4];
		int i;
		for (i = 0; i < 2; ++i) {
			cards[i]=deck.getDeck().get(deckIndex);
			playerHand.setCards(cards[i]);
			int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
			updateHandPlayer(x);
			setdeckIndex(deckIndex+1);
		}
		for (i = 2; i < 4; ++i) {
			cards[i]=deck.getDeck().get(deckIndex);
			dealerHand.setCards(cards[i]);
			int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
			updateHandDealer(x);
			setdeckIndex(deckIndex+1);
		}
		setRound(round+1);
		return cards;

	}

	public String createHitLogic() {
		String card;
		card = deck.getDeck().get(deckIndex);
		int x= Integer.parseInt(deck.getDeck().get(deckIndex).replaceAll("[\\D]", ""));
		updateHandPlayer(x);
		playerHand.setCards(card);
		setdeckIndex(deckIndex+1);
     return card;
	}

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
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
	public void setdeckIndex(int x)
	{ 
		if(x>=0)
			if(x<52)
		this.deckIndex=x;
			else x=0;
	}

}
