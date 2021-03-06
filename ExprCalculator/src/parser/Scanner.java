package parser;

import exceptions.*;

/**
 * Scanner类用于词法分析，将输入串转化为一串Token，并在分析到错误时抛出词法分析错误异常
 */
public class Scanner {
	public String input;
	public int index;
	public int lastTokenType;
	
	/**
	* 构造函数
	* @param expression 输入字符串
	*/
	public Scanner(String expression) {
		input = expression + "$";
		index = 0;
		lastTokenType = -1;
	}
	
	/**
	* 获取输入串中的下一个Token
	* @return 返回分析得到的下一个Token
	* @throws LexicalException 词法异常
	*/
	public Token GetNextToken() throws LexicalException {
		if (input.charAt(index) == '$')
			return new Token("$");
		int state = 0;
		int startIndex = index;
		while (true) {
			char c = input.charAt(index);
			index++;
			if (state == 0 && c == ' ') continue;
			
			switch (state) {
			case 0:
				if (c >= '0' && c <= '9') state = 1;
				else if (c == 'c') state = 7;
				else if (c == 'm') state = 9;
				else if (c == 's') state = 12;
				else if (c == 't') state = 14;
				else if (c == 'f') state = 17;
				else if (c == '>') state = 21;
				else if (c == '<') state = 22;
				else if (c == '+') {
					String str = String.valueOf(c);
					Token token = new Token(str);
					lastTokenType = Parser.map.get(str);
					return token;
				}
				else if (c == '-') {
					Token token;
					if (lastTokenType == 0) {
						String str = String.valueOf(c);
						token = new Token(str);
						lastTokenType = Parser.map.get(str);
					}
					else {
						token = new Token("neg");
						lastTokenType = Parser.map.get("neg");
					}
					return token;
				}
				else if (c == '*' || c == '/' || c == '^' || c == '(' || c == ')' || c == ','
						|| c == '&' || c == '|' || c == '!' || c == '=' || c == '?' || c == ':') {
					String str = String.valueOf(c);
					Token token = new Token(str);
					lastTokenType = Parser.map.get(str);
					return token;
				}
				else throw new IllegalSymbolException();
				break;
			case 1:
				if (c >= '0' && c <= '9') state = 1;
				else if (c == 'E' || c == 'e') state = 3;
				else if (c == '.') state = 2;
				else {
					index--;
					Token token = new Token("decimal", input.substring(startIndex, index));
					//System.out.println(input.substring(startIndex, index));
					lastTokenType = Parser.map.get("decimal");
					return token;
				}
				break;
			case 2:
				if (c >= '0' && c <= '9') state = 4;
				else throw new IllegalDecimalException();
				break;
			case 3:
				if (c == '+' || c == '-') state = 5;
				else if (c >= '0' && c <= '9') state = 6;
				else throw new IllegalDecimalException();
				break;
			case 4:
				if (c >= '0' && c <= '9') state = 4;
				else if (c == 'E' || c == 'e') state = 3;
				else {
					index--;
					Token token = new Token("decimal", input.substring(startIndex, index));
					lastTokenType = Parser.map.get("decimal");
					return token;
				}
				break;
			case 5:
				if (c >= '0' && c <= '9') state = 6;
				else throw new IllegalDecimalException();
				break;
			case 6:
				if (c >= '0' && c <= '9') state = 6;
				else {
					index--;
					Token token = new Token("decimal", input.substring(startIndex, index));
					lastTokenType = Parser.map.get("decimal");
					return token;
				}
				break;
			case 7:
				if (c == 'o') state = 8;
				else throw new IllegalIdentifierException();
				break;
			case 8:
				if (c == 's') {
					Token token = new Token("cos");
					lastTokenType = Parser.map.get("cos");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 9:
				if (c == 'i') state = 10;
				else if (c == 'a') state = 11;
				else throw new IllegalIdentifierException();
				break;
			case 10:
				if (c == 'n') {
					Token token = new Token("min");
					lastTokenType = Parser.map.get("min");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 11:
				if (c == 'x') {
					Token token = new Token("max");
					lastTokenType = Parser.map.get("max");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 12:
				if (c == 'i') state = 13;
				else throw new IllegalIdentifierException();
				break;
			case 13:
				if (c == 'n') {
					Token token = new Token("sin");
					lastTokenType = Parser.map.get("sin");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 14:
				if (c == 'r') state = 15;
				else throw new IllegalIdentifierException();
				break;
			case 15:
				if (c == 'u') state = 16;
				else throw new IllegalIdentifierException();
				break;
			case 16:
				if (c == 'e') {
					Token token = new Token("true");
					lastTokenType = Parser.map.get("true");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 17:
				if (c == 'a') state = 18;
				else throw new IllegalIdentifierException();
				break;
			case 18:
				if (c == 'l') state = 19;
				else throw new IllegalIdentifierException();
				break;
			case 19:
				if (c == 's') state = 20;
				else throw new IllegalIdentifierException();
				break;
			case 20:
				if (c == 'e') {
					Token token = new Token("false");
					lastTokenType = Parser.map.get("false");
					return token;
				}
				else throw new IllegalIdentifierException();
			case 21:
				if (c == '=') {
					Token token = new Token(">=");
					lastTokenType = Parser.map.get(">=");
					return token;
				}
				else {
					index--;
					Token token = new Token(">");
					lastTokenType = Parser.map.get(">");
					return token;
				}
			case 22:
				if (c == '=') {
					Token token = new Token("<=");
					lastTokenType = Parser.map.get("<=");
					return token;
				}
				else if (c == '>') {
					Token token = new Token("<>");
					lastTokenType = Parser.map.get("<>");
					return token;
				}
				else {
					index--;
					Token token = new Token("<");
					lastTokenType = Parser.map.get("<");
					return token;
				}
			}
		}
	}
}
