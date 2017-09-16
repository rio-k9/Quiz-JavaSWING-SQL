package question;

import java.util.Arrays;
import java.util.HashMap;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
* ResultSetManager
* <p>A utilities class for the management of ResultSet and related CachedRowSetImplToMap objects.</p>
* @author  Adam Kearney
*/

public class ResultSetUtils {
	
	//--------------------------------------------------------------------------------//
	//Constructor//
	
	//Private constructor to prevent instantiation
	
	private ResultSetUtils(){
	}
	
	//--------------------------------------------------------------------------------//
	//Public Methods//
	
	//Takes a resultSet as an input and outputs a HashMap with generated keys for each row of data.
	public static HashMap<Integer,String[]> resultSetToMap(ResultSet input) throws SQLException {
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the output
		output = _resultSetToMap(input);
				
		//Return the output
		return output;	
	}
	
	//Takes a CachedRowSetImpl as an input and outputs a HashMap with generated keys for each row of data.
	public static HashMap<Integer, String[]> CachedRowSetImplToMap(CachedRowSetImpl input) throws SQLException {
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the output
		output = _CachedRowSetImplToMap(input);
				
		//Return the output
		return output;	
	}
	
	/*Takes a resultSet as an input and outputs a HashMap with a specified column as the key,
	and with one or more specified columns as values. Make sure that the HashMap key is a column
	of unique values in the table, since every key in a HashMap needs to be unique.*/
	public static HashMap<Integer,String[]> resultSetToMap(ResultSet input, int key, int[] values) throws SQLException {
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the output
		output = _resultSetToMap(input, key, values);
				
		//Return the output
		return output;	
	}
	
	/*Takes a CachedRowSetImpl as an input and outputs a HashMap with a specified column as the key,
	and with one or more specified columns as values. Make sure that the HashMap key is a column
	of unique values in the table, since every key in a HashMap needs to be unique.*/
	public static HashMap<Integer, String[]> CachedRowSetImplToMap(CachedRowSetImpl input, int key, int[] values) throws SQLException {
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the output
		output = _CachedRowSetImplToMap(input, key, values);
				
		//Return the output
		return output;	
	}
		
	//--------------------------------------------------------------------------------//
	//Private Methods//
	
	private static HashMap<Integer,String[]> _resultSetToMap(ResultSet input) throws SQLException {
		
		/*A counter to generate the key values, starting from 0, but the starting value is inconsequential.
		Having a counter is necessary, as the SQLite resultSet cursor cannot go backwards, and thus
		the number of rows cannot be returned beforehand without retrieving the data from the database twice.*/
		int j = 0;
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the metadata from the resultSet
		ResultSetMetaData inputMd = input.getMetaData();
		
		//Get the number of columns in the resultSet
		int columnCount = inputMd.getColumnCount();
		
		//Check if there are values left in the input resultSet
		while (input.next()) {
			
			//Create the array
			String[] valuesArray = new String[columnCount];
		
			//Cycle through columns between 1 and the number of columns
			for (int i = 1; i<=columnCount; i++) {
				
				//Get the name of the column
				String columnName = ((ResultSetMetaData) input).getColumnName(i);
				
				//Get the SQL type of the column
				int columnType = ((ResultSetMetaData) input).getColumnType(i);
				
				//Lookup an approximation of the appropriate Java 
				String columnTypeName = _columnDataTypeChecker(columnType);			
				
				//Writing this out the long way, but still probably better than using reflection
				if (columnTypeName.equals("int")) {
					valuesArray[i-1] =  String.valueOf(input.getInt(columnName));
				}
				else if (columnTypeName.equals("String")) {
					valuesArray[i-1] = input.getString(columnName);
				}
				else if (columnTypeName.equals("null")) {
					valuesArray[i-1] = null;
				}
			}
			
			//Add the values to the HashMap
			output.put(j, valuesArray);
			
			//Increment the row counter to generate the next HashMap key
			j++;
			
		}		
		return output;	
	}
	
	private static HashMap<Integer, String[]> _CachedRowSetImplToMap(CachedRowSetImpl input) throws SQLException {
		
		/*A counter to generate the key values, starting from 0, but the starting value is inconsequential.
		Having a counter is necessary, as the SQLite resultSet cursor cannot go backwards, and thus
		the number of rows cannot be returned beforehand without retrieving the data from the database twice.*/
		int j = 0;
		
		//Initialize a HashMap to store the output
		HashMap<Integer, String[]> output = new HashMap<Integer, String[]>();
		
		//Retrieve the metadata from the resultSet
		ResultSetMetaData inputMd = input.getMetaData();
		
		//Get the number of columns in the resultSet
		int columnCount = inputMd.getColumnCount();
		
		//Check if there are values left in the input resultSet
		while (input.next()) {
			
			//Create the array
			String[] valuesArray = new String[columnCount];
		
			//Cycle through columns between 1 and the number of columns
			for (int i = 1; i<=columnCount; i++) {
				
				//Get the name of the column
				String columnName = inputMd.getColumnName(i);
				
				//Get the SQL type of the column
				int columnType = inputMd.getColumnType(i);
				
				//Lookup an approximation of the appropriate Java 
				String columnTypeName = _columnDataTypeChecker(columnType);			
				
				//Writing this out the long way, but still probably better than using reflection
				if (columnTypeName.equals("int")) {
					valuesArray[i-1] =  String.valueOf(input.getInt(columnName));
				}
				else if (columnTypeName.equals("String")) {
					valuesArray[i-1] = input.getString(columnName);
				}
				else if (columnTypeName.equals("null")) {
					valuesArray[i-1] = null;
				}
			}
			
			//Add the values to the HashMap
			output.put(j, valuesArray);
			
			//Increment the row counter to generate the next HashMap key
			j++;
			
		}		
		return output;	
	}
	
	private static HashMap<Integer,String[]> _resultSetToMap(ResultSet input, int key, int[] values) throws SQLException {
		
		//Initialize hashMapKey
		int hashMapKey = 0;
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the metadata from the resultSet
		ResultSetMetaData inputMd = input.getMetaData();
		
		//Get the number of columns in the resultSet
		int columnCount = inputMd.getColumnCount();
		
		/*If the number of columns requested are greater than the number of columns in the table,
		then the request is not valid*/
		if (values.length > columnCount) {
			return null;
		}
		
		//Check if there are values left in the input resultSet
		while (input.next()) {
			
			//Initialize counter for the generated array
			int j = 0;
			
			//Create the array
			String[] valuesArray = new String[values.length];
			
			//Cycle through columns between 1 and the number of columns
			for (Integer i = 1; i<=columnCount; i++) {
				
				boolean test = Arrays.stream(values).anyMatch(i::equals);
				
				//If the column is one of the requested columns
				if (test) {
					
					//Get the name of the column
					String columnName = ((ResultSetMetaData) input).getColumnName(i);
					
					//Get the SQL type of the column
					int columnType = ((ResultSetMetaData) input).getColumnType(i);
					
					//Lookup an approximation of the appropriate Java 
					String columnTypeName = _columnDataTypeChecker(columnType);			
					
					//Writing this out the long way, but still probably better than using reflection
					if (columnTypeName.equals("int")) {
						valuesArray[j] = String.valueOf(input.getInt(columnName));
						j++;
					}
					else if (columnTypeName.equals("String")) {
						valuesArray[j] = input.getString(columnName);
						j++;
					}
					else if (columnTypeName.equals("null")) {
						valuesArray[j] = null;
						j++;
					}
				}
				
				//Check if the column contains the desired HashMap key
				if (i == key) {
					
					//Get the name of the column
					String columnName = ((ResultSetMetaData) input).getColumnName(i);
					
					//Get the SQL type of the column
					int columnType = ((ResultSetMetaData) input).getColumnType(i);
					
					//Lookup an approximation of the appropriate Java 
					String columnTypeName = _columnDataTypeChecker(columnType);			
					
					//Assign the value of the HashMap key
					if (columnTypeName.equals("int")) {
						hashMapKey = input.getInt(columnName);
					}
				}
				
				//Add the values to the HashMap
				output.put(hashMapKey, valuesArray);
			}
		}

		//Placeholder
		return output;	
	}
	
	private static HashMap<Integer, String[]> _CachedRowSetImplToMap(CachedRowSetImpl input, int key, int[] values) throws SQLException {
		
		//Initialize hashMapKey
		int hashMapKey = 0;
		
		//Initialize a HashMap to store the output
		HashMap<Integer,String[]> output = new HashMap<Integer,String[]>();
		
		//Retrieve the metadata from the resultSet
		ResultSetMetaData inputMd = input.getMetaData();
		
		//Get the number of columns in the resultSet
		int columnCount = inputMd.getColumnCount();
		
		/*If the number of columns requested are greater than the number of columns in the table,
		then the request is not valid*/
		if (values.length > columnCount) {
			return null;
		}
		
		//Check if there are values left in the input resultSet
		while (input.next()) {
			
			//Initialize counter for the generated array
			int j = 0;
			
			//Create the array
			String[] valuesArray = new String[values.length];
			
			//Cycle through columns between 1 and the number of columns
			for (Integer i = 1; i<=columnCount; i++) {
				
				boolean test = Arrays.stream(values).anyMatch(i::equals);
				
				//If the column is one of the requested columns
				if (test) {
					
					//Get the name of the column
					String columnName = input.getMetaData().getColumnName(i);
					
					//Get the SQL type of the column
					int columnType = input.getMetaData().getColumnType(i);
					
					//Lookup an approximation of the appropriate Java 
					String columnTypeName = _columnDataTypeChecker(columnType);			
					
					//Writing this out the long way, but still probably better than using reflection
					if (columnTypeName.equals("int")) {
						valuesArray[j] = String.valueOf(input.getInt(columnName));
						j++;
					}
					else if (columnTypeName.equals("String")) {
						valuesArray[j] = input.getString(columnName);
						j++;
					}
					else if (columnTypeName.equals("null")) {
						valuesArray[j] = null;
						j++;
					}
				}
				
				//Check if the column contains the desired HashMap key
				if (i == key) {
					
					//Get the name of the column
					String columnName = input.getMetaData().getColumnName(i);
					
					//Get the SQL type of the column
					int columnType = input.getMetaData().getColumnType(i);
					
					//Lookup an approximation of the appropriate Java 
					String columnTypeName = _columnDataTypeChecker(columnType);			
					
					//Assign the value of the HashMap key
					if (columnTypeName.equals("int")) {
						hashMapKey = input.getInt(columnName);
					}
				}
				
				//Add the values to the HashMap
				output.put(hashMapKey, valuesArray);
			}
		}
		return output;
	}
		
	//Rough conversion of SQL Data Type to Java Data Type. Decided against the use of fall through to improve ease of reading.
	private static String _columnDataTypeChecker(int SQLType) {
		
		String JavaType;
		
		//This is clearly not an exhaustive list, and was designed for ease of use, rather than attempting
		//to map to types that although more similar to the SQL type in question, are not commonly used.
		switch ( SQLType ) {
			case -6:  JavaType = "byte"; break;
			case -1: JavaType = "String"; break;
			case 0:  JavaType = "null"; break; //null type; this is not the same as the column being empty
			case 1: JavaType = "char"; break;
			case 2: JavaType = "java.math.BigDecimal"; break;
			case 3: JavaType = "java.math.BigDecimal"; break;
			case 4: JavaType = "int"; break;
			case 6: JavaType = "double"; break;
			case 8: JavaType = "double"; break;
			case 12: JavaType = "String"; break;
			
			//Uncommon or unknown data types.
			default: return null;
	}
		
		//Return the data type as a String
		return JavaType;
		
	}
}
