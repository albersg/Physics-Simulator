package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

public class LawSelectorDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	private List<JSONObject> _lawsInfo = null;
	private LawsTableModel _model = null;
	private JTable _table = null;
	private JPanel _mainPanel = null;
	private JComboBox<String> _lawsComboBox = null;
	private JList<String> _lawsList = null;
	private Integer _status;
	// Usamos este índice para guarduar el indice dependiendo de si se utiliza la combo o la list
	private Integer _lawIndex = 0;

	public LawSelectorDialog(Frame parent, List<JSONObject> lawsInfo) {
		super(parent, true);
		_status = 0;
		_lawsInfo = lawsInfo;
		initGUI();
	}

	public void initGUI() {
		helpMsg();
		tableLaws();
		comboLaws();
		createButtons();

		this.setMinimumSize(new Dimension(500, 400));
		this.setPreferredSize(new Dimension(500, 400));
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setSize(500, 400);
		hidePanel();
	}

	public void helpMsg() {
		this.setTitle("Force Law Selection");
		_mainPanel = new JPanel();
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(_mainPanel);

		JLabel help = new JLabel(
				"<html><p>Select a force law and provide values for the parametes in the <b>Value column</b> (default values are used for parametes with no value).</p></html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		_mainPanel.add(help);
		_mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
	}

	public void tableLaws() {
		JPanel tablePanel = new JPanel(new BorderLayout());
		_mainPanel.add(tablePanel, BorderLayout.CENTER);

		_model = new LawsTableModel();
		_table = new JTable(_model);

		tablePanel.add(new JScrollPane(_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));

		_model.updateTable(_lawsInfo.get(0).getJSONObject("data"));

	}

	public void comboLaws() {
		JPanel comboPanel = new JPanel(new FlowLayout());

		String[] _lawsName = new String[3];
		int i = 0;
		for (JSONObject ob : _lawsInfo) {
			_lawsName[i++] = ob.getString("desc");
		}

		_lawsComboBox = new JComboBox<String>(_lawsName);
		_lawsComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LawSelectorDialog.this.updateTableModel(_lawsComboBox.getSelectedIndex());
			}
		});

		_lawsList = new JList<String>(_lawsName);
		_lawsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_lawsList.setFixedCellHeight(20);
		_lawsList.setFixedCellWidth(200);
		_lawsList.setBorder(BorderFactory.createLineBorder(Color.black));
		_lawsList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				LawSelectorDialog.this.updateTableModel(_lawsList.getSelectedIndex());
			}
		});

		comboPanel.add(_lawsComboBox);
		comboPanel.add(Box.createRigidArea(new Dimension(10, 10)));
		comboPanel.add(_lawsList);
		_mainPanel.add(comboPanel);
	}

	public void updateTableModel(int index) {
		_lawIndex = index;
		_model.updateTable(_lawsInfo.get(_lawIndex).getJSONObject("data"));
	}

	public void createButtons() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new FlowLayout());

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				LawSelectorDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (_lawsComboBox.getSelectedItem() != null) {
					_status = 1;
					LawSelectorDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);

		_mainPanel.add(buttonsPanel);

	}

	public void hidePanel() {
		this.setVisible(false);
	}

	public int open() {
		pack();
		setVisible(true);
		return _status;
	}

	public JSONObject getSelectedLaw() {
		JSONObject lawInfo = _lawsInfo.get(_lawIndex);
		JSONObject newLaw = new JSONObject();

		newLaw.put("type", lawInfo.getString("type"));
		newLaw.put("data", _model.getData());
		newLaw.put("desc", lawInfo.getString("desc"));

		return newLaw;
	}
}
