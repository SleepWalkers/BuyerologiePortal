$(".series_title").click(function() {
	$(this).siblings("ul").slideToggle();
	var src=$(this).find("img").prop("src");
	if (src.indexOf("up") > -1) {
		$(this).find("img").prop("src",src.replace("up","down"));
	}
	else {
		$(this).find("img").prop("src",src.replace("down","up"));
	}
})

$(".delete_icon").click(function() {
	$.ajax({
		url: $(this).data("url"),
		type: 'POST',
		dataType: "json",  
		data:{
			videoId: $(this).data("id")
		},
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					location.reload()
					//$(this).parent("li").remove();
				} else {
					alert(data.msg);
				};
			};
		}
	});
	
})

//$(".user_nav li:first").click(function(e) {
//	e.preventDefault();
//	$(".info_part").fadeOut(200);
//	$(".lesson_part").fadeIn(200);
//	$(this).addClass("active");
//	$(this).siblings().removeClass("active");
//})
//
//$(".user_nav li:last").click(function(e) {
//	e.preventDefault();
//	$(".lesson_part").fadeOut(200);
//	$(".info_part").fadeIn(200);
//	$(this).addClass("active");
//	$(this).siblings().removeClass("active");
//})

$(".delete_btn").click(function() {
	$.ajax({
		url: $(this).data("url"),
		type: 'POST',
		dataType: "json",  
		data:{
			topicId: $(this).data("id")
		},
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					location.reload();
				} else {
					alert(data.msg);
				};
			};
		}
	});
	
})