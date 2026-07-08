package mvcVersion;

import javafx.application.Application;
import javafx.stage.Stage;

public class CalculatorMain extends Application {
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage st) throws Exception {
		CalculatorModel model = new CalculatorModel();
		CalculatorService service = new CalculatorService(model);
		CalculatorFormatter formatter = new CalculatorFormatter();
		CalculatorUI ui = new CalculatorUI(service, formatter);
		ui.start(st);
	}
}
