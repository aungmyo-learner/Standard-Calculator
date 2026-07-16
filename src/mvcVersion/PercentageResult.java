package mvcVersion;

import java.util.List;
import java.util.Map;

public record PercentageResult(
		InputState inputState,
		OperationState operationState,
		double num1,
		double num2,
		double result,
		Map<UnaryPosition, List<UnaryOperator>> unary) {}
