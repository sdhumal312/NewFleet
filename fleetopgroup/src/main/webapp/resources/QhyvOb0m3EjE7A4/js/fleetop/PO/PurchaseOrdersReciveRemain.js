var incoiceId;
function receiveQuantityValidation(obj){
	var receivedId 	= obj.id;
	incoiceId 		= receivedId.split("_")[1];
		
	var receivedQuantity 	= Number($("#receivedQuantity1_" + incoiceId).val());
	var remQuantity			= Number($("#remainingQuantity_" + incoiceId).val());
	
	if(receivedQuantity > remQuantity) {
		$("#receivedQuantity1_" + incoiceId).val(0);
		showMessage('info', "Received Quantity Should Not be More Than " + remQuantity + "")
		return false;
	}
}