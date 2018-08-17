'use strict';

module.exports = function(grunt) {

	grunt.loadNpmTasks('grunt-mocha-test');
    
    // Time how long tasks take. Can help when optimizing build times
    require('time-grunt')(grunt);

    // Configurable paths for the application
    var appConfig = {
        dist: 'dist'
    };

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json')
        , mochatest: {
            unit: {
                src: 'test/*_unit.js',
                options: {
                    ui: 'tdd'
                    , reporter: 'spec'
                    , bail: false
                }
            }
            , integration: {
                src: 'test/*_integration.js',
                options: {
                    ui: 'tdd'
                    , reporter: 'spec'
                    , bail: false
                }
            }
        }
        , clean: {
            js: [ 'dist' ]
        }
        , jshint: {
                // define the files to lint
                files: ['Gruntfile.js', 'src/**/*.js']
                // configure JSHint (documented at http://www.jshint.com/docs/)
                , options: {
                // more options here if you want to override JSHint defaults
                 globals: {
                  jQuery: true,
                  console: true,
                  module: true
                }
            }
        }
        , copy: {
            main: {
                files: [
                    { expand: true, cwd: 'src/',  src: ['**'], dest: 'dist/'}
                    ,{ expand: true, cwd: './',  src: ['package.json'], dest: 'dist/'}
                ]
            }
        }
        , watch: {
                src: {
                    files: ['src/**/*.js'],
                    tasks: ['clean', 'copy'],
                    options: {
                            spawn: false
                    }
                }
        }

    });

    // Default task(s).
    grunt.registerTask('default', [ 'copy', 'watch', 
        'jshint', 'clean', 'mochatest:unit' ]);

    grunt.registerTask('test', [
        'mochatest:unit'
        , 'mochatest:integration'
      ]);
    
    grunt.registerTask('travisbuild', [
        'clean'
        , 'copy'
      ]);

    grunt.registerTask('build', [
        'mochatest:unit'
        , 'clean'
        , 'copy'
      ]);

};
