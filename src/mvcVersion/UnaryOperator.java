package mvcVersion;

public enum UnaryOperator {
	NOUNARY{

		@Override
		public String format(String text, int count) {
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
		public String format(String text, int count) {
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
		public String format(String text, int count) {
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
		public String format(String text, int count) {
			return "sqr(" + text + ")";
		}
		
		public UnaryResult calculate(double value) {
			return new UnaryResult(Math.pow(value, 2), false);
		}
	},
	TOGGLE{
		public String format(String text, int count) {
			if (count >=0) {
				return "negate(" + text + ")";
			}
			return text;
		}

		public UnaryResult calculate(double value) {
			return new UnaryResult(-value, false);
		}
	};
	public abstract String format(String text, int count);
	
	public abstract UnaryResult calculate(double value);
}
