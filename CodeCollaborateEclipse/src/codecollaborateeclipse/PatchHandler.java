package codecollaborateeclipse;

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
	
	public String recievePatch(String patch) {
		//TODO
		//Implement this shit later
		return "";
	}
	
}
