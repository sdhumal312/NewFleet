var TotalTyreSoldAmount = 0;
var tyreArray = new Array();
$(document).ready(function() {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["invoiceId"]		= $('#invoiceId').val();
	$.ajax({
		url: "getTyreSoldDetailsByTyreSoldInvoiceId",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setsoldTyreDetails(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
});

function setsoldTyreDetails(data){
	$("#showTyreTable").empty();
	
	var tyreList = data.tyreSoldDetails ;
	if( tyreList != undefined || tyreList.length > 0  ){
	
		for(var index = 0 ; index < tyreList.length; index++){
	
		var columnArray = new Array();
		
		columnArray.push('<td class="fit">'+(index+1)+'</td>');
		columnArray.push('<td class="fit" style="vertical-align: middle;"> <h4> '+ tyreList[index].tyre_NUMBER +"-"+ tyreList[index].tyre_SIZE +' </h4> ' 
				+' <div class="col-md-9"> <table class="table"> <tbody style="border-top: 0px;"> <tr class="ng-scope"> <td colspan="2">'
					+' <div id="inputField" class="row1">'
					+' <a id="addLabor'+tyreList[index].tyre_ID+'" href="javascript:toggle2Labor(changeLabor'+tyreList[index].tyre_ID+', addLabor'+tyreList[index].tyre_ID+');">Enter Sold Tyre Amount </a>'
					+' </div>'
					+' <div id="changeLabor'+tyreList[index].tyre_ID+'" style="display: none">'
						+' <div class="row1">'
			        		+' <div class="col-md-9 form-group">'
				        		+' <div class="col-md-3"><label style="font-size: small;"> Tyre Sold Amount </label></div>'
				        		+' <div class="col-md-1"><label style="font-size: small;" >Discount</label></div>'
				        		+' <div class="col-md-1"><label style="font-size: small;" >GST</label></div>'
				        		+' <div class="col-md-3"><label style="font-size: small;" >Total</label></div>'
			        		+'</div>'
		        		+'</div>'
		        		+' <div class="row1">'
			        		+' <div class="col-md-9 form-group">'
				        		+' <div class="col-md-3"><input type="text" class="form-text" name="unitprice_many" value="0.0" id="unitprice_'+tyreList[index].tyre_ID+ '" maxlength="7" min="0.0"placeholder="Unit Cost" required="required" data-toggle="tip" data-original-title="enter Unit Price"onkeypress="return isNumberKey(event,this);" onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:sumthere(\'unitprice_' + tyreList[index].tyre_ID + "', 'discount_" + tyreList[index].tyre_ID + "', 'tax_" +tyreList[index].tyre_ID + "', 'totalCost_" + tyreList[index].tyre_ID + '\' );"\t ondrop="return false;"></div>'
				        		+' <div class="col-md-1"><input type="text" class="form-text" name="discount_many" value="0.0" min="0.0" id="discount_'+tyreList[index].tyre_ID+ '" maxlength="5" placeholder="Discount" required="required"data-toggle="tip" data-original-title="enter Discount" onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:sumthere(\'unitprice_' +tyreList[index].tyre_ID + "', 'discount_" + tyreList[index].tyre_ID + "', 'tax_" + tyreList[index].tyre_ID + "', 'totalCost_" + tyreList[index].tyre_ID + '\' );"\t ondrop="return false;"></div>' 
				        		+' <div class="col-md-1"> <input type="text" class="form-text" name="tax_many" value="0.0" id="tax_'+tyreList[index].tyre_ID+ '" maxlength="5" placeholder="GST" required="required"data-toggle="tip" data-original-title="enter GST" onkeypress="return isNumberKeyQut(event,this);" onkeyup="javascript:sumthere(\'unitprice_' + tyreList[index].tyre_ID + "', 'discount_" + tyreList[index].tyre_ID + "', 'tax_" + tyreList[index].tyre_ID + "', 'totalCost_" + tyreList[index].tyre_ID + '\' );"\t ondrop="return false;"></div>'
				        		+' <div class="col-md-3"><input type="text" class="form-text" name="totalCost" value="0.0" min="0.0"  id="totalCost_'+tyreList[index].tyre_ID + '" readonly="readonly"data-toggle="tip" data-original-title="Total Cost" onkeypress="return isNumberKey(event,this);" ondrop="return false;"></div></div></div>'
				        		+'<input class="col-md-3 btn btn-success" id="saveSoldCost" onclick="saveSoldTyreCost('+tyreList[index].tyre_ID+');"type="submit" value="Save Sold Cost">'
			        		+' </div>'
						+' </div>'
						+' <div class="row1">'
							+'<div class="col-md-5 col-md-offset-2">'
								
							+'</div>'
						+' </div>'		
					+'</div>'
				+'</td> </tr> </tbody> </table> </div>'	
			+' </td>"');
		columnArray.push("<td class='fit ar'><i class='fa fa-inr'></i>" + tyreList[index].tyre_AMOUNT +"</td>");
		columnArray.push("<td class='fit ar'>" + tyreList[index].discount + "%</td>");
		columnArray.push("<td class='fit ar'>" + tyreList[index].gst + "%</td>");
		columnArray.push("<td class='fit ar'><i class='fa fa-inr'></i>" + tyreList[index].tyreNetAmount + "</td>");
		/*columnArray.push("<td class='fit ar'><a href='#' class='confirmation' onclick='deletePickAndDropLocation("+tyreList[index].pickAndDropLocationId+");'><span class='fa fa-trash'></span> Delete</a></td>");*/
		
		$('#showTyreTable').append("<tr id='penaltyID"+tyreList[index].tyre_ID+"' >" + columnArray.join(' ') + "</tr>");
		
		TotalTyreSoldAmount +=  tyreList[index].tyreNetAmount; 
		tyreArray.push(tyreList[index].tyre_ID);
		}
		
		$("#totalTyreSoldAmount").html(TotalTyreSoldAmount);
		$("#soldDate").html(data.soldDate);
		$("#tyreState").html(data.tyreStatus);
		
		var input_Statues = data.tyreStatus;
		switch (input_Statues) {
		case "IN PROCESS":
			 $("#status-open").addClass("status-led-open");
			break;
		case "SOLD":
			 $("#status-in-progress").addClass("status-led-in-progress");
			break;
		}
		
		
		$("#soldInvoiceNo").html(data.soldInvoiceNumber);
		columnArray = [];
	}else{
		showMessage('info','No Record Found!')
	}

	}

function toggle2Labor(e, t) {
    var n = document.getElementById(e),
        o = document.getElementById(t);
    "block" == e.style.display ? (e.style.display = "none", t.innerHTML = "Enter Retread Tyre") : (e.style.display = "block", t.innerHTML = "Cancel Retread Tyre")
}
function sumthere(b, c, d, e) {
    var f = 1,
        g = document.getElementById(b).value,
        h = document.getElementById(c).value,
        i = document.getElementById(d).value,
        j = parseFloat(f) * parseFloat(g),
        k = j * h / 100,
        l = j - k,
        a = l * i / 100,
        m = l + a;
    isNaN(m) || (document.getElementById(e).value = m.toFixed(2))
    var invoiceAmount = 0;
    $("input[name=totalCost]").each(function(){
	});
}
function saveSoldTyreCost(tyreId){
	showLayer();
	
	if($("#unitprice_"+tyreId).val() == "" || $("#unitprice_"+tyreId).val()  == undefined){
		showMessage('info','Please Enter Unit Price')
		hideLayer();
		return false;
	}
	if($("#discount_"+tyreId).val() == "" || $("#discount_"+tyreId).val()  == undefined){
		showMessage('info','Please Enter Discount')
		hideLayer();
		return false;
	}
	if($("#tax_"+tyreId).val() == "" || $("#tax_"+tyreId).val()  == undefined){
		showMessage('info','Please Enter gst')
		hideLayer();
		return false;
	}
	
	var jsonObject					= new Object();
	jsonObject["tyreId"]			= tyreId;
	jsonObject["soldAmount"]		= $("#unitprice_"+tyreId).val();
	jsonObject["comission"]			= $("#discount_"+tyreId).val();
	jsonObject["gst"]				= $("#tax_"+tyreId).val();
	jsonObject["soldNetAmount"]		= $("#totalCost_"+tyreId).val();
	$.ajax({
		url: "saveSoldTyreCost",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			location.reload();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


$(document).ready(
function($) {
	$('button[id=btnSumbit]').click(function(e) {
		
		if(TotalTyreSoldAmount < 0){
			showMessage('info','Please Enter The Tyre Sold Cost');
			hideLayer();
			return false;
		}
		
		var jsonObject				= new Object();
		jsonObject["tyreSoldInvoiceId"]			= $('#invoiceId').val();
		jsonObject["tyreSoldInvoiceAmount"]		= TotalTyreSoldAmount;
		jsonObject["description"]				= $('#description').val();
		jsonObject["tyreArray"]					= JSON.stringify(tyreArray).replace(/[\[\]']+/g,'').replace(/\"/g, "");
		
		
		$.ajax({
			url: "updateTyreSoldInvoiceDetils",// for those tyre which belong to the Available State
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				location.reload();
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
		
		
	});
});


function isNumberKeyQut(a) {
    var b = 0 == a.keyCode ? a.charCode : a.keyCode,
        c = 46 == b || b >= 48 && 57 >= b || -1 != specialKeys.indexOf(a.keyCode) && a.charCode != a.keyCode;
    return document.getElementById("errorINEACH").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorINEACH").style.display = c ? "none" : "inline", c
}