package tim;

import java.io.File;
import java.io.IOException;

import gui.Login;


/**
 * @author Tim
 *
 */
public class Verify {

	/**
	 * @param args
	 */
	
	public static void initialise() {
		// Check for settings file
		// else create default settings
		try {
			if (!Serializer.checkFile()) {
				File f_new = new File("settings.sav");
				f_new.createNewFile();
				System.out.println("No settings found.");
				Serializer.write(Staff.defaultSettings());
			} else {
				System.out.println("Settings found.");
			}
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	static gui.Login rioVerify = new Login();
	
	
	//DECLARE THE IMPORTED VARIABLE OUTSIDE THE OBJECT 
	public void Sync(String y, String z) throws IOException, ClassNotFoundException {
		initialise();
		//String y = rioVerify.getUser();
		//String z = rioVerify.getPass();
		System.out.println(y);
		System.out.println(z);
		Token response = Staff.login(y, z);  
		System.out.println(response.message);
		System.out.println(response.granted);
		Boolean accessVerify = response.granted;
		rioVerify.authToken(accessVerify);
		Student testStudent = new Student();
		System.out.println(testStudent.school);
		testStudent.newAnswer(2, 3);
		Integer a = testStudent.getAnswer(2);
		if (a!=0) { System.out.println(a); }
		
	}

}
