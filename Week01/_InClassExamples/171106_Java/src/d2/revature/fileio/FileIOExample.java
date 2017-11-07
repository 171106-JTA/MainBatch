package d2.revature.fileio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIOExample {

	private final String FILENAME = "examplefile.txt";
	
	FileInputStream fis;
	FileOutputStream fos;
	
	public static void main(String[] args) {
		FileIOExample fioe = new FileIOExample();
		try{
			//fioe.writeFile("bobbert");
			fioe.readFile("bobbert");
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	
	public void writeFile(String fileName) throws IOException{
		char rand;

		/*
		 * FileInputstream will read in data through a stream,one byte at a time.
		 */
		
		try {
			fos = new FileOutputStream(fileName);
			
			//30 random letters on 4 different lines
			for(int i = 0; i < 4; i++){
				for(int j = 0; j < 30; j++){
					rand = (char)((Math.random() * 26) + 'A');
					fos.write((byte)rand);
				}	
				fos.write('\n');
			}

		} catch (IOException e) {
			
			e.printStackTrace();
			//Always close your resources
		} finally {
			if(fos != null){
				fos.close();
			}
		}
		
	}
	
	public void readFile(String fileName) throws IOException{
		/*
		 * FileInputstream will read in data through a stream,one byte at a time.
		 */
		
		try {
			fis = new FileInputStream(fileName);
			
			byte in;
			//When there are no more bytes left, -1 is returned.
			while((in = (byte)fis.read())!= -1){
				System.out.println(in);
			}

		} catch (IOException e) {
			
			e.printStackTrace();
			//Always close your resources
		} finally {
			if(fis != null){
				fis.close();
			}
		}
		
	}


}
