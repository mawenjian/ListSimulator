package net.mawenjian.code.listsimulator.nextval;

/**
 * 常量生成器
 * 
 * @author mawenjian@gmail.com
 *
 * @param <T>
 */
public class ConstantNextVal<T> implements NextValIface<T> {

	// 常量值
	private T constantVal;

	public ConstantNextVal() {
	}

	public ConstantNextVal(T constantVal) {
		setConstantVal(constantVal);
	}

	@Override
	public T nextVal() {
		return getConstantVal();
	}

	public T getConstantVal() {
		return constantVal;
	}

	public void setConstantVal(T constantVal) {
		this.constantVal = constantVal;
	}

	public void reset() {

	}

}