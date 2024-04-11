var myMessages = [ 'info', 'warning', 'errors', 'success' ]; // define the messages types

function hideAllMessages() {
	var messagesHeights = new Array(); // this array will store height for each
	//alert('1');
	for (var i = 0; i < myMessages.length; i++) {
		messagesHeights[i] = $('.' + myMessages[i]).outerHeight();
		$('.' + myMessages[i]).css('top', -messagesHeights[i]); //move element outside view port
		
		$('.' + myMessages[i]).addClass("hideElemnt");
		$('.' + myMessages[i]).removeClass("showElemnt");
	}
}

function showMessage(type, msg) {

	setTimeout(function(){
		hideAllMessages();
	},5000);
	
	$('.' + type).addClass("showElemnt");
	$('.' + type).removeClass("hideElemnt");
	$('.' + type).html('<h2>'+msg+'<span style="padding-left: 100px;"><span onclick="hideAllMessages()" ><span class="glyphicon glyphicon-remove-sign"></span> Close</span></span></h2>');
	$('.' + type).animate({
		top : "0"
	}, 400);
}


function showMessage1(type, msg) {
	$('.' + type).addClass("showElemnt");
	$('.' + type).removeClass("hideElemnt");
	$('.' + type).html('<h2>'+msg+'<span style="widht: 20%; padding-left: 100px;"><span onclick="hideAllMessages()" ><span class="glyphicon glyphicon-remove-sign"></span> Close</span></span></h2>');
	$('.' + type).animate({
		top : "170",
		width : "30%",
		left : "70"
			
	}, 400);
}

$(document).ready(function() {

	// Initially, hide them all
	hideAllMessages();
	// When message is clicked, hide it
	$('.message').click(function() {
		$(this).animate({
			top : -$(this).outerHeight()
		}, 500);
	});
});