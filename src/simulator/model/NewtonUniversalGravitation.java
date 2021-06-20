package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {
	protected double _G;

	public NewtonUniversalGravitation(double G) {
		_G = G;
	}

	// Se podría hacer en vez de calcular todas las combinaciones, coger todos los
	// pares posibles distintos
	// y calcular la fuerza sobre uno y al otro sumarle la misma fuerza pero en
	// negativo, pero así
	// nos da problemas con la coma flotante para luego comparar.
	public void apply(List<Body> bs) {
		for (Body b1 : bs) {
			for (Body b2 : bs) {
				Vector2D Fij = force(b1, b2);
				b1.addForce(Fij);
			}
		}
	}

	private Vector2D force(Body a, Body b) {
		Vector2D delta = b.getPosition().minus(a.getPosition());
		double dist = delta.magnitude();
		double magnitude = dist > 0 ? (_G * a.getMass() * b.getMass()) / (dist * dist) : 0.0;
		return delta.direction().scale(magnitude);
	}
	
	public String toString() {
		return "Newton's Universal Gravitation with G = " + _G;
	}

}
