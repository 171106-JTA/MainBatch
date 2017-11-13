package com.revature.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOExample {

	private final String FILENAME = "exampleFile.txt"; 
	
	FileInputStream fis;
	FileOutputStream fos;
	FileReader fr;
	FileWriter fw;
	BufferedReader br;
	BufferedWriter bw;
	
	public static void main(String[] args) {
		FileIOExample fioe = new FileIOExample();
		try {
			fioe.writeFile(fioe.FILENAME);
			fioe.readFile(fioe.FILENAME);
			fioe.readFileViaFileReader(fioe.FILENAME);
			fioe.bufferedReadWrite(fioe.FILENAME);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	

	public void writeFile(String fileName) throws IOException { 
		char rand;
		
		/*
		 * FileInputStream will read in data through stream, one byte at a time. 
		 * 
		 */
		
		try {
			fos = new FileOutputStream(fileName);
			
			// 30 random letters on 4 different lines
			for (int i = 0; i < 4; i++)
			{
				for (int j = 0; j < 30; j++)
				{
					rand = (char) ((Math.random() * 26) + 'A');
					fos.write((byte)rand);				
					
				}
				fos.write('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
			//always close your resources!
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	public void readFile(String fileName) throws IOException { 
		
		/*
		 * FileInputStream will read in data through stream, one byte at a time. 
		 * 
		 */
		
		try {
			fis = new FileInputStream(fileName);
			
			byte in;
			// When there are no more bytes left, -1 is returned
			while ((in = (byte)fis.read()) != -1)
			{
				System.out.print((char)in);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
			//always close your resources!
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	public void readFileViaFileReader(String fileName) throws IOException{ 
		try
		{
			/*
			 * FileReader,FileWriter serve the same purpose as input streams: they open a stream and take in 
			 * 	data/output data. However, the difference is a FileReader,FileWriter will parse data one character
			 * 	(16 bits) at a time. Though this may carry a negligible overhead, it does provide a safer and more 
			 * 	efficient means of reading data.
			 */
			fr = new FileReader(fileName);
			fw = new FileWriter(fileName + "_lowercase");
			
			int in;
			while((in = fr.read()) != -1)
			{
				if (in == 10) {
					fw.write((char)(in)); // I don't want to change newline
				}
				else
				{
					fw.write((char)(in+32)); // makes letter lowercase
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		finally
		{
			if (fr != null) {
				fr.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
	}
	/**
	 * This method will read from a file as indicated by the input parameter.
	 * It will take the contents of the file and save it as a new file
	 * This method utilizes BufferedRead/BufferedWrite streams.
	 * 
	 * @param fileName - Directory of the file to be read
	 * @throws IOException - Please handle this
	 */
	public void bufferedReadWrite(String fileName ) throws IOException {
		try
		{
			br = new BufferedReader(new FileReader(fileName));
			bw = new BufferedWriter(new FileWriter(fileName + "_Copy"));
			
			String input = "";
			/*
			 * readLine() works like a one-way iterator. (Think of a singly linked list.)
			 */
			while ((input = br.readLine()) != null) {
				input += '\n';
				bw.write(input);
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null) { 
				br.close();
				
			}
			if (bw != null) { 
				bw.close();
			}
		}
	}
}
