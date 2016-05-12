$(function() {
    console.log( "ready!" );

    $("#query").on("click",
    	function(e){
    		$panel=$("#querypanel");
    		$panel.slideDown();
    		if( $panel.is(":visible") )
    		{
    			$(this).text("query <");
    		}
    		else
    			$(this).text("query >");
    	}
    );

});