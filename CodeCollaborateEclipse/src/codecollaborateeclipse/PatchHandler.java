package codecollaborateeclipse;

import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codecollaborateeclipse.models.Notification;
import google.diffmatchpatch.*;
import google.diffmatchpatch.diff_match_patch.Patch;

/**
 * PatchHandler
 * 
 * This class contains methods that handle incoming and outgoing patches.
 *
 */
public class PatchHandler {
	
	private diff_match_patch dmp = new diff_match_patch();
	
	public PatchHandler() {
		
	}
	
	public Patch createPatch(String text1, String text2) {
		
		Patch result = dmp.patch_make(text1, text2).getFirst();
		
		return result;
		
	}
	
	public String createPatchString(String text1, String text2) {
		
		Patch result = dmp.patch_make(text1, text2).getFirst();
		return result.toString();
		
	}
	
	public Object[] recievePatch(String message, String text) {
		
		Object[] results = new Object[4];
		//parse JSON data
		try {
			System.out.println(message);
			//deal with patch text
			Patch p = dmp.patch_fromText(message).get(0);
			String prefix;
			String operation;
			if (p.diffs.size() == 1) {
				prefix = "";
				operation = p.diffs.getFirst().operation.toString();
			} else {
				prefix = p.diffs.getFirst().text;
				operation = p.diffs.getLast().operation.toString();
			}
			String suffix = p.diffs.getLast().text;
			System.out.println("Change to be made: " + suffix);
			System.out.println("Operation: " + operation);
			System.out.println("Context: " + prefix);
			int insertPos = dmp.match_main(text, prefix, p.start1);
			int start = insertPos + prefix.length();
			int end = insertPos + prefix.length() + suffix.length();
			
//			LinkedList<Patch> patchList = new LinkedList<Patch>();
//			patchList.add(p);
//			Object[] patchResult = dmp.patch_apply(patchList, text);
//			
//			boolean[] patchesApplied = (boolean[]) patchResult[1];
//			
//			for(boolean patchAppliedResult : patchesApplied) {
//				if(!patchAppliedResult) {
//					System.out.println("Failed to apply a patch");
//				}
//			}
			results[0] = suffix;
			results[1] = operation;
			results[2] = start;
			results[3] = end;
			return results;
			
		} catch(Exception e) {
			e.printStackTrace();
			return results;
		}
		
	}
	
}
