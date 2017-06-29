$(".main").on("click", "#button-local", function() {
	loadTransition(".main", "./sync/local");
});

$(".main").on("submit", "#dir-select-form", function() {
	console.log("Submitting dirs with " + $("#dir-select-form").serialize());
	loadTransitionPost(".main", $("#dir-select-form"), "./sync/local");
	event.preventDefault();
});
