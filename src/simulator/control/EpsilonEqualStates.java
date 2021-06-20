package simulator.control;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class EpsilonEqualStates implements StateComparator {
	private double _eps;

	public EpsilonEqualStates(double eps) {
		_eps = eps;
	}

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if (Math.abs(s1.getDouble("time") - s2.getDouble("time")) <= _eps && s1.length() == s2.length()) {
			for (int i = 0; i < s1.length(); i++) {
				Vector2D v1, v2;
				JSONObject b1 = s1.getJSONArray("bodies").getJSONObject(i),
						b2 = s2.getJSONArray("bodies").getJSONObject(i);
				if (!(b1.getString("id").equals(b2.getString("id")))) {
					return false;
				}
				v1 = new Vector2D(b1.getJSONArray("p").getDouble(0), b1.getJSONArray("p").getDouble(1));
				v2 = new Vector2D(b2.getJSONArray("p").getDouble(0), b2.getJSONArray("p").getDouble(1));
				if (v1.distanceTo(v2) > _eps)
					return false;

				v1 = new Vector2D(b1.getJSONArray("v").getDouble(0), b1.getJSONArray("v").getDouble(1));
				v2 = new Vector2D(b2.getJSONArray("v").getDouble(0), b2.getJSONArray("v").getDouble(1));
				if (v1.distanceTo(v2) > _eps)
					return false;

				v1 = new Vector2D(b1.getJSONArray("f").getDouble(0), b1.getJSONArray("f").getDouble(1));
				v2 = new Vector2D(b2.getJSONArray("f").getDouble(0), b2.getJSONArray("f").getDouble(1));
				if (v1.distanceTo(v2) > _eps)
					return false;
			}
		} else
			return false;

		return true;
	}
}
