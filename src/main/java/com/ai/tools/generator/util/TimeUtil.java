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

import java.text.SimpleDateFormat;

import java.util.Date;


/**
 * <a href="Time.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TimeUtil {
    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;
    public static final String RFC822_FORMAT = "EEE, dd MMM yyyy HH:mm:ss Z";
    public static final String TIMESTAMP_FORMAT = "yyyyMMddkkmmssSSS";
    public static final String TIMESTAMP_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String SHORT_TIMESTAMP_FORMAT = "yyyyMMddkkmm";

    public static String getDescription(long milliseconds) {
        String s = "";

        int    x = 0;

        if ((milliseconds % WEEK) == 0) {
            x = (int) (milliseconds / WEEK);

            s = x + " Week";
        } else if ((milliseconds % DAY) == 0) {
            x = (int) (milliseconds / DAY);

            s = x + " Day";
        } else if ((milliseconds % HOUR) == 0) {
            x = (int) (milliseconds / HOUR);

            s = x + " Hour";
        } else if ((milliseconds % MINUTE) == 0) {
            x = (int) (milliseconds / MINUTE);

            s = x + " Minute";
        } else if ((milliseconds % SECOND) == 0) {
            x = (int) (milliseconds / SECOND);

            s = x + " Second";
        }

        if (x > 1) {
            s += "s";
        }

        return s;
    }

    public static String getRFC822() {
        return getRFC822(new Date());
    }

    public static String getRFC822(Date date) {
        return getSimpleDate(date, RFC822_FORMAT);
    }

    public static String getShortTimestamp() {
        return getShortTimestamp(new Date());
    }

    public static String getShortTimestamp(Date date) {
        return getSimpleDate(date, SHORT_TIMESTAMP_FORMAT);
    }

    public static String getSimpleDate(Date date, String format) {
        String s = StringPool.BLANK;

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);

            s = sdf.format(date);
        }

        return s;
    }

    public static String getTimestamp() {
        return getTimestamp(new Date());
    }

    public static String getTimestamp(Date date) {
        return getSimpleDate(date, TIMESTAMP_FORMAT_DEFAULT);
    }
}
