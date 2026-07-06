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
		
		model.operatorDisableOn();
		
		if (model.isWaitSecondNumber()) {
			currentText = "";
			model.setAddOperator(false);
			model.setWaitSecondNumber(false);
		}
		if (currentText.equals("0")) {
			return formatNumber(value);
		}
		return currentText + formatNumber(value);
	}
	
	public boolean isOperatorDisableOff() {
		return model.isOperatorDisableOff();
	}
	
	public SolveOperator inputOperator(char op, String text) {
		double value = Double.parseDouble(text);

		String history = "";
		String progress = "";
		String answer ="";
		
		if (model.isAddOperator()) {
			model.setOperator(op);
			model.setNum1(value);
			
			if (model.isUnary() && !model.getUnaryPrevious().isEmpty()) {
				progress = model.getUnaryPrevious() + " " + model.getOperator();
			}else {
				progress = formatNumber(value) + " " + op;
			}
			
			answer = formatNumber(value);
			return new SolveOperator(progress, history, answer);
		}
		
		if (model.getOperator() == ' ') {
			model.setNum1(value);
		}else {
			model.setNum2(value);
			if (!operate()) {
				progress = formatNumber(model.getNum1()) + " " + model.getOperator() +
						" " + formatNumber(model.getNum2()) + " " + op;
				answer = "Cannot divided by zero";
				model.setNum1(0);
				model.setOperator(' ');
				model.operatorDisableOff();
				model.setWaitSecondNumber(true);
				model.setUnary(false);
				return new SolveOperator(progress, history, answer);
			}
			
			if (model.isUnary()) {
				
				history = model.getUnaryPrevious() + " " +
						model.getOperator() + " " + 
						model.getUnaryPrevious() + "=\n" + formatNumber(model.getAnswer());
				
				model.setUnary(false);
				
			}else {
				history = formatNumber(model.getNum1()) + " " +
						model.getOperator() + " " +
						formatNumber(model.getNum2()) + "=\n" + formatNumber(model.getAnswer());
			}
			answer = formatNumber(model.getAnswer());
			model.setNum1(model.getAnswer());
			model.setAnswer(0);
			model.setNum2(0);
		}
		
		model.setOperator(op);
		model.setWaitSecondNumber(true);
		model.setAddOperator(true);
		progress = formatNumber(model.getNum1()) + " " + op;
		return new SolveOperator(progress, history, answer);
	}
	
	public String squareRoot(String text) {
		double value = Double.parseDouble(text);
		
		if(value <0) {
			model.setUnaryCurrent("\u221A(" + formatNumber(value) + ")");
			return "Invalid input";
		}
		
		double result = Math.sqrt(value);

		if (model.getUnaryCurrent().isEmpty()) {
			model.setUnaryCurrent( formatNumber(value));
		}
		model.setUnaryCurrent("\u221A(" + model.getUnaryCurrent() + ")");
		model.setUnary(true);
		return formatNumber(result);
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

	public String getUnaryCurrent() {
		return model.getUnaryCurrent();
	}

	public String getUnaryPrevious() {
		return model.getUnaryPrevious();
	}

	public String showunaryProgress() {
		if(model.getOperator() == ' ') {
			return model.getUnaryCurrent();
		}
			if(model.getUnaryPrevious().isEmpty()) {
				return formatNumber(model.getNum1()) + " " + model.getOperator() +" " +  model.getUnaryCurrent();
			}
		return model.getUnaryPrevious() + " " + model.getOperator() + " " + model.getUnaryCurrent(); 
	}
	
	private boolean operate() {
		switch (model.getOperator()) {
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
