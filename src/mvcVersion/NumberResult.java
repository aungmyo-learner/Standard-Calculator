package mvcVersion;

import java.util.List;
import java.util.Map;

public record NumberResult (
		String current,
		double value,
		Map<UnaryPositon, List<UnaryOperator>> unary,
		double operand,
		InputState inputState,
		OperationState operationState,
		UnaryPositon unaryPosition,
		HistoryState historyState,
		double num1,
		char operator
) {}
