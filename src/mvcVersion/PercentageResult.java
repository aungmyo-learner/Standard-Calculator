package mvcVersion;

import java.util.List;
import java.util.Map;

public record PercentageResult(
		InputState inputState,
		OperationState operationState,
		double num1,
		double num2,
		double result,
		char operator,
		double operand,
		UnaryPosition unaryPosition,
		Map<UnaryPosition, List<UnaryOperator>> unary) {}
