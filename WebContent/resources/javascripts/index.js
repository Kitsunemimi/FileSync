var transitionOut = {opacity: 0, top: "25px"};
var transitionIn = {opacity: 1, top: "0px"};

function loadTransition(element, target) {
	$(element).animate(transitionOut, 150, "easeInQuad", function() {
		$(element).load(target, function() {
			$(element).css("top", "-25px");
			$(element).animate(transitionIn, 150, "easeOutQuad");
		});
	});
}

function loadTransitionPost(element, data, target) {
	$.post({
		'url': target,
		'data': data.serialize(),
		'success': function(response) {
			$(element).animate(transitionOut, 150, "easeInQuad", function() {
				$(element).html(response);
				$(element).css("top", "-25px");
				$(element).animate(transitionIn, 150, "easeOutQuad");
			});
		}
	});
}

$(document).ready(function() {
	$("#menu-sync").click(function() {
		loadTransition(".main", "./sync");
		$(".bg").css("background-image", "url('./resources/images/sync backsplash.png')");
		$(".bg").fadeIn();
		$.getScript("resources/javascripts/sync.js");
	});
});
