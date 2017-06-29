package org.ibayer.personal.kalah.service;

import java.util.UUID;

import org.ibayer.personal.kalah.exception.InvalidRequestException;
import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.Player;
import org.ibayer.personal.kalah.model.PlayerEnum;
import org.ibayer.personal.kalah.model.factory.GameFactory;
import org.ibayer.personal.kalah.repository.GameRepository;
import org.ibayer.personal.kalah.service.engine.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class
 * 
 * @author ibrahim.bayer
 *
 */
@Service
public class GameService {

	private static final int MIN_HOLE_INDEX = 1;

	private static final int MAX_HOLE_INDEX = 6;

	private final GameRepository gameRepository;

	private final GameEngine gameEngine;

	/**
	 * @param gameRepository
	 * @param gameEngine
	 */
	@Autowired
	public GameService(final GameRepository gameRepository, final GameEngine gameEngine) {
		this.gameRepository = gameRepository;
		this.gameEngine = gameEngine;
	}

	/**
	 * Stores a new game instance and configures.
	 * @param game
	 * @return
	 */
	public Game save(Game game) {
		String resourceId = UUID.randomUUID().toString();
		game = gameRepository.findByResourceId(resourceId);
		return game;
	}

	/**
	 * Move operation for Game board and active player {@link PlayerEnum}.
	 * @param game instance to move
	 * @param moveId value between 1 to 6. Index of hole
	 */
	private void move(Game game, final Integer moveId) {

		if (moveId > MAX_HOLE_INDEX || moveId < MIN_HOLE_INDEX) {
			throw new InvalidRequestException(String.valueOf(moveId));
		}

		final Player player = gameEngine.getPlayer(game);
		final Player opponent = gameEngine.getOpponent(game);

		// hole index is minus 1 from id of list
		Integer holeIndex = moveId - 1;
		// get all coins remaining at hole
		int coinsRemaining = gameEngine.getAllCoinsInHole(player, holeIndex--);

		coinsRemaining = gameEngine.addCoinsToPlayer(game,player, opponent, coinsRemaining, holeIndex);

		gameEngine.addCoinsToOpponent(game,opponent, coinsRemaining);

		// check remaining coins on all holes
		gameEngine.checkIsGameFinished(game);
	}

	/**
	 * Updates a game instance and executes move method if moveId is provided.
	 * 
	 * @param game
	 *            Instance to play
	 * @param gameId
	 *            Resource id to access game
	 * @param moveId
	 *            the move to proceed. Accepted value is between 1 to 6
	 */
	public Game put(Game game, String gameId, Integer moveId) {
		Game savedGame = get(gameId);
		if (moveId != null) {
			// TODO point to improve : the sync could rely on cache, has to be
			// multi node lockable
			synchronized (savedGame) {
				move(savedGame, moveId);
			}
		}
		return savedGame;
	}

	/**
	 * Gets game from repository, if the game isn't found a new instance is created by {@link GameFactory}
	 * 
	 * @param gameResourceId
	 * @return instance
	 */
	public Game get(String gameResourceId) {
		return gameRepository.findByResourceId(gameResourceId);
	}
}
