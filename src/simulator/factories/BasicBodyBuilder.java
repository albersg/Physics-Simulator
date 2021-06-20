package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body> {
	private final static String _idString = "String that represents each body";
	private final static String _posString = "Vector2D that represents the position of the body";
	private final static String _velString = "Vector2D that represents the velocity of the body";
	private final static String _massString = "Value that represents the mass of the body";

	public BasicBodyBuilder() {
		super("basic", "Objects (that not loss mass) in the simulation that are those that move with the gravity laws");
	}

	@Override
	public Body createTheInstance(JSONObject info) {
		double x, y;
		String id = info.getString("id");
		x = info.getJSONArray("p").getDouble(0);
		y = info.getJSONArray("p").getDouble(1);
		Vector2D p = new Vector2D(x, y);
		x = info.getJSONArray("v").getDouble(0);
		y = info.getJSONArray("v").getDouble(1);
		Vector2D v = new Vector2D(x, y);
		double m = info.getDouble("m");

		return new Body(id, v, p, m);
	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();

		data.put("id", _idString);
		data.put("p", _posString);
		data.put("v", _velString);
		data.put("m", _massString);

		return data;
	}

}
