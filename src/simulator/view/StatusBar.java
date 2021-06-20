package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private static final long serialVersionUID = 1L;

	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies

	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));

		_currTime = new JLabel();
		_currTime.setText("Time: ");
		_currTime.setPreferredSize(new Dimension(110,20));
		_currTime.setMinimumSize(new Dimension(110,20));

		_numOfBodies = new JLabel();
		_numOfBodies.setText("Bodies: ");
		_numOfBodies.setPreferredSize(new Dimension(90,20));
		_numOfBodies.setMinimumSize(new Dimension(90,20));

		_currLaws = new JLabel();
		_currLaws.setText("Laws:");
		_currLaws.setMinimumSize(new Dimension(440,20));
		_currLaws.setPreferredSize(new Dimension(440,20));

		this.add(_currTime);
		this.add(_numOfBodies);
		this.add(_currLaws);
	}

	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("Time: " + String.valueOf(time));
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
				_currLaws.setText("Laws: " + String.valueOf(fLawsDesc));
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("Time: " + String.valueOf(time));
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
				_currLaws.setText("Laws: " + String.valueOf(fLawsDesc));
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
			}
		});
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currTime.setText("Time: " + String.valueOf(time));
				_numOfBodies.setText("Bodies: " + String.valueOf(bodies.size()));
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_currLaws.setText("Laws: " + String.valueOf(fLawsDesc));
			}
		});
	}
}