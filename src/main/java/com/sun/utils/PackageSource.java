package com.sun.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PackageSource {

	static FileWriter fw;
	static FileReader fr;
	static  BufferedReader br;// = new
 
	public static boolean Begin(int step, String test) throws IOException
	{
		try{
			
			if(getFile(test).exists()){
				fr = new FileReader(getFile(test).getAbsoluteFile());
				char Stat = Status();
				int Status = ChartoInt(Stat);				
				fr.close();
				if(Status >= step)
					return false;
					else
					return true;
			}
			else
				{
				getFile(test).createNewFile();
				return true;
			}		
			
		}
		
		catch(IOException e){return false;}
		
	}
	
	public static void delete(String test){
			if(getFile(test).exists()){
				File FileObject;
				FileObject= new File(test+ ".txt");
				FileObject.delete();
			}		
	}
	
	public static void End(int step,Boolean Result,String test){
		try
		{
			
			if (Result)
			{	
				fw = new FileWriter(getFile(test).getAbsoluteFile());				
				fw.write(InttoChar(step));
				fw.flush();
				fw.close();
			}
			else
			System.exit(0); 
		}
		catch(IOException e){}		
	}
	
	public static char Status()
	{
		try{
			br = new BufferedReader(fr) ;
			char toReturn = (char)br.read(); 
			br.close();
			return toReturn;
		}
		catch(Exception E){
			return '0';
		}		
	}
	
	/*----------------------------------------------------------------*/
	/*Creation of file------------------------starts here.*/
	private static String getFileName()
	{
		StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
		StackTraceElement main = stack[stack.length - 1];
		return  main.getMethodName();
	}
	
	/*returns a file with filename as the name of the test*/
	private static File getFile(String test)
	{
		
		File FileObject;		
		//FileObject= new File(getFileName() + ".txt");
		FileObject= new File(test+ ".txt");
		return FileObject;
	}
	/*Creation of file------------------------ends here.*/
	/*----------------------------------------------------------------*/
	/*-------------itoa-----------------------------------*/
	public static int ChartoInt(char number)
	{
		int i=number-48;	
		return i;
	}
	public static char InttoChar(int number)
	{
		char c = (char)(48+number);
		return c;
	}
	/*-------------itoa-----------------------------------*/
}