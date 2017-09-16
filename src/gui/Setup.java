package gui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


/*
 *  @author Rio Karim
 * 
 */

@SuppressWarnings("serial")
public class Setup extends JFrame {

	JPanel contentPane;

	/**
	 * Launch the application window.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Setup contentPane = new Setup();
					contentPane.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Main MainConstructor = new Main();  //CONSTRUCTOR FOR MAIN CLASS TO CALL SOUND FILES AND LOGO
	public Setup() {
		style();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we)
		    { 
		        String exitButtons[] = {"Exit","Return"};
		        MainConstructor.chimeSound();
		        int exitAttempt = JOptionPane.showOptionDialog(null,"Are you sure you wish to exit?","Exit Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,exitButtons,exitButtons[1]);
		        if(exitAttempt == JOptionPane.YES_OPTION)
		        {	
			        MainConstructor.clickSound();
		            System.exit(0);
		        }
		    }
		});
}
	
	public void style() {

		setBounds(100, 100, 600, 500); //CREATING CONTENTPANE
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		MainConstructor.Logo(); //CALLING LOGO FROM MAIN CLASS
		getContentPane().add(Main.logoImageAbc); //ADDING TO CONTENT PAIN
		contentPane.setLayout(null);
		
		//CONTENT HEADER
		JLabel logoMainMenu = new JLabel("QUIZ SETUP");   
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(200, 30, 200, 50);
		getContentPane().add(logoMainMenu);
		
		//Create New Question Button
		JButton createNewQuestionButton = new JButton("Create New Question");
		createNewQuestionButton.setToolTipText("Click here to start the quiz!");
		createNewQuestionButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		createNewQuestionButton.setBackground(Color.WHITE);
		createNewQuestionButton.setBounds(200, 250, 200, 71);
		contentPane.add(createNewQuestionButton);
		createNewQuestionButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	
		    	//EVENT LISTENER FOR WHEN BUTTON IS HIGHLIGHTED
		        MainConstructor.clickSound();
				
		    }
		});
		
		//Create New Topic Button
		JButton createNewTopicButton = new JButton("Create New Topic");
		createNewTopicButton.setToolTipText("Click here to create a new topic");
		createNewTopicButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		createNewTopicButton.setBackground(Color.WHITE);
		createNewTopicButton.setBounds(200, 150, 200, 71);
		contentPane.add(createNewTopicButton);
		createNewTopicButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	
		    	//EVENT LISTENER FOR WHEN BUTTON IS HIGHLIGHTED
		        MainConstructor.clickSound();
				
		    }
		});
		
		//ON CLICK LISTENER FOR WHEN BUTTON IS CLICKED
		createNewQuestionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CreateNewQuestion createNewQuestionConstructor = new CreateNewQuestion();
					createNewQuestionConstructor = new CreateNewQuestion();  //PROCEED TO TOPICS FRAME
					createNewQuestionConstructor.setVisible(true);
			        MainConstructor.wooshSound();
			        dispose(); //CLOSE CURRENT FRAME
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		//ON CLICK LISTENER FOR WHEN BUTTON IS CLICKED
		createNewTopicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					CreateNewTopic createNewTopicConstructor = new CreateNewTopic();
					createNewTopicConstructor = new CreateNewTopic();  //PROCEED TO TOPICS FRAME
					createNewTopicConstructor.setVisible(true);
			        MainConstructor.wooshSound();
			        dispose(); //CLOSE CURRENT FRAME
				} catch (ClassNotFoundException | SQLException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		//BROWSE TOPICS BUTTON
		JButton buttonBrowse = new JButton("Browse Topics");
		buttonBrowse.setToolTipText("Click here to start the quiz!");
		buttonBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonBrowse.setBackground(Color.WHITE);
		buttonBrowse.setBounds(200, 350, 200, 71);
		contentPane.add(buttonBrowse);
		buttonBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	
		    	//EVENT LISTENER FOR WHEN BUTTON IS HIGHLIGHTED
		        MainConstructor.clickSound();
				
		    }
		});
		
		//ON CLICK LISTENER FOR WHEN BUTTON IS CLICKED
		buttonBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Topics topicsConstructor = new Topics();
					topicsConstructor = new Topics();  //PROCEED TO TOPICS FRAME
			        topicsConstructor.setVisible(true);
			        MainConstructor.wooshSound();
			        dispose(); //CLOSE CURRENT FRAME
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton buttonMenu = new JButton("Log Out");
		buttonMenu.setBounds(10, 427, 101, 23);
		contentPane.add(buttonMenu);
		buttonMenu.setBackground(UIManager.getColor("activeCaption"));
		buttonMenu.setForeground(Color.BLACK);
		buttonMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainConstructor.chimeSound();
				int returnToMenu = JOptionPane.showConfirmDialog(null, "You are about to log out and return to the main menu. \nYour settings will be saved.\n\nContinue?", "Return To Menu", JOptionPane.OK_CANCEL_OPTION);
				if (returnToMenu == JOptionPane.OK_OPTION) {
					MainConstructor.wooshSound();
					dispose();
					Main mainConstructor = new Main();
					mainConstructor.frame.setVisible(true);
				}
				else {
					MainConstructor.clickSound();
				}
			}
		});
		
		/*SPARE BUTTON
		 * 
		JButton buttonSelect = new JButton("Placeholder"); //PLACEHOLDER BUTTON, CHANGE STRING TO WHICHEVER DESIRED
		buttonSelect.setToolTipText("Click here to start the quiz!");
		buttonSelect.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonSelect.setBackground(Color.WHITE);
		buttonSelect.setBounds(200, 138, 200, 71);
		getContentPane().add(buttonSelect);
		buttonSelect.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        MainConstructor.clickSound();
		    }
		});
		buttonSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainConstructor.wooshSound();
			}
		});
		*/
		
		/* SPARE BUTTON
		JButton buttonEdit = new JButton("Placeholder");
		buttonEdit.setToolTipText("Click here to start the quiz!");
		buttonEdit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonEdit.setBackground(Color.WHITE);
		buttonEdit.setBounds(200, 302, 200, 71);
		contentPane.add(buttonEdit);
		buttonEdit.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		        MainConstructor.clickSound();
		    }
		});
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainConstructor.wooshSound();
			}
		});
		*/

		
	}
}
