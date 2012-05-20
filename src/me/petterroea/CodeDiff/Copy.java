package me.petterroea.CodeDiff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;

import javax.swing.JOptionPane;

public class Copy {
	public static void copy()
	{
		File src = new File("src");
		if(!src.exists())
		{
			JOptionPane.showMessageDialog(null,
			    "I did not find a folder called 'src'. Are you sure you have placed me in the root directory of a project?",
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!src.isDirectory())
		{
			JOptionPane.showMessageDialog(null,
				    "src is a file",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
				return;
		}
		File copyTo = new File("changed_files");
		if(!copyTo.exists())
		{
			copyTo.mkdirs();
		}
		if(!copyTo.isDirectory())
		{
			copyTo.delete();
			copyTo.mkdir();
		}
		for(int i = 0; i < src.listFiles().length; i++)
		{
			if(src.listFiles()[i].isDirectory())
			{
				doFolder(src.listFiles()[i]);
			}
			else
			{
				doFile(src.listFiles()[i]);
			}
		}
	}
	private static void doFolder(File folder)
	{
		for(int i = 0; i < folder.listFiles().length; i++)
		{
			if(folder.listFiles()[i].isDirectory())
			{
				doFolder(folder.listFiles()[i]);
			}
			else
			{
				doFile(folder.listFiles()[i]);
			}
		}
	}
	private static void doFile(File file)
	{
		try {
			if(!CodeDiff.md5.containsKey(getId(file)))
			{
				copyFile(file);
				System.out.println("File " + file.getName()+ " is new!");
			}
			else if(!CodeDiff.md5.getString(getId(file)).equals(getMD5(file.getAbsolutePath())))
			{
				System.out.println("File " + file.getName()+ " has changed!");
				copyFile(file);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void copyFile(File file)
	{
		//Insert code here
		File src = new File("src");
		File newpos = new File("changed_files");
		String path = file.getAbsolutePath().replace(src.getAbsolutePath(), newpos.getAbsolutePath());
		System.out.println("The files new location will be: " + path);
		File newFile = new File(path);
		newFile.getParentFile().mkdirs();
		copyFile(file, newFile);
	}
	private static void copyFile(File srFile, File dtFile){
		  try{
		  File f1 = srFile;
		  File f2 = dtFile;
		  InputStream in = new FileInputStream(f1);
		  
		  //For Append the file.
		//  OutputStream out = new FileOutputStream(f2,true);

		  //For Overwrite the file.
		  OutputStream out = new FileOutputStream(f2);

		  byte[] buf = new byte[1024];
		  int len;
		  while ((len = in.read(buf)) > 0){
		  out.write(buf, 0, len);
		  }
		  in.close();
		  out.close();
		  System.out.println("File copied.");
		  }
		  catch(FileNotFoundException ex){
		  System.out.println(ex.getMessage() + " in the specified directory.");
		  System.exit(0);
		  }
		  catch(IOException e){
		  System.out.println(e.getMessage());  
		  }
		  }
	public static String getId(File file)
	{
		File src = new File("src");
		return file.getAbsolutePath().replace(src.getAbsolutePath(), "file:");
	}
	public static byte[] createChecksum(String filename) throws Exception {
	       InputStream fis =  new FileInputStream(filename);

	       byte[] buffer = new byte[1024];
	       MessageDigest complete = MessageDigest.getInstance("MD5");
	       int numRead;

	       do {
	           numRead = fis.read(buffer);
	           if (numRead > 0) {
	               complete.update(buffer, 0, numRead);
	           }
	       } while (numRead != -1);

	       fis.close();
	       return complete.digest();
	   }

	   // see this How-to for a faster way to convert
	   // a byte array to a HEX string
	   public static String getMD5(String filename) throws Exception {
	       byte[] b = createChecksum(filename);
	       String result = "";

	       for (int i=0; i < b.length; i++) {
	           result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
	       }
	       return result;
	   }
}
