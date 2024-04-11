(function ($) {
    'use strict';

    var SlickPrint = function () {

        var _self = this;
        var _grid;

        this.init = function (grid) {
            _grid = grid;
            
        };
        

        this.printToHtml = function () {
            var numRows = _grid.getDataLength();
            var columns = _grid.getColumns();
            var numCols = columns.length;
            var r, c;
            var rows = [], cols = [], headers = [],footers= [];
            var columnPicker='<div id="grpChkBox" style="position:fixed;top:20px;left:20px;background: #EEE; overflow-y: auto; width:325px;height:530px; overflow-x: auto;padding:10px;" class="hidden-print">';
            var cellNode;
            var topRow = _grid.getRenderedRange().top;
          
            var footerRowNodes = _grid.getFooterRow().childNodes;
            
            for(var i = 0; i<footerRowNodes.length;i++ ){
            	footers.push(footerRowNodes[i].innerText);
            }
            
            
            columnPicker+="<button style='width:100%;' class='btn btn-success' onclick='applyPrintSetting();'>Laser Print</button>";
            columnPicker+="<button style='width:100%;' class='btn btn-primary' onclick='applyPrintSettingPlainPrint();'>Plain Print</button>";
            columnPicker+="<button style='width:100%;' class='btn btn-warning' onclick='exportDataToExcel();'>Export To Excel</button>";
            columnPicker+="<button style='width:100%;' class='btn btn-warning' onclick='exportDataToPDF();'>Export To PDF</button>";
            columnPicker+="<button style='width:100%;' class='btn btn-danger' onclick='window.close();'>Close</button>";
            columnPicker+="</div>";
            
            var headerValue = $("#reportHeader").html();
           
            var companyName = $('#companyName').html();
            /*
             * If headerValue is undefined then making it empty
             */
            if(headerValue == undefined) {
            	headerValue = "";
            }
            var headerTable;
            	
            headerTable = 	'<tr><td align="center" class="" style="font-size: large;"><b>'+companyName+'</b></td></tr>'+
					'<tr><td align="center" class="" style="font-size: medium;"><b id="tableHeaderValue">'+headerValue+'</b></td></tr>'+$('#selectedReportDetails').html();
					
            
            
            /*
             * If headerValue is undefined then making it empty
             */
            if(headerValue == undefined) {
            	headerValue = "";
            }
           
            columns.forEach(function (col) {
                headers.push(col.name);
            });
            

            Slick.GlobalEditorLock.cancelCurrentEdit();

            _grid.scrollRowToTop(0);
            
            for (r = 0; r < numRows; r++) {
                cols = [];
                for (c = 0; c < numCols; c++) {
                    cellNode = _grid.getCellNode(r, c);
                    if (!cellNode) {
                        _grid.scrollRowToTop(r);
                        cellNode = _grid.getCellNode(r, c);
                    }
                    cols.push($(cellNode).text());
                }
                console.log('');
                //row+="<td data-cell='"+columns[c].name+"' class='"+columns[c].field+" "+columns[c].cssClass+" truncate'>"+cellNode+"</td>";
                rows.push('<td>' + cols.join('</td><td>') + '</td>');
            }
            
           // rows.push(footers);

            var table = [
            	  '<div>',columnPicker,
                  '<div id="chromeTable" style=""><table class="print">',headerTable,'</table><table id="table" border="1"  class="print">',
                '<thead>',
                '<tr>',
                    '<th>' + headers.join('</th><th>') + '</th>',
                '</tr>',
                '</thead>',
                '<tbody>',
                    '<tr>' + rows.join('</tr>\n<tr>') + '</tr>',
                    '<tr>',
                    	'<th>' + footers.join('</th><th>') + '</th>',
                    '</tr>',
                '</tbody>',
                '</table></div></div>'
            ].join('\n');

            _grid.scrollRowToTop(topRow);

            return table;
        };

        this.printToElement = function ($element) {
            $($element).html(_self.printToHtml());
         };

        
        this.printToWindow = function (w) {
            w.onload = function () {
                setTimeout(function () {
                    _self.printToElement(w.document.body,w);
                });
            };
        };
        
       /* this.printToWindow = function (w) {
            w.onload = function () {
                setTimeout(function () {
                    _self.printToElement(w.document.body);
                });
            };
        };*/
    };

    // register namespace
    $.extend(true, window, {
        Slick: {
            Plugins: {
                Print: SlickPrint
            }
        }
    });
}(jQuery));
