$(function() {
	set_at_color();	
})

$("#back_btn").click(function() {
	history.back();
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

$(".see_more").click(function() {
	$(".comment_body_plus").slideToggle();
	$(".see_more .glyphicon").toggleClass("glyphicon-chevron-down");
	$(".see_more .glyphicon").toggleClass("glyphicon-chevron-up");
})

function set_at_color() {
	$(".comment_line .comment_content").each(function() {
		if ($(this).text().indexOf("@")>-1 && $(this).text().indexOf("@")<$(this).text().length-1) {
			var string = $(this).html().split("@");
			string[1] = string[1].replace(/\s/,"</span> ");
			$(this).html(string.join("<span style='color:#8094CC;'>@"));
		};
	})
}
