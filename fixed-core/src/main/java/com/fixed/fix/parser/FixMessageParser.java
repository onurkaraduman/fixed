package com.fixed.fix.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.fixed.fix.source.Source;

public class FixMessageParser {

	public static final String SOH_STRING = "\u0001";

	private Source source;

	public FixMessageParser(Source source) {
		this.source = source;
	}

	public List<String> parse() throws IOException {
		BufferedReader reader = new BufferedReader(source.getReader());
		List<String> fixMessages = new ArrayList<>();

		String line;
		while ((line = reader.readLine()) != null) {
			String rawMessage = extractMessage(line, reader);
			if (rawMessage != null) {
				fixMessages.add(rawMessage);
			}
		}
		return fixMessages;
	}

	private String extractMessage(String line, BufferedReader reader) throws IOException {
		int messageStartIndex = line.indexOf(FixMessageConstant.MESSAGE_START);
		if (messageStartIndex == -1) {
			return null;
		}

		String delimeter = getDelimeter(line);
		while (delimeter == null) {
			String nextLine = reader.readLine();
			if (nextLine == null) {
				break;
			}

			line += nextLine;
			delimeter = getDelimeter(line);
		}

		int checkSumFieldIndex = line.indexOf(delimeter + "10=");
		while (checkSumFieldIndex == -1) {
			String nextLine = reader.readLine();
			if (nextLine == null) {
				break;
			}

			line += nextLine;
			checkSumFieldIndex = line.indexOf(delimeter + "10=");
		}

		if (checkSumFieldIndex != -1) {
			line = correctDelimeter(line, delimeter);
			if (!line.endsWith(SOH_STRING)) {
				line += SOH_STRING;
			}
		}
		return line;
	}

	private String getDelimeter(String line) {
		int endIndex = line.indexOf("9=");
		if (endIndex == -1) {
			return null;
		}
		return line.substring(endIndex - 1, endIndex);

	}

	private String correctDelimeter(String line, String delimeter) {
		if (!delimeter.equals(SOH_STRING)) {
			if (delimeter.length() == 1) {
				line = line.replaceAll("\\" + delimeter, SOH_STRING);
			} else {
				line = line.replaceAll(delimeter, SOH_STRING);
			}
		}
		return line;
	}
}
