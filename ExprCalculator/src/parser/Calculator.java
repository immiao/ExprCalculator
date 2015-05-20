/**
 * @Copyright(C) 2008 Software Engineering Laboratory (SELAB), Department of Computer 
 * Science, SUN YAT-SEN UNIVERSITY. All rights reserved.
**/

package parser;

import exceptions.*;

/**
 * Main program of the expression based calculator ExprEval
 * 
 * @author Miao Kaixiang
 * @version 1.00 (Last update: 2015/5/17)
**/
public class Calculator
{
	/**
	 * The main program of the parser. You should substitute the body of this method 
	 * with your experiment result. 
	 * 
	 * @param expression  user input to the calculator from GUI. 
	 * @return  if the expression is well-formed, return the evaluation result of it. 
	 * @throws ExpressionException  if the expression has error, a corresponding 
	 *                              exception will be raised. 
	**/
	public double calculate(String expression) throws ExpressionException
	{
		// You should substitute this method body ...
		System.out.println("------------------------------------------------------------------");
		if (expression.isEmpty())
			throw new EmptyExpressionException();
		Parser parser = new Parser();
		double result = parser.parse(expression); //((int) (Math.random() * 1000000000)) / 100.0;
		return result;
	}
}
