package com.forms;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Clase que extiende de la clase Serializable.
 * 
 * Clase de utilidad para la capa de modelado de datos.
 * 
 * @author Manuel Guerrero del Pino
 * @version 1.0
 */

public class DataBean implements java.io.Serializable {

	private static final String ID_KEY = "IDKEY";
	private static final String VALUE_KEY = "VALUEKEY";

	private Hashtable _data;

	public DataBean() {
		_data = new Hashtable();
	}

	public DataBean(Hashtable data) {
		_data = data;
	}

	public DataBean(HashMap data) {
		_data = new Hashtable();

		Iterator it = data.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			Object value = data.get(key);
			_data.put(key, value);
		}
	}

	public DataBean(String id, String value) {
		_data = new Hashtable();

		this.setId(id);
		this.setValue(value);
	}

	public Object getValue(String key) {
		return _data.get(key);
	}

	public String getStringValue(String key) {
		String value = (String) _data.get(key);
		if (value == null)
			value = "";
		return value;
	}

	public void setValue(String key, Object value) {
		if (value == null) {
			return;
		}
		_data.put(key, value);
	}

	public Hashtable getData() {
		return _data;
	}

	public void setData(Hashtable data) {
		_data = data;
	}

	public void setId(String id) {
		this.setValue(ID_KEY, id);
	}

	public String getId() {
		return this.getStringValue(ID_KEY);
	}

	public String getCode() {
		return getId();
	}

	public void setValue(String value) {
		this.setValue(VALUE_KEY, value);
	}

	public String getValue() {
		return this.getStringValue(VALUE_KEY);
	}

	public String getDescription() {
		return getValue();
	}

	public static ArrayList getDataBeans(ArrayList data) {
		ArrayList beans = new ArrayList();
		for (int i = 0; i < data.size(); i++) {
			HashMap record = (HashMap) data.get(i);
			DataBean bean = new DataBean(record);
			beans.add(bean);
		}

		return beans;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		Enumeration keys = _data.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			Object value = _data.get(key);
			sb.append(key + ":" + value + "\n");
		}
		return sb.toString();
	}

}
