var maxSizeOfFileToUpload	= 1;

/*
 * Function created to refresh and hide page with different behavior
 */
function refreshAndHidePartOfPage(divId, behavior) {	
	var ele				= null;
	
	ele		= document.getElementById(divId);
	
	if(ele != null) {
		
		if(behavior == 'hide') {
			$("#"+divId).hide();
		} else if(behavior == 'hideAndRefresh') {
			$("#"+divId).load(location.href+" #"+divId+">*", "");
			$("#"+divId).hide();
		} else if(behavior == 'refresh') {
			$("#"+divId).load(location.href+" #"+divId+">*", "");
		}
	}	
}

/*
 * Show part of the page
 */
function showPartOfPage(divId) {
	var ele				= null;
	
	ele		= document.getElementById(divId);
	
	if(ele != null) {
		$("#"+divId).show();
	}
	
}

/*
 * Change display property
 */
function changeDisplayProperty(id, displayType) {
	var ele				= null;
	
	ele		= document.getElementById(id);

	if(ele != null) {		
		ele.style.display	= displayType;		
	}
}

function changePageScrolling(id, scrollType) {
	var ele				= null;
	
	ele		= document.getElementById(id);

	if(ele != null) {		
		ele.style.overflow	= scrollType;		
	}
}

function changePageHeight(id, height) {
	var ele				= null;
	
	ele		= document.getElementById(id);

	if(ele != null) {		
		ele.style.height	= height;		
	}
}

function changePageWidth(id, width) {
	var ele				= null;
	
	ele		= document.getElementById(id);

	if(ele != null) {		
		ele.style.width	= width;		
	}
}

/*
 * Change visibility 
 */
function changeVisibility(eleId, visibleType) {
	var ele				= null;
	
	ele		= document.getElementById(eleId);

	if(ele != null) {
		ele.style.visibility = visibleType;
	}
}

/*
 * Remove table rows 
 */
function removeTableRows(tableId, type) {
	var ele				= null;
	
	ele		= document.getElementById(tableId);
	
	if(ele != null) {
		
		if(type == 'table') {
			$("#"+tableId+" tr").remove();
		} else if(type == 'tbody') {
			$("#"+tableId+" tbody tr").remove();
		} else if(type == 'tfoot') {
			$("#"+tableId+" tfoot tr").remove();
		} else if(type == 'thead') {
			$("#"+tableId+" thead tr").remove();
		}		
	}	
}

/*
 * Remove Html tag attribute with id or class
 */
function removeHtmlAttribute(filter, id, attribute) {
	if(filter == 1) {	//with class
		$("." + id).removeAttr(attribute);
	} else if(filter == 2) {	//with id
		$("#" + id).removeAttr(attribute);
	}
}

/*
 * Function created to get difference between two days
 */
function diffBetweenTwoDays(id) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	var oldDate		= null;
	
	if(ele != null) {
		oldDate		= ele.value;
	}
	
	var newDate		= oldDate.split('-');	
	
	var start 		= new Date(newDate[2], +newDate[1]-1, newDate[0]);
	
	var currentDate	= new Date();	
	var oneDay 		= 24 * 60 * 60 * 1000;
	var diffDays 	= (currentDate.getTime() - start.getTime())/oneDay;
	
	var days 		= Math.round(diffDays);
	
	return days;
}

/*
 * Function created to get difference between two date
 */
function diffBetweenTwoDate(currentDate, futureDate) {
	var oneDay 		= 24 * 60 * 60 * 1000; // hours*minutes*seconds*milliseconds
	
	var diffTime	= futureDate.getTime() - currentDate.getTime();

	var diffDays	= Math.round(diffTime / oneDay);
	
	return diffDays;
}

//remove row from array with particular index
function removeRowFromArray(totalSD, uniqueVal) {
	for(var i = totalSD.length - 1; i >= 0; i--) {
		if(totalSD[i] === uniqueVal) {
			totalSD.splice(i, 1);
		}
	}
	
	return totalSD;
}

/*
 * Reload the page within 1.5 second
 */
function reloadPage() {
	setTimeout(function(){
		window.location.reload(1);
	}, 1500); 
}

/*
 * Change text field color and set focus
 */
function changeTextFieldColor(textId, textColor, bgColorCode, borderColorCode) {
	var ele				= null;
	
	ele		= document.getElementById(textId);
	
	if(ele != null) {
		ele.style.color				= textColor;
		ele.style.borderColor 		= borderColorCode;
		ele.style.backgroundColor	= bgColorCode;
		ele.style.borderStyle		= 'solid';
		ele.focus();
	}
}

function resetError(obj) {
	if(document.getElementById(obj.id) != null) {
		document.getElementById(obj.id).borderStyle = '';
		document.getElementById(obj.id).borderColor = 'green';
	}
}

/*
 * Change text field color without focus
 */
function changeTextFieldColorWithoutFocus(textId, textColor, bgColorCode, borderColorCode) {
	var ele				= null;
	
	ele		= document.getElementById(textId);
	
	if(ele != null) {
		ele.style.color				= textColor;
		ele.style.borderColor 		= borderColorCode;
		ele.style.backgroundColor	= bgColorCode;
		ele.style.borderStyle		= 'solid';
	}
}

function changeHtmlTagColor(textId, textColor, bgColorCode, borderColorCode, borderStyle) {
	var ele				= null;
	
	ele		= document.getElementById(textId);
	
	if(ele != null) {
		ele.style.color				= textColor;
		ele.style.borderColor 		= borderColorCode;
		ele.style.backgroundColor	= bgColorCode;
		ele.style.borderStyle		= borderStyle;
	}
}

/*
 * validate input elements with different cases 
 */
function validateInputTextFeild(filter, elementID, errorElementId, messageType, errorMsg) {
	var element = document.getElementById(elementID);

	if(!element) {
		console.log('Element not found');
		return true;
	}

	switch (Number(filter)) {
	case 1:
		if (element.value == '' || element.value == 0) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			isValidationError	= true;
			return false;
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
			isValidationError	= false;
		}
		break;

	case 2:
		var reg = /^[6789]\d{9}$/ig;
		if(element.value.length == 10) {
			if( !element.value.match(reg)) {
				showMessage(messageType, errorMsg);
				changeTextFieldColor(errorElementId, '', '', 'red');
				isValidationError	= true;
				return false;
			} else {
				hideAllMessages();
				changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
			}
		}
		break;

	case 3:
		if (element.value == '' || element.value == -1) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			isValidationError	= true;
			return false;
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;

	case 4:
		if (element.value != '' && element.value != null && element.value.length != 11) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			isValidationError	= true;
			return false;
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
		
	case 5:
		if (element.value != '' && element.value != null && element.value.length != 10) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			isValidationError	= true;
			return false;
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
		
	case 6:
		if (element.value < 0) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			isValidationError	= true;
			return false;
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
		
	case 7:		//filter to validate for phone number
		if(element.value.length > 5 && element.value.length < 9 || element.value.length == 10 || element.value.length == 0) {
			changeDisplayProperty(elementID, 'none');
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
			return true;
		} else {
			if(element.value != '0000000000') {
				showMessage(messageType, errorMsg);
				changeDisplayProperty(elementID, 'block');
				changeTextFieldColor(errorElementId, '', '', 'red');
				isValidationError	= true;
				return false;
			}
		}
		break;
	case 8:		//filter to validate for Pan Number
		var pattern 	= /^([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}?$/;
		
		if(element.value != null && element.value != '' && element.value != 'PAN NUMBER') {
			if(!element.value.match(pattern)) {
				showMessage(messageType, errorMsg);
				changeTextFieldColor(elementID, '', '', 'red');
				isValidationError	= true;
				return false;
			} else {
				hideAllMessages();
				changeTextFieldColorWithoutFocus(elementID, '', '', 'green');
				return true;
			}
		}
		break;
	case 9:
		var gstReg = /\d{2}[a-zA-Z]{5}\d{4}[a-zA-Z]{1}\d[zZ]{1}[a-zA-Z\d]{1}/;
		if (element.value != '' && element.value != null && element.value.length != 15) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			element.focus();
			isValidationError	= true;
			return false;			
		} else if(element.value.length == 15){
			if( !element.value.match(gstReg)) {
				showMessage(messageType, gstnValidationErrMsg);
				changeTextFieldColor(errorElementId, '', '', 'red');
				element.focus();
				isValidationError = true;
				return false;
			}
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
	case 10:
		var gstRegex = /\d{2}[a-zA-Z]{5}\d{4}[a-zA-Z]{1}\d[zZ]{1}[a-zA-Z\d]{1}/;
		if (element.value != null && element.value.length != 15) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			element.focus();
			isValidationError	= true;
			return false;			
		} else if(element.value.length == 15){
			if( !element.value.match(gstRegex)) {
				showMessage(messageType, gstnValidationErrMsg);
				changeTextFieldColor(errorElementId, '', '', 'red');
				element.focus();
				isValidationError	= true;
				return false;
			}
		}else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
		
	case 11:
		if (element.value != '' && element.value != 0 && element.value.length != 6) {
			showMessage(messageType, errorMsg);
			changeTextFieldColor(errorElementId, '', '', 'red');
			element.focus();
			isValidationError	= true;
			return false;			
		} else {
			hideAllMessages();
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
		}
		break;
	case 12:
		if(element.value.length == 12) {
			//changeDisplayProperty(elementID, 'none');
			changeTextFieldColorWithoutFocus(errorElementId, '', '', 'green');
			return true;
		} else {
			showMessage(messageType, errorMsg);
			isValidationError	= true;
			element.focus();
			changeTextFieldColor(errorElementId, '', '', 'red');
			return false;
		}
		break;
	case 13:		//filter to validate for Tan Number
		var pattern 	= /^([a-zA-Z]){4}([0-9]){5}([a-zA-Z]){1}?$/;
		
		if(element.value != null && element.value != '' && element.value != 'TAN NUMBER') {
			if(!element.value.match(pattern)) {
				showMessage(messageType, errorMsg);
				changeTextFieldColor(elementID, '', '', 'red');
				isValidationError	= true;
				return false;
			} else {
				hideAllMessages();
				changeTextFieldColorWithoutFocus(elementID, '', '', 'green');
				return true;
			}
		}
		break;
	case 14:		//filter to validate for Aadhar Card Number
		var adharcardTwelveDigit 	= /^\d{12}$/;
        var adharSixteenDigit 		= /^\d{16}$/;
		
		if(element.value != null && element.value != '') {
			if (element.value.match(adharcardTwelveDigit)) {
				hideAllMessages();
				changeTextFieldColorWithoutFocus(elementID, '', '', 'green');
                return true;
            } else if (element.value.match(adharSixteenDigit)) {
            	hideAllMessages();
				changeTextFieldColorWithoutFocus(elementID, '', '', 'green');
                return true;
            } else {
            	showMessage(messageType, errorMsg);
				changeTextFieldColor(elementID, '', '', 'red');
				isValidationError	= true;
				return false;
            }
		}
		break;
	default:
		break;
	}

	return true;
}

/*
 * Get value from html tag
 */
function getValueFromHtmlTag(tagId) {
	var value	= null;
	var ele		= null;
	
	ele		= document.getElementById(tagId);
	
	if(ele != null) {
		value 	= $("#"+tagId).html();
	}
	
	return value;
}

/*
 * Set Value to html tag
 */
function setValueToHtmlTag(tagId, value) {
	var ele				= null;
	
	ele		= document.getElementById(tagId);
	
	if(ele != null) {
		ele.innerHTML	= value;
	}
}

/*
 * Get value from input field
 */
function getValueFromInputField(id) {
	var value	= null;
	var ele		= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		value	= $("#"+id).val();
	}
	
	return value;
}

/*
 * Get option text from option selected
 */
function getValueTextFromOptionField(id) {
	var value	= null;
	var ele		= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		value	= $("#"+id+" option:selected").text();
	}
	
	return value;
}

/*
 * Set value to input field
 */
function setValueToTextField(id, value) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	var newValue	= value;
	
	if(ele != null) {
		ele.value	= newValue;
	}
}

/*
 * create new options in HTML select pass HTMl select id , option key and value with Different Id and Value.
 */
function createOptionInSelectTag(id, optionId, optionValue, htmlText) {
	var newOption = $("<option/>");
	
	$('#'+id).append(newOption);
	
	newOption.attr('id', optionId);
	newOption.val(optionValue);
	newOption.html(htmlText);
}

/*
 * create new options in html on secong position select pass HTMl select id , option key and value.
 */
function createSecondOptionsInSelectTag(id, key, value) {
	$('#'+id+' option:first').after(
		$('<option />', {
			value :	key,
			text  :	value
		})
	);
}

/*
 * Remove option value from dropdown list with respect to value
 */
function removeOptionValFromList(id, value) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		$("#"+id+" option[value='"+value+"']").remove();
	}
}

/*
 * Get option Text from from dropdown list with respect to value
 */
function getOptionTextFromList(id, value) {
	var selectedName	= null;
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		selectedName	= $("#"+id+" option[value='"+value+"']").text();
	}
	
	return selectedName;
}

/*
 * Get selected text from dropdown list
 */
function getSeletedTextFromList(id) {
	
	var selectedText  	= null;
	var ele				= null;
	
	ele		= document.getElementById(id);

	if(ele != null) {
		selectedText	 = $("#"+id+" option:selected").text();
	}
	
	return selectedText;
}

/*
 * Get selected value from dropdown list
 */
function getSeletedValueFromList(id) {
	var ele				= null;
	var selectedVal  	= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		selectedVal	 = $("#"+id+" option:selected").val();
	}
	
	return selectedVal;
}

/*
 * Get all option value from dropdown list to Array
 */
function getAllOptionValueFromList(listId) {
	var ele				= null;
	var optionArray		= [];
	var list			= null;
	
	ele		= document.getElementById(listId);
	
	if(ele != null) {
		list = ele.options;
		
		optionArray		= new Array(list.length);
		
		for(var i = 0; i < list.length; i++) {
			
			if( list[i].value == "undefined"){
				optionArray[i]	= "0";
			} else{
				optionArray[i]	= list[i].value;
			}
			
		}
	}
	
	return optionArray;
}

/*
 * Remove All option form Select tag
 */
function removeAllOption(listId) {
	
	var ele	= document.getElementById(listId);
	
	if(ele != null) {
		ele.options.length = 0;
	}
}

/*
 * Check value in array
 */
function isValueExistInArray(arr, value) {

	for(var i = 0; i < arr.length; i++) {
		if(arr[i] == value) {
			return true;
		}
	}
	
	return false;
}

function operationOnSelectTag(selectId, optType, text, value) {
	var ele				= null;
	
	ele		= document.getElementById(selectId);
	
	if(ele != null) {
		if(optType == 'remove') {
			$("#"+selectId+" option[value='"+value+"']").remove();
		} else if(optType == 'removeAll') {
			$('#'+selectId).find('option').remove().end();
		} else if(optType == 'replaceAllAndAddNew') {
			$('#'+selectId).find('option').remove().end().append('<option value="'+value+'">'+text+'</option>');
		} else if(optType == 'addNew') {
			$('#'+selectId).append('<option value="'+value+'" id="'+value+'">'+text+'</option>');
		} else if(optType == 'prepend') {
			$('#'+selectId).prepend("<option value='"+value+"' selected='selected'>"+text+"</option>");
		}		 
	}
}

function createTable(id, className, border, width) {
	var	table 	= $(document.createElement('table'));
	
	table.attr("id", id);
	table.attr("class", className);
	table.attr("border", border);
	table.attr("width", width);
	
	return table; 
}

function createTableInHtml(id, className, border, width, style) {
	var	table 	= $(document.createElement('table'));
	
	table.attr("id", id);
	table.attr("class", className);
	table.attr("border", border);
	table.attr("width", width);
	table.attr("style", style);
	
	return table; 
}

//Create row in HTML table
function createRowInTable(Id, Class, Style){
	var newRow 	=  $('<tr/>');
	
	newRow.attr({
		id 		: Id,
		class	: Class,
		style	: Style
	});
	
	return newRow;
}

/*
 * create new column with parent id which have to append it and some general configuration.
 */
function createColumnInRow(tableRow, Id, Class, Width, Align, Style, Collspan) {
	var newCol 	=  $('<td/>');
	
	newCol.attr({
		id 			: Id,
		class		: Class,
		width		: Width,
		align		: Align,
		colspan 	: Collspan,
		style		: Style,
		valign		: "top"
	});
	
	$(tableRow).append(newCol);
	
	return newCol;
}

/*
 * Append value in column of table
 */
function appendValueInTableCol(col, value) {
	$(col).append(value);
}

/*
 * Append row in table
 */
function appendRowInTable(tableId, tableRow) {
	var ele				= null;
	
	ele		= document.getElementById(tableId);
	
	if(ele != null) {
		$('#'+tableId).append(tableRow);
	}
}

/*
 * Hide or Show table column
 */
function hideShowTableCol(colId, type) {
	var ele				= null;
	
	ele		= document.getElementById(colId);
	
	if(ele != null) {
		if(type == 'hide') {
			$('#'+colId).hide();
		} else if(type == 'show') {
			$('#'+colId).show();
		}
	}
}

/*
 * Switch old class to new class of any html tag
 */
function switchHtmlTagClass(tagId, newClass, oldClass) {
	var ele				= null;
	
	ele		= document.getElementById(tagId);

	if(ele != null) {
		$('#'+tagId).switchClass(newClass, oldClass);
		if(newClass == 'show'){
			$('#'+tagId).removeClass('hide');
		}else{
			$('#'+tagId).addClass('hide');
		}
	}
}

/*
 * Set value to any html tag or text-field
 */
function setValueToContent(id, contentType, value) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		if(contentType == 'text') {
			$('#' + id).text(value);
		} else if(contentType == 'htmlTag') {
			$('#' + id).html(value);
		} else if(contentType == 'formField') {
			$('#' + id).val(value);
		}
	}
}

/*
 * Create <ul> tag with parentId as id of tag to append and jsonobject of attribute
 */

function createUlTag(parentId, jsonObject) {
	var newItem = $("<ul />");
	$('#'+parentId).append(newItem);

	newItem.attr({
		id		: jsonObject.id
	});

	if(jsonObject.style) {
		newItem.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.class) {
		newItem.attr( {
			class : jsonObject.class
		});
	};

	if(jsonObject.html) {
		newItem.html(jsonObject.html);
	};

	return newItem;
}


/*
 * Create <li> tag with parentId as id of tag to append and jsonobject of attribute 
 */
function createLiTag(parentId, jsonObject) {
	var newItem = $("<li />");
	$('#'+parentId).append(newItem);

	newItem.attr({
		id		: jsonObject.id
	});

	if(jsonObject.style) {
		newItem.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.class) {
		newItem.attr( {
			class : jsonObject.class
		});
	};

	if(jsonObject.html) {
		newItem.html(jsonObject.html);
	};

	return newItem;
}

/*
 * create new <Span> with parent id which have to append it and some general configuration.
 */
function createSpanTag(parentId, jsonObject) {
	var newSpan 	=  $('<span/>');

	newSpan.attr({
		id		: jsonObject.id,
		class	: jsonObject.class
	});

	if(jsonObject.style) {
		newSpan.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.align) {
		newSpan.attr( {
			align : jsonObject.align
		});
	};

	if(jsonObject.html) {
		newSpan.attr( {
			html : jsonObject.html
		});
	};

	parentId.append(newSpan);
	return newSpan;
}

/*
 * create new <Div> with parent id which have to append it and some general configuration.
 */
function createDivTag(parentId, jsonObject) {
	var newDiv 	=  $('<div/>');

	$('#'+parentId).append(newDiv);
	
	newDiv.attr({
		id		: jsonObject.id,
		class	: jsonObject.class
	});

	if(jsonObject.style) {
		newDiv.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.align) {
		newDiv.attr( {
			align : jsonObject.align
		});
	};

	if(jsonObject.html) {
		newDiv.attr( {
			html : jsonObject.html
		});
	};

	return newDiv;
}

/*
 * create new label with parent id which have to append it and some general configuration.
 */
function createLabel(parentEle, Id, Value, Style, Class, For) {
	var labels = $('<label/>');
	$(parentEle).append(labels);

	labels.attr( {
		id			: Id,
		style		: Style,
		class		: Class,
		'for'		: For
	});

	labels.html(Value);
	return labels;
}

//Function to set check to check-box
function checkedUnchecked(id, trueFalse) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		if(trueFalse == 'true') {
			$('#'+id).prop('checked', true);
		} else if(trueFalse == 'false') {
			$('#'+id).prop('checked', false);
		}
	}
}

//Function to enable and disable input field
function enableDisableInputField(id, trueFalse) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		if(trueFalse == 'true') {
			ele.disabled  = true;
		} else if(trueFalse == 'false') {
			ele.disabled  = false;
		}
	}
}

//Function to get single check-box value
function getCheckedValue(id) {
	var ele				= null;
	var value			= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		value	= $('#'+id+':checked').val();
	}
	
	return value;
}

function isCheckBoxChecked(id) {
	var ele				= null;
	var value			= false;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		if(ele.checked == true) {
			value 	= true;
		} else {
			value 	= false;
		}
	}
	
	return value;
}

//Function to get All selected check-box value in array
function getAllCheckBoxSelectValue(inputName) {
	
	var checkBoxArray	= [];
	
	checkBoxArray	= new Array();
	
	$.each($("input[name="+inputName+"]:checked"), function() { 
		checkBoxArray.push($(this).val());
	});
	
	return checkBoxArray;
}

/*
 * Function to dispable the div tag
 */
function disableDiv(id, dis) {
	$('#'+id + ' :input').attr("disabled", dis);
}

function removeAllWhiteSpace(value) {
	value	= value.replace(/\s/g, '');
	
	value	= value.replace(/[()]/g, "");
	
	return value;
}

/*
 * Function to set class name table tag
 */
function setClassName(id, className) {
	var ele				= null;
	
	ele		= document.getElementById(id);
	
	if(ele != null) {
		ele.className	= className;
	}
}

/*
 * Function to remove column with sum of Zero in Table
 */
function removeColumnWithZeroTotal(id) {
	var ele	= document.getElementById(id);
	
	if(ele != null) {
		//Remove 0 value cols
		var lastRow  	= $('#'+id+' tr:last')[0];
		var firstRow 	= $('#'+id+' tr:first')[0];
		var noOfCells 	= lastRow.cells.length;
		var cellIndex 	= 0;
		var zeroCols 	= new Array();
		var idx 		= 0;
		
		for(var i = 0; i < noOfCells; i++) {
			if(lastRow.cells[i].colSpan > 1) {
				cellIndex = cellIndex + lastRow.cells[i].colSpan - 1;
			} else {
				cellIndex++;
			};
			
			if(parseInt(lastRow.cells[i].innerHTML, 10) == 0) {
				zeroCols[idx] = cellIndex;
				idx++;
			}
		}
		
		var delColNo 	= 0;
		var colNo 		= 0;
	
		for(var i = 0; i < zeroCols.length; i++) {
			colNo = (zeroCols[i] - delColNo);
			$('#'+id+' td:nth-child('+colNo+')').remove();
			delColNo++;
		}

		lastRow = $('#'+id+' tr:last')[0];
		
		for(var k = 0; k < lastRow.cells.length; k++) {
			if(parseInt(lastRow.cells[k].innerHTML, 10) == 0) {
				firstRow.deleteCell(k);
				lastRow.deleteCell(k);
				k--;
			};
		}	
	}
}

/*
 * Function to set readOnly to text field
 */
function setReadOnly(id, trueFalse) {
	var ele	= document.getElementById(id);
	
	if(ele != null) {
		ele.readOnly	= trueFalse;
	}
}

/*
 * Function to remove child element from List at particular position
 */
function removeChildEleFromList(id, position) {
	var list = document.getElementById(id);
	
	if(list != null) {
		list.removeChild(list.childNodes[position]);
	}
}

/*
 * Function to remove child element from parent element
 */
function removeChildEleFromParent(id) {
	var row = document.getElementById(id);
	
	if(row != null) {
		row.parentElement.removeChild(row);
	}
}

/*
 * Function to check value in table column 
 */
function checkValueInTableColumn(tableId, value) {
	var selectedTable	= document.getElementById(tableId);

	var result	= false;
	
	if(selectedTable != null) {
		if(selectedTable.rows.length > 0) {
			for(var i = 0; i < selectedTable.rows.length; i++) {
				var colValue	= selectedTable.rows[i].cells[0].innerHTML;
				
				if(value == colValue) {
					result	= true;
					break;
				} else {
					result	= false;
				}
			}
		}
	}
	
	return result;
}

/*
 * Function added to open div as Popup window
 */
function openDialog(id) {
	$("#"+id).dialog({
		modal: true,
		width:'auto',
		height: 'auto',
		minWidth: 700,
		maxWidth: 600,
		// minHeight: 500,
		//position: ['center', 50],
		position: { 
			my: "center", 
			at: "center", 
			of: window 
		},
		closeOnEscape: false,
		resizable: false,
	    show: {
	    	effect: "blind",
	    	duration: 1000
	    },
	    hide: {
	    	effect: "explode",
	    	duration: 1000
	    },
		draggable: true,
		close: function(ev, ui) {
			
		}
	}).css("font-size", "12px");
}

/*
 * Function added to close jQuery popup window
 */
function closeJqueryDialog(id) {
	
	var ele		= document.getElementById(id);
	
	if(ele != null) {
		$('#'+id).dialog('close');
	}
}

/*
 * Function to select all checkBox with one click
 */
function selectAllCheckBox(param, id) {
	var ele 	= document.getElementById(id);
	
	if(ele != null) {
		var count 	= parseFloat(ele.rows.length - 1);
	
		if(param == true) {
			for (var row = count; row > 0; row--) {
				if(ele.rows[row].style.display == '') {
					ele.rows[row].cells[0].firstChild.checked = true;
				}
			}
		} else if(param == false) {
	
			for (row = count; row > 0; row--) {
				if(ele.rows[row].style.display == '') {
					ele.rows[row].cells[0].firstChild.checked = false;
				}
			}
		}
	}
}

/*
 * Function created to empty inner value of any html tag
 */
function emptyInnerValue(id) {
	$('#'+id).empty();
}

/*
 * Function created to empty child value of any html tag
 */
function emptyChildInnerValue(id, childTag) {
	$('#'+id).children(childTag).empty();
}

/*
 * Function to delete row from table with index
 */
function deleteTableRow(id, index) {
	var row = document.getElementById(id);
	
	if(row != null) {
		row.deleteRow(index);
	}
}

/*
 * Function to count row in table
 */
function countTableRow(id) {
	var table = document.getElementById(id);
	
	var length = 0;
	
	if(table != null) {
		length = table.rows.length;
	}
	
	return length;
}

/*
 * This function is used to change the text to uppercase, lowercase or to capitalized.
 */
function setTextTransform(id, transFormType) {
	var ele 	= document.getElementById(id);
	
	if(ele != null) {
		ele.style.textTransform = transFormType;
	}
}

function getCurrentDate() {
	var today 		= new Date();
	var currentdate	= null;
	var dd 			= today.getDate();
	var mm 			= today.getMonth() + 1; //January is 0!

	var yyyy = today.getFullYear();

	if(dd < 10){
	   dd = '0' + dd;
	}  
	
	if(mm < 10) {
	    mm = '0' + mm;
	} 
	
	currentdate = dd + '-' + mm + '-' + yyyy;
	
	return currentdate;
}

function getCurrentDateSqlFormat() {
	var today 		= new Date();
	var currentdate	= null;
	var dd 			= today.getDate();
	var mm 			= today.getMonth() + 1; //January is 0!

	var yyyy = today.getFullYear();

	if(dd < 10){
	   dd = '0' + dd;
	}  
	
	if(mm < 10) {
	    mm = '0' + mm;
	} 
	
	currentdate = yyyy+ '-'+ mm + '-'+dd;
	
	return currentdate;
}

function getCurrentTime() {
	var dt 		= new Date();
	var hour 	= dt.getHours();
	var minute  = dt.getMinutes();
	if(hour < 10){
		hour = '0' + hour;
	} 
	
	if(minute < 10){
		minute = '0' + minute;
	} 
	
	return hour + ":" + minute;
}
/*
 * Convert datetimestamp to Date & time 
 */
function toJSDate (dateTime) {

	var dateTime = dateTime.split(" ");//dateTime[0] = date, dateTime[1] = time

	var date = dateTime[0].split("-");
	var time = dateTime[1].split(":");

	//(year, month, day, hours, minutes, seconds, milliseconds)
	return new Date(date[0], date[1]-1, date[2], time[0], time[1], time[2], 0);
	   
}

function changeAttributeOfJSEvent(id, eventType, value) {
	$('#'+id).attr(eventType, value);
}

/*
 * create All kind of input tag passed in json object with parent id which have to append it and some general configuration.
 */
function createInput(parentEle, jsonObject) {

	var inputs = $('<input type=' + jsonObject.type + ' />');
	
	parentEle.append(inputs);

	inputs.attr ({
		id			: jsonObject.id
		,name		: jsonObject.name
		,value		: jsonObject.value
	});

	if(jsonObject.style) {
		inputs.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.class) {
		inputs.attr( {
			class : jsonObject.class
		});
	};

	if(jsonObject.disabled) {
		inputs.attr( {
			disabled : jsonObject.disabled
		});
	};

	if(jsonObject.maxlength) {
		inputs.attr( {
			maxlength : jsonObject.maxlength
		});
	};

	if(jsonObject.readonly) {
		inputs.attr( {
			readonly : jsonObject.readonly
		});
	};

	if(jsonObject.onblur) {
		inputs.attr( {
			onblur : jsonObject.onblur
		});
	};

	if(jsonObject.onkeypress) {
		inputs.attr( {
			onkeypress : jsonObject.onkeypress
		});
	};

	if(jsonObject.onfocus) {
		inputs.attr( {
			onfocus : jsonObject.onfocus
		});
	};

	if(jsonObject.onkeydown) {
		inputs.attr( {
			onkeydown : jsonObject.onkeydown
		});
	};

	if(jsonObject.onkeyup) {
		inputs.attr( {
			onkeyup : jsonObject.onkeyup
		});
	};

	if(jsonObject.onclick) {
		inputs.attr( {
			onclick : jsonObject.onclick
		});
	};

	if(jsonObject.onmouseup) {
		inputs.attr( {
			onmouseup : jsonObject.onmouseup
		});
	};

	if(jsonObject.onmousedown) {
		inputs.attr( {
			onmousedown : jsonObject.onmousedown
		});
	};

	if(jsonObject.placeholder) {
		inputs.attr( {
			placeholder : jsonObject.placeholder
		});
	};
	
	if(jsonObject.checked) {
		inputs.attr( {
			checked : jsonObject.checked
		});
	};

	return inputs;
}

/*
 * create new <div> with parent id which have to append it and some general configuration.
 */

function createButton(parentEle, jsonObject) {
	var button = $('<button type=' + jsonObject.type +' />');
	
	parentEle.append(button);

	button.attr ({
		id			: jsonObject.id
		,name		: jsonObject.name
		,value		: jsonObject.value
	});

	if(jsonObject.class) {
		button.attr( {
			class : jsonObject.class
		});
	};

	if(jsonObject.onclick) {
		button.attr( {
			onclick : jsonObject.onclick
		});
	};

	if(jsonObject.html) {
		button.html(jsonObject.html);
	};

	if(jsonObject.style) {
		button.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.onmouseover) {
		button.attr( {
			onmouseover : jsonObject.onmouseover
		});
	};

	return button;
}

/*
 * Create <a> tag with id and value as html 
 */
function createHyperLink(jsonObject) {
	var newItem = $("<a />");

	newItem.attr({
		id		: jsonObject.id
	});

	newItem.attr({
		href		: jsonObject.href
	});

	if(jsonObject.style) {
		newItem.attr( {
			style : jsonObject.style
		});
	};

	if(jsonObject.class) {
		newItem.attr( {
			class : jsonObject.class
		});
	};

	if(jsonObject.html) {
		newItem.html(jsonObject.html);
	};

	if(jsonObject.onClick) {
		newItem.attr( {
			onClick : jsonObject.onClick
		});
	};

	return newItem;
}

function convertNumberToWord(inputNumber) {
    var str 	= new String(inputNumber)
    var splt 	= str.split("");
    var rev 	= splt.reverse();
    var once 	= ['Zero', ' One', ' Two', ' Three', ' Four', ' Five', ' Six', ' Seven', ' Eight', ' Nine'];
    var twos 	= ['Ten', ' Eleven', ' Twelve', ' Thirteen', ' Fourteen', ' Fifteen', ' Sixteen', ' Seventeen', ' Eighteen', ' Nineteen'];
    var tens 	= ['', 'Ten', ' Twenty', ' Thirty', ' Forty', ' Fifty', ' Sixty', ' Seventy', ' Eighty', ' Ninety'];

    numLength 	= rev.length;
    var word 	= new Array();
    var j 		= 0;

    for (i = 0; i < numLength; i++) {
        switch (i) {
            case 0:
                if ((rev[i] == 0) || (rev[i + 1] == 1)) {
                    word[j] = '';
                } else {
                    word[j] = '' + once[rev[i]];
                }
                word[j] = word[j];
                break;

            case 1:
                aboveTens();
                break;

            case 2:
                if (rev[i] == 0) {
                    word[j] = '';
                } else if ((rev[i - 1] == 0) || (rev[i - 2] == 0)) {
                    word[j] = once[rev[i]] + " Hundred ";
                } else {
                    word[j] = once[rev[i]] + " Hundred and";
                }
                break;

            case 3:
                if (rev[i] == 0 || rev[i + 1] == 1) {
                    word[j] = '';
                } else {
                    word[j] = once[rev[i]];
                }
                
                if ((rev[i + 1] != 0) || (rev[i] > 0)) {
                    word[j] = word[j] + " Thousand";
                }
                break;

                
            case 4:
                aboveTens();
                break;

            case 5:
                if ((rev[i] == 0) || (rev[i + 1] == 1)) {
                    word[j] = '';
                } else {
                    word[j] = once[rev[i]];
                }
                
                if (rev[i + 1] !== '0' || rev[i] > '0') {
                    word[j] = word[j] + " Lakh";
                }
                 
                break;

            case 6:
                aboveTens();
                break;

            case 7:
                if ((rev[i] == 0) || (rev[i + 1] == 1)) {
                    word[j] = '';
                } else {
                    word[j] = once[rev[i]];
                }
               
                if (rev[i + 1] !== '0' || rev[i] > '0') {
                    word[j] = word[j] + " Crore";
                }                
                break;

            case 8:
                aboveTens();
                break;

            default: break;
        }
        j++;
    }

    function aboveTens() {
        if (rev[i] == 0) { word[j] = ''; }
        else if (rev[i] == 1) { word[j] = twos[rev[i - 1]]; }
        else { word[j] = tens[rev[i]]; }
    }

    word.reverse();
    var finalOutput = '';
   
    for (i = 0; i < numLength; i++) {
        finalOutput = finalOutput + word[i];
    }
    
    return finalOutput;    
}

function getDateFromTimestamp(timestamp, filter) {
	var date	= null;
	
	date	= timestamp;
	date 	= date.substring(0, date.length - 5);

	if(filter == 1) {		//only for date
		date 	= date.substring(0, 10);
	} else if(filter == 2) {	//date in AM - PM
		date1 	= date.substring(0, 10);
		time 	= date.substring(14, 16);

		var hour = date.substring(11, 13);
		
		if(hour > 12) {
			date = date1 + " " + (hour - 12) + ":" + time + " PM";
		} else {
			date = date + " AM";
		}
	}
	
	return date;
}

function convertTimestamp(timestamp) {
	var d 	= new Date(timestamp * 1000),	// Convert the passed timestamp to milliseconds
	
	yyyy 	= d.getFullYear(),
	mm 		= ('0' + (d.getMonth() + 1)).slice(-2),	// Months are zero based. Add leading 0.
	dd 		= ('0' + d.getDate()).slice(-2),			// Add leading 0.
	hh 		= d.getHours(),
	h 		= hh,
	min 	= ('0' + d.getMinutes()).slice(-2),		// Add leading 0.
	ampm 	= 'AM',
	time;

	if (hh > 12) {
		h 		= hh - 12;
		ampm 	= 'PM';
	} else if (hh === 12) {
		h 		= 12;
		ampm 	= 'PM';
	} else if (hh == 0) {
		h 		= 12;
	}

	// ie: 2013-02-18, 8:35 AM	
	time = yyyy + '-' + mm + '-' + dd + ', ' + h + ':' + min + ' ' + ampm;

	return time;
}

function setMonthYear(obj) {
    if((obj.value.length == 1 && obj.value > 31) || (obj.value.length == 2 && obj.value > 31)) {
    	obj.value	= '';
        return true;
    }

    if(obj.value.length == 2) {
		 var today		= curSystemDate;
		 var currMonth 	= today.getMonth() + 1;
		
		 if (currMonth/10 < 1)
			 currMonth = '0' + currMonth;
        
		 var currYear  	= today.getFullYear();
         obj.value		= obj.value + '-' + currMonth + '-' + currYear;
	}
}

function getDateInDMYFromTimestamp(timestamp) {
	var dateInDMY	= '';
	
	if(timestamp != undefined) {
		var Date 	= (timestamp).substring(0, 16) ;
		var year  	= Date.substring(0, 4);
		var month 	= Date.substring(5, 7);
		var day	  	= Date.substring(8, 10);
		
		dateInDMY 	= day + "-" + month + "-" + year;
	}
	
	return dateInDMY;
}

//Validates Date in mm-dd-yyyy format
function isValidDate(date) {
    if ( date.match(/^(\d{1,2})\-(\d{1,2})\-(\d{4})$/) ) {
        var dd = RegExp.$1;
        var mm = RegExp.$2;
        var yy = RegExp.$3;

        // try to create the same date using Date Object
        var dt = new Date(parseFloat(yy), parseFloat(mm) - 1, parseFloat(dd), 0, 0, 0, 0);
        // invalid day
        if ( parseFloat(dd) != dt.getDate() ) { return false; }
        // invalid month
        if ( parseFloat(mm)-1 != dt.getMonth() ) { return false; }
        // invalid year
        if ( parseFloat(yy) != dt.getFullYear() ) { return false; }
        // everything fine
        return true;
    } else {
        // not even a proper date
        return false;
    }
}

function resetTextFeild(obj, defaultVal) {
	var value		= obj.value;
	
	if(value == defaultVal) {
		obj.value	= '';
	}
}

function clearIfNotNumeric(obj, text) {
	var textValue = obj.value;

	if(obj.value.length > 0 && isNaN(obj.value)) {
		obj.value	= text;
		alert('Invalid Number !');
		setTimeout(function(){if(obj)obj.focus();obj.select();},100); // Used to set focus as obj.focus(); doesn't work after alert90
		return false;
	} else if(textValue == '') {
		obj.value = text;
	} else {
		text.value = textValue;
	}
	
	return false;
}

function getKeyCode(event) {
	return event.which || event.keyCode;
}

function closeJqueryDialog(id) {
	if(document.getElementById(id) != null) {
		$('#'+id).dialog('close');
	}
}

function fillclearText(text, text1) {
	var textValue = text.value;
	
	if(textValue == '') {
		text.value = text1;
	} else {
		text.value = textValue;
	};
}

function fillclearTextArea(text, text1) {
	var textValue = text.value;
	
	if(textValue == '') {
		text.value = text1;
	};
}

function noNumbers(evt){
	if (evt.ctrlKey ==1){
		return true;
	}else{
		var keynum = null;
		if(window.event){ // IE
			keynum = evt.keyCode;
		} else if(evt.which){ // Netscape/Firefox/Opera
			keynum = evt.which;
		}
		if(keynum!=null){
			if(keynum == 8){
				return true;
			} else if(keynum == 45 || keynum == 47) {
				return true;
			} else if (keynum < 48 || keynum > 57 ) {
				return false;
			}
		}
		return true;
	}
}

function validateFloatKeyPress(evt,_this) {
	if (evt.ctrlKey ==1){
		return true;
	} else{
		var charCode = (evt.which) ? evt.which : evt.keyCode;
		if (charCode != null) {
			if (charCode == 8) {
				return true;
			} else if (charCode == 46) {
		        if (_this.value.indexOf('.') === -1) {
		            return true;
		        } else {
		            return false;
		        }
		    } else {
		        if (charCode > 31
		             && (charCode < 48 || charCode > 57))
		            return false;
		    }
		}
	}
    //get the carat position
	var number = _this.value.split('.');
    var caratPos = getSelectionStart(_this);
    var dotPos = _this.value.indexOf(".");
    if( caratPos > dotPos && dotPos>-1 && (number[1].length > 1)){
        return false;
    }
    return true;
}

function getSelectionStart(o) {
    if (o.createTextRange) {
        var r = document.selection.createRange().duplicate()
        r.moveEnd('character', o.value.length)
        if (r.text == '') return o.value.length
        return o.value.lastIndexOf(r.text)
    } else return o.selectionStart
}
function sortDropDownList(targetId) {
	var ddl 			= document.getElementById(targetId);
	var selectedValue 	= ddl.options[ddl.selectedIndex].value;
	var selectedIndex 	= null;
	var arrTexts 		= new Array();
	var txtAndVal 		= new Array();

	for(var i = 0; i < ddl.length; i++) {
		arrTexts[i] 	= ddl.options[i].text.toLowerCase() + '$$' + ddl.options[i].text + '^^' + ddl.options[i].value;
	}
	
	arrTexts.sort();
	
	for(i = 0; i< ddl.length; i++) {
		txtAndVal 				= arrTexts[i].split("$$")[1].split("^^");
		ddl.options[i].text 	= txtAndVal[0];
		ddl.options[i].value 	= txtAndVal[1];
		
		if(txtAndVal[1] == selectedValue) {
			selectedIndex = i;
		}
	}
	
	ddl.options.selectedIndex	= selectedIndex;
}

function parseDate(str) {
    var mdy = str.split('-');
    return new Date(mdy[2], mdy[1] - 1, mdy[0]);
}

/*
 * pass date object with identifier and get normal for view
 */
function dateWithDateFormatForCalender(dateObject, identifier) {
	var d = new Date(dateObject);

	if(d == 'Invalid Date' || d == 'NaN') {
		var t = dateObject.split(/[- :]/);
		d = new Date(t[0], t[1]-1, t[2], t[3], t[4], t[5]);
	}

	var day 	= d.getDate();
	var month 	= d.getMonth() + 1;
	var year 	= d.getFullYear();
	
	if (day < 10) {
		day = "0" + day;
	}
	
	if (month < 10) {
		month = "0" + month;
	}
	
	var date = day + identifier + month + identifier + year;

	return date;
}

function goToPosition(elementId, slideSpeed) {
	$('html,body').animate({
	    scrollTop: $("#" + elementId).offset().top},
	    slideSpeed);
}

//######################################################################

function pad2(n) { return n < 10 ? '0' + n : n; }
function pad3(n) { return n < 10 ? '00' + n : (n < 100 ? '0' + n : n); }

//class Date : method toDateTimeString extension
//Date+Time
function toDateTimeString(x) {
	x = x instanceof Date ? x : this instanceof Date ? this : new Date;
	
	return x.getFullYear() + '-' +
	 pad2(x.getMonth() + 1) + '-' +
	 pad2(x.getDate()) + ' ' +
	 pad2(x.getHours()) + ':' +
	 pad2(x.getMinutes()) + ':' +
	 pad2(x.getSeconds()) + '.' +
	 pad3(x.getMilliseconds());
}

//Date
function toDateString(x, filter) {
  x = x instanceof Date ? x : this instanceof Date ? this : new Date;
  
  var date	= '';

  switch (filter) {
  case 1: //dd-mm-YYYY
	  date	= pad2(x.getDate()) + '-' + pad2(x.getMonth() + 1) + '-' + x.getFullYear();
	  break;
  case 2://YYYY-mm-dd
	  date	= x.getFullYear() + '-' + pad2(x.getMonth() + 1) + '-' + pad2(x.getDate());
	  break;
  default:
	  break;
  }

  return date;
}

//Time
function toTimeString(x) {
  x = x instanceof Date ? x : this instanceof Date ? this : new Date;
  
  return pad2(x.getHours()) + ':' +
    pad2(x.getMinutes()) + ':' +
    pad2(x.getSeconds()) + '.' +
    pad3(x.getMilliseconds());
}

//######################################################################

var weekNames = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
var monthNames = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                  'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
// class Date : method toHttpDate extension
// Date+Time
// "Thu, 01 Dec 1994 16:00:00 GMT"
function toHttpDate(x) {
  x = x instanceof Date ? x : this instanceof Date ? this : new Date;
  
  return weekNames[x.getUTCDay()] + ', ' +
    pad2(x.getUTCDate()) + ' ' +
    monthNames[x.getUTCMonth()] + ' ' +
    x.getUTCFullYear() + ' ' +
    pad2(x.getUTCHours()) + ':' +
    pad2(x.getUTCMinutes()) + ':' +
    pad2(x.getUTCSeconds()) + ' GMT';
}

function destroyBTModel() {
	$( '.modal' ).modal( 'hide' ).data( 'bs.modal', null );
	$('.modal').remove();
	$('.modal-backdrop').remove();
	$('body').removeClass( "modal-open" );
}

function noSpclChars(e){

	var keynum =null;

	if(window.event){ // IE
		keynum = e.keyCode;
	} else if(e.which) {// Netscape/Firefox/Opera
		keynum = e.which;
	}
	if(keynum == 8 || keynum == 95 ){
		return true;
	}
	if ((keynum > 32 && keynum < 48)|| (keynum > 57 && keynum < 65)|| (keynum > 90 && keynum < 97)|| (keynum > 122 && keynum < 127) ) {
		return false;
	}
	return true;
}

function validAmount(evt){
	if (evt.ctrlKey ==1){
		return true;
	}else{
		var keynum = null;
		if(window.event){ // IE
			keynum = evt.keyCode;
		} else if(evt.which){ // Netscape/Firefox/Opera
			keynum = evt.which;
		}
		if(keynum!=null){
			if(keynum == 8  || keynum == 46 ){
				return true;
			}
			if (keynum < 48 || keynum > 57 ) {
				return false;
			}
		}
		return true;
	}
}

function validateFileTypeAndSize() {
	$('#photo').bind('change', function() {
		if (this.files && this.files[0]) {
			var sFileName 		= this.files[0].name;
			var fileSize		= this.files[0].size / 1048576;  //size in mb 
			var elementId		= $(this).attr('id');
			
			var sFileExtension 	= sFileName.split('.')[sFileName.split('.').length - 1].toLowerCase();
			
			if(!(sFileExtension === 'jpg' || sFileExtension === 'jpeg' || sFileExtension === 'png')) {
				showMessage('info', validImageFileInfoMsg);
				$.trim($(this).val(''));
				return false;	
			} else if(fileSize > 1) { //in mb
				showMessage('info', maxFileSizeInfoMsg(maxSizeOfFileToUpload));
				$.trim($(this).val(''));
				return false;	
			}
			
			var fileReader	= new FileReader();

			fileReader.readAsDataURL(this.files[0]);
		}
		
		return true;
	});
}

function validateFileTypeAndSizeForMultiPhoto(noOfFileToUpload, maxSizeOfFileToUpload) {
	for(var i = 1; i <= noOfFileToUpload; i++) {
		
		$('#photo_' + i).bind('change', function() {
			if (this.files && this.files[0]) {
				var sFileName 		= this.files[0].name;
				var fileSize		= this.files[0].size / 1024;  //size in kb 
				var elementId		= $(this).attr('id');
				
				var sFileExtension 	= sFileName.split('.')[sFileName.split('.').length - 1].toLowerCase();
				
				if(!(sFileExtension === 'jpg' || sFileExtension === 'jpeg' || sFileExtension === 'png')) {
					showMessage('info', validImageFileInfoMsg);
					$.trim($(this).val(''));
					return false;	
				} else if(fileSize > maxSizeOfFileToUpload) { //in kb
					showMessage('info', maxFileSizeInfoMsg(maxSizeOfFileToUpload));
					$.trim($(this).val(''));
					return false;	
				}
				
				var fileReader	= new FileReader();
	
				fileReader.readAsDataURL(this.files[0]);
			}
			
			return true;
		});
	}
}


function isNumberKeyWithDecimal(evt,id){ 
	try{

        var charCode = (evt.which) ? evt.which : event.keyCode;
        var txt=document.getElementById(id).value;
  
       if($('#validateTripRoutePoint').val() == 'true' || $('#validateTripRoutePoint').val() == true){
		   if(id == ('RoutePoint'+$('#driver1Id').val())){
					if(charCode==49 || charCode==50){
						return true;
					}else{
						showMessage('info', "Please fill either 1 or 2");
			            document.getElementById(id).value = ''; // Clear the input field
			            return false;
					}
			   }
		}
       
        if(charCode==46){
         
            if(!(txt.indexOf(".") > -1)){
	
                return true;
            }
        }
        if (charCode > 31 && (charCode < 48 || charCode > 57) )
            return false;

        if(txt.indexOf(".") > -1 && (txt.split('.')[1].length > 1)){
            event.preventDefault();
        }
        return true;
	}catch(w){
		alert(w);
	}
}

function isNumberKey(evt)
{	
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
}

function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) { 
		var R = 6371; // Radius of the earth in km 
		var dLat = deg2rad(lat2-lat1);
		var dLon = deg2rad(lon2-lon1); 
		var a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2) ;
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		var d = R * c; // Distance in km 
		return d; 
} 
function deg2rad(deg) { 
	return deg * (Math.PI/180) ;
}

function isMobileNum(evt)
{
	var inputMobileNum = document.getElementById(evt.id);
	var reg = /^[6789]\d{9}$/ig;

	if(inputMobileNum.value.length == 10  && inputMobileNum.value.match(reg))
	{
		return true;
	}
	else
	{
		inputMobileNum.value = "";
		document.getElementById(evt.id).focus();
		showMessage('info','Please Enter Valid Mobile Number');
		return false;
	}

}

function isDecimalNumber(evt, element){
	  var charCode = (evt.which) ? evt.which : event.keyCode
			    if (            
			        (charCode != 46 || $(element).val().indexOf('.') != -1) &&      // “.” CHECK DOT, AND ONLY ONE.
			        (charCode < 48 || charCode > 57))
			        return false;
			        return true;

}


function validatedate(inputText)
{
var dateformat = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
// Match the date format through regular expression
if(inputText.value.match(dateformat))
{
document.form1.text1.focus();
//Test which seperator is used '/' or '-'
var opera1 = inputText.value.split('/');
var opera2 = inputText.value.split('-');
lopera1 = opera1.length;
lopera2 = opera2.length;
// Extract the string into month, date and year
if (lopera1>1)
{
var pdate = inputText.value.split('/');
}
else if (lopera2>1)
{
var pdate = inputText.value.split('-');
}
var dd = parseInt(pdate[0]);
var mm  = parseInt(pdate[1]);
var yy = parseInt(pdate[2]);
// Create list of days of a month [assume there is no leap year by default]
var ListofDays = [31,28,31,30,31,30,31,31,30,31,30,31];
if (mm==1 || mm>2)
{
if (dd>ListofDays[mm-1])
{
alert('Invalid date format!');
return false;
}
}
if (mm==2)
{
var lyear = false;
if ( (!(yy % 4) && yy % 100) || !(yy % 400)) 
{
lyear = true;
}
if ((lyear==false) && (dd>=29))
{
alert('Invalid date format!');
return false;
}
if ((lyear==true) && (dd>29))
{
alert('Invalid date format!');
return false;
}
}
}
else
{
alert("Invalid date format!");
document.form1.text1.focus();
return false;
}
}


function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
}
function createSelect2Div(divId, labelName, Id){
 return ' <div class="row1" id="'+divId+'" class="form-group">'
	+ '<label class="L-size control-label" for="'+Id+'">'+labelName+' :<abbr title="required">*</abbr></label>'
	+ '<div class="I-size"> '
	+ '<input type="hidden" id="'+Id+'" '
	+ 'name="'+Id+'" style="width: 100%;" '
	+ 'placeholder="Please Enter 2 or more '+labelName+' Name" />'
	+ '</div>'
	+ '</div>'
}

function formatDate(date) {
	var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear(),
	hour = ''+ d.getHours(),
	mintes = ''+d.getMinutes(),
	seconds = ''+d.getSeconds(); 
	
if (month.length < 2) 
    month = '0' + month;
if (day.length < 2) 
    day = '0' + day;

if (hour.length < 2) 
	hour = '0' + hour;

if (mintes.length < 2) 
	mintes = '0' + mintes;

if (seconds.length < 2) 
	seconds = '0' + seconds;

return [day, month, year].join('-')+' '+ [hour, mintes, seconds].join(': ') ;
}
function formatOnlyDate(date) {
	var d = new Date(date),
    month = '' + (d.getMonth() + 1),
    day = '' + d.getDate(),
    year = d.getFullYear();
	
if (month.length < 2) 
    month = '0' + month;
if (day.length < 2) 
    day = '0' + day;

return [day, month, year].join('-');
}
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}
function replacer(i, val) {
     if ( typeof(val) === 'number' ) 
     { 
        return val.toFixed(2); // change null to empty string
     } else {
        return val; // return unchanged
     }
}
