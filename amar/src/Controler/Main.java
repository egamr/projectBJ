package Controler;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Gui.Thing;
import java.awt.Color;
import java.awt.Font;

public class Main extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel panel;
	private Thing game;
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmNewgame;
	private JMenuItem mntmExit;
	private JMenuItem mntmHelp;
	private JMenu mnHelop;
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		setTitle("BlackJack Game");
		setBackground(new Color(0, 128, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 600, 700);
		contentPane = new JPanel();
		contentPane.setForeground(Color.GREEN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		panel = new JPanel();
		panel.setBounds(0, 68, 584, 599);
	
	
		game=new Thing();   //creat a new game
		panel.add(game); //adding the game to the panel
		game.start(); 
		game.init();
		
		contentPane.add(panel);
		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 774, 21);
		contentPane.add(menuBar);
		
		mnOptions = new JMenu("Options");
		mnOptions.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
		mnOptions.setForeground(new Color(0, 128, 0));
		menuBar.add(mnOptions);
		
		mntmNewgame = new JMenuItem("NewGame");
		mntmNewgame.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		mntmNewgame.setForeground(Color.BLACK);
		mntmNewgame.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mousePressed(MouseEvent e) {
			 
				if(game.loop1==true) //if loop1=true that means that the player still playing
				{
				int reply = JOptionPane.showConfirmDialog(null, "are you sure you want to start a new game?", "New Game?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION) {
					return; //do nothing
				}else
				{
			        panel.remove(game);//remove the game to avoid nullpointer exceptions while creating new game
			    	game=new Thing();
			    	panel.add(game);
			    	game.start();
				game.init();
					
				}
				}
				else
				if(game==null)//no game created yet
				{
				game=new Thing();	
			
				panel.add(game);
				game.start();
				game.init();
				}
			    else
			    {
			    	
			    	panel.remove(game);
			    	game=new Thing();
			    
			    	panel.add(game);
			    	game.start();
				game.init();
			    	
			    	
			    	
			    	
			    }
			
			}
		});
		mnOptions.add(mntmNewgame);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		mntmExit.addMouseListener(new MouseAdapter()//action lestenr for the exit from the menu bar
		{
			@Override
			public void mousePressed(MouseEvent e) {
				int reply = JOptionPane.showConfirmDialog(null, "are you sure you want to Exit?", "Exit?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.NO_OPTION) {
					return; //do nothing
				}else
				{
				
				dispose();//clos the window
			}}
		});
		mnOptions.add(mntmExit);
		
		mnHelop = new JMenu("Help");
		mnHelop.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		mnHelop.setForeground(new Color(0, 128, 0));
		menuBar.add(mnHelop);
		
		mntmHelp = new JMenuItem("Help");
		mnHelop.add(mntmHelp);
		mntmHelp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				String help="Welcome tho our game \n "
						+ "in this game you will compete against the dealer and try to beat him and win"+
			  "\n every round you will take 2 cards and your goal is to has sum of 21 \n"
			  + "if you had less than 21 you should had more than the dealer hand to win\n if you want more cards you can press the hit button\n"
			  + "if you confused with the sum you have you click the stand and then \nthe dealer will take cards until he had 17 or more then every one show his cards\n and one player win"
			  + "if the round is odd you will win/lose double of the value of your cards if its not you will get trible !\n press deal to begin new round \n Good luck   " ;
				JOptionPane.showMessageDialog(null, help);
			}
		});
		mntmHelp.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
	}
}

