function IsDepartName(e){var r=0==e.keyCode?e.charCode:e.keyCode,n=r>31&&33>r||r>44&&48>r||r>=48&&57>=r||r>=65&&90>=r||r>=97&&122>=r||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDepartName").innerHTML="Special Characters not allowed",document.getElementById("errorDepartName").style.display=n?"none":"inline",n}function IsDepartCode(e){var r=0==e.keyCode?e.charCode:e.keyCode,n=r>31&&33>r||r>44&&48>r||r>=48&&57>=r||r>=65&&90>=r||r>=97&&122>=r||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDepartCode").innerHTML="Special Characters not allowed",document.getElementById("errorDepartCode").style.display=n?"none":"inline",n}function IsDepartHOD(e){var r=0==e.keyCode?e.charCode:e.keyCode,n=r>31&&33>r||r>39&&42>r||r>=45&&57>=r||r>=65&&90>=r||r>=97&&122>=r||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDepartHOD").innerHTML="Special Characters not allowed",document.getElementById("errorDepartHOD").style.display=n?"none":"inline",n}function IsDepartDescription(e){var r=0==e.keyCode?e.charCode:e.keyCode,n=r>31&&33>r||r>=45&&57>=r||r>=65&&90>=r||r>=97&&122>=r||-1!=specialKeys.indexOf(e.keyCode)&&e.charCode!=e.keyCode;return document.getElementById("errorDepartDescription").innerHTML="Special Characters not allowed",document.getElementById("errorDepartDescription").style.display=n?"none":"inline",n}var specialKeys=new Array;specialKeys.push(8),specialKeys.push(9),specialKeys.push(46),specialKeys.push(36),specialKeys.push(35),specialKeys.push(37),specialKeys.push(39);

function departmentvalidate(){
	
	if($("#departmentName").val() == undefined || ($("#departmentName").val()).trim() == "" ){
		showMessage('info','Please Enter  Department Name')

		return false;
	}
} 