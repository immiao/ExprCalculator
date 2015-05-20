package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exceptions.DividedByZeroException;
import exceptions.FunctionCallException;
import exceptions.LexicalException;
import exceptions.MissingLeftParenthesisException;
import exceptions.MissingOperandException;
import exceptions.MissingOperatorException;
import exceptions.MissingRightParenthesisException;
import exceptions.SemanticException;
import exceptions.SyntacticException;
import exceptions.TrinaryOperationException;
import exceptions.TypeMismatchedException;

/**
 * Parser类用于做语法分析和语义规则计算，得到一个表达式的值
 */
public class Parser {
	public static Map<String, Integer> map;
    public final static int[][] table= new int[][]
    {
    	{-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0  },
    	{-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,-7	,-7	,-7	,-7	,-7	,-1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0	,0	,0	,0  },
    	{-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,-7	,-7	,-7	,-7	,-7	,-1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0	,0	,0	,0  },
    	{-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,2	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3 },
    	{-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,2	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3	,-3 },
    	{-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,2	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5 },
    	{-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,2	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5	,-5 },
    	{1	,-7	,-7	,1	,1	,1	,1	,-1	,0	,0	,0	,0	,0	,0	,2	,0	,-7	,0	,0	,0	,0	,0	,0	,0	,-7	,-7	,-7	,0  },
    	{1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,0	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,-4 },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,0	,0	,0	,1	,1	,1	,1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,0	,0	,0	,1	,1	,1	,1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,0	,0	,0	,0	,0	,1	,1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,0	,0	,0	,0	,0	,1	,1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,0	,0	,0	,0	,0	,0	,1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,2	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,-4 },
    	{-1	,-1	,-1	,-1	,-1	,-1	,-1	,-1	,0	,0	,0	,0	,0	,0	,-1	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,0	,-1	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-6	,1	,1	,1	,1	,1	,1	,-6	,-6	,2	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6 },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-6	,1	,1	,1	,1	,1	,1	,0	,1	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,-6	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,-7	,-7	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,0	,0	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,0  },
    	{1	,1	,1	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,1	,0	,0	,-7	,1	,1	,1	,1	,1	,1	,0	,0	,1	,0  },
    	{1	,1	,1	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,-7	,1	,0	,0  ,-7	,1	,1	,1	,1	,1	,1	,0	,0	,1	,0  },
    	{1	,1	,1	,1	,1	,1	,1	,1	,-1	,1	,1	,1	,1	,1	,1	,1	,0	,-1	,1	,1	,1	,1	,1	,1	,1	,1	,1	,0  },
    	{1	,1	,1	,1	,1	,1	,1	,1	,-2	,1	,1	,1	,1	,1	,1	,-3	,1	,-7	,1	,1	,1	,1	,1	,1	,1	,1	,1	,0  }
    };
    
    /**
	* 构造函数，对Token类型映射到整数，用于查询OPP表
	*/
    Parser() {
    	map = new HashMap<String, Integer>();
    	map.put("decimal", new Integer(0));
    	map.put("true", new Integer(1));
    	map.put("false", new Integer(2));
    	map.put("sin", new Integer(3));
    	map.put("cos", new Integer(4));
    	map.put("max", new Integer(5));
    	map.put("min", new Integer(6));
    	map.put("neg", new Integer(7));
    	map.put(",", new Integer(8));
    	map.put("+", new Integer(9));
    	map.put("-", new Integer(10));
    	map.put("*", new Integer(11));
    	map.put("/", new Integer(12));
    	map.put("^", new Integer(13));
    	map.put("(", new Integer(14));
    	map.put(")", new Integer(15));
    	map.put("?", new Integer(16));
    	map.put(":", new Integer(17));
    	map.put(">", new Integer(18));
    	map.put("=", new Integer(19));
    	map.put("<", new Integer(20));
    	map.put("<=", new Integer(21));
    	map.put(">=", new Integer(22));
    	map.put("<>", new Integer(23));
    	map.put("&", new Integer(24));
    	map.put("|", new Integer(25));
    	map.put("!", new Integer(26));
    	map.put("$", new Integer(27));
    }
    public ArrayList<Token> stack;
    public ArrayList<Expr> sentence;
    public Scanner scanner;
    public Token lookahead;
    public Token top;
    
    /**
	* 语法分析函数
	* @param expression 输入串
	* @return 分析得到的表达式的值
	* @throws LexicalException 词法异常，由Scanner抛出
	* @throws SyntacticException 语法分析异常
	* @throws SemanticException 语义规则异常
	*/
	public double parse(String expression) throws LexicalException, SyntacticException, SemanticException{
		
		scanner = new Scanner(expression);
		stack = new ArrayList<Token>();
		sentence = new ArrayList<Expr>();
		double result = 0;

		Token terminal = new Token("$");
		stack.add(terminal);
		sentence.add(terminal);
		top = stack.get(stack.size() - 1);
		lookahead = scanner.GetNextToken();
		while (true) {
			//System.out.println(top.content + "  " +lookahead.content);
			top = stack.get(stack.size() - 1);
			if (top.content.equals("$") && lookahead.content.equals("$")) {
				result = accept();
				
				return result;
			}
			//System.out.println(top.content);
			if (table[top.typeId][lookahead.typeId] == 1 || table[top.typeId][lookahead.typeId] == 2) {
				//System.out.println("shift "+lookahead.content);
				shift(lookahead);
				stack.add(lookahead);
				lookahead = scanner.GetNextToken();
				//System.out.println("next "+lookahead.content);
			}
			else if (table[top.typeId][lookahead.typeId] == 0) {
				int stackTopId;
				reduce();
				do {
					top = stack.get(stack.size() - 1);
					//System.out.println("pop "+top.content);
					stack.remove(stack.size() - 1);
					stackTopId = stack.get(stack.size() - 1).typeId;
				} while (table[stackTopId][top.typeId] == 0 || table[stackTopId][top.typeId] == 2);
			}
			else
				error(table[top.typeId][lookahead.typeId]);
//			System.out.print("STACK:   ");
//			for (int i=0;i<stack.size();i++)
//				System.out.print(stack.get(i).content);
//			System.out.println();
			
			System.out.println();
			for (int i=0;i<sentence.size();i++)
				System.out.print(sentence.get(i).content+" ");
			
		}
		
	}
	
	/**
	* 接受函数
	* @return 返回计算得到的表达式的值
	* @throws SyntacticException 语法分析异常
	*/
	double accept() throws SyntacticException{
		//System.out.println(sentence.size());
		if (sentence.size() == 2 && sentence.get(1).content.equals("ArithExpr")) {
			return sentence.get(1).doubleValue;
		}
		else throw new SyntacticException();
	}
	
	/**
	* 查询OPP表时抛出异常的函数
	* @param errorType 错误类型
	* @throws SyntacticException 语法分析异常
	* @throws SemanticException 语义规则异常
	*/
	void error(int errorType) throws SyntacticException, SemanticException{
		if (errorType == -1) throw new MissingOperatorException();
		else if (errorType == -2) throw new MissingOperandException();
		else if (errorType == -3) throw new MissingLeftParenthesisException();
		else if (errorType == -4) throw new MissingRightParenthesisException();
		else if (errorType == -5) throw new FunctionCallException();
		else if (errorType == -6) throw new TrinaryOperationException();
		else if (errorType == -7) throw new TypeMismatchedException();
	}
	
	/**
	* 移入符号栈的函数
	* @param token 即将移入栈的Token
	*/
	void shift(Token token) {
		sentence.add(token);
	}
	
	/**
	* 规约函数，对sentence进行规约
	* @throws SyntacticException 语法分析异常
	* @throws SemanticException 语义规则异常
	*/
	void reduce() throws SyntacticException, SemanticException {
		int index = sentence.size() - 1;
		int state = 0;
		
		while (true) {
			if (index == 0) throw new SyntacticException(); //can not reduce
			switch (state) {
			case 0:
				if (sentence.get(index).content.equals("ArithExpr")) {
					if (sentence.get(index - 1).content.equals(",")) {
						double value = Double.parseDouble(sentence.get(index).content);
						Expr expr = new Expr("ArithExprList", value);
						sentence.set(index, expr);
						return;
					}
					else state = 1;
				}
				else if (sentence.get(index).content.equals("ArithExprList")) state = 8;
				else if (sentence.get(index).content.equals("true")) {
					Expr expr = new Expr("BoolExpr", true);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("false")) {
					Expr expr = new Expr("BoolExpr", false);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("decimal")) {
					double value = Double.parseDouble(sentence.get(index).strValue);
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value);
					else
						expr = new Expr("ArithExpr", value);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) state = 17;
				else if (sentence.get(index).content.equals("!")) state = 21;
				else if (sentence.get(index).content.equals(")")) state = 22;
				else throw new MissingOperandException();
				break;
			case 1:
				if (sentence.get(index).content.equals("+")) state = 2;
				else if (sentence.get(index).content.equals("-")) state = 3;
				else if (sentence.get(index).content.equals("*")) state = 4;
				else if (sentence.get(index).content.equals("/")) state = 5;
				else if (sentence.get(index).content.equals("^")) state = 6;
				else if (sentence.get(index).content.equals("<")) state = 7;
				else if (sentence.get(index).content.equals(">=")) state = 13;
				else if (sentence.get(index).content.equals("<=")) state = 14;
				else if (sentence.get(index).content.equals("<>")) state = 15;
				else if (sentence.get(index).content.equals(">")) state = 16;
				else if (sentence.get(index).content.equals(":")) state = 9;
				else if (sentence.get(index).content.equals("neg")) {
					//if (index + 1 >= sentence.size() || !sentence.get(index).content.equals("ArithExpr"))
						//throw new MissingOperandException();
					//else {
						double value = sentence.get(index + 1).doubleValue;
						Expr expr;
						if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
							expr = new Expr("ArithExprList", -value);
						else
							expr = new Expr("ArithExpr", -value);
						sentence.remove(index + 1);
						sentence.set(index, expr);
						return;
					//}
				}
				else throw new MissingOperatorException();
				break;
			case 2:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value1 + value2);
					else
						expr = new Expr("ArithExpr", value1 + value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 3:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value1 - value2);
					else
						expr = new Expr("ArithExpr", value1 - value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 4:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value1 * value2);
					else
						expr = new Expr("ArithExpr", value1 * value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 5:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					if (value2 == 0) throw new DividedByZeroException();
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value1 / value2);
					else
						expr = new Expr("ArithExpr", value1 / value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 6:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", Math.pow(value1, value2));
					else
						expr = new Expr("ArithExpr", Math.pow(value1, value2));
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 7:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr = new Expr("BoolExpr", value1 < value2 ? true : false);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("BoolExpr")) throw new TypeMismatchedException();
				else throw new MissingOperandException();
			case 8:
				if (sentence.get(index).content.equals(",")) state = 12;
				else throw new FunctionCallException();
				break;
			case 9:
				if (sentence.get(index).content.equals("ArithExpr")) state = 10;
				else throw new TrinaryOperationException();
				break;
			case 10:
				if (sentence.get(index).content.equals("?")) state = 11;
				else throw new TrinaryOperationException();
				break;
			case 11:
				if (sentence.get(index).content.equals("BoolExpr")) {
					double value = sentence.get(index).boolValue ? sentence.get(index + 2).doubleValue 
							: sentence.get(index + 4).doubleValue;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
						expr = new Expr("ArithExprList", value);
					else
						expr = new Expr("ArithExpr", value);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new TrinaryOperationException();
			case 12:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double maxValue1 = sentence.get(index).maxDouble;
					double maxValue2 = sentence.get(index + 2).maxDouble;
					double minValue1 = sentence.get(index).minDouble;
					double minValue2 = sentence.get(index + 2).minDouble;
					Expr expr = new Expr("ArithExprList");
					expr.maxDouble = maxValue1 > maxValue2 ? maxValue1 : maxValue2;
					expr.minDouble = minValue1 < minValue2 ? minValue1 : minValue2;
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new FunctionCallException();
			case 13:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr = new Expr("BoolExpr", value1 >= value2 ? true : false);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 14:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr = new Expr("BoolExpr", value1 <= value2 ? true : false);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 15:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr = new Expr("BoolExpr", value1 != value2 ? true : false);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 16:
				if (sentence.get(index).content.equals("ArithExpr")) {
					double value1 = sentence.get(index).doubleValue;
					double value2 = sentence.get(index + 2).doubleValue;
					Expr expr = new Expr("BoolExpr", value1 > value2 ? true : false);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 17:
				if (sentence.get(index).content.equals("&")) state = 18;
				else if (sentence.get(index).content.equals("|")) state = 19;
				else throw new MissingOperatorException();
				break;
			case 18:
				if (sentence.get(index).content.equals("BoolExpr")) {
					boolean value1 = sentence.get(index).boolValue;
					boolean value2 = sentence.get(index + 2).boolValue;
					Expr expr = new Expr("BoolExpr", value1 & value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 19:
				if (sentence.get(index).content.equals("BoolExpr")) {
					boolean value1 = sentence.get(index).boolValue;
					boolean value2 = sentence.get(index + 2).boolValue;
					Expr expr = new Expr("BoolExpr", value1 & value2);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 21:
				if (sentence.get(index).content.equals("BoolExpr")) {
					boolean value = sentence.get(index + 1).boolValue;
					Expr expr = new Expr("BoolExpr", !value);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingOperandException();
			case 22:
				if (sentence.get(index).content.equals("BoolExpr")) state = 23;
				else if (sentence.get(index).content.equals("ArithExpr")) state = 24;
				else if (sentence.get(index).content.equals("ArithExprList")) state = 25;
				else throw new MissingOperandException();
				break;
			case 23:
				if (sentence.get(index).content.equals("(")) {
					boolean value = sentence.get(index + 1).boolValue;
					Expr expr = new Expr("BoolExpr", value);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new MissingLeftParenthesisException();
			case 24:
				if (sentence.get(index).content.equals("(")) {
					if (sentence.get(index - 1).content.equals("sin")) {
						double value = sentence.get(index + 1).doubleValue;
						Expr expr;
						if (lookahead.content.equals(")") && sentence.get(index - 2).content.equals(","))
							expr = new Expr("ArithExprList", Math.sin(value));
						else
							expr = new Expr("ArithExpr", Math.sin(value));
						sentence.remove(index);
						sentence.remove(index);
						sentence.remove(index);
						sentence.set(index - 1, expr);
						return;
					}
					else if (sentence.get(index - 1).content.equals("cos")) {
						double value = sentence.get(index + 1).doubleValue;
						Expr expr;
						if (lookahead.content.equals(")") && sentence.get(index - 2).content.equals(","))
							expr = new Expr("ArithExprList", Math.cos(value));
						else
							expr = new Expr("ArithExpr", Math.cos(value));
						sentence.remove(index);
						sentence.remove(index);
						sentence.remove(index);
						sentence.set(index - 1, expr);
						return;
					}
					else if (sentence.get(index - 1).content.equals("max") || sentence.get(index - 1).content.equals("min"))
						throw new MissingOperandException();
					else {
						double value = sentence.get(index + 1).doubleValue;
						Expr expr;
						if (lookahead.content.equals(")") && sentence.get(index - 1).content.equals(","))
							expr = new Expr("ArithExprList", value);
						else
							expr = new Expr("ArithExpr", value);
						sentence.remove(index + 1);
						sentence.remove(index + 1);
						sentence.set(index, expr);
						return;
					}
				}
				else throw new MissingLeftParenthesisException();
			case 25:
				if (sentence.get(index).content.equals("(")) state = 26;
				else throw new MissingLeftParenthesisException();
				break;
			case 26:
				if (sentence.get(index).content.equals("max")) {
					double value = sentence.get(index + 2).maxDouble;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 2).content.equals(","))
						expr = new Expr("ArithExprList", value);
					else
						expr = new Expr("ArithExpr", value);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else if (sentence.get(index).content.equals("min")) {
					double value = sentence.get(index + 2).minDouble;
					Expr expr;
					if (lookahead.content.equals(")") && sentence.get(index - 2).content.equals(","))
						expr = new Expr("ArithExprList", value);
					else
						expr = new Expr("ArithExpr", value);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.remove(index + 1);
					sentence.set(index, expr);
					return;
				}
				else throw new FunctionCallException();
 			}
			index--;
		}
	}
}
