package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	private double _g = 9.81;
	private Vector2D _c;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		_c = c;
		_g = g;
	}
	
	@Override
	public void apply(List<Body> bs) {
		for(Body b: bs) {
			b.addForce(_c.minus(b.getPosition()).direction().scale(_g*b.getMass()));
		}
	}

	@Override
	public String toString() {
		return "Moving towards " + _c + " with constant aceleration: " + _g;
	}
	

}
