package mvcVersion;

public class CalculatorService {
	private final CalculatorModel model;
	
	public CalculatorService(CalculatorModel model) {
		this.model = model;
	}
	
	public NumberResult inputNumber(String currentText, int value) {
		return number(currentText, value);
	}
	
	private NumberResult error(String currentText, int value) {
		OperationState operationState = model.getOperationState();
		model.clear();
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), operationState,
				model.getUnaryState(), DivisionNumber.NONE, model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult afterOperatorAndSuccess(String currentText, int value) {
		InputState inputState = model.getInputState();
		model.setInputState(InputState.NORMAL);
		
		if (model.getUnary().containsKey(DivisionNumber.SECOND)) {
			model.removeUnary(DivisionNumber.SECOND);
			model.setUnaryState(UnaryState.NORMAL);
		}
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), inputState, model.getOperationState(),
				model.getUnaryState(), DivisionNumber.FIRST, model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult normalAndSuccess(String currentText, int value) {
		if (model.getUnary().containsKey(DivisionNumber.SECOND)) {
			model.removeUnary(DivisionNumber.SECOND);
			model.setOperand2(0);
		}
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), model.getOperationState(),
				model.getUnaryState(), DivisionNumber.NONE, model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult afterUnary(String currentText, int value) {
		UnaryState unaryState = model.getUnaryState();
		model.setUnaryState(UnaryState.STILL_UNARY);
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), model.getOperationState(),
				unaryState, DivisionNumber.FIRST, model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult number(String currentText, int value) {
		
		state();
		if (model.getOperationState() == OperationState.ERROR) {
			return error(currentText, value);			
		}
		
		if (model.getInputState() == InputState.AFTER_OPERATOR && model.getOperationState() == OperationState.SUCCESS) {
			return afterOperatorAndSuccess(currentText, value);
		}
		
		if (model.getInputState() == InputState.NORMAL && (model.getUnaryState() == UnaryState.AFTER_UNARY
				|| model.getUnaryState() == UnaryState.STILL_UNARY)) {
			return afterUnary(currentText, value);
		}

		if (model.getInputState() == InputState.NORMAL && model.getOperationState() == OperationState.SUCCESS) {
			return normalAndSuccess(currentText, value);
		}
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), model.getOperationState(),
				model.getUnaryState(), DivisionNumber.NONE, model.getNum1(), model.getFirstOperator());
	}

	public void clickClear() {
		model.clear();
	}
	
	public EqualResult clickEqual(String text) {
		return equal(text);
	}
	
	private EqualResult equal(String text) {
		if (text.equals("0") && model.getFirstOperator() == ' ') {
			return new EqualResult(true, model.getUnary(), model.getFirstOperator(),
					false, model.getOperand1(), model.getOperand2(),
					model.getNum1(), model.getNum2(), 0, model.isOperate());
		}
		double value = Double.parseDouble(text);
		
		if (!model.getUnary().isEmpty() && model.getFirstOperator() == ' ') {
			model.setEqualed(true);
			return new EqualResult(false, model.getUnary(), model.getFirstOperator(),
					false, model.getOperand1(), model.getOperand2(),
					model.getNum1(), model.getNum2(), value, false);
			
		}
		
		if (model.isEqualed()) {
			model.setNum1(value);
			if (!operate()) {
				model.setOperationError(true);
				return new EqualResult(false, model.getUnary(), model.getFirstOperator(),
						model.isOperationError(), model.getOperand1(), model.getOperand2(),
						model.getNum1(), model.getNum2(), 0, model.isOperate());
			}
		}else {
			model.setNum2(value);
			if (!operate()) {
				model.setOperationError(true);
				return new EqualResult(false, model.getUnary(), model.getFirstOperator(),
						model.isOperationError(), model.getOperand1(), model.getOperand2(),
						model.getNum1(), model.getNum2(), 0, model.isOperate());
			}
		}
		model.setOperationError(false);
		model.setOperate(true);
		model.setEqualed(true);
	return new EqualResult(false, model.getUnary(), model.getFirstOperator(),
			model.isOperationError(), model.getOperand1(), model.getOperand2(),
			model.getNum1(), model.getNum2(), model.getAnswer(), model.isOperate());
	}
	
	public CalculationResult clickOperator(char op, String text) {
		state();
		System.out.println("operator operation state: " + model.getOperationState() +
				"\n input state: " + model.getInputState() +
				"\n__________________");
		if (model.getUnaryState() == UnaryState.STILL_UNARY) {
			model.clearUnary();
			model.setUnaryState(UnaryState.NORMAL);
		}
		double value = Double.parseDouble(text);
		
		if (model.getFirstOperator() == ' ') {
			model.setNum1(value);
			model.setFirstOperator(op);
		}else {

			if (model.getInputState() == InputState.AFTER_OPERATOR) {
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
				model.setOperationState(OperationState.ERROR);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.isAddOperator(),
						model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
				
			}
			model.setSecondOperator(op);
			model.setOperate(true);
		}
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		model.setInputState(InputState.AFTER_OPERATOR);
		model.setOperationState(OperationState.SUCCESS);
		
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), model.getAnswer(), model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
	}
	
	public CalculationResult clickSQR(String text) {
		return sqr(text);
	}
	
	private CalculationResult sqr(String text) {
		model.setInputState(InputState.NORMAL);
		double value = Double.parseDouble(text);
		state();
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
	
	private CalculationResult  inverse(String text) {
		model.setInputState(InputState.NORMAL);
		double value = Double.parseDouble(text);
		state();
		
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
	private void state() {
		if (model.isEqualed()) {
			model.clear();
		}
		if (model.isOperate()) {
			model.ifOperate();
		}
	}
	public CalculationResult clickSquareRoot(String text) {
		return squareRoot(text);
	}
	
	private CalculationResult squareRoot(String text) {
		model.setInputState(InputState.NORMAL);
		double value = Double.parseDouble(text);
		state();
		
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
		model.setUnaryState(UnaryState.AFTER_UNARY);
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), result, model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
	}
	
	private CalculationResult unaryError(double value,UnaryOperator unary) {
		model.setUnaryState(UnaryState.NORMAL);
		model.setOperationState(OperationState.ERROR);
		model.setUnaryErrorOperator(unary);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), value, model.isAddOperator(),
				model.isUnaryError(), model.isOperationError(), model.isOperate(), model.getUnaryErrorOperator());
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
