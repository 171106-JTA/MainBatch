package d2.revature.pillars;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SerializationDecryption {

	static ObjectInputStream ois;
	static Employee emp;
	
	public static void main(String[] args) throws Exception{
		
		try {
			ois = new ObjectInputStream(new FileInputStream("employee.ser"));
			emp = (Employee)ois.readObject();
			
			System.out.println(emp);
		} catch(IOException e){
			
		} catch(ClassNotFoundException e){
			
		} finally {
			if(ois != null){
				ois.close();
			}
		}

	}

}
