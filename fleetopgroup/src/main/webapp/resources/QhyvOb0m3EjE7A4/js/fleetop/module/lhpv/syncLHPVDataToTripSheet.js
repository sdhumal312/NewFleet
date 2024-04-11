 $(document).ready(function() {
    $("#RenewalSelectVehicle").select2({
        minimumInputLength: 2,
        minimumResultsForSearch: 10,
        ajax: {
            url: "getVehicleListFuel.in?Action=FuncionarioSelect2",
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
                            text: e.vehicle_registration,
                            slug: e.slug,
                            id: e.vid
                        }
                    })
                }
            }
        }
    })
});
 
 function syncLHPVDataFromIVCARGO(){
	 
	 	showLayer();
 
		var jsonObject			= new Object();
		jsonObject["vid"] 		=  $('#RenewalSelectVehicle').val();
		$.ajax({
	             url: "syncLhpvDetailsForVehicle",
	             type: "POST",
	             dataType: 'json',
	             data: jsonObject,
	             success: function (data) {
	            	setLhpvData(data);
	            	 hideLayer();
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		});
	 
 }
 
 function setLhpvData(data){
	 if(data.lhpvDetailsList != undefined && data.lhpvDetailsList != null && data.lhpvDetailsList.length > 0){
		 $('#lhpvDiv').show();
		$("#lhpvBody").empty();
		 var lhpvDetailsList = data.lhpvDetailsList;
		var srNo = 1;
		 for(var i = 0; i < lhpvDetailsList.length; i++ ) {
				var tr = '<tr>'
					+'<td class="fit" >'+srNo+'</td>'
					+'<td class="fit" ><input name="select" type="checkBox" id="lhpv_'+lhpvDetailsList[i].lHPVDetailsId+'" /></td>'
					+'<td><a href "#">'+lhpvDetailsList[i].lHPVNumber+'</a></td>'
					+'<td>'+lhpvDetailsList[i].lhpvDateTime+'</td>'
					+'<td>'+lhpvDetailsList[i].advanceAmount+'</td>'
					+'<td>'+lhpvDetailsList[i].lorryHire+'</td>'
					/*+'<td><a href="addTripSheetEntries.in?lHPVDetailsId='+lhpvDetailsList[i].lHPVDetailsId+'">Create TripSheet</a></td>'*/
					+'</tr>';
				$('#lhpvBody').append(tr);
				srNo++;
			}
			
	 }else{
		 	$('#lhpvDiv').hide();
			$("#lhpvBody").empty();
		 showMessage('info', 'No Record Found !');
	 }
 }
 
 function createMultipleLhpvTripSheet(){
	 console.log('createMultipleLhpvTripSheet ..........');
	 var anyChecked = false;
	 var lhpvIds	= '';
	 $('input[name*=select]' ).each(function(){
			var vehicleVal = this.id;
			if($('#'+vehicleVal).prop("checked")){
				anyChecked = true;
				var lhpvId = vehicleVal.split('_')[1];
				lhpvIds += lhpvId+',';
			}
	});
	 if(!anyChecked){
		 showMessage('info', 'Please select atleast one Lhpv !');
		 return false;
	 }
	 lhpvIds = lhpvIds.slice(0,-1);
	 
	 window.location.replace("addTripSheetEntries.in?hexLhpvIds="+toHex(lhpvIds));
 }
 
 function toHex(str){
	  try{
		  console.log('string : ', str)
	   var  hex = unescape(encodeURIComponent(str))
	    .split('').map(function(v){
	      return v.charCodeAt(0).toString(16)
	    }).join('')
	    console.log('hex : ', hex)
	  }
	  catch(e){
	    hex = str
	    console.log('invalid text input: ' + str)
	  }
	  return hex
	}