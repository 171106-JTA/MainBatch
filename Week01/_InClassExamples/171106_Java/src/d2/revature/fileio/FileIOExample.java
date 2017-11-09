package d2.revature.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
<<<<<<< HEAD
=======
import java.io.File;
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOExample {

	private final String FILENAME = "examplefile.txt";

	FileInputStream fis;
	FileOutputStream fos;
	FileReader fr;
	FileWriter fw;
<<<<<<< HEAD
	BufferedReader br; // Preferred way of reading and
	BufferedWriter bw; // writing to the file.

	public static void main(String[] args) {
		FileIOExample fioe = new FileIOExample();
		try {
=======
	BufferedReader br;
	BufferedWriter bw;
	
	public static void main(String[] args) {
		FileIOExample fioe = new FileIOExample();
		try{
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
			fioe.writeFile("bobbert");
			fioe.readFile("bobbert");
			fioe.readFileViaFileReader("bobbert");
			fioe.bufferedReadWrite("bobbert");
<<<<<<< HEAD
		} catch (IOException e) {
=======
		}catch(IOException e){
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
			e.printStackTrace();
		}

	}

	public void writeFile(String fileName) throws IOException {
		char rand;

		/*
		 * FileInputstream will read in data through a stream,one byte at a time.
		 */

		try {
			fos = new FileOutputStream(fileName);

			// 30 random letters on 4 different lines
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 30; j++) {
					rand = (char) ((Math.random() * 26) + 'A');
					fos.write((byte) rand);
				}
				fos.write('\n');
			}

		} catch (IOException e) {

			e.printStackTrace();
			// Always close your resources
		} finally {
			if (fos != null) {
				fos.close();
			}
		}

	}

	public void readFile(String fileName) throws IOException {
		/*
		 * FileInputstream will read in data through a stream,one byte at a time.
		 */

		try {
			fis = new FileInputStream(fileName);

			byte in;
<<<<<<< HEAD
			// When there are no more bytes left, -1 is returned.
			while ((in = (byte) fis.read()) != -1) {
				System.out.println(in);
=======
			//When there are no more bytes left, -1 is returned.
			while((in = (byte)fis.read())!= -1){
				System.out.print((char)in);
>>>>>>> 908c4a08b3cf9c5cb65e60015f7c54cf564145ce
			}
			System.out.println();

		} catch (IOException e) {

			e.printStackTrace();
			// Always close your resources
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}
	
	public void readFileViaFileReader(String fileName) throws IOException{
		
		try {
			/*
			 * FileReader and FileWriter serve the same as input streams. They open a stream
			 * and take in data/output data. However, the difference being that FileWriter and Reader
			 * will parse data one character at a time. (16 bits at a time). Though this may carry
			 * a negligible overhead, it does provide a safer and more efficient means of reading data.
			 */
			
			fr = new FileReader(fileName);
			fw = new FileWriter(fileName + "_lowercase");
			
			int in;
			while((in = fr.read())!= -1){
				if(in == 10){
					fw.write((char)(in)); //I don't want to change a newline
				} else {
					fw.write((char)(in + 32)); //This makes a letter lowercase
				}
			}
			
		} catch (IOException e) {

			e.printStackTrace();
			
		} finally {
			if(fr!=null){
				fr.close();
			}
			if(fw!=null){
				fw.close();
			}
		}	
	}
	
	/**
	 * This method will read from a file as indicated by the input parameter.
	 * It will take the contents of the file and save it as a new file.
	 * This method utilizes bufferedRead/Write Streams.
	 * 
	 * @param fileName - Directory of the file to be read
	 * @throws IOException - Please handle this.
	 */
	public void bufferedReadWrite(String fileName) throws IOException{
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			bw = new BufferedWriter(new FileWriter(fileName + "_Copy"));
			
			String input = "";
			/*
			 * readLine() works like a one-way iterator. 
			 * Once you call readLine(), it provides the line that it is on, then moves it's
			 * "pointer" to the next line.
			 */
			while((input = br.readLine())!=null){
				input += '\n';
				bw.write(input);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally{
			if(br!=null){
				br.close();
			}
			if(bw!=null){
				bw.close();
			}
		}
		
	}

	public void readFileViaFileReader(String fileName) throws IOException {
		try {
			/*
			 * FileReader and FileWriter serve the same as input streams. They open a stream
			 * and take in data/output data. However, the difference being that FileWriter
			 * and Reader will parse data one character at a time. (16 bits at a time).
			 * Though this may carry a negligible overhead, it does provide a safer and more
			 * efficient means of reading data.
			 * 
			 */
			fr = new FileReader(fileName);
			fw = new FileWriter(fileName + "_lowerCase");
			int in;
			while ((in = fr.read()) != -1) {
				if (in == 10) {
					fw.write((char) (in)); // Do not change newline
				} else {
					fw.write((char) (in + 32)); // This makes letters lower case
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (fw != null) {
				fw.close();
			}
		}

	}
	
	/**
	 * This method will read from a file as indicated by the input parameter
	 * It will take the contents of the file and save it as a new file
	 * This method utilizes bufferedRead/Write Streams.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void bufferedReadWrite(String fileName) throws IOException {
		try {
			br = new BufferedReader(new FileReader(fileName));
			bw = new BufferedWriter(new FileWriter(fileName+ "_Copy"));
			
			String input = "";
			while((input = br.readLine()) != null) {
				input +='\n';
				bw.write(input);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(br != null) {
				br.close();
			}
			if(bw != null) {
				bw.close();
			}
		}
		
	}
}
