package mvcVersion;

public class SolveOperator {
	private final String progress;
	private final String history;
	private final String answer;
	
	public SolveOperator(String progress, String history, String answer) {
		this.progress = progress;
		this.history = history;
		this.answer = answer;
	}
	
	public String getProgress() {
		return progress;
	}
	public String getHistory() {
		return history;
	}

	public String getAnswer() {
		return answer;
	}
}
