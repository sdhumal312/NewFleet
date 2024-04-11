var grid = null;
var data = null;
var columnConfiguration = null;
var tableProperties = null;
var columns	= null;
var gridId = '';
var containerId	= '';
//define some minimum height/width/padding before resizing
var DATAGRID_MIN_HEIGHT = 180;
var DATAGRID_MIN_WIDTH = 300;
var DATAGRID_BOTTOM_PADDING = 20;

function formatter(row, cell, value, columnDef, dataContext) {
    return value;
}

function setSlickData(resultList,columnConfig, tableProp){
	
	tableProperties	= tableProp;
	columnConfiguration = columnConfig;
	columns = [];
	if(tableProperties.showSerialNumber){
		columns.push( {
            id:  'sr', 
            name: 'SR No',
            field: 'sr',
            formatter: formatter,
            sortable: false,
            showColumnTotal : false,
			width:15,
			printWidth:15
      });
		//columnConfiguration.length = columnConfiguration.length + 1;
	}
		if(tableProperties.showSerialNumber){
			for (var i = 1; i <= columnConfiguration.length; i++) {
				  if(columnConfiguration[i-1].show == true){
					  columns.push( {
				            id:  i, 
				            name: columnConfiguration[i-1].labelid,
				            field: i,
				            formatter: formatter,
				            sortable: columnConfiguration[i-1].sortable,
				            showColumnTotal : columnConfiguration[i-1].showColumnTotal,
				            buttonCallback  : columnConfiguration[i-1].buttonCallback
				      });
				  }
			  		
		    }
		}else{
			for (var i = 0; i < columnConfiguration.length; i++) {
				  if(columnConfiguration[i].show == true){
					  columns.push( {
				            id:  i, 
				            name: columnConfiguration[i].labelid,
				            field: i,
				            formatter: formatter,
				            sortable: columnConfiguration[i].sortable,
				            showColumnTotal : columnConfiguration[i].showColumnTotal,
				            buttonCallback  : columnConfiguration[i].buttonCallback
				      });
				  }
			  		
		    }
		}
	  
	  
	  var options = {
			    enableCellNavigation: true,
			    enableColumnReorder: true,
			    autoHeight: false,
			    forceFitColumns : true,
			    createFooterRow: true,
			    showFooterRow: true,
			    footerRowHeight: 30,
			    headerRowHeight: 40,
			    rowHeight : 25,
			    multiColumnSort: true
	  };
	  
	  
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
	  	}
	  
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
		    grid = null;
		    var groupItemMetadataProvider = new Slick.Data.GroupItemMetadataProvider();
		    dataView = new Slick.Data.DataView({
		      groupItemMetadataProvider: groupItemMetadataProvider,
		      inlineFilters: true
		    });
		    
		    if(tableProperties.readDivFromConfig != undefined && tableProperties.readDivFromConfig){
		    	gridId		= tableProperties.gridDataId;
				containerId	= tableProperties.divId;
			}else{
				gridId		= 'myGrid';
				containerId	= 'gridContainer';
			}
		    console.log('slick data : ', data);
		    
		    grid = new Slick.Grid("#"+gridId, data, columns, options);
		    
		    attachAutoResizeDataGrid(grid, gridId, containerId);
		    
		    UpdateAllTotals(grid);
		    
		      grid.onCellChange.subscribe(function(e, args) {
		        UpdateTotal(args.cell, args.grid);
		      });
		    
		      grid.onColumnsReordered.subscribe(function(e, args) {
		        UpdateAllTotals(args.grid);
		      });
		      var printPlugin = new Slick.Plugins.Print();

			    grid.registerPlugin(printPlugin);

			    $('#printBtn').on('click', function () {
			        var strWindowFeatures = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes";
			        printPlugin.printToWindow(window.open('resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/print-grid.html', 'print_window', strWindowFeatures));
			       
			    });
			 
			    
				grid.onClick.subscribe(function (e,args) {
					var gridObj = args.grid;
					var dataView = gridObj.getData();
					var cell = gridObj.getCellFromEvent(e);
					
					var columnHead = grid.getColumns();

						if(columnHead[gridObj.getColumns()[cell.cell].id].buttonCallback != undefined) {
							var functionname = columnHead[gridObj.getColumns()[cell.cell].id].buttonCallback;
							window[functionname + ""](gridObj, resultList[args.row], args.row);
							e.stopPropagation();
						}
				});
				
				grid.onSort.subscribe(function (e, args) {
				      var cols = args.sortCols;
				      data.sort(function (dataRow1, dataRow2) {
				        for (var i = 0, l = cols.length; i < l; i++) {
				          var field = cols[i].sortCol.field;
				          var sign = cols[i].sortAsc ? 1 : -1;
				          var value1 = dataRow1[field], value2 = dataRow2[field];
				          var result = (value1 == value2 ? 0 : (value1 > value2 ? 1 : -1)) * sign;
				          if (result != 0) {
				            return result;
				          }
				        }
				        return 0;
				      });
				      grid.invalidate();
				      grid.render();
				    });
				
				 var columnData = data;
					for (var i = 0; i < columnData.length; i++) {
						columnData[i]['id']=parseInt(i)+1;
					} // adding row number to the table
					var old = JSON.stringify(columnData) //convert to JSON string
					columnData = JSON.parse(old);  // for removing null values in the jsonstring from webservice
					
					dataView.setItems(columnData);
					
					
					  //setAggregateFunction(dataView, '1');
}

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
		$('#' + gridId).height((grid.getOptions().rowHeight * grid.getData().length) + 90);
		$('#' + gridId).width(newSizes.width - 22);
		
		// resize the slickgrid canvas on all browser except some IE versions
		// exclude all IE below IE11
		if (new RegExp('MSIE [6-8]').exec(navigator.userAgent) === null && grid) {
			grid.resizeCanvas();
		}
	}
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
    	//else if(!isNaN(getValueFronATag(data[i][columnId]))){
			//total += (parseFloat(getValueFronATag(data[i][columnId])) || 0);
		//}
    }
    var columnElement = grid.getFooterRowColumn(columnId);
    $(columnElement).html(total.toFixed(2));
  }

function getValueFronATag(aTag){
	
	var va = aTag.includes('<a href="#" style="color: blue; background: #ffc;">');
	if(va){
		aTag = aTag.replace('<a href="#" style="color: blue; background: #ffc;">', '');
		aTag = aTag.replace('</a>', '');
		aTag = Number(aTag.trim());
	}
	return aTag;
}


function setAggregateFunction(dataView,column){
	var dataViewObject = dataView;
	
	var columnsArr = new Array();
	var columns = grid.getColumns();
	columns.forEach(function (col) {
		if(col.showColumnTotal){
			columnsArr.push(new Slick.Data.Aggregators.Sum(col.field));
		}
	});
	
	 dataView.setGrouping({
		    getter: column,
		    formatter: function (g) {
		      return g.value + "  <span style='color:green'>(" + g.count + " items)</span>";
		    },
		    aggregators: columnsArr,
		    aggregateCollapsed: false,
		    lazyTotalsCalculation: true,
		    comparer: function (a, b) {
				var x = a['value'], y = b['value'];
				return 1 * (x === y ? 0 : (x > y ? 1 : -1));
			 }
		  });
}