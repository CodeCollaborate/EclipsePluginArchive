package codecollaborateeclipse.resources;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import codecollaborateeclipse.models.FileMetadata;

public final class ResourceManager {
	
	public static ArrayList<FileMetadata> getFileMetadata() {
		try {
			PrintWriter writer = new PrintWriter("samplefile.txt", "UTF-8");
			writer.println("hi");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
