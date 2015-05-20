package parser;

/**
 * Token��������ս���ţ������͵Ķ���ͨ���ɴʷ�������Scanner����õ�
 * Token��̳���Expr�࣬typeId��ΪToken���͵�����ӳ�䣬���ڷ���OPP��
 */
public class Token extends Expr{
	public int typeId;
	
	/**
	* ���캯��
	* @param content Token������
	*/
	public Token(String content) {
		super(content);
		this.typeId = Parser.map.get(content);
	}
	/**
	* ���캯��
	* @param content Token������
	* @param value Token���ַ������ʹ洢��ֵ
	*/
	public Token(String content, String value) {
		super(content, value);
		this.typeId = Parser.map.get(content);
	}
}
