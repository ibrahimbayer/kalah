package org.ibayer.personal.kalah.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Game model
 * @author ibrahim.bayer
 *
 */
public class Game {
	
	private String resourceId;
	
	@ApiModelProperty(readOnly = true,hidden = true)
	//TODO not reflected to swagger editor, i have invested but couldn't find the reason
	private Boolean isFinished;

	@ApiModelProperty(readOnly = true)
	private Player playerBlack;

	@ApiModelProperty(readOnly = true)
	private Player playerWhite;
	
	@ApiModelProperty(readOnly = true)
	private PlayerEnum activePlayer;
	
	@ApiModelProperty(readOnly = true)
	private PlayerEnum winner;
	

	public PlayerEnum getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(PlayerEnum activePlayer) {
		this.activePlayer = activePlayer;
	}

	public Boolean isFinished() {
		return isFinished;
	}

	public void setFinished(Boolean isFinished) {
		this.isFinished = isFinished;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Player getPlayerBlack() {
		return playerBlack;
	}

	public void setPlayerBlack(Player playerBlack) {
		this.playerBlack = playerBlack;
	}

	public Player getPlayerWhite() {
		return playerWhite;
	}

	public void setPlayerWhite(Player playerWhite) {
		this.playerWhite = playerWhite;
	}

	public PlayerEnum getWinner() {
		return winner;
	}

	public void setWinner(PlayerEnum winner) {
		this.winner = winner;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [resourceId=" + resourceId + ", isFinished=" + isFinished + ", playerBlack=" + playerBlack
				+ ", playerWhite=" + playerWhite + ", activePlayer=" + activePlayer + ", winner=" + winner + "]";
	}
	
	
}
