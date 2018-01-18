package com.fixed.fix.model;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.field.MsgType;

import java.util.ArrayList;
import java.util.List;

public class LogGroup extends LogField {

	private final List<LogField> fields;

	public LogGroup(MsgType msgType, Field field, DataDictionary dataDictionary) {
		super(msgType, field, dataDictionary);
		fields = new ArrayList<>();
	}

	public void addField(LogField logField) {
		fields.add(logField);
	}

	public List<LogField> getFields() {
		return fields;
	}
}
