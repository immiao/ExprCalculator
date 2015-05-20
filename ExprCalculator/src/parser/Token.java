package parser;

/**
 * Token类代表了终结符号，该类型的对象通常由词法分析器Scanner计算得到
 * Token类继承自Expr类，typeId作为Token类型的整数映射，用于访问OPP表
 */
public class Token extends Expr{
	public int typeId;
	
	/**
	* 构造函数
	* @param content Token的类型
	*/
	public Token(String content) {
		super(content);
		this.typeId = Parser.map.get(content);
	}
	/**
	* 构造函数
	* @param content Token的类型
	* @param value Token的字符串类型存储的值
	*/
	public Token(String content, String value) {
		super(content, value);
		this.typeId = Parser.map.get(content);
	}
}
