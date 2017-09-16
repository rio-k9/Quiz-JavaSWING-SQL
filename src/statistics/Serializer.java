package statistics;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public interface Serializer	extends java.io.Serializable
{	
	
	static boolean checkFile() throws IOException {
		File checkMe = new File("results.sav");
		boolean exists = checkMe.exists();
		return exists;		
	}
	
	static void write(Object myObject) {
		try {
			// File Stream Out
			FileOutputStream f_out = new FileOutputStream("results.sav");
			
			// Buffered Stream Out
			BufferedOutputStream b_out = new BufferedOutputStream(f_out);
			
			// Object Stream Out
			ObjectOutputStream obj_out = new ObjectOutputStream(b_out);
			
			// Write object
			obj_out.writeObject( myObject );
			
			// Close object
			obj_out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	
	static AnswerSets restore() {
		try {
			// File Stream In
			FileInputStream f_in = new FileInputStream("results.sav");
			
			// Buffered Stream In
			BufferedInputStream b_in = new BufferedInputStream(f_in);
			
			// Object Stream In
			ObjectInputStream obj_in = new ObjectInputStream(b_in);
				
			// Read object
			AnswerSets obj = (AnswerSets) obj_in.readObject();
			
			// Close object
			obj_in.close();
			
			return obj;
		} catch (IOException i) {
			i.printStackTrace();
			return new AnswerSets(new HashMap<Integer, Integer>());
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found.");
			return new AnswerSets(new HashMap<Integer, Integer>());
		}
	}
	

}
