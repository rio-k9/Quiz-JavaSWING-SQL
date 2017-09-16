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

public class CreateNewQuestion extends JFrame {

	JPanel contentPane;
	public Integer topicID;
	private File questionImageFile;
	public SQLiteQuestionDatabaseManager DBSync;
	public static HashMap<Integer, String[]> questionMap;
	private static ArrayList<JLabel> inputFeedbackImages;
	public CachedRowSetImpl questionsCrs;
	public static ImageIcon greenTickImageIcon;
	public static ImageIcon redCrossImageIcon;
	
	//A variable to track whether the fields are validated
	public static Integer[] validationStatus = {0,0,1,0,0,0,0,0};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateNewQuestion contentPane = new CreateNewQuestion();
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
	public CreateNewQuestion() throws ClassNotFoundException, SQLException, IOException {
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
		
		JLabel logoMainMenu = new JLabel("Create New Question"); 
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
		HashMap<Integer, String[]> topicsMap = question.ResultSetUtils.CachedRowSetImplToMap(topicsCrs);
		
		/*---- Validation Images ----*/

		//Load the images
		ImageIcon redCrossImageIcon = new ImageIcon("img/redCrossSmall.png");
		ImageIcon greenTickImageIcon = new ImageIcon("img/greenTickSmall.png");
			
		//Generate an ArrayList of labels, and place each label at a vertical increment of 30 from the previous vertical position
		inputFeedbackImages = new ArrayList<JLabel>();
		
		int verticalPosition = 95;
		for (int i=0; i<8; i++) {

			JLabel validationImage = new JLabel("", redCrossImageIcon, JLabel.CENTER);
			
			//The initial "null" value is valid for the question image field
			if (i == 2) {
				validationImage.setIcon(greenTickImageIcon);
			}
			
			validationImage.setBounds(520, verticalPosition, 25, 25);
			inputFeedbackImages.add(validationImage);
			contentPane.add(validationImage);
			verticalPosition += 30;
		}
		
		ImageIcon userSelectionImageDisplayIcon = new ImageIcon();
		JLabel userSelectionImageDisplay = new JLabel("", null, JLabel.CENTER);
		userSelectionImageDisplay.setBounds(250, 350, 100, 100);
		contentPane.add(userSelectionImageDisplay);
		
		/*----  ComboBoxs ----*/
		
		//Topic Selection ComboBox
		
		//Initialize arrays to store the topic name and topic ID
		String [] topicNameArray = new String[topicsMap.size()];
		
		//Populate the topic name array with the relevant data
		for (Integer key : topicsMap.keySet()) {
			topicNameArray[key] = topicsMap.get(key)[1];
		}
				
		//Create a new ComboBox to select the topic, and set the contents to the topic name array
		JComboBox topicComboBox = new JComboBox(topicNameArray);
		topicComboBox.setBounds(120, 95, 400, 30);
		topicComboBox.setSelectedItem(null);
		contentPane.add(topicComboBox);
		
		//Add event listener to the topic ComboBox
		topicComboBox.addItemListener(e -> {
			inputFeedbackImages.get(0).setIcon(new ImageIcon("img/greenTickSmall.png"));
			validationStatus[0] = 1;
		});
		
		String[] correctAnswerArray = {"1", "2", "3", "4"};
		
		JComboBox correctAnswerComboBox = new JComboBox(correctAnswerArray);
		correctAnswerComboBox.setBounds(120, 305, 75, 30);
		correctAnswerComboBox.setSelectedItem(null);
		contentPane.add(correctAnswerComboBox); 
		
		//Add event listener to the correct answer ComboBox
		correctAnswerComboBox.addItemListener(e -> {
			inputFeedbackImages.get(7).setIcon(new ImageIcon("img/greenTickSmall.png"));
			validationStatus[7] = 1;
		});
			
		/*---- Input Fields: JTextFields ----*/
		
		//Create an ArrayList of JTextFields and position them incrementally
		ArrayList<JTextField> textFieldArrayList = new ArrayList<JTextField>();
		
		//Set the initial vertical position
		verticalPosition = 125;
		
		for (int i=0; i<6; i++) {
			
			//Skip i=1, as this will be the position of the question image selection field
			if (i != 1) {
				
				//Set a final variable to keep a reference of the i counter for use in the listener
				final int referenceInteger = i;
				
				//Create and add the jTextField
				JTextField jTextField = new JTextField();
				jTextField.setBounds(120, verticalPosition, 400, 30);
				contentPane.add(jTextField);
				textFieldArrayList.add(jTextField);
					
				//Set a listener for the jtextField
				jTextField.getDocument().addDocumentListener(new DocumentListener() {

					@Override
					public void insertUpdate(DocumentEvent e) {
						validateInput(jTextField.getText(), referenceInteger);
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						validateInput(jTextField.getText(), referenceInteger);
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
						validateInput(jTextField.getText(), referenceInteger);
					}
				});		
			}	
			//Increment the vertical position
			verticalPosition += 30;	
		}	
				
		/*---- Labels ----*/
		
		//Set the labels of each field
		String[] labelText = {"Topic", "Question Text", "Question Image", "Option 1", "Option 2", "Option 3", "Option 4", "Correct Answer"};
		verticalPosition = 100;
		
		for (int i=0; i < labelText.length; i++) {
			JLabel jLabel = new JLabel(labelText[i]);
			jLabel.setBounds(20, verticalPosition, 120, 20);
			contentPane.add(jLabel);
			verticalPosition += 30;
		}
		
		JLabel questionImageContentFieldLabel = new JLabel();
		questionImageContentFieldLabel.setText(null);
		questionImageContentFieldLabel.setBounds(120, 155, 250, 30);
		contentPane.add(questionImageContentFieldLabel);
		
		/*---- Buttons ----*/
		
		//Create a new jFile chooser to select images for the question
		final JFileChooser fc = new JFileChooser("img");
				
		//Use extension filter to limit image selection to compatible file types
		FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Images", "jpg", "png", "gif", "jpeg");
		fc.setFileFilter(extensionFilter);
		
		JButton selectImageButton = new JButton("Browse");
		selectImageButton.setBounds(445, 155, 75, 30);

		selectImageButton.addActionListener(e-> {
			
			//Launch file browser
			int returnVal = fc.showOpenDialog(null);
			
			//If the user has successfully chosen an image file, assign it to the question image file variable
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				questionImageFile = fc.getSelectedFile();
				
				try {
					String imageFilePath = questionImageFile.getCanonicalPath();
					String directoryPath = new File(".").getCanonicalPath();
					
					//If the file is in the project directory, get the file path relative to the project's directory
					if (imageFilePath.contains(directoryPath + "/")) {
						imageFilePath = imageFilePath.replaceAll((directoryPath + "/"), "");
						
						//Set the image frame to display the added image
						Image userSelectedImage = ImageIO.read(new File(imageFilePath));
						Image scaledImage = userSelectedImage.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH);
						ImageIcon selectedImageIcon = new ImageIcon(scaledImage);				
						userSelectionImageDisplay.setIcon(selectedImageIcon);
					}
					
					questionImageContentFieldLabel.setText(imageFilePath);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
		});
		contentPane.add(selectImageButton);
		
		JButton removeImageButton = new JButton("Remove");
		removeImageButton.setBounds(370, 155, 75, 30);
		contentPane.add(removeImageButton);
		
		removeImageButton.addActionListener(e-> {
			questionImageContentFieldLabel.setText(null);
			userSelectionImageDisplay.setIcon(null);
		});
		
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
			if (Arrays.asList(validationStatus).contains(0)) {
				JOptionPane.showMessageDialog(this, "Please review your entries", null, JOptionPane.ERROR_MESSAGE);
			}
			else {
				//If all fields are valid, add the question to the database
				try {
					Integer topicId = SQLiteQuestionDatabaseManager.getTopicID(topicComboBox.getSelectedItem().toString());
					Integer correctAnswer = correctAnswerComboBox.getSelectedIndex();
					Integer questionID = SQLiteQuestionDatabaseManager.addQuestion(textFieldArrayList.get(0).getText(), 
							questionImageContentFieldLabel.getText(), textFieldArrayList.get(1).getText(), 
							textFieldArrayList.get(2).getText(), textFieldArrayList.get(3).getText(), textFieldArrayList.get(4).getText(), 
							correctAnswer);
					SQLiteQuestionDatabaseManager.addQuestionToTopic(questionID, topicId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "Your question has been successfully created");
				Main mainConstructor = new Main();
				mainConstructor.frame.setVisible(true);
			}
		});
	}
	
	//Changes the validation icon (i.e. the tick or cross) next to the text input field, depending on the validity of the input
	public static void validateInput(String inputText, Integer referenceInteger) {
		
		//Use the reference integer to retrieve the field name
		String[] referenceIntegerToFieldName = {"QuestionText", null, "Option1", "Option2", "Option3", "Option4"};
		
		//Validate the input text using the field name
		Integer validation = FieldLengthValidator.validateField(inputText, referenceIntegerToFieldName[referenceInteger]);		
		
		//Index the inputFeedbackImages using the reference integer and set the icon accordingly
		if (validation == 1) {
			inputFeedbackImages.get(referenceInteger+1).setIcon(new ImageIcon("img/greenTickSmall.png"));
			inputFeedbackImages.get(referenceInteger+1).validate();
			inputFeedbackImages.get(referenceInteger+1).repaint();
			
			//Update the validation status
			if (referenceInteger == 0) {
				validationStatus[1] = 1;
			}
			else {
				validationStatus[referenceInteger+1] = 1;
			}
		}
		else {
			inputFeedbackImages.get(referenceInteger+1).setIcon(new ImageIcon("img/redCrossSmall.png"));
			inputFeedbackImages.get(referenceInteger+1).validate();
			inputFeedbackImages.get(referenceInteger+1).repaint();
			
			//Update the validation status
			if (referenceInteger == 0) {
				validationStatus[1] = 0;
			}
			else {
				validationStatus[referenceInteger+1] = 0;
			}
		}			
	}	
}