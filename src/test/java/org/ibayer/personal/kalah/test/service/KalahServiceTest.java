package org.ibayer.personal.kalah.test.service;

import org.ibayer.personal.kalah.exception.InvalidRequestException;
import org.ibayer.personal.kalah.exception.ResourceNotFoundException;
import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.Player;
import org.ibayer.personal.kalah.model.PlayerEnum;
import org.ibayer.personal.kalah.repository.GameRepository;
import org.ibayer.personal.kalah.service.KalahService;
import org.ibayer.personal.kalah.service.engine.GameEngine;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

public class KalahServiceTest {

	@Mock
	GameRepository gameRepository = Mockito.mock(GameRepository.class);

	@Mock
	GameEngine gameEngine = Mockito.mock(GameEngine.class);

	@Spy
	KalahService kalahService = new KalahService(gameRepository, gameEngine);

	public void testSave() {
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);

		game = kalahService.save(game);

		Assert.assertNotNull(game);
		Assert.assertNotNull(game.getResourceId());
		Assert.assertNotNull(game.getPlayerBlack());
		Assert.assertNotNull(game.getPlayerWhite());
		Assert.assertNotNull(game.getPlayerBlack().getHoles());
		Assert.assertNotNull(game.getPlayerWhite().getHoles());
		Assert.assertEquals(Integer.valueOf(6), Integer.valueOf(game.getPlayerBlack().getHoles().size()));
		Assert.assertEquals(Integer.valueOf(6), Integer.valueOf(game.getPlayerWhite().getHoles().size()));

	}

	@Test
	public void testMove() {
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);
		Player blackPlayer = new Player();
		Mockito.when(gameEngine.getPlayer(Mockito.any(Game.class))).thenReturn(blackPlayer);

		Player whitePlayer = new Player();
		Mockito.when(gameEngine.getOpponent(Mockito.any(Game.class))).thenReturn(whitePlayer);

		Mockito.when(gameEngine.getAllCoinsInHole(Mockito.any(Player.class), Mockito.anyInt())).thenReturn(6);

		Mockito.when(gameEngine.addCoinsToPlayer(Mockito.any(Player.class), Mockito.any(Player.class), Mockito.anyInt(),
				Mockito.anyInt())).thenReturn(2);

		Mockito.when(gameEngine.addCoinsToOpponent(Mockito.any(Player.class), Mockito.anyInt()))
				.thenReturn(Boolean.TRUE);

		
		Mockito.doNothing().when(gameEngine).changeActivePlayer(Mockito.any(Game.class));
		
		Mockito.doNothing().when(gameEngine).checkIsGameFinished(Mockito.any(Game.class));
		kalahService.put(game,"", 1);
		
		//gameEngine changeActivePlayer has to be called one time
		Mockito.verify(gameEngine,Mockito.times(1)).changeActivePlayer(Mockito.any(Game.class));
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testNegativeMoveId(){
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);
		kalahService.put(game, "",Integer.MIN_VALUE);
	}
	
	@Test(expected = InvalidRequestException.class)
	public void testBigMoveId(){
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);
		kalahService.put(game, "",Integer.MAX_VALUE);
	}
	
	@Test
	public void testNullMoveId(){
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);
		kalahService.put(game, "",null);
		Mockito.verify(gameEngine,Mockito.times(0)).getPlayer(Mockito.any(Game.class));
	}
	
	@Test
	public void testGetGame(){
		Game game = initGame();
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(game);
		
		Game actualGame = kalahService.get("");
		Assert.assertNotNull(actualGame);
		Assert.assertEquals(game, actualGame);
		
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void testGetGameNull(){
		Mockito.when(gameRepository.findByResourceId(Mockito.anyString())).thenReturn(null);
		kalahService.get("");
	}

	private Game initGame() {
		Game game = new Game();
		game.setActivePlayer(PlayerEnum.BLACK);
		game.setFinished(false);
		return game;
	}

}
