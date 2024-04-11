function validateDepotWiseDailyTripCollectionReport(){
		if(Number($('#TCGroupWise').val())  <= 0){
				showMessage('info','Please Select Depot Name !');
					return false;
		}	
}

