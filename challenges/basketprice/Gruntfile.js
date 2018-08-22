'use strict';

module.exports = function(grunt) {

	grunt.loadNpmTasks('grunt-mocha-test');
    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json')
        , mochaTest: {
  	      unit: {
  	        options: {
  	          reporter: 'spec',
  	          captureFile: 'tests.txt', // Optionally capture the reporter output to a file
  	          quiet: false, // Optionally suppress output to standard out (defaults to false)
  	          clearRequireCache: false, // Optionally clear the require cache before running tests (defaults to false)
  	          clearCacheFilter: (key) => true, // Optionally defines which files should keep in cache
  	          noFail: false // Optionally set to not fail on failed tests (will still fail on other errors)
  	        },
  	        src: ['test/*_unit.js']
  	      }
    	, integration: {
	        options: {
	  	          reporter: 'spec',
	  	          captureFile: 'tests.txt', // Optionally capture the reporter output to a file
	  	          quiet: false, // Optionally suppress output to standard out (defaults to false)
	  	          clearRequireCache: false, // Optionally clear the require cache before running tests (defaults to false)
	  	          clearCacheFilter: (key) => true, // Optionally defines which files should keep in cache
	  	          noFail: false // Optionally set to not fail on failed tests (will still fail on other errors)
	  	        },
	  	        src: ['test/*_integration.js']
	  	      }
  	    }
    });

    // Default task(s).
    grunt.registerTask('default', [ 'mochaTest:unit','mochaTest:integration' ]);

    grunt.registerTask('test', [
        'mochaTest:unit'
      ]);
    
    grunt.registerTask('integration', [
        'mochaTest:integration'
      ]);


};
