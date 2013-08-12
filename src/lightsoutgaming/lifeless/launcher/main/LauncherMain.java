/*
 * Copyright (c) 2004-2010, P. Simon Tuffs (simon@simontuffs.com)
 * All rights reserved.
 *
 * See the full license at http://one-jar.sourceforge.net/one-jar-license.html
 * This license is also included in the distributions of this software
 * under doc/one-jar-license.txt
 */
package lightsoutgaming.lifeless.launcher.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;

public class LauncherMain {
    
	URL base;
	
    public static void main(String args[]) {
        if (args == null)
            args = new String[0];
        System.out.println("Launcher main entry point, args=" + Arrays.asList(args));
        new LauncherMain().run();
    }
    
    // Bring up the application: only expected to exit when user interaction
    // indicates so.
    public void run() {
    	try{
    		base = new URL("https://dl.dropboxusercontent.com/u/36450292/LifeLess/java/");
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}
        System.out.println("Launcher main is running");
        // Implement the functionality of the application. 
        File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\vertion.txt");
        if(f.exists()){
        	String version = null;
        	try {
				FileInputStream fis = new FileInputStream(f);
				InputStreamReader in = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(in);
				version = br.readLine();
				br.close();
				in.close();
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(version == getVersion()){
        		launch();
        	}else{
        		install();
        	}
        }else{
        	install();
        }
        System.out.println("Launcher OK.");
    }
    
    public void install(){
    	File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess");
    	f.mkdirs();
    	f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\version.txt");
    	if(!f.exists()){
    		try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\LifeLess.jar");
    	if(!f.exists()){
    		try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	try {
    	     URL url = new URL(base, "version.txt");
    	    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
    	    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\version.txt");
				fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	try {
   	     URL url = new URL(base, "LifeLess.jar");
   	    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
   	    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\LifeLess.jar");
				fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	launch();
    }
    
    public String getVersion(){
    	String version = "null";
    	try {
			URL versionfile = new URL(base, "version.txt");
			BufferedReader in = new BufferedReader(
			new InputStreamReader(versionfile.openStream()));
			version = in.readLine();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return version;
    }
    
    public void launch(){
    	try {
			Process proc = Runtime.getRuntime().exec("java -jar " + System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\LifeLess.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
