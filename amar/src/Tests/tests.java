package Tests;
import java.util.List;

import org.junit.Test;

import Model.Deck;
import Model.Game;


public class tests {

	Game game = new Game();
	@Test
	public void testShuffle() {
		Deck deck1 = new Deck();
	    Deck deck2 = new Deck();
	    game.shuffle(deck2);
	   
	    assert false == deck1.equals(deck2);
	}

	@Test
	public void testUpdateHandPlayer() {
		int sum = game.getPlayerHand().getSum();
		game.updateHandPlayer(5);
		int updatedSum = game.getPlayerHand().getSum();
		int diff = updatedSum - sum;
		
		assert true == (sum == (diff));
	}

	@Test
	public void testUpdateHandDealer() {
		int sum = game.getDealerHand().getSum();
		game.updateHandDealer(5);
		int updatedSum = game.getDealerHand().getSum();
		int diff = updatedSum - sum;
		
		assert true == (sum == (diff));
	}

	@Test
	public void testCreateHitLogic() {
		List<String> cards = game.getPlayerHand().getCards();
		String card = game.createHitLogic();
		List<String> updatedCards = game.getPlayerHand().getCards();
		
		assert true == (!cards.contains(card) && updatedCards.contains(card));
	}
	@Test
	public void testCalculateScore() {
		int newscore;
		game.setRound(0);
		game.setValue(23);
		game.setScore(0);
	    newscore= game.getScore()- 3*(game.getValue());
		game.calculateScore("d");
		assert(game.getScore() == newscore );
		game.calculateScore("d");
		newscore= game.getScore()+ 3*(game.getValue());
		assert(game.getScore() == newscore );
		//*********//
		game.setRound(1);
	    newscore= game.getScore()- 2*(game.getValue());
		game.calculateScore("d");
		assert(game.getScore() == newscore );
		assert(game.getScore() == newscore );
		game.calculateScore("d");
		newscore= game.getScore()+ 2*(game.getValue());
		assert(game.getScore() == newscore );
		
	
		
	}
}
