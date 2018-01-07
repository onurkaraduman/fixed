package com.fixed.fix.util;

import quickfix.FieldConvertError;
import quickfix.field.converter.UtcTimestampConverter;

import java.util.Date;

public class FixMessageHelper {
    public static String getTargetCompId(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("56=") + 3;
        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static String getSenderCompId(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("49=") + 3;
        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static String getMessageType(String rawMessage, char delimeter) {
        int beginIndex = rawMessage.indexOf("35=");
        if (beginIndex == -1) {
            return null;
        }

        beginIndex += 3;

        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return rawMessage.substring(beginIndex, endIndex);
    }

    public static Date getSendingTime(String rawMessage, char delimeter) throws FieldConvertError {
        int beginIndex = rawMessage.indexOf("52=");
        if (beginIndex == -1) {
            return null;
        }

        beginIndex += 3;

        int endIndex = rawMessage.indexOf(delimeter, beginIndex);
        return UtcTimestampConverter.convert(rawMessage.substring(beginIndex, endIndex));
    }
}
