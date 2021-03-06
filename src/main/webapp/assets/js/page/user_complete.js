$(function() {
	initSelectJob();

	$("#avatar_input").on("change", function(e){
		var file = e.target.files[0];

		if (!file.type.match('image.*')) {
	    	return false;
		}

		var reader = new FileReader();
		reader.readAsDataURL(file);

		reader.onload = function(arg) {
	    	$("#avatar_preview").prop("src",arg.target.result);
		}
	});
})


$(".circle").click(function() {
	$(this).siblings("img").show();
	$("#user_complete_submit_btn").prop("disabled",false);
})

$(".checked_circle").click(function() {
	$(this).hide();
	$("#user_complete_submit_btn").prop("disabled",true);
})

$("#user_complete_submit_btn").click(function (e) {
	e.preventDefault();
    var options = {
        beforeSubmit: function () {
            // showLoader();
        },
        success: function (data) {
            // hideLoader();
            json = $.parseJSON(data);
            if (json.isSuccess == 1) {
                alert("保存成功");
            } else {
                alert(json.msg);
            }
        },
    };
    $("#user-complete-form").ajaxSubmit(options);
});
function initSelectJob() {
	function toggleModal() {
		if ($(".selectJobModal").is(":hidden")) {
			$(".select_job img").prop("src",$(".select_job img").prop("src").replace("down","up"));
			$(".selectJobModal").show();
		}
		else {
			$(".select_job img").prop("src",$(".select_job img").prop("src").replace("up","down"));
			$(".selectJobModal").hide();
		};
	}

	$(".select_job .block").click(function() {
		toggleModal();
	})

	$(".selectJobModal li").click(function() {
		if (!$(this).hasClass("active")) {
			$(".selectJobModal li").removeClass("active");
			$(this).addClass("active");
			$(".select_job .block").children("span").text($(this).text());
			$("input[name='profession']").val($(this).text());
		}
		toggleModal();
	})
}