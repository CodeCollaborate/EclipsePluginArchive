package codecollaborateeclipse.document;

import java.io.IOException;
import java.util.LinkedList;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import codecollaborateeclipse.models.Notification;
import google.diffmatchpatch.*;
import google.diffmatchpatch.DiffMatchPatch.Patch;
import google.diffmatchpatch.DiffMatchPatch.PatchRange;

/**
 * PatchHandler
 * 
 * This class contains methods that handle incoming and outgoing patches.
 *
 */
public class PatchHandler {
	
	private DiffMatchPatch dmp = new DiffMatchPatch();
	
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
		
		try {
			
			System.out.println(message);
			
			//deal with patch text and apply patch
			Patch p = dmp.patch_fromText(message).get(0);
			LinkedList<Patch> patches = new LinkedList<Patch>();
			patches.add(p);
			Object[] appliedPatchResults = dmp.patch_apply(patches, text);
			
			//check boolean array for fuck-ups
			boolean[] applied = (boolean[]) appliedPatchResults[1];
			for (boolean b : applied) {
				if (!b) {
					System.out.println("Oopsie! Your patch didn't apply!");
					throw new Exception();
				}
			}
			
			//pull out patch ranges
			PatchRange[] ranges = (PatchRange[]) appliedPatchResults[2];
			
			//deal with Diff cases
			String operation;
			String edit;
			
			if (p.diffs.size() == 1) {
				operation = p.diffs.getFirst().operation.toString();
				edit = p.diffs.getFirst().text;
			} else if (p.diffs.size() == 2) {
				if (p.diffs.getFirst().operation.toString().equals("EQUAL")) {
					operation = p.diffs.getLast().operation.toString();
					edit = p.diffs.getLast().text;
				} else {
					operation = p.diffs.getFirst().operation.toString();
					edit = p.diffs.getFirst().text;
				}
			} else {
				operation = p.diffs.get(1).operation.toString();
				edit = p.diffs.get(1).text;
			}
			
			//some debug print statements
//			System.out.println("Change to be made: " + edit);
//			System.out.println("Operation: " + operation);
//			System.out.println("Context: " + prefix);
//			int insertPos = dmp.match_main(text, prefix, p.start1);
			int start = ranges[0].getStartPos();
			int end = ranges[0].getEndPos();
			
			results[0] = edit;
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
