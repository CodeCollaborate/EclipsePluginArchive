package codecollaborateeclipse.listener;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.texteditor.ITextEditor;
import codecollaborateeclipse.PatchHandler;


public class EditorListener {
	
	public EditorListener() {
		
	}
	
	public void listen() {
		
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editor.getEditorInput();
		IDocument doc = ((ITextEditor)editor).getDocumentProvider().getDocument(input);
		
		doc.addDocumentListener(
			new IDocumentListener() {
			String oldDoc = "";
			PatchHandler patchy = new PatchHandler();
			//Arghh, matey!
			
	        @Override
	        public void documentChanged(DocumentEvent event) {
	            //System.out.println("Change happened: " + event.toString());
	        	String p = patchy.createPatchString(oldDoc, doc.get());
	        	System.out.println(p);
	        }

	        @Override
	        public void documentAboutToBeChanged(DocumentEvent event) {
	            //System.out.println("I predict that the following change will occur: " + event.toString());
	        	oldDoc = doc.get();
	        }
			});
		
		
//		input.
		
	}
	
}
