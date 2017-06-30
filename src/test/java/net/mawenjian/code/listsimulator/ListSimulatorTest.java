package net.mawenjian.code.listsimulator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import net.mawenjian.code.listsimulator.nextval.NextValIface;
import net.mawenjian.code.listsimulator.nextval.RandomNextVal;
import net.mawenjian.code.listsimulator.nextval.SerialNextVal;
import net.mawenjian.code.listsimulator.nextval.SerialNextVal.SerialStringNextVal;

public class ListSimulatorTest {
	
	
	/**
	 * 测试使用顺序化生成器
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMixedUse() throws Exception {
		ListSimulator<DemoBean> listSimulator = new ListSimulator.Builder<DemoBean>(DemoBean.class)
				.applySerialVal("a1")
				.applyConstantVal("d1", (int)3)
				.applyRandomVal("i")
				.applySerialStringVal("i2", "i2-{0}")
				.applySerialStringVal("i3", "i3-{0}", 10)
				.applyNextVal("innerBean", testDiyNextValIface)
				.build();
		List<DemoBean> list1 = new ArrayList<DemoBean>();
		List<DemoBean> list2 = new ArrayList<DemoBean>();
		List<DemoBean> list3 = new ArrayList<DemoBean>();
		List<DemoBean> list4 = new ArrayList<DemoBean>();
		
		//生成多个完全一样的对象
		listSimulator.generate(10, list1, list2);
		//接着上面的序号，继续顺序生成对象
		listSimulator.generate(10, list3);
		listSimulator.reset();
		listSimulator.generate(10, list4);
		
		System.out.println(JSON.toJSONString(list1));
		System.out.println(JSON.toJSONString(list2));
		System.out.println(JSON.toJSONString(list3));
		System.out.println(JSON.toJSONString(list4));
	}
	
	/**
	 * 测试使用顺序化生成器
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testSeriaNextVal() throws Exception {
		ListSimulator<DemoBean> listSimulator = new ListSimulator.Builder<DemoBean>(DemoBean.class)
				.applySerialVal("a1").applySerialVal("a2")
				.applySerialVal("b1", "b2", "c1", "c2", "d1", "d2", "e1", "e2", "f1", "f2", "g1", "g2", "h1", "h2", "i", "j", "k")
				.applySerialStringVal("i2", "i2-{0}")
				.applySerialStringVal("i3", "i3-{0}", 10)
				.build();
		List<DemoBean> list1 = new ArrayList<DemoBean>();
		List<DemoBean> list2 = new ArrayList<DemoBean>();
		List<DemoBean> list3 = new ArrayList<DemoBean>();
		List<DemoBean> list4 = new ArrayList<DemoBean>();
		
		//生成多个完全一样的对象
		listSimulator.generate(10, list1, list2);
		//接着上面的序号，继续顺序生成对象
		listSimulator.generate(10, list3);
		listSimulator.reset();
		listSimulator.generate(10, list4);
		
		System.out.println(JSON.toJSONString(list1));
		System.out.println(JSON.toJSONString(list2));
		System.out.println(JSON.toJSONString(list3));
		System.out.println(JSON.toJSONString(list4));
	}
	
	/**
	 * 测试使用随机化生成器
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRandomNextVal() throws Exception {
		ListSimulator<DemoBean> listSimulator = new ListSimulator.Builder<DemoBean>(DemoBean.class)
				.applyRandomVal("a1").applyRandomVal("a2")
				.applyRandomVal("b1", "b2", "c1", "c2", "d1", "d2", "e1", "e2", "f1", "f2", "g1", "g2", "h1", "h2", "i", "i2", "i3", "j", "k")
				.build();
		List<DemoBean> list1 = new ArrayList<DemoBean>();
		List<DemoBean> list2 = new ArrayList<DemoBean>();
		List<DemoBean> list3 = new ArrayList<DemoBean>();
		
		//生成多个完全一样的对象
		listSimulator.generate(10, list1, list2);
		//接着上面的序号，继续顺序生成对象
		listSimulator.generate(10, list3);
		
		System.out.println(JSON.toJSONString(list1));
		System.out.println(JSON.toJSONString(list2));
		System.out.println(JSON.toJSONString(list3));
	}
	
	/**
	 * 测试使用常量（固定值）生成器
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstantNextVal() throws Exception {
		Object[][] params = {{"a2", false}, {"c2", (short)2}};
		ListSimulator<DemoBean> listSimulator = new ListSimulator.Builder<DemoBean>(DemoBean.class)
				.applyConstantVal("a1", true)
				.applyConstantVal("b1", (byte)1)
				.applyConstantVal("c1", (short)2)
				.applyConstantVal("d1", (int)3)
				.applyConstantVal("d1", (int)30)
				.applyConstantVal("e1", (long)4)
				.applyConstantVal("f1", (float)5)
				.applyConstantVal("g1", (double)6)
				.applyConstantVal("h1", (char)7)
				.applyConstantVal("i", "hello")
				.applyConstantVal("i2", "world")
				.applyConstantVal("i3", "!")
				.applyConstantVal("j", BigDecimal.valueOf(100))
				.applyConstantVal("k", new Date())
				.applyConstantVal(params)
				.build();
		List<DemoBean> list1 = new ArrayList<DemoBean>();
		List<DemoBean> list2 = new ArrayList<DemoBean>();
		List<DemoBean> list3 = new ArrayList<DemoBean>();
		
		//生成多个完全一样的对象
		listSimulator.generate(10, list1, list2);
		//接着上面的序号，继续顺序生成对象
		listSimulator.generate(10, list3);
		
		System.out.println(JSON.toJSONString(list1));
		System.out.println(JSON.toJSONString(list2));
		System.out.println(JSON.toJSONString(list3));
	}
	
	/**
	 * 测试自定义生成器
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testDiyNextVal() throws Exception {
		ListSimulator<DemoBean> listSimulator = new ListSimulator.Builder<DemoBean>(DemoBean.class)
				.applyNextVal("innerBean", testDiyNextValIface)
				.build();
		List<DemoBean> list1 = new ArrayList<DemoBean>();
		List<DemoBean> list2 = new ArrayList<DemoBean>();
		List<DemoBean> list3 = new ArrayList<DemoBean>();
		
		//生成多个完全一样的对象
		listSimulator.generate(10, list1, list2);
		//接着上面的序号，继续顺序生成对象
		listSimulator.generate(10, list3);
		
		System.out.println(JSON.toJSONString(list1));
		System.out.println(JSON.toJSONString(list2));
		System.out.println(JSON.toJSONString(list3));
	}
	
	/**
	 * 自定义生成器
	 */
	private static NextValIface<DemoInnerBean> testDiyNextValIface = new NextValIface<DemoInnerBean>() {

		private int counter;
		private SerialStringNextVal nextSerialNmIface;
		private NextValIface<String> nextRndStringIface;
		
		{
			counter = 1;
			
			nextSerialNmIface = SerialNextVal.newStringInstance();
			nextSerialNmIface.setFormatString("test-for-mwj-{0}");
			nextSerialNmIface.setFixedNumberLength(10);
			
			nextRndStringIface = RandomNextVal.newStringInstance();
		}
		
		@Override
		public DemoInnerBean nextVal() {
			DemoInnerBean testBean = new DemoInnerBean();
			
			int id = counter++;
			String serialNm = nextSerialNmIface.nextVal();
			String rndString = nextRndStringIface.nextVal();
			
			testBean.setId(id);
			testBean.setSerialNm(serialNm);
			testBean.setRndString(rndString);
			
			return testBean;
		}

		@Override
		public void reset() {
			this.counter = 1;
			this.nextSerialNmIface.reset();
			this.nextRndStringIface.reset();
		}
		
	};
}