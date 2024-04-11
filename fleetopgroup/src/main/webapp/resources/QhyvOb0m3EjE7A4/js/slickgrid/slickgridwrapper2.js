var dataView;
var resultList;
var grid;
var data = [];
var tableProperties = null;
var DATAGRID_MIN_HEIGHT = 180;
var DATAGRID_MIN_WIDTH = 300;
var DATAGRID_BOTTOM_PADDING = 20;
var columns = [];
var columnFilters = {};
var totalAverage = 0;
var gridId = '';
var containerId	= '';


function formatter(row, cell, value, columnDef, dataContext) {
    return value;
}
var linkFormatter = function (row, cell, value, columnDef, dataContext) {
    return '<a href="#/Link/' + dataContext['id'] + '">' + value + '</a>';
};
function attachAutoResizeDataGrid(grid, gridId, gridContainerId) {
	var gridDomElm = $('#' + gridId);
	if (!gridDomElm || typeof gridDomElm.offset() === "undefined") {
		// if we can't find the grid to resize, return without attaching anything
		return null;
	}
	//-- 1st resize the datagrid size on first load (because the onResize is not triggered on first page load)
	resizeToFitBrowserWindow(grid, gridId, gridContainerId);
	
	//-- 2nd attach a trigger on the Window DOM element, so that it happens also when resizing after first load
	$(window).on("resize", function () {
		// for some yet unknown reason, calling the resize twice removes any stuttering/flickering when changing the height and makes it much smoother
		resizeToFitBrowserWindow(grid, gridId, gridContainerId);
		resizeToFitBrowserWindow(grid, gridId, gridContainerId);
	});
	// in a SPA (Single Page App) environment you SHOULD also call the destroyAutoResize()
}

/* destroy the resizer when user leaves the page */
function destroyAutoResize() {
	$(window).trigger('resize').off('resize');
}
/**
* Private function, calculate the datagrid new height/width from the available space, also consider that a % factor might be applied to calculation
* object gridOptions
*/
function calculateGridNewDimensions(gridId, gridContainerId) {
	var availableHeight = $(window).height() - $('#' + gridId).offset().top - DATAGRID_BOTTOM_PADDING;
	var availableWidth = $('#' + gridContainerId).width();
	var newHeight = availableHeight;
	var newWidth = availableWidth;
	// we want to keep a minimum datagrid size, apply these minimum if required
	if (newHeight < DATAGRID_MIN_HEIGHT) {
		newHeight = DATAGRID_MIN_HEIGHT;
	}
	if (newWidth < DATAGRID_MIN_WIDTH) {
		newWidth = DATAGRID_MIN_WIDTH;
	}
	
	return {
		height: newHeight,
		width: newWidth
	};
}

/** resize the datagrid to fit the browser height & width */
function resizeToFitBrowserWindow(grid, gridId, gridContainerId) {
	// calculate new available sizes but with minimum height of 220px
	var newSizes = calculateGridNewDimensions(gridId, gridContainerId);
	if (newSizes) {
		// apply these new height/width to the datagrid
		
		$('#' + gridId).height((grid.getOptions().rowHeight * grid.getData().getLength()) + 90);
		$('#' + gridId).width(newSizes.width - 22);
		
		// resize the slickgrid canvas on all browser except some IE versions
		// exclude all IE below IE11
		if (new RegExp('MSIE [6-8]').exec(navigator.userAgent) === null && grid) {
			grid.resizeCanvas();
		}
	}
}

	
function avgTotalsFormatter(totals, columnDef, grid) {
	  var val = totals.avg && totals.avg[columnDef.field];
	  
	  if (val != null) {
	    return "avg: " + val.toFixed(2);
	  }
	  return "";
	}
	function sumTotalsFormatter(totals, columnDef, grid) {
		var val = totals.sum && totals.sum[columnDef.field];
		if (val != null) {
			return "<b> Total : " + ((parseFloat(val)*100)/100).toFixed(2)+"</b>";
		}
		return "";
	}//  return Number(value).toFixed( nDecimals );

	
	function comparer(a, b) {
	  var x = a[sortcol], y = b[sortcol];
	  return (x == y ? 0 : (x > y ? 1 : -1));
	}
	function setGrouping(feild) {
		var columnsArr = new Array();
		var columns = grid.getColumns();
		columns.forEach(function (col) {
			if(col.showColumnTotal){
				columnsArr.push(new Slick.Data.Aggregators.Sum(col.field));
			}
			if(col.showColumnTotalAvg){
				columnsArr.push(new Slick.Data.Aggregators.Avg(col.field));
			}
		});
	  dataView.setGrouping([
		    {
		      getter: feild,
		      formatter :function (g) {
		    	  return  g.value + "  <span style='color:green'>(" + g.count + " rows)</span>";
		      },
		      	aggregators: columnsArr,
			    aggregateCollapsed: true,
			    lazyTotalsCalculation: true
		    }
		  ]);
	  if(tableProperties.collapseOnLoad){
		  dataView.collapseAllGroups();
	  }
	}
	
	function setMultiGrouping(feild) {
		var columnsArr = new Array();
		var columns = grid.getColumns();
		columns.forEach(function (col) {
			if(col.showColumnTotal){
				columnsArr.push(new Slick.Data.Aggregators.Sum(col.field));
			}
			if(col.showColumnTotalAvg){
				columnsArr.push(new Slick.Data.Aggregators.Avg(col.field));
			}
		});
		var groupColArr =  feild.split(',');
	  dataView.setGrouping([
		   {
		      getter: groupColArr[0],
		      formatter :function (g) {
		    	  return  g.value + "  <span style='color:green'>(" + g.count + " rows)</span>";
		      },
		      	aggregators: columnsArr,
			    aggregateCollapsed: true,
			    lazyTotalsCalculation: true
		    },
		    {
		      getter: groupColArr[1],
		      formatter :function (g) {
		    	  return  g.value + "  <span style='color:green'>(" + g.count + " rows)</span>";
		      },
		        aggregators: columnsArr,
			    aggregateCollapsed: true,
			    lazyTotalsCalculation: true
		    }
		  ]);
	  if(tableProperties.collapseOnLoad){
		  dataView.collapseAllGroups();
	  }
	}
	
	
	function loadData() {
	  data = [];
	  
		data = [];
	    for (var i = 0; i< resultList.length; i++) {
	        var d = (data[i] = {});
	          d["id"] = i;
	          
	        if(tableProperties.showSerialNumber){
	        	d['sr'] = i + 1;
	        	 for(var j = 1; j <= columnConfiguration.length; j++){
		        	  
		        	  var feildName = columnConfiguration[j -1].tableDtoName;
		        	  
		        	  if(columnConfiguration[j -1 ].show){
		        		  if(columnConfiguration[j-1].inputElement == undefined || columnConfiguration[j-1].inputElement != 'link'){
		        			  if(typeof(resultList[i][feildName]) == 'number'){
		        				  d[j] = (resultList[i][feildName]).toFixed(2); 
		        			  }else{
		        				  d[j] = resultList[i][feildName]; 
		        			  }
		        			  
		        		  }else{
		        			  d[j] = '<a href="#" style="color: blue; background: #ffc;">'+resultList[i][feildName]+'</a>';
		        		  }
		        		   
		        	  }
		          }
	        }else{
	        	 for(var j = 0; j < columnConfiguration.length; j++){
		        	  
		        	  var feildName = columnConfiguration[j].tableDtoName;
		        	  
		        	  if(columnConfiguration[j].show){
		        		  if(columnConfiguration[j].inputElement == undefined || columnConfiguration[j].inputElement != 'link'){
		        			  if(typeof(resultList[i][feildName]) == 'number'){
		        				  d[j] = (resultList[i][feildName]).toFixed(2); 
		        			  }else{
		        				  d[j] = resultList[i][feildName]; 
		        			  }
		        			  
		        		  }else{
		        			  d[j] = '<a href="#" style="color: blue; background: #ffc;">'+resultList[i][feildName]+'</a>';
		        		  }
		        		   
		        	  }
		          }
	        }
	          
	       
	      }
		
	}
	
	function setSlickData(resultLit,columnConfig, tableProp){
		 var options = {
				    enableCellNavigation: true
				    ,enableColumnReorder: true
				    ,autoHeight: false
				    ,forceFitColumns : true
				    ,createFooterRow: true
				    ,showFooterRow: true
				    ,footerRowHeight: 30
				    ,headerRowHeight: 40
				    ,rowHeight : 30
				    ,multiColumnSort: true
				    ,showGrouping:false
				    ,fullTableHeight : true
				    ,showMultiGrouping : false
				    
		  };
		
		resultList = resultLit;
		
		
		tableProperties	= tableProp;
		columnConfiguration = columnConfig;
		
		if(tableProperties != undefined && tableProperties != null){
	  		if(tableProperties.enableColumnReOrder != undefined){
	  			options.enableColumnReOrder = tableProperties.enableColumnReOrder;
	  		}
	  		if(tableProperties.enableCellNavigation != undefined){
	  			options.enableCellNavigation = tableProperties.enableCellNavigation;
	  		}
	  		if(tableProperties.rowHeight != undefined){
	  			options.rowHeight = tableProperties.rowHeight;
	  		}
	  		if(tableProperties.forceFitColumns != undefined){
	  			options.forceFitColumns = tableProperties.forceFitColumns;
	  		}
	  		if(tableProperties.showGrouping != undefined){
	  			options.showGrouping = tableProperties.showGrouping;
	  			tableProperties.showSerialNumber = false
	  			
	  			if(tableProperties.columnNumber != undefined){
		  			options.columnNumber = tableProperties.columnNumber;
		  		}
	  		}
	  		if(tableProperties.showMultiGrouping != undefined){
	  			options.showMultiGrouping = tableProperties.showMultiGrouping;
	  			options.showGrouping = false;
	  			tableProperties.showSerialNumber = false
	  			if(tableProperties.columnNumber != undefined){
		  			options.columnNumber = tableProperties.columnNumber;
		  		}
	  		}
	  		if(tableProperties.fullTableHeight != undefined){
	  			options.fullTableHeight = tableProperties.fullTableHeight;
	  		}
	  		if(tableProperties.readDivFromConfig != undefined && tableProperties.readDivFromConfig){
		    	gridId		= tableProperties.gridDataId;
				containerId	= tableProperties.divId;
			}else{
				gridId		= 'myGrid';
				containerId	= 'gridContainer';
			}
	  		
	  		if(tableProperties.gridDataHeight != undefined){
				  $('#'+gridId).height(tableProperties.gridDataHeight);
	  		}
	  		
	  		
	  		
	  	}

		columns = [];
		if(tableProperties.showSerialNumber){
			columns.push( {
		        id:  'sr', 
		        name: 'SR No',
		        field: 'sr',
		        formatter: formatter,
		        sortable: false,
		        showColumnTotal : false,
		        showColumnTotalAvg : false,
				width:15,
				printWidth:15
		  });
			//{id: "vehicle_registration", name: "Vehicle", field: "vehicle_registration", width: 70, sortable: true},
		}
			if(tableProperties.showSerialNumber){
				for (var i = 1; i <= columnConfiguration.length; i++) {
					  if(columnConfiguration[i-1].show == true){
						  var fieldPropertyStr = {
						            id:  i, 
						            name: columnConfiguration[i-1].labelid,
						            field: i,
						            formatter: formatter,
						            sortable: columnConfiguration[i-1].sortable,
						            showColumnTotal : columnConfiguration[i-1].showColumnTotal,
						            showColumnTotalAvg : columnConfiguration[i-1].showColumnTotalAvg,
						            buttonCallback  : columnConfiguration[i-1].buttonCallback
						    } ;
						  
						  if(columnConfiguration[i-1].showColumnTotal){
								fieldPropertyStr.groupTotalsFormatter = sumTotalsFormatter;
						  }
						  
						  if(columnConfiguration[i-1].showColumnTotalAvg){
							  fieldPropertyStr.groupTotalsFormatter = avgTotalsFormatter;
						  }
						  
						  columns.push(fieldPropertyStr);	
					  }
			    }
			}else{
				for (var i = 0; i < columnConfiguration.length; i++) {
					  if(columnConfiguration[i].show == true){
						 
						  var fieldPropertyStr = {
						            id:  i, 
						            name: columnConfiguration[i].labelid,
						            field: i,
						            formatter: formatter,
						            sortable: columnConfiguration[i].sortable,
						            showColumnTotal : columnConfiguration[i].showColumnTotal,
						            showColumnTotalAvg : columnConfiguration[i].showColumnTotalAvg,
						            buttonCallback  : columnConfiguration[i].buttonCallback
						     } ;
						  
						  if(columnConfiguration[i].showColumnTotal){
								fieldPropertyStr.groupTotalsFormatter = sumTotalsFormatter;
						  }
						  
						  if(columnConfiguration[i].showColumnTotalAvg){
							  fieldPropertyStr.groupTotalsFormatter = avgTotalsFormatter;
						  }
						  
						  columns.push(fieldPropertyStr);
					  }
				  		
			    }
			}
		
		 grid = null;
		 var groupItemMetadataProvider = new Slick.Data.GroupItemMetadataProvider();
		  dataView = new Slick.Data.DataView({
		    groupItemMetadataProvider: groupItemMetadataProvider,
		    inlineFilters: true
		  });

			loadData();
			
		  grid = new Slick.Grid("#"+gridId, dataView, columns, options);
		  dataView.beginUpdate();
		  
		  if(options.showGrouping){
				  setGrouping(options.columnNumber);
		  }else if(options.showMultiGrouping){
			  setMultiGrouping(options.columnNumber);
		  }
		  // register the group item metadata provider to add expand/collapse group handlers
		  grid.registerPlugin(groupItemMetadataProvider);
		  var printPlugin = new Slick.Plugins.Print();

		  grid.registerPlugin(printPlugin);
		  grid.setSelectionModel(new Slick.CellSelectionModel());
		 // var pager = new Slick.Controls.Pager(dataView, grid, $("#pager"));
		  var columnpicker = new Slick.Controls.ColumnPicker(columns, grid, options);
		  
			// initialize the model after all the events have been hooked up
			 
		
		
		  UpdateAllTotals(grid);
		  UpdateAllAvgTotals(grid);
		    
	      grid.onCellChange.subscribe(function(e, args) {
	        UpdateTotal(args.cell, args.grid);
	        UpdateAvg(args.cell, args.grid);
	      });
	    
	      grid.onColumnsReordered.subscribe(function(e, args) {
	        UpdateAllTotals(args.grid);
	        UpdateAllAvgTotals(args.grid);
	      });
	      $('#printBtn').on('click', function () {
		        var strWindowFeatures = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes";
		        printPlugin.printToWindow(window.open('resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/print-grid.html', 'print_window', strWindowFeatures));
		       
		    });
		 
			grid.onClick.subscribe(function (e,args) {
				var gridObj = args.grid;
				var dataView = gridObj.getData();
				var cell = gridObj.getCellFromEvent(e);
				var columnHead = grid.getColumns();
					if(columnHead[gridObj.getColumns()[cell.cell].id] != undefined && columnHead[gridObj.getColumns()[cell.cell].id].buttonCallback != undefined) {
						var functionname = columnHead[gridObj.getColumns()[cell.cell].id].buttonCallback;
						window[functionname + ""](gridObj, resultList[args.row], args.row);
						e.stopPropagation();
					}
			});
		  
			grid.onSort.subscribe(function (e, args) {
			    gridSorter(args.sortCols, dataView);
			});

			
		  dataView.onRowCountChanged.subscribe(function (e, args) {
			    grid.updateRowCount();
			    grid.render();
			  });
			  dataView.onRowsChanged.subscribe(function (e, args) {
			    grid.invalidateRows(args.rows);
			    grid.render();
			    if(options.fullTableHeight){
			    	attachAutoResizeDataGrid(grid, gridId, containerId);
			    }
			  });
			  dataView.setItems(data);

		
			  
			  dataView.endUpdate();
			  if(options.fullTableHeight){
			    	attachAutoResizeDataGrid(grid, gridId, containerId);
			   }
			  //gridDataHeight
			  
	}
	
	function UpdateAllTotals(grid) {
		
	    var columnIdx = grid.getColumns().length;
	   
	   var column = grid.getColumns();
	    while (columnIdx--) {
	    	if(columns[columnIdx].showColumnTotal){
	    		UpdateTotal(columnIdx, grid);
	    	}
	    }
	  }
	
	function UpdateTotal(cell, grid) {
	    var columnId = grid.getColumns()[cell].id;
	    var total = 0;
	    var i = data.length;
	    while (i--) {
	    	if(!isNaN(parseFloat(data[i][columnId]))){
	    		total += (parseFloat(data[i][columnId]) || 0);
	    	}
	    }
	    
	    var columnElement = grid.getFooterRowColumn(columnId);
	    $(columnElement).html(total.toFixed(2));
	  }
	
	function UpdateAllAvgTotals(grid) {
		
	    var columnIdx = grid.getColumns().length;
	   
	   var column = grid.getColumns();
	    while (columnIdx--) {
	    	if(columns[columnIdx].showColumnTotalAvg){
	    		UpdateAvg(columnIdx, grid);
	    	}
	    }
	  }
		
	function UpdateAvg(cell, grid) {
	    var columnId = grid.getColumns()[cell].id;
	    var total = 0;
	    var avg = 0;
	    var i = data.length;
	    var denom = i;
	    while (i--) {
	    	if(!isNaN(parseFloat(data[i][columnId]))){
	    		total += (parseFloat(data[i][columnId]));
	    	}
	    }
	    
	    var columnElement = grid.getFooterRowColumn(columnId);
	    $(columnElement).html(total.toFixed(2));
	  }
	
	/* destroy the resizer when user leaves the page */
	function destroyAutoResize() {
		$(window).trigger('resize').off('resize');
	}
	function gridSorter(sortCols, dataview) {
	    dataview.sort(function (row1, row2) {
	        for (var i = 0, l = sortCols.length; i < l; i++) {
	            var field = sortCols[i].sortCol.field;
	            var sign = sortCols[i].sortAsc ? 1 : -1;
	            var x = row1[field], y = row2[field];
	            var result = (x < y ? -1 : (x > y ? 1 : 0)) * sign;
	            if (result != 0) {
	                return result;
	            }
	        }
	        return 0;
	    }, true);
	}
	 function filter(item) {
		    for (var columnId in columnFilters) {
		      if (columnId !== undefined && columnFilters[columnId] !== "") {
		        var c = grid.getColumns()[grid.getColumnIndex(columnId)];
		        if (item[c.field] != columnFilters[columnId]) {
		          return false;
		        }
		      }
		    }
		    return true;
		  }
	 
	 