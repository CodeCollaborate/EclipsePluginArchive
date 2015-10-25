package codecollaborateeclipse.listener;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;

import javax.print.Doc;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.texteditor.ITextEditor;

import codecollaborateeclipse.CCWebSocketConnector;
import codecollaborateeclipse.PatchHandler;

public class EditorListener {

	CCWebSocketConnector connector;
	PatchHandler patchy;
	IEditorPart editor;
	IEditorInput input;
	IDocument doc;

	IDocumentListener listener;
	// Arghh, matey!

	public EditorListener(CCWebSocketConnector connector) {
		this.connector = connector;
		this.patchy = new PatchHandler();
		editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		input = editor.getEditorInput();
		doc = ((ITextEditor) editor).getDocumentProvider().getDocument(input);
	}

	public void listen() {

		listener = new IDocumentListener() {
			String oldDoc = "";

			@Override
			public void documentChanged(DocumentEvent event) {
				// System.out.println("Change happened: " + event.toString());
				String p = patchy.createPatchString(oldDoc, doc.get());
				connector.sendPatch(p);
				// System.out.println(p);
			}

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {
				// System.out.println("I predict that the following change will
				// occur: " + event.toString());
				oldDoc = doc.get();
			}
		};

		this.doc.addDocumentListener(listener);

	}

	public void recievePatch(String msg) {

		String newDocText = this.patchy.recievePatch(msg, this.doc.get());
		System.out.println("Replace \"" + doc.get() + "\" with \"" + newDocText + "\"");
		
		Display.getDefault().syncExec(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				doc.removeDocumentListener(listener);
				doc.set(newDocText);
				doc.addDocumentListener(listener);
				
			}
			
		});

	}

}
