package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	private double _realTime;
	private ForceLaws _force;
	private double _currentTime;
	private List<Body> _listOfBodies;
	private List<SimulatorObserver> _listOfObservers;

	public PhysicsSimulator(double realTime, ForceLaws force) throws IllegalArgumentException {
		if (realTime <= 0)
			throw new IllegalArgumentException("Time is not valid");
		else
			_realTime = realTime;
		if (force == null)
			throw new IllegalArgumentException("Force is null");
		else
			_force = force;

		_listOfBodies = new ArrayList<Body>();
		_listOfObservers = new ArrayList<SimulatorObserver>();
	}

	public void advance() {
		for (Body b : _listOfBodies) {
			b.resetForce();
		}

		_force.apply(_listOfBodies);

		for (Body b : _listOfBodies) {
			b.move(_realTime);
		}
		_currentTime += _realTime;

		for (SimulatorObserver o : _listOfObservers)
			o.onAdvance(_listOfBodies, _currentTime);
	}

	public void addBody(Body b) throws IllegalArgumentException {
		if (!_listOfBodies.contains(b))
			_listOfBodies.add(b);

		for (SimulatorObserver o : _listOfObservers)
			o.onBodyAdded(_listOfBodies, b);
	}

	public JSONObject getState() {
		JSONObject state = new JSONObject();

		JSONArray JA = new JSONArray();
		for (Body b : _listOfBodies) {
			JA.put(b.getState());
		}

		state.put("time", _currentTime);
		state.put("bodies", JA);

		return state;
	}

	public void reset() {
		_listOfBodies.clear();
		_currentTime = 0.0;

		for (SimulatorObserver o : _listOfObservers)
			o.onReset(_listOfBodies, _currentTime, _realTime, _force.toString());

	}

	public void setDeltaTime(double dt) {
		if (dt < 0.0)
			throw new IllegalArgumentException("Delta-Time not valid");
		_realTime = dt;

		for (SimulatorObserver o : _listOfObservers)
			o.onDeltaTimeChanged(_realTime);
	}

	public void setForceLaws(ForceLaws forceLaws) {
		if (forceLaws == null)
			throw new IllegalArgumentException("Force Law not valid");
		_force = forceLaws;

		for (SimulatorObserver o : _listOfObservers)
			o.onForceLawsChanged(_force.toString());
	}

	public void addObserver(SimulatorObserver o) {
		if (!_listOfObservers.contains(o))
			_listOfObservers.add(o);
		o.onRegister(_listOfBodies, _currentTime, _realTime, _force.toString());
	}

	public String toString() {
		return getState().toString();
	}

}
