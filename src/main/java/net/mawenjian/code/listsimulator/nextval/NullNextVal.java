package net.mawenjian.code.listsimulator.nextval;

/**
 * NULL值生成器
 * 
 * @author mawenjian@gmail.com
 *
 */
public class NullNextVal implements NextValIface<Void> {

	// @Override
	public Void nextVal() {
		return null;
	}

	public void reset() {
		
	}

}
