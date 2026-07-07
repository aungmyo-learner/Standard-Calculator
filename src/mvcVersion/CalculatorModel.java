package mvcVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculatorModel {
	private boolean waitSecondNumber = false;

	private String operand1 = "";
	private String operand2 ="";
	private double num1 = 0;
	private double num2 = 0;
	private double answer = 0;
	private char firstOperator = ' ';
	private char secondOperator = ' ';
	private boolean addOperator = false;
	
	private boolean calculated = false;
	private boolean isOperate = false;

	private boolean unaryError = false;
	private boolean operationError = false;
	private Map<DivisionNumber, List<UnaryOperator>> unary = new LinkedHashMap<>();
	private boolean isUnary = false;

	public String getOperand1() {
		return operand1;
	}

	public void setOperand1(String operand1) {
		this.operand1 = operand1;
	}

	public String getOperand2() {
		return operand2;
	}

	public void setOperand2(String operand2) {
		this.operand2 = operand2;
	}

	public boolean isUnaryError() {
		return unaryError;
	}

	public void setUnaryError(boolean unaryError) {
		this.unaryError = unaryError;
	}

	public boolean isOperationError() {
		return operationError;
	}

	public void setOperationError(boolean operationError) {
		this.operationError = operationError;
	}

	public boolean isOperate() {
		return isOperate;
	}

	public void setOperate(boolean isOperate) {
		this.isOperate = isOperate;
	}

	public char getFirstOperator() {
		return firstOperator;
	}

	public void setFirstOperator(char firstOperator) {
		this.firstOperator = firstOperator;
	}

	public char getSecondOperator() {
		return secondOperator;
	}

	public void setSecondOperator(char secondOperator) {
		this.secondOperator = secondOperator;
	}
//
//	public boolean isHasError() {
//		return hasError;
//	}
//
//	public void setHasError(boolean hasError) {
//		this.hasError = hasError;
//	}

	public boolean isUnary() {
		return isUnary;
	}

	public void setUnary(boolean isUnary) {
		this.isUnary = isUnary;
	}

	public Map<DivisionNumber, List<UnaryOperator>> getUnary() {
		return unary.isEmpty()? Map.of(): Collections.unmodifiableMap(unary);
	}
	
	public void setUnary(Map<DivisionNumber, List<UnaryOperator>> unary) {
		this.unary = unary;
	}

	public void addUnary(DivisionNumber count, UnaryOperator unary) {
		this.unary.computeIfAbsent(count, c-> new LinkedList<>()).add(unary);
	}

	public boolean isAddOperator() {
		return addOperator;
	}

	public void setAddOperator(boolean addOperator) {
		this.addOperator = addOperator;
	}

	public boolean isCalculated() {
		return calculated;
	}

	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
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

	public void setWaitSecondNumber(boolean waitSecondNumber) {
		this.waitSecondNumber = waitSecondNumber;
	}

	public boolean isWaitSecondNumber() {
		return waitSecondNumber;
	}

}
