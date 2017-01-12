//$(function() {
//	if ($(document).width()>=992) {
//		$height='402'
//	} else {
//		$height='300'
//	};
//	player = polyvObject('#plv_9b986371749aa39d26ecc7b66c063c83_9').videoPlayer({
//	    'width': '100%',
//	    'height': $height,
//		// 'list_id':'1480865569520_9',
//	    'vid' : '9b986371749aa39d26ecc7b66c063c83_9'
//	});
//
//	$(".list_content").height($(".course_list .col-sm-8").outerHeight()-74);
//
//	set_at_color();	
//})

$(window).resize(function() {
	$(".list_content").height($(".course_list .col-sm-8").outerHeight()-74);
})

$("#favourite_btn").click(function() {
	if ($(this).data("logged")) {
		$.ajax({
			url: $(this).data("favourite"),
			type: 'POST',
			dataType: "json",  
			data: {
				topicId: $(this).data("info-id"),
			},
			success: function(data) {
				if (data.isRedirect) {
					location.href=data.redirectURL;
				} else {
					if (data.isSuccess) {
						$(".j_fav").children("span").text("已收藏");
						$(".j_fav").attr("disabled",true);
					} else {
						alert(data.msg);
					};
				};
			}
		});
	} else {
		$('#loginModal').modal("show");
	};
})

$("#comment_btn").click(function(e) {
	e.preventDefault();
	var url = $(".j_comment_button").data("comment");
	var comment = $("#commentForm textarea[name=comment]").val();
	var newsId = $("#commentForm input[name=newsId]").val();
	if(comment == ""){
		alert("评论不能为空");
		return false;
	}
	$.ajax({
		url: url,
		data: {
			comment : comment,
			newsId : newsId
		},
		type: 'POST',
		dataType: 'json',
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					location.reload()
				} else {
					alert(data.msg);
					return false;
				};
			};
		}
	});
})


function switchCourse(obj) {
	if ($(document).width()>=992) {
		$height='402'
	} else {
		$height='300'
	};
	player = polyvObject('#plv_9b986371749aa39d26ecc7b66c063c83_9').videoPlayer({
	    'width': '100%',
	    'height': $height,
	    'vid' : $(obj).data("course-id")
	});
}