package mvcVersion;

public class OperatorPresenter {
	private final String currentText;
	private final String progressText;
	private final String historyText;
	
	public OperatorPresenter(String currentText, String progressText, String historyText) {
		this.currentText = currentText;
		this.progressText = progressText;
		this.historyText = historyText;
	}
	
	public String getCurrentText() {
		return currentText;
	}
	public String getProgressText() {
		return progressText;
	}
	public String getHistoryText() {
		return historyText;
	}
}
