package com.revature.display;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDisplays <T>{
	
	BufferedReader myBufferReader;
	BufferedWriter myBufferWriter;
	
	/*Format the date according to the chosen format*/
	public String formatDate(Date date, String format) {
		SimpleDateFormat outFormat = new SimpleDateFormat(format);		
		return outFormat.format(date);
	}
	
	/*This method reverses formatDate to retrieve the Date format*/
	public Date reverseFormatDate(String date) {
		SimpleDateFormat simpleDate = new SimpleDateFormat("MM/dd/yyyy");	
		Date val = new Date();
		try {
			val = simpleDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return val;
	}
			
	public static ObjectOutputStream myOutStream;
	
	/*Serialization method for a give object into a specified file name*/
	public void serialize(ArrayList<T> tobeSerialized, String fileName) {		
		try {
			myOutStream = new ObjectOutputStream(new FileOutputStream(fileName));
			myOutStream.writeObject(tobeSerialized);
			} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}	
		
	@SuppressWarnings("unchecked")
	public ArrayList<T> readObject(String fileName) {
		ObjectInputStream myInStream;	
		ArrayList<T> myObjectList = new ArrayList<>();
		try {
			myInStream = new ObjectInputStream(new FileInputStream(fileName));
			myObjectList = (ArrayList<T>) myInStream.readObject();
			//myTObject = (T)myInStream.readObject();
			} 
		catch (FileNotFoundException e) {
			e.printStackTrace();} 
		catch (IOException e) {
			e.printStackTrace();} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myObjectList;
	}	
}