package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private List<Builder<T>> _builders;
	private List<JSONObject> _factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = builders;
	}

	@Override
	public T createInstance(JSONObject info) {
		for(Builder<T> b: _builders) {
			if(b.createInstance(info) != null) 
				return b.createInstance(info);
		}
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {
		_factoryElements = new ArrayList<JSONObject>();
		for(Builder<T> b: _builders) {
			_factoryElements.add(b.getBuilderInfo());
		}
		return _factoryElements;
	}
}
