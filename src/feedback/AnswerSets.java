package feedback;

import java.util.ArrayList;
import java.util.HashMap;

public class AnswerSets implements java.io.Serializable {

	private static final long serialVersionUID = 7526472345622776147L;

	public final HashMap<Integer, Integer> correctAnswers;
	public HashMap<Integer, ArrayList<Integer>> studentAnswers;

	// constructor
	public AnswerSets(HashMap<Integer, Integer> correct) {
		this.correctAnswers = correct;
		this.studentAnswers = new HashMap<Integer, ArrayList<Integer>>();
	}


	// getters
	public HashMap<Integer, Integer> getCorrect() {
		return this.correctAnswers;
	}

	public HashMap<Integer, ArrayList<Integer>> getStudent() {
		return this.studentAnswers;
	}


	// setters
	public void setStudent(HashMap<Integer, ArrayList<Integer>> student) {
		this.studentAnswers = student;
	}
}
