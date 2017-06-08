package org.ibayer.personal.kalah.repository;

import org.ibayer.personal.kalah.model.Game;
import org.ibayer.personal.kalah.model.factory.GameFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * Cache can be managed by a cache manager to scale application.
 * 
 * @author ibrahim.bayer
 *
 */
@Component
@CacheConfig(cacheNames = "games")
public class GameRepository {
	// cache manager reference page for hazelcast
	// https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-hazelcast.html

	/**
	 * Finds game, returns a new instance. If the game isn't present a new
	 * instance is created
	 * 
	 * @param resourceId
	 * @return
	 */
	@Cacheable(cacheNames = "games")
	public Game findByResourceId(String resourceId) {
		return GameFactory.newInstance(resourceId);
	}

}
