package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends Body {
	private double _lossFactor;
	private double _lossFrequency;
	private double _accumulatedTime;

	public MassLosingBody(String id, Vector2D v, Vector2D p, double mass, double lossFactor, double lossFrequency) {
		super(id, v, p, mass);
		_lossFactor = lossFactor;
		_lossFrequency = lossFrequency;
		_accumulatedTime = 0.0;
	}

	void move(double t) {
		super.move(t);
		_accumulatedTime += t;
		if (_accumulatedTime >= _lossFrequency) {
			_accumulatedTime = 0.0;
			_mass = _mass * (1 - _lossFactor);
		}
	}
}