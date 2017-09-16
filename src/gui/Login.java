package gui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tim.Verify;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;



/*
 *  @author Rio Karim
 * 
 */


@SuppressWarnings("serial")
public class Login extends JFrame {
	
	private JPanel contentPane;
	private JPasswordField enterPassword;
	private JTextField enterUser;

	/**
	 * Launch the application window.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Method for getting login token from tim's package
	 */ 
	int failed_login_limit = 3;
	public void authToken(Boolean tokenState) {
		System.out.println(tokenState);
		if (tokenState == true) {
			//If login credentials returns as true proceed to next page
			String loginButton[] = {"Ok"}; //OK button
	        int loginConfirm = JOptionPane.showOptionDialog(null,"Login successful!","Login",JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE,null,loginButton, null);
	        if(loginConfirm == JOptionPane.OK_OPTION) //Login successful dialog box
	        {
	        	
	        	
	        	dispose();  //DISPOSE CURRENT WINDOW #needs review
	        	
	        	
				Setup setupConstructor = new Setup(); //CALL SETUP WINDOW
				setupConstructor.setVisible(true);
				MainConstructor.wooshSound(); //SOUND
	        }
		}
		else {
			failed_login_limit--; //LOGIN RETURNS AS FALSE, ATTEMPT INT BY 1
			String attemptButton[] = {"Ok"};
	        JOptionPane.showOptionDialog(null,"Login failed. " + failed_login_limit + " attempts remaining.\nLogin credentials are case-sensitive.","Login",JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE,null,attemptButton, null);
			System.out.println("login failed.");
			if (failed_login_limit <= 0){  //IF ATTEMPTS REACH 0, RETURN DIALOG BOX AND MAIN MENU
				String failedButton[] = {"Ok"};
		        JOptionPane.showOptionDialog(null,"Please contact an event administrator for help setting up the quiz.","Exit",JOptionPane.OK_OPTION,JOptionPane.WARNING_MESSAGE,null,failedButton, null);
		        MainConstructor.wooshSound();
		        
		        
				dispose(); //DISPOSE CURRENT WINDOW #needs review
				
				
				Main mainConstructor = new Main();
				mainConstructor.frame.setVisible(true); //CALLS MENU WINDOW
				failed_login_limit = 3;
			}
		}
	}
	
	Main MainConstructor = new Main();
	public Login() {
		style(); //CALLING FRAME STYLING
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISABLING 'X' BUTTON ON WINDOW FRAME
		addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) //EVENT LISTENER FOR PRESSING 'X' ON CLOSE FRAME
		    { 
		    	MainConstructor.chimeSound();
		        String exitButtons[] = {"Exit","Return"}; //RETURNS DIALOG BOX
		        int exitAttempt = JOptionPane.showOptionDialog(null,"Are you sure you wish to exit?","Exit Warning",JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,exitButtons,exitButtons[1]);
		        if(exitAttempt == JOptionPane.YES_OPTION)
		        {
		        	MainConstructor.clickSound();
		            System.exit(0); //SYSTEM EXIT ON 'YES' OPTION
		        }
		        else {
		        	MainConstructor.clickSound(); //DO NOTHING / CLOSE DIALOG ON NO OPTION
		        }
		    }
		});
	}
	
	public void style() {
		//SETS SIZE OF CONTENTPANE (THE TOTAL OF MATERIAL APPLIED TO WINDOW)
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		MainConstructor.Logo(); //CALL LOGO
		getContentPane().add(Main.logoImageAbc); //CALLS LOGO IMAGE VARIABLE FROM PREVIOUS CLASS
		contentPane.setLayout(null);
		

		JLabel logoMainMenu = new JLabel("ADMIN LOGIN");   //PAGE HEADER
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(200, 30, 200, 50);
		getContentPane().add(logoMainMenu);
		
		enterPassword = new JPasswordField(); //PASSWORD FIELD
		enterPassword.setHorizontalAlignment(SwingConstants.CENTER);
		enterPassword.setBackground(UIManager.getColor("controlHighlight"));
		enterPassword.setBounds(277, 246, 265, 40);
		contentPane.add(enterPassword);
		
		enterUser = new JTextField(); //USER FIELD
		enterUser.setHorizontalAlignment(SwingConstants.CENTER);
		enterUser.setBackground(UIManager.getColor("controlHighlight"));
		enterUser.setBounds(277, 162, 265, 40);
		contentPane.add(enterUser);
		enterUser.setColumns(10);
		
		
		JLabel labelUser = new JLabel("Username:"); //"USERNAME: " LABEL
		labelUser.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelUser.setBounds(277, 127, 265, 33);
		contentPane.add(labelUser);
		
		JLabel labelPassword = new JLabel("Password:"); //"PASSWORD: " LABEL
		labelPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		labelPassword.setBounds(277, 213, 265, 33);
		contentPane.add(labelPassword);
		
		
		JButton buttonLogin = new JButton("Login"); //LOGIN BUTTON
		buttonLogin.setBackground(UIManager.getColor("activeCaption"));
		buttonLogin.setForeground(Color.BLACK);
		buttonLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		buttonLogin.addActionListener(new ActionListener() {  //EVENT LISTENER FOR WHEN BUTTON IS CLICKED
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) { 
				MainConstructor.clickSound();  //SOUND
				String u_User = enterUser.getText(); //GETTING USERNAME STRING
				String u_Pass = enterPassword.getText(); //GETTING PASSWORD STRING
				System.out.println(u_User);
				System.out.println(u_Pass);
				tim.Verify verifyMe = new Verify(); //CALLING VERIFICATION FROM TIM'S MODULE
				try {
					verifyMe.Sync(u_User, u_Pass); //PASSING THROUGH STRING USER AND PASS
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		buttonLogin.setBounds(313, 313, 200, 40);
		contentPane.add(buttonLogin);
		
		JLabel iconSettings = new JLabel(); //SETTINGS ICON IMAGE
		iconSettings.setBounds(34, 150, 180, 180);
		ImageIcon imgSettings = new ImageIcon("src/img/Settings.png");
		Image image = imgSettings.getImage();
		Image newimg = image.getScaledInstance(180, 180,  java.awt.Image.SCALE_SMOOTH);
		imgSettings = new ImageIcon(newimg);
		contentPane.add(iconSettings);
		iconSettings.setIcon(imgSettings);
		
		JButton buttonBack = new JButton("Back"); //BACK BUTTON
		buttonBack.setBounds(10, 427, 89, 23);
		contentPane.add(buttonBack);
		buttonBack.setBackground(UIManager.getColor("activeCaption"));
		buttonBack.setForeground(Color.BLACK);
		buttonBack.setFont(new Font("Tahoma", Font.PLAIN, 12));
		buttonBack.addActionListener(new ActionListener() { //CLICK LISTENER FOR BACK BUTTON
			public void actionPerformed(ActionEvent arg0) {

					
				
					MainConstructor.wooshSound();  //SOUND	
					dispose(); //CLOSE FRAME
					Main mainConstructor = new Main(); //OPEN MAIN MENU FRAME
					mainConstructor.frame.setVisible(true);

				
				
			}
		});
	}
}