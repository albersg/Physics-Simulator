package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MyMenu extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private MainWindow _mw;
	private JMenuItem _load, _quit, _law, _play, _stop;
	private JMenu _file, _run;

	public MyMenu(MainWindow mw) {
		_mw = mw;
		initGUI();
	}

	private void initGUI() {
		fileMenu();
		runMenu();
	}

	private void runMenu() {
		_run = new JMenu("Run");
		this.add(_run);
		_run.setMnemonic(KeyEvent.VK_R);

		playItem();
		stopItem();

		_run.add(_play);
		_run.add(_stop);
	}

	private void stopItem() {
		_stop = new JMenuItem("Stop");
		_stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mw.stop();
			}
		});
		_stop.setMnemonic(KeyEvent.VK_S);
		_stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));		
	}

	private void playItem() {
		_play = new JMenuItem("Play");
		_play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mw.play();
			}
		});
		_play.setMnemonic(KeyEvent.VK_P);
		_play.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
	}

	private void fileMenu() {
		_file = new JMenu("File");
		this.add(_file);
		_file.setMnemonic(KeyEvent.VK_F);

		loadItem();
		lawItem();
		quitItem();

		_file.add(_load);
		_file.add(_law);
		_file.add(_quit);
	}

	private void lawItem() {
		_law = new JMenuItem("Gravity Law");
		_law.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mw.chooseLaw();
			}
		});
		_law.setMnemonic(KeyEvent.VK_G);
		_law.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
	}

	private void quitItem() {
		_quit = new JMenuItem("Quit");
		_quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mw.exit();
			}
		});
		_quit.setMnemonic(KeyEvent.VK_Q);
		_quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
	}

	private void loadItem() {
		_load = new JMenuItem("Load");
		_load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mw.chooseFile();
			}
		});
		_load.setMnemonic(KeyEvent.VK_L);
		_load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
	}
}
