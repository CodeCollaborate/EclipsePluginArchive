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
		//parse JSON data
		try {
			System.out.println(message);
			//deal with patch text
			Patch p = dmp.patch_fromText(message).get(0);
			LinkedList<Patch> patches = new LinkedList<Patch>();
			Object[] appliedPatchResults = dmp.patch_apply(patches, text);
			//TODO: check boolean array for fuck-ups
			
			PatchRange[] ranges = (PatchRange[]) appliedPatchResults[2];
			String prefix;
			String operation;
			String suffix;
			if (p.diffs.size() == 1) {
				prefix = "";
				operation = p.diffs.getFirst().operation.toString();
				suffix = p.diffs.getLast().text;
			} else if (!p.diffs.getFirst().operation.toString().equals("EQUAL")) {
				prefix = "";
				operation = p.diffs.getFirst().operation.toString();
				suffix = p.diffs.getLast().text;
			} else if (!p.diffs.getLast().operation.toString().equals("EQUAL")) {
				prefix = p.diffs.getFirst().text;
				operation = p.diffs.get(1).operation.toString();
				suffix = p.diffs.getLast().text;
			} else {
				prefix = p.diffs.getFirst().text;
				operation = p.diffs.get(1).operation.toString();
				suffix = p.diffs.getLast().text;
			}
			System.out.println("Change to be made: " + suffix);
			System.out.println("Operation: " + operation);
			System.out.println("Context: " + prefix);
//			int insertPos = dmp.match_main(text, prefix, p.start1);
			int start = ranges[0].getStartPos();
			int end = ranges[0].getEndPos();
			
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
