package mvcVersion;

import java.util.List;
import java.util.Map;

public record EqualResult (
		Map<UnaryPositon, List<UnaryOperator>> unary,
		char operator,
		double operand1,
		double operand2,
		OperationState operationState,
		HistoryState historyState,
		UnaryPositon unaryPositon,
		double num1,
		double num2,
		double reslt
		){}
