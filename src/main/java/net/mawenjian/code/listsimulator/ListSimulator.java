package net.mawenjian.code.listsimulator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.mawenjian.code.listsimulator.nextval.ConstantNextVal;
import net.mawenjian.code.listsimulator.nextval.NextValIface;
import net.mawenjian.code.listsimulator.nextval.NullNextVal;
import net.mawenjian.code.listsimulator.nextval.RandomNextVal;
import net.mawenjian.code.listsimulator.nextval.SerialNextVal;
import net.mawenjian.code.listsimulator.nextval.SerialNextVal.SerialStringNextVal;

/**
 * List模拟数据生成类
 * 
 * @author mawenjian@gmail.com
 *
 */
public class ListSimulator<T> {
	
	private final Class<? extends T> genericType;

	// NULL值生成器，默认所有属性使用此生成器
	private final NextValIface<?> nullNextValIface = new NullNextVal();

	// 建立属性名称与对应的属性值生成器的映射
	private final Map<String, NextValIface<?>> fieldNameToValGeneratorMap = new HashMap<String, NextValIface<?>>();

	private ListSimulator(Class<? extends T> clazz) {
		this.genericType = clazz;
		for (Field field : genericType.getDeclaredFields()) {
			String className = field.getName();

			NextValIface<?> nextValIface = nullNextValIface;
			fieldNameToValGeneratorMap.put(className, nextValIface);
		}
	}

	public static class Builder<T> {
		private ListSimulator<T> listGenerator;
		private Set<String> limitedFields = new HashSet<String>();
		private Map<String, NextValIface<?>> defaultValFields = new HashMap<String, NextValIface<?>>();

		public Builder(Class<? extends T> clazz) {
			listGenerator = new ListSimulator<T>(clazz);
		}

		/**
		 * 添加属性名称，即只使用所有属性中有限的部分属性
		 * 
		 * @param limitedFields
		 * @return
		 */
		public Builder<T> addLimitedFields(String... limitedFields) {
			if (limitedFields != null && limitedFields.length > 0) {
				this.limitedFields.addAll(Arrays.asList(limitedFields));
			}
			return this;
		}

		/**
		 * 只使用有限制的属性
		 * 
		 * @param limitedField
		 * @return
		 */
		public Builder<T> addLimitedField(String limitedField) {
			if ((limitedField != null) && (!limitedField.equals("")) && (!limitedFields.contains(limitedField))) {
				this.limitedFields.add(limitedField);
			}
			return this;
		}

		/**
		 * 设置属性所使用的属性值生成器
		 * 
		 * @param fieldName
		 * @param nextValIface
		 * @return
		 */
		public Builder<T> applyNextVal(String fieldName, NextValIface<?> nextValIface) {
			defaultValFields.put(fieldName, nextValIface);
			return this;
		}

		/**
		 * 批量设置属性所使用的属性值生成器
		 * 
		 * @param fieldnameValPairs
		 * @return
		 */
		public Builder<T> applyNextVals(Map<String, NextValIface<?>> fieldnameValPairs) {
			if (fieldnameValPairs != null && !fieldnameValPairs.isEmpty()) {
				for (String fieldName : fieldnameValPairs.keySet()) {
					NextValIface<?> fieldValue = fieldnameValPairs.get(fieldName);
					applyNextVal(fieldName, fieldValue);
				}
			}
			return this;
		}

		/**
		 * 设置属性使用的默认值（即使用某个常量）
		 * 
		 * @param fieldName
		 * @param fieldValue
		 * @return
		 */
		public Builder<T> applyConstantVal(String fieldName, Object fieldValue) {
			NextValIface<?> nextValIface = new ConstantNextVal<Object>(fieldValue);
			return applyNextVal(fieldName, nextValIface);
		}

		/**
		 * 批量设置属性使用的默认值（即使用常量）
		 * 
		 * @param fieldnameValPairs [[fieldName, value], ...]
		 * @return
		 */
		public Builder<T> applyConstantVal(Object[]... fieldnameValPairs) {
			// 检查一下
			if (fieldnameValPairs == null) {
				throw new InvalidParameterException("fieldnameValPairs is null.");
			} else {
				for (Object[] pair : fieldnameValPairs) {
					if (pair == null || pair.length != 2) {
						throw new InvalidParameterException("the length in array of fieldnameValPairs is not equal 2.");
					}
				}
			}

			for (Object[] pair : fieldnameValPairs) {
				String fieldName = pair[0].toString();
				Object fieldValue = pair[1];
				applyConstantVal(fieldName, fieldValue);
			}

			return this;
		}

		/**
		 * 设置属性采用顺序化生成器
		 * 
		 * @param fieldNames
		 * @return
		 * @throws Exception
		 */
		public Builder<T> applySerialVal(String... fieldNames) throws Exception {
			if (fieldNames != null && fieldNames.length > 0) {
				Set<String> serialFieldSet = new HashSet<String>(Arrays.asList(fieldNames));
				listGenerator.applySerialFields(serialFieldSet);
			}
			return this;
		}

		/**
		 * 
		 * @param fieldName
		 *            变量名称
		 * @param formatString
		 *            包含{0}形式的字符串
		 * @return
		 * @throws Exception
		 */
		public Builder<T> applySerialStringVal(String fieldName, String formatString) throws Exception {
			listGenerator.applySerialStringVal(fieldName, formatString);
			return this;
		}
		
		/**
		 * 
		 * @param fieldName
		 *            变量名称
		 * @param formatString
		 *            包含{0}形式的字符串
		 * @param fixedNumberLength
		 *            数字部分的固定长度
		 * @return
		 * @throws Exception
		 */
		public Builder<T> applySerialStringVal(String fieldName, String formatString, Integer fixedNumberLength) throws Exception {
			listGenerator.applySerialStringVal(fieldName, formatString, fixedNumberLength);
			return this;

		}

		/**
		 * 
		 * @param fieldNameToFormatString
		 * @return
		 * @throws Exception
		 */
		public Builder<T> applySerialStringVal(Object[]... fieldNameToFormatString) throws Exception {
			// 检查一下
			if (fieldNameToFormatString == null) {
				throw new InvalidParameterException("fieldNameToFormatString is null.");
			} else {
				for (Object[] pair : fieldNameToFormatString) {
					if (pair == null || pair.length < 2 || pair.length > 3) {
						throw new InvalidParameterException("the length in array of fieldNameToFormatString is not equal 2 or equal 3.");
					}
				}
			}

			for (Object[] pair : fieldNameToFormatString) {
				String fieldName = pair[0].toString();
				String fieldValue = pair[1].toString();
				if (pair.length == 2) {
					applySerialStringVal(fieldName, fieldValue);
				} else {
					Integer fixedNumberLength = Integer.parseInt(pair[2].toString());
					applySerialStringVal(fieldName, fieldValue, fixedNumberLength);
				}
			}

			return this;

		}

		public Builder<T> applyRandomVal(String... fieldNames) throws Exception {
			if (fieldNames != null && fieldNames.length > 0) {
				Set<String> randomFieldSet = new HashSet<String>(Arrays.asList(fieldNames));
				listGenerator.applyRandomlVal(randomFieldSet);
			}
			return this;
		}

		public ListSimulator<T> build() throws Exception {
			if (!defaultValFields.isEmpty()) {
				listGenerator.applyNextVals(defaultValFields);
			}
			// 不为空则只使用有限属性
			if (!limitedFields.isEmpty()) {
				listGenerator.useLimitedFields(limitedFields);
			}
			return listGenerator;
		}
	}

	/**
	 * 生成列表
	 * 
	 * @param size
	 *            列表大小
	 * @param tlist
	 *            列表的副本，最少一个，最多不限
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked" })
	public void generate(final int size, List<T>... tlist) throws Exception {
		if (tlist == null || tlist.length <= 0) {
			throw new InvalidParameterException("at least one parameter is needed!");
		} else {
			for (List<T> list : tlist) {
				if (list == null) {
					throw new InvalidParameterException("tlist cannot contains null value!");
				}
			}
		}
		for (int i = 0; i < size; i++) {
			// 对象实例化
			T[] instanceList = (T[]) Array.newInstance(genericType, tlist.length);
			for (int j = 0; j < tlist.length; j++) {
				instanceList[j] = genericType.newInstance();
			}
			// 对象赋值
			for (Field field : genericType.getDeclaredFields()) {
				// 当前属性名称
				String thisFieldName = field.getName();

				NextValIface<?> nextValIface = fieldNameToValGeneratorMap.get(thisFieldName);
				Object thisFieldVal = nextValIface.nextVal();
				if (thisFieldVal == null) {
					continue;
				}

				boolean isAccessibile = field.isAccessible();
				if (!isAccessibile) {
					field.setAccessible(true);
					for (T thisInstance : instanceList) {
						field.set(thisInstance, thisFieldVal);
					}
					field.setAccessible(false);
				} else {
					for (T thisInstance : instanceList) {
						field.set(thisInstance, thisFieldVal);
					}
				}
			}
			// 添加到列表
			for (int j = 0; j < tlist.length; j++) {
				tlist[j].add(instanceList[j]);
			}
		}
	}

	/**
	 * 重置生成器，主要是针对序列化递增的需求设计的
	 * 
	 * @throws Exception
	 */
	public void reset() throws Exception {
		for (NextValIface<?> nextValIface : fieldNameToValGeneratorMap.values()) {
			nextValIface.reset();
		}
	}

	/**
	 * 只对有限的属性有效
	 * 
	 * @param limitedFieldSet
	 */
	private void useLimitedFields(Set<String> limitedFieldSet) {
		if (limitedFieldSet == null || limitedFieldSet.isEmpty()) {
			throw new InvalidParameterException("limitedFieldSet cannot be null or empty!");
		}
		// 剩余属性使用NullNextVal生成（因为NULL不会真正地赋给属性值，所以对基本类型依然适用）
		for (String fieldName : fieldNameToValGeneratorMap.keySet()) {
			if (!limitedFieldSet.contains(fieldName)) {
				fieldNameToValGeneratorMap.put(fieldName, nullNextValIface);
			}
		}
	}

	/**
	 * 某些属性使用设定值
	 * 
	 * @param nextValFields
	 * @throws Exception
	 * @throws NoSuchFieldException
	 */
	private void applyNextVals(Map<String, NextValIface<?>> nextValFields) throws Exception {
		if (nextValFields == null || nextValFields.isEmpty()) {
			throw new InvalidParameterException("defaultValFields cannot be null or empty!");
		}

		for (String fieldName : nextValFields.keySet()) {
			NextValIface<?> fieldValue = nextValFields.get(fieldName);
			fieldNameToValGeneratorMap.put(fieldName, fieldValue);
		}
	}

	/**
	 * 为属性生成递增值
	 * 
	 * @param serialFieldSet
	 * @throws Exception
	 * @throws SecurityException
	 */
	private void applySerialFields(Set<String> serialFieldSet) throws Exception {
		if (serialFieldSet == null || serialFieldSet.isEmpty()) {
			return;
		}
		// 剩余属性使用NullNextVal生成（因为NULL不会真正地赋给属性值，所以对基本类型依然适用）
		for (String fieldName : serialFieldSet) {
			if (fieldNameToValGeneratorMap.containsKey(fieldName)) {
				Field field = genericType.getDeclaredField(fieldName);
				Class<?> clazz = field.getType();
				NextValIface<?> nextValIface = SerialNextVal.newInstance(clazz);
				if(nextValIface instanceof NullNextVal) {
					throw new InvalidParameterException(String.format("[%s], type of [%s], has no default SerialNextVal generator, please define it by yourself.", fieldName, field.getName()));
				}
				fieldNameToValGeneratorMap.put(fieldName, nextValIface);
			}
		}
	}

	/**
	 * 为属性生成递增值
	 * @param fieldName 属性名称
	 * @param formatString 包含{0}的格式化字符
	 * @param fixedNumberLength 递增部分的固定长度
	 * @throws Exception
	 */
	private void applySerialStringVal(String fieldName, String formatString, Integer... fixedNumberLength) throws Exception {
		if (fieldName == null || fieldName.equals("") || formatString == null || !formatString.contains("{0}")) {
			throw new InvalidParameterException("applySerialStringFields() has invalid params!");
		}

		if (fieldNameToValGeneratorMap.containsKey(fieldName)) {
			Field field = genericType.getDeclaredField(fieldName);
			Class<?> clazz = field.getType();
			if (clazz != String.class) {
				throw new InvalidParameterException(String.format("%s is not a String variable!", fieldName));
			}
			SerialStringNextVal nextValIface = SerialNextVal.newStringInstance();
			nextValIface.setFormatString(formatString);
			if (fixedNumberLength != null && fixedNumberLength.length == 1) {
				nextValIface.setFixedNumberLength(fixedNumberLength[0]);
			}
			fieldNameToValGeneratorMap.put(fieldName, nextValIface);
		} else {
			throw new InvalidParameterException(String.format("%s is not a variable!", fieldName));
		}
	}

	/**
	 * 为属性生成随机值
	 * 
	 * @param randomFieldSet
	 * @throws Exception
	 * @throws SecurityException
	 */
	private void applyRandomlVal(Set<String> randomFieldSet) throws Exception {
		if (randomFieldSet == null || randomFieldSet.isEmpty()) {
			return;
		}
		// 剩余属性使用NullNextVal生成（因为NULL不会真正地赋给属性值，所以对基本类型依然适用）
		for (String fieldName : randomFieldSet) {
			if (fieldNameToValGeneratorMap.containsKey(fieldName)) {
				Field field = genericType.getDeclaredField(fieldName);
				Class<?> clazz = field.getType();
				NextValIface<?> nextValIface = RandomNextVal.newInstance(clazz);
				if(nextValIface instanceof NullNextVal) {
					throw new InvalidParameterException(String.format("[%s], type of [%s], has no default RandomNextVal generator, please define it by yourself.", fieldName, field.getName()));
				}
				fieldNameToValGeneratorMap.put(fieldName, nextValIface);
			}
		}
	}
}
