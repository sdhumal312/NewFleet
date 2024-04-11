var data = [];
$(function() {
	function a(a, b) {
		$("#dateRange").val(a.format("DD-MM-YYYY")+" to "+b.format("DD-MM-YYYY"))
	}
	a(moment().subtract(1, "days"), moment()), $("#dateRange").daterangepicker( {
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
}
);
//Expense Name DropDown
$(document).ready(function() {
	   $("#ReportExpenseName").select2( {
	        minimumInputLength:2, minimumResultsForSearch:10, ajax: {
	            url:"getSearchExpenseName.in", dataType:"json", type:"POST", contentType:"application/json", quietMillis:50, data:function(a) {
	                return {
	                    term: a
	                }
	            }
	            , results:function(a) {
	                return {
	                    results:$.map(a, function(a) {
	                        return {
	                            text: a.expenseName, slug: a.slug, id: a.expenseID
	                        }
	                    }
	                    )
	                }
	            }
	        }
	    } 
	    ),$("#tallyCompanyId").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getTallyCompanySearchList.in?Action=FuncionarioSelect2",
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
	                            text: a.companyName ,
	                            slug: a.slug,
	                            id: a.tallyCompanyId
	                        }
	                    })
	                }
	            }
	        }
	    }),$("#vendorId").select2({
	        minimumInputLength: 3,
	        minimumResultsForSearch: 10,
	        ajax: {
	            url: "getVendorSearchListOnTripExpense.in?Action=FuncionarioSelect2",
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
	                            text: a.vendorName + " - " + a.vendorLocation,
	                            slug: a.slug,
	                            id: a.vendorId
	                        }
	                    })
	                }
	            }
	        }
	    });
});

$(document).ready(
		function($) {
			$('button[type=submit]').click(function(e) {
				e.preventDefault();

				showLayer();
				var jsonObject			= new Object();

				jsonObject["ReportExpenseName"] 	=  $('#ReportExpenseName').val();				
				jsonObject["dateRange"] 	  		=  $('#dateRange').val();
				jsonObject["tallyCompanyId"] 	  	=  $('#tallyCompanyId').val();
				jsonObject["vendorId"] 	  			=  $('#vendorId').val();
				
				if($('#dateRange').val() == undefined || $('#dateRange').val() == null || $('#dateRange').val().trim() == ''){					
					showMessage('info', 'Please Select Date Range !');
					hideLayer();
					return false;
				}
				
				$.ajax({
					url: "getVoucherDateWiseExpenseReport.do",
					type: "POST",
					dataType: 'json',
					data: jsonObject,
					success: function (data) {
						renderReportData(data);
						hideLayer();
					},
					error: function (e) {
						showMessage('errors', 'Some error occured!')
						hideLayer();
					}
				});


			});

		});

//slickgreed

function renderReportData(resultData) {
	if(resultData.expense != undefined){
		setHeaderData(resultData.company);
		var columnConfiguration ;
		var tableProperties;
		$('#ResultContent').show();
		$("#companyTable tr").remove();
		$("#selectedReportDetails tr").remove();
		$('#myGrid').empty();
		
		
		$('#reportHeader').html('<b>Create Day Wise Expense Report</b>');
		var defaultVal ="All"
		
		var ReportExpenseName = $('#ReportExpenseName').select2('data');
		var tallyCompanyName = $('#tallyCompanyId').select2('data');
		var vendorName = $('#vendorId').select2('data');
		console.log("data",ReportExpenseName)
		
		if(ReportExpenseName){
			var tr =' <tr data-object-id="">'
						+'<td class="fit"> Expense Name : '+ReportExpenseName.text+'</td>'
					+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit">  Expense Name :'+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}
		
		if(tallyCompanyName){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> TallyCompany Name : '+tallyCompanyName.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit">  TallyCompany Name :'+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}
	
		if(vendorName){
			var tr =' <tr data-object-id="">'
				+'<td class="fit"> Vendor Name : '+vendorName.text+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr);
		}else{
			var tr1  =' <tr data-object-id="">'
				+'<td class="fit">  Vendor Name :'+defaultVal+'</td>'
				+'</tr>';
			$('#selectedReportDetails').append(tr1);
		}
		
		if(resultData.tableConfig != undefined) {
			var ColumnConfig = resultData.tableConfig.columnConfiguration;
			var columnKeys	= _.keys(ColumnConfig);
			var bcolConfig		= new Object();
			
			for (var i = 0; i < columnKeys.length; i++) {
				var bObj	= ColumnConfig[columnKeys[i]];
				
				if (bObj.show != undefined && bObj.show == true) {
					bcolConfig[columnKeys[i]] = bObj;
				}
			}
		
			columnConfiguration	= _.values(bcolConfig);
			tableProperties	=  resultData.tableConfig.tableProperties;
			
		}
		
		setSlickData(resultData.expense, columnConfiguration, tableProperties);
		
		$('#gridContainer').show();
		$('#printBtn').removeClass('hide');
		$('#printPdf').removeClass('hide');
	}else{
		//$('#gridContainer').hide();
		$('#ResultContent').hide();
	    $('#printBtn').addClass('hide');
	    showMessage('info', 'No record found !');
	}

}

function setHeaderData(company){
	$("#companyTable tr").remove();
	if(company != undefined && company != null){
		$('#companyTable').show();
		if(company.company_id_encode != null){
			$('#companyHeader').append('<tr id="imgRow"><td id="companyLogo"> </td><td id="printBy"</td></tr>');
			$('#companyHeader').append('<tr><td colspan="2" id="branchInfo"></td></tr>');
			$('#companyLogo').append('<img id="imgSrc" src="downloadlogo/'+company.company_id_encode+'.in" class="img-rounded " alt="Company Logo" width="280" height="40" />');		
		 	$('#printBy').html('Print By : '+company.firstName+'_'+company.lastName); 
		 	$('#branchInfo').html('Branch : '+company.branch_name+' , Department : '+company.department_name);
		}
		$('#companyName').html(company.company_name);
	}
}

function showVehicleDetails(gridObj, dataView, row){

	var win = window.open('showVehicle.in?vid='+dataView.vid, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}

}

function showTripSheetNumberDetails(gridObj, dataView, row){
	
	var win;
	win = window.open('showTripSheet.in?tripSheetID='+dataView.tripSheetID, '_blank');
	if (win) {
	    win.focus();
	} else {
	    alert('Please allow popups for this website');
	}
}

$('#printPdf').on('click', function () {  
    $("#companyNameOverlay").hide();
    $("#companyNameDialog").fadeOut(0);
   $('body').scrollTop(0);  
   saveImageToPdf('ResultContent');
  });  

  function saveImageToPdf(idOfHtmlElement)
  {
	  console.log("sgfds")
     var fbcanvas = document.getElementById(idOfHtmlElement);
     html2canvas($(fbcanvas),
          {

              onrendered: function (canvas) {

                  var width = canvas.width;
                  var height = canvas.height;
                  var millimeters = {};
                  millimeters.width = Math.floor(width * 0.264583);
                  millimeters.height = Math.floor(height * 0.264583);

                  var imgData = canvas.toDataURL(
                      'image/png');
                  var doc = new jsPDF("p", "mm", "a4");
                  doc.deletePage(1);
                  doc.addPage(millimeters.width, millimeters.height);
                  doc.addImage(imgData, 'PNG', 0, 0);
                  doc.save('ReportPrint.pdf');
              }
          });
  }