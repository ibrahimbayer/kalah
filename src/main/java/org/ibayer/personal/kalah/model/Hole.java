package org.ibayer.personal.kalah.model;

public class Hole {

	public Hole(){
	}
	
	public Hole(final Integer coins,final Integer index) {
		super();
		this.coins = coins;
		this.index = index;
	}

	private Integer coins;
	private Integer index;

	public Integer getCoins() {
		return coins;
	}

	public void setCoins(Integer coins) {
		this.coins = coins;
	}

	public Integer getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "Hole [coins=" + coins + ", index=" + index + "]";
	}
	
	
	
}
