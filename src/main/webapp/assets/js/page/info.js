$(function(){
	$('#info_list').jscroll({
        debug: false, // When set to true, outputs useful information to the console display if the console object exists.
        autoTrigger: true, // When set to true, triggers the loading of the next set of content automatically when the user scrolls to the bottom of the containing element. When set to false, the required next link will trigger the loading of the next set of content when clicked
        loadingHtml: '<img src=""></img>', //加载图片
        padding: 1000, // The distance from the bottom of the scrollable content at which to trigger the loading of the next set of content. This only applies when autoTrigger is set to true.
        contentSelector: '', // A convenience selector for loading only part of the content in the response for the next set of content. This selector will be ignored if left blank and will apply the entire response to the DOM.
        nextSelector: '.infinite_scroll_next a:last', // The selector to use for finding the link which contains the href pointing to the next set of content. If this selector is not found, or if it does not contain a href attribute, jScroll will self-destroy and unbind from the element upon which it was called.
        // callback: load_next_page_callback, //Pass a function to this option and it will be called at the end of each page load. Alternatively, you can pass a function as the only argument to the jScroll instantiation instead of an options object, and it will be returned as a callback.
    });
})


$(window).scroll(function() {
	if ($(window).scrollTop() > $(window).height()) {
		$(".to_top").show();
	}
	else {
		$(".to_top").hide();
	}
})

$(".to_top").click(function() {
	$("html,body").animate({scrollTop:0},500);
})