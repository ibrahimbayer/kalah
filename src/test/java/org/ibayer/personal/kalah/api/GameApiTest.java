package org.ibayer.personal.kalah.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.PlayerEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(print = MockMvcPrint.NONE)
public class GameApiTest {


	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void postGame() throws Exception {
		mockMvc.perform(post("/games").contentType(APPLICATION_JSON_UTF8).content("{}"))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.resourceId").exists())
				.andExpect(jsonPath("$.activePlayer").value(PlayerEnum.BLACK.toString()))
				.andExpect(jsonPath("$.winner").doesNotExist()).andExpect(jsonPath("$.finished").value(Boolean.FALSE))
				.andExpect(jsonPath("$.playerBlack.holes[0].index").value(1))
				.andExpect(jsonPath("$.playerBlack.holes[0].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[1].index").value(2))
				.andExpect(jsonPath("$.playerBlack.holes[1].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[2].index").value(3))
				.andExpect(jsonPath("$.playerBlack.holes[2].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[3].index").value(4))
				.andExpect(jsonPath("$.playerBlack.holes[3].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[4].index").value(5))
				.andExpect(jsonPath("$.playerBlack.holes[4].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[5].index").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[5].coins").value(6))

				.andExpect(jsonPath("$.playerWhite.holes[0].index").value(1))
				.andExpect(jsonPath("$.playerWhite.holes[0].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[1].index").value(2))
				.andExpect(jsonPath("$.playerWhite.holes[1].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[2].index").value(3))
				.andExpect(jsonPath("$.playerWhite.holes[2].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[3].index").value(4))
				.andExpect(jsonPath("$.playerWhite.holes[3].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[4].index").value(5))
				.andExpect(jsonPath("$.playerWhite.holes[4].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[5].index").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[5].coins").value(6)).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void putGame() throws Exception {
		String gameString = mockMvc.perform(post("/games").contentType(APPLICATION_JSON_UTF8).content("{}"))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Game game = mapper.readValue(gameString, Game.class);

		mockMvc
				.perform(put("/games/" + game.getResourceId() + "?moveId=3").contentType(APPLICATION_JSON_UTF8)
						.content("{}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.activePlayer").value(PlayerEnum.WHITE.toString()))
				.andExpect(jsonPath("$.winner").doesNotExist()).andExpect(jsonPath("$.finished").value(Boolean.FALSE))
				.andExpect(jsonPath("$.playerBlack.holes[0].index").value(1))
				.andExpect(jsonPath("$.playerBlack.holes[0].coins").value(7))
				.andExpect(jsonPath("$.playerBlack.holes[1].index").value(2))
				.andExpect(jsonPath("$.playerBlack.holes[1].coins").value(7))
				.andExpect(jsonPath("$.playerBlack.holes[2].index").value(3))
				.andExpect(jsonPath("$.playerBlack.holes[2].coins").value(0))
				.andExpect(jsonPath("$.playerBlack.holes[3].index").value(4))
				.andExpect(jsonPath("$.playerBlack.holes[3].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[4].index").value(5))
				.andExpect(jsonPath("$.playerBlack.holes[4].coins").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[5].index").value(6))
				.andExpect(jsonPath("$.playerBlack.holes[5].coins").value(6))

				.andExpect(jsonPath("$.playerBlack.mainHole.coins").value(1))

				.andExpect(jsonPath("$.playerWhite.holes[0].index").value(1))
				.andExpect(jsonPath("$.playerWhite.holes[0].coins").value(7))
				.andExpect(jsonPath("$.playerWhite.holes[1].index").value(2))
				.andExpect(jsonPath("$.playerWhite.holes[1].coins").value(7))
				.andExpect(jsonPath("$.playerWhite.holes[2].index").value(3))
				.andExpect(jsonPath("$.playerWhite.holes[2].coins").value(7))
				.andExpect(jsonPath("$.playerWhite.holes[3].index").value(4))
				.andExpect(jsonPath("$.playerWhite.holes[3].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[4].index").value(5))
				.andExpect(jsonPath("$.playerWhite.holes[4].coins").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[5].index").value(6))
				.andExpect(jsonPath("$.playerWhite.holes[5].coins").value(6))

				.andExpect(jsonPath("$.playerWhite.mainHole.coins").value(0))

				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void getBoard() throws Exception {
		String gameString = mockMvc.perform(post("/games/").contentType(APPLICATION_JSON_UTF8).content("{}"))
				.andDo(MockMvcResultHandlers.print()).andReturn().getResponse().getContentAsString();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Game game = mapper.readValue(gameString, Game.class);

		mockMvc.perform(get("/games/" + game.getResourceId()).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}
}
