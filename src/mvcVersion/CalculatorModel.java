package mvcVersion;

public class CalculatorModel {
	private boolean waitSecondNumber = false;

	private double num1 = 0;
	private double num2 = 0;
	private double answer = 0;
	private char operator = ' ';

	private boolean isOperatorDisableOff = false;
	
	private String unaryCurrent = "";
	private String unaryPrevious = "";
	private boolean isUnary = false;

	public void operatorDisableOff() {
		isOperatorDisableOff = true;
	}
	
	public void operatorDisableOn() {
		isOperatorDisableOff = false;
	}
	
	public boolean isOperatorDisableOff() {
		return isOperatorDisableOff;
	}
	public void setOperator(char operator) {
		this.operator = operator;
	}

	public void setOperatorDisableOff(boolean isOperatorDisableOff) {
		this.isOperatorDisableOff = isOperatorDisableOff;
	}

	public boolean isUnary() {
		return isUnary;
	}
	
	public void setUnary(boolean isUnary) {
		this.isUnary = isUnary;
	}

	public void setNum1(double num1) {
		this.num1 = num1;
	}

	public void setNum2(double num2) {
		this.num2 = num2;
	}

	public void setAnswer(double answer) {
		this.answer = answer;
	}

	public double getNum1() {
		return num1;
	}

	public double getNum2() {
		return num2;
	}

	public double getAnswer() {
		return answer;
	}

	public char getOperator() {
		return operator;
	}

	public String getUnaryCurrent() {
		return unaryCurrent;
	}

	public void setUnaryCurrent(String unaryCurrent) {
		this.unaryCurrent = unaryCurrent;
	}

	public String getUnaryPrevious() {
		return unaryPrevious;
	}

	public void setUnaryPrevious(String unaryPrevious) {
		this.unaryPrevious = unaryPrevious;
	}

	public void setWaitSecondNumber(boolean waitSecondNumber) {
		this.waitSecondNumber = waitSecondNumber;
	}

	public boolean isWaitSecondNumber() {
		return waitSecondNumber;
	}

}
