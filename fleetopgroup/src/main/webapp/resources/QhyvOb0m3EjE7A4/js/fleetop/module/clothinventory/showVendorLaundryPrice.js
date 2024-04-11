$(document).ready(function() {
	getVendorLaundryRate(1);
});

function getVendorLaundryRate(pageNumber) {
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;
	jsonObject["vendorId"]			= $('#vendorId').val();
	
	$.ajax({
		url: "getPageWiseVendorLaundryRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setVendorLaundryRateData(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setVendorLaundryRateData(data) {
	$("#VendorPaymentTable").empty();
	if(data.vendor != undefined){
		var valcal = 'ShowVendor.in?vendorId='+data.vendor.vendorId;
		var cancelUrl = '<a href="'+data.vendor.vendorTypeId+'/ShowVendor.in?vendorId='+data.vendor.vendorId+'&page=1">Cancel</a>';
		
		$("#cancelDiv").prop("href", valcal);
		$("#cancel").prop("href", valcal);
		
		var vendorLinkUrl	= '<a '
			+' href="'+data.vendor.vendorTypeId+'/ShowVendor.in?vendorId='+data.vendor.vendorId+'&page=1" '
			+' data-toggle="tip" data-original-title="Click Vendor Details">'
			+' '+data.vendor.vendorName+' '
			+' </a>';
		$('#vendorLink').html('');
		$('#vendorLink').append(vendorLinkUrl);
		$('#vendorType').html(data.vendor.vendorType);
		$('#vendorPhone').html(data.vendor.vendorFirPhone);
		$('#vendorPan').html(data.vendor.vendorPanNO);
		$('#vendorGst').html(data.vendor.vendorGSTNO);
	}
	
	$('#contentTable').show();

	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	
	var th4		= $('<th class="fit ar">');
	
	
	var th7		= $('<th class="fit ar">');
	var th8		= $('<th class="fit ar">');
	var th9		= $('<th class="fit ar">');
	var th10	= $('<th class="fit ar">');
	var th11	= $('<th class="fit ar">');
	var th12	= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Cloth Name"));
	tr1.append(th4.append("Cost"));
	tr1.append(th7.append("Discount"));
	tr1.append(th8.append("GST"));
	tr1.append(th9.append("Total"));
	tr1.append(th12.append("Action"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.vendorFixed != undefined && data.vendorFixed.length > 0) {
		
		var vendorFixed = data.vendorFixed;
	
		for(var i = 0; i < vendorFixed.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td id="name_'+vendorFixed[i].vendorLaundryRateId+'" >');
			var td3		= $('<td id="eachCost_'+vendorFixed[i].vendorLaundryRateId+'" class="fit ar">');
			var td4		= $('<td id="discount_'+vendorFixed[i].vendorLaundryRateId+'" class="fit ar">');
			var td5		= $('<td id="gst_'+vendorFixed[i].vendorLaundryRateId+'" class="fit ar">');
			var td6		= $('<td id="total_'+vendorFixed[i].vendorLaundryRateId+'" class="fit ar">');
			var td7		= $('<td  class="fit ar">');
			var td8		= $('<td id="clothTypesId_'+vendorFixed[i].vendorLaundryRateId+'" style="display:none;" class="fit ar">');
			
			tr1.append(td1.append(i+1));
			tr1.append(td2.append(vendorFixed[i].clothTypeName));
			tr1.append(td3.append(vendorFixed[i].clothEachCost));
			tr1.append(td4.append(vendorFixed[i].clothDiscount));
			tr1.append(td5.append(vendorFixed[i].clothGst));
			tr1.append(td6.append(vendorFixed[i].clothTotal));
			
			
			
			tr1.append(td7.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a onclick="editLaundryRate('+vendorFixed[i].vendorId+','+vendorFixed[i].vendorLaundryRateId+');" href="#"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deleteLaundryRate('+vendorFixed[i].vendorLaundryRateId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));
			
			tr1.append(td8.append(vendorFixed[i].clothTypesId));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable").append(thead);
	$("#VendorPaymentTable").append(tbody);
	
	$("#navigationBar").empty();

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getVendorLaundryRate(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getVendorLaundryRate('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getVendorLaundryRate('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getVendorLaundryRate('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getVendorLaundryRate('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getVendorLaundryRate('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}
}

function showHideAddView(){
	//addVendorLaundryRate
	if($('#addVendorLaundryRate').hasClass('hide')){
		$('#addVendorLaundryRate').removeClass('hide');
		$('#contentTable').addClass('hide');
	}else{
		$('#addVendorLaundryRate').addClass('hide');
		getVendorLaundryRate(1);
		$('#contentTable').removeClass('hide');
	}
	
}
$(document).ready(function() {
	$("#clothTypes").select2({
    minimumInputLength: 2,
    minimumResultsForSearch: 10,
    ajax: {
        url: "getClothTypesList.in?Action=FuncionarioSelect2",
        dataType: "json",
        type: "POST",
        contentType: "application/json",
        quietMillis: 50,
        data: function(a) {
            return {
                term: a
            }
        },
        results: function(a) {
            return {
                results: $.map(a, function(a) {
                    return {
                        text: a.clothTypeName,
                        slug: a.slug,
                        id: a.clothTypesId
                    }
                })
            }
        }
    }
});
	var saved 			= getUrlParameter('saved');
	var deleted			= getUrlParameter('deleted');
	var noRecordFound 	= getUrlParameter('noRecordFound');
	var already			= getUrlParameter('already');
if(saved == true || saved == 'true'){
	showMessage('success', 'Data Saved Successfully !');
}
if(deleted == true || deleted == 'true'){
	showMessage('success', 'Data Deleted Successfully !');
}
if(noRecordFound == true || noRecordFound == 'true'){
	showMessage('info', 'No Record Found !');
}
if(already == true || already == 'true'){
	showMessage('errors', 'Rate for this Cloth Types already exits !');
}

});
function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function	saveLaundryRate(){

	showLayer();
	var jsonObject					= new Object();
	jsonObject["vendorId"]			= $('#vendorId').val();
	jsonObject["clothTypes"]		= $('#clothTypes').val();
	jsonObject["quantity"]			= $('#quantity').val();
	jsonObject["discount"]			= $('#discount').val();
	jsonObject["tax"]				= $('#tax').val();
	jsonObject["tatalcost"]			= $('#tatalcost').val();
	jsonObject["parteachcost"]		= $('#parteachcost').val();
	
	$.ajax({
		url: "saveVendorLaundryRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saved){
				window.location.replace("VendorLaundryPrice.in?Id="+ $('#vendorId').val()+"&saved=true");
			}
			if(data.already){
				window.location.replace("VendorLaundryPrice.in?Id="+ $('#vendorId').val()+"&already=true");
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});

}

function sumthere(e, n, t, r, a) {
	
    var i = document.getElementById(e).value,
        o = document.getElementById(n).value,
        l = document.getElementById(t).value,
        c = document.getElementById(r).value,
        s = parseFloat(i) * parseFloat(o),
        d = s * l / 100,
        u = s - d,
        e = u * c / 100,
        y = u + e;
    isNaN(y) || (document.getElementById(a).value = y.toFixed(2))
}
function isNumberKeyQut(e) {
    var n = 0 == e.keyCode ? e.charCode : e.keyCode,
        t = n >= 48 && 57 >= n || -1 != specialKeys.indexOf(e.keyCode) && e.charCode != e.keyCode;
    return document.getElementById("errorPin").innerHTML = "Alphabetical Characters not allowed", document.getElementById("errorPin").style.display = t ? "none" : "inline", t
}

function isNumberKeyEach(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyDis(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function isNumberKeyTax(e, n) {
    var t = e.which ? e.which : event.keyCode;
    if (t > 31 && (48 > t || t > 57) && 46 != t && 8 != charcode) return !1;
    var r = $(n).val().length,
        a = $(n).val().indexOf(".");
    if (a > 0 && 46 == t) return !1;
    if (a > 0) {
        var i = r + 1 - a;
        if (i > 3) return !1
    }
    return !0
}

function editLaundryRate(vendorId, rateId){
	
	showLayer();
	
	
	$('#editclothTypes').val($('#name_'+rateId).html());
	$('#editdiscount').val($('#discount_'+rateId).html());
	$('#edittax').val($('#gst_'+rateId).html());
	$('#edittatalcost').val($('#total_'+rateId).html());
	$('#editparteachcost').val($('#eachCost_'+rateId).html());
	$('#vendorLaundryRateId').val(rateId);
	$('#editvendorId').val(vendorId);
	$('#clothTypesId').val($('#clothTypesId_'+rateId).html());
	
	$('#editLaundryRate').modal('show');
	hideLayer();
}

function updateLaundryRate(){
	var jsonObject					= new Object();
	jsonObject["vendorId"]			= $('#editvendorId').val();
	jsonObject["clothTypes"]		= $('#clothTypesId').val();
	jsonObject["quantity"]			= $('#editquantity').val();
	jsonObject["discount"]			= $('#editdiscount').val();
	jsonObject["tax"]				= $('#edittax').val();
	jsonObject["tatalcost"]			= $('#edittatalcost').val();
	jsonObject["parteachcost"]		= $('#editparteachcost').val();
	jsonObject["vendorLaundryRateId"]		= $('#vendorLaundryRateId').val();
	
	$.ajax({
		url: "updateVendorLaundryRate",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.saved){
				window.location.replace("VendorLaundryPrice.in?Id="+ $('#vendorId').val()+"&saved=true");
			}else{
				showMessage('errors', 'Some Error Occurred Please contact to System Administrator !');
			}
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}
function deleteLaundryRate(rateId){
	var jsonObject						= new Object();
	
	var ans = confirm("Are you sure to Delete ?");
	if(ans){
		showLayer();
		jsonObject["rateId"]	= rateId;
		$.ajax({
			url: "deleteLaundryRate",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted)
					window.location.replace("VendorLaundryPrice.in?Id="+ $('#vendorId').val()+"&deleted=true");
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	
}
var specialKeys = new Array;
specialKeys.push(8), specialKeys.push(9), specialKeys.push(46), specialKeys.push(36), specialKeys.push(35), specialKeys.push(37), specialKeys.push(39)
