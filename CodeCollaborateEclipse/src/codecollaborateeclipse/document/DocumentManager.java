package codecollaborateeclipse.document;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;

import java.util.ArrayList;

import javax.print.Doc;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.ITextEditor;

import codecollaborateeclipse.connections.CCWebSocketConnector;
import codecollaborateeclipse.events.DocumentChangedListener;

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
	
				@Override
				public void documentChanged(DocumentEvent event) {
					// System.out.println("Change happened: " + event.toString());
					String p = patchy.createPatchString(oldDoc, doc.get());
					DocumentManager.this.notify(p);
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
							doc.replace(start, 1, "");
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
		closed = true;
	}
	
	public void addDocumentChangedListener(DocumentChangedListener listener) {
		listeners.add(listener);
	}
	
	public void notify(String patch) {
		if (patch == null)
			throw new NullPointerException("Patch cannot be null");
		for (DocumentChangedListener listeny : listeners) {
			listeny.onDocumentModified(patch);
		}
	}
}
