package mvcVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculatorModel {
	
	private double operand1 = 0;
	private double operand2 = 0;
	private double num1 = 0;
	private double num2 = 0;
	private double answer = 0;
	private char firstOperator = ' ';
	private char secondOperator = ' ';
	private InputState inputState = InputState.NORMAL;
	private HistoryState historyState = HistoryState.NONE;
	private UnaryPosition unaryPosition = UnaryPosition.NONE;
	private Map<UnaryPosition, List<UnaryOperator>> unary = new LinkedHashMap<>();
	private UnaryOperator unaryErrorOperator = UnaryOperator.NOUNARY;
	private OperationState operationState = OperationState.NORMAL;
	
	
	public HistoryState getHistoryState() {
		return historyState;
	}

	public void setHistoryState(HistoryState historyState) {
		this.historyState = historyState;
	}

	public UnaryPosition getUnaryPosition() {
		return unaryPosition;
	}

	public void setUnaryPosition(UnaryPosition unaryPosition) {
		this.unaryPosition = unaryPosition;
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
	
	public Map<UnaryPosition, List<UnaryOperator>> getUnary() {
		return unary.isEmpty()? Map.of(): Collections.unmodifiableMap(unary);
	}
	public void clearUnary() {
		unary.clear();
	}
	public void addUnary(UnaryPosition count, UnaryOperator unary) {
		this.unary.computeIfAbsent(count, c-> new LinkedList<>()).add(unary);
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

	public void clear() {
		num1 =0;
		num2 =0;
		answer =0;
		firstOperator = ' ';
		secondOperator = ' ';
		unary.clear();
		operand1 = 0;
		operand2 = 0;
		operationState = OperationState.NORMAL;
		unaryPosition = UnaryPosition.NONE;
		historyState = HistoryState.NONE;
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
		historyState = HistoryState.NONE;
		operationState = OperationState.SUCCESS;
		unaryPosition = UnaryPosition.NONE;
	}
	public void removeUnary(UnaryPosition num) {
		unary.remove(num);
	}
}
