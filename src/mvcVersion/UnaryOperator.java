package mvcVersion;

public enum UnaryOperator {
	SQRT {
		public String format(String text) {
			return "\u221A(" + text + ")";
		}
		
		public double calculate(double value) {
			return Math.sqrt(value);
		}
	},
	INVERSE{
		public String format(String text) {
			return "1/(" + text + ")";
		}
		
		public double calculate(double value) {
			return 1/value;
		}
	},
	SQR{
		public String format(String text) {
			return "sqr(" + text + ")";
		}
		
		public double calculate(double value) {
			return Math.pow(value, 2);
		}
	},
	NEGATE{
		public String format(String text) {
			return "negate(" + text + ")";
		}

		public double calculate(double value) {
			return -value;
		}
	};
	public abstract String format(String text);
	
	public abstract double calculate(double value);
}
