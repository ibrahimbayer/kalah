package org.ibayer.personal.kalah.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.validation.Valid;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.service.KalahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest controller class to manage API export for {@link Game}
 * 
 * @author ibrahim.bayer
 *
 */
@RestController
@RequestMapping(value = "/games")
public class GameApi {

	@Autowired
	public GameApi(final KalahService kalahService) {
		this.kalahService = kalahService;
	}

	private final KalahService kalahService;

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new game", notes = "Creates a new game", tags = { "Games" })
	@ApiResponses({ @ApiResponse(code = 201, message = "Saved!", response = Game.class) })
	public ResponseEntity<Resource<Game>> post(@Valid @RequestBody Game game) {
		game = kalahService.save(game);
		Resource<Game> resource = generateResource(game);
		return new ResponseEntity<>(resource, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{gameId}", method = RequestMethod.PUT)
	@ApiOperation(value = "Updates an existing game", notes = "Updates an existing game", tags = { "Games" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Updated!", response = Game.class) })
	public ResponseEntity<Resource<Game>> put(@PathVariable(name = "gameId") String gameId, @Valid @RequestBody Game game,
			@RequestParam Integer moveId) {
		game = kalahService.put(game, gameId, moveId);
		Resource<Game> resource = generateResource(game);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	@ApiOperation(value = "Gets an existing game", notes = "Gets an existing game", tags = { "Games" })
	@ApiResponses({ @ApiResponse(code = 200, message = "Returned!", response = Game.class) })
	public ResponseEntity<Resource<Game>> get(@PathVariable(name = "gameId") String gameId) {
		Game game = kalahService.get(gameId);
		Resource<Game> resource = generateResource(game);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

	// TODO move another class
	private Resource<Game> generateResource(Game game) {
		Resource<Game> resource = new Resource<>(game);
		resource.add(linkTo(methodOn(GameApi.class).get(game.getResourceId())).withSelfRel());
		return resource;
	}

}
