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
	
	public String recievePatch(String message, String text) {
		//parse JSON data
		try{
		ObjectMapper mapper = new ObjectMapper();
		Notification notification = mapper.readValue(message, Notification.class);
		
		//deal with patch text
		Patch p = dmp.patch_fromText(notification.getData().Changes).get(0);
		LinkedList<Patch> patchList = new LinkedList<Patch>();
		patchList.add(p);
		Object[] patchResult = dmp.patch_apply(patchList, text);
		
		boolean[] patchesApplied = (boolean[]) patchResult[1];
		
		for(boolean patchAppliedResult : patchesApplied){
			if(!patchAppliedResult){
				System.out.println("Failed to apply a patch");
			}
		}
		
		return (String) patchResult[0];
		}catch(Exception e){
			return text;
		}
	}
	
}
