package net.mawenjian.code.listsimulator.nextval;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 随机值生成器
 * 
 * @author mawenjian@gmail.com
 *
 */
public class RandomNextVal {

	private static Random r = new Random();

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
			throw new InvalidParameterException(String.format("type of [%s] is not supported by RandomNextVal, please implements NextVal interface by yourself.", clazz.getName()));
		}

		return nextValIface;
	}

	public static NextValIface<Boolean> newBooleanInstance() {
		NextValIface<Boolean> nextBoolean = new RandomBooleanNextVal();
		return nextBoolean;
	}

	public static NextValIface<Byte> newByteInstance() {
		NextValIface<Byte> nextByte = new RandomByteNextVal();
		return nextByte;
	}

	public static NextValIface<Character> newCharacterInstance() {
		NextValIface<Character> nextCharacter = new RandomCharacterNextVal();
		return nextCharacter;
	}
	
	public static NextValIface<Short> newShortInstance() {
		NextValIface<Short> nextShort = new RandomShortNextVal();
		return nextShort;
	}

	public static NextValIface<Integer> newIntegerInstance() {
		NextValIface<Integer> nextInteger = new RandomIntegerNextVal();
		return nextInteger;
	}

	public static NextValIface<Long> newLongInstance() {
		NextValIface<Long> nextLong = new RandomLongNextVal();
		return nextLong;
	}

	public static NextValIface<Float> newFloatInstance() {
		NextValIface<Float> nextFloat = new RandomFloatNextVal();
		return nextFloat;
	}

	public static NextValIface<Double> newDoubleInstance() {
		NextValIface<Double> nextDouble = new RandomDoubleNextVal();
		return nextDouble;
	}

	public static NextValIface<BigDecimal> newBigDecimalInstance() {
		NextValIface<BigDecimal> nextBigDecimal = new RandomBigDecimalNextVal();
		return nextBigDecimal;
	}

	public static NextValIface<Date> newDateInstance() {
		NextValIface<Date> nextDate = new RandomDateNextVal();
		return nextDate;
	}

	public static NextValIface<String> newStringInstance() {
		NextValIface<String> nextString = new RandomStringNextVal();
		return nextString;
	}

	public static class RandomBooleanNextVal implements NextValIface<Boolean> {
		// @Override
		public Boolean nextVal() {
			Boolean bool = r.nextBoolean();
			return bool;
		}

		public void reset() {
			
		}
	}

	public static class RandomByteNextVal implements NextValIface<Byte> {
		// @Override
		public Byte nextVal() {
			byte aByte = (byte) r.nextInt();
			return aByte;
		}

		public void reset() {
			
		}
	}

	public static class RandomCharacterNextVal implements NextValIface<Character> {
		// @Override
		public Character nextVal() {
			char rndChar = (char) r.nextInt();
			return rndChar;
		}

		public void reset() {
			
		}
	}
	
	public static class RandomShortNextVal implements NextValIface<Short> {
		// @Override
		public Short nextVal() {
			Short rndShort = (short) r.nextInt();
			return rndShort;
		}
		
		public void reset() {
			
		}
	}

	public static class RandomIntegerNextVal implements NextValIface<Integer> {
		// @Override
		public Integer nextVal() {
			Integer rndInt = r.nextInt();
			return rndInt;
		}

		public void reset() {
			
		}
	}

	public static class RandomLongNextVal implements NextValIface<Long> {
		// @Override
		public Long nextVal() {
			Long rndLong = r.nextLong();
			return rndLong;
		}

		public void reset() {
			
		}
	}

	public static class RandomFloatNextVal implements NextValIface<Float> {
		// @Override
		public Float nextVal() {
			Float rndFloat = r.nextFloat();
			return rndFloat;
		}

		public void reset() {
			
		}
	}

	public static class RandomDoubleNextVal implements NextValIface<Double> {
		// @Override
		public Double nextVal() {
			Double rndDbl = r.nextDouble();
			return rndDbl;
		}

		public void reset() {
			
		}
	}

	public static class RandomBigDecimalNextVal implements NextValIface<BigDecimal> {
		public BigDecimal nextVal() {
			BigDecimal rndBd = new BigDecimal(r.nextDouble());
			return rndBd;
		}

		public void reset() {
			
		}
	}

	public static class RandomDateNextVal implements NextValIface<Date> {
		// @Override
		public Date nextVal() {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(r.nextLong());
			return cal.getTime();
		}

		public void reset() {
			
		}
	}

	public static class RandomStringNextVal implements NextValIface<String> {
		// @Override
		public String nextVal() {
			String timeHexString = UUID.randomUUID().toString();
			return timeHexString;
		}

		public void reset() {
			
		}

	}

}
