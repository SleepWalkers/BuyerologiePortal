//$(document).ready(function() {
//	alertPaySuccess();
//})

function alertPayNotice() {
	$("#payNoticeModal").modal("show");
}

function alertPaySuccess() {
	$("#paySuccessModal").modal("show");
}

$("#pay_order").click(function(e) {
	e.preventDefault();

	var url = $(".j_PayUrl").val();
	// 支付宝
	if ($(".j_PayType").val() == '1') {
		// 订单支付页面url
		window.open(url);
		alertPayNotice();
	}
	// 微信支付
	else if ($(".j_PayType").val() == '2') {
		$("#weixinModal").modal('show');
		$.ajax({
			url: url,
			type: 'POST',
			dataType: 'json',
			success: function(data) {
				if (data.isRedirect) {
					location.href=data.redirectURL;
				} else {
					if (data.isSuccess) {
						// 显示二维码和弹窗						
						$("#weixinModal #qr_code").prop("src","/trade/pay/getqrcode?codeUrl="+data.data);
						
						var interval = setInterval(function(){
							$.ajax({
								url: "/trade/isPaid",
								data:{
									orderNumber: $(".j_OrderNumber").val()
								},
								type: 'POST',
								dataType: 'json',
								success: function(data) {
									if(data.data){
										alertPaySuccess();
										$("#weixinModal").modal("hide");
										clearInterval(interval);
									}
								}
							});
						},3000);
					} else {
						alert(data.msg);
					};
				};
			},
		});
	};	
})
$("#cancel_order").click(function(e) {
	e.preventDefault();
	history.back();
})
