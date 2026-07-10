package mvcVersion;

import java.util.List;
import java.util.Map;

public record NumberResult (
		String current,
		double value,
		boolean  unaryHistory,
		Map<DivisionNumber, List<UnaryOperator>> unary,
		double operand,
		boolean zero,
		boolean unaryErro,
		boolean operationError,
		DivisionNumber num
) {}
