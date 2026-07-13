package mvcVersion;

import java.util.List;
import java.util.Map;

public record NumberResult (
		String current,
		double value,
		Map<DivisionNumber, List<UnaryOperator>> unary,
		double operand,
		InputState inputState,
		OperationState operationState,
		UnaryState unaryState,
		DivisionNumber num,
		double num1,
		char operator
) {}
