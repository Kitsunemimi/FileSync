$(".main").on("click", "#button-local", function() {
	loadTransition(".main", "./sync/local");
});

$(".main").on("submit", "#dir-select-form", function() {
	console.log($("#dir-select-form").serialize());
	loadTransitionPost(".main", $("#dir-select-form"), "./sync/local/test");
	event.preventDefault();
});
