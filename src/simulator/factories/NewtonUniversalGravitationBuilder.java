package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	private static final Double _GDefaultValue = 6.67E-11;
	private static final String _GString = "the gravitational constant (a number)";
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton's law of universal gravitation");
	}

	@Override
	public ForceLaws createTheInstance(JSONObject info) {
		double G;
		if(info.has("G"))
			G = info.getDouble("G");
		else 
			G = _GDefaultValue;
		return new NewtonUniversalGravitation(G);
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();

		data.put("G", _GString);

		return data;
	}

}
