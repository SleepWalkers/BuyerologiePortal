$(function() {
	toggleSet();
})

$(".course").click(function() {
	if (!$(this).hasClass("active")) {
		$(this).parents(".sets").find(".course").removeClass("active");
		$(this).addClass("active");

		toggleSet();
	};
})

$("#pay_btn").click(function(e) {
	e.preventDefault();

	$.ajax({
		url: $(this).data("pay"),
		data: JSON.stringify({
			set: $set,
			price: $price,
			way: $("input[type=radio]:checked").val()
			//...
		}),
		success: function(data) {
			if (data.isRedirect) {
				location.href=data.redirectURL;
			} else {
				if (data.isSuccess) {
					// 返回购买套餐名称
					$("#paySuccessModal .set_type").text(data.data.set_type);
					$("#paySuccessModal").modal('show');
				} else {
					alert(data.msg);
				};
			};
		},
		contentType: 'application/json',
		type: 'POST',
	}).fail(function() {
		// 跳转失败弹窗
		$("#payFailedModal").modal('show');
	})
})

$("#cancel_btn").click(function(e) {
	e.preventDefault();
	history.back();
})

function toggleSet() {
	$set = $(".course.active").data("set");
	$price = parseFloat($(".course.active .price").text().substring(1,$(".course.active .price").text().length))
	$(".description").hide();
	$(".set"+$set).show();
	$(".payment_amount").text("¥"+$price.toFixed(2))
}