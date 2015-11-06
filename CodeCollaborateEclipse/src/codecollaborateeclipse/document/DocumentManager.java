package codecollaborateeclipse.document;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;

import javax.print.Doc;

import codecollaborateeclipse.Storage;
import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.events.DocumentChangedListener;
import codecollaborateeclipse.models.Response.File;
import codecollaborateeclipse.models.Response.PatchData;
import codecollaborateeclipse.models.Response.Project;
import codecollaborateeclipse.resources.ResourceManager;

public class DocumentManager {

	private ArrayList<DocumentChangedListener> listeners = new ArrayList<DocumentChangedListener>();
	private PatchHandler patchy;
	private IEditorPart editor;
	private IEditorInput input;
	private IDocument doc;

	private IDocumentListener docListener;
	private boolean closed;
	// Arghh, matey!

	public DocumentManager() {
		this.patchy = new PatchHandler();
		editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		input = editor.getEditorInput();
		doc = ((ITextEditor) editor).getDocumentProvider().getDocument(input);
	}

	public void listen() {
		closed = false;
		if (docListener == null) {
			docListener = new IDocumentListener() {
				String oldDoc = "";
				String fileId = getActiveFileId();
				@Override
				public void documentChanged(DocumentEvent event) {
					// System.out.println("Change happened: " + event.toString());
					String p = patchy.createPatchString(oldDoc, doc.get());
					DocumentManager.this.notify(fileId, p);
					// System.out.println(p);
				}
	
				@Override
				public void documentAboutToBeChanged(DocumentEvent event) {
					// System.out.println("I predict that the following change will
					// occur: " + event.toString());
					oldDoc = doc.get();
				}
			};
			this.doc.addDocumentListener(docListener);
		}
	}
	
	public String getFullPathOfActiveDocument() {
		
		String path = ((IFileEditorInput) this.input).getFile().getRawLocation().toString();
		// + "/" + ((IFileEditorInput) this.input).getFile().getName();
		return path;
		
	}
	
	public String getRelativePathOfActiveDocument() {
		String full = getFullPathOfActiveDocument();
		String wd = ResourceManager.getWorkingDirectory();
		return full.substring(wd.length()+1, full.length());
	}
	
	public String getActiveProjectId() {
		String relativePath = getRelativePathOfActiveDocument();
		for (Project project : Storage.getInstance().getProjectsArray()) {
			if (project.getFiles() != null) {
				for (File f : project.getFiles()) {
					if (relativePath.equals(project.getName() + "/" + f.getPath() + f.getName()))
						return project.getId();
				}
			}
		}
		return null;
	}
	
	public String getActiveFileId() {
		String relativePath = getRelativePathOfActiveDocument();
		for (Project project : Storage.getInstance().getProjectsArray()) {
			if (project.getFiles() != null) {
				for (File f : project.getFiles()) {
					if (relativePath.equals(project.getName() + "/" + f.getPath() + f.getName()))
						return f.getId();
				}
			}
		}
		return null;
	}

	public void recievePatch(String msg) {

		Object[] textEdit = this.patchy.recievePatch(msg, this.doc.get());
		String edit = (String) textEdit[0];
		String operation = (String) textEdit[1];
		int start = (int) textEdit[2];
		int end = (int) textEdit[3];
		int length = end - start;
		//System.out.println("Replace \"" + doc.get() + "\" with \"" + newDocText + "\"");
		
		Display.getDefault().syncExec(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				doc.removeDocumentListener(docListener);
					try {
						if (operation.equals("DELETE"))
							doc.replace(start, edit.length(), "");
						else
							doc.replace(start, 0, edit);
					} catch (BadLocationException e) {
						// TODO Auto-generated catch block
						
					}
				doc.addDocumentListener(docListener);
				
			}
			
		});

	}

	public void close() {
		System.out.println("closing");
		closed = true;
		doc.removeDocumentListener(docListener);
		docListener = null;
	}
	
	public void addDocumentChangedListener(DocumentChangedListener listener) {
		listeners.add(listener);
	}
	
	public void notify(String resId, String patch) {
		if (patch == null)
			throw new NullPointerException("Patch cannot be null");
		for (DocumentChangedListener listeny : listeners) {
			listeny.onDocumentModified(resId, patch);
		}
	}

	public void flushFile(PatchData[] changes, byte[] bytes) {
		String docText = new String(bytes);
		Display.getDefault().syncExec(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				doc.removeDocumentListener(docListener);
				doc.set(docText);
				doc.addDocumentListener(docListener);
				
			}
			
		});
		if (changes != null) {
    		for (int i = 0; i < changes.length; i++) {
    			recievePatch(changes[i].getChanges());
    		}
		}
	}
}
