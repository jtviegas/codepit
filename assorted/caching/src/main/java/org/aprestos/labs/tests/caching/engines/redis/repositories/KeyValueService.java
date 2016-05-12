package org.aprestos.labs.tests.caching.engines.redis.repositories;

import javax.inject.Inject;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KeyValueService {

	@Inject
	private StringRedisTemplate template;

	@Inject
	public KeyValueService(StringRedisTemplate redisTemplate){
		this.template = redisTemplate;
	}
	
	public void remove(String key) {
		template.delete(key);
	}
	
	public void put(String key, String value){
		template.opsForValue().set(key,value);
	}
	
	public String get(String key) {
		return  template.opsForValue().get(key);
	}
	
	public void clear(){
		template.getConnectionFactory().getConnection().flushDb();
	}
	
	public void shutdown(){
		template.getConnectionFactory().getConnection().close();
		template.getConnectionFactory().getConnection().shutdown();
	}

}
