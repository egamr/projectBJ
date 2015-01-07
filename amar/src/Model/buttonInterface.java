package Model;

public interface buttonInterface {

	/**
	 * This method give two cards for the dealer and player in the beginning of the round
	 * call shuffle method
	 * call updateHandDealer & updatePlayerHand
	 * set the deckIndex
	 * @return cards array
	 */
	public String[] createDealLogic();

	/**
	 * This method gives card for the player
	 * call updateHandPlayer (with the integer card value )
	 * set the deckIndex
	 * @return one card
	 */
	public String createHitLogic();

	/**
	 * when pressing in stand button the dealer get card till the sum is less than 17
	 * updateHandeDealer.
	 * set the deckIndex
	 * set the dealer cards
	 */
	public void createStandLogic();
}
