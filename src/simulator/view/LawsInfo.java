package simulator.view;

public class LawsInfo {
	private String _key;
	private String _value;
	private String _desc;
	
	public LawsInfo(String key, String value, String desc) {
		_key = key;
		_value = value;
		_desc = desc;
	}

	public String get_key() {
		return _key;
	}

	public String get_value() {
		return _value;
	}

	public void set_value(String value) {
		_value = value;
	}

	public String get_desc() {
		return _desc;
	}
	
}
