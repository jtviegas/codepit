module.exports = function(grunt) {
	// Project configuration.
	grunt.initConfig({
        
        clean: {  dist:'dist/**/*' },
        jshint: ['Gruntfile.js'],	
        pkg: grunt.file.readJSON('package.json'),
        copy: { main: 
			{ files: [ // includes files within path
                		{flatten: true, expand: true, src: ['src/backend/**/*.js'], dest: 'dist/backend/'}
            			]		 
			} 
		},
	watch: { backendjs: {
        			files: [ 'src/backend/**/*.js' ],
        			tasks: ['clean', 'copy'],
        			options: { spawn: false } 
			}  
		}
	});
	// Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-jshint');
	// Default task(s).
	grunt.registerTask('default', ['copy', 'watch', 'jshint', 'clean']);
};
