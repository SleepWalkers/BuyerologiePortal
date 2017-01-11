$('#loginModal').on('show.bs.modal', function (e) {
	modalButtonToggle(this);
})

$('#registerModal').on('show.bs.modal', function (e) {
	modalButtonToggle(this);
	send_btn_toggle($(this));
})

$('#registerSuccessModal').on('hidden.bs.modal', function (e) {
	location.reload();
})

$('.myModal input[type=tel]').on('input', function() {
	send_btn_toggle(this.parentNode);
})

$('.myModal input').on('input', function() {
	modalButtonToggle(this.parentNode.parentNode);
})

$("#login_btn").click(function(e) {
	e.preventDefault();

	$.ajax({
		url: $(this).data("login"),
		type: 'POST',
		dataType: "json",  
		data:{
			phone: $("#loginModal input[name=phone]").val(),
			password: $("#loginModal input[name=password]").val()
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

$("#logout_btn").click(function(e) {
	e.preventDefault();

	$.ajax({
		url: $(this).data("logout"),
		type: 'POST',
		dataType: "json",
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					location.href="/";
				} else {
					alert(data.msg);
				};
			};
		}
	});
})

$("#register_btn").click(function(e) {
	e.preventDefault();

	$.ajax({
		url: $(".j_Register_btn").data("register"),
		type: 'POST',
		dataType: "json",
		data:{
			phone: $("#registerModal input[name=phone]").val(),
			code: $("#registerModal input[name=code]").val(),
			password: $("#registerModal input[name=password]").val(),
			redirectUrl: window.document.location.href
		},
		success: function(data) {
			if (data.isSuccess) {
				changeModal(this,'#registerSuccessModal');
			} else {
				alert(data.msg);
			}
		}
	});
})

$("#forget_btn").click(function(e) {
	e.preventDefault();

	$.ajax({
		url: $(this).data("forget"),
		type: 'POST',
		data: {
			phone: $("#forgetModal input[name=phone]").val(),
			code: $("#forgetModal input[name=code]").val(),
			password: $("#forgetModal input[name=password]").val()
		},
		dataType:'json',
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

function show_more(obj) {
	var $current_page = $(".pager:last").text().split("/")[0];
	var $total_page = $(".pager:last").text().split("/")[1];

	$.ajax({
		url: $(obj).data("more"),
		data: {
			//$current_page+1
		},
		success: function(data) {
			$(".see_more").hide();
			$(".comment_body").append(data);
			$current_page = $(".pager:last").text().split("/")[0];
			$total_page = $(".pager:last").text().split("/")[1];
			if ($current_page == $total_page) {
				$(".see_more").hide();
			};
		},
		type: 'GET',
	})
}

function changeModal(obj,target) {
	$(obj).parents(".myModal").modal('hide');
	$(target).modal("show");
	modalButtonToggle(target);
	send_btn_toggle(target);
}

function modalButtonToggle(target) {
	var $disabled = false;
	$(target).find("input").each(function() {
		if($(this).val()=='') {
			$disabled = true;
		}
	})
	$(target).find("button[type=submit]").attr("disabled",$disabled);
}

function send_btn_toggle(target) {
	var $input = $(target).find("input[type=tel]");
	var $btn = $(target).find(".v_code").children("button");
	if ($input.val()) {
		$btn.attr("disabled",false);
	}else {
		$btn.attr("disabled",true);
	}
}

function send_f_code(obj) {
	var url = $(".send_forget_btn").data("send-reset-code");
	var phone = $("#forgetModal input[name=phone]").val();
	$.ajax({
		url: url,
		type: 'POST',
		dataType: "json",
		data:{
			phone: phone
		},
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					$(obj).attr("disabled","true");
					$(obj).addClass("sending");
					$(obj).html("<span id='send_font'><span id='count'>60</span>秒后重发</span>");
					$timer = setInterval(function() {
						$('#count').text($('#count').text()-1);
						if ($(obj).parents(".myModal").is(":hidden") || $('#count').text()==0) {
							clearInterval($timer);
							$(obj).removeAttr("disabled");
							$(obj).removeClass("sending");
							$(obj).html("发送验证码");
						};
					},1000);
				} else {
					alert(data.msg);
				};
			};
		}
	})
}


function send_v_code(obj) {
	var url = $(".send_btn").data("send-code");
	var phone = $("#registerModal input[name=phone]").val();
	$.ajax({
		url: url,
		type: 'POST',
		dataType: "json",
		data:{
			phone: phone
		},
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					$(obj).attr("disabled","true");
					$(obj).addClass("sending");
					$(obj).html("<span id='send_font'><span id='count'>60</span>秒后重发</span>");
					$timer = setInterval(function() {
						$('#count').text($('#count').text()-1);
						if ($(obj).parents(".myModal").is(":hidden") || $('#count').text()==0) {
							clearInterval($timer);
							$(obj).removeAttr("disabled");
							$(obj).removeClass("sending");
							$(obj).html("发送验证码");
						};
					},1000);
				} else {
					alert(data.msg);
				};
			};
		}
	})
}

function set_at_color() {
	$(".comment_line .comment_content").each(function() {
		if ($(this).text().indexOf("@")>-1 && $(this).text().indexOf("@")<$(this).text().length-1) {
			var string = $(this).html().split("@");
			string[1] = string[1].replace(/\s/,"</span> ");
			$(this).html(string.join("<span style='color:#8094CC;'>@"));
		};
	})
}