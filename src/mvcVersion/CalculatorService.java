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
	
	public String inputOperation(char op, String text) {
		double value = Double.parseDouble(text);
		
		if (model.getOperator() == ' ') {
			model.setNum1(value);
		}else {
			model.setNum2(value);
			if (!operate()) {
				text = formatNumber(model.getNum1()) + " " + model.getOperator() +
						" " + formatNumber(model.getNum2()) + " " + op;
				
				model.setNum1(0);
				model.setOperator(' ');
				model.operatorDisableOff();
				model.setWaitSecondNumber(true);
				model.setUnary(false);
				return text;
			}
		}
		
		model.setOperator(op);
		model.setWaitSecondNumber(true);
		return formatNumber(model.getNum1()) + " " + op;
	}
	public String backspace(String text) {

		if(text.length() <=1) {
			return "0";
		}
		return text.substring(0, text.length() -1);
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
