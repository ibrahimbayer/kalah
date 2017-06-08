package org.ibayer.personal.kalah.model.factory;

import java.util.ArrayList;
import java.util.List;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.Hole;
import org.ibayer.personal.kalah.model.Player;
import org.ibayer.personal.kalah.model.PlayerEnum;

/**
 * Factory class to create new {@link Game} instance.
 * 
 * @author ibrahim.bayer
 *
 */
public class GameFactory {

	private static final int INITIAL_SEED_COUNT_FOR_HOLE = 6;

	/**
	 * Returns new {@link Game} instance with black and white {@link Player}.
	 * Creates main {@link Hole}.
	 * 
	 * @param resourceId
	 *            of Game
	 * @return instance
	 */
	public static Game newInstance(String resourceId) {
		Game game = new Game();
		game.setPlayerBlack(new Player());
		game.setPlayerWhite(new Player());
		game.getPlayerBlack().setMainHole(new Hole(0, null));
		game.getPlayerWhite().setMainHole(new Hole(0, null));
		game.setResourceId(resourceId);
		game.setActivePlayer(PlayerEnum.BLACK);
		game.setFinished(Boolean.FALSE);
		List<Hole> holes = createHolesList(game);
		game.getPlayerBlack().setHoles(holes);
		holes = createHolesList(game);
		game.getPlayerWhite().setHoles(holes);
		return game;
	}

	/**
	 * Instantiates a new hole list of 6
	 * 
	 * @param game
	 * @return
	 */
	private static List<Hole> createHolesList(Game game) {
		List<Hole> holes = new ArrayList<>(6);
		for (int i = 1; i < 7; i++) {
			Hole hole = new Hole(INITIAL_SEED_COUNT_FOR_HOLE, i);
			holes.add(hole);
		}
		return holes;
	}

}
