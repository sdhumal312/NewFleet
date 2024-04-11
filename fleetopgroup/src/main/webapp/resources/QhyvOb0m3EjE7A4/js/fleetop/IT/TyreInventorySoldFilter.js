var AVAILABLE_TO_SOLD 	= 3;// tyreTyreConstant
var SCRAPED_TO_SOLD		= 4;

var TYRE_ASSIGN_STATUS_AVAILABLE = 1; // inventorytyredto
var TYRE_ASSIGN_STATUS_SCRAPED 	 = 3;

$(document).ready(function() {
	getTyre();
});

function getTyre(){
	var soldType 	= $("#soldType").val();
	var tyreStatus	= 0;
	
	if(soldType == AVAILABLE_TO_SOLD){
		tyreStatus = TYRE_ASSIGN_STATUS_AVAILABLE; // to search Available tyre
	}else if(soldType == SCRAPED_TO_SOLD){
		tyreStatus = TYRE_ASSIGN_STATUS_SCRAPED;// to search Scraped tyre
	}
	
		
	$("#tyreId").select2({
		minimumInputLength : 3, 
		minimumResultsForSearch : 10,
		multiple:!0,
		ajax : {
			url : "getAllTyreListByStatus.in",
			dataType : "json",
			type : "POST",
			contentType : "application/json",
			quietMillis : 50,
			data : function(e) {
				return {
					term : e,
					status : tyreStatus
				}
			},
			results : function(e) {
				return {
					results : $.map(e, function(e) {
						return {
							text : e.TYRE_NUMBER,
							slug : e.slug,
							id : e.TYRE_ID
						}
					})
				}
			}
		}
	});
	/*$('#tyreId').select2('data', {
		id : "",
		text : "All",
		value : ""
		});*/
}

function validateSoldTyreFilter (){
	if($("#soldType").val() == "" || $("#soldType").val() == undefined || $("#soldType").val() == null){
		showMessage('info','Please Select Sold Type');
		$("#soldType").focus();
		return false;
	}
	if($("#tyreId").val() == "" || $("#tyreId").val() == undefined || $("#tyreId").val() == null){
		showMessage('info','Please Select Tyre');
		$("#tyreId").focus();
		return false;
	}
}
