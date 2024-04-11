
var incoiceId;
function receiveQuantityValidation(obj){
	
	var receivedId 	= obj.id;
	incoiceId 		= receivedId.split("_")[1];
	console.log('inside receiveQuantityValidation .....', receivedId);
	console.log('inside receiveQuantityValidation incoiceId.....', incoiceId);
		
	var receivedQuantity 	= Number($("#receivedQuantity1_" + incoiceId).val());
	var remQuantity			= Number($("#remainingQuantity_" + incoiceId).val());
	
	console.log('remQuantity ', remQuantity);
	
	if(receivedQuantity > remQuantity) {
		$("#receivedQuantity1_" + incoiceId).val(0);
		showMessage('info', "Received Quantity Should Not be More Than " + remQuantity + "")
		return false;
	}
}