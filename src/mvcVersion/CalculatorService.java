package mvcVersion;

import java.util.Map;

public class CalculatorService {
	private final CalculatorModel model;
	
	public CalculatorService(CalculatorModel model) {
		this.model = model;
	}
	
	private String formatNumber(double num){
        if(num == (long) num){
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }
	
	public String inputNumber(String currentText, int value) {
		return number(currentText, value);
	}
	
	private String number(String currentText, int value) {
		
		if (model.isWaitSecondNumber()) {
			currentText = "";
			model.setAddOperator(false);
			model.setWaitSecondNumber(false);
		}
		if (model.isOperate()) {
			model.setNum1(model.getAnswer());
			model.setAnswer(0);
			model.setNum2(0);
			model.setFirstOperator(model.getSecondOperator());
			model.setOperand1("");
			model.setOperand2("");
			model.setSecondOperator(' ');
			model.setOperate(false);
			model.setUnary(false);
		}
		
		if (model.isUnaryError() || model.isOperationError()) {
			clear();
		}
		
		if (currentText.equals("0")) {
			return formatNumber(value);
		}
		return currentText + formatNumber(value);
	}

	public boolean isUnaryError() {
		return model.isUnaryError();
	}
	
	public boolean isOperationError() {
		return model.isOperationError();
	}

	public void clickClear() {
		clear();
	}
	
	private void clear() {
		model.setNum1(0);
		model.setNum2(0);
		model.setFirstOperator(' ');
		model.setSecondOperator(' ');
		model.setUnary(Map.of());
		model.setAnswer(0);
		model.setOperand1("");
		model.setOperand2("");
		model.setAddOperator(false);
		model.setCalculated(false);
		model.setOperate(false);
		model.setUnary(false);
		model.setUnaryError(false);		
		model.setOperationError(false);
		
//		System.out.println("num1: " + model.getNum1());
//		System.out.println("num2: " + model.getNum2());
//		System.out.println("first operator: " + model.getFirstOperator());
//		System.out.println("second operator: " + model.getSecondOperator());
//		System.out.println("operandd1: " + model.getOperand1());
//		System.out.println("operand2: " + model.getOperand2());
//		System.out.println("isUnary: " + model.isUnary());
//		System.out.println("is add operator: " + model.isAddOperator());
//		System.out.println("is operate: " + model.isOperate());
//		System.out.println("is calculated: " + model.isCalculated());
//		System.out.println("is unary error: " + model.isUnaryError());
//		System.out.println("is operation error: " + model.isOperationError());
//		System.out.println("is wait second number: " + model.isWaitSecondNumber());
//		System.out.println("is Empty: " + model.getUnary().isEmpty());
	}
	
	public CalculationResult clickOperator(char op, String text) {
		double value = Double.parseDouble(text);

		if (model.isAddOperator()) {
			model.setFirstOperator(op);
			model.setNum1(value);
			
			return new CalculationResult(model.getOperand1(),model.getOperand2(),
					formatNumber(model.getNum1()), formatNumber(model.getNum2()), model.getFirstOperator(),
					model.getUnary(), model.isUnary(),
					"", model.isAddOperator(), model.isUnaryError(), model.isOperationError(), model.isOperate());
		}
		
		if (model.getFirstOperator() == ' ') {
			model.setNum1(value);
			model.setFirstOperator(op);
		}else {
			model.setNum2(value);
			if (!operate()) {
				model.setWaitSecondNumber(true);
				
				return new CalculationResult(model.getOperand1(),model.getOperand2(),
						formatNumber(model.getNum1()), formatNumber(model.getNum2()), model.getFirstOperator(),
						model.getUnary(), model.isUnary(),
						"", model.isAddOperator(), model.isUnaryError(), model.isOperationError(), model.isOperate());
				
			}
			model.setOperate(true);
		}
		
		model.setSecondOperator(op);
		model.setWaitSecondNumber(true);
		if (!model.isOperate()) {
			model.setAddOperator(true);
		}
		
		return new CalculationResult(model.getOperand1(),model.getOperand2(),
				formatNumber(model.getNum1()), formatNumber(model.getNum2()), model.getFirstOperator(),
				model.getUnary(), model.isUnary(),
				formatNumber(model.getAnswer()), model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate());
	}
//	
//	public CalculationResult inputInverse(String text) {
//		return inverse(text);
//	}
//	
//	public void ifCalculated() {
//		if (model.isCalculated()) {
//			model.setNum1(0);
//			model.setNum2(0);
//			model.setAnswer(0);
//			model.setOperator(' ');
//			model.setUnaryCurrent("");
//			model.setUnaryPrevious("");
//			model.setCalculated(false);
//			model.setAddOperator(false);
//		}
//	}
//	private CalculationResult  inverse(String text) {
//		double value = Double.parseDouble(text);
//		
//		if(value == 0) {
//			return new CalculationResult(formatNumber(value), "", ' ', List.of(),false, false, "", false, true);
//		}
//		
//		double answer = 1.0/value;
//		String result = formatNumber(answer);
//		UnaryOperator unary = UnaryOperator.INVERSE;
//		model.addUnary(unary);
//		if (model.getOperator() == ' ') {
//			model.setFirstUnary(true);
//		}else {
//			model.setSecondUnary(true);
//		}
//	}
//	
	public CalculationResult clickSquareRoot(String text) {
		return squareRoot(text);
	}
	
	private CalculationResult squareRoot(String text) {
		double value = Double.parseDouble(text);
		
		if (model.getFirstOperator() == ' ') {
			DivisionNumber first = DivisionNumber.FIRST;
			UnaryOperator sqrt = UnaryOperator.SQRT;
			model.addUnary(first, sqrt);
			if (!model.isUnary()) {
				model.setOperand1(formatNumber(value));
			}
			
			if(value <0) {
				model.setUnaryError(true);
				model.setUnaryError(true);
				return new CalculationResult(model.getOperand1(),model.getOperand2(),
						formatNumber(model.getNum1()), formatNumber(model.getNum2()), ' ',
						model.getUnary(), model.isUnary(),
						"", model.isAddOperator(), model.isUnaryError(), model.isOperationError(), model.isOperate());
			}
			
			
		}else {
			DivisionNumber second = DivisionNumber.FIRST;
			UnaryOperator sqrt = UnaryOperator.SQRT;
			model.addUnary(second, sqrt);
			if (!model.isUnary()) {
				model.setOperand2(formatNumber(value));
			}
			if(value <0) {
				value = -value;
				model.setOperand2(formatNumber(value));
				model.setUnaryError(true);
				return new CalculationResult(model.getOperand1(),model.getOperand2(),
						formatNumber(model.getNum1()), formatNumber(model.getNum2()), ' ',
						model.getUnary(), model.isUnary(),
						"", model.isAddOperator(), model.isUnaryError(), model.isOperationError(), model.isOperate());
			}
		}
		
		double result = Math.sqrt(value);
		model.setUnary(true);
		return new CalculationResult(model.getOperand1(),model.getOperand2(),formatNumber(model.getNum1()),
				formatNumber(model.getNum2()), ' ',model.getUnary(),
				model.isUnary(), formatNumber(result), model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate());
	}

	public boolean isCalculated() {
		return model.isCalculated();
	}

	public String backspace(String text) {

		if(text.length() <=1) {
			return "0";
		}
		return text.substring(0, text.length() -1);
	}

	private boolean operate() {
		switch (model.getFirstOperator()) {
		case '+':
			model.setAnswer(model.getNum1() + model.getNum2());
			break;
		case '-':
			model.setAnswer(model.getNum1() - model.getNum2());
			break;
		case 'x':
			model.setAnswer(model.getNum1() * model.getNum2());
			break;
		case '÷':
			if(model.getNum2() ==0) {
				return false;
			}else {
				model.setAnswer(model.getNum1() / model.getNum2());
			}
			break;
		}
		return true;
	}

}
