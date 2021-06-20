package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private BodiesTableModel _btm;
	private JTable _table;
	private Controller _ctrl;

	BodiesTable(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
	}

	public void initGUI() {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));

		_btm = new BodiesTableModel(_ctrl);
		_table = new JTable(_btm);

		this.add(new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}

}
