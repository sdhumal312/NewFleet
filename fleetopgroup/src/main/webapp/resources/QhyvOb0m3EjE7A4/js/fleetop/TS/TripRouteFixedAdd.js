$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_wrap"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseName" id="Expense' + a + '" onChange="getExpenseMaxLimit('+a+');" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" readonly="readonly" onkeyup="return validateExpenseRange('+a+');" name="Amount" id ="Amount' + a + '" min="0" required="required" placeholder="Amount"> <input type="hidden" id="maxAmount' + a + '"></div><div class="col-md-3"><input type="text" class="form-text" name="expenseRefence" placeholder="Reference" value="R00"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
            $("#Expense" + a).empty(), $("#task" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#Expense" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#Expense1").empty(), $("#Expense1").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Expense1").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    }),
    $("#driverId").select2({
        minimumInputLength: 3,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getDriverSearchAllListFuel.in?Action=FuncionarioSelect2",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            quietMillis: 50,
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.driver_empnumber + " - " + e.driver_firstname,
                            slug: e.slug,
                            id: e.driver_id
                        }
                    })
                }
            }
        }
    })
});
$(document).ready(function() {
    var e = 25,
        t = $(".input_fields_income"),
        c = $(".add_field_button_income"),
        n = 1;
    $(c).click(function(c) {
        if (c.preventDefault(), e > n) {
            n++;
            $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="incomeName" id="task_income' + n + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="incomeAmount" id="Amount' + n + '" min="0" required="required" placeholder="Amount"></div><div class="col-md-3"><input type="text" class="form-text" name="incomeRefence" placeholder="Reference" value="X0"></div></div><a href="#" class="remove_field_income"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("tripIncomeListRoute.in", function(e) {
                $("#task_income" + n).empty(), $("#task_income" + n).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                    $("#task_income" + n).append($("<option>").text(t.incomeName).attr("value", t.incomeID))
                })
            });
            $(".select2").select2()
        }
    }), $(t).on("click", ".remove_field_income", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), n--
    }), $.getJSON("tripIncomeListRoute.in", function(e) {
        $("#Income").empty(), $("#Income").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#Income").append($("<option>").text(t.incomeName).attr("value", t.incomeID))
        })
    }), $("#TripRouteSubList").select2({
        ajax: {
            url: "getTripRouteSubList.in",
            dataType: "json",
            type: "POST",
            contentType: "application/json",
            data: function(e) {
                return {
                    term: e
                }
            },
            results: function(e) {
                return {
                    results: $.map(e, function(e) {
                        return {
                            text: e.routeNo + " " + e.routeName,
                            slug: e.slug,
                            id: e.routeID
                        }
                    })
                }
            }
        }
    })
});

$(document).ready(function() {
    var e = 25,
        t = $(".add_field_button_expenseRange"),
        n = $(".add_field_button"),
        a = 1;
    $(n).click(function(n) {
        n.preventDefault(), e > a && (a++, $(t).append('<div><div class="row1"><div class="col-md-4"><select class="form-text select2" name="expenseRangeTask" id="expenseRange' + a + '" required="required"></select></div><div class="col-md-3"><input type="number" class="form-text" name="expenseMaxLimt" id ="expenseMaxLimt' + a + '" min="0" required="required" placeholder="Amount"></div></div><a href="#" class="remove_field"><font color="FF00000"><i class="fa fa-trash"></i> Remove</a></font></div>'), $.getJSON("getTripExpenseList.in", function(e) {
            $("#expenseRangeTask" + a).empty(), $("#expenseRangeTask" + a).append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
                $("#expenseRangeTask" + a).append($("<option>").text(t.expenseName).attr("value", t.expenseID))
            })
        }), $(".select2").select2())
    }), $(t).on("click", ".remove_field", function(e) {
        e.preventDefault(), $(this).parent("div").remove(), a--
    }), $.getJSON("getTripExpenseList.in", function(e) {
        $("#expenseRange").empty(), $("#expenseRange").append($("<option>").text("Please Select ").attr("value", 0)), $.each(e, function(e, t) {
            $("#expenseRange").append($("<option>").text(t.expenseName).attr("value", t.expenseID))
        })
    })
});


$("#showExpenseRange").click(function(){
	getExpenseRangeList(1);
}); 


function saveExpenseRange(onblurFunction){
	
	var jsonObject			= new Object();
	var array	 					= new Array();
	var tripRouteExpenseRangeId 	= new Array();
	var expneseId 					= new Array();
	var maxLimit 					= new Array();
	
	
	$("input[name=tripRouteExpenseRangeId]").each(function(){
		tripRouteExpenseRangeId.push($(this).val());
	});
	$("input[name=expenseRangeName]").each(function(){
		expneseId.push($(this).val());
	});
	$("input[name=expenseRangeAmount]").each(function(){
		maxLimit.push($(this).val());
	});
	
	
	for(var i =0 ; i< expneseId.length; i++){
		
		var list					= new Object();
		
		list.tripRouteExpenseRangeId	= tripRouteExpenseRangeId[i];
		list.expenseId					= expneseId[i];
		list.expenseMaxLimt				= maxLimit[i];
		list.routeId					= $('#routeId').val()
		
		array.push(list);
	}
	jsonObject.finalList = JSON.stringify(array);
	
	
	$.ajax({
		url: "addTripRouteExpenseRange",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
		//	$('#addRouteExpenseRange').modal('hide');
			if(onblurFunction == undefined || onblurFunction == false){
				showMessage('info','Expense Max Range Added Successfully')
				location.reload();
			}
			getExpenseRangeList(1);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}

function getExpenseRangeList(pageIndex){
	var jsonObject			= new Object();

	jsonObject["routeId"]				= $('#routeId').val();
	jsonObject["pageNumber"]			= pageIndex;
	
	$.ajax({
		url: "getTripExpenseListToSetRouteWiseExpenseRange",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			setExpenseRangeList(data);
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
}


function setExpenseRangeList(data){
	$("#expenseMaxRangeTable").empty();
	$('#searchData').show();
	$('#countDiv').show();
	
	var tripRouteExpenseRangeList = data.tripRouteExpenseRangeList;
	
	if(tripRouteExpenseRangeList != undefined || tripRouteExpenseRangeList != null){
		for(var index = 0 ; index < tripRouteExpenseRangeList.length; index++){
			
			var columnArray = new Array();
			columnArray.push("<td class='fit'>"+(index+1)+"</td>");
			columnArray.push("<td class='fit'ar><input type='hidden' name='tripRouteExpenseRangeId' value="+tripRouteExpenseRangeList[index].tripRouteExpenseRangeId+"><input type='hidden' name='expenseRangeName' value="+tripRouteExpenseRangeList[index].tripExpenseId+"> <h5> "+ tripRouteExpenseRangeList[index].expenseName  +"</td>");
			columnArray.push("<td class='fit ar'><input type='text' maxlength='6' class='form-text' name='expenseRangeAmount' onblur='saveExpenseRange("+true+");' id='expenseMaxLimtId"+tripRouteExpenseRangeList[index].tripExpenseId+"' onkeypress='return isNumberKeyWithDecimal(event,this.id);' value="+ tripRouteExpenseRangeList[index].expenseMaxLimt +"> </td>");
			
			$('#expenseMaxRangeTable').append("<tr id='penaltyID"+tripRouteExpenseRangeList[index].tripExpenseId+"' >" + columnArray.join(' ') + "</tr>");
			
		}
		
		columnArray = [];
		}else{
			showMessage('info','No Record Found!')
		}	
	
	$("#navigationBar").empty();
	
	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;&lt;</a></li>');		
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getExpenseRangeList(1)">&lt;&lt;</a></li>');		
	}

	if(data.currentIndex == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&lt;</a></li>');
	} else {
		$("#navigationBar").append('<li><a href="#" onclick="getExpenseRangeList('+(data.currentIndex - 1)+')">&lt;</a></li>');
	}
	for (i = data.beginIndex; i <= data.endIndex; i++) {
	    if(i == data.currentIndex) {
	    	$("#navigationBar").append('<li class="active"><a href="#" onclick="getExpenseRangeList('+i+')">'+i+'</a></li>');	    	
	    } else {
	    	$("#navigationBar").append('<li><a href="#" onclick="getExpenseRangeList('+i+')">'+i+'</a></li>');	    	
	    }
	} 
	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getExpenseRangeList('+(data.currentIndex + 1)+')">&gt;</a></li>');			
		}
	}

	if(data.deploymentLog.totalPages == 1) {
		$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');
	} else {
		if(data.currentIndex == data.deploymentLog.totalPages) {
			$("#navigationBar").append('<li class="disabled"><a href="#" onclick="">&gt;&gt;</a></li>');			
		} else {
			$("#navigationBar").append('<li><a href="#" onclick="getExpenseRangeList('+(data.deploymentLog.totalPages)+')">&gt;&gt;</a></li>');			
		}
	 }

	
	$('#addRouteExpenseRange').modal('show');
	
}


function getExpenseMaxLimit(a){
	
	$("#Amount"+a).val("");
	$("#Amount"+a).attr('readonly','readonly');
	
	var jsonObject			= new Object();

	jsonObject["routeId"]			= $('#routeId').val();
	jsonObject["expenseId"]			= $("#Expense"+a).val();
	
	$.ajax({
		url: "getExpenseMaxLimit",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			$("#Amount"+a).removeAttr('readonly');
			$("#maxAmount"+a).val(data.maxAmount)
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	
}


function validateExpenseRange(a){
	
	var	expenseAmount 		= Number($("#Amount"+a).val());
	var	maxAmount 			= Number($("#maxAmount"+a).val());
	var	maxlimitConfig		= $("#maxlimitConfig").val();
	var	expenseOutOfRange	= $("#expenseOutOfRange").val();
	
	if((maxAmount > 0) && (expenseAmount > maxAmount) && (maxlimitConfig == true ||  maxlimitConfig == 'true') && (expenseOutOfRange == false || expenseOutOfRange == 'false')){
		$("#Amount"+a).val(maxAmount);
		showMessage('info','You Are Exceeding The Max Limit Of Expense Amount Please Contact System Admin')
		return false;
	}
}

function addAllowanceDetails(){
	$('#addAllowanceModal').modal('show');
}

function validateAllowance(){
	if(Number($('#driJobId').val()) <= 0){
		showMessage('info', 'Please select Job Title !');
		return false;
	}
	if(Number($('#allowanceExpenseId').val()) <= 0){
		showMessage('info', 'Please Select Expnse Type !');
		return false;
	}
	if(Number($('#allowanceAmount').val()) <= 0){
		showMessage('info', 'Please Enter Amount !');
		return false;
	}
	
	return true;
}

function saveAllowanceDetails(){
	$('#saveAllowanceDiv').hide();
	
	if(!validateAllowance()){
		$('#saveAllowanceDiv').show();
		return false;
	}
	
	showLayer();
	var jsonObject			= new Object();

	jsonObject["routeId"]			= $('#routeId').val();
	jsonObject["driJobId"]			= $("#driJobId").val();
	jsonObject["amount"]			= $("#allowanceAmount").val();
	jsonObject["expenseId"]			= $("#allowanceExpenseId").val();
	
	$.ajax({
		url: "saveAllowanceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			if(data.duplicate != undefined && data.duplicate){
				showMessage('info', 'Data Already Exists !');
				hideLayer();
				return false;
			}
			if(data.saved != undefined && data.saved){
				showMessage('success', 'Data Saved Successfully !');
				location.replace("showTripRoute.in?routeID="+$('#routeId').val());
			}
			showMessage('errors', 'Some Error Occurred!');
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	

}
function removeAllowanceDetails(routeFixedAllowanceId){

	
	showLayer();
	var jsonObject			= new Object();

	jsonObject["routeFixedAllowanceId"]			= routeFixedAllowanceId;
	
	$.ajax({
		url: "removeAllowanceDetails",
		type: "POST",
		dataType: 'json',
		data: jsonObject,
		success: function (data) {
			showMessage('success', 'Data Deleted Successfully !');
			location.replace("showTripRoute.in?routeID="+$('#routeId').val());
			hideLayer();
		},
		error: function (e) {
			hideLayer();
			showMessage('errors', 'Some Error Occurred!');
		}
	});
	


}