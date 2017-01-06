$(function(){
	$("#Carousel").carousel({interval:4000});
})

function go_lesson_list(obj) {
	var $logged = $(obj).data("logged");

	if ($logged) {
		// 跳转课程列表页
		// location.href=''
	} else {
		$("#loginModal").modal('show');
	};
}

function go_member_page(obj) {
	var $logged = $(obj).data("logged");

	if ($logged) {
		// 跳转会员套餐页
		location.href='/confirm/order.html'
	} else {
		$("#loginModal").modal('show');
	};
}

function go_course_page(obj) {
	var $logged = $(obj).data("logged");
	var $course_id = $(obj).data("course-id");

	if ($logged) {
		// 跳转课程列表页的某一指定
		location.href='/course/video/'+$course_id + '.html';
	} else {
		$("#loginModal").modal('show');
	};
}