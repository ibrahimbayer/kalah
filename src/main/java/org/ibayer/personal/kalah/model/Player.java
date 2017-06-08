package org.ibayer.personal.kalah.model;

import java.util.List;

public class Player {
		
	private List<Hole> holes;

	private Hole mainHole;
	
	public List<Hole> getHoles() {
		return holes;
	}

	public void setHoles(List<Hole> holes) {
		this.holes = holes;
	}

	public Hole getMainHole() {
		return mainHole;
	}

	public void setMainHole(Hole mainHole) {
		this.mainHole = mainHole;
	}

	@Override
	public String toString() {
		return "Player [holes=" + holes + ", mainHole=" + mainHole + "]";
	}

	
}
