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

	
	function deleteUreaInventoryDetails(ureaInvoiceToDetailsId){
		showLayer();
		var jsonObject			= new Object();
		jsonObject["ureaInvoiceToDetailsId"] 	  =  ureaInvoiceToDetailsId;
		jsonObject["invoiceId"] 	  =  Number($('#invoiceId').val());
		
		$.ajax({
	             url: "deleteUreaInventoryDetails",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	 window.location.replace("showUreaInvoice?Id="+Number($('#invoiceId').val())+"&delete="+true+"");
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	}
	
	function deleteUreaInvoiceById(invoiceId) {
		
		  if(confirm("Are You Sure to Delete ?")){
			  showLayer();
				var jsonObject			= new Object();
				jsonObject["invoiceId"] =  invoiceId ;
			$.ajax({
		             url: "deleteUreaInvoice",
		             type: "POST",
		             dataType: 'json',
		             data: jsonObject,
		             success: function (data) {
		            	 if(data.deleted){
		            		 window.location.replace("UreaInventory?deleted="+true+"");
		            	 }else{
		            		showMessage('info', 'Delete Urea Details first !');
		            		hideLayer();
		            	 }
		            	 hideLayer();
		             },
		             error: function (e) {
		            	 showMessage('errors', 'Some error occured!')
		            	 hideLayer();
		             }
			});

		  }
	}
	
	function deleteUreaInvoice(){
		var invoiceId 		 	= Number($('#invoiceId').val());
		deleteUreaInvoiceById(invoiceId);
	}
	
	
	function ureaEntriesSearchOnEnter(e){
		var code = (e.keyCode ? e.keyCode : e.which);
		if(code == 13) { //Enter keycode
			ureaEntriesSearch();
		}
	}
	function invoiceSearch(pageNumber){
		showLayer();
		var jsonObject					= new Object();

		jsonObject["pageNumber"]			= pageNumber;
		jsonObject["term"]					= $('#searchInv').val();

		$.ajax({
			url: "searchUreaInvoice",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				setUreaInvoiceList(data);
			},
			error: function (e) {
				hideLayer();
				showMessage('errors', 'Some Error Occurred!');
			}
		});
	}
	function invoiceSearchOnEnter(e){
		var code = (e.keyCode ? e.keyCode : e.which);
		if(code == 13) { //Enter keycode
			invoiceSearch(1);
		}
	}

	function ureaEntriesSearch(ureaInvoiceNumber){

		showLayer();
		var jsonObject						= new Object();
		jsonObject["ureaInvoiceNumber"]			= $('#ureaInvoiceNumber').val();

		$.ajax({
			url: "searchUreaEntriesByNumber",
			type: "POST",
			dataType: 'json',
			data: jsonObject,
			success: function (data) {
				hideLayer();
				console.log('data ', data);
				if(data.invoiceId != undefined && data.invoiceId > 0){
					window.location.replace("showUreaDetails?Id="+data.invoiceId+"");
				}else{
					showMessage('errors', 'No Record Found !');
				}
				hideLayer();
			},
			error: function (e) {
				hideLayer();
				console.log(e);
				showMessage('errors', 'Some Error Occurred!');
			}
		});


	}
	