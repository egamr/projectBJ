package Gui;

import java.awt.Button;
import java.awt.Color;
//import java.awt.Color;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Model.*;
import processing.core.*;

public class Thing extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<PImage> imgs;
	String[] cards = new String[5];
	String[] dcards = new String[2];
	String[] pcards = new String[2];
	public int flag1 = 0, flag = 0, numcards = 0, flag2 = 0, check = 0,
			runs = 0;
	int x, y, w, h, z, f, y1, z1, w1, h1, x1;
	int xspeed, yspeed, zspeed, x1speed, x2speed;
	PImage background, deck, deck2, dcard1, dcard3, dcard4, deck4;
	Image amar;
	PImage pcard1, pcard2, pcard5, pcard6, deck5, dcard5, dcard6, dcard7;
	Game game = new Game();
	Hand hand = new Hand();
	Hand dhand = new Hand();
	Label labe = new Label("Round:");
	// Button score = new Button();
	int value;
	boolean loop = true, loop1 = true;
	String stround;
	String stscore = "";

	public void setValue(int value) {
		this.value = value;
	}

	public void setup() {

		game = new Game();
		labe.setBounds(10, 32, 46, 14);
		size(600, 600);
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

		background = loadImage(getimg("Table.png"));
		background.resize(600, 600);

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
		game.setValue(value);
		stround = "" + game.getRound();

		stscore = "" + game.getScore();

	}

	public void draw() {
	

		if (loop1 == false) {
			noLoop();
			reset();

			if (game.getScore() > 100)
				background = loadImage(getimg("win.gif"));
			else
				background = loadImage(getimg("sad.jpg"));
			background.resize(600, 600);

			redraw();
		}

		background(background);
		image(deck2, 440, 280);

		Button deal = new Button("DEAL");
		Button hit = new Button("HIT");
		Button stand = new Button("STAND");
		// Button round = new Button("Round:  " + stround);

		if (loop) {

			// Color red = new Color(250, 0, 0);
			// Color d = new Color(0, 250, 0);

			// score = new Button("Score: " + stscore);
			// score.setName(("Score:   " + stscore));
			// score.setLocation(500, 100);
			// score.setBounds(500, 100, 100, 50);
			// score.setVisible(true);
			// this.add(score);
			// score.setBackground(d);

			// round.setLocation(500, 50);
			// round.setBounds(500, 50, 80, 50);
			// round.setVisible(true);
			// round.setBackground(red);
			// this.add(round);

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

			loop = false;
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
			
				if (loop1 == false) {
					JOptionPane
							.showMessageDialog(null,
									"you quit the game ! please start a new game if you want to play again");
					return;
				}
				stround = "" + game.getRound();
				stscore = "" + game.getScore();
				loop = true;
				if (flag == 0) {
					flag = 1;
					check = 0;

				} else if ((flag1 == 0) && (flag2 == 0))
					return;
				else {
					reset();
					flag = 0;

				}

				checkdeal();
				if (check == 1)
					showscore();

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
				if (loop1 == false) {
					JOptionPane
							.showMessageDialog(null,
									"you quit the game ! please start a new game if you want to play again");
					return;
				}
				if (game.getPlayerHand().getSum() > 21)
					return;

				if (flag != 1) {
					return;
				}
				if (flag2 != 0)
					return;

				if (flag1 < 2)

					flag1++;
				String c = null;
				if (flag1 == 1) {

					c = game.createHitLogic();

					pcard5 = loadImage(getimg(c + ".png"));
					pcard5.resize(80, 120);
					checkhit();

				}

				if ((x1 == 140) && (f > 140)) {

					pcard6 = loadImage(getimg(game.createHitLogic() + ".png"));
					pcard6.resize(80, 120);
					checkhit();
				}
				if (check == 1)
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
						dcard5 = loadImage(getimg(dealercards.get(2) + ".png"));
						dcard5.resize(80, 120);
						dcard1 = loadImage(getimg(dcards[1] + ".png"));
						dcard1.resize(80, 120);
						checkstand();

						flag2 = 3;
					}
					if (dealercards.size() == 4) {
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

		if (flag == 1) {

			x = x - xspeed;
			if (x <= 140)
				xspeed = 0;

			image(deck, x, 280);

			if (x == 140) {
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

		if (flag1 == 1) {
			x1 = x1 - x1speed;

			if (x1 <= 140)
				x1speed = 0;

			image(deck, x1, 280);

			if (x1 == 140) {

				image(pcard1, x, 280);
				image(pcard2, x + 18, 280);
				image(pcard5, x1 + 36, 280);
			}
		}

		if (flag1 == 2) {
			f = f - x2speed;
			if (f <= 140)
				x2speed = 0;

			image(deck5, f, 280);

			if (x1 == 140) {
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

		if (flag2 == 1) {
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);

		}
		if (flag2 == 2) {
			dcard1 = loadImage(getimg(dcards[1] + ".png"));
			dcard1.resize(80, 120);
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);

		}

		if (flag2 == 3) {
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);
			image(dcard5, 250 + 36, y);

		}
		if (flag2 == 4) {
			image(dcard1, 250, y);
			image(dcard4, 250 + 18, y);
			image(dcard5, 250 + 36, y);
			image(dcard6, 250 + 54, y);

		}
		if (flag2 == 5) {
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
	public String getimg(String x) {

		URL imgUrl = getClass().getClassLoader().getResource("pics/" + x);
		ImageIcon icon = new ImageIcon(imgUrl);

		return icon.toString();

	}

	public void reset() {

		runs++;
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

	public void checkdeal() {
		delay(1000);
		if (game.getPlayerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");
				// showscore();

				check = 1;
				flag2 = 2;

			}
			return;

		}

		if (game.getDealerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");
				// showscore();
				check = 1;
				flag2 = 2;

				return;
			}
		}

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	public void checkhit() {

		delay(1000);
		if (game.getPlayerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");
				// showscore();
				check = 1;
				flag2 = 2;

				return;
			}
		}

		if (game.getPlayerHand().getSum() > 21) {
			if (check == 1)
				return;
			else {

				JOptionPane.showMessageDialog(null,
						"**Busted** \n   You Loose!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");
				// showscore();
				check = 1;
				flag2 = 2;

				return;
			}
		}

	}

	// /////////////////////////////////////////
	// /////////////////////////////////////////
	// /////////////////////////////////////////
	public void checkstand() {
		delay(500);

		if (game.getDealerHand().getSum() > 21) {
			if (check == 1)
				return;
			else {

				JOptionPane.showMessageDialog(null,
						"**Dealer Busted** \n   You win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");
				// showscore();
				check = 1;

				return;
			}
		}

		if ((game.getDealerHand().getSum() > game.getPlayerHand().getSum())
				&& (game.getDealerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {

				JOptionPane.showMessageDialog(null,
						"**Bad luck** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");
				// showscore();
				check = 1;

				return;
			}
		}
		if ((game.getDealerHand().getSum() < game.getPlayerHand().getSum())
				&& (game.getPlayerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**congratulations** \n   you win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("p");
				// showscore();
				check = 1;

				return;
			}
		}
		if ((game.getDealerHand().getSum() == game.getPlayerHand().getSum())
				&& (game.getPlayerHand().getSum() < 21)) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**unlucky** \n  ! ! Draw The dealer win !!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");
				// showscore();
				check = 1;

				return;
			}
		}
		if (game.getDealerHand().getSum() == 21) {
			if (check == 1)
				return;
			else {
				JOptionPane.showMessageDialog(null,
						"**BlackJack** \n   Delaer win!");
				game.setValue(game.getPlayerHand().getSum());

				game.calculateScore("d");
				// showscore();
				check = 1;

				return;
			}
		}

	}

	private void showscore() {

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
			if (game.getScore() >= 100)
				JOptionPane.showMessageDialog(null, "Thank you for playing \n"
						+ winmessage);
			else
				JOptionPane.showMessageDialog(null, "Thank you for playing \n"
						+ losemessage);
			loop1 = false;
		} else
			reset();

	}

}
