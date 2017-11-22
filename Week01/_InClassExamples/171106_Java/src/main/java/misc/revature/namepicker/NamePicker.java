package misc.revature.namepicker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class NamePicker {

	static BufferedReader br;
	static ArrayList <String>nameList;
	static final String inputFile = "171106_Names2";
	
	public static void main(String[] args) throws IOException {		
		try {
			br = new BufferedReader(new FileReader(inputFile));
			
			String in;
			nameList = new ArrayList<>();
			while((in = br.readLine())!=null){
				nameList.add(in);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(br !=null){
				br.close();
			}
		}
		
		
		
		int size = nameList.size();
		int rand;
		for(int i = 0; i < size; i++){
			rand = (int)(Math.random()*(size-i));
			System.out.println((i+1) + ":\t" + nameList.get(rand));
			nameList.remove(rand);
			
		}

	}

}
