package simulator.control;

import org.json.JSONObject;

public class NotEqualException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEqualException(String message) {
		super(message);
	}
	
	public NotEqualException(JSONObject expState, JSONObject currState, int step) {
		super("\nIn execution step " + step + " the following states are not equal:\nExpected state: " + expState + "\nCurrent state: " + currState);
	}

}
