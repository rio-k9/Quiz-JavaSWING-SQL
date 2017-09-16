package tim;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Serializer	extends java.io.Serializable
{	
	
	static boolean checkFile() throws IOException {
		File checkMe = new File("settings.sav");
		boolean exists = checkMe.exists();
		return exists;		
	}
	
	static void write(Object myObject) {
		try {
			// File Stream Out
			FileOutputStream f_out = new FileOutputStream("settings.sav");
			
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
	
	
	static Settings restore() {
		try {
			// File Stream In
			FileInputStream f_in = new FileInputStream("settings.sav");
			
			// Buffered Stream In
			BufferedInputStream b_in = new BufferedInputStream(f_in);
			
			// Object Stream In
			ObjectInputStream obj_in = new ObjectInputStream(b_in);
				
			// Read object
			Settings obj = (Settings) obj_in.readObject();
			
			// Close object
			obj_in.close();
			
			return obj;
		} catch (IOException i) {
			i.printStackTrace();
			return Staff.defaultSettings();
		} catch (ClassNotFoundException c) {
			System.out.println("Settings class not found.");
			return Staff.defaultSettings();
		}
	}
	

}
