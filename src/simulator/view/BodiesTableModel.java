package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	private static final long serialVersionUID = 1L;
	
	private List<Body> _bodies;
	private String[] _header = { "Id", "Mass", "Position", "Velocity", "Force" };

	BodiesTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	@Override
	public int getRowCount() {
		return _bodies.size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public String getColumnName(int column) {
		return _header[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _bodies.get(rowIndex).getId();
			break;
		case 1:
			s = _bodies.get(rowIndex).getMass();
			break;
		case 2:
			s = _bodies.get(rowIndex).getPosition();
			break;
		case 3:
			s = _bodies.get(rowIndex).getVelocity();
			break;
		case 4:
			s = _bodies.get(rowIndex).getForce();
			break;
		}
		return s;
	}
	
	private void update(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				fireTableStructureChanged();
			}
		});
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		update(bodies);

	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		update(bodies);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		update(bodies);
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		update(bodies);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {

	}
}