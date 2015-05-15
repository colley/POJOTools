/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ai.tools.generator.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * <a href="ArrayUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ArrayUtil {
    public static Boolean[] append(Boolean[] array, Boolean obj) {
        Boolean[] newArray = new Boolean[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Double[] append(Double[] array, Double obj) {
        Double[] newArray = new Double[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Float[] append(Float[] array, Float obj) {
        Float[] newArray = new Float[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Integer[] append(Integer[] array, Integer obj) {
        Integer[] newArray = new Integer[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Long[] append(Long[] array, Long obj) {
        Long[] newArray = new Long[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Object[] append(Object[] array, Object obj) {
        Object[] newArray = new Object[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Object[][] append(Object[][] array, Object[] obj) {
        Object[][] newArray = new Object[array.length + 1][];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Short[] append(Short[] array, Short obj) {
        Short[] newArray = new Short[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static String[] append(String[] array, String obj) {
        String[] newArray = new String[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static String[][] append(String[][] array, String[] obj) {
        String[][] newArray = new String[array.length + 1][];

        System.arraycopy(array, 0, newArray, 0, array.length);

        newArray[newArray.length - 1] = obj;

        return newArray;
    }

    public static Boolean[] append(Boolean[] array1, Boolean[] array2) {
        Boolean[] newArray = new Boolean[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Double[] append(Double[] array1, Double[] array2) {
        Double[] newArray = new Double[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Float[] append(Float[] array1, Float[] array2) {
        Float[] newArray = new Float[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Integer[] append(Integer[] array1, Integer[] array2) {
        Integer[] newArray = new Integer[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Long[] append(Long[] array1, Long[] array2) {
        Long[] newArray = new Long[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Object[] append(Object[] array1, Object[] array2) {
        Object[] newArray = new Object[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Object[][] append(Object[][] array1, Object[][] array2) {
        Object[][] newArray = new Object[array1.length + array2.length][];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static Short[] append(Short[] array1, Short[] array2) {
        Short[] newArray = new Short[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static String[] append(String[] array1, String[] array2) {
        String[] newArray = new String[array1.length + array2.length];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static String[][] append(String[][] array1, String[][] array2) {
        String[][] newArray = new String[array1.length + array2.length][];

        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);

        return newArray;
    }

    public static void combine(Object[] array1, Object[] array2, Object[] combinedArray) {
        System.arraycopy(array1, 0, combinedArray, 0, array1.length);

        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
    }

    public static boolean contains(boolean[] array, boolean value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(char[] array, char value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(double[] array, double value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(long[] array, long value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(int[] array, int value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(short[] array, short value) {
        if ((array == null) || (array.length == 0)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value == array[i]) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean contains(Object[] array, Object value) {
        if ((array == null) || (array.length == 0) || (value == null)) {
            return false;
        } else {
            for (int i = 0; i < array.length; i++) {
                if (value.equals(array[i])) {
                    return true;
                }
            }

            return false;
        }
    }

    public static String[] distinct(String[] array) {
        return distinct(array, null);
    }

    public static String[] distinct(String[] array, Comparator<String> comparator) {
        if ((array == null) || (array.length == 0)) {
            return array;
        }

        Set<String> set = null;

        if (comparator == null) {
            set = new TreeSet<String>();
        } else {
            set = new TreeSet<String>(comparator);
        }

        for (int i = 0; i < array.length; i++) {
            String s = array[i];

            if (!set.contains(s)) {
                set.add(s);
            }
        }

        return set.toArray(new String[set.size()]);
    }

    public static int getLength(Object[] array) {
        if (array == null) {
            return 0;
        } else {
            return array.length;
        }
    }

    public static Object getValue(Object[] array, int pos) {
        if ((array == null) || (array.length <= pos)) {
            return null;
        } else {
            return array[pos];
        }
    }

    public static String[] removeByPrefix(String[] array, String prefix) {
        List<String> list = new ArrayList<String>();

        for (int i = 0; i < array.length; i++) {
            String s = array[i];

            if (!s.startsWith(prefix)) {
                list.add(s);
            }
        }

        return list.toArray(new String[list.size()]);
    }

    public static Boolean[] toArray(boolean[] array) {
        Boolean[] newArray = new Boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = Boolean.valueOf(array[i]);
        }

        return newArray;
    }

    public static Double[] toArray(double[] array) {
        Double[] newArray = new Double[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Double(array[i]);
        }

        return newArray;
    }

    public static Float[] toArray(float[] array) {
        Float[] newArray = new Float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Float(array[i]);
        }

        return newArray;
    }

    public static Integer[] toArray(int[] array) {
        Integer[] newArray = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Integer(array[i]);
        }

        return newArray;
    }

    public static Long[] toArray(long[] array) {
        Long[] newArray = new Long[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Long(array[i]);
        }

        return newArray;
    }

    public static Short[] toArray(short[] array) {
        Short[] newArray = new Short[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = new Short(array[i]);
        }

        return newArray;
    }

    public static boolean[] toArray(Boolean[] array) {
        boolean[] newArray = new boolean[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].booleanValue();
        }

        return newArray;
    }

    public static double[] toArray(Double[] array) {
        double[] newArray = new double[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].doubleValue();
        }

        return newArray;
    }

    public static float[] toArray(Float[] array) {
        float[] newArray = new float[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].floatValue();
        }

        return newArray;
    }

    public static int[] toArray(Integer[] array) {
        int[] newArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].intValue();
        }

        return newArray;
    }

    public static long[] toArray(Long[] array) {
        long[] newArray = new long[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].longValue();
        }

        return newArray;
    }

    public static short[] toArray(Short[] array) {
        short[] newArray = new short[array.length];

        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i].shortValue();
        }

        return newArray;
    }

    public static Boolean[] toArray(List<Boolean> list) {
        Boolean[] array = new Boolean[list.size()];

        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    //	public static Double[] toArray(List<Double> list) {
    //		Double[] array = new Double[list.size()];
    //
    //		for (int i = 0; i < list.size(); i++) {
    //			array[i] = list.get(i);
    //		}
    //
    //		return array;
    //	}
    //
    //	public static Float[] toArray(List<Float> list) {
    //		Float[] array = new Float[list.size()];
    //
    //		for (int i = 0; i < list.size(); i++) {
    //			array[i] = list.get(i);
    //		}
    //
    //		return array;
    //	}
    //
    //	public static Integer[] toArray(List<Integer> list) {
    //		Integer[] array = new Integer[list.size()];
    //
    //		for (int i = 0; i < list.size(); i++) {
    //			array[i] = list.get(i);
    //		}
    //
    //		return array;
    //	}
    //
    //	public static Long[] toArray(List<Long> list) {
    //		Long[] array = new Long[list.size()];
    //
    //		for (int i = 0; i < list.size(); i++) {
    //			array[i] = list.get(i);
    //		}
    //
    //		return array;
    //	}
    //
    //	public static Short[] toArray(List<Short> list) {
    //		Short[] array = new Short[list.size()];
    //
    //		for (int i = 0; i < list.size(); i++) {
    //			array[i] = list.get(i);
    //		}
    //
    //		return array;
    //	}
}
