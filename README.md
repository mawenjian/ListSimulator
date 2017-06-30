# ListSimulator

`ListSimulator`是一个基于 _Java_ 的列表（_List_）模拟数据生成工具，主要用于在书写测试代码时，按照自定义规则生成一系列具有特定规律的自定义数据。

## 程序演示

### 1. 一个综合例子

<pre><code>
    ListSimulator&lt;DemoBean&gt; listSimulator = new ListSimulator.Builder&lt;DemoBean&gt;(DemoBean.class)
    		.applySerialVal("d1")	//顺序化生成值（从1开始）
    		.applyConstantVal("d2", 100)	//生成固定值100
    		.applyRandomVal("i")	//生成随机值
    		.applySerialStringVal("i2", "i2-{0}")	//顺序化生成字符串，数字部分不固定长度
    		.applySerialStringVal("i3", "i3-{0}", 10)	//顺序化生成字符串，数字部分不固定长度
    		.applyNextVal("innerBean", testDiyNextValIface)	//使用自定义的生成器生成值
		.build();
	
	List&lt;DemoBean&gt; list1 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list2 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list3 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list4 = new ArrayList&lt;DemoBean&gt;();
	
	//生成多个完全一样的对象
	listSimulator.generate(10, list1, list2);
	//接着上面的序号，继续顺序生成对象
	listSimulator.generate(10, list3);
	//重置生成器
	listSimulator.reset();
	//重新生成列表
	listSimulator.generate(10, list4);
	
	System.out.println(JSON.toJSONString(list1));
	System.out.println(JSON.toJSONString(list2));
	System.out.println(JSON.toJSONString(list3));
	System.out.println(JSON.toJSONString(list4));
</code></pre>

### 2. 使用顺序化生成器

<pre><code>
    ListSimulator&lt;DemoBean&gt; listSimulator = new ListSimulator.Builder&lt;DemoBean&gt;(DemoBean.class)
    		.applySerialVal("a1")
    		.applySerialVal("a2")
    		.applySerialVal("b1", "b2", "c1", "c2", "d1", "d2", "e1", "e2", "f1", "f2", "g1", "g2", "h1", "h2", "i", "j", "k")
    		.applySerialStringVal("i2", "i2-{0}")
    		.applySerialStringVal("i3", "i3-{0}", 10)
    		.build();

	List&lt;DemoBean&gt; list1 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list2 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list3 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list4 = new ArrayList&lt;DemoBean&gt;();
	
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
</code></pre>

### 3. 使用随机化生成器

<pre><code>
    ListSimulator&lt;DemoBean&gt; listSimulator = new ListSimulator.Builder&lt;DemoBean&gt;(DemoBean.class)
        	.applyRandomVal("a1").applyRandomVal("a2")
        	.applyRandomVal("b1", "b2", "c1", "c2", "d1", "d2", "e1", "e2", "f1", "f2", "g1", "g2", "h1", "h2", "i", "i2", "i3", "j", "k")
        	.build();
    
	List&lt;DemoBean&gt; list1 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list2 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list3 = new ArrayList&lt;DemoBean&gt;();
	
	//生成多个完全一样的对象
	listSimulator.generate(10, list1, list2);
	//接着上面的序号，继续顺序生成对象
	listSimulator.generate(10, list3);
	
	System.out.println(JSON.toJSONString(list1));
	System.out.println(JSON.toJSONString(list2));
	System.out.println(JSON.toJSONString(list3));
</code></pre>

### 4. 使用常量（固定值）生成器

<pre><code>
    Object[][] params = {{"a2", false}, {"c2", (short)2}};
		ListSimulator&lt;DemoBean&gt; listSimulator = new ListSimulator.Builder&lt;DemoBean&gt;(DemoBean.class)
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
			
	List&lt;DemoBean&gt; list1 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list2 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list3 = new ArrayList&lt;DemoBean&gt;();
	
	//生成多个完全一样的对象
	listSimulator.generate(10, list1, list2);
	//接着上面的序号，继续顺序生成对象
	listSimulator.generate(10, list3);
	
	System.out.println(JSON.toJSONString(list1));
	System.out.println(JSON.toJSONString(list2));
	System.out.println(JSON.toJSONString(list3));
</code></pre>

### 5. 自定义生成器

<pre><code>
    ListSimulator&lt;DemoBean&gt; listSimulator = new ListSimulator.Builder&lt;DemoBean&gt;(DemoBean.class)
			.applyNextVal("innerBean", testDiyNextValIface)
			.build();
			
	List&lt;DemoBean&gt; list1 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list2 = new ArrayList&lt;DemoBean&gt;();
	List&lt;DemoBean&gt; list3 = new ArrayList&lt;DemoBean&gt;();
	
	//生成多个完全一样的对象
	listSimulator.generate(10, list1, list2);
	//接着上面的序号，继续顺序生成对象
	listSimulator.generate(10, list3);
	
	System.out.println(JSON.toJSONString(list1));
	System.out.println(JSON.toJSONString(list2));
	System.out.println(JSON.toJSONString(list3));
</code></pre>

其中，`testDiyNextValIface`的定义如下：

<pre><code>
    NextValIface&lt;DemoInnerBean&gt; testDiyNextValIface = new NextValIface&lt;DemoInnerBean&gt;() {

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
}
</code></pre>

`innerBean`的类型如下：

<pre><code>
public class DemoInnerBean {
	private int id;
	private String serialNm;
	private String rndString;

	//getter and setter...
}
</code></pre>

## 支持的数据类型

ListSimulator提供了对Java基本数据类型（`boolean`、`byte`、`short`、`int`、`long`、`float`、`double`、`char`）及`String`、`BigDecimal`、`Date`等常用数据类型的支持。

## 内置数据生成器类型

* `NextValIface<T>`：所有内置数据生成器的数据接口；
    
* `ConstantNextVal<T>`：生成具有固定值的常量；
    
* `NullNextVal`：生成_NULL_值；
    
* `RandomNextVal`：生成随机数/值；
    
* `SerialNextVal`：生成顺序化的数/值；

## 可扩展

其余数据类型及特定的用户需求支持通过实现`NextValIface<T>`接口自行实现。

## 联系方式

* 邮箱：mawenjian#gmail.com

* 博客：[http://mawenjian.net/](http://mawenjian.net "马文建的博客")
