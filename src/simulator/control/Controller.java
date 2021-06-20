package simulator.control;

import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;

public class Controller {
	PhysicsSimulator _ps;
	Factory<Body> _fact;
	Factory<ForceLaws> _force;

	public Controller(PhysicsSimulator ps, Factory<Body> fact, Factory<ForceLaws> force) {
		_ps = ps;
		_fact = fact;
		_force = force;
	}

	public void loadBodies(InputStream in) throws JSONException {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		for (int i = 0; i < jsonInput.getJSONArray("bodies").length(); i++) {
			_ps.addBody(_fact.createInstance(jsonInput.getJSONArray("bodies").getJSONObject(i)));
		}
	}

	public void run(int n, OutputStream out, InputStream expOut, StateComparator cmp) throws NotEqualException {
		JSONObject jsonExpOut = null;

		if (expOut != null) {
			jsonExpOut = new JSONObject(new JSONTokener(expOut));
		}

		if (out == null) {
			out = new OutputStream() {
				@Override
				public void write(int b) throws IOException {
				}
			};
		}

		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(_ps.getState());

		if (jsonExpOut != null) {
			if (!cmp.equal(jsonExpOut.getJSONArray("states").getJSONObject(0), _ps.getState())) {
				throw new NotEqualException(jsonExpOut.getJSONArray("states").getJSONObject(0), _ps.getState(), 0);
			}
		}

		for (int i = 1; i <= n; i++) {
			_ps.advance();
			p.println("," + _ps.getState());
			if (jsonExpOut != null) {
				if (!cmp.equal(_ps.getState(), jsonExpOut.getJSONArray("states").getJSONObject(i))) {
					throw new NotEqualException(jsonExpOut.getJSONArray("states").getJSONObject(i), _ps.getState(), i);
				}
			}

		}
		p.println("]");
		p.println("}");
	}

	public void run(int n) {
		for (int i = 0; i < n; i++)
			_ps.advance();
	}

	public void reset() {
		_ps.reset();
	}

	public void setDeltaTime(double dt) {
		_ps.setDeltaTime(dt);
	}

	public void addObserver(SimulatorObserver o) {
		_ps.addObserver(o);
	}

	public List<JSONObject> getForceLawsInfo() {
		return _force.getInfo();
	}

	public void setForceLaws(JSONObject info) {
		ForceLaws aux = _force.createInstance(info);
		_ps.setForceLaws(aux);
	}
}
