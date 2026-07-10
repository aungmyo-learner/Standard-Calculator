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
	Map<DivisionNumber, List<UnaryOperator>> unary,
	double result,
	boolean addOperator,
	boolean unaryError,
	boolean operationError,
	boolean operate,
	UnaryOperator unaryErrorOperator
	){}
	