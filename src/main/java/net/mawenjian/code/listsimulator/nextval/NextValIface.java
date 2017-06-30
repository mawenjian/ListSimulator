package net.mawenjian.code.listsimulator.nextval;

/**
 * 返回下一个值接口
 * @author mawenjian@gmail.com
 *
 * @param <T>
 */
public interface NextValIface<T> {
	/**
	 * 下一个值
	 * @return
	 */
	public T nextVal();
	
	/**
	 * 重置
	 */
	public void reset();
}
