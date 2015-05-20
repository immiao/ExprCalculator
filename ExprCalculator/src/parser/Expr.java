package parser;

/**
 * ���ڴ洢���ʽ���࣬����ı��ʽ��ָ���ս���ź��ս����
 * ���ʽ��ֵ�����Ǹ����ͻ��߲����ͣ�ͬʱ���ַ������ʹ洢��ֵ
 * �����ʽ����ΪArithExprListʱ�������������ֵ����Сֵ������min��max�����ļ���
 */
public class Expr {
	public String content;
	public String strValue;
	public boolean boolValue;
	public double doubleValue;
	public double maxDouble;
	public double minDouble;
	
	/**
	* ���캯��
	* @param expression ���ʽ������
	*/
	public Expr(String expression) {
		this.content = expression;
	}
	/**
	* ���캯��
	* @param expression ���ʽ������
	* @param value ���ʽ��ֵ�����ַ������ʹ洢
	*/
	public Expr(String expression, String value) {
		this.content = expression;
		this.strValue = value;
		//System.out.println(this.strValue);
	}
	/**
	* ���캯��
	* @param expression ���ʽ������
	* @param value ���ʽ��ֵ���Բ������ʹ洢
	*/
	public Expr(String expression, boolean value) {
		this.content = expression;
		boolValue = value;
	}
	/**
	* ���캯��
	* @param expression ���ʽ������
	* @param value ���ʽ��ֵ���Ը��������ʹ洢
	*/
	public Expr(String expression, double value) {
		this.content = expression;
		doubleValue = value;
		maxDouble = value;
		minDouble = value;
	}
}
