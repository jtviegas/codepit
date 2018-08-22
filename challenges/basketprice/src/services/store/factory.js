"use strict";

const fs = require('fs');

var logger = require('../common').logger;

const StoreServiceFactory = (function(){

    var modules = {};
    
    var init = function(){
    	logger.debug('[StoreServiceFactory.init] IN');
    	fs.readdirSync( __dirname ).forEach(f => {
                logger.debug('[StoreServiceFactory.init] processing file %s', f);

                let moduletype = f;
                let moduleFolder = __dirname + '/' + moduletype;
                let s = fs.statSync( moduleFolder );

                    if( s.isDirectory() ){
                        logger.debug('[StoreServiceFactory.init] processing module %s', moduletype);
                            if( fs.statSync( moduleFolder + '/' + 'index.js' ).isFile() ){
                                logger.info('[StoreServiceFactory.init] loading module %s from ', moduletype, moduleFolder);
                                let module = require(moduleFolder);
                                console.log(module);
                                modules[moduletype.toLowerCase()] = module;
                                console.log('modules', modules);
                                logger.info('[StoreServiceFactory.init] loaded %s module', moduletype);
                            }
                  
                    }

            });
 
    	logger.debug('[StoreServiceFactory.init] OUT');
    }

    var get = function() {
        logger.debug('[StoreServiceFactory.get] IN');
        
        
        // extend this rationale to provide other store service implementations
        let moduletype = 'base';
        
        if(!modules[moduletype]) 
             throw new Error("!!! couldn't find module " + moduletype + " !!!"); 

        logger.debug('[StoreServiceFactory.get] OUT');
        return modules[moduletype];
    };

    init();

    return {
        get: get
    };

})();

module.exports = StoreServiceFactory;