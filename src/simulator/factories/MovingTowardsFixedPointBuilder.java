package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private static final Double _gDefaultValue = 9.81;
	private static final String _centerString = "the point towards which bodies move (a json list of 2 numbers, e.g., [100.0,50.0])";
	private static final String _gString = "the length of the acceleration vector (a number)";

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards a fixed point");
	}
	
	@Override
	public ForceLaws createTheInstance(JSONObject info) {
		Vector2D c;
		double g;
		if (info.has("c"))
			c = new Vector2D(info.getJSONArray("c").getDouble(0), info.getJSONArray("c").getDouble(1));
		else
			c = new Vector2D();
		
		if(info.has("g"))
			g = info.getDouble("g");
		else
			g = _gDefaultValue;
		
		return new MovingTowardsFixedPoint(c,g);
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();

		data.put("c", _centerString);
		data.put("g", _gString);

		return data;
	}

}
