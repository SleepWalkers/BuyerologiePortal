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

	var url = $(this).data("pay") + "?productId=" + $set + "&payType=" + $("input[type=radio]:checked").val();
	window.open(url);  
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