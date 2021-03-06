/*
 * spa.js
 * Root namespace module
*/

/*jslint           browser : true,   continue : true,
  devel  : true,    indent : 2,       maxerr  : 50,
  newcap : true,     nomen : true,   plusplus : true,
  regexp : true,    sloppy : true,       vars : false,
  white  : true
*/
/*global $, spa */

var nos = (function () {
  var initModule = function ( $container ) {
    nos.shell.initModule( $container );
  };

  return { initModule: initModule };
}());
