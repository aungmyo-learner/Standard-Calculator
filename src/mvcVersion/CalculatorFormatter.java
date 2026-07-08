package mvcVersion;

import java.util.List;
import java.util.Map;

public class CalculatorFormatter{

	private String formatNumber(double num){
        if(num == (long) num){
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

	public UnaryPresenter unaryPresent(CalculationResult ressult) {
		return unary(ressult);
	}
	
	private String getOperandString(Map<DivisionNumber, List<UnaryOperator>> unary, String operand, DivisionNumber num) {
		String text = "";
		for (var key : unary.entrySet()) {
			if (key.getKey() == num) {
				for (UnaryOperator symbol : key.getValue()) {
					
					if (text.isEmpty()) {
						text = symbol.format(operand);
					}else {
						text = symbol.format(text);
					}
				}
			}				
		}
		return text;
	}

	private String unaryError(Map<DivisionNumber, List<UnaryOperator>> unary, String operand1, String operand2,
			char firstOperator, String num1) {
		
		if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
			return operand1;
		}
		if(!unary.containsKey(DivisionNumber.FIRST) && unary.containsKey(DivisionNumber.SECOND)){
			return num1 + " " + firstOperator + " " + operand2;
		}
		return operand1 + " " + firstOperator + " " + operand2;
	}
	
	private UnaryPresenter unary(CalculationResult result) {
		String currentText = "";
		String progressText = "";
		
		String firstOperand = "";
		String secondOperand = "";
		if (result.isUnaryError()) {
			progressText = unaryError(result.getUnary(), formatNumber(result.getOperand1()), formatNumber(result.getOperand2()),
					result.getFirstOperator(), formatNumber(result.getNum1()));
			currentText = "Invalid input";
		}
		firstOperand = getOperandString(result.getUnary(), formatNumber(result.getOperand1()), DivisionNumber.FIRST);
		secondOperand = getOperandString(result.getUnary(), formatNumber(result.getOperand2()), DivisionNumber.SECOND);
		if (result.getUnary().containsKey(DivisionNumber.FIRST) && !result.getUnary().containsKey(DivisionNumber.SECOND)) {
			progressText = firstOperand;
		}		
		
		if (!result.getUnary().containsKey(DivisionNumber.FIRST) && result.getUnary().containsKey(DivisionNumber.SECOND)) {
			progressText = formatNumber(result.getNum1()) + " " + result.getFirstOperator() + " " + secondOperand;
		}
		
		if (result.getUnary().containsKey(DivisionNumber.FIRST) && result.getUnary().containsKey(DivisionNumber.SECOND)) {
			progressText = firstOperand + " " + result.getFirstOperator() + " " + secondOperand;
		}
		
		currentText = formatNumber(result.getResult());
		
		return new UnaryPresenter(currentText, progressText);
	}
	
	public OperatorPresenter operatePresent(CalculationResult result) {
		return operate(result);
	}

	private OperatorPresenter operate(CalculationResult result) {
		String progress =
				progressText(result.isAddOperator(), result.getUnary(), formatNumber(result.getResult()),result.getSecondOperator(),
				formatNumber(result.getNum1()), formatNumber(result.getOperand1()),formatNumber(result.getOperand2()),
				result.isOperationError(), result.getFirstOperator(), formatNumber(result.getNum2()));
		
		String current = currentText(formatNumber(result.getResult()), result.getSecondOperator(), formatNumber(result.getNum1()));
		
		String history = "";
		if (result.isOperate()) {
			history = historyText(formatNumber(result.getOperand1()), formatNumber(result.getOperand2()), result.getUnary(),
					formatNumber(result.getNum1()), formatNumber(result.getNum2()), formatNumber(result.getResult()),
					result.getFirstOperator());
		}
		return new OperatorPresenter(current, progress, history);
	}
	
	private String currentText(String result, char secondOperator, String num1) {
		if (secondOperator == ' ') {
			return num1;
		}
		return result;
		
	}
	
	private String progressText(boolean addOperator, Map<DivisionNumber, List<UnaryOperator>> unary,
			String result, char secondOperator, String num1, String operand1, String operand2,
			boolean operationError, char firstOperator, String num2) {

		if (addOperator) {
			if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
				return getOperandString(unary, operand1, DivisionNumber.FIRST);
			}
		}
		
		if (operationError) {
			return num1 + " " + firstOperator + " " + num2 + secondOperator;
		}
		
		if (secondOperator == ' ') {
			return num1 + firstOperator;
		}
		return result + secondOperator;
	}
	
	private String historyText(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
			String num1, String num2, String result, char firstOperator) {
		
			if (!unary.isEmpty()) {
				return operationUnaryResult(operand1, operand2, unary, num1, num2, result, firstOperator);
			}
			return num1 + " " + firstOperator + " " + num2 + " =\n" + result;
	}
	
	private String operationUnaryResult(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
			String num1, String num2, String result, char firstOperator) {

		String firstOperand = "";
		String secondOperand = "";
		secondOperand = getOperandString(unary, operand2, DivisionNumber.SECOND);
		firstOperand = getOperandString(unary, operand1, DivisionNumber.FIRST);
		if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
			
			return firstOperand + " " + firstOperator + " " + num2 + " =\n" + result;
		}
		
		if (!unary.containsKey(DivisionNumber.FIRST) && unary.containsKey(DivisionNumber.SECOND)) {
			
			return num1 + " " + firstOperator + " " + secondOperand + " =\n" + result;
		}
		
		return firstOperand + " " + firstOperator + " " + secondOperand + " =\n" + result;
	}
}
