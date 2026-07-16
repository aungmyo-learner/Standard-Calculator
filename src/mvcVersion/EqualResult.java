package mvcVersion;

import java.util.List;
import java.util.Map;

public record EqualResult (
		String current,
		Map<UnaryPosition, List<UnaryOperator>> unary,
		char operator,
		double operand1,
		double operand2,
		OperationState operationState,
		HistoryState historyState,
		UnaryPosition unaryPositon,
		double num1,
		double num2,
		double result
		){}
