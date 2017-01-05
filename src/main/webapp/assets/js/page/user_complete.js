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