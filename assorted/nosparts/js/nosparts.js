$(function() {

  	$(".comments-toggle").click( function (e) {
    	e.preventDefault();
    	$comments = $(this).next("div .comment");
    	if ( $comments.is (":visible") ) 
    	{
    		$(this).text("show comments");
    		$comments.slideUp();
    	}
    	else
    	{
    		$(this).text("hide comments");
    		$comments.slideDown();
    	}
    
  	});

  	$(".addcomment-toggle").click( function (e) {
    	e.preventDefault();
    	var $addcomment = $(this).parents("div .comment").next("div .addcomment");
    	//console.log($addcomment);
    	if ( !$addcomment.is (":visible") ) 
    	{
    		$(this).text("");
    		$addcomment.slideDown();
    	}
    	else
    	{
    		$(this).text("add comment");
    		$addcomment.slideUp();
    	}
    	
		
  	});

  	$(".addcomment-cancel").click( function (e) {
    	
    	$(this).parents("div .addcomment").prev().children(".addcomment-toggle").click();
  	});


});
