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
	public NumberPresenter numberPresent(NumberResult result) {
		String current = numberCurrentText(result);
		String history = numberHistoryText(result);
		return new  NumberPresenter(current, history);
	}
	
	private String numberHistoryText(NumberResult result) {

		if (result.unaryHistory()) {
			String operand = getOperandString(result.unary(), formatNumber(result.operand()), result.num());
			return operand + "\n" + result.current();
		}
		return "";
	}
	
	private String numberCurrentText(NumberResult result) {
		if (result.zero()) {
			return formatNumber(result.value());
		}
		return result.current() + formatNumber(result.value());
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
						text = symbol.format(operand, key.getKey());
					}else {
						text = symbol.format(text, key.getKey());
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
		String currentText = unaryCurrentText(result.unaryError(), result.unaryErrorOperator(), formatNumber(result.result()));
		
		String progressText = unaryProgressText(result.unaryError(), result.unary(),
				formatNumber(result.operand1()), formatNumber(result.operand2()),
				result.firstOperator(), formatNumber(result.num1()));
		
		return new UnaryPresenter(currentText, progressText);
	}
	
	private String unaryCurrentText(boolean unaryError, UnaryOperator operator, String result) {
		//System.out.println("is unary error: " + unaryError);
		if (unaryError) {
			
			if (operator == UnaryOperator.INVERSE) {
				return "Cannot divided by zero";
			}else {
				return "Invalid input";
			}
		}
		return result;
	}
	private String unaryProgressText(boolean unaryError, Map<DivisionNumber, List<UnaryOperator>> unary,
			String operand1, String operand2, char firstOperator,
			String num1) {
		
		String firstOperand = "";
		String secondOperand = "";
		
		if (unaryError) {
			return  unaryError(unary, operand1, operand2,firstOperator, num1);
		}

		firstOperand = getOperandString(unary, operand1, DivisionNumber.FIRST);
		secondOperand = getOperandString(unary, operand2, DivisionNumber.SECOND);
		if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
			return firstOperand;
		}		
		
		if (!unary.containsKey(DivisionNumber.FIRST) && unary.containsKey(DivisionNumber.SECOND)) {
			return num1 + " " + firstOperator + " " + secondOperand;
		}
		return firstOperand + " " + firstOperator + " " + secondOperand;
	}
	
	public OperatorPresenter operatePresent(CalculationResult result) {
		return operate(result);
	}

	private OperatorPresenter operate(CalculationResult result) {
		String progress =
				progressText(result.addOperator(), result.unary(), formatNumber(result.result()),result.secondOperator(),
				formatNumber(result.num1()), formatNumber(result.num2()), formatNumber(result.operand1()),
				formatNumber(result.operand2()),	result.operationError(), result.firstOperator(), result.operate());
		
		String current = currentText(result.operationError(), formatNumber(result.result()),
				result.operate(), formatNumber(result.num1()), result.secondOperator());
		
		String history = "";
		if (result.operate()) {
			history = historyText(formatNumber(result.operand1()), formatNumber(result.operand2()), result.unary(),
					formatNumber(result.num1()), formatNumber(result.num2()), formatNumber(result.result()),
					result.firstOperator());
		}
		return new OperatorPresenter(current, progress, history);
	}
	
	private String currentText(boolean operationError, String result, boolean operate, String num1, char secondOperator) {
		
		if (operationError) {
			return "Cannot divided by zero";
		}
		
		if (operate) {
			return result;
		}
		
		return num1;
	}
	
	private String progressText(boolean addOperator, Map<DivisionNumber, List<UnaryOperator>> unary,
			String result, char secondOperator, String num1, String num2, String operand1, String operand2,
			boolean operationError, char firstOperator, boolean operate) {

		String text = "";
		if (addOperator) {
			if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
				text = getOperandString(unary, operand1, DivisionNumber.FIRST);
			}
		}
		
		if (operationError) {
			text = num1 + " " + firstOperator + " " + num2 + secondOperator;
		}
		
		if (operate) {
			text = result + secondOperator;
			
		}
		if (secondOperator == ' ' && unary.isEmpty()){
			text = num1 + firstOperator;
		}
		if(secondOperator == ' ' && !unary.isEmpty()){
			String firstOperand = getOperandString(unary, operand1, DivisionNumber.FIRST);
			text = firstOperand + " " + firstOperator;
		}
		return text;
	}
	
	private String historyText(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
			String num1, String num2, String result, char firstOperator) {
		
			if (!unary.isEmpty()) {
				return operationUnaryHistory(operand1, operand2, unary, num1, num2, result, firstOperator);
			}
			return num1 + " " + firstOperator + " " + num2 + " =\n" + result;
	}
	
	private String operationUnaryHistory(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
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
