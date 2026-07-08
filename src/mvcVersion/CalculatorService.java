package mvcVersion;

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
			model.ifOperate();
		}
		
		if (model.isUnaryError() || model.isOperationError()) {
			model.clear();
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
//	
//	public boolean isAddOperator() {
//		return model.isAddOperator();
//	}
	public void clickClear() {
		model.clear();
	}
	
	public CalculationResult clickOperator(char op, String text) {
		
		if (model.isOperate()) {
			model.ifOperate();
		}
		double value = Double.parseDouble(text);
		
		if (model.getFirstOperator() == ' ') {
			model.setNum1(value);
			model.setFirstOperator(op);
		}else {

			if (model.isAddOperator()) {
				model.setFirstOperator(op);
				model.setNum1(value);
				
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate());
			}
			model.setNum2(value);
			if (!operate()) {
				model.setWaitSecondNumber(true);
				
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate());
				
			}
			
			model.setSecondOperator(op);
			model.setOperate(true);
		}
		
		model.setWaitSecondNumber(true);
		model.setAddOperator(true);
		System.out.println("Answer: " + model.getAnswer());
		System.out.println("Operand2: " + model.getOperand2());
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), model.getAnswer(), model.isAddOperator(),
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
		model.setAddOperator(false);
		double value = Double.parseDouble(text);
		if (model.isOperate()) {
			model.ifOperate();
		}
		if (model.getFirstOperator() == ' ') {
			DivisionNumber first = DivisionNumber.FIRST;
			UnaryOperator sqrt = UnaryOperator.SQRT;
			model.addUnary(first, sqrt);
			if (model.getUnary().isEmpty()) {
				model.setOperand1(value);
			}
			
			if(value <0) {
				model.setUnaryError(true);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), value, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate());
			}
			
			model.setNum1(Math.sqrt(value));
		}else {
			DivisionNumber second = DivisionNumber.SECOND;
			UnaryOperator sqrt = UnaryOperator.SQRT;
			model.addUnary(second, sqrt);
			if (!model.getUnary().isEmpty()) {
				model.setOperand2(value);
			}
			System.out.println("operand2: " + model.getOperand2());
			if(value <0) {
				value = -value;
				model.setOperand2(value);
				model.setUnaryError(true);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), value, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate());
			}
			model.setNum2(Math.sqrt(value));
		}
		double result = Math.sqrt(value);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), result, model.isAddOperator(),
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
