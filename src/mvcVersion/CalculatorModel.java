package mvcVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculatorModel {
	private boolean waitSecondNumber = false;

	private double operand1 = 0;
	private double operand2 = 0;
	private double num1 = 0;
	private double num2 = 0;
	private double answer = 0;
	private char firstOperator = ' ';
	private char secondOperator = ' ';
	private boolean addOperator = false;
	private InputState inputState = InputState.NORMAL;
	private boolean equaled = false;
	private boolean isOperate = false;
	private boolean unaryHistory = false;
	private boolean unaryError = false;
	private boolean operationError = false;
	private Map<DivisionNumber, List<UnaryOperator>> unary = new LinkedHashMap<>();
	private UnaryOperator unaryErrorOperator = UnaryOperator.NOUNARY;
	private OperationState operationState = OperationState.NORMAL;
	private UnaryState unaryState = UnaryState.NORMAL;
	
	
	public UnaryState getUnaryState() {
		return unaryState;
	}

	public void setUnaryState(UnaryState unaryState) {
		this.unaryState = unaryState;
	}

	public InputState getInputState() {
		return inputState;
	}

	public void setInputState(InputState inputState) {
		this.inputState = inputState;
	}

	public OperationState getOperationState() {
		return operationState;
	}

	public void setOperationState(OperationState operationState) {
		this.operationState = operationState;
	}

	public UnaryOperator getUnaryErrorOperator() {
		return unaryErrorOperator;
	}

	public boolean isUnaryHistory() {
		return unaryHistory;
	}

	public void setUnaryHistory(boolean unaryHistory) {
		this.unaryHistory = unaryHistory;
	}

	public void setUnaryErrorOperator(UnaryOperator unaryErrorOperator) {
		this.unaryErrorOperator = unaryErrorOperator;
	}

	public double getAnswer() {
		return answer;
	}

	public void setAnswer(double answer) {
		this.answer = answer;
	}

	public double getOperand1() {
		return operand1;
	}

	public void setOperand1(double operand1) {
		this.operand1 = operand1;
	}

	public double getOperand2() {
		return operand2;
	}

	public void setOperand2(double operand2) {
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
	
	public Map<DivisionNumber, List<UnaryOperator>> getUnary() {
		return unary.isEmpty()? Map.of(): Collections.unmodifiableMap(unary);
	}
	public void clearUnary() {
		unary.clear();
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

	public boolean isEqualed() {
		return equaled;
	}

	public void setEqualed(boolean equaled) {
		this.equaled = equaled;
	}

	public void setNum1(double num1) {
		this.num1 = num1;
	}

	public void setNum2(double num2) {
		this.num2 = num2;
	}

	public double getNum1() {
		return num1;
	}

	public double getNum2() {
		return num2;
	}

	public void setWaitSecondNumber(boolean waitSecondNumber) {
		this.waitSecondNumber = waitSecondNumber;
	}

	public boolean isWaitSecondNumber() {
		return waitSecondNumber;
	}
	
	public void clear() {
		num1 =0;
		num2 =0;
		firstOperator = ' ';
		secondOperator = ' ';
		unary.clear();
		operand1 = 0;
		operand2 = 0;
		inputState = InputState.NORMAL;
		operationState = OperationState.NORMAL;
		unaryState = UnaryState.NORMAL;
		addOperator = false;
		equaled = false;
		isOperate = false;
		unaryError = false;
		operationError = false;
	}
	
	public void ifOperate() {
		num1 = answer;
		answer = 0;
		num2 = 0;
		firstOperator = secondOperator;
		operand1 = 0;
		operand2 = 0;
		secondOperator = ' ';
		unary.clear();
		isOperate = false;
	}
	public void removeUnary(DivisionNumber num) {
		unary.remove(num);
	}
}
