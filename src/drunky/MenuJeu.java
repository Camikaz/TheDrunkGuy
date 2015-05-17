package drunky;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


@SuppressWarnings("serial")
public class MenuJeu extends JFrame implements ActionListener{
	
	
	public String player_name = "Player 1";
	public String PLAY = "PLAY";
	public JButton play;
	public JLabel label;
	public JLabel text_file;
	public JCheckBox musicbox = new JCheckBox("Soundtrack is ON", true);
	public JTextField player = new JTextField("Enter Player's Name...");
	public JSlider difficulty = new JSlider(0,30,10);
	public static boolean sound = true;
	public int dizzyness;

	
	public MenuJeu(){
		
		
		this.setSize(1000,700);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setTitle("Main menu : Welcome " +player_name);
		this.setResizable(true);
		
		panel pan = new panel();
		
		label = new JLabel("The Drunken Mile");
		label.setFont(new Font("BlackBoard", Font.BOLD, 60));
		play = new bouton("PLAY");
		play.addActionListener(this);
		play.setOpaque(false);
		play.repaint();
		
		JPanel title = new JPanel(); //titre
		title.add(label);
		title.setOpaque(false);
		
		Font police = new Font("Rockwell",Font.BOLD,25);
		text_file = new JLabel();
		text_file.setFont(police);
		OpenFile();
		JPanel highscore = new JPanel();
		highscore.setLayout(new GridLayout(2,1));
		highscore.setBackground(Color.GREEN);
		highscore.add(new JLabel("HIGHSCORE"));
		highscore.add(text_file);
		highscore.setOpaque(false);
		    
	    JPanel settings = new JPanel();
	    settings.setBackground(Color.CYAN);
	    settings.setOpaque(false);
	    settings.setLayout(new GridLayout(4,2));
		
		// parametres modifiables dans le menu
		    
		musicbox.addActionListener(this);
	    String settxt = "SETTINGS";
		    
	    player.addActionListener(this);
	    musicbox.setOpaque(false);
	    player.setOpaque(false);
	    difficulty.setOpaque(false);
	    settings.add(new JLabel(settxt)).setFont(new Font("Serif", Font.BOLD, 30));
	    settings.add(musicbox);
	    settings.add(player);
	    settings.add(difficulty);
		
	    
	    pan.setLayout(new GridLayout(3,1));
	    
	    
	    JPanel[] espace = new JPanel[20];
	    for(int i = 0; i< 20; i++){
	    	espace[i] = new JPanel();
	    	JLabel emptylabel = new JLabel("");
	    	espace[i].add(emptylabel);
	    	espace[i].setOpaque(false);
	    	espace[i].setBackground(getBackground());
	    }
	   
	   
		JPanel firstpan = new JPanel();
		firstpan.setLayout(new GridLayout(3,1));
		firstpan.setOpaque(false);
		firstpan.add(espace[0]);
		firstpan.add(title);
		firstpan.add(espace[1]);
		pan.add(firstpan);
		
		JPanel secondpan = new JPanel();
		secondpan.setLayout(new GridLayout(1,3));
		secondpan.setOpaque(false);
	   	secondpan.add(espace[2]);
		secondpan.add(play);
		secondpan.add(espace[3]);
		pan.add(secondpan);
		
		JPanel thirdpan = new JPanel();
		thirdpan.setLayout(new GridLayout(1,2));
		thirdpan.setOpaque(false);
	   	thirdpan.add(highscore);
		thirdpan.add(settings);
		pan.add(thirdpan);
		
		//---------------------------------------------
	    //On ajoute le conteneur
		
		this.add(pan);
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
	
	public void OpenFile() {
	
		BufferedReader br = null;
		String sCurrentLine = "Error while loading...";
		File txt = new File("C:\\Users\\Alest\\git\\TheDrunkGuy\\src\\Highscore_memory.txt");
		String totaltext = "<html>";
		
		try {
 
			br = new BufferedReader(new FileReader(txt));
 
			while ((sCurrentLine = br.readLine()) != null) {
				totaltext = totaltext + sCurrentLine + "<br>";
			}
			totaltext = totaltext + "</html>";
			text_file.setText(totaltext);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
