package mvcVersion;

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

public class CalculatorUI {

	private final CalculatorService service;
	
	public CalculatorUI(CalculatorService service) {
		this.service = service;
	}

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
	
	public void start(Stage st) throws Exception {
		
		Scene sc = addButtonsIntoRoot();
		clickProgress(sc);
		st.setScene(sc);
		st.setTitle("Standard Calculator");
		st.show();
		
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
	
	private void clickProgress(Scene sc) {
		actionNoNumber(sc);
	}
	
	private void actionNoNumber(Scene sc) {
//		
//		decimal.setOnAction(e->actionDecimal());
//		
//		//Percentage Case
//		percentage.setOnAction(e-> actionPercentage());
//		
		//Clear Case
		c.setOnAction(e-> actionC());
		ce.setOnAction(e->actionCE());
		backspace.setOnAction(e->current.setText(service.backspace(current.getText())));
		
		//Memory case
		memoryClear.setOnAction(e->actionMemoryClear());
		memoryRecover.setOnAction(e->actionMemoryRecover());
		memoryTrash.setOnAction(e-> actionMemoryTrash());
		memoryStore.setOnAction(e-> actionMemoryStore());
		
		//History case
		historyTrash.setOnAction(e-> actionHistoryTrash());
		
		// Unary Case
		squareRoot.setOnAction(e->{
			String text = service.squareRoot(current.getText());
			if (text.equals("Invalid input")) {
				operatorDisableOFF();
				current.setText(text);
				progress.setText(service.getUnaryCurrent());
				return;
			}
			current.setText(text);
			progress.setText(service.showunaryProgress());
		});
		// Operator case
		plus.setOnAction(e->actionOperator('+'));
		divide.setOnAction(e-> actionOperator('÷'));
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
	
	private void createNumberButtons() {
		
		for (int i = 0; i < numbers.length; i++) {
			int value = i;
			numbers[i] = new Button(String.valueOf(value));
			numbers[i].setPrefSize(65, 65);
			numbers[i].setOnAction(e->{
				if (service.isOperatorDisableOff()) {
					operatorDisableOn();
					progress.setText("");
				}
				current.setText(service.inputNumber(current.getText(), value));
			});
		}
	}
	
	private void actionOperator(char op) {
		String text = service.inputOperation(op, current.getText());
		progress.setText(text);
		if (service.isOperatorDisableOff()) {
			operatorDisableOFF();
			current.setText("Cannot divided by zero");
			return;
		}
	}
	private void actionCE() {
		if (progress.getText().contains("=")) {
			progress.setText("");
		}
		current.setText("0");
	}

	private void actionC() {
		current.setText("0");
		progress.setText("");
	}
	
	//History Case
	private void updateHistoryTrashButton() {
		boolean empty = history.getItems().isEmpty();
		
		historyTrash.setVisible(!empty);
		historyTrash.setManaged(!empty);
	}

	private void actionHistoryTrash() {
		history.getItems().clear();
		updateHistoryTrashButton();
	}

	// Memory Case
	private void updateMemoryTrashButton() {
		boolean empty = memory.getItems().isEmpty();
		
		memoryTrash.setVisible(!empty);
		memoryTrash.setManaged(!empty);
	}

	private void updateMemoryButton() {
		boolean isEmpty = memory.getItems().isEmpty();
		
		memoryClear.setDisable(isEmpty);
		memoryRecover.setDisable(isEmpty);
	}
	
	private void actionMemoryTrash() {
		memory.getItems().clear();
		updateMemoryTrashButton();
		updateMemoryButton();
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
	
}
