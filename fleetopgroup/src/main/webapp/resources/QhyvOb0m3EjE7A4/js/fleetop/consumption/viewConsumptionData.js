var CREATION_DATE_TYPE 		= 1;
var TRANSACTION_DATE_TYPE	= 2;
var pageNumber				= 1

$("#cancel,#cancel1").click(function(){
	window.location.replace("viewConsumption");
	});

$(document).ready(function() {
	setTimeout(function(){
		$("#dateRange").val($("#startDate").val()+"to"+$("#endDate").val());
		getConsumptionData($("#dateType").val(),pageNumber);
	}, 500);
});

function getCompany(){
	$.getJSON("getCompanyInformationDetails.in", function(e) {
		companyList	= e;//To get All Company Name 
		$("#companyId").empty();
		$("#companyId").append($("<option>").text("Please Select Company ").attr("value",0));
		$('#companyId').select2();

		for(var k = 0; k <companyList.length; k++){
			$("#companyId").append($("<option>").text(companyList[k].company_name).attr("value", companyList[k].company_id));
		}

	});	
	
	if($('#companyDropdownConfig').val() == 'true' || $('#companyDropdownConfig').val() == true){
		$('#companyRow').show();
	}else{
		$('#companyRow').hide();
	}
	if($("#dateType").val() == ""){
		getConsumptionData(CREATION_DATE_TYPE,pageNumber);
	}else{
		getConsumptionData($("#dateType").val(),pageNumber);
	}
}

$(function() {
	function a(a, b) {
        $("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
    }
    a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
    	maxDate: new Date(),
		format : 'DD-MM-YYYY',
    	ranges: {
            Today: [moment(), moment()],
            Yesterday: [moment().subtract(1, "days"), moment().subtract(1, "days")],
            "Last 7 Days": [moment().subtract(6, "days"), moment()],
            "This Month": [moment().startOf("month"), moment().endOf("month")],
            "Last Month": [moment().subtract(1, "months").startOf("month"), moment().subtract(1, "months").endOf("month")]
        }
    }
    , a)
});

function selectDateType(dateType){
	if(dateType == 1){
		$('#creationDateId').addClass('btn-success').removeClass('btn-default');
		$('#transactionDateId').addClass('btn-default').removeClass('btn-success');
		
	}else if(dateType == 2){
		$('#transactionDateId').addClass('btn-success').removeClass('btn-default');
		$('#creationDateId').addClass('btn-default').removeClass('btn-success');
	}
	
	$("#dateType").val(dateType);
	getConsumptionData(dateType,pageNumber);
}

function dateChange(){
	if($("#dateType").val() == ""){
		getConsumptionData(CREATION_DATE_TYPE, pageNumber);
	}else{
		getConsumptionData($("#dateType").val(), pageNumber);
	}
}

function addCommas(x) {
	return x.toString().split('.')[0].length > 3 ? x.toString().substring(0,x.toString().split('.')[0].length-3).replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," + x.toString().substring(x.toString().split('.')[0].length-3): x.toString();
}