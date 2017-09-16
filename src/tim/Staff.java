package tim;
/**
 * @author Tim
 *
 */

import java.io.IOException;
import java.util.List;


public interface Staff {

	
	static Token login(String user, String pass) throws IOException, ClassNotFoundException {
		Token token = new Token();
		
		Settings auth = Serializer.restore();
		
		if (user.equals(auth.user) && pass.equals(auth.pass)) { //USER INPUT VARIABLE HERE and USER INPUT PASS
			token.message = "Log in successful.";
			token.granted = true;
		} else {
			token.message = "Log in failed.";
			token.granted = false;
		}
		
		return token;
	}
	
	
	static void setTopic(int id) throws IOException, ClassNotFoundException {
		Settings settings = Serializer.restore();
		settings.set_id = id;
		Serializer.write(settings);
	}
	
	static List<String> listTopics() throws IOException, ClassNotFoundException {
		return Serializer.restore().topics;
	}
	
	static void setSchool(String school) throws IOException, ClassNotFoundException {
		Settings settings = Serializer.restore();
		settings.school_name = school;
		Serializer.write(settings);
	}
	
	static String getSchool() throws IOException, ClassNotFoundException {
		return Serializer.restore().school_name;
	}
	
	
	static void reset() {
		// Reset password - security first time set up
		System.out.print("reset");
	}
	
	
	static Settings defaultSettings() {
		return new Settings("adminJango", "adminFett", 0, "Cardiff");
	}

		
}
