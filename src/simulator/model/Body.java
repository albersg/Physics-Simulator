package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	protected String _id;
	protected Vector2D _v;
	protected Vector2D _f;
	protected Vector2D _p;
	protected double _mass;

	public Body() {
	}

	public Body(String id, Vector2D v, Vector2D p, double mass) {
		_id = id;
		_v = v;
		_p = p;
		_mass = mass;
		_f = new Vector2D();
	}

	public String getId() {
		return _id;
	}

	public Vector2D getVelocity() {
		return _v;
	}

	public Vector2D getForce() {
		return _f;
	}

	public Vector2D getPosition() {
		return _p;
	}

	public double getMass() {
		return _mass;
	}

	void addForce(Vector2D f) {
		_f = _f.plus(f);
	}

	void resetForce() {
		_f = new Vector2D();
	}

	void move(double t) {
		Vector2D aceleration;
		if (_mass > 0) {
			aceleration = _f.scale(1.0 / _mass);
		} else
			aceleration = new Vector2D();

		_p = _p.plus(_v.scale(t).plus(aceleration.scale(0.5 * t * t)));
		_v = _v.plus(aceleration.scale(t));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Body other = (Body) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	public JSONObject getState() {
		JSONObject state = new JSONObject();
		state.put("id", _id);
		state.put("m", _mass);
		state.put("p", _p.asJSONArray());
		state.put("v", _v.asJSONArray());
		state.put("f", _f.asJSONArray());

		return state;
	}

	public String toString() {
		return getState().toString();
	}
}
