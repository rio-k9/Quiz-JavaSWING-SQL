package tim;

import java.util.ArrayList;
import java.util.HashMap;

public class Answers {
	
	String school;
	HashMap<Integer, ArrayList<Integer>> results;
	
	// constructor
	public Answers() {
		// retrieve school name from settings.sav
		this.school = Serializer.restore().school_name;
		// Dictionary { q : a } for storing results
		this.results = new HashMap<Integer, ArrayList<Integer>>();
	}
	
	void newAnswer (Integer q, Integer a) {
		// method for creating an entry in students results
		// create arraylist for entry if none exists
		if (results.get(q)==null) {
			ArrayList<Integer> newEntry = new ArrayList<Integer>();
			newEntry.add(a);
			results.put(q, newEntry);
		} else {
			// append to existing list
			results.get(q).add(a);
		}
	}
	
	ArrayList<Integer> getAnswers (Integer q) {
		// method for accessing question q from results map
		if (results.containsKey(q)) {
			return results.get(q);
		} else {
			System.out.println("No answers found.");
			return null;
		}
	}

}
