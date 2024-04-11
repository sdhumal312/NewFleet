$(document).ready(function() {
	
	console.log("before request ")
	$.ajax({
             url		: "getConfigurationModules",
             type		: "POST",
             dataType	: 'json',
             success: function (data) {
            	 console.log("Modules-- "+ JSON.stringify(data.Modules))
            	 setModuleIdAndName(data);
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	 });
	 
	 $('#saveModuleConfig').click(function(e) {
			var jsonObject			= new Object();
			jsonObject["moduleId"]  = $("#moduleId").val();
			
			showLayer();
			$.ajax({
	             url		: "saveModuleConfiguration",
	             type		: "POST",
	             dataType	: 'json',
	             data     	: jsonObject,
	             success: function (data) {
					 if(data.savedDataToDB == "true" || data.savedDataToDB == true){
	            	 	console.log("success-- "+ JSON.stringify(data.savedDataToDB));
	            	 	hideLayer();
	            	 	showMessage('success', 'Configuration Saved Successfully..!');
	            	 }
	             },
	             error: function (e) {
	            	 showMessage('errors', 'Some error occured!')
	            	 hideLayer();
	             }
		   });
		
	 });
	 

});
function setModuleIdAndName(data){
	console.log("inside method -- " + JSON.stringify(data.Modules));
	var selectElement = $("#moduleId");
	selectElement.empty();
	
	$.each(data.Modules, function (id, text) {
	    var option = $("<option>", {
	        value: id,
	        text: text,
	        style: "text-align: center;"
	    });
    selectElement.append(option);
    });
    selectElement.select2();
    selectElement.val(selectElement.find('option:first').val()).trigger('change');
}

$(document).ready(function() {
    $.getJSON("getCompanyNameList.in", function(e) {
		setCompanyIdAndName(e);
    })
    
	$('#getConfigurationData').click(function(e) {
	 	getConfigurationData();
	});
});


function setCompanyIdAndName(data){
	 console.log("inside method -- " + JSON.stringify(data));
	 var selectElement = $("#companyId");
	 selectElement.empty();

	  $.each(data, function (index, company) {
        var option = $("<option>", {
            value: company.company_id,
            text: company.companyCode,
            style: "text-align: center;"
        });
           
    selectElement.append(option);
    });
    selectElement.select2();
    selectElement.val(selectElement.find('option:first').val()).trigger('change');
}


function getConfigurationData(){
	
	if ($("#companyId").val() === "" || $("#companyId").val() === null || $("#companyId").val() === undefined) {
	    console.log("inside validate");
	    showMessage('info', 'Please Select Company First!! ');
	    return false;
	}

	var jsonObject			= new Object();
		jsonObject["companyId"]  = $("#companyId").val();
		jsonObject["moduleId"]   = $("#moduleId").val();
	 	 
	 	 
	 	showLayer();
		$.ajax({
             url		: "getCompanyConfigurationData",
             type		: "POST",
             dataType	: 'json',
             data     	: jsonObject,
             success: function (data) {
				 hideLayer();
				 console.log("configuration ---------- "+ JSON.stringify(data.configuration));
				 console.log("configurationList "+ JSON.stringify(data.configurationList));
				 setUIConfiguration(data);
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	   });
}

function setUIConfiguration(data){

	$("#switchContainer").empty();
	var configList = data.configurationList;

	const switchContainer = document.getElementById('switchContainer');
	 
	 //switch and property starts here
	 for(var i=0 ;i<configList.length; i++) {
		 
		var value 			  = configList[i].value;
		var companyConfigId   = configList[i].companyConfigurationId;
		var name 			  = configList[i].name;
	    var flag 			  = false;
         
            console.log("value -- " + configList[i].value);
           
            const divElement = document.createElement('div');
	        divElement.classList.add('grid');
           
            const switchElement = document.createElement('label');
            switchElement.classList.add('switch');

			
			let switchInput = null;
			let  sliderSpan = null;
            if(configList[i].value === "true" || configList[i].value=== true  || configList[i].value === false || configList[i].value === "false"){
				
				if(configList[i].value === "true" || configList[i].value=== true){
					flag= true;
				}
				
				console.log("inside if")
	            switchInput = document.createElement('input');
	            switchInput.type = 'checkbox';
	            switchInput.checked = flag; // Set the initial state based on the value
	            switchInput.classList.add('input');
	            
                sliderSpan  = document.createElement('span');
                sliderSpan.classList.add('slider', 'round');

			    // change event listener for the switch button
			    switchInput.addEventListener('change', createSwitchHandler(configList[i].companyConfigurationId, configList[i].name));
            
			}else{
				console.log("inside else");
				switchInput = document.createElement('button');
				switchInput.textContent = "Click"; // Set the initial text content based on the value
				switchInput.classList.add('btn', 'btn-primary');
				
				console.log("value "+ value);

				//button clicked event
				(function (value, companyConfigId,name) {
		            switchInput.addEventListener('click', function () {
		                console.log("inside click");
		                showModal(value,companyConfigId,name);
		            });
		        })(value, companyConfigId,name);
			}
			
            // Create a span for the slider
            const labelElement = document.createElement('label');
	        labelElement.textContent = configList[i].name;
			
           	switchElement.appendChild(switchInput);
           	if(sliderSpan != null){
           		 switchElement.appendChild(sliderSpan);
            }
            divElement.append(switchElement);
            divElement.appendChild(labelElement);
            switchContainer.appendChild(divElement);
		 
		   // Create a function factory to capture the values at each iteration
	       function createSwitchHandler(companyConfigId, name) {
	          return function () {
	            setValues(switchInput.checked, companyConfigId, name);
	        };
	    }
	 }
	 
	 //for ends
}

function showModal(value,companyConfigId,name){
	
	console.log("inside method " + companyConfigId);
	$("#companyConfigId").val(companyConfigId);
	$("#propertyValue").val(value);
	$("#property").val(name);
	$("#propertyText").html(name);
	$("#modalContainer").modal('show');
	
}
function setValues(value,companyConfigId,name)
{
	$("#companyConfigId").val(companyConfigId);
	$("#propertyValue").val(value);
	$("#property").val(name);
	
	updatePropertyValue();
}

function updatePropertyValue(){
	console.log("update ...")
	
	console.log("propertyValue "+$("#propertyValue").val());
	console.log("moduleId "+ $("#moduleId").val());
	console.log("company "+ $("#companyId").val());
	console.log("configId  "+ $("#companyConfigId").val());
	 
	var JsonObject  = new Object();
	JsonObject["configId"]  		= $("#companyConfigId").val();
	JsonObject["propertyValue"]		= $("#propertyValue").val();
	JsonObject["companyId"]         = $("#companyId").val();
	JsonObject["moduleId"]			= $("#moduleId").val();
	JsonObject["propertyName"]		= $("#property").val();
	
    showLayer();
	$.ajax({
             url		: "updateCompanyConfigurationValue",
             type		: "POST",
             dataType	: 'json',
             data     	:  JsonObject,
             success: function (data) {
				hideLayer();
				if(data.Success == true || data.success =="true"){
					showMessage('success', 'Data Updated Successfully !! ');
				}
				$("#modalContainer").modal('hide');
				getConfigurationData();
				
             },
             error: function (e) {
            	 showMessage('errors', 'Some error occured!')
            	 hideLayer();
             }
	   });
			
}






/*$.each(data.configuration, function (key, value) {
		
		   console.log("insidde each ")
           
           console.log("value -- " + value);
           
            const divElement = document.createElement('div');
	        divElement.classList.add('grid');
           
            const switchElement = document.createElement('label');
            switchElement.classList.add('switch');

			
			let switchInput = null;
			let  sliderSpan = null;
            // Create a switch input
            if(value === "true" || value=== true  || value === false ||value === "false"){
				console.log("inside if")
	            switchInput = document.createElement('input');
	            switchInput.type = 'checkbox';
	            switchInput.checked = value; // Set the initial state based on the value
	            switchInput.classList.add('input');
	            
                sliderSpan  = document.createElement('span');
                sliderSpan.classList.add('slider', 'round');
            
			}else{
				console.log("inside else");
				switchInput = document.createElement('button');
				switchInput.textContent = "Click"; // Set the initial text content based on the value
				switchInput.classList.add('btn', 'btn-primary');
				
				switchInput.addEventListener('click', function() {
				    // Your click event handling code goes here
				    console.log("inside click");
				    showModal(value);
				})

				console.log("inside else")
				switchInput = document.createElement('button');
	            switchInput.type = 'text';
	            switchInput.value = value; // Set the initial state based on the value
			}
			
            // Create a span for the slider
           
           
            const labelElement = document.createElement('label');
	        labelElement.textContent = key;

			
           	switchElement.appendChild(switchInput);
           	if(sliderSpan != null){
           		 switchElement.appendChild(sliderSpan);
            }
            divElement.append(switchElement);
            divElement.appendChild(labelElement);
            
            switchContainer.appendChild(divElement);
			
	 });*/
	 
	 
///oldd
        /*$("#companyId").empty(), $("#companyId").append($("<option value=''>").text("Please Select ").attr("value", "")), $.each(e, function(e, t) {
            $("#companyId").append($("<option>").text(t.companyCode).attr("value", t.company_id))
        })*/
        
 
