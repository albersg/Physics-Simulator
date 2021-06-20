package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.json.JSONException;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	// ...
	private Controller _ctrl;
	private ControlPanel _ctrlPanel;
	private JPanel _centerPanel;
	private StatusBar _statusBar;
	private Viewer _viewer;
	private BodiesTable _table;
	private LawSelectorDialog _dialog;
	private JFileChooser _fileChooser;
	private MyMenu _myMenu;

	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		_myMenu = new MyMenu(this);
		this.setJMenuBar(_myMenu);

		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		_ctrlPanel = new ControlPanel(_ctrl, this);
		mainPanel.add(_ctrlPanel, BorderLayout.PAGE_START);

		_centerPanel = new JPanel();
		_centerPanel.setLayout(new BoxLayout(_centerPanel, BoxLayout.Y_AXIS));
		mainPanel.add(_centerPanel, BorderLayout.CENTER);

		_table = new BodiesTable(_ctrl);
		_centerPanel.add(_table);
		_table.setPreferredSize(new Dimension(650, 250));

		_viewer = new Viewer(_ctrl);
		_centerPanel.add(_viewer);
		_viewer.setPreferredSize(new Dimension(650, 350));

		_statusBar = new StatusBar(_ctrl);
		mainPanel.add(_statusBar, BorderLayout.PAGE_END);

		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				exit();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
		});

		this.setResizable(true);
		this.pack();
		this.setVisible(true);
	}

	protected void play() {
		_ctrlPanel.advance();
	}

	protected void exit() {
		int exitOption = JOptionPane.showConfirmDialog(this, "Are you sure you want to close the program?",
				"Exit the simulation", JOptionPane.YES_NO_OPTION);

		if (exitOption == JOptionPane.YES_OPTION) {
			System.exit(0);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else if (exitOption == JOptionPane.NO_OPTION)
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	protected void stop() {
		_ctrlPanel.changeStateStopButton(true);
	}

	protected void chooseFile() {
		try {
			if (_fileChooser == null) {
				_fileChooser = new JFileChooser();
				_fileChooser.setCurrentDirectory(new File("./resources/examples"));
				_fileChooser.setMultiSelectionEnabled(false);
			}
			int selection = _fileChooser.showOpenDialog(this);

			if (selection == JFileChooser.APPROVE_OPTION) {
				_ctrl.reset();
				_ctrl.loadBodies(new FileInputStream(_fileChooser.getSelectedFile()));
			}
		} 
		catch (JSONException jex) {
			JOptionPane.showMessageDialog(this, "The file is not correct", "Error loading the file",
					JOptionPane.OK_OPTION);
		}
		catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(this, "The file couldn't be found", "Error on selecting file",
					JOptionPane.OK_OPTION);
		}
	}

	protected void chooseLaw() {
		if (_dialog == null)
			_dialog = new LawSelectorDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl.getForceLawsInfo());
		int status = _dialog.open();

		if (status == 1) {
			try {
				_ctrl.setForceLaws(_dialog.getSelectedLaw());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this.getParent(), "Something went wrong: " + e.getLocalizedMessage(),
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
}
