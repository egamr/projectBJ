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

public class Home extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int value=0;
	private JPanel contentPane;
	private JPanel panel;
	private Thing game;
	private JMenuBar menuBar;
	private JMenu mnOptions;
	private JMenuItem mntmNewgame;
	private JMenuItem mntmExit;
	/*
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
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
	public Home() {
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
				dispose();//clos the window
			}
		});
		mnOptions.add(mntmExit);
	}
}
