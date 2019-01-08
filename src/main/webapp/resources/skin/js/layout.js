$(document).ready(function() {
	$(".button-menu-toggle").click(function() {
		$('.nav-menu').toggle("slow");
	});

	if ($(".button-menu-toggle").css('display') == 'none') {

	} else {
		$(".menu .nav-items .nav-item a").click(function() {
			$('.nav-submenu').toggle("slow");
		});
	}

	var menues = $(".nav-menu a");
	menues.click(function() {
		menues.removeClass("active");
		$(this).addClass("active");
	});
});

function handleSubmit(xhr, status, args, dialog) {
	var jqDialog = dialog.jq;
	if (args.validationFailed) {
		jqDialog.effect('shake', {
			times : 3
		}, 100);
	} else {
		dialog.hide();
	}
}
