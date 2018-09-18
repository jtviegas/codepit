// should be const
var redisService = require('lib/redisService');

// should be inside the structure in some guarded scope
const cache;

function init(opts) {
  
  // TODO exception handling, in case resources are not taken already
  // TODO propper logging throughout
  cache = redisService.init({
    port: opts.port,
    host: opts.host
  });

}

function get(key) {
  return cache.get(key);
}

function set(key, value, ttl) {
  return cache.set(key, value, ttl || opts.stdTTL);
}

function del(key) {
  return cache.del(key);
}

function flush() {
  return cache.flush();
}

function getCache(opts) {
  //should init here with opts
      return {
        get: get,
        set: set,
        del: del,
        flush: flush
      };
}

module.exports = {
  // should not export init
  init: init,
  getCache: getCache
};
