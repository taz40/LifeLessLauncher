/*
 * Copyright (c) 2004-2010, P. Simon Tuffs (simon@simontuffs.com)
 * All rights reserved.
 *
 * See the full license at http://one-jar.sourceforge.net/one-jar-license.html
 * This license is also included in the distributions of this software
 * under doc/one-jar-license.txt
 */
package lightsoutgaming.lifeless.launcher.main;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
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

import javax.media.j3d.GraphicsConfigTemplate3D;

import taz40.lightsoutgamingengine.V1.Game;

public class LauncherMain {
    
	static URL base;
	
    public static void main(String args[]) {
        if (args == null)
            args = new String[0];
        System.out.println("Launcher main entry point, args=" + Arrays.asList(args));
        new LauncherMain().run();
    }
    
    // Bring up the application: only expected to exit when user interaction
    // indicates so.
    public void run() {
    	try
    	{
    	   GraphicsConfigTemplate3D gconfigTemplate = new GraphicsConfigTemplate3D();
    	   GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getBestConfiguration(gconfigTemplate);
    	}
    	catch (Error e) // You shouldn't normally catch java.lang.Error... this is an exception
    	{
    		File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess");
	    	f.mkdirs();
	    	f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\java3d.exe");
	    	if(!f.exists()){
	    		System.out.println("f no exists");
	    		try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    	try {
	    	     URL url = new URL(base, "java3d.exe");
	    	    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
	    	    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\java3d.exe");
					fos.getChannel().transferFrom(rbc, 0, 1 << 24);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		try {
				Runtime.getRuntime().exec(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\java3d.exe", null, new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		System.out.println("hi");
    		//f.delete();
    	}
    	
    	Game game = new Game(800,600,"Lifeless",30);
    	game.getScreenFactory().showScreen(new mainscreen(game.getScreenFactory()));
    }
    
    public static void update(){
    	try{
    		base = new URL("https://dl.dropboxusercontent.com/u/36450292/LifeLess/java/");
    	}catch(MalformedURLException e){
    		e.printStackTrace();
    	}
        System.out.println("Launcher main is running");
        // Implement the functionality of the application. 
        File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\version.txt");
        if(f.exists()){
        	System.out.println("f exists");
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
        	if(version.equals(getVersion())){
        		launch();
        	}else{
        		System.out.println("on PC: "+version);
        		System.out.println("on DB: "+getVersion());
        		System.out.println("initiating Yes/No");
        		Game yesno = new Game(310, 200, "Update?", 30);
        		yesno.getScreenFactory().showScreen(new yesnoscreen(yesno.getScreenFactory()));
        		try {
					yesno.t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }else{
        	install();
        }
        System.out.println("Launcher OK.");
    }
    
    public static void install(){
    	try {
			URL versionfile = new URL(base, "downloads.txt");
			BufferedReader in = new BufferedReader(
			new InputStreamReader(versionfile.openStream()));
			String line = in.readLine();
			while(line != null){
				File f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\"+line);
		    	f.mkdirs();
		    	f = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\"+line);
		    	if(!f.exists()){
		    		System.out.println("f no exists");
		    		try {
						f.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	try {
		    	     URL url = new URL(base, line);
		    	    ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		    	    FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\"+line);
						fos.getChannel().transferFrom(rbc, 0, 1 << 24);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	launch();
    }
    
    public static String getVersion(){
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
    
    public static void launch(){
    	try {
			Process proc = Runtime.getRuntime().exec("java -jar " + System.getProperty("user.home") + "\\AppData\\Roaming\\.LifeLess\\LifeLess.jar");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    

}
