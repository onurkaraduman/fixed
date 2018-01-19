package com.fixed.fix.model;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.field.MsgType;

import java.util.ArrayList;
import java.util.List;

public class FixGroup extends FixField {

	private final List<FixField> fields;

	public FixGroup(MsgType msgType, Field field, DataDictionary dataDictionary) {
		super(msgType, field, dataDictionary);
		fields = new ArrayList<>();
	}

	public void addField(FixField logField) {
		fields.add(logField);
	}

	public List<FixField> getFields() {
		return fields;
	}
}
