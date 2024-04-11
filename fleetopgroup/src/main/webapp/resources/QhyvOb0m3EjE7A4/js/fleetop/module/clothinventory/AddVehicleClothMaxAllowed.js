$(document).ready(function() {
	getMaxAllowedDetails(1);
});

function getMaxAllowedDetails(pageNumber) {
	
	showLayer();
	var jsonObject					= new Object();
	jsonObject["pageNumber"]		= pageNumber;

	$.ajax({
		url: "getVehicleClothMaxAllowed",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			hideLayer();
			setMaxAllowed(data);
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

$(document).ready(function() {
	
	$("#vehicleId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }),
	$("#upholsteryType").select2({
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
	    })
});

$(document).ready(function() {
    var a = 10,
        b = $(".input_fields_wrap"),
        c = $(".add_field_button"),
        d = 1;
    $(c).click(function(c) {
        c.preventDefault(), a > d && (d++, $(b).append('<div><div class="panel panel-success"><div class="panel-body"><div class="row1"><label class="L-size control-label">Upholstery Types:<abbr title="required">*</abbr></label><div class="I-size"><input type="hidden" id="upholsteryType' + d + '" name="upholsteryType" style="width: 100%;" required="required" placeholder="Please Enter 2 or more Cloth Type Name" /></div></div><br/><br/><div class="row"><label class="L-size control-label" id="Type">Assign Max Qty:<abbr title="required">*</abbr></label><div class="I-size"><input type="text" class="form-text" id="maxQuantity' + d + '" maxlength="249" name="maxQuantity" required="required" placeholder="Enter Max Qnty For Assignment to a Vehicle" /></div></div><br/><div class="row"><label class="L-size control-label" id="Type">Description:</label><div class="I-size"><input type="text" class="form-text" id="description' + d + '" maxlength="249" name="description" placeholder="Enter description" /></div></div><br /></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i>Remove</a></font></div>'), 
        		
          $(document).ready(function() {
            $("#upholsteryType" + d).select2({
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
            })
        }))
    }), $(b).on("click", ".remove_field", function(a) {
        a.preventDefault(), $(this).parent("div").remove(), d--
    })
});

function saveMaxAllowed(){
	
				if(Number($('#vehicleId').val() <= 0)){
					 $("#vehicleId").select2('focus');
					showMessage('errors', 'Please Select Vehicle!');
					return false;
				}
				
				var noQuantity = false;
				$('input[name*=maxQuantity]' ).each(function(){
					var vehicleVal = $("#"+$( this ).attr( "id" )).val();
					if(vehicleVal <= 0){
						 $("#"+$( this ).attr( "id" )).focus();
						 noQuantity	= true;
						showMessage('errors', 'Please Enter Quantity!');
						return false;
					}
				});
				
				var noManufacturer	= false;
				$('input[name*=upholsteryType]' ).each(function(){
					var vehicleVal = $("#"+$( this ).attr( "id" )).val();
					if(vehicleVal <= 0){
						 $("#"+$( this ).attr( "id" )).select2('focus');
						 noManufacturer	= true;
						showMessage('errors', 'Please Select Cloth Types!');
						return false;
					}
				});
				
				if(noManufacturer || noQuantity){
					return false;
				}
				
				var jsonObject			= new Object();
				var clothMan 			= new Array();
				var quantity_many 		= new Array();
				var description_many 	= new Array();
				

				jsonObject["vehicleId"] 	  				=  $('#vehicleId').val();
				jsonObject["upholsteryType"] 				=  $('#upholsteryType').val();
				jsonObject["maxQuantity"] 					=  $('#maxQuantity').val();
				jsonObject["description"] 					=  $('#description').val();
				

				$("input[name=upholsteryType]").each(function(){
					clothMan.push($(this).val().replace(/"/g, ""));
				});
				
				$("input[name=maxQuantity]").each(function(){
					quantity_many.push($(this).val());
				});
				
				$("input[name=description]").each(function(){
					description_many.push($(this).val());
				});
				
				var array = new Array();
				for(var i =0 ; i< clothMan.length; i++){
					var VehicleClothMaxAllowedSetting	= new Object();
					VehicleClothMaxAllowedSetting.clothTypesId			= clothMan[i];
					VehicleClothMaxAllowedSetting.maxAllowedQuantity	= quantity_many[i];
					VehicleClothMaxAllowedSetting.remark				= description_many[i];
					
					array.push(VehicleClothMaxAllowedSetting);
				}
				jsonObject.VehicleClothMaxAllowed = JSON.stringify(array);
				showLayer();
				$.ajax({
			             url: "saveVehicleClothMaxAllowed",
			             type: "POST",
			             dataType: 'json',
			             data: jsonObject,
			             success: function (data) {
			    			
			    			if(data.alreadyExist != undefined && data.alreadyExist == true){
			    				showMessage('info', 'Max Qty Already Assigned To A Vehicle.' 
			    				  +'Duplicate Assignment Count : '+data.duplicateCount+', Succesfull Assignment Count : '+data.actualCount);
			    				hideLayer();
			    				setTimeout(function(){
			    					//window.reload("addVehicleClothMaxAllowed.in&saved=true");
			    					location.reload();
			    					},4000);
			    				//return false;
			    			} else{
				    			showMessage('success', 'Max Qty Assigned Successfully!');
				    			hideLayer();
				    			setTimeout(function(){
			    					location.reload();
			    					},500);
			    			}
			    			//setMaxAllowed(data);
			    			
			             },
			             error: function (e) {
			            	 showMessage('errors', 'Some error occured!')
			            	 hideLayer();
			             }
				});
}

function setMaxAllowed(data) {
	
	$("#VendorPaymentTable").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle Name"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Description"));
	tr1.append(th6.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.VehicleClothMaxAllowed != undefined && data.VehicleClothMaxAllowed.length > 0) {
		
		var VehicleClothMaxAllowed = data.VehicleClothMaxAllowed;
	
		for(var i = 0; i < VehicleClothMaxAllowed.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(VehicleClothMaxAllowed[i].vehicle_registration));
			tr1.append(td3.append(VehicleClothMaxAllowed[i].clothTypeName));
			tr1.append(td4.append(VehicleClothMaxAllowed[i].maxAllowedQuantity));
			tr1.append(td5.append(VehicleClothMaxAllowed[i].remark));
			
			
			tr1.append(td6.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="editMaxAllowedSettingById('+VehicleClothMaxAllowed[i].maxAllowedSettingId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deletemaxAllowedSettingype('+VehicleClothMaxAllowed[i].maxAllowedSettingId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

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
		$("#navigationBar").append('<li><a href="#" onclick="getMaxAllowedDetails(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getMaxAllowedDetails('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getMaxAllowedDetails('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getMaxAllowedDetails('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getMaxAllowedDetails('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getMaxAllowedDetails('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	}

}

$(document).ready(function() {
	
	$("#editVehicleId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    }),
	$("#editUpholsteryType").select2({
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
	    })
});

function editMaxAllowedSettingById(maxAllowedSettingId){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["maxAllowedSettingId"]	= maxAllowedSettingId;

	$.ajax({
		url: "getVehicleClothMaxAllowedById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$('#editVehicleId').val(data.maxAllowedSettingId.vehicle_registration);
			$('#editUpholsteryType').val(data.maxAllowedSettingId.clothTypeName);
			$('#editMaxQuantity').val(data.maxAllowedSettingId.maxAllowedQuantity);
			$('#editDescription').val(data.maxAllowedSettingId.remark);
			$('#maxAllowedSettingId').val(data.maxAllowedSettingId.maxAllowedSettingId);
			
			var VID = data.maxAllowedSettingId.vid;
			var VText = $("#editVehicleId").val();
			$('#editVehicleId').select2('data', {
				id : VID,
				text : VText
			});
			
			var UtID = data.maxAllowedSettingId.clothTypesId;
			var UtText = $("#editUpholsteryType").val();
			$('#editUpholsteryType').select2('data', {
				id : UtID,
				text : UtText
			});
			
			$('#editMaxAllowed').modal('show');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function updateMaxAllowedSettingById(){

	showLayer();
	var jsonObject						= new Object();

	jsonObject["editVehicleId"]				= $('#editVehicleId').val();
	jsonObject["editUpholsteryType"]		= $('#editUpholsteryType').val();
	jsonObject["editMaxQuantity"]			= $('#editMaxQuantity').val();
	jsonObject["editDescription"]			= $('#editDescription').val();
	jsonObject["maxAllowedSettingId"]		= $('#maxAllowedSettingId').val();

	$.ajax({
		url: "updateVehicleClothMaxAllowedById.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			
			$('#editMaxAllowed').modal('hide');
			if(data.alreadyExist != undefined && data.alreadyExist == true){
				showMessage('info', 'Max Qty Already Assigned To A Vehicle!');
				hideLayer();
				return false;
			}
			//setMaxAllowed(data);
			//getMaxAllowedDetails(1);
			window.location.replace("addVehicleClothMaxAllowed.in?saved=true");
			
			showMessage('success', 'Max Qty Assignment to Vehicle Updated Successfully!');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function deletemaxAllowedSettingype(maxAllowedSettingId) {
	
	if (confirm('are you sure to delete ?')) {
		showLayer();
		var jsonObject					= new Object();

		jsonObject["maxAllowedSettingId"]	= maxAllowedSettingId;
		
		$.ajax({
			url: "deleteVehicleClothMaxAllowedById.do",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				if(data.deleted != undefined && (data.deleted == 'true' || data.deleted == true )){
					
					showMessage('success', 'Data Deleted Sucessfully!');
	    			hideLayer();
	    			setTimeout(function(){
    					location.reload();
    					},2000);
					
				} else {
					setMaxAllowed();
					hideLayer();
				}
				
			},
			error: function (e) {
				console.log("Error : " , e);
				hideLayer();
			}
		});
	} 
	
}

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			return false;
		}
  return true;
}


$(document).ready(function() {
	
	$("#vehId").select2( {
        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
            url:"getVehicleSearchServiceEntrie.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
                return {
                    term: a
                }
            }
            , results:function(a) {
                return {
                    results:$.map(a, function(a) {
                        return {
                            text: a.vehicle_registration, slug: a.slug, id: a.vid
                        }
                    }
                    )
                }
            }
        }
    });
});

function searchByVehicle(){
	
	var jsonObject	= new Object();
	jsonObject["vehId"]	= $('#vehId').val();
	
	if($('#vehId').val().trim() == ''){
		$('#vehId').focus();
		showMessage('errors', 'Please Select Vehicle !');
		hideLayer();
		return false;
	}
	
	showLayer();
	$.ajax({
		url: "getVehicleClothListBySearch.do",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setSearchData(data);
			$('#mainTable').addClass('hide');
			hideLayer();
		},
		error: function (e) {
			console.log("Error : " , e);
			hideLayer();
		}
	});

}

function setSearchData(data) {
	
	$("#VendorPaymentTable1").empty();
	
	var thead = $('<thead>');
	var tr1 = $('<tr>');

	var th1		= $('<th>');
	var th2		= $('<th>');
	var th3		= $('<th class="fit ar">');
	var th4		= $('<th class="fit ar">');
	var th5		= $('<th class="fit ar">');
	var th6		= $('<th>');

	tr1.append(th1.append("No"));
	tr1.append(th2.append("Vehicle Name"));
	tr1.append(th3.append("Upholstery Name"));
	tr1.append(th4.append("Quantity"));
	tr1.append(th5.append("Description"));
	tr1.append(th6.append("Actions"));

	thead.append(tr1);
	
	var tbody = $('<tbody>');
	if(data.VehicleClothMaxAllowed != undefined && data.VehicleClothMaxAllowed.length > 0) {
		
		var VehicleClothMaxAllowed = data.VehicleClothMaxAllowed;
	
		for(var i = 0; i < VehicleClothMaxAllowed.length; i++) {
			var tr1 = $('<tr class="ng-scope">');
			
			var td1		= $('<td>');
			var td2		= $('<td>');
			var td3		= $('<td class="fit ar">');
			var td4		= $('<td class="fit ar">');
			var td5		= $('<td class="fit ar">');
			var td6		= $('<td>');
			
			tr1.append(td1.append(i + 1));
			tr1.append(td2.append(VehicleClothMaxAllowed[i].vehicle_registration));
			tr1.append(td3.append(VehicleClothMaxAllowed[i].clothTypeName));
			tr1.append(td4.append(VehicleClothMaxAllowed[i].maxAllowedQuantity));
			tr1.append(td5.append(VehicleClothMaxAllowed[i].remark));
			
			
			tr1.append(td6.append(
			'<div class="btn-group">'
			+'<a class="btn btn-default btn-sm dropdown-toggle" data-toggle="dropdown" href="#"> <span class="fa fa-ellipsis-v"></span></a>'
			+'<ul class="dropdown-menu pull-right">'
			+'<li><a href="#" class="confirmation" onclick="editMaxAllowedSettingById('+VehicleClothMaxAllowed[i].maxAllowedSettingId+')"><i class="fa fa-edit"></i> Edit</a></li>'
			+'<li><a href="#" class="confirmation" onclick="deletemaxAllowedSettingype('+VehicleClothMaxAllowed[i].maxAllowedSettingId+')"><span class="fa fa-trash"></span> Delete</a></li>'
			+'</ul>'
			+'</div>'
			));

			tbody.append(tr1);
		}
	}else{
		showMessage('info','No record found !')
	}
	
	$("#VendorPaymentTable1").append(thead);
	$("#VendorPaymentTable1").append(tbody);

}
			
			




