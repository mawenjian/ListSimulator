package net.mawenjian.code.listsimulator.nextval;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 顺序值生成器
 * @author mawenjian@gmail.com
 *
 */
public class SerialNextVal {
	/**
	 * 工厂方法
	 * 
	 * @param clazz
	 * @return
	 */
	public static NextValIface<?> newInstance(Class<?> clazz) {
		NextValIface<?> nextValIface;

		if(clazz == null || clazz == Void.class) {
			nextValIface = new NullNextVal();
		} else if (clazz == Boolean.class || clazz == boolean.class) {
			nextValIface = newBooleanInstance();
		} else if (clazz == Byte.class || clazz == byte.class) {
			nextValIface = newByteInstance();
		} else if (clazz == Character.class || clazz == char.class) {
			nextValIface = newCharacterInstance();
		} else if (clazz == Short.class || clazz == short.class) {
			nextValIface = newShortInstance();
		} else if (clazz == Integer.class || clazz == int.class) {
			nextValIface = newIntegerInstance();
		} else if (clazz == Long.class || clazz == long.class) {
			nextValIface = newLongInstance();
		} else if (clazz == Float.class || clazz == float.class) {
			nextValIface = newFloatInstance();
		} else if (clazz == Double.class || clazz == double.class) {
			nextValIface = newDoubleInstance();
		} else if (clazz == BigDecimal.class) {
			nextValIface = newBigDecimalInstance();
		} else if (clazz == Date.class) {
			nextValIface = newDateInstance();
		} else if (clazz == String.class) {
			nextValIface = newStringInstance();
		} else {
			throw new InvalidParameterException(String.format("type of [%s] is not supported by SerialNextVal, please implements NextVal interface by yourself.", clazz.getName()));
		}

		return nextValIface;
	}

	public static SerialBooleanNextVal newBooleanInstance() {
		SerialBooleanNextVal nextBoolean = new SerialBooleanNextVal();
		return nextBoolean;
	}

	public static SerialByteNextVal newByteInstance() {
		SerialByteNextVal nextByte = new SerialByteNextVal();
		return nextByte;
	}

	public static SerialCharacterNextVal newCharacterInstance() {
		SerialCharacterNextVal nextCharacter = new SerialCharacterNextVal();
		return nextCharacter;
	}
	
	public static SerialShortNextVal newShortInstance() {
		SerialShortNextVal nextShort = new SerialShortNextVal();
		return nextShort;
	}

	public static SerialIntegerNextVal newIntegerInstance() {
		SerialIntegerNextVal nextInteger = new SerialIntegerNextVal();
		return nextInteger;
	}

	public static SerialLongNextVal newLongInstance() {
		SerialLongNextVal nextLong = new SerialLongNextVal();
		return nextLong;
	}

	public static SerialFloatNextVal newFloatInstance() {
		SerialFloatNextVal nextFloat = new SerialFloatNextVal();
		return nextFloat;
	}

	public static SerialDoubleNextVal newDoubleInstance() {
		SerialDoubleNextVal nextDouble = new SerialDoubleNextVal();
		return nextDouble;
	}

	public static SerialBigDecimalNextVal newBigDecimalInstance() {
		SerialBigDecimalNextVal nextBigDecimal = new SerialBigDecimalNextVal();
		return nextBigDecimal;
	}

	public static SerialDateNextVal newDateInstance() {
		SerialDateNextVal nextDate = new SerialDateNextVal();
		return nextDate;
	}

	public static SerialStringNextVal newStringInstance() {
		SerialStringNextVal nextString = new SerialStringNextVal();
		return nextString;
	}

	public static class SerialBooleanNextVal implements NextValIface<Boolean> {
		private int counter = 1;

		// @Override
		public Boolean nextVal() {
			Boolean bool = (counter++) % 2 == 0;
			return bool;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialByteNextVal implements NextValIface<Byte> {
		private int counter = 1;

		// @Override
		public Byte nextVal() {
			byte aByte = (byte) counter++;
			return aByte;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialCharacterNextVal implements NextValIface<Character> {
		private int counter = 1;

		// @Override
		public Character nextVal() {
			char serialNextChar = (char) counter++;
			return serialNextChar;
		}

		public void reset() {
			counter = 1;
		}
	}
	
	public static class SerialShortNextVal implements NextValIface<Short> {
		private int counter = 1;
		
		// @Override
		public Short nextVal() {
			Short serialNextShort = (short) counter++;
			return serialNextShort;
		}
		
		public void reset() {
			counter = 1;
		}
	}

	public static class SerialIntegerNextVal implements NextValIface<Integer> {
		private int counter = 1;

		// @Override
		public Integer nextVal() {
			Integer serialNextInt = counter++;
			return serialNextInt;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialLongNextVal implements NextValIface<Long> {
		private long counter = 1;

		// @Override
		public Long nextVal() {
			Long serialNextLong = counter++;
			return serialNextLong;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialFloatNextVal implements NextValIface<Float> {
		private float counter = 0.0f;

		// @Override
		public Float nextVal() {
			Float serialNextFloat = counter++;
			return serialNextFloat;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialDoubleNextVal implements NextValIface<Double> {
		private double counter = 0.0;

		// @Override
		public Double nextVal() {
			Double serialNextDbl = counter++;
			return serialNextDbl;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialBigDecimalNextVal implements NextValIface<BigDecimal> {
		private long counter = 1;

		public BigDecimal nextVal() {
			BigDecimal serialNextBd = new BigDecimal(counter++);
			return serialNextBd;
		}

		public void reset() {
			counter = 1;
		}
	}

	public static class SerialDateNextVal implements NextValIface<Date> {
		private Date instancedDate = new Date();
		private Calendar cal = Calendar.getInstance();

		public SerialDateNextVal() {
			cal.setTime(instancedDate);
		}

		// @Override
		public Date nextVal() {
			Date date = cal.getTime();
			cal.add(Calendar.DATE, 1);
			return date;
		}
		public void reset() {
			cal.setTime(instancedDate);
		}
	}

	public static class SerialStringNextVal implements NextValIface<String> {
		//格式化字符串
		private String formatString = "ID-{0}";
		//数字部分的固定长度
		private Integer fixedNumberLength = null;

		private int counter = 1;

		public SerialStringNextVal() {
		}

		public String nextVal() {
			// 字符串的数字部分
			String numberPartString = null;
			if (getFixedNumberLength() == null || getFixedNumberLength() <= 0) {
				numberPartString = Integer.toString(counter++);
			} else {
				numberPartString = String.format("%0" + Integer.toString(getFixedNumberLength()) + "d", counter++);
			}
			return MessageFormat.format(formatString, numberPartString);
		}

		public void reset() {
			counter = 1;
		}

		public String getFormatString() {
			return formatString;
		}

		/**
		 * 设置格式化字符串
		 * @param formatString
		 */
		public void setFormatString(String formatString) {
			this.formatString = formatString;
		}

		public Integer getFixedNumberLength() {
			return fixedNumberLength;
		}

		/**
		 * 设置数字部分的固定长度（默认无固定长度）
		 * @param fixedNumberLength
		 */
		public void setFixedNumberLength(Integer fixedNumberLength) {
			this.fixedNumberLength = fixedNumberLength;
		}
	}

}
