	$(function(){
	$(".navbar>li>a").mouseover(function(){
		$(".navbar>li>ul").stop().slideUp("fast");
		$(this).next().stop().slideDown("fast");
	});
	$(".navbar>li>ul").mouseleave(function(){
		$(".navbar>li>ul").stop().slideUp("fast");
	});
	});
	/**
 * 
 */