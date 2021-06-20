package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class LawsTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private String[] _columns = { "Key", "Value", "Description" };
	private List<LawsInfo> _rows;

	public LawsTableModel() {
		_rows = new ArrayList<LawsInfo>();
	}

	public void updateTable(JSONObject data) {
		_rows.clear();

		for (String key : data.keySet()) {
			_rows.add(new LawsInfo(key, "", data.getString(key)));
		}

		fireTableStructureChanged();
	}

	@Override
	public int getRowCount() {
		return _rows.size();
	}

	@Override
	public int getColumnCount() {
		return _columns.length;
	}

	@Override
	public String getColumnName(int column) {
		return _columns[column];
	}

	@Override
	public void setValueAt(Object o, int rowIndex, int columnIndex) {
		_rows.get(rowIndex).set_value(o.toString());
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return columnIndex == 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		LawsInfo li = _rows.get(rowIndex);
		String s = "";

		switch (columnIndex) {
		case 0:
			s = li.get_key();
			break;
		case 1:
			s = li.get_value();
			break;
		case 2:
			s = li.get_desc();
			break;
		}

		return s;
	}

	public JSONObject getData() {
		StringBuilder s = new StringBuilder();
		s.append('{');
		for (int i = 0; i < _rows.size(); i++) {
			if (!_rows.get(i).get_key().isEmpty() && !_rows.get(i).get_value().isEmpty()) {
				s.append('"');
				s.append(_rows.get(i).get_key());
				s.append('"');
				s.append(':');
				s.append(_rows.get(i).get_value());
				s.append(',');
			}
		}

		if (s.length() > 1)
			s.deleteCharAt(s.length() - 1);
		s.append('}');

		JSONObject data = new JSONObject(s.toString());
		return data;
	}

}
