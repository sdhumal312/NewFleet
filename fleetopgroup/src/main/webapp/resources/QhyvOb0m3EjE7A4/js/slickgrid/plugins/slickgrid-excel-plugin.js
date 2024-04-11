(function ($) {
    'use strict';

    var SlickExcel = function () {

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
            var cellNode;
            var topRow = _grid.getRenderedRange().top;
          
            var footerRowNodes = _grid.getFooterRow().childNodes;
            
            for(var i = 0; i<footerRowNodes.length;i++ ){
            //innerText	
            	footers.push(footerRowNodes[i].innerText);
            }

            columns.forEach(function (col) {
                headers.push(col.name);
            });

            Slick.GlobalEditorLock.cancelCurrentEdit();

            _grid.scrollRowToTop(0);
            
           /* columns.forEach(function (col) {
            	if(col.dataType == 'text' || col.dataType == 'number'){
            		if(document.getElementById('columnTotal_'+col.slickId+col.field) != null){
            			footers+="<th class='"+col.field+" "+col.cssClass+"' style='width:"+col.printWidth+"mm;background-color: lightblue;'>"+document.getElementById('columnTotal_'+col.slickId+col.field).innerHTML+"</th>";;
            		}else{
            			footers+="<th class='"+col.field+" "+col.cssClass+"' style='width:"+col.printWidth+"mm;background-color: lightblue;'></th>";
            		}
            	}
            });
*/
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
                rows.push('<td>' + cols.join('</td><td>') + '</td>');
            }
            
           // rows.push(footers);

            var table = [
                '<table border="1" id="printTable" class="table table-bordered">',
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
                '</table>'
            ].join('\n');

            _grid.scrollRowToTop(topRow);

            return table;
        };

        this.printToElement = function ($element,w) {
            $($element).html(_self.printToHtml());
            w.applyColumnPicker();
            w.resizeColumn();
            w.exportDataToExcel();
             }

        this.printToWindow = function (w) {
            w.onload = function () {
                setTimeout(function () {
                    _self.printToElement(w.document.body,w);
                });
            };
        };
    };

    // register namespace
    $.extend(true, window, {
        Slick: {
            Plugins: {
                Excel: SlickExcel
            }
        }
    });
}(jQuery));
