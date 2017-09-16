package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import com.sun.rowset.CachedRowSetImpl;

import question.SQLiteQuestionDatabaseManager;

public class TopicSelection extends JFrame {
	
	JPanel contentPane;
	SQLiteQuestionDatabaseManager DBSync;
	Integer topicID;

	
	public static HashMap<Integer, String[]> questionMap;
	public CachedRowSetImpl questionsCrs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TopicSelection contentPane = new TopicSelection();
					contentPane.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	Main MainConstructor = new Main();
	@SuppressWarnings("serial")
	public TopicSelection() throws ClassNotFoundException, SQLException {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we)
		    { 
		        String exitButtons[] = {"Exit","Return"};
		        MainConstructor.chimeSound();
		        int exitAttempt = JOptionPane.showOptionDialog(null,"Are you sure you wish to exit?","Exit Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,exitButtons,exitButtons[1]);
		        if(exitAttempt == JOptionPane.YES_OPTION)
		        {	MainConstructor.clickSound();
		            System.exit(0);
		        }
		    }
		});
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		MainConstructor.Logo();
		getContentPane().add(Main.logoImageAbc);

		JButton buttonBack = new JButton("Back");
		buttonBack.setBounds(486, 427, 89, 23);
		contentPane.add(buttonBack);
		buttonBack.setBackground(UIManager.getColor("activeCaption"));
		buttonBack.setForeground(Color.BLACK);
		buttonBack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					MainConstructor.wooshSound();
					dispose();
					Main mainConstructor = new Main();
					mainConstructor.frame.setVisible(true);
			}
		});
		
		JLabel logoMainMenu = new JLabel("TOPIC SELECTION");
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(20, 30, 600, 50);
		contentPane.add(logoMainMenu);
		
		//--------------------------------------------------------------------------------//
		// Database Initialization //
		
		//Instantiate database object
		DBSync = new SQLiteQuestionDatabaseManager("QuestionDB");
		
		//Retrieve the topics as a CachedRowSetImpl object
		CachedRowSetImpl topicsCrs = DBSync.getTopics();
		
		//Convert the CachedRowSetImpl to a HashMap
		HashMap<Integer, String[]> topicsMap = question.ResultSetUtils.CachedRowSetImplToMap(topicsCrs);
		
		//--------------------------------------------------------------------------------//
		// Topics Buttons
		
		JPanel topicsButtonContainer = new JPanel();
		topicsButtonContainer.setBounds(50, 100, 400, 300);
		
		for (Integer key : topicsMap.keySet()) {
			
			//Initialize an array to hold the mapped topic string array
			String[] topicArray = topicsMap.get(key);
			
			//Retrieve the questions for the given topic from the database
			topicID = Integer.parseInt(topicArray[0]);
			questionsCrs = DBSync.getQuestions(topicID);
			questionMap = question.ResultSetUtils.CachedRowSetImplToMap(questionsCrs);
			questionsCrs.close();
			
			//If the topic has questions, then create a button to select it
			if (questionMap.size() > 0) {
				JButton topicButton = new JButton(topicArray[1]);
				
				//Clicking the button will begin a new quiz with the topic selected
				topicButton.addActionListener(e -> {
					
					int topicIDInner = Integer.parseInt(topicArray[0]);
					
					try {
						
						//Generate a map of the questions
						questionsCrs = DBSync.getQuestions(topicIDInner);
						questionMap = question.ResultSetUtils.CachedRowSetImplToMap(questionsCrs);
						questionsCrs.close();
						
						//Dispose of the frame and start the quiz
						this.dispose();
						Play playConstructor = new Play(questionMap, topicIDInner);
						playConstructor.setVisible(true);
						
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
				});	
				topicsButtonContainer.add(topicButton);
			}
		}
		contentPane.add(topicsButtonContainer);
	}
}