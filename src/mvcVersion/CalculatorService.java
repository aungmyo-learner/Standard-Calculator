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
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult unaryAndSuccess(String currentText, int value) {
		InputState inputState = model.getInputState();
		model.setInputState(InputState.NORMAL);
		
		if (model.getUnaryPosition() == UnaryPositon.SECOND) {
			model.removeUnary(UnaryPositon.SECOND);
			model.setUnaryPosition(UnaryPositon.NONE);
			model.setOperand2(0);
			return new NumberResult(currentText, value, model.getUnary(),
					model.getOperand1(), inputState, model.getOperationState(),
					model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
		}
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), inputState, model.getOperationState(),
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult normalAndSuccess(String currentText, int value) {
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), model.getOperationState(),
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult afterUnary(String currentText, int value) {
		InputState inputState = model.getInputState();
		model.setInputState(InputState.NORMAL);
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), inputState, model.getOperationState(),
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult number(String currentText, int value) {
		
		System.out.println("Service number method\ninput state: " + model.getInputState() +
				"\noperator: " + model.getFirstOperator() +
				"\noperation state: " + model.getOperationState()+
				"\nunary position: " + model.getUnaryPosition() +
				"\nnum1: " + model.getNum1() +
				"\nis first unary: " + model.getUnary().containsKey(UnaryPositon.FIRST) +
				"\nis second unary: " + model.getUnary().containsKey(UnaryPositon.SECOND) +
				"\n______________________");
		
		state();
		if (model.getOperationState() == OperationState.ERROR) {
			return error(currentText, value);			
		}
		
		if (model.getInputState() == InputState.AFTER_UNARY && model.getOperationState() == OperationState.SUCCESS) {
			return unaryAndSuccess(currentText, value);
		}
		
		if (model.getInputState() == InputState.AFTER_UNARY &&
				model.getUnaryPosition() == UnaryPositon.FIRST &&
				model.getFirstOperator() == ' ') {
			return afterUnary(currentText, value);
		}

		if (model.getInputState() == InputState.NORMAL && model.getOperationState() == OperationState.SUCCESS) {
			return normalAndSuccess(currentText, value);
		}
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), model.getInputState(), model.getOperationState(),
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}

	public void clickClear() {
		model.clear();
	}
	
	public EqualResult clickEqual(String text) {
		return equal(text);
	}
	
	private EqualResult equal(String text) {
		
		double value = Double.parseDouble(text);
		model.setHistoryState(HistoryState.SHOW);
		
		if (model.getUnaryPosition() != UnaryPositon.NONE && model.getFirstOperator() == ' ') {
			model.setInputState(InputState.AFTER_EQUAL);
			return new EqualResult(model.getUnary(), model.getFirstOperator(), model.getOperand1(),
					model.getOperand2(), model.getOperationState(), model.getHistoryState(),
					model.getUnaryPosition(), model.getNum1(), model.getNum2(), value);
			
		}
		
		if (model.getInputState() == InputState.AFTER_EQUAL) {
			model.setNum1(value);
			if (!operate()) {
				model.setHistoryState(HistoryState.NONE);
				model.setOperationState(OperationState.ERROR);
				return new EqualResult(model.getUnary(), model.getFirstOperator(), model.getOperand1(),
						model.getOperand2(), model.getOperationState(), model.getHistoryState(),
						model.getUnaryPosition(), model.getNum1(), model.getNum2(), 0);
			}
		}else {
			model.setNum2(value);
			if (!operate()) {
				model.setHistoryState(HistoryState.NONE);
				model.setOperationState(OperationState.ERROR);
				return new EqualResult(model.getUnary(), model.getFirstOperator(), model.getOperand1(),
						model.getOperand2(), model.getOperationState(), model.getHistoryState(),
						model.getUnaryPosition(), model.getNum1(), model.getNum2(), 0);
			}
			return new EqualResult(model.getUnary(), model.getFirstOperator(), model.getOperand1(),
					model.getOperand2(), model.getOperationState(), model.getHistoryState(),
					model.getUnaryPosition(), model.getNum1(), model.getNum2(), model.getAnswer());
		}
		model.setInputState(InputState.AFTER_EQUAL);
		
	return new EqualResult(model.getUnary(), model.getFirstOperator(), model.getOperand1(),
			model.getOperand2(), model.getOperationState(), model.getHistoryState(),
			model.getUnaryPosition(), model.getNum1(), model.getNum2(), value);
	}
	
	public CalculationResult clickOperator(char op, String text) {
		state();
		System.out.println("input state: " + model.getInputState() +
				"\noperator1: " + model.getFirstOperator() +
				"\noperator2: " + model.getSecondOperator() +
				"\noperation state: " + model.getOperationState()+
				"\nunary position: " + model.getUnaryPosition() +
				"\nnum1: " + model.getNum1() +
				"\nis first unary: " + model.getUnary().containsKey(UnaryPositon.FIRST) +
				"\nis second unary: " + model.getUnary().containsKey(UnaryPositon.SECOND) +
				"\n______________________");
		if (model.getInputState() == InputState.NORMAL &&
				model.getUnaryPosition() == UnaryPositon.FIRST &&
				model.getFirstOperator() == ' ') {
			model.clearUnary();
			model.setUnaryPosition(UnaryPositon.NONE);
		}
		double value = Double.parseDouble(text);
		
		if (model.getFirstOperator() == ' ') {
			model.setNum1(value);
			model.setFirstOperator(op);
		}else {

			if (model.getInputState() == InputState.AFTER_OPERATOR) {
				model.setFirstOperator(op);
				model.setNum1(value);
				model.setHistoryState(HistoryState.NONE);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.getUnaryPosition(), model.getInputState(),
						model.getOperationState(), model.getHistoryState(), model.getUnaryErrorOperator());
			}
			
			model.setNum2(value);
			if (!operate()) {
				model.setSecondOperator(op);
				model.setHistoryState(HistoryState.NONE);
				model.setOperationState(OperationState.ERROR);
				return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
						model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
						model.getUnary(), 0, model.getUnaryPosition(), model.getInputState(),
						model.getOperationState(), model.getHistoryState(), model.getUnaryErrorOperator());
				
			}
			model.setSecondOperator(op);
			model.setHistoryState(HistoryState.SHOW);
			
		}
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		model.setInputState(InputState.AFTER_OPERATOR);
		if (model.getSecondOperator() == ' ') {
			model.setOperationState(OperationState.SUCCESS);
		}
		
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), model.getAnswer(), model.getUnaryPosition(), model.getInputState(),
				model.getOperationState(), model.getHistoryState(), model.getUnaryErrorOperator());
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
			UnaryPositon first = UnaryPositon.FIRST;
			unaryState();

			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			
			model.addUnary(first, sqr);
			
			if (result.error()) {
				return unaryError(value, sqr);
			}
			model.setNum1(result.result());
		}else {
			UnaryPositon second = UnaryPositon.SECOND;
			unaryState();

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
			UnaryPositon first = UnaryPositon.FIRST;
			unaryState();
			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			
			model.addUnary(first, inverse);
			
			if (result.error()) {
				return unaryError(value, inverse);
			}
			model.setNum1(result.result());
			
		}else {
			UnaryPositon second = UnaryPositon.SECOND;
			unaryState();
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
		if (model.getInputState() == InputState.AFTER_EQUAL) {
			model.clear();
		}
		if (model.getHistoryState() == HistoryState.SHOW) {
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
			UnaryPositon first = UnaryPositon.FIRST;
			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			model.addUnary(first, sqrt);
			
			
			if (result.error()) {
				return unaryError(value, sqrt);
			}
			model.setNum1(result.result());
			unaryState();
		}else {
			UnaryPositon second = UnaryPositon.SECOND;

			if (!model.getUnary().containsKey(second)) {
				model.setOperand2(value);
			}
			
			model.addUnary(second, sqrt);
			
			if (result.error()) {
				return unaryError(value, sqrt);
			}
			
			model.setNum2(result.result());
			unaryState();
		}
		return UnaryResult(result.result());
	}
	
	private void unaryState() {
		model.setInputState(InputState.AFTER_UNARY);
		if (model.getFirstOperator() == ' ') {
			model.setUnaryPosition(UnaryPositon.FIRST);
		}else {
			model.setUnaryPosition(UnaryPositon.SECOND);
		}
	}
	private CalculationResult UnaryResult(double result) {
		model.setHistoryState(HistoryState.NONE);
		model.setUnaryErrorOperator(UnaryOperator.NOUNARY);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), result, model.getUnaryPosition(), model.getInputState(),
				model.getOperationState(), model.getHistoryState(), model.getUnaryErrorOperator());
	}
	
	private CalculationResult unaryError(double value,UnaryOperator unary) {
		model.setOperationState(OperationState.ERROR);
		model.setHistoryState(HistoryState.NONE);
		model.setUnaryErrorOperator(unary);
		return new CalculationResult(model.getOperand1(),model.getOperand2(), model.getNum1(),
				model.getNum2(), model.getFirstOperator(), model.getSecondOperator(),
				model.getUnary(), value, model.getUnaryPosition(), model.getInputState(),
				model.getOperationState(), model.getHistoryState(), model.getUnaryErrorOperator());
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
