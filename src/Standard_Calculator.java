
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Standard_Calculator extends Application {
	
//	private String[] chars = new String[] {
//			"%", "CE", "C", "⌫",
//			"1/x", "x\u00B2", "\u221A", "÷",
//			"7", "8", "9", "x",
//			"4", "5", "6", "-",
//			"1", "2", "3", "+",
//			"\u00B1", "0", ".", "="
//	};
//	private TilePane btnPane = new TilePane();
	
	private Button percentage = new Button("%");
	private Button ce = new Button("CE");
	private Button c = new Button("C");
	private Button backspace = new Button("⌫"); // "\u232B"
	
	private Button inverse = new Button("1/x");
	private Button superscript = new Button("x\u00B2"); //"x\u00B2"
	private Button squareRoot = new Button("\u221A"); // "\u221A"
	private Button divide = new Button("÷");
	
	private Button multiple = new Button("x");
	private Button minus = new Button("-");
	private Button plus = new Button("+");
	private Button toggle = new Button("\u00B1");
	
	private Button decimal = new Button(".");
	private Button equal = new Button("=");
	
	private Button memoryStore = new Button("MS");
	private Button memoryClear = new Button("MC");
	private Button memoryRecover = new Button("MR");
	private Button memoryPlus = new Button("M+");
	private Button memoryMinus = new Button("M-");
	
	private HBox box = new HBox();
	
	private ListView<String> history = new ListView<>();
	private Button historyTrash = new Button("🗑"); //\uD83D\uDDD1
	private BorderPane historyPane = new BorderPane();
	Tab historyTab = new Tab("History", historyPane);

	private ListView<String> memory = new ListView<>();
	private Button memoryTrash = new Button("🗑"); //\uD83D\uDDD1
	private BorderPane memoryPane = new BorderPane();
	Tab memoryTab = new Tab("Memory", memoryPane);
	
	private TextField progress = new TextField();
	private TextField current = new TextField("0");
	
	private Button[] numbers = new Button[10];
	
	private TabPane pane = new TabPane();
	private GridPane root = new GridPane();
	
	

	private boolean waittingForSecondNumber = false;
	private boolean calculated = false;
	private boolean isOperatorDisableOFF = false;
	private boolean isOperatorEmpty = false;
	private String unaryPrevious ="";
	private boolean isUnary = false;
	private String unaryCurrent ="";
	
	private double num1 =0, num2 =0, answer =0;
	private char operator =' ';
	private boolean addOperator = false;
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage st) throws Exception {
		
		Scene sc = addButtonsIntoRoot();
		clickProgress(sc);
		st.setScene(sc);
		st.setTitle("Standard Calculator");
		st.show();
		
	}
		
	private void clickProgress(Scene sc) {
		actionNoNumber(sc);
	}
	
	private void actionDecimal() {
		if (!current.getText().contains(".")) {
			current.setText(current.getText() + decimal.getText());
		}
	}
	
	private void actionNoNumber(Scene sc) {
		decimal.setOnAction(e->actionDecimal());
		
		sc.setOnKeyPressed(e->{
		    System.out.println("Printed: " + e.getCode());
			switch (e.getCode()) {
			case NUMPAD0: actionNumbers(0);
				break;
			
			case DIGIT0: actionNumbers(0);
				break;
				
			case NUMPAD1: actionNumbers(1);
				break;
				
			case DIGIT1: actionNumbers(1);
				break;
				
			case NUMPAD2: actionNumbers(2);
				break;
			
			case DIGIT2: actionNumbers(2);
				break;
				
			case NUMPAD3: actionNumbers(3);
				break;
			
			case DIGIT3: actionNumbers(3);
				break;
				
			case NUMPAD4: actionNumbers(4);
				break;
			
			case DIGIT4: actionNumbers(4);
				break;
				
			case NUMPAD5: actionNumbers(5);
				break;
				
			case DIGIT5:
				if (e.isShiftDown()) {
					actionPercentage();
				}else {
					actionNumbers(5);
				}
				break;
				
			case NUMPAD6: actionNumbers(6);
				break;
			
			case DIGIT6: actionNumbers(6);
				break;
				
			case NUMPAD7: actionNumbers(7);
				break;
			
			case DIGIT7: actionNumbers(7);
				break;
				
			case NUMPAD8: actionNumbers(8);
				break;
			
			case DIGIT8:
				if (e.isShiftDown()) {
					actionOperator('x');
				}else {
					actionNumbers(8);
				}
				break;
				
			case NUMPAD9: actionNumbers(9);
				break;
			
			case DIGIT9: actionNumbers(9);
				break;
				
			case BACK_SPACE: actionBackspace();
				break;
				
			case MULTIPLY: actionOperator('x');
				break;
				
			case ADD: actionOperator('+');
				break;
				
			case SUBTRACT:
			case MINUS: actionOperator('-');
				break;
				
			case SLASH: actionOperator('÷');
				break;
				
			case DIVIDE: actionOperator('÷');
				break;
				
			case ENTER:
			case EQUALS: 
				if (e.isShiftDown()) {
					actionOperator('+');
				}else {
					actionEqual();
				}
				break;
				
			case ESCAPE: actionC();
				break;
				
			case DELETE: actionCE();
				break;
			
			case DECIMAL: actionDecimal();
				break;
			case PERIOD: actionDecimal();
				break;
			default:
				break;
			}
		});
		
		memoryTrash.setOnAction(e->actionMemoryTrash());
		memoryMinus.setOnAction(e->actionMemoryMinus());
		memoryPlus.setOnAction(e->actionMemoryPlus());
		memoryStore.setOnAction(e->actionMemoryStore());
		memoryRecover.setOnAction(e->actionMemoryRecover());
		memoryClear.setOnAction(e->actionMemoryClear());
		squareRoot.setOnAction(e->actionSquareRoot());
		superscript.setOnAction(e->actionSuperScript());
		inverse.setOnAction(e->actionInverse());
		percentage.setOnAction(e->actionPercentage());
		toggle.setOnAction(e-> actionToggle());
		equal.setOnAction(e->actionEqual());
		c.setOnAction(e->actionC());
		backspace.setOnAction(e-> actionBackspace());
		ce.setOnAction(e-> actionCE());
		historyTrash.setOnAction(e->actionHistoryTrash());
		plus.setOnAction(e->actionOperator('+'));
		minus.setOnAction(e->actionOperator('-'));
		multiple.setOnAction(e->actionOperator('x'));
		divide.setOnAction(e->actionOperator('÷'));
	}
	
	private void actionMemoryTrash() {
		memory.getItems().clear();
		updateMemoryTrashButton();
		updateMemoryButton();
	}
	
	private void actionHistoryTrash() {
		history.getItems().clear();
		updateHistoryTrashButton();
	}
	
	private void actionMemoryMinus() {
		ObservableList<String> items = memory.getItems();
		
		if (!items.isEmpty()) {			
			double memoryValue = Double.parseDouble(items.get(0));
			double currentValue = Double.parseDouble(current.getText());
			double result = memoryValue - currentValue;
			memory.getItems().set(0, formatNumber(result));
		}
		updateMemoryTrashButton();
	}
	
	private void actionMemoryPlus() {
		ObservableList<String> items = memory.getItems();
		
		if (!items.isEmpty()) {			
			double memoryValue = Double.parseDouble(items.get(0));
			double currentValue = Double.parseDouble(current.getText());
			double result = memoryValue + currentValue;
			memory.getItems().set(0, formatNumber(result));
		}
		updateMemoryTrashButton();
	}
	
	private void actionMemoryStore() {
		memory.getItems().add(0, current.getText());
		updateMemoryButton();
		updateMemoryTrashButton();
	}
	
	private void actionMemoryRecover() {
		ObservableList<String> items = memory.getItems();

		if(!items.isEmpty()) {
			current.setText(items.get(0));
		}
		updateMemoryButton();
		updateMemoryTrashButton();
	}
	
	private void actionMemoryClear() {
		memory.getItems().clear();
		updateMemoryButton();
		updateMemoryTrashButton();
	}
	
	private void actionSquareRoot() {
		
		double value = Double.parseDouble(current.getText());
		ifCalculated();
		if(value <0) {
			current.setText("Invalid input");
			unaryCurrent = "\u221A(" + formatNumber(value) + ")";
			progress.setText(unaryCurrent);
			operatorDisableOFF();
			isOperatorDisableOFF = true;
			return;
		}
		double result = Math.sqrt(value);
		current.setText(formatNumber(result));

		if (unaryCurrent.isEmpty()) {
			unaryCurrent = formatNumber(value) + "";
		}
		unaryCurrent = "\u221A(" + unaryCurrent + ")";
		
		showunaryProgress();
		isUnary = true;
	}
	
	private void showunaryProgress() {
		if(operator == ' ') {
			progress.setText(unaryCurrent);
		}else {
			if(unaryPrevious.isEmpty()) {
				progress.setText(formatNumber(num1) + " " + operator +" " + unaryCurrent);
			}else {
				progress.setText(unaryPrevious + " " + operator + " " + unaryCurrent); 
			}
			
		}
	}
	
	private void actionSuperScript() {
		
		double value = Double.parseDouble(current.getText());
		double result = Math.pow(value, 2);
		ifCalculated();
		current.setText(formatNumber(result));
		
		if (unaryCurrent.isEmpty()) {
			unaryCurrent = formatNumber(value) + "";
		}
		unaryCurrent = "sqr(" + unaryCurrent + ")";
		showunaryProgress();
		isUnary = true;
	}
	
	private void actionInverse() {
		
		double value = Double.parseDouble(current.getText());
		ifCalculated();
		if(value == 0) {
			current.setText("Cannot divided by zero");
			operatorDisableOFF();
			isOperatorDisableOFF = true;
			return;
		}
		
		double result = 1.0/value;
		current.setText(formatNumber(result));
		
		if (unaryCurrent.isEmpty()) {
			unaryCurrent = formatNumber(value) + "";
		}
		unaryCurrent = "1/(" + unaryCurrent + ")";
		showunaryProgress();
		isUnary = true;
	}
	
	private void actionPercentage() {
		if(operator == ' ') {
			current.setText("0");
			progress.setText("0");
			isOperatorEmpty = true;
			return;
		}
		double value = Double.parseDouble(current.getText());
		double result = 0;
		if(operator == '+' || operator == '-') {
			
			result = num1 * value / 100;
		}else if(operator == 'x' || operator == '÷') {
			
			result = value / 100;
		}
		current.setText(formatNumber(result));
		progress.setText(progress.getText() + " " + formatNumber(result));
	}
	
	private void actionOperator(char op) {
		double value = Double.parseDouble(current.getText());
		if (addOperator) {
			operator = op;
			num1 = value;
			if (isUnary && !unaryPrevious.isEmpty()) {
				progress.setText(unaryPrevious + " " + op);
			}else {
				progress.setText(formatNumber(num1) + " " + op);
			}
			return;
		}
		if (operator == ' ') {
			num1 = value;
			
		}else {
			num2 = value;
			if (!operate()) {
				progress.setText(progress.getText() + " " + formatNumber(num2) + " " + op);
				num1 =0;
				operator = ' ';
				operatorDisableOFF();
				isOperatorDisableOFF = true;
				waittingForSecondNumber = true;
				isUnary = false;
				return;
			}
			
			if(isUnary) {
				history.getItems().add(0, progress.getText() + " =\n" + formatNumber(answer));
				unaryPrevious = "";
				unaryCurrent = "";
				isUnary = false;
			}else {
				history.getItems().add(0, formatNumber(num1) + " " + operator + 
						" " + formatNumber(num2) + " =\n" + formatNumber(answer));
			}
			updateHistoryTrashButton();
			num1 = answer;
			current.setText(formatNumber(num1));
			num2 =0;
			answer =0;
		}
		operator = op;
		
		if (isUnary) {
			progress.setText(unaryCurrent + " " + op);
			unaryPrevious = unaryCurrent;
			unaryCurrent ="";
		}else {
			progress.setText(formatNumber(num1) + " " + op);
		}
		waittingForSecondNumber = true;
		addOperator = true;
	}
	
	private boolean operate() {
		switch (operator) {
		case '+':
			answer = num1 + num2;
			break;
		case '-':
			answer = num1 - num2;
			break;
		case 'x':
			answer = num1 * num2;
			break;
		case '÷':
			if(num2 ==0) {
				current.setText("Cannot divided by zero");
				return false;
			}else {
				answer = num1 / num2;
			}
			break;
		}
		return true;
	}
	
	private void actionBackspace() {
		String text = current.getText();
		
		if(text.length() <=1) {
			current.setText("0");
			return;
		}
		current.setText(text.substring(0, text.length()-1));
	}
	
	private void actionCE() {
		if (progress.getText().contains("=")) {
			progress.setText("");
		}
		current.setText("0");
	}
	
	private void actionToggle() {
		double value =  Double.parseDouble(current.getText());
		value = - value;
		if(current.getText().contains(".")) {
			current.setText(Double.toString(value));
		}else {
			current.setText(formatNumber(value));
		}
		
	}
	
	private void actionEqual() {
		if (operator == ' ' && current.getText().equals("0")) {
			progress.setText("0" + equal.getText());
			history.getItems().add(0, progress.getText() + "\n" + "0");
		}else {
			double value = Double.parseDouble(current.getText());
			
			if (operator == ' ') {
				if(isUnary) {
					progress.setText(unaryCurrent + " " + equal.getText());
					history.getItems().add(0, progress.getText() + "\n" + current.getText());
					
				}else {
					progress.setText(formatNumber(value) + equal.getText());
					history.getItems().add(0, progress.getText() + "\n" + formatNumber(value));
				}
				
			}else {
				
				if (calculated) {
					num1 = Double.parseDouble(current.getText());
					if (!operate()) {
						notOperate();
						return;
					}
				}else {
					num2 = value;
					if (!operate()) {
						notOperate();
						return;
					}
				}

				if (isUnary) {
					
					if(unaryPrevious.isEmpty()) {
						progress.setText(formatNumber(num1) + " " + operator + " " + unaryCurrent + " =");
						
					}else if(unaryCurrent.isEmpty()) {
						progress.setText(unaryPrevious + " " + operator + formatNumber(num2) + " =");
						
					}else if(!unaryCurrent.isEmpty()) {
						progress.setText(unaryPrevious + " " + operator + unaryCurrent + " =");
						
					}
					
					isUnary = false;
					
				}else {
					progress.setText(formatNumber(num1) + " " + operator + " " 
							+ formatNumber(num2) + " =");
					
				}
				

				current.setText(formatNumber(answer));
				history.getItems().add(0, progress.getText() + "\n" + formatNumber(answer) );
			}
		}		
		updateHistoryTrashButton();
		calculated = true;
	}
	private void ifCalculated() {
		if (calculated) {
			num1 =0;
			num2 =0;
			answer =0;
			operator = ' ';
			unaryCurrent ="";
			unaryPrevious = "";
			current.setText("0");
			progress.setText("");
			calculated = false;
			addOperator = false;
		}
	}
	private void notOperate() {
		operatorDisableOFF();
		isOperatorDisableOFF = true;
		progress.setText(progress.getText());
		isUnary = false;
		calculated = true;
		addOperator = false;
	}
	
	private void actionC() {
		current.setText("0");
		num1 =0;
		num2 =0;
		operator = ' ';
		answer =0;
		unaryCurrent = "";
		unaryPrevious = "";
		progress.setText("");
		isUnary = false;
		calculated = false;
		isOperatorEmpty = false;
		isOperatorDisableOFF = false;
		waittingForSecondNumber = false;
		addOperator = false;
	}
	
	private Scene addButtonsIntoRoot() {
		pane.getTabs().addAll(historyTab, memoryTab);
		pane.setStyle("-fx-font-size:20;");
		
		progress.setAlignment(Pos.TOP_RIGHT);
		current.setAlignment(Pos.TOP_RIGHT);
		
		root.add(pane, 6, 0, 3, 9);
		createNumberButtons();
		root.add(progress, 0, 0, 4, 1);
		root.add(current, 0, 1, 4, 1);
		
		historyTab.setClosable(false);
		memoryTab.setClosable(false);
		
		box.setStyle("-fx-font-size: 17;");
		
		historyPane.setCenter(history);
		historyPane.setBottom(historyTrash);
		BorderPane.setAlignment(historyTrash, Pos.BOTTOM_RIGHT);
		updateHistoryTrashButton();
		
		memoryPane.setCenter(memory);
		memoryPane.setBottom(memoryTrash);
		BorderPane.setAlignment(memoryTrash, Pos.BOTTOM_RIGHT);
		
		box.getChildren().addAll(memoryClear, memoryRecover, memoryPlus, memoryMinus, memoryStore);
		root.add(box, 0, 2, 4, 1);
		updateMemoryButton();
		
		root.addColumn(0, percentage, inverse, numbers[7], numbers[4], numbers[1], toggle);
		root.addColumn(1, ce, superscript, numbers[8], numbers[5], numbers[2],numbers[0]);
		root.addColumn(2, c, squareRoot, numbers[9], numbers[6], numbers[3], decimal);
		root.addColumn(3, backspace, divide, multiple, minus, plus, equal);

		progress.setEditable(false);
		current.setEditable(false);
		history.setEditable(false);
		
		root.setStyle("-fx-font-size: 20;");
		btnsPrefSize();
		
		return new Scene(root);
	}
	
	private void createNumberButtons() {
		
		for (int i = 0; i < numbers.length; i++) {
			int value = i;
			numbers[i] = new Button(String.valueOf(value));
			numbers[i].setPrefSize(65, 65);
			numbers[i].setOnAction(e->actionNumbers(value));
		}
	}
	
	private void btnsPrefSize() {
		percentage.setPrefSize(65, 65);
		ce.setPrefSize(65, 65);
		c.setPrefSize(65, 65);
		backspace.setPrefSize(65, 65);
		inverse.setPrefSize(65, 65);
		superscript.setPrefSize(65, 65);
		squareRoot.setPrefSize(65, 65);
		divide.setPrefSize(65, 65);
		multiple.setPrefSize(65, 65);
		minus.setPrefSize(65, 65);
		plus.setPrefSize(65, 65);
		equal.setPrefSize(65, 65);
		decimal.setPrefSize(65, 65);
		toggle.setPrefSize(65, 65);
	}
	
	private void actionNumbers(double value) {
		
		if (waittingForSecondNumber) {
			current.setText("0");
			addOperator = false;
			waittingForSecondNumber  = false;
		}
		
		ifCalculated();
		
		if (isUnary && operator == ' ') {
			history.getItems().add(0, unaryCurrent + " =\n" + current.getText());
			current.setText("0");
			unaryCurrent ="";
			isUnary = false;
		}
		
		if (isOperatorDisableOFF) {
			operatorDisableOn();
			current.setText("0");
			progress.setText("");
			isOperatorDisableOFF = false;
		}
		
		if(isOperatorEmpty) {
			history.getItems().add("0\n0");
			progress.setText("");
			isOperatorEmpty = false;
		}
		
		if(current.getText().equals("0")) {
			current.setText(formatNumber(value));
		}else {
			current.setText(current.getText() + formatNumber(value));
		}
	}
	
	private void operatorDisableOFF() {
		plus.setDisable(true);
		minus.setDisable(true);
		multiple.setDisable(true);
		divide.setDisable(true);
		percentage.setDisable(true);
		superscript.setDisable(true);
		squareRoot.setDisable(true);
		inverse.setDisable(true);
		toggle.setDisable(true);
		decimal.setDisable(true);
	}
	
	private void operatorDisableOn() {
		plus.setDisable(false);
		minus.setDisable(false);
		multiple.setDisable(false);
		divide.setDisable(false);
		squareRoot.setDisable(false);
		inverse.setDisable(false);
		toggle.setDisable(false);
		superscript.setDisable(false);
		percentage.setDisable(false);
		decimal.setDisable(false);
	}
	
	private String formatNumber(double num){
        if(num == (long) num){
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }
	
	private void updateMemoryButton() {
		boolean isEmpty = memory.getItems().isEmpty();
		
		memoryClear.setDisable(isEmpty);
		memoryRecover.setDisable(isEmpty);
	}
	
	private void updateHistoryTrashButton() {
		boolean empty = history.getItems().isEmpty();
		
		historyTrash.setVisible(!empty);
		historyTrash.setManaged(!empty);
	}

	private void updateMemoryTrashButton() {
		boolean empty = memory.getItems().isEmpty();
		
		memoryTrash.setVisible(!empty);
		memoryTrash.setManaged(!empty);
	}
	
}
