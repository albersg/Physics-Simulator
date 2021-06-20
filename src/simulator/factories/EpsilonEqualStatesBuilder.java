package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	private static final Double _defaultCenter = 0.0;
	private static final String _epsString = "the value that we use to comparate the bodies in the comparator mod. epsilon";

	public EpsilonEqualStatesBuilder() {
		super("epseq", "Comparator of states mod. epsilon");
	}
	
	@Override
	public StateComparator createTheInstance(JSONObject info) {
		double eps;
		if(info.has("eps"))
			eps = info.getDouble("eps");
		else
			eps = _defaultCenter;
		return new EpsilonEqualStates(eps);
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		
		data.put("eps", _epsString);

		return data;
	}

}
