package Gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Model.*;
import processing.core.*;

public class Thing extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] cards = new String[5];
	String[] dcards = new String[2];
	String[] pcards = new String[2];
	public int flag1 = 0, flag = 0, numcards = 0, flag2 = 0, check = 0;

	int x, y, w, h, z, f, y1, z1, w1, h1, x1;
	int xspeed, yspeed, zspeed, x1speed, x2speed;
	PImage background, deck, deck2, dcard1, dcard3, dcard4, deck4;
	PImage pcard1, pcard2, pcard5, pcard6, deck5, dcard5, dcard6, dcard7;
	Game game = new Game();
	Hand hand = new Hand();
	Hand dhand = new Hand();

	int value;
	boolean loop = true, loop1 = true;

	AudioClip win;

	public void setValue(int value) {
		this.value = value;
	}

	public void setup() {

		game = new Game();// the game from the backend

		size(600, 600);// the size of the screen
		f = x = x1 = 440;// coordinate x of the cards we will use
		y = y1 = 280;
		z = z1 = 440;
		w = w1 = 60;
		h = h1 = 60;
		xspeed = x1speed = x2speed = 3;// the speed of the cards movement on
										// coordinate x
		yspeed = -3;// the speed of the cards movement on coordinate y
		zspeed = 3;
		cards = (game.createDealLogic());// call the deal logic have the first 4
											// cards
		// put the cards on the hands of each player

		pcards[0] = cards[0];
		pcards[1] = cards[1];
		dcards[0] = cards[2];
		dcards[1] = cards[3];

		background = loadImage(getimg("Table.png"));// set the background
		background.resize(600, 600);

		deck = loadImage(getimg("deck.jpg"));// set the image of the deck
		deck.resize(80, 120);

		pcard1 = loadImage(getimg(pcards[numcards++] + ".png"));// load image of
																// the first
																// card on the
																// player hand
		pcard1.resize(80, 120);

		deck2 = loadImage(getimg("deck.jpg"));
		deck2.resize(80, 120);

		pcard2 = loadImage(getimg(pcards[numcards++] + ".png"));// load player
																// second card
		pcard2.resize(80, 120);

		dcard1 = loadImage(getimg("deck.jpg"));// the first card of the dealer
												// is coverd
		dcard1.resize(80, 120);

		dcard3 = loadImage(getimg("deck.jpg"));
		dcard3.resize(80, 120);

		dcard4 = loadImage(getimg(dcards[0] + ".png"));// the second card of the
														// dealer
		dcard4.resize(80, 120);

		deck4 = loadImage(getimg("deck.jpg"));
		deck4.resize(80, 120);

		deck5 = loadImage(getimg("deck.jpg"));
		deck5.resize(80, 120);
		game.setValue(value);

		shufflesound();
	}

	public void draw() {

		if (loop1 == false) // if the player end the game then loop1=false
		{
			noLoop();// stop drawing
			reset();// return the cards to the deck

			if (game.getScore() > 100) {
				background = loadImage(getimg("win.gif"));// put win image as
															// background
				congsound();// play cong sound
			} else {
				background = loadImage(getimg("sad.jpg"));// put lose image as
															// background
				losesound();// play wawawa sound
			}
			background.resize(600, 600);

			redraw();
		}

		background(background);// load the table in the background
		image(deck2, 440, 280);

		Button deal = new Button("DEAL");
		Button hit = new Button("HIT");
		Button stand = new Button("STAND");

		if (loop)// this code is running just once

		{
			deal.setLocation(400, 500);
			deal.setBounds(400, 500, 100, 50);
			deal.setVisible(true);
			this.add(deal);

			stand.setLocation(400, 500);
			stand.setBounds(300, 500, 100, 50);
			stand.setVisible(true);
			this.add(stand);

			hit.setLocation(400, 500);
			hit.setBounds(200, 500, 100, 50);
			hit.setVisible(true);
			this.add(hit);

			loop = false; // the next time do not run this code
		}

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////

		/*
		 * Deal button oop singleton :D we want the deal button to act one time
		 * so we change his value to be 1
		 */
		deal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (loop1 == false)// if the player exit the game and trying to
									// hit deal
				{
					JOptionPane
							.showMessageDialog(null,
									"you quit the game ! please start a new game if you want to play again");
					return;
				}

				loop = true;
				if (flag == 0) // the first time deal pressed put the flag to 1
				{
					dealsound();// play sound of dealing cards
					flag = 1;// the round beggin
					check = 0;// the flag of the check functions return to zero
								// to let the functions work

				} else // the round had began
				if ((check == 0))// no deal or hit button clicked so return
					return;
				else {
					reset();
					flag = 0;

				}

				checkdeal();// check if one of the players had black jack 21
				if (check == 1)// if one has 21
					showscore();// call the show score

			}
		});

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////

		/*
		 * HIT Button we claimed that the player will have no more than 4 cards
		 * in his hand before he exceed the 21 and we added the flags from the
		 * deal button and the stand to make the button disabled after stand and
		 * enable after deal
		 */
		hit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				hitesound();
				if (loop1 == false) {
					JOptionPane
							.showMessageDialog(null,
									"you quit the game ! please start a new game if you want to play again");
					return;
				}
				if (game.getPlayerHand().getSum() > 21)
					return;

				if (flag != 1) {// the deal button not pressed
					return;
				}
				if (flag2 != 0)// the stand button pressed so cant hit
					return;

				if (flag1 < 2)

					flag1++;
				String c = null;
				if (flag1 == 1) {// add the first card

					c = game.createHitLogic();

					pcard5 = loadImage(getimg(c + ".png"));
					pcard5.resize(80, 120);
					checkhit();

				}

				if ((x1 == 140) && (f > 140)) {// the card moved and arrived the
												// player hand then show the
												// card

					pcard6 = loadImage(getimg(game.createHitLogic() + ".png"));
					pcard6.resize(80, 120);
					checkhit();// call the check function
				}
				if (check == 1)// if he had 21 or more call the showscore
								// function
					showscore();
			}
		});

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////

		/**
		 * Stand button function call the stand function and check how many
		 * cards the dealer have and fill the cards
		 * 
		 * 
		 * */

		stand.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (loop1 == false) {
					JOptionPane
							.showMessageDialog(null,
									"you quit the game ! please start a new game if you want to play again");
					return;
				}
				if (flag == 0)
					return;

				Hand dealerhand;
				List<String> dealercards;
				dcard1 = loadImage(getimg(dcards[1] + ".png"));
				dcard1.resize(80, 120);
				if (flag2 == 0) {
					flag2++;
					game.createStandLogic();

					dealerhand = game.getDealerHand();
					dealercards = dealerhand.getCards();
					if (dealercards.size() == 2) {
						checkstand();
						flag2 = 2;

					}
					if (dealercards.size() == 3) {
						hitesound();
						dcard5 = loadImage(getimg(dealercards.get(2) + ".png"));
						dcard5.resize(80, 120);
						dcard1 = loadImage(getimg(dcards[1] + ".png"));
						dcard1.resize(80, 120);
						checkstand();

						flag2 = 3;
					}
					if (dealercards.size() == 4) {
						hitesound();
						hitesound();
						dcard1 = loadImage(getimg(dcards[1] + ".png"));
						dcard1.resize(80, 120);
						dcard5 = loadImage(getimg(dealercards.get(2) + ".png"));
						dcard5.resize(80, 120);
						dcard6 = loadImage(getimg(dealercards.get(3) + ".png"));
						dcard6.resize(80, 120);
						checkstand();
						flag2 = 4;
					}

					if (dealercards.size() == 5) {
						hitesound();
						hitesound();
						hitesound();
						dcard1 = loadImage(getimg(dcards[1] + ".png"));
						dcard1.resize(80, 120);
						dcard5 = loadImage(getimg(dealercards.get(2) + ".png"));
						dcard5.resize(80, 120);
						dcard6 = loadImage(getimg(dealercards.get(3) + ".png"));
						dcard6.resize(80, 120);
						dcard7 = loadImage(getimg(dealercards.get(4) + ".png"));
						dcard7.resize(80, 120);
						checkstand();
					}
				}
				if (check == 1)
					showscore();

			}
		});

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////

		if (flag == 1)// the deal button pressed
		{

			x = x - xspeed;// move the cards to the players hands
			if (x <= 140)
				xspeed = 0;// stop moving

			image(deck, x, 280);

			if (x == 140) {// show cards
				image(pcard1, x, 280);
				image(pcard2, x + 18, 280);
			}

			y = y + yspeed;
			z = z - zspeed;

			if (y <= 100)
				yspeed = 0;
			if (z <= 250)
				zspeed = 0;

			if (flag2 == 0) {
				image(dcard1, z, y);
				if (y <= 100 && z <= 250)
					image(dcard4, z + 18, y);
			}
		}

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// *the hit pressed*//
		if (flag1 == 1) {// the hit button pressed
			x1 = x1 - x1speed;// move card to the player hand

			if (x1 <= 140)
				x1speed = 0;// stop the card move

			image(deck, x1, 280);

			if (x1 == 140) {// flip the card

				image(pcard1, x, 280);
				image(pcard2, x + 18, 280);
				image(pcard5, x1 + 36, 280);
			}
		}

		if (flag1 == 2) {// the hit card pressed again
			f = f - x2speed;
			if (f <= 140)
				x2speed = 0;

			image(deck5, f, 280);

			if (x1 == 140) {// flip the second card
				image(pcard1, x, 280);
				image(pcard2, x + 18, 280);
				image(pcard5, x1 + 36, 280);
			}

			if (f == 140)
				image(pcard6, x1 + 54, 280);

		}

		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// /////////////////////////////////////////
		// * the stand pressed*//
		if (flag2 == 1) {
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);

		}
		if (flag2 == 2) {// the dealer has more than 17 show the cards and the
							// stand pressed
			dcard1 = loadImage(getimg(dcards[1] + ".png"));
			dcard1.resize(80, 120);
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);

		}

		if (flag2 == 3) {// the dealer has 3 cards show them
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);
			image(dcard5, 250 + 36, y);

		}
		if (flag2 == 4) {// the dealer has 4 cards show them
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);
			image(dcard5, 250 + 36, y);
			image(dcard6, 250 + 54, y);

		}
		if (flag2 == 5) {// the dealer has 5 cards sho them
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);
			image(dcard5, 250 + 36, y);
			image(dcard6, 250 + 54, y);
			image(dcard7, 250 + 72, y);

		}

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// this function take the card name and return the suitable image

	public String getimg(String x) {

		URL imgUrl = getClass().getClassLoader().getResource("pics/" + x);
		ImageIcon icon = new ImageIcon(imgUrl);

		return icon.toString();

	}

	// ////////////////////////////////////////////////////
	// //////////////////////////////////////////////////
	// this function reset all the cards to the start point
	public void reset() {
		if (loop1 == true)

			shufflesound();

		flag = flag1 = flag2 = 0;
		numcards = 0;
		f = x = x1 = 440;
		y = y1 = 280;
		z = z1 = 440;
		w = w1 = 60;
		h = h1 = 60;
		xspeed = x1speed = x2speed = 3;
		yspeed = -3;
		zspeed = 3;

		cards = (game.createDealLogic());
		pcards[0] = cards[0];
		pcards[1] = cards[1];
		dcards[0] = cards[2];
		dcards[1] = cards[3];
		deck = loadImage(getimg("deck.jpg"));
		deck.resize(80, 120);

		pcard1 = loadImage(getimg(pcards[numcards++] + ".png"));
		pcard1.resize(80, 120);

		deck2 = loadImage(getimg("deck.jpg"));
		deck2.resize(80, 120);

		pcard2 = loadImage(getimg(pcards[numcards++] + ".png"));
		pcard2.resize(80, 120);

		dcard1 = loadImage(getimg("deck.jpg"));
		dcard1.resize(80, 120);

		dcard3 = loadImage(getimg("deck.jpg"));
		dcard3.resize(80, 120);

		dcard4 = loadImage(getimg(dcards[0] + ".png"));
		dcard4.resize(80, 120);

		deck4 = loadImage(getimg("deck.jpg"));
		deck4.resize(80, 120);

		deck5 = loadImage(getimg("deck.jpg"));
		deck5.resize(80, 120);

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// *check if any one win after the dealing *//
	public void checkdeal() {
		delay(1000);
		if (game.getPlayerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				winsound();
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");

				check = 1;
				flag2 = 2;

			}
			return;

		}

		if (game.getDealerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				failsound();
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");

				check = 1;
				flag2 = 2;

				return;
			}
		}

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// *check if the player has 21 or more after the hit click*//

	public void checkhit() {

		delay(1000);
		if (game.getPlayerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				winsound();
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");

				check = 1;
				flag2 = 2;

				return;
			}
		}

		if (game.getPlayerHand().getSum() > 21) {
			if (check == 1)
				return;
			else {
				failsound();
				JOptionPane.showMessageDialog(null,
						"**Busted** \n   You Loose!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");

				check = 1;
				flag2 = 2;

				return;
			}
		}

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// ** after the stand button pressed check how win **//

	public void checkstand() {
		delay(500);

		if (game.getDealerHand().getSum() > 21) {
			if (check == 1)
				return;
			else {
				winsound();
				JOptionPane.showMessageDialog(null,
						"**Dealer Busted** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");

				check = 1;

				return;
			}
		}

		if ((game.getDealerHand().getSum() > game.getPlayerHand().getSum())
				&& (game.getDealerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {
				failsound();
				JOptionPane.showMessageDialog(null,
						"**Bad luck** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");

				check = 1;

				return;
			}
		}
		if ((game.getDealerHand().getSum() < game.getPlayerHand().getSum())
				&& (game.getPlayerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {
				winsound();
				JOptionPane.showMessageDialog(null,
						"**congratulations** \n   you win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");
				check = 1;

				return;
			}
		}
		if ((game.getDealerHand().getSum() == game.getPlayerHand().getSum())
				&& (game.getPlayerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {
				failsound();
				JOptionPane.showMessageDialog(null,
						"**unlucky** \n  ! ! Draw The dealer win !!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");

				check = 1;

				return;
			}
		}
		if (game.getDealerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				failsound();
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");

				check = 1;

				return;
			}
		}
		if (game.getDealerHand().getSum() < 17) {
			if (check == 1)
				return;
			else {
				reset();
			}
		}

	}

	// **function that show a message with the score and the round number and
	// ask the player if he want to continue the game
	private void showscore() {
		// ////////////////////

		// ///////////////////////
		String winmessage = "Congratulations you win : "
				+ (game.getScore() - 100 + " $");
		String losemessage = "Ooooh you just lost :"
				+ (100 - game.getScore() + " $");
		String message = "your score is : " + game.getScore() + "\n "
				+ "next round is round number : " + (game.getRound() + 1)
				+ " continue?";

		String title = "Continue Playing?";

		int reply = JOptionPane.showConfirmDialog(null, message, title,
				JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.NO_OPTION) {
			if (game.getScore() >= 100) {

				JOptionPane.showMessageDialog(null, "Thank you for playing \n"
						+ winmessage);
			} else
				JOptionPane.showMessageDialog(null, "Thank you for playing \n"
						+ losemessage);
			loop1 = false;
		} else
			reset();

	}

	// ** functions for the sound**//

	public void winsound() {

		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/cheer.wav");
		if (urlSound == null) {
			return;
		}
		win = Applet.newAudioClip(urlSound);

		win.play();

	}

	public void losesound() {
		AudioClip lose;

		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/lose.wav");
		if (urlSound == null) {
			return;
		}
		lose = Applet.newAudioClip(urlSound);

		lose.play();

	}

	public void congsound() {
		AudioClip cong;

		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/cong.wav");
		if (urlSound == null) {
			return;
		}
		cong = Applet.newAudioClip(urlSound);

		cong.play();

	}

	public void dealsound() {
		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/deal.wav");
		AudioClip deal = Applet.newAudioClip(urlSound);
		deal.play();
	}

	public void hitesound() {
		URL urlSound = getClass().getClassLoader()
				.getResource("sounds/hit.wav");
		AudioClip hit = Applet.newAudioClip(urlSound);
		hit.play();
	}

	public void failsound() {
		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/fail.wav");
		AudioClip fail = Applet.newAudioClip(urlSound);
		fail.play();
	}

	public void shufflesound() {
		URL urlSound = getClass().getClassLoader().getResource(
				"sounds/shuffle.wav");
		AudioClip shuffl = Applet.newAudioClip(urlSound);
		shuffl.play();
	}
}
