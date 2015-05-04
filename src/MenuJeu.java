import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class MenuJeu extends JFrame implements ActionListener{
	
	public String player_name = "Player 1";
	public String PLAY = "PLAY";
	public JButton play;
	public JLabel label;
	public JCheckBox musicbox = new JCheckBox("Soundtrack is ON", true);
	public JTextField player = new JTextField("Enter Player's Name...");
	public JSlider difficulty = new JSlider(0,30,10);
	public static boolean sound = true;
	public int dizzyness;
	public Image img;
	
	public MenuJeu(){
		
		this.setSize(1000,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setTitle("Main menu : Welcome " +player_name);
		this.setResizable(true);
		
		JPanel pan = new JPanel();
		
		label = new JLabel("The Drunken Mile");
		label.setFont(new Font("BlackBoard", Font.BOLD, 70));
		play = new JButton(PLAY);
		play.setFont(new Font("BlackBoard", Font.BOLD, 50));
		play.addActionListener(this);
		
			JPanel title = new JPanel(); //titre
		    title.setPreferredSize(new Dimension(1000, 200));
		    title.add(label);
		    
		    JPanel jouer = new JPanel(); // contient le jbutton play
		    jouer.setBackground(Color.PINK);
		    jouer.setPreferredSize(new Dimension(1000, 100));
		    jouer.setLayout(new GridLayout(1,3));
		    jouer.add(new JPanel());
		    jouer.add(play);
		    jouer.add(new JPanel());
		    
		   
		    JPanel highscore = new JPanel();
		    highscore.setBackground(Color.YELLOW);
		    highscore.setPreferredSize(new Dimension(500, 300 ));
		    
		    JPanel Settings = new JPanel();
		    Settings.setBackground(Color.BLACK);
		    Settings.setPreferredSize(new Dimension(500, 300));
		    Settings.setLayout(new GridLayout(4,2));
		    
		    // parametres modifiables dans le menu
		    
		    musicbox.addActionListener(this);
		    String settxt = "                       SETTINGS";
		    
		    player.addActionListener(this);
		    Settings.add(new JLabel(settxt)).setFont(new Font("Serif", Font.BOLD, 30));
		    Settings.add(musicbox);
		    Settings.add(player);
		    Settings.add(difficulty);
		    
		    JPanel espace1 = new JPanel();
		    espace1.setPreferredSize(new Dimension(1000, 50));
		    JPanel espace2 = new JPanel();
		    espace2.setPreferredSize(new Dimension(1000, 50));
		    
		    //Le conteneur principal
		    pan.setPreferredSize(new Dimension(1000, 700));

		    //On définit le layout manager
		    pan.setLayout(new GridBagLayout());
		    pan.setBackground(Color.WHITE);
		    
		    //L'objet servant à positionner les composants
		    GridBagConstraints gbc = new GridBagConstraints();
		    
		    //La taille en hauteur et en largeur
		    gbc.gridheight = 1;
		    gbc.gridwidth = 2;
		    gbc.weightx= 0.8;
		    gbc.weighty = 1.0;
		    gbc.fill = GridBagConstraints.BOTH;
		    
		    //On positionne la case de départ du composant ici le titre
		    gbc.gridx = 0;
		    gbc.gridy = 0;
		    pan.add(espace1, gbc);
		    
		    gbc.gridx = 0;
		    gbc.gridy = 1;
		    pan.add(title, gbc);
		    
		    //---------------------------------------------
		    gbc.gridx = 0;
		    gbc.gridy = 2;
		    gbc.weighty = 0.2;
		    
		    pan.add(espace1, gbc);
		    //---------------------------------------------
		    gbc.gridx = 0;
		    gbc.gridy =3;
		    gbc.weighty = 0.6;
		    
		    pan.add(jouer, gbc);        
		    //---------------------------------------------
		    //Cette instruction informe le layout que c'est une fin de ligne
		    gbc.gridx = 0;
		    gbc.gridy = 4;
		    gbc.weighty = 0.2;
		    pan.add(espace2, gbc);
		    
		    //---------------------------------------------
		    gbc.gridwidth = 1;
		    gbc.gridheight = GridBagConstraints.REMAINDER;
		    gbc.gridx = 0;
		    gbc.gridy = 5;
		    gbc.weighty = 1.0;
		    pan.add(highscore, gbc);
		    
		    
		    gbc.gridwidth = GridBagConstraints.REMAINDER;
		    gbc.gridheight = GridBagConstraints.REMAINDER;
		    gbc.gridx = GridBagConstraints.RELATIVE;
		    gbc.gridy = 5;
		    gbc.weighty = 1.0;
		    pan.add(Settings, gbc);
		    
		    
		    //---------------------------------------------
		    //On ajoute le conteneur
		    this.setContentPane(pan);
		    this.setVisible(true);
		    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == play){
			
			new FenetreDrunk();
			this.dispose();
		}
		
		if(e.getSource() == player){
			player_name = player.getText();
			this.setTitle("Main menu : Welcome "+player_name);
		}
		
		if(e.getSource() == musicbox){
			sound = !sound;
			if(sound){
				musicbox.setText("Soundtrack is ON ");
			} else{
				musicbox.setText("Soundtrack is OFF ");
			}
		}
		
	}

}
