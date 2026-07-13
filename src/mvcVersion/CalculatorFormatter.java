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
	public Presenter equalPresent(EqualResult result) {
		String current = equalCurrentText(result);
		String progress = equalProgressText(result);
		return new Presenter(current, progress, "");
	}
	
	private String equalCurrentText(EqualResult result) {
		if (result.zero() && result.operator() == ' ') {
			return "0";
		}
		if (result.error()) {
			return "Cannot divided by zero";
		}
//		if (!result.unary().isEmpty() && result.operator() == ' ') {
//			return formatNumber(result.num1());
//		}
		if (result.operate()) {
			return formatNumber(result.reslt());
		}
		return formatNumber(result.num1());
	}
	
	private String equalProgressText(EqualResult result) {
		if (result.zero() && result.operator() == ' ') {
			return "0=";
		}

		if (result.error()) {
			return formatNumber(result.num1()) + " " + result.operator();
		}
		
		if (result.operator() == ' ') {
			if (!result.unary().isEmpty()) {
				return getOperandString(result.unary(), formatNumber(result.operand1()), DivisionNumber.FIRST) + " =";
			}else {
				return formatNumber(result.num1()) + " =";
			}
		}
		
		return historyText(formatNumber(result.operand1()), formatNumber(result.operand2()),
				result.unary(), formatNumber(result.num1()), formatNumber(result.num2()), result.operator());
		
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
				"\nunary state: " + result.unaryState() +
				"\nnum1: " + result.num1() +
				"\ndivision number: " + result.num() +
				"\n______________________");
		
		if (result.inputState() == InputState.AFTER_OPERATOR && result.operationState() == OperationState.SUCCESS) {
			
			if (result.unaryState() == UnaryState.AFTER_UNARY) {
				if (result.num() == DivisionNumber.FIRST) {
					return getOperandString(result.unary(), formatNumber(result.operand()), result.num()) +
							" " + result.operator();
				}
				return formatNumber(result.num1()) + " " + result.operator();
			}
		}
		
		if (result.inputState() == InputState.NORMAL && 
				(result.unaryState() == UnaryState.AFTER_UNARY || result.unaryState() == UnaryState.STILL_UNARY)) {
			return getOperandString(result.unary(), formatNumber(result.operand()), result.num());
		}
		
		if (result.inputState() == InputState.NORMAL && result.operationState() == OperationState.SUCCESS) {
			return unaryFirstText(result);
		}
		return "";
	}
	
	private String unaryFirstText(NumberResult result) {
		if (result.unaryState() == UnaryState.AFTER_UNARY) {
			if (result.num() == DivisionNumber.FIRST) {
				return getOperandString(result.unary(), formatNumber(result.operand()), result.num()) +
						" " + result.operator();
			}
		}
		return formatNumber(result.num1()) + " " + result.operator();
	}
	
	private String numberHistoryText(NumberResult result) {

		if (result.unaryState() ==  UnaryState.AFTER_UNARY && result.inputState() == InputState.NORMAL) {
			String operand = getOperandString(result.unary(), formatNumber(result.operand()), result.num());
			return operand;
		}
		return "";
	}
	
	private String numberCurrentText(NumberResult result) {
		if (result.operationState() == OperationState.ERROR || result.current().equals("0")
				|| result.unaryState() == UnaryState.AFTER_UNARY || result.inputState() == InputState.AFTER_OPERATOR) {
			return formatNumber(result.value());
		}
//		
//		if (result.inputState() == InputState.AFTER_OPERATOR && result.operationState() == OperationState.SUCCESS) {
//			System.out.println("is after unary: " + result.unaryState());
//			if (result.unaryState() == UnaryState.AFTER_UNARY) {
//				return formatNumber(result.value());
//			}
//		}
		return result.current() + formatNumber(result.value());
	}
	
	public Presenter unaryPresent(CalculationResult ressult) {
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
			return getOperandString(unary, operand2, DivisionNumber.FIRST);
		}
		if(!unary.containsKey(DivisionNumber.FIRST) && unary.containsKey(DivisionNumber.SECOND)){
			return num1 + " " + firstOperator + " " + getOperandString(unary, operand2, DivisionNumber.SECOND);
		}
		return getOperandString(unary, operand2, DivisionNumber.FIRST)
				+ " " + firstOperator + " " +
				getOperandString(unary, operand2, DivisionNumber.SECOND);
	}
	
	private Presenter unary(CalculationResult result) {
		String currentText = unaryCurrentText(result.unaryError(), result.unaryErrorOperator(), formatNumber(result.result()));
		
		String progressText = unaryProgressText(result.unaryError(), result.unary(),
				formatNumber(result.operand1()), formatNumber(result.operand2()),
				result.firstOperator(), formatNumber(result.num1()));
		
		return new Presenter(currentText, progressText, "");
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
	
	public Presenter operatePresent(CalculationResult result) {
		return operate(result);
	}

	private Presenter operate(CalculationResult result) {
		String progress =
				progressText(result.addOperator(), result.unary(), formatNumber(result.result()),result.secondOperator(),
				formatNumber(result.num1()), formatNumber(result.num2()), formatNumber(result.operand1()),
				formatNumber(result.operand2()),	result.operationError(), result.firstOperator(), result.operate());
		
		String current = currentText(result.operationError(), formatNumber(result.result()),
				result.operate(), formatNumber(result.num1()), result.secondOperator());
		
		String history = "";
		if (result.operate()) {
			history = historyText(formatNumber(result.operand1()), formatNumber(result.operand2()), result.unary(),
					formatNumber(result.num1()), formatNumber(result.num2()), result.firstOperator());
		}
		return new Presenter(current, progress, history);
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

		if (operationError) {
			text = num1 + " " + firstOperator + " " + num2 + secondOperator;
		}
		
		if (operate) {
			text = result + " " + secondOperator;
			
		}
		if (secondOperator == ' ' && unary.isEmpty()){
			text = num1 + " " + firstOperator;
		}
		if(secondOperator == ' ' && !unary.isEmpty()){
			String firstOperand = getOperandString(unary, operand1, DivisionNumber.FIRST);
			text = firstOperand + " " + firstOperator;
		}
		return text;
	}
	
	private String historyText(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
			String num1, String num2, char firstOperator) {
		
			if (!unary.isEmpty()) {
				return operationUnary(operand1, operand2, unary, num1, num2, firstOperator);
			}
			return num1 + " " + firstOperator + " " + num2 + " =";
	}
	
	private String operationUnary(String operand1, String operand2,  Map<DivisionNumber, List<UnaryOperator>> unary,
			String num1, String num2, char firstOperator) {

		String firstOperand = "";
		String secondOperand = "";
		secondOperand = getOperandString(unary, operand2, DivisionNumber.SECOND);
		firstOperand = getOperandString(unary, operand1, DivisionNumber.FIRST);
		if (unary.containsKey(DivisionNumber.FIRST) && !unary.containsKey(DivisionNumber.SECOND)) {
			
			return firstOperand + " " + firstOperator + " " + num2 + " =";
		}
		
		if (!unary.containsKey(DivisionNumber.FIRST) && unary.containsKey(DivisionNumber.SECOND)) {
			
			return num1 + " " + firstOperator + " " + secondOperand + " =";
		}
		
		return firstOperand + " " + firstOperator + " " + secondOperand + " =";
	}
}
