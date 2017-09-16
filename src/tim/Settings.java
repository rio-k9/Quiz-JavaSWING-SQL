package tim;
import java.util.List;

public class Settings implements java.io.Serializable {
	
	private static final long serialVersionUID = -55857686305273843L;

	public String user;
	public String pass;
	int set_id;
	String school_name;
	List<String> topics;
	
	public Settings(String user, String pass, int topic, String school) {
		this.user = user;
		this.pass = pass;
		this.set_id = topic;
		this.school_name = school;
	}
	
	public void addTopic(String topic) {
		topics.add(topic);
	}
	
	public String toString() {
		String value = "user: " + user + "\ntopic_select: " + set_id +
				"\nschool_name: " + school_name;
		return value;
	}
}
