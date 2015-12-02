package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Fio {
	private static PrintStream oldout;
	
	public static void setOut(String file){
		try {
			oldout = System.out;
			File f = new File(file);
			f.delete(); //delete the exist one
			f = new File(file);
			
			System.setOut(new PrintStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void resetOut(){
		System.setOut(oldout);
	}
	
	public static Boolean isFileExist(String path){
		File file = new File(path);
		if(file.exists()) return true;
		
		return false;
	}
	
	public static String[] readfile(String filename) throws IOException{
    	FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }
}
