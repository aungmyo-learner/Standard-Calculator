package mvcVersion;

public class CalculatorService {
	private final CalculatorModel model;
	
	public CalculatorService(CalculatorModel model) {
		this.model = model;
	}
	
	public NumberResult inputNumber(String currentText, int value) {
		return number(currentText, value);
	}
	
	private NumberResult number(String currentText, int value) {

		if (model.isUnaryError() || model.isOperationError()) {
			model.clear();
		}

		if (model.isOperate()) {
			model.ifOperate();
		}

		if (model.isWaitSecondNumber()) {
			currentText = "";
			model.setAddOperator(false);
			model.setWaitSecondNumber(false);
		}
		
		if (currentText.equals("0")) {
			return new NumberResult(currentText, value, false, model.getUnary(), model.getOperand1(),
					 true, model.isUnaryError(), model.isOperationError(), DivisionNumber.FIRST);
		}
		
		if(model.getUnary().containsKey(DivisionNumber.FIRST) && model.getFirstOperator() == ' ') {
			return new NumberResult(currentText, value, true, model.getUnary(), model.getOperand1(),
					false, model.isUnaryError(), model.isOperationError(), DivisionNumber.FIRST);
		}
		return new NumberResult(currentText, value, false, model.getUnary(),model.getOperand1(),
				false, model.isUnaryError(), model.isOperationError(), DivisionNumber.FIRST);
	}

	public boolean isUnaryError() {
		return model.isUnaryError();
	}
	
	public boolean isOperationError() {
		return model.isOperationError();
	}
	
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
						model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
			}
			model.setNum2(value);
			if (!operate()) {
				model.setWaitSecondNumber(true);
				model.setSecondOperator(op);
				model.setOperationError(true);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
				
			}
			model.setSecondOperator(op);
			model.setOperate(true);
		}
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		model.setWaitSecondNumber(true);
		model.setAddOperator(true);
		
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), model.getAnswer(), model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
	}
	
	public CalculationResult clickSQR(String text) {
		return sqr(text);
	}
	
	private CalculationResult sqr(String text) {
		model.setAddOperator(false);
		double value = Double.parseDouble(text);
		if (model.isOperate()) {
			model.ifOperate();
		}
		UnaryOperator sqr = UnaryOperator.SQR;
		UnaryResult result = sqr.calculate(value);
		if (model.getFirstOperator() == ' ') {
			DivisionNumber first = DivisionNumber.FIRST;

			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			
			model.addUnary(first, sqr);
			
			if (result.error()) {
				return unaryError(value, sqr);
			}
			model.setNum1(result.result());
			
		}else {
			DivisionNumber second = DivisionNumber.SECOND;

			if (!model.getUnary().containsKey(second)) {
				model.setOperand2(value);
			}
			
			model.addUnary(second, sqr);
			
			if (result.error()) {
				return unaryError(value, sqr);
			}
			
			model.setNum2(result.result());
		}
		
		return UnaryResult(result.result());
	}
	
	public CalculationResult clickInverse(String text) {
		return inverse(text);
	}
	
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
	
	private CalculationResult  inverse(String text) {
		model.setAddOperator(false);
		double value = Double.parseDouble(text);
		if (model.isOperate()) {
			model.ifOperate();
		}
		UnaryOperator inverse = UnaryOperator.INVERSE;
		UnaryResult result = inverse.calculate(value);
		if (model.getFirstOperator() == ' ') {
			DivisionNumber first = DivisionNumber.FIRST;

			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			
			model.addUnary(first, inverse);
			
			if (result.error()) {
				return unaryError(value, inverse);
			}
			model.setNum1(result.result());
			
		}else {
			DivisionNumber second = DivisionNumber.SECOND;

			if (!model.getUnary().containsKey(second)) {
				model.setOperand2(value);
			}
			
			model.addUnary(second, inverse);
			
			if (result.error()) {
				return unaryError(value, inverse);
			}
			
			model.setNum2(result.result());
		}
		
		return UnaryResult(result.result());
	}
	
	public CalculationResult clickSquareRoot(String text) {
		return squareRoot(text);
	}
	
	private CalculationResult squareRoot(String text) {
		model.setAddOperator(false);
		double value = Double.parseDouble(text);
		if (model.isOperate()) {
			model.ifOperate();
		}
		UnaryOperator sqrt = UnaryOperator.SQRT;
		UnaryResult result = sqrt.calculate(value);
		
		if (model.getFirstOperator() == ' ') {
			DivisionNumber first = DivisionNumber.FIRST;
			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			model.addUnary(first, sqrt);
			
			
			if (result.error()) {
				return unaryError(value, sqrt);
			}
			model.setNum1(result.result());
		}else {
			DivisionNumber second = DivisionNumber.SECOND;

			if (!model.getUnary().containsKey(second)) {
				model.setOperand2(value);
			}
			
			model.addUnary(second, sqrt);
			
			if (result.error()) {
				return unaryError(value, sqrt);
			}
			
			model.setNum2(result.result());
		}
		return UnaryResult(result.result());
	}
	
	private CalculationResult UnaryResult(double result) {
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), result, model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
	}
	private CalculationResult unaryError(double value,UnaryOperator unary) {
		model.setUnaryError(true);
		model.setUnaryErrorOperator(unary);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), value, model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
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
