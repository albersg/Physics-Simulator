package simulator.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class Viewer extends JComponent implements SimulatorObserver {
	private static final long serialVersionUID = 1L;

	private final static String _helpMsg = "h: toggle help, v: toggle vectors, +: zoom-in, -: zoom-out, =: fit";
	private final static String _helpMsg2 = "Scaling ratio: ";
	private int _centerX;
	private int _centerY;
	private double _scale;
	private List<Body> _bodies;
	private boolean _showHelp;
	private boolean _showVectors;

	Viewer(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}

	private void initGUI() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Viewer",
				TitledBorder.LEFT, TitledBorder.TOP));
		_bodies = new ArrayList<>();
		_scale = 1.0;
		_showHelp = true;
		_showVectors = true;
		addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyChar()) {
				case '-':
					_scale = _scale * 1.1;
					repaint();
					break;
				case '+':
					_scale = Math.max(1000.0, _scale / 1.1);
					repaint();
					break;
				case '=':
					autoScale();
					repaint();
					break;
				case 'h':
					_showHelp = !_showHelp;
					repaint();
					break;
				case 'v':
					_showVectors = !_showVectors;
					repaint();
					break;
				default:
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		addMouseListener(new MouseListener() {
			// ...
			@Override
			public void mouseEntered(MouseEvent e) {
				requestFocus();
			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gr = (Graphics2D) g;
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		_centerX = getWidth() / 2;
		_centerY = getHeight() / 2;
		drawCross(gr);
		drawBodies(g, gr);
		if (_showHelp) {
			gr.setColor(Color.red);
			gr.drawString(_helpMsg + "\n", 10, 23);
			gr.drawString(_helpMsg2 + _scale, 10, 35);
		}
	}

	// other private/protected methods
	// ...
	private void drawCross(Graphics2D gr) {
		gr.setColor(Color.red);
		gr.drawLine(_centerX - 8, _centerY, _centerX + 8, _centerY);
		gr.drawLine(_centerX, _centerY - 8, _centerX, _centerY + 8);
	}

	private void drawBodies(Graphics g, Graphics2D gr) {
		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			Vector2D v = b.getVelocity();
			Vector2D f = b.getForce();

			int x = _centerX + (int) (p.getX() / _scale);
			int y = _centerY - (int) (p.getY() / _scale);

			gr.setColor(Color.blue);
			gr.fillOval(x, y, 5, 5);
			gr.setColor(Color.black);
			gr.drawString(b.getId(), x - 4, y - 5);

			if (_showVectors) {
				showVectors(x, y, p, v, f, g);
			}
		}
	}

	private void showVectors(int x, int y, Vector2D p, Vector2D v, Vector2D f, Graphics g) {
		// x1 y y1 son double porque si no el tamaño de las coordenadas de f no cabe
		// dentro de un int
		double x1 = x + f.getX();
		double y1 = y - f.getY();
		int x2 = x + (int) v.getX();
		int y2 = y - (int) v.getY();

		Vector2D vAux1 = new Vector2D(x1 - x, y1 - y).direction();
		Vector2D vAux2 = new Vector2D(x2 - x, y2 - y).direction();
		drawLineWithArrow(g, x + 2, y + 2, (int) (x + 2 + (vAux1.getX()) * 25), (int) (y + 2 + (vAux1.getY()) * 25), 4,
				4, Color.green, Color.green);
		drawLineWithArrow(g, x + 2, y + 2, (int) (x + 2 + (vAux2.getX()) * 25), (int) (y + 2 + (vAux2.getY()) * 25), 4,
				4, Color.red, Color.red);
	}

	private void autoScale() {
		double max = 1.0;
		for (Body b : _bodies) {
			Vector2D p = b.getPosition();
			max = Math.max(max, Math.abs(p.getX()));
			max = Math.max(max, Math.abs(p.getY()));
		}
		double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
		_scale = max > size ? 4.0 * max / size : 1.0;
	}

// This method draws a line from (x1,y1) to (x2,y2) with an arrow.
// The arrow is of height h and width w.
// The last two arguments are the colors of the arrow and the line
	private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor,
			Color arrowColor) {
		int dx = x2 - x1, dy = y2 - y1;
		double D = Math.sqrt(dx * dx + dy * dy);
		double xm = D - w, xn = xm, ym = h, yn = -h, x;
		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;
		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xpoints = { x2, (int) xm, (int) xn };
		int[] ypoints = { y2, (int) ym, (int) yn };

		g.setColor(lineColor);
		g.drawLine(x1, y1, x2, y2);
		g.setColor(arrowColor);
		g.fillPolygon(xpoints, ypoints, 3);
	}

	private void update(List<Body> bodies) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				_bodies = bodies;
				autoScale();
				repaint();
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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	public void onDeltaTimeChanged(double dt) {

	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {

	}
}
