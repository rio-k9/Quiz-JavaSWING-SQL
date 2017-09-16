package tim;
import java.io.IOException;
import java.util.HashMap;


/**
 * @author Tim
 *
 */
public class Student {

	/**
	 * @param args
	 */
	
	Integer age;
	String school;
	
	// Dictionary { q : a } for storing results
	HashMap<Integer, Integer> results = new HashMap<Integer, Integer>();
	
	// Student constructor
	public Student() throws IOException, ClassNotFoundException {
		// retrieve school name from settings.sav
		Settings temp = (Settings) Serializer.restore();
		school = temp.school_name;
	}
	
	void newAnswer (Integer q, Integer a) {
		// method for creating an entry in students results
		results.put(q, a);
	}
	
	Integer getAnswer (Integer q) {
		// method for accessing question q from students results
		Integer a;
		if (results.containsKey(q)) {
			a = results.get(q);
		} else {
			a = 0;
			System.out.println("No answer found.");
		}
		return a;
	}
	
}
