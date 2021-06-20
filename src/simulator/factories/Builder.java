package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	private String _typeTag;
	private String _desc;
	
	public Builder(String typeTag, String desc) {
		_typeTag = typeTag;
		_desc = desc;
	}
	
	public T createInstance(JSONObject info) {
		T aux = null;
		if(_typeTag != null && _typeTag.equals(info.getString("type"))) {
			aux = createTheInstance(info.getJSONObject("data"));
		}
		return aux;
	}
	
	public abstract T createTheInstance(JSONObject info);
	
	public JSONObject getBuilderInfo() {
		JSONObject info = new JSONObject();
		info.put("type", _typeTag);
		info.put("data", createData());
		info.put("desc", _desc);
		return info;
	}
	
	public abstract JSONObject createData();
}
