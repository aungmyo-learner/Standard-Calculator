package mvcVersion;

public class UnaryPresenter {

	private final String currentText;
	private final String progressText;
	public UnaryPresenter(String currentText, String progressText) {
		this.currentText = currentText;
		this.progressText = progressText;
	}
	public String getCurrentText() {
		return currentText;
	}
	public String getProgressText() {
		return progressText;
	}
	
}
