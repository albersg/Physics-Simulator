package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver {
	private static final long serialVersionUID = 1L;
	// ...
	private Controller _ctrl;
	private boolean _stopped;

	private MainWindow _mw;
	private JToolBar _toolBar;
	private JButton _fileButton;
	private JButton _playButton;
	private JButton _lawsButton;
	private JButton _stopButton;
	private JButton _closeButton;
	private JSpinner _stepsSpinner;
	private JTextField _deltaTime;

	ControlPanel(Controller ctrl, MainWindow mw) {
		_mw = mw;
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}

	private void initGUI() {
		this.setLayout(new BorderLayout());
		_toolBar = new JToolBar();
		this.add(_toolBar, BorderLayout.PAGE_START);
		fileButton();
		_toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		lawsButton();
		_toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		playButton();
		stopButton();
		_toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		stepsSpinner();
		deltaTimeField();
		_toolBar.add(new JSeparator(SwingConstants.VERTICAL));
		closeButton();
	}

	private void deltaTimeField() {
		JLabel deltaTimeText = new JLabel("Delta-Time: ");
		_deltaTime = new JTextField(5);
		_deltaTime.setMinimumSize(new Dimension(70, 35));
		_deltaTime.setMaximumSize(new Dimension(100, 35));
		_deltaTime.setPreferredSize(new Dimension(70, 35));
		_toolBar.add(deltaTimeText);
		_toolBar.add(_deltaTime);
	}

	private void stepsSpinner() {
		JLabel spinnerText = new JLabel("Steps: ");
		_stepsSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, 100000, 100));
		_stepsSpinner.setMinimumSize(new Dimension(70, 35));
		_stepsSpinner.setMaximumSize(new Dimension(100, 35));
		_stepsSpinner.setPreferredSize(new Dimension(70, 35));

		_toolBar.add(spinnerText);
		_toolBar.add(_stepsSpinner);
		_toolBar.add(Box.createRigidArea(new Dimension(10, 10)));
	}

	private void closeButton() {
		_closeButton = new JButton();
		_closeButton.setToolTipText("Button to close the program");
		_closeButton.setIcon(new ImageIcon("./resources/icons/exit.png"));
		_closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this._mw.exit();
			}
		});
		_toolBar.add(_closeButton);
		_closeButton.setVisible(true);
	}

	private void stopButton() {
		_stopButton = new JButton();
		_stopButton.setToolTipText("Button to stop the program");
		_stopButton.setIcon(new ImageIcon("./resources/icons/stop.png"));
		_stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this._mw.stop();
			}
		});
		_toolBar.add(_stopButton);
		_stopButton.setVisible(true);
	}

	private void fileButton() {
		_fileButton = new JButton();
		_fileButton.setToolTipText("Button to select the input file");
		_fileButton.setIcon(new ImageIcon("./resources/icons/open.png"));
		_fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this._mw.chooseFile();
			}
		});
		_toolBar.add(_fileButton);
		_fileButton.setVisible(true);
	}
	
	private void playButton() {
		_playButton = new JButton();
		_playButton.setToolTipText("Button to resume the program");
		_playButton.setIcon(new ImageIcon("./resources/icons/run.png"));
		_playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this._mw.play();
			}
		});
		_toolBar.add(_playButton);
		_playButton.setVisible(true);
	}

	private void lawsButton() {
		_lawsButton = new JButton();
		_lawsButton.setToolTipText("Button to choose the gravity law");
		_lawsButton.setIcon(new ImageIcon("./resources/icons/physics.png"));
		_lawsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this._mw.chooseLaw();
			}
		});
		_toolBar.add(_lawsButton);
		_lawsButton.setVisible(true);
	}

	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "ERROR", "Error simulating the program", JOptionPane.OK_OPTION);
				changeStateButtons(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					run_sim(n - 1);
				}
			});
		} else {
			_stopped = true;
			changeStateButtons(true);
		}
	}

	protected void advance() {
		_stopped = false;
		changeStateButtons(false);
		_ctrl.setDeltaTime(Double.parseDouble(_deltaTime.getText()));
		run_sim((Integer) _stepsSpinner.getValue());
	}

	protected void changeStateStopButton(boolean b) {
		_stopped = b;
	}

	protected void changeStateButtons(boolean state) {
		_fileButton.setEnabled(state);
		_lawsButton.setEnabled(state);
		_closeButton.setEnabled(state);
		_playButton.setEnabled(state);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_deltaTime.setText(Double.toString(dt));
			}
		});
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_deltaTime.setText(Double.toString(dt));
			}
		});
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {

	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {

	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_deltaTime.setText(Double.toString(dt));
			}
		});
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {

	}
}
