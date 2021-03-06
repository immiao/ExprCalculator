package parser;

/**
 * 用于存储表达式的类，这里的表达式是指非终结符号和终结符号
 * 表达式的值可能是浮点型或者布尔型，同时以字符串类型存储了值
 * 当表达式类型为ArithExprList时，保存了其最大值和最小值，用以min和max函数的计算
 */
public class Expr {
	public String content;
	public String strValue;
	public boolean boolValue;
	public double doubleValue;
	public double maxDouble;
	public double minDouble;
	
	/**
	* 构造函数
	* @param expression 表达式的内容
	*/
	public Expr(String expression) {
		this.content = expression;
	}
	/**
	* 构造函数
	* @param expression 表达式的内容
	* @param value 表达式的值，以字符串类型存储
	*/
	public Expr(String expression, String value) {
		this.content = expression;
		this.strValue = value;
		//System.out.println(this.strValue);
	}
	/**
	* 构造函数
	* @param expression 表达式的内容
	* @param value 表达式的值，以布尔类型存储
	*/
	public Expr(String expression, boolean value) {
		this.content = expression;
		boolValue = value;
	}
	/**
	* 构造函数
	* @param expression 表达式的内容
	* @param value 表达式的值，以浮点数类型存储
	*/
	public Expr(String expression, double value) {
		this.content = expression;
		doubleValue = value;
		maxDouble = value;
		minDouble = value;
	}
}
