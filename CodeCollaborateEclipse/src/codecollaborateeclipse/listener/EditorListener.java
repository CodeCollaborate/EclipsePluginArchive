package codecollaborateeclipse.listener;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.text.edits.ISourceModifier;
import org.eclipse.text.edits.TextEditProcessor;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filebuffers.IFileBufferManager;;


public class EditorListener {
	
	public EditorListener() {
		
	}
	
	public void listen() {
		
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		IEditorInput input = editor.getEditorInput();
		IFileBufferManager buffManager = editor.getAdapter(IFileBufferManager.class);
		IFileBuffer[] buff = buffManager.getFileBuffers();
		
		
		
//		input.
		
	}
	
}
