package org.aprestos.labs.snippets.impl.data.redis.dao;

import java.net.URL;

import javax.annotation.Resource;

import org.aprestos.labs.snippets.boot.config.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("example")
public class Example {

	public Example(){
		
	}
	// inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    // can also inject as Value, Set, ZSet, and HashOperations
    @Resource(name="redisTemplate")
    private ListOperations<String, String> listOps;

    @SuppressWarnings("unchecked")
	public void addLink(String userId, URL url) {
    	
    	/*template = (RedisTemplate<String, String>) 
    			Context.getInstance().getApplicationContext().getBean("redisTemplate");*/
        listOps.leftPush(userId, url.toExternalForm());
        // or use template directly
        template.boundListOps(userId).leftPush(url.toExternalForm());
    }

}
