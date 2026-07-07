package mvcVersion;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CalculationResult {
	private final String operand1;
	private final String operand2;
	private final String num1;
	private final String num2;
	private final char operator;
	private final Map<DivisionNumber, List<UnaryOperator>> unary;
	private final boolean isUnary;
	private final String result;
	private final boolean addOperator;
	private final boolean unaryError;
	private final boolean operationError;
	private final boolean operate;
	
	public CalculationResult(String operand1, String operand2, String num1, String num2, char operator,
			Map<DivisionNumber, List<UnaryOperator>> unary, boolean isUnary, String result, boolean addOperator,
			boolean unaryError, boolean operationError, boolean operate) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.num1 = num1;
		this.num2 = num2;
		this.operator = operator;
		Map<DivisionNumber, List<UnaryOperator>> copy = new LinkedHashMap<>();
		for (var key : unary.entrySet()) {
			copy.computeIfAbsent(key.getKey(), k->new LinkedList<>()).addAll(key.getValue());
		}
		this.unary = copy.isEmpty()?Map.of():Collections.unmodifiableMap(copy);
		this.isUnary = isUnary;
		this.result = result;
		this.addOperator = addOperator;
		this.unaryError = unaryError;
		this.operationError = operationError;
		this.operate = operate;
	}
	public boolean isOperate() {
		return operate;
	}
	public String getOperand1() {
		return operand1;
	}
	public String getOperand2() {
		return operand2;
	}
	public String getNum1() {
		return num1;
	}
	public String getNum2() {
		return num2;
	}
	public char getOperator() {
		return operator;
	}
	public Map<DivisionNumber, List<UnaryOperator>> getUnary() {
		return unary;
	}
	public boolean isUnary() {
		return isUnary;
	}
	public String getResult() {
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
	private String getOperandString(String operand, DivisionNumber num) {
		String text = "";
		for (var key : unary.entrySet()) {
			if (key.getKey() == num) {
				for (UnaryOperator symbol : key.getValue()) {
					switch (symbol) {
					case SQRT:
						if (text.isEmpty()) {
							text = "sqrt(" + operand + ")";
						}else {
							text = "sqrt(" + text + ")";
						}
						break;
					case SQR:
						if (text.isEmpty()) {
							text = "sqr(" + operand + ")";
						}else {
							text = "sqr(" + text + ")";
						}
						break;
					case INVERSE:

						if (text.isEmpty()) {
							text = "1/(" + operand + ")";
						}else {
							text = "1/(" + text + ")";
						}
						break;
					case NEGATE:

						if (text.isEmpty()) {
							if (num == DivisionNumber.SECOND) {
								text = "negate(" + operand + ")";
							}
						}else {
							text = "1/(" + text + ")";
						}
						break;
					default:
						break;
					}
				}
			}				
		}
		return text;
	}
	
	public String unaryError() {
		
		if (operator == ' ') {
			return operand1;
		}
		if(operand1.isEmpty()){
			return num1 + " " + operator + " " + operand2;
		}
		return operand1 + " " + operator + " " + operand2;
	}
	
	public String unaryResult() {
		String firstOperand = "";
		
		if (operator == ' ') {
			firstOperand = getOperandString( operand1, DivisionNumber.FIRST);
			return firstOperand;
		}
		String secondOperand = "";
		
		if (operand2.isEmpty()) {
			secondOperand = getOperandString(operand2, DivisionNumber.SECOND);
			return num1 + " " + operator + " " + secondOperand;
		}
		
		firstOperand = getOperandString( operand1, DivisionNumber.FIRST);
		secondOperand = getOperandString(operand2, DivisionNumber.SECOND);
		return firstOperand + " " + operator + " " + secondOperand;
	}
	
	public String operateMovement() {

		if (addOperator) {
			if (isUnary) {
				String operand = getOperandString(operand1, DivisionNumber.FIRST);
				return operand;
			}
		}
		
		if (operate) {
			return result;
		}
		
		return num1;
	}
	
	public String operateResult() {

		if (operate && isUnary) {
			return operationUnaryResult();
		}
		return num1 + " " + operator + " " + num2 + " =";
	}
	public String operationError() {
		return num1 + " " + operator + " " + num2;
	}
	
	private String operationUnaryResult() {

		String firstOperand = "";
		
		if (operand2.isEmpty()) {
			firstOperand = getOperandString( operand1, DivisionNumber.FIRST);
			return firstOperand + " " + operator + " " + num2 + " =";
		}
		String secondOperand = "";
		if (operand1.isEmpty()) {
			secondOperand = getOperandString(operand2, DivisionNumber.SECOND);
			return num1 + " " + operator + " " + secondOperand + " =";
		}
		firstOperand = getOperandString( operand1, DivisionNumber.FIRST);
		secondOperand = getOperandString(operand2, DivisionNumber.SECOND);
		return firstOperand + " " + operator + " " + secondOperand + " =";
	}
}
