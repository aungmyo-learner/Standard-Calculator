package mvcVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class CalculationResult{
	private final double operand1;
	private final double operand2;
	private final double num1;
	private final double num2;
	private final char firstOperator;
	private final char secondOperator;
	private final Map<DivisionNumber, List<UnaryOperator>> unary;
	private final double result;
	private final boolean addOperator;
	private final boolean unaryError;
	private final boolean operationError;
	private final boolean operate;
	
	public CalculationResult(double operand1, double operand2, double num1, double num2, char firstOperator,
			char secondOperator, Map<DivisionNumber, List<UnaryOperator>> unary, double result, boolean addOperator,
			boolean unaryError, boolean operationError, boolean operate) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.num1 = num1;
		this.num2 = num2;
		this.firstOperator = firstOperator;
		this.secondOperator = secondOperator;
		Map<DivisionNumber, List<UnaryOperator>> copy = new LinkedHashMap<>();
		for (var key : unary.entrySet()) {
			copy.computeIfAbsent(key.getKey(), k->new LinkedList<>()).addAll(key.getValue());
		}
		this.unary = copy.isEmpty()?Map.of():Collections.unmodifiableMap(copy);
		this.result = result;
		this.addOperator = addOperator;
		this.unaryError = unaryError;
		this.operationError = operationError;
		this.operate = operate;
	}
	
	public double getOperand1() {
		return operand1;
	}

	public double getOperand2() {
		return operand2;
	}

	public double getNum1() {
		return num1;
	}

	public double getNum2() {
		return num2;
	}

	public boolean isOperate() {
		return operate;
	}
	public char getFirstOperator() {
		return firstOperator;
	}
	public char getSecondOperator() {
		return secondOperator;
	}
	public Map<DivisionNumber, List<UnaryOperator>> getUnary() {
		return unary;
	}
	public double getResult() {
		return result;
	}
	public boolean isAddOperator() {
		return addOperator;
	}
	
	public boolean isUnaryError() {
		return unaryError;
	}
	public boolean isOperationError() {
		return operationError;
	}
	
}
