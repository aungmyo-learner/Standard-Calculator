package mvcVersion;

public enum UnaryOperator {
	NOUNARY{

		@Override
		public String format(String text, DivisionNumber num) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public UnaryResult calculate(double value) {
			// TODO Auto-generated method stub
			return null;
		}
		
	},
	SQRT {
		public String format(String text, DivisionNumber num) {
			return "\u221A(" + text + ")";
		}
		
		public UnaryResult calculate(double value) {
			if (value < 0) {
				return new UnaryResult(value, true);
			}
			double result = Math.sqrt(value);
			return new UnaryResult(result, false);
		}
	},
	INVERSE{
		public String format(String text, DivisionNumber num) {
			return "1/(" + text + ")";
		}
		
		public UnaryResult calculate(double value) {
			if (value == 0) {
				return new UnaryResult(value, true);
			}
			double result = 1.0 / value;
			return new UnaryResult(result, false);
		}
	},
	SQR{
		public String format(String text, DivisionNumber num) {
			return "sqr(" + text + ")";
		}
		
		public UnaryResult calculate(double value) {
			return new UnaryResult(Math.pow(value, 2), false);
		}
	},
	NEGATE{
		public String format(String text, DivisionNumber num) {
			if (num == DivisionNumber.SECOND) {
				return "negate(" + text + ")";
			}
			return text;
		}

		public UnaryResult calculate(double value) {
			return new UnaryResult(-value, false);
		}
	};
	public abstract String format(String text, DivisionNumber num);
	
	public abstract UnaryResult calculate(double value);
}
