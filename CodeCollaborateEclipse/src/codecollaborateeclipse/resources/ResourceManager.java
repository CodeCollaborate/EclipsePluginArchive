package codecollaborateeclipse.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.eclipse.core.resources.ResourcesPlugin;

import codecollaborateeclipse.models.FileMetadata;

public final class ResourceManager {
		
	public ResourceManager() {
		
	}
	
	public static String getWorkingDirectory() {
		
		return ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		
	}
	
	public static ArrayList<FileMetadata> getFileMetadata() {
		try {
			String dir = getWorkingDirectory() + "/.metadata/.codecollaborate/metadata.txt";
			File metadata = new File(dir);
			if (!metadata.exists()) {
				metadata.getParentFile().mkdirs();
				metadata.createNewFile();
			}
			PrintWriter writer = new PrintWriter(metadata);
			writer.println("hi");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
