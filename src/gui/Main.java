package gui;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.sql.SQLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/*
 *  @author Rio Karim
 * 
 */

public class Main {

	static JLabel logoImageAbc = new JLabel();

	public static Component logoImageAbc1;  //COMPONENT, ALLOWS ME TO USE THE LOGO IMAGE VARIABLE ACROSS ALL CLASSES

	JFrame frame; 

	//LAUNCH A JAVA WINDOW FRAME
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//CREATES AN EXECUTABLE APPLICATION
	public Main() {
		style();
		Logo(); //CALLING LOGO METHOD
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we)
		    { 
		    	chimeSound();
		        String exitButtons[] = {"Exit","Return"};
		        int exitAttempt = JOptionPane.showOptionDialog(null,"Are you sure you wish to exit?","Exit Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,exitButtons,exitButtons[1]);
		        if(exitAttempt == JOptionPane.YES_OPTION)
		        {
		        	clickSound();
		            System.exit(0);
		        }

		        else {
		        	clickSound();
		        }
		       
		    }
		});
		
	}
/*
	public void addLogo(){
	frame.getContentPane().add(logoImageAbc);
	}
	*/
	
	public void Logo(){

		//CODE TO CALL IMAGE LOGO
		
		ImageIcon imageIcon = new ImageIcon("src/img/Logo.png");  //IMAGE VARIABLE
		//CODE FOR RESIZE
		Image image = imageIcon.getImage(); //image icon to image
		Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back to image icon
		//CODE TO SET IMAGE
		logoImageAbc.setToolTipText("You can do it!"); //sets image tooltip to panel
		logoImageAbc.setIcon(imageIcon); //sets resized image as panel icon
		logoImageAbc.setBounds(40, 30, 50, 50); //sets position
		frame.getContentPane().add(logoImageAbc); //adds image panel to frame
		//addLogo();
	}
	
	//sound method
	public void clickSound(){
		try {
			String soundName = "src/img/click.wav";     //Wav file
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile()); //makes it into audio output
			Clip clip = AudioSystem.getClip(); //turns audio output into java clip
			clip.open(audioInputStream); //plays clip
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//sound method
	public void chimeSound(){
		try {
			String soundName = "src/img/chime.wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	//sound method
	public void wooshSound(){
		try {
			String soundName = "src/img/woosh.wav";    
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


	



	//CODE TO INITIALISE FRAME
	void style() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLabel logoMainMenu = new JLabel("MAIN MENU");
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(200, 30, 200, 50);
		frame.getContentPane().add(logoMainMenu);
		
		//QUIZ SETUP BUTTON
		JButton buttonQuizSetup = new JButton("Quiz Setup");
		buttonQuizSetup.setToolTipText("Click here to enter question setup and administrator settings.");
		buttonQuizSetup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonQuizSetup.setBackground(Color.WHITE);
		buttonQuizSetup.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        clickSound();
		    }
		});
		buttonQuizSetup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wooshSound();
				frame.dispose();
				Login loginConstructor = new Login();
				loginConstructor.setVisible(true);
			}
		});
		buttonQuizSetup.setBounds(200, 290, 200, 71);
		frame.getContentPane().add(buttonQuizSetup);
		
		//QUIZ PLAY BUTTON
		JButton buttonPlayQuiz = new JButton("Play Quiz");
		buttonPlayQuiz.setToolTipText("Click here to start the quiz!");
		buttonPlayQuiz.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonPlayQuiz.setBackground(Color.WHITE);
		buttonPlayQuiz.setBounds(200, 175, 200, 71);
		frame.getContentPane().add(buttonPlayQuiz);
		buttonPlayQuiz.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        clickSound();
		    }
		});
		buttonPlayQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				wooshSound();
				frame.dispose();
				try {
					TopicSelection topicSelectionConstructor = new TopicSelection();
					topicSelectionConstructor.setVisible(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

	}
}
