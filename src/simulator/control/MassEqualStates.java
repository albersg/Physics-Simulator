package simulator.control;

import org.json.JSONObject;

public class MassEqualStates implements StateComparator{

	@Override
	public boolean equal(JSONObject s1, JSONObject s2) {
		if(s1.get("time").equals(s2.get("time")) && s1.length() == s2.length()) {
			boolean found = false;
			int i = 0;
			
			// searching a pair of bodies that are different
			while(!found && i < s1.getJSONArray("bodies").length()) {
				if(s1.getJSONArray("bodies").getJSONObject(i).getString("id").equals(s2.getJSONArray("bodies").getJSONObject(i).getString("id"))
						&& s1.getJSONArray("bodies").getJSONObject(i).getDouble("mass") == s2.getJSONArray("bodies").getJSONObject(i).getDouble("mass"))
					i++;
				else found = true;
			}
			
			if(!found)
				return true;
		}
		return false;
	}

}
