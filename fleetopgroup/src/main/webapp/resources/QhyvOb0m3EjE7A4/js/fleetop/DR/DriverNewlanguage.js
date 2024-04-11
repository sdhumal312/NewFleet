$(document).ready(function(){$("#DriverTable").DataTable({sScrollX:"100%", lengthMenu: [[10, 50, 100, -1], [10, 50, 100, "All"]], bScrollcollapse:!0,dom:"Blfrtip",buttons:["excel","print"],order:[[0,"desc"]]})}),$(document).ready(function(){$("#DriverReminderTable").DataTable({sScrollX:"100%",bScrollcollapse:!0,dom:"Blfrtip",buttons:["excel","print"],order:[[0,"desc"]]})});

$(document).ready(function(){
	
		$('input[type="checkbox"][name="downloadDocument"]').change(function() {
	        if ($(this).is(':checked')) {
	            if($("input[name='downloadDocument']:checked").length > 5){
					showMessage('info', 'You Can Select Maximum 5 Drivers  At a Time For Downloading Documents');
					$(this).prop('checked', false);
				}
	        } 
	    });
	
	
	
        $("#downloadZip").click(function () {
			showLayer();
			
			if($("input[name='downloadDocument']:checked").length > 5){
				showMessage('info', 'Please select 5 Drivers  at a time ');
				hideLayer();
			}else{
				var jsonObject			= new Object();
				jsonObject["companyId"]	= $("#companyId").val();
				
				var driverIds = new Array();
				$("input[name=downloadDocument]").each(function(){
					if ($(this).prop("checked")) {
						driverIds.push($(this).val());
					}
				});
				jsonObject.driverIds = driverIds;
			    const encodedData = encodeURIComponent(JSON.stringify(jsonObject));
				const contentType = 'application/zip, application/octet-stream; charset=UTF-8';
			   	const fileName = 'Driver Documents';
			    const uriWithParams = `${encodeURI("/downloadDriverDocumentsZip")}?jsonData=${encodedData}`;
			
				let request = new XMLHttpRequest();
			    request.open('GET', uriWithParams, true);
			
			    request.setRequestHeader('Content-Type', contentType);
				request.responseType = 'blob';
				
				    request.onload = function(e) {
				        console.log(e);
				        if (this.status === 200) {
							
							let blob = this.response;
							
								if (blob.size > 0) {
						            if (window.navigator.msSaveOrOpenBlob) {
						                window.navigator.msSaveBlob(blob, fileName);
						            } else {
						                let downloadLink = window.document.createElement('a');
						                downloadLink.href = window.URL.createObjectURL(new Blob([blob], { type: 'application/zip' }));
						                downloadLink.download = fileName;
						                document.body.appendChild(downloadLink);
						                downloadLink.click();
						                document.body.removeChild(downloadLink);
						            
						                $('input[type="checkbox"][name="downloadDocument"]').prop('checked', false);
						            }
						         }else{
									 $('input[type="checkbox"][name="downloadDocument"]').prop('checked', false);
									 showMessage('info', 'No Documents Found For Selected Driver.');
								 }   
				        }else if(this.status === 204){
							 // Handle scenario where server indicates no content
							 $('input[type="checkbox"][name="downloadDocument"]').prop('checked', false);
        					showMessage('info', 'No Documents Found For Selected Driver.');
						}
				        
				        
				    };
				    request.send();
					hideLayer();
				}	
				
				 
	        });
});
