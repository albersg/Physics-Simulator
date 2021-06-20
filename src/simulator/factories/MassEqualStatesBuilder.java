package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{
	
	public MassEqualStatesBuilder() {
		super("masseq", "Comparator based on masses");
	}

	@Override
	public StateComparator createTheInstance(JSONObject info) {
		return new MassEqualStates();
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();

		return data;
	}

}
