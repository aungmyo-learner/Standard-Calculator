package mvcVersion;

import java.util.List;
import java.util.Map;

public record CalculationResult(
	double operand1,
	double operand2,
	double num1,
	double num2,
	char firstOperator,
	char secondOperator,
	Map<UnaryPosition, List<UnaryOperator>> unary,
	double result,
	UnaryPosition unaryPosition,
	InputState inputState,
	OperationState operationState,
	HistoryState historyState,
	UnaryOperator unaryErrorOperator
	){}
	