package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import com.sun.rowset.CachedRowSetImpl;

import question.SQLiteQuestionDatabaseManager;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CreateNewTopic extends JFrame {

	JPanel contentPane;
	public SQLiteQuestionDatabaseManager DBSync;
	public static JTextField jTextField;
	public CachedRowSetImpl questionsCrs;
	public static ImageIcon greenTickImageIcon;
	public static ImageIcon redCrossImageIcon;
	public static HashMap<Integer, String[]> topicsMap;
	public static JLabel validationImage;
	
	//A variable to track whether the fields are validated
	public static Integer validationStatus = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateNewTopic contentPane = new CreateNewTopic();
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
	public CreateNewTopic() throws ClassNotFoundException, SQLException, IOException {
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
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		MainConstructor.Logo();
		getContentPane().add(Main.logoImageAbc); //CALLS LOGO IMAGE VARIABLE FROM PREVIOUS CLASS

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
					Setup setupConstructor = new Setup(); //CALL SETUP WINDOW
					setupConstructor.setVisible(true);;
			}
		});
		
		JLabel logoMainMenu = new JLabel("CREATE NEW TOPIC"); 
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(20, 30, 600, 50);
		contentPane.add(logoMainMenu);
		
		/*---- Database Initialization ----*/
		
		//Instantiate database object
		DBSync = new SQLiteQuestionDatabaseManager("QuestionDB");
		
		//Retrieve the topics as a CachedRowSetImpl object
		CachedRowSetImpl topicsCrs = DBSync.getTopics();
		
		//Convert the CachedRowSetImpl to a HashMap
		topicsMap = question.ResultSetUtils.CachedRowSetImplToMap(topicsCrs);
		
		/*---- Validation Images ----*/

		//Load the images
		ImageIcon redCrossImageIcon = new ImageIcon("img/redCrossSmall.png");
		ImageIcon greenTickImageIcon = new ImageIcon("img/greenTickSmall.png");
					
		validationImage = new JLabel("", redCrossImageIcon, JLabel.CENTER);
		validationImage.setBounds(550, 125, 25, 25);
		contentPane.add(validationImage);
		validationImage.setIcon(redCrossImageIcon);

		
		ImageIcon userSelectionImageDisplayIcon = new ImageIcon();
		JLabel userSelectionImageDisplay = new JLabel("", null, JLabel.CENTER);
		userSelectionImageDisplay.setBounds(250, 350, 100, 100);
		contentPane.add(userSelectionImageDisplay);
		
		/*----  ComboBoxs ----*/
		
		//Topic Selection ComboBox
		
		//Initialize arrays to store the topic name and topic ID
		String [] topicNameArray = new String[topicsMap.size()];
		

		
		/*---- Input Fields: JTextFields ----*/
		
		//Create an ArrayList of JTextFields and position them incrementally
		ArrayList<JTextField> textFieldArrayList = new ArrayList<JTextField>();
		
		JTextField jTextField = new JTextField();
		jTextField.setBounds(120, 125, 400, 30);
		contentPane.add(jTextField);
		textFieldArrayList.add(jTextField);
		
		jTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				validateInput(jTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				validateInput(jTextField.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				validateInput(jTextField.getText());
			}
		});	
				
		/*---- Labels ----*/
		
		//Set the labels of each field
		
		JLabel questionImageContentFieldLabel = new JLabel();
		questionImageContentFieldLabel.setText(null);
		questionImageContentFieldLabel.setBounds(120, 155, 250, 30);
		contentPane.add(questionImageContentFieldLabel);
		
		/*---- Buttons ----*/
		
		//Create a new jFile chooser to select images for the question
		
		


		/*---- Save ----*/
		
		//Retrieve and transform the image for the button icon
		ImageIcon saveimageIcon = new ImageIcon(Topics.class.getResource("/img/save.png"));
		Image saveimage = saveimageIcon.getImage(); // transform it 
		Image savenewimg = saveimage.getScaledInstance(33, 33,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		saveimageIcon = new ImageIcon(savenewimg);  // transform it back
		
		//Create the save button and set the image
		JButton saveButton = new JButton("");
		saveButton.setIcon(saveimageIcon);
		saveButton.setBounds(500, 360, 57, 51);
		contentPane.add(saveButton);
		
		//Add the event handler for the save button
		saveButton.addActionListener(e -> {
			
			//If a field is invalid, don't attempt to modify the database
			if (validationStatus == 0) {
				JOptionPane.showMessageDialog(this, "Your topic name already exists", null, JOptionPane.ERROR_MESSAGE);
			}
			else {
				
				String topicName = jTextField.getText();
				
				try {
					DBSync.addTopic(topicName);
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(this, "Your topic has been successfully created");
				Main mainConstructor = new Main();
				mainConstructor.frame.setVisible(true);
			}
		});
	}
	
	//Changes the validation icon (i.e. the tick or cross) next to the text input field, depending on the validity of the input
	public static void validateInput(String inputText) {
		
		String[] topicNameArray = new String[topicsMap.size()];
		
		for (int i=0; i< topicsMap.size(); i++) {
			topicNameArray[i] = topicsMap.get(i)[1];
		}
		
		if (Arrays.asList(topicNameArray).contains(inputText)) {
			validationImage.setIcon(new ImageIcon("img/redCrossSmall.png"));
			validationStatus = 0;
		}
		else {
			validationImage.setIcon(new ImageIcon("img/greenTickSmall.png"));
			validationStatus = 1;
		}
	}	
}