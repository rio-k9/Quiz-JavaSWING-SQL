package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


@SuppressWarnings("serial")
public class StatisticsFrame extends JFrame {

	JPanel contentPane;
	private HashMap<Integer, String[]> questionMap;

	/**
	 * Launch the application window.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StatisticsFrame contentPane = new StatisticsFrame(null);
					contentPane.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Main MainConstructor = new Main();  //CONSTRUCTOR FOR MAIN CLASS TO CALL SOUND FILES AND LOGO
	
	public StatisticsFrame(HashMap<Integer, String[]> questionMap) {
		
		this.questionMap = questionMap;
		
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
		JLabel logoMainMenu = new JLabel("STATISTICS");   
		logoMainMenu.setFont(new Font("MV Boli", Font.BOLD, 26));
		logoMainMenu.setHorizontalAlignment(SwingConstants.CENTER);
		logoMainMenu.setBounds(200, 30, 200, 50);
		getContentPane().add(logoMainMenu);
		
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
		
		JTable statisticsTable = new JTable();
		statisticsTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JScrollPane tableScrollPane = new JScrollPane(statisticsTable);
		tableScrollPane.setBounds(50, 90, 500, 300);
		//tableScrollPane.add(statisticsTable);
		contentPane.add(tableScrollPane);
		
		statistics.Statistics statistics = new statistics.Statistics();
		String statisticsText = "";

		Object[][] tableData = new Object[questionMap.size()][4];
		
		for (Integer key : questionMap.keySet()) {
			
			Integer indexingValue = Integer.parseInt(questionMap.get(key)[0]);
			
			tableData[key][0] = Integer.parseInt(questionMap.get(key)[0]);
			tableData[key][1] = statistics.getTotalCount(indexingValue);
			tableData[key][2] = statistics.getCorrectCount(indexingValue);
			
			int val1 = statistics.getTotalCount(indexingValue);
			int val2 = statistics.getCorrectCount(indexingValue);
			
			Double percentageCorrect = ((double) val2/val1) * 100;
			
			tableData[key][3] = percentageCorrect;
		}
		
		statisticsTable.setModel(new DefaultTableModel(
				
				//Use the tableData array to add data to the table
				tableData,
				
				//Create a new String array to add the headings to the table
				new String[] {
					"Question ID", "Total", "Correct", "Percentage Correct"
				})
			 {
				Class[] columnTypes = new Class[] {
					Integer.class, Integer.class, Integer.class, Double.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});		
	}
}
