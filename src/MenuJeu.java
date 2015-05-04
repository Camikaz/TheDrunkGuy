import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;


public class MenuJeu extends JFrame {
	
	public String player_name = "Player 1";
	public String PLAY = "PLAY";
	public JButton play;
	public JLabel label;
	
	public MenuJeu(){
		
		this.setSize(1000,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setTitle("Main menu : Welcome " +player_name);
		this.setResizable(false);
		
		JPanel pan = new JPanel();
		
		label = new JLabel("The Drunken Mile");
		label.setFont(new Font("BlackBoard", Font.BOLD, 70));
		
		play = new JButton(PLAY);
		
			JPanel title = new JPanel(); //titre
		    title.setBackground(Color.WHITE);
		    title.setPreferredSize(new Dimension(1000, 200));
		    title.add(label);
		    
		    JPanel jouer = new JPanel(); // contient le jbutton play
		    jouer.setBackground(Color.PINK);
		    jouer.setPreferredSize(new Dimension(1000, 100));
		    
		    JPanel highscore = new JPanel();
		    highscore.setBackground(Color.YELLOW);
		    highscore.setPreferredSize(new Dimension(500, 300 ));
		    
		    JPanel Settings = new JPanel();
		    Settings.setBackground(Color.BLACK);
		    Settings.setPreferredSize(new Dimension(500, 300));
		    
		    JPanel espace1 = new JPanel();
		    espace1.setBackground(Color.RED);
		    espace1.setPreferredSize(new Dimension(1000, 50));
		    JPanel espace2 = new JPanel();
		    espace2.setBackground(Color.GREEN);
		    espace2.setPreferredSize(new Dimension(1000, 50));
		    
		    //Le conteneur principal
		    pan.setPreferredSize(new Dimension(1000, 700));
		    pan.setBackground(Color.LIGHT_GRAY);
		    //On définit le layout manager
		    pan.setLayout(new GridBagLayout());
		    
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
		    pan.add(title, gbc);
		    
		    //---------------------------------------------
		    gbc.gridx = 0;
		    gbc.gridy = 1;
		    gbc.weighty = 0.2;
		    
		    pan.add(espace1, gbc);
		    //---------------------------------------------
		    gbc.gridx = 0;
		    gbc.gridy =2;
		    gbc.weighty = 0.6;
		    
		    pan.add(jouer, gbc);        
		    //---------------------------------------------
		    //Cette instruction informe le layout que c'est une fin de ligne
		    gbc.gridx = 0;
		    gbc.gridy = 3;
		    gbc.weighty = 0.2;
		    pan.add(espace2, gbc);
		    
		    //---------------------------------------------
		    gbc.gridwidth = 1;
		    gbc.gridheight = GridBagConstraints.REMAINDER;
		    gbc.gridx = 0;
		    gbc.gridy = 4;
		    gbc.weighty = 1.0;
		    pan.add(highscore, gbc);
		    
		    
		    gbc.gridwidth = GridBagConstraints.REMAINDER;
		    gbc.gridheight = GridBagConstraints.REMAINDER;
		    gbc.gridx = GridBagConstraints.RELATIVE;
		    gbc.gridy = 4;
		    gbc.weighty = 1.0;
		    pan.add(Settings, gbc);
		    
		    
		    //---------------------------------------------
		    //On ajoute le conteneur
		    this.setContentPane(pan);
		    this.setVisible(true);
		    
	}

}
