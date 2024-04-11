function printDiv(divId) {
	var printContents = document.getElementById(divId).innerHTML;
	PopUp(printContents)
}

function PopUp(data) {
	var mywindow = window
			.open('', '',
					'left=0,top=0,width=900,height=600,toolbar=0,status=0,addressbar=0');

	var is_chrome = Boolean(mywindow.chrome);
	mywindow.document.write("<html><head><title>www.fleetop.com</title>");
	mywindow.document
			.write('<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/printBootstrap.css" />');
	

	mywindow.document
			.write('<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/FleetopPrint.min.css"  />');
	mywindow.document
			.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />">');

	mywindow.document.write('</head><body >');
	mywindow.document.write(data);
	mywindow.document.write('</body></html>');
	mywindow.document.close(); // necessary for IE >= 10 and necessary before onload for chrome

	if (is_chrome) {
		mywindow.onload = function() { // wait until all resources loaded 
			mywindow.focus(); // necessary for IE >= 10
			mywindow.print(); // change window to mywindow
			mywindow.close();// change window to mywindow
		};
	} else {
		mywindow.document.close(); // necessary for IE >= 10
		mywindow.focus(); // necessary for IE >= 10
		mywindow.print();
		mywindow.close();
	}

	return true;
}

function printDiv(divId) {
	var printContents = document.getElementById(divId).innerHTML;
	PopUp(printContents)
}

function PopUp(data) {
	var mywindow = window
			.open('', '',
					'left=0,top=0,width=900,height=600,toolbar=0,status=0,addressbar=0');

	var is_chrome = Boolean(mywindow.chrome);
	mywindow.document.write("<html><head><title>www.fleetop.com</title>");
	mywindow.document
			.write('<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/printBootstrap.css"/>');

	mywindow.document
			.write('<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/FleetopPrint.min.css" />');
	mywindow.document
			.write('<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" />');

	mywindow.document.write('</head><body >');
	mywindow.document.write(data);
	mywindow.document.write('</body></html>');
	mywindow.document.close(); // necessary for IE >= 10 and necessary before onload for chrome

	if (is_chrome) {
		mywindow.onload = function() { // wait until all resources loaded 
			mywindow.focus(); // necessary for IE >= 10
			mywindow.print(); // change window to mywindow
			mywindow.close();// change window to mywindow
		};
	} else {
		mywindow.document.close(); // necessary for IE >= 10
		mywindow.focus(); // necessary for IE >= 10
		mywindow.print();
		mywindow.close();
	}

	return true;
}


/*var advanceTableToExcel = (function() {
	  var uri = 'data:application/vnd.ms-excel;base64,'
	    , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="https://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'
	    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) }
	    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	  return function(table, name) {
	    if (!table.nodeType) table = document.getElementById(table)
	    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
	    window.location.href = uri + base64(format(template, ctx))
	  }
	})()*/

function advanceTableToExcel(tableId,reportName){
	var dt 			= new Date();
    var day 		= dt.getDate();
    var month 		= dt.getMonth() + 1;
    var year		= dt.getFullYear();
    var hour 		= dt.getHours();
    var mins 		= dt.getMinutes();
    
    var postfix = day + "_" + month + "_" + year + "_" + hour + "_" + mins;
	var finalName = reportName+"_"+postfix+".xlsx";
	TableToExcel.convert(document.getElementById(tableId), {
		  name: finalName,
		  sheet: {
		    name: "Sheet 1"
		  }
		});
	
	/*var dt 			= new Date();
    var day 		= dt.getDate();
    var month 		= dt.getMonth() + 1;
    var year		= dt.getFullYear();
    var hour 		= dt.getHours();
    var mins 		= dt.getMinutes();

var postfix = day + "." + month + "." + year + "_" + hour + "." + mins;
    //creating a temporary HTML link element (they support setting file names)
    var a = document.createElement('a');
    //getting data from our div that contains the HTML table
    var data_type = 'data:application/vnd.ms-excel';
    var table_div = document.getElementById(tableId);
    var table_html = table_div.outerHTML.replace(/ /g, '%20');
    a.href = data_type + ', ' + table_html;
    //setting the file name
    a.download = ''+reportName+'' + postfix + '.xlsx';
    //triggering the function
    a.click();
    //just in case, prevent default behaviour
    preventDefault();*/
}
	
function saveImageToPdf(idOfHtmlElement){
	
	$("#printPdf").addClass('hide');
	$("#scrollId").removeClass("scrollit");
	
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
                doc.save('InvoicePrint.pdf');
                
                $("#scrollId").addClass("scrollit");
                $("#printPdf").removeClass('hide');
            }
        });
  
   
}	

