package org.ibayer.personal.kalah.test.service;

import java.util.ArrayList;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.Hole;
import org.ibayer.personal.kalah.model.Player;
import org.ibayer.personal.kalah.model.PlayerEnum;
import org.ibayer.personal.kalah.model.factory.GameFactory;
import org.ibayer.personal.kalah.service.engine.GameEngine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class GameEngineTest {

	private static final int HOLE_COUNT = 6;

	@InjectMocks
	GameEngine gameEngine = new GameEngine();

	private static final Integer INIT_HOLE_COINS = 0;
	private static final Integer EXPECTED_HOLE_COINS_AFTER_ADDING = INIT_HOLE_COINS + 1;
	private static final Integer ONE_COIN = 1;
	private static final Integer ZERO_COIN = 0;

	Game game;
	Player playerBlack;
	Player playerWhite;

	@Before
	public void beforeTest() {
		game = new Game();

		playerBlack = new Player();
		playerWhite = new Player();
		game.setPlayerBlack(playerBlack);
		game.setPlayerWhite(playerWhite);
	}

	@Test
	public void testGetPlayer() {

		// set active player
		game.setActivePlayer(PlayerEnum.BLACK);

		// assert result of get player
		Assert.assertEquals(playerBlack, gameEngine.getPlayer(game));
	}

	@Test
	public void testGetOpponent() {
		// set active player
		game.setActivePlayer(PlayerEnum.BLACK);

		// assert result of get opponent
		Assert.assertEquals(playerWhite, gameEngine.getOpponent(game));
	}

	@Test
	public void testChangeActivePlayer() {
		game.setActivePlayer(PlayerEnum.BLACK);
		gameEngine.changeActivePlayer(game);
		Assert.assertEquals(playerWhite, gameEngine.getPlayer(game));
		Assert.assertEquals(playerBlack, gameEngine.getOpponent(game));
	}

	@Test
	public void testIncreaseHoleCoins() {
		game.setActivePlayer(PlayerEnum.BLACK);
		initHoles();
		gameEngine.increaseHoleCoins(playerBlack.getMainHole());
		Assert.assertNotNull(playerBlack.getMainHole());
		Assert.assertEquals(EXPECTED_HOLE_COINS_AFTER_ADDING, playerBlack.getMainHole().getCoins());
	}

	@Test
	public void checkIsGameFinishedByBlack() {
		game.setActivePlayer(PlayerEnum.BLACK);
		initHoles();
		game.getPlayerBlack().getMainHole().setCoins(3);
		gameEngine.checkIsGameFinished(game);
		Assert.assertNotNull(game.getWinner());
		Assert.assertTrue(game.isFinished());
		Assert.assertEquals(PlayerEnum.BLACK, game.getWinner());
	}
	
	@Test
	public void checkIsGameFinishedByWhite() {
		game.setActivePlayer(PlayerEnum.BLACK);
		initHoles();
		game.getPlayerWhite().getMainHole().setCoins(3);
		gameEngine.checkIsGameFinished(game);
		Assert.assertNotNull(game.getWinner());
		Assert.assertTrue(game.isFinished());
		Assert.assertEquals(PlayerEnum.WHITE, game.getWinner());
	}
	
	@Test
	public void checkAllCoinsInHole() {
		game.setActivePlayer(PlayerEnum.BLACK);
		initHoles();
		//add a coin to first hole
		Integer holeIndex = 0;
		playerBlack.getHoles().add(new Hole(ONE_COIN,holeIndex));
		
		Integer coins = gameEngine.getAllCoinsInHole(playerBlack, holeIndex);
		Assert.assertEquals(ONE_COIN, coins);
		Assert.assertEquals(ZERO_COIN, game.getPlayerBlack().getHoles().get(0).getCoins());
	}
	
	@Test
	public void testAddCoinsToPlayer(){
		game = GameFactory.newInstance("");
		Integer remainingCoins = gameEngine.addCoinsToPlayer(game,game.getPlayerBlack(), game.getPlayerWhite(),5, 5);
		Assert.assertEquals(ZERO_COIN, remainingCoins);
		Assert.assertEquals(Integer.valueOf(6), game.getPlayerBlack().getMainHole().getCoins());
		Assert.assertEquals(ZERO_COIN, game.getPlayerWhite().getHoles().get(1).getCoins());
		Assert.assertEquals(PlayerEnum.WHITE, game.getActivePlayer());
	}
	
	@Test
	public void testAddCoinsToPlayerWithRemaining(){
		game = GameFactory.newInstance("");
		Integer remainingCoins = gameEngine.addCoinsToPlayer(game,game.getPlayerBlack(), game.getPlayerWhite(),6, 4);
		Assert.assertEquals(ZERO_COIN, remainingCoins);
		Assert.assertEquals(ONE_COIN, game.getPlayerBlack().getMainHole().getCoins());
		Assert.assertEquals(Integer.valueOf(6), game.getPlayerWhite().getHoles().get(1).getCoins());
		Assert.assertEquals(Integer.valueOf(6), game.getPlayerBlack().getHoles().get(5).getCoins());
		Assert.assertEquals(Integer.valueOf(7), game.getPlayerBlack().getHoles().get(1).getCoins());
		Assert.assertEquals(Integer.valueOf(7), game.getPlayerBlack().getHoles().get(0).getCoins());
		Assert.assertEquals(PlayerEnum.BLACK, game.getActivePlayer());
	}


	private void initHoles() {
		playerBlack.setMainHole(new Hole(INIT_HOLE_COINS, null));
		playerWhite.setMainHole(new Hole(INIT_HOLE_COINS, null));
		playerBlack.setHoles(new ArrayList<>(HOLE_COUNT));
		playerWhite.setHoles(new ArrayList<>(HOLE_COUNT));
	}

}
