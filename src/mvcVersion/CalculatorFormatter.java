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

	public String clickDecimal(String text) {
		if (!text.contains(".")) {
			return  text + ".";
		}
		return text;
	}
	
	public String clickBackspace(String text) {

		if(text.length() <=1) {
			return "0";
		}
		return text.substring(0, text.length() -1);
	}

	public Presenter percentagePersenter(PercentageResult result) {
		String current = percentageCurrentText(result);
		String progress = percentageProgressText(result);
		String history = "";
		
		return new Presenter(current, progress, history);
	}
	
	private String percentageCurrentText(PercentageResult result) {
		return formatNumber(result.result());
	}
	
	private String percentageProgressText(PercentageResult result) {
		if (result.operator() == ' ') {
			return formatNumber(result.result());
		}
		if (result.unaryPosition() == UnaryPosition.FIRST) {
			return getOperandString(result.unary(), formatNumber(result.operand()), result.unaryPosition()) + " " +
				result.operator() + " " + formatNumber(result.num2());
		}
		return formatNumber(result.num1()) + " " + result.operator() + " " + formatNumber(result.num2());
	}
	
	public Presenter equalPresent(EqualResult result) {
		String current = equalCurrentText(result);
		String progress = equalProgressText(result);
		return new Presenter(current, progress, "");
	}
	
	private String equalCurrentText(EqualResult result) {

		if (result.operationState() == OperationState.ERROR) {
			return "Cannot divided by zero";
		}
		if (!result.unary().isEmpty() && result.operator() == ' ') {
			return formatNumber(result.num1());
		}
		if (result.operationState() == OperationState.SUCCESS) {
			return formatNumber(result.result());
		}
		return formatNumber(result.num1());
	}
	
	private String equalProgressText(EqualResult result) {

		if (result.operationState() == OperationState.ERROR) {
			return formatNumber(result.num1()) + " " + result.operator();
		}
		
		if (result.operator() == ' ' && result.operationState() == OperationState.NORMAL) {
			if (result.unaryPositon() == UnaryPosition.FIRST) {
				return getOperandString(result.unary(), formatNumber(result.operand1()), result.unaryPositon()) + " =";
			}
				return result.current() + " =";
		}
		
		if (!result.unary().isEmpty()) {
			return equalUnaryProgressText(result);
		}
		return formatNumber(result.num1()) + " " + result.operator() + " " + formatNumber(result.num2()) + " =";
	}
	
	private String equalUnaryProgressText(EqualResult result) {

		String firstOperand = "";
		String secondOperand = "";
		secondOperand = getOperandString(result.unary(), formatNumber(result.operand2()), UnaryPosition.SECOND);
		firstOperand = getOperandString(result.unary(), formatNumber(result.operand1()), UnaryPosition.FIRST);
		if (result.unary().containsKey(UnaryPosition.FIRST) &&
				!result.unary().containsKey(UnaryPosition.SECOND)) {
			
			return firstOperand + " " + result.operator() + " " + formatNumber(result.num2()) + " =";
		}
		
		if (!result.unary().containsKey(UnaryPosition.FIRST) &&
				result.unary().containsKey(UnaryPosition.SECOND)) {
			
			return formatNumber(result.num1()) + " " + result.operator() + " " + secondOperand + " =";
		}
		
		return firstOperand + " " + result.operator() + " " + secondOperand + " =";
	}
	public Presenter numberPresent(NumberResult result) {
		String current = numberCurrentText(result);
		String progress = numberProgressText(result);
		String history = numberHistoryText(result);
		return new  Presenter(current, progress, history);
	}
	
	private String numberProgressText(NumberResult result) {
		System.out.println("input state: " + result.inputState() +
				"\noperator: " + result.operator() +
				"\noperation state: " + result.operationState()+
				"\nnum1: " + result.num1() +
				"\nunary position: " + result.unaryPosition() +
				"\n______________________");
		
		if ((result.inputState() == InputState.AFTER_OPERATOR && result.operationState() == OperationState.SUCCESS) ||
				(result.inputState() == InputState.NORMAL && result.operationState() == OperationState.SUCCESS) ||
				(result.inputState() == InputState.AFTER_UNARY && result.operationState() == OperationState.SUCCESS)||
				(result.inputState() == InputState.AFTER_PERCENTAGE && result.operationState() == OperationState.SUCCESS)) {
			return success(result);
		}
		
		if ((result.inputState() == InputState.AFTER_UNARY && result.unaryPosition() == UnaryPosition.FIRST &&
				result.operationState() == OperationState.NORMAL) ||
				(result.inputState() == InputState.NORMAL && result.unaryPosition() == UnaryPosition.FIRST &&
				result.operationState() == OperationState.NORMAL)) {
			
			return getOperandString(result.unary(), formatNumber(result.operand()), result.unaryPosition());
		}
		
		return "";
	}
	
	private String success(NumberResult result) {
		if (result.unaryPosition() == UnaryPosition.FIRST) {
			return getOperandString(result.unary(), formatNumber(result.operand()), result.unaryPosition()) +
					" " + result.operator();
		}
	return formatNumber(result.num1()) + " " + result.operator();
	}
	
	private String numberHistoryText(NumberResult result) {

		if (result.historyState() ==  HistoryState.SHOW && result.inputState() == InputState.AFTER_UNARY) {
			return getOperandString(result.unary(), formatNumber(result.operand()), result.unaryPosition());
		}
		return "";
	}
	
	private String numberCurrentText(NumberResult result) {
		if (result.operationState() == OperationState.ERROR || result.current().equals("0")
				|| result.inputState() == InputState.AFTER_UNARY || result.inputState() == InputState.AFTER_OPERATOR ||
				result.inputState() == InputState.AFTER_PERCENTAGE || result.inputState() == InputState.AFTER_EQUAL) {
			return formatNumber(result.value());
		}
		
		return result.current() + formatNumber(result.value());
	}
	
	public Presenter unaryPresent(CalculationResult ressult) {
		return unary(ressult);
	}
	
	private String getOperandString(Map<UnaryPosition, List<UnaryOperator>> unary, String operand, UnaryPosition num) {
		String text = "";
		int count =-1;
		for (var key : unary.entrySet()) {
			if (key.getKey() == num) {
				for (UnaryOperator symbol : key.getValue()) {
					
					if (text.isEmpty()) {
						text = symbol.format(operand, count);
					}else {
						text = symbol.format(text, count);
					}
					if (symbol != UnaryOperator.TOGGLE) {
						count++;
					}
				}
			}				
		}
		return text;
	}

	private String unaryError(Map<UnaryPosition, List<UnaryOperator>> unary, String operand1, String operand2,
			char firstOperator, String num1) {
		
		if (unary.containsKey(UnaryPosition.FIRST) && !unary.containsKey(UnaryPosition.SECOND)) {
			return getOperandString(unary, operand2, UnaryPosition.FIRST);
		}
		if(!unary.containsKey(UnaryPosition.FIRST) && unary.containsKey(UnaryPosition.SECOND)){
			return num1 + " " + firstOperator + " " + getOperandString(unary, operand2, UnaryPosition.SECOND);
		}
		return getOperandString(unary, operand2, UnaryPosition.FIRST)
				+ " " + firstOperator + " " +
				getOperandString(unary, operand2, UnaryPosition.SECOND);
	}
	
	private Presenter unary(CalculationResult result) {
		String currentText = unaryCurrentText(result.operationState(), result.unaryErrorOperator(), formatNumber(result.result()));
		
		String progressText = unaryProgressText(result.operationState(), result.unary(),
				formatNumber(result.operand1()), formatNumber(result.operand2()),
				result.firstOperator(), formatNumber(result.num1()));
		
		return new Presenter(currentText, progressText, "");
	}
	
	private String unaryCurrentText(OperationState state, UnaryOperator operator, String result) {
		if (state == OperationState.ERROR) {
			
			if (operator == UnaryOperator.INVERSE) {
				return "Cannot divided by zero";
			}else {
				return "Invalid input";
			}
		}
		return result;
	}
	private String unaryProgressText(OperationState state, Map<UnaryPosition, List<UnaryOperator>> unary,
			String operand1, String operand2, char firstOperator,
			String num1) {
		
		String firstOperand = "";
		String secondOperand = "";
		
		if (state == OperationState.ERROR) {
			return  unaryError(unary, operand1, operand2,firstOperator, num1);
		}

		firstOperand = getOperandString(unary, operand1, UnaryPosition.FIRST);
		secondOperand = getOperandString(unary, operand2, UnaryPosition.SECOND);
		if (unary.containsKey(UnaryPosition.FIRST) && !unary.containsKey(UnaryPosition.SECOND)) {
			return firstOperand;
		}		
		
		if (!unary.containsKey(UnaryPosition.FIRST) && unary.containsKey(UnaryPosition.SECOND)) {
			return num1 + " " + firstOperator + " " + secondOperand;
		}
		return firstOperand + " " + firstOperator + " " + secondOperand;
	}
	
	public Presenter operatePresent(CalculationResult result) {
		return operate(result);
	}

	private Presenter operate(CalculationResult result) {
		String progress =
				operatorProgressText(result);
		
		String current = operatorCurrentText(result);
		
		String history = "";
		if (result.historyState() == HistoryState.SHOW) {
			history = operatorHistoryText(result);
		}
		return new Presenter(current, progress, history);
	}
	
	private String operatorCurrentText(CalculationResult result) {
		
		if (result.operationState() == OperationState.ERROR) {
			return "Cannot divided by zero";
		}
		
		if (result.historyState() == HistoryState.SHOW) {
			return formatNumber(result.result());
		}
		
		return formatNumber(result.num1());
	}
	
	private String operatorProgressText(CalculationResult result) {

		String text = "";

		if (result.operationState() == OperationState.ERROR) {
			text = formatNumber(result.num1()) + " " + result.firstOperator() + " " +
		formatNumber(result.num2()) + " " + result.secondOperator();
		}
		
		if (result.operationState() == OperationState.SUCCESS) {
			text = formatNumber(result.result()) + " " + result.secondOperator();
		}
		
		if (result.secondOperator() == ' ' && result.unaryPosition() == UnaryPosition.NONE){
			text = formatNumber(result.num1()) + " " + result.firstOperator();
		}
		
		if(result.secondOperator() == ' ' && result.unaryPosition() == UnaryPosition.FIRST){
			String firstOperand = getOperandString(result.unary(), formatNumber(result.operand1()), result.unaryPosition());
			text = firstOperand + " " + result.firstOperator();
		}
		
		return text;
	}
	
	private String operatorHistoryText(CalculationResult result) {
		
			if (result.unaryPosition() != UnaryPosition.NONE) {
				return operationUnary(result);
			}
			return formatNumber(result.num1()) + " " + result.firstOperator() + " " + formatNumber(result.num2()) + " =";
	}
	
	private String operationUnary(CalculationResult result) {

		String firstOperand = "";
		String secondOperand = "";
		secondOperand = getOperandString(result.unary(), formatNumber(result.operand2()), UnaryPosition.SECOND);
		firstOperand = getOperandString(result.unary(), formatNumber(result.operand1()), UnaryPosition.FIRST);
		
		if (result.unary().containsKey(UnaryPosition.FIRST) &&
				!result.unary().containsKey(UnaryPosition.SECOND)) {
			
			return firstOperand + " " + result.firstOperator() + " " + formatNumber(result.num2()) + " =";
		}
		
		if (!result.unary().containsKey(UnaryPosition.FIRST) &&
				result.unary().containsKey(UnaryPosition.SECOND)) {
			
			return formatNumber(result.num1()) + " " + result.firstOperator() + " " + secondOperand + " =";
		}
		
		return firstOperand + " " + result.firstOperator() + " " + secondOperand + " =";
	}
}
