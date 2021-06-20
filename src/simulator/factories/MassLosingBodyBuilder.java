package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;

public class MassLosingBodyBuilder extends Builder<Body> {
	private final static String _idString = "String that represents each body";
	private final static String _posString = "Vector2D that represents the position of the body";
	private final static String _velString = "Vector2D that represents the velocity of the body";
	private final static String _massString = "Double that represents the mass of the body";
	private final static String _freqString = "Double that represents the periodicity of lossing mass";
	private final static String _factorString = "Double that represents the factor of lossing mass";

	public MassLosingBodyBuilder() {
		super("mlb","Mass losing body");
	}
	
	@Override
	public Body createTheInstance(JSONObject info) {
		double x, y, freq, factor;
		String id = info.getString("id");
		x = info.getJSONArray("p").getDouble(0);
		y = info.getJSONArray("p").getDouble(1);
		Vector2D p = new Vector2D(x, y);
		x = info.getJSONArray("v").getDouble(0);
		y = info.getJSONArray("v").getDouble(1);
		Vector2D v = new Vector2D(x, y);
		double m = info.getDouble("m");
		freq = info.getDouble("freq");
		factor = info.getDouble("factor");
		return new MassLosingBody(id, v, p, m, factor, freq);

	}

	@Override
	public JSONObject createData() {
		JSONObject data = new JSONObject();

		data.put("id", _idString);
		data.put("p", _posString);
		data.put("v", _velString);
		data.put("m", _massString);
		data.put("freq", _freqString);
		data.put("factor", _factorString);
		
		return data;
	}

}
