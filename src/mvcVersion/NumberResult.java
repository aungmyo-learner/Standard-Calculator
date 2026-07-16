package mvcVersion;

import java.util.List;
import java.util.Map;

public record NumberResult (
		String current,
		double value,
		Map<UnaryPosition, List<UnaryOperator>> unary,
		double operand,
		InputState inputState,
		OperationState operationState,
		UnaryPosition unaryPosition,
		HistoryState historyState,
		double num1,
		char operator
) {}
