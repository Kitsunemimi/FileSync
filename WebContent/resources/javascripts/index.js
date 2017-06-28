var transitionOut = {opacity: 0, top: "30px"};
var transitionIn = {opacity: 1, top: "0px"};


$(document).ready(function() {
	$("#menu-sync").click(function() {
		$(".main").animate(transitionOut, 150, function() {
			$(".main").load("derp.jsp", function() {
				$(".main").css("top", "-30px");
				$(".main").animate(transitionIn, 150);
			});
		});
	});
});
