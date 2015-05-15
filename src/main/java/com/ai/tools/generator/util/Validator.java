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

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <a href="Validator.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alysa Carver
 *
 */
public class Validator {
    public static boolean equals(String s1, String s2) {
        if ((s1 == null) && (s2 == null)) {
            return true;
        } else if ((s1 == null) || (s2 == null)) {
            return false;
        } else {
            return s1.equals(s2);
        }
    }

    public static boolean isAddress(String address) {
        if (isNull(address)) {
            return false;
        }

        String[] tokens = address.split(StringPool.AT);

        if (tokens.length != 2) {
            return false;
        }

        for (String token : tokens) {
            for (char c : token.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if c is a letter between a-z and A-Z.
     *
     * @param                c a character
     * @return                true if c is a letter between a-z and A-Z
     */
    public static boolean isChar(char c) {
        int x = c;

        if ((x >= _CHAR_BEGIN) && (x <= _CHAR_END)) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if s is a string of letters that are between a-z and A-Z.
     *
     * @param                s a string
     * @return                true if s is a string of letters that are between a-z and
     *                                A-Z
     */
    public static boolean isChar(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isChar(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isDate(int month, int day, int year) {
        return isGregorianDate(month, day, year);
    }

    /**
     * Returns true if c is a digit between 0 and 9.
     *
     * @param                c a character
     * @return                true if c is a digit between 0 and 9
     */
    public static boolean isDigit(char c) {
        int x = c;

        if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
            return true;
        }

        return false;
    }

    /**
     * Returns true if s is a string of letters that are between 0 and 9.
     *
     * @param                s a string
     * @return                true if s is a string of letters that are between 0 and 9
     */
    public static boolean isDigit(String s) {
        if (isNull(s)) {
            return false;
        }

        for (char c : s.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isDomain(String domainName) {
        // See RFC-1034 (section 3), RFC-1123 (section 2.1), and RFC-952
        // (section B. Lexical grammar)
        if (isNull(domainName)) {
            return false;
        }

        if (domainName.length() > 255) {
            return false;
        }

        String[] domainNameArray = StringUtil.split(domainName, StringPool.PERIOD);

        for (String domainNamePart : domainNameArray) {
            char[] domainNamePartCharArray = domainNamePart.toCharArray();

            for (int i = 0; i < domainNamePartCharArray.length; i++) {
                char c = domainNamePartCharArray[i];

                if ((i == 0) && (c == CharPool.DASH)) {
                    return false;
                } else if ((i == (domainNamePartCharArray.length - 1)) && (c == CharPool.DASH)) {
                    return false;
                } else if ((!isChar(c)) && (!isDigit(c)) && (c != CharPool.DASH)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isEmailAddressSpecialChar(char c) {
        // LEP-1445
        for (int i = 0; i < _EMAIL_ADDRESS_SPECIAL_CHAR.length; i++) {
            if (c == _EMAIL_ADDRESS_SPECIAL_CHAR[i]) {
                return true;
            }
        }

        return false;
    }

    public static boolean isGregorianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {
                31,
                28,
                31,
                30,
                31,
                30,
                31,
                31,
                30,
                31,
                30,
                31
            };

        if (month == 1) {
            int febMax = 28;

            if ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0)) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    public static boolean isHex(String s) {
        if (isNull(s)) {
            return false;
        }

        return true;
    }

    public static boolean isHTML(String s) {
        if (isNull(s)) {
            return false;
        }

        if (((s.indexOf("<html>") != -1) || (s.indexOf("<HTML>") != -1)) &&
                ((s.indexOf("</html>") != -1) || (s.indexOf("</HTML>") != -1))) {
            return true;
        }

        return false;
    }

    public static boolean isIPAddress(String ipAddress) {
        Matcher matcher = _ipAddressPattern.matcher(ipAddress);

        return matcher.matches();
    }

    public static boolean isJulianDate(int month, int day, int year) {
        if ((month < 0) || (month > 11)) {
            return false;
        }

        int[] months = {
                31,
                28,
                31,
                30,
                31,
                30,
                31,
                31,
                30,
                31,
                30,
                31
            };

        if (month == 1) {
            int febMax = 28;

            if ((year % 4) == 0) {
                febMax = 29;
            }

            if ((day < 1) || (day > febMax)) {
                return false;
            }
        } else if ((day < 1) || (day > months[month])) {
            return false;
        }

        return true;
    }

    public static boolean isLUHN(String number) {
        if (number == null) {
            return false;
        }

        number = StringUtil.reverse(number);

        int total = 0;

        for (int i = 0; i < number.length(); i++) {
            int x = 0;

            if (((i + 1) % 2) == 0) {
                x = Integer.parseInt(number.substring(i, i + 1)) * 2;

                if (x >= 10) {
                    String s = String.valueOf(x);

                    x = Integer.parseInt(s.substring(0, 1)) + Integer.parseInt(s.substring(1, 2));
                }
            } else {
                x = Integer.parseInt(number.substring(i, i + 1));
            }

            total = total + x;
        }

        if ((total % 10) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isName(String name) {
        if (isNull(name)) {
            return false;
        }

        for (char c : name.trim().toCharArray()) {
            if (((!isChar(c)) && (!Character.isWhitespace(c))) || (c == CharPool.COMMA)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    public static boolean isNotNull(Long l) {
        return !isNull(l);
    }

    public static boolean isNotNull(String s) {
        return !isNull(s);
    }

    public static boolean isNotNull(Object[] array) {
        return !isNull(array);
    }

    public static boolean isNull(Object obj) {
        if (obj instanceof Long) {
            return isNull((Long) obj);
        } else if (obj instanceof String) {
            return isNull((String) obj);
        } else if (obj == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(Long l) {
        if ((l == null) || (l.longValue() == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNull(String s) {
        if (s == null) {
            return true;
        }

        s = s.trim();

        if ((s.length() == 0) || (s.equals(StringPool.NULL))) {
            return true;
        }

        return false;
    }

    public static boolean isNull(Object[] array) {
        if ((array == null) || (array.length == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNumber(String number) {
        if (isNull(number)) {
            return false;
        }

        for (char c : number.toCharArray()) {
            if (!isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPassword(String password) {
        if (isNull(password)) {
            return false;
        }

        if (password.length() < 4) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (!isChar(c) && !isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPhoneNumber(String phoneNumber) {
        return isNumber(StringUtil.extractDigits(phoneNumber));
    }

    public static boolean isVariableTerm(String s) {
        if (s.startsWith(_VARIABLE_TERM_BEGIN) && s.endsWith(_VARIABLE_TERM_END)) {
            return true;
        } else {
            return false;
        }
    }

    private static final int _CHAR_BEGIN = 65;
    private static final int _CHAR_END = 122;
    private static final int _DIGIT_BEGIN = 48;
    private static final int _DIGIT_END = 57;
    private static final char[] _EMAIL_ADDRESS_SPECIAL_CHAR = new char[] {
            '.',
            '!',
            '#',
            '$',
            '%',
            '&',
            '\'',
            '*',
            '+',
            '-',
            '/',
            '=',
            '?',
            '^',
            '_',
            '`',
            '{',
            '|',
            '}',
            '~'
        };
    private static final String _VARIABLE_TERM_BEGIN = "[$";
    private static final String _VARIABLE_TERM_END = "$]";
    private static final Logger _log = Logger.getLogger(Validator.class.getName());
    private static Pattern _ipAddressPattern = Pattern.compile("\\b" +
            "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
            "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
            "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." +
            "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" + "\\b");
}
