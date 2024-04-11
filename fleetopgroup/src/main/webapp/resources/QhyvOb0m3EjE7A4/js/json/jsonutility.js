/**
 * get data from server
 */
function getJSON(jsonObject, url, callbackfun, filter) {

	var Model = Backbone.Model.extend({});
	var model = new Model();

	model.url = url;
	if (jsonObject == null) {
		jsonObject	= new Object();
	}

	jsonObject.CorporateAccountId	= localStorage.getItem("currentCorporateAccountId");

	model.fetch({
		type: 'POST',
		data : jsonObject,
		success : function(collection, response, options) {
			executeGetJsonResult(response, callbackfun, filter);
		},
		error : function(err) {
			showMessage('error', 'Not able to execute process !')
			hideLayer();
		}
	});

	return true;
}

/**
 * execute JSON response
 */
function executeGetJsonResult (response, callbackfun, filter) {

	switch (filter) {
	case EXECUTE_WITHOUT_ERROR :

		/**
		 * display response message
		 */
		showResponseMessage(response);

		/**
		 * check if error found
		 */
		if (isError(response)) {
			return false;
		}

		/**
		 * execute callback function
		 */
		callbackfun(response);
		break;
	case EXECUTE_WITH_ERROR : 
		/**
		 * display response message
		 */
		showResponseMessage(response);

		/**
		 * execute callback function
		 */
		callbackfun(response);
		break;
	default:

		/**
		 * execute callback function
		 */
		callbackfun(response);
	break;
	}
	return true;
}

function getAllInputElementJSONObject() {
	var jsonOutObject = new Object();
	/**
	 * return values of input values as element name and element value pair 
	 */
	$('body :input').each(function (index){if($(this).val() != ""){jsonOutObject[$(this).attr('name')] = $.trim($(this).val());}});
	return jsonOutObject;
}

function getInputElementJSONObjectByDivId(divId) {
	var jsonOutObject = new Object();
	/**
	 * return values of input values as element name and element value pair 
	 */
	$('#'+divId +' :input').each(function (index){if($(this).val() != ""){jsonOutObject[$(this).attr('name')] = $.trim($(this).val());}});
	return jsonOutObject;
}

function getInputElementJSONObjectByElementIdArray(elementIdArray) {
	var jsonOutObject = new Object();
	/**
	 * return values of input values as element name and element value pair 
	 */
	$.each(elementIdArray, function( index, value ) {
		if($('#'+value).val() != ""){jsonOutObject[$('#'+value).attr('name')] = $.trim($('#'+value).val());};
	});

	return jsonOutObject;
}

function getJsonDataThroughAjax(inObject){
	$.getJSON(inObject.url, {json:JSON.stringify(inObject.inObject)}, function(data) {
		inObject.callbackFunction(data);
	}).error(function(e) { 
		showMessage('error', 'Not able to execute process !');
		hideLayer();
	})
}

function checkErrorInData(jsonInObj){
	if(jsonInObj.message != null && jsonInObj.message != undefined ){
		showMessage('error',jsonInObj.message.description);
		hideLayer();
		return false;
	}
	return true;
}