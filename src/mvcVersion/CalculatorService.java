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
		
		if (model.getUnaryPosition() == UnaryPosition.SECOND) {
			model.removeUnary(UnaryPosition.SECOND);
			if (model.getUnary().containsKey(UnaryPosition.FIRST)) {
				model.setUnaryPosition(UnaryPosition.FIRST);
			}else {
				model.setUnaryPosition(UnaryPosition.NONE);
			}
			model.setOperand2(0);
			model.setNum2(0);
			return new NumberResult(currentText, value, model.getUnary(),
					model.getOperand1(), inputState, model.getOperationState(),
					model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
		}
		
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), inputState, model.getOperationState(),
				model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
	}
	
	private NumberResult success(String currentText, int value) {
		InputState inputState = model.getInputState();
		model.setInputState(InputState.NORMAL);
		return new NumberResult(currentText, value, model.getUnary(),
				model.getOperand1(), inputState, model.getOperationState(),
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
		state();
		System.out.println("Service number method\ninput state: " + model.getInputState() +
				"\noperator: " + model.getFirstOperator() +
				"\noperation state: " + model.getOperationState()+
				"\nunary position: " + model.getUnaryPosition() +
				"\nnum1: " + model.getNum1() +
				"\nnum2: " + model.getNum2() +
				"\nis first unary: " + model.getUnary().containsKey(UnaryPosition.FIRST) +
				"\nis second unary: " + model.getUnary().containsKey(UnaryPosition.SECOND) +
				"\n______________________");
		
		if (model.getOperationState() == OperationState.ERROR) {
			return error(currentText, value);			
		}
		
		if (model.getInputState() == InputState.AFTER_UNARY && model.getOperationState() == OperationState.SUCCESS) {
			return unaryAndSuccess(currentText, value);
		}
		
		if (model.getOperationState() == OperationState.SUCCESS && model.getInputState() == InputState.AFTER_PERCENTAGE) {
			InputState inputState = model.getInputState();
			model.setInputState(InputState.NORMAL);
			model.setNum2(0);
			return new NumberResult(currentText, value, model.getUnary(),
					model.getOperand1(), inputState, model.getOperationState(),
					model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
		}
		if (model.getInputState() == InputState.AFTER_UNARY &&
				model.getUnaryPosition() == UnaryPosition.FIRST &&
				model.getFirstOperator() == ' ') {
			return afterUnary(currentText, value);
		}

		if ((model.getInputState() == InputState.NORMAL && model.getOperationState() == OperationState.SUCCESS)
				|| (model.getInputState() == InputState.AFTER_OPERATOR && model.getOperationState() == OperationState.SUCCESS)) {
			return success(currentText, value);
		}
		if (model.getInputState() == InputState.AFTER_EQUAL) {
			InputState inputState = model.getInputState();
			model.setInputState(InputState.NORMAL);
			return new NumberResult(currentText, value, model.getUnary(),
					model.getOperand1(), inputState, model.getOperationState(),
					model.getUnaryPosition(), model.getHistoryState(), model.getNum1(), model.getFirstOperator());
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
		
		if ( model.getFirstOperator() == ' ') {
			model.setNum1(value);
			
		}else {
			if (model.getInputState() == InputState.AFTER_EQUAL) {
				model.setNum1(value);
				
				if (!operate()) {
					model.setHistoryState(HistoryState.NONE);
					model.setOperationState(OperationState.ERROR);
					return new EqualResult(text, model.getUnary(), model.getFirstOperator(), model.getOperand1(),
							model.getOperand2(), model.getOperationState(), model.getHistoryState(),
							model.getUnaryPosition(), model.getNum1(), model.getNum2(), 0);
				}
			}else {
				model.setNum2(value);
				if (!operate()) {
					model.setHistoryState(HistoryState.NONE);
					model.setOperationState(OperationState.ERROR);
					return new EqualResult(text, model.getUnary(), model.getFirstOperator(), model.getOperand1(),
							model.getOperand2(), model.getOperationState(), model.getHistoryState(),
							model.getUnaryPosition(), model.getNum1(), model.getNum2(), 0);
				}
				
			}
			model.setInputState(InputState.AFTER_EQUAL);
			model.setOperationState(OperationState.SUCCESS);
			return new EqualResult(text, model.getUnary(), model.getFirstOperator(), model.getOperand1(),
					model.getOperand2(), model.getOperationState(), model.getHistoryState(),
					model.getUnaryPosition(), model.getNum1(), model.getNum2(), model.getAnswer());
		}
		
	return new EqualResult(text, model.getUnary(), model.getFirstOperator(), model.getOperand1(),
			model.getOperand2(), model.getOperationState(), model.getHistoryState(),
			model.getUnaryPosition(), model.getNum1(), model.getNum2(), value);
	}
	
	public CalculationResult clickOperator(char op, String text) {
		state();
		System.out.println("Serivce clickOperator\ninput state: " + model.getInputState() +
				"\noperator1: " + model.getFirstOperator() +
				"\noperator2: " + model.getSecondOperator() +
				"\noperation state: " + model.getOperationState()+
				"\nunary position: " + model.getUnaryPosition() +
				"\nnum1: " + model.getNum1() +
				"\nnum2: " + model.getNum2() +
				"\nis first unary: " + model.getUnary().containsKey(UnaryPosition.FIRST) +
				"\nis second unary: " + model.getUnary().containsKey(UnaryPosition.SECOND)+
				"\n______________________");
		
		if (model.getInputState() == InputState.NORMAL &&
				model.getUnaryPosition() == UnaryPosition.FIRST &&
				model.getOperationState() == OperationState.NORMAL) {
			model.clearUnary();
			model.setUnaryPosition(UnaryPosition.NONE);
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
			UnaryPosition first = UnaryPosition.FIRST;
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
			UnaryPosition second = UnaryPosition.SECOND;
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
			UnaryPosition first = UnaryPosition.FIRST;
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
			UnaryPosition second = UnaryPosition.SECOND;
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
			return;
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
			UnaryPosition first = UnaryPosition.FIRST;
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
			UnaryPosition second = UnaryPosition.SECOND;

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

	public CalculationResult clickToggle(String text) {
		return toggle(text);
	}
	
	private CalculationResult toggle(String text) {
		model.setInputState(InputState.NORMAL);
		double value = Double.parseDouble(text);
		state();
		
		UnaryOperator toggle = UnaryOperator.TOGGLE;
		UnaryResult result = toggle.calculate(value);
		

		if (model.getFirstOperator() == ' ') {
			UnaryPosition first = UnaryPosition.FIRST;
			if (!model.getUnary().containsKey(first)) {
				model.setOperand1(value);
			}
			model.addUnary(first, toggle);
			
			
			if (result.error()) {
				return unaryError(value, toggle);
			}
			model.setNum1(result.result());
			unaryState();
		}else {
			UnaryPosition second = UnaryPosition.SECOND;

			if (!model.getUnary().containsKey(second)) {
				model.setOperand2(value);
			}
			
			model.addUnary(second, toggle);
			
			if (result.error()) {
				return unaryError(value, toggle);
			}
			
			model.setNum2(result.result());
			unaryState();
		}
		return UnaryResult(result.result());
	}
	
	private void unaryState() {
		model.setInputState(InputState.AFTER_UNARY);
		if (model.getFirstOperator() == ' ') {
			model.setUnaryPosition(UnaryPosition.FIRST);
		}else {
			model.setUnaryPosition(UnaryPosition.SECOND);
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
	
	public PercentageResult clickPercentage(String text) {
		return percentage(text);
	}
	private PercentageResult percentage(String text) {
		double result =0;
			double value = Double.parseDouble(text);
			if(model.getFirstOperator() == '+' || model.getFirstOperator() == '-') {
				
				result = model.getNum1() * value / 100;
				percentageState(result);
			}else if(model.getFirstOperator() == 'x' || model.getFirstOperator() == '÷') {
				
				result = value / 100;
				percentageState(result);
			}
		return new PercentageResult(model.getInputState(), model.getOperationState(), model.getNum1(),
				model.getNum2(), result, model.getFirstOperator(), model.getOperand1(), model.getUnaryPosition(),
				model.getUnary());
	}
	
	private void percentageState(double result) {
		model.setNum2(result);
		model.setOperationState(OperationState.SUCCESS);
		model.setInputState(InputState.AFTER_PERCENTAGE);
	}
}
