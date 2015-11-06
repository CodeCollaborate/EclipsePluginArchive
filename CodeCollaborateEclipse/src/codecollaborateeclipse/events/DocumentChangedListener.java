package codecollaborateeclipse.events;

public interface DocumentChangedListener {
	public void onDocumentModified(String resId, String patch);
}
