package org.ibayer.personal.kalah.service.engine;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.Hole;
import org.ibayer.personal.kalah.model.Player;
import org.ibayer.personal.kalah.model.PlayerEnum;
import org.springframework.stereotype.Component;

/**
 * Managed bean implementation for game methods used to play.<br/>
 * Is responsible to execute game events
 * 
 * @author ibrahim.bayer
 *
 */
@Component
public class GameEngine {

	private static final int COINST_TO_ADD_HOLE = 1;

	/**
	 * Returns current player based on games active player enum.
	 * {@link PlayerEnum}
	 * 
	 * @return
	 */
	public Player getPlayer(Game game) {
		return game.getActivePlayer() == PlayerEnum.BLACK ? game.getPlayerBlack() : game.getPlayerWhite();
	}

	/**
	 * Returns oppponent player based on games active player enum.
	 * {@link PlayerEnum}
	 * 
	 * @return
	 */
	public Player getOpponent(Game game) {
		return game.getActivePlayer() == PlayerEnum.BLACK ? game.getPlayerWhite() : game.getPlayerBlack();
	}

	/**
	 * Increases holes coins by one
	 * 
	 * @param hole
	 */
	public void increaseHoleCoins(Hole hole) {
		hole.setCoins(hole.getCoins() + COINST_TO_ADD_HOLE);
	}

	/**
	 * Changes active player of {@link Game} , {@link PlayerEnum} is changed
	 * from BLACK to WHITE or vise versa.
	 * 
	 * @param game
	 */
	public void changeActivePlayer(Game game) {
		game.setActivePlayer(game.getActivePlayer() == PlayerEnum.BLACK ? PlayerEnum.WHITE : PlayerEnum.BLACK);
	}

	/**
	 * Returns remaining coins of {@link Player}
	 * 
	 * @param player
	 *            to sum remaining coins
	 * @return sum of coins
	 */
	private int getRemainingCoinsOf(Player player) {
		return player.getHoles().stream().mapToInt(Hole::getCoins).sum();
	}

	/**
	 * If one player is completed all coins in his holes then the game is
	 * finished. The winner is one who has most coins at main hole
	 */
	public void checkIsGameFinished(Game game) {
		// end game if there are no remaining coins
		int remainingCoinsBlackPlayer = this.getRemainingCoinsOf(game.getPlayerBlack());
		int remainingCoinsWhitePlayer = this.getRemainingCoinsOf(game.getPlayerWhite());
		if (remainingCoinsBlackPlayer == 0 || remainingCoinsWhitePlayer == 0) {

			// add remaining coins in holes to main hole
			game.getPlayerBlack().getMainHole()
					.setCoins(game.getPlayerBlack().getMainHole().getCoins() + remainingCoinsBlackPlayer);
			game.getPlayerWhite().getMainHole()
					.setCoins(game.getPlayerWhite().getMainHole().getCoins() + remainingCoinsWhitePlayer);

			// empty holes
			game.getPlayerBlack().getHoles().forEach(hole -> hole.setCoins(0));
			game.getPlayerWhite().getHoles().forEach(hole -> hole.setCoins(0));

			game.setFinished(true);
			// TODO if same count of coins
			game.setWinner(
					game.getPlayerBlack().getMainHole().getCoins() > game.getPlayerWhite().getMainHole().getCoins()
							? PlayerEnum.BLACK : PlayerEnum.WHITE);
		}
	}

	/**
	 * The game starts with one player selects a hole to play and all coins in
	 * that hole is used
	 * 
	 * @param player
	 *            Active player to play his turn
	 * @param holeIndex
	 *            Index of hole to play. This is the holeId -1 which means index
	 *            on array. <br/>
	 *            <code>First holeId = 1, holeIndex = 0 - last holeId = 6,
	 *            holeIndex = 5<code>
	 * @return Remaining coins in hole
	 */
	public int getAllCoinsInHole(Player player, int holeIndex) {
		int coinsRemaining = player.getHoles().get(holeIndex).getCoins();
		player.getHoles().get(holeIndex).setCoins(0);
		return coinsRemaining;
	}

	/**
	 * The player who has the turn will add remaining coins to his holes one by
	 * one. The direction of play is from index to 0.
	 * 
	 * @param player
	 *            Active Player referred by {@link PlayerEnum}
	 * @param opponent
	 *            Opponent Player referred by {@link PlayerEnum}
	 * @param coinsRemaining
	 *            remaining coins to play
	 * @param holeIndex
	 *            index of hole
	 * @return remaining coins after all holes has been feeded
	 */
	public int addCoinsToPlayer(Game game, Player player, Player opponent, int coinsRemaining, int holeIndex) {

		// one by one add coins to remaining holes
		while (holeIndex >= 0 && coinsRemaining > 0) {
			increaseHoleCoins(player.getHoles().get(holeIndex));
			coinsRemaining--;
			// if there are no remaining coins in player zone get from opponent
			if (coinsRemaining == 0) {
				getAllOpponentCoinsAt(player, opponent, holeIndex);
				changeActivePlayer(game);
			}
			holeIndex--;
		}
		// add one remaining to main hole
		if (coinsRemaining > 0) {
			increaseHoleCoins(player.getMainHole());
			coinsRemaining--;
		}
		return coinsRemaining;
	}

	/**
	 * The player who has the turn will add remaining coins to his holes one by
	 * one. The direction of play is from 0 to 6.
	 * 
	 * @param game
	 *            Game instance
	 * @param opponent
	 *            Opponent Player referred by {@link PlayerEnum}
	 * @param coinsRemaining
	 *            remaining coins to play
	 */
	public void addCoinsToOpponent(Game game, Player opponent, int coinsRemaining) {
		// one by one add coins to opponent holes if remaining
		if (coinsRemaining > 0) {
			int holeIndex = 0;
			while (coinsRemaining > 0 && holeIndex < 6) {
				increaseHoleCoins(opponent.getHoles().get(holeIndex));
				coinsRemaining--;
				holeIndex++;
			}
			changeActivePlayer(game);
		}
	}

	private void getAllOpponentCoinsAt(Player player, Player opponent, int holeIndex) {
		player.getMainHole().setCoins(player.getMainHole().getCoins() + opponent.getHoles().get(holeIndex).getCoins());
		opponent.getHoles().get(holeIndex).setCoins(0);
	}

}
