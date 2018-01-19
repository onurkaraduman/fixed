package com.fixed.fix.model;

import quickfix.DataDictionary;
import quickfix.Field;
import quickfix.FieldType;
import quickfix.field.MsgType;

import java.util.ArrayList;
import java.util.List;

public class FixField {

	private final DataDictionary dataDictionary;
	private final Field field;

	private final FieldType type;
	private final int tag;
	private final String name;
	private final String valueName;
	private String value;
	private final boolean required;
	private final boolean header;
	private final boolean trailer;
	private List<FixGroup> groups = new ArrayList<>();

	public static FixField creteLogField(MsgType msgType, Field field, DataDictionary dataDictionary) {
		return new FixField(msgType, field, dataDictionary);
	}

	protected FixField(MsgType msgType, Field field, DataDictionary dataDictionary) {
		this.dataDictionary = dataDictionary;
		this.field = field;

		final String messageTypeString = msgType.getValue();
		final int fieldTag = field.getTag();
		tag = fieldTag;

		type = dataDictionary.getFieldType(fieldTag);
		name = dataDictionary.getFieldName(fieldTag);
		valueName = dataDictionary.getValueName(fieldTag, field.getObject().toString());
		value = field.getObject().toString();
		required = dataDictionary.isRequiredField(messageTypeString, fieldTag);

		header = dataDictionary.isHeaderField(fieldTag);
		trailer = !header && dataDictionary.isTrailerField(fieldTag);
	}

	public void addGroup(FixGroup logGroup) {
		if (groups == null) {
			groups = new ArrayList<>();
		}
		groups.add(logGroup);
	}

	public DataDictionary getDataDictionary() {
		return dataDictionary;
	}

	public Field getField() {
		return field;
	}

	public FieldType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getValueName() {
		return valueName;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isHeader() {
		return header;
	}

	public boolean isTrailer() {
		return trailer;
	}

	public List<FixGroup> getGroups() {
		return groups;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getTag() {
		return tag;
	}
}
