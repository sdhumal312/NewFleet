function showLayer() {
	$("#shadow").show();
	$("#question").show();
	$(".questionLayer").html("Please wait...");
	$(".questionLayer").show();
	$(".loader").show();
}

function hideLayer() {
	$("#shadow").hide();
	$("#question").hide();
	$(".questionLayer").hide();
	$(".loader").hide();
	$(".arrow").hide();
	$(".bounce").hide();
}

function showUpperSignLayer(message) {
	$("#shadow").show();
	$("#question").show();
	$(".bounce").show();
	$(".arrow").show();
	$(".questionLayer").html(message);
	$(".questionLayer").show();
}