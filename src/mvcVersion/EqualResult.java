package mvcVersion;

import java.util.List;
import java.util.Map;

public record EqualResult (
		boolean zero,
		Map<DivisionNumber, List<UnaryOperator>> unary,
		char operator,
		boolean error,
		double operand1,
		double operand2,
		double num1,
		double num2,
		double reslt,
		boolean operate
		){}
