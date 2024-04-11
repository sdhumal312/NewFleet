define(['slickGrid','moment'],function(slickGrid,moment) {
	var _slickThisObj;
	var slickGridInstanceGlobalObj = new Object();
	return {
		applyGrid:function(jsonInObject) {
			_slickThisObj = this;
			var options = {
					ColumnHead  		: [],  // header row array
					ColumnData  		: [],  // column data with key
					Language  	 		: [],
					//DivId	   	 		: 'slickDivId',
					ShowPager  	 		: false,
					ShowPinToLeft 		: false,
					ShowSorting 		: true,
					ShowColumnPicker 	: false,
					EnableColumnReorder : false,
					ShowCheckBox 		: false,
					ShowGrouping 		: false,
					ShowDeleteButton 	: false,
					ShowPrintButton 	: false,
					ShowExportExcelButton: false,
					ShowPartialButton 	: false,
					EditRowsInSlick		: false,
					InnerSlickId 		: 'mySlickGrid',
					InnerSlickHeight	: '250px',
					EditableColumn 		: false,
					RemoveSelectAllCheckBox: false,
					PagerHeight			: '20px',
					ColumnHiddenConfiguratin: [],
					DivId	   	 		: 'mainDivId',
					PersistGridToAppend : false,
					CallBackFunctionForPartial : null,
					fullTableHeight		: false,
					rowHeight			: 25
			}

			var jsonOptions = $.extend(options,jsonInObject);

			if(typeof  jsonOptions.fullTableHeight != "undefined"){
				fullTableHeight = jsonOptions.fullTableHeight;
			}else{
				fullTableHeight = false;
			}

			if(typeof  jsonOptions.rowHeight != "undefined"){
				rowHeight = jsonOptions.rowHeight;
			}

			if(fullTableHeight){
				jsonOptions.InnerSlickHeight = jsonOptions.ColumnData.length*(jsonOptions.rowHeight)+90+'px';
			}
			
			if(jsonOptions.PersistGridToAppend == false){
				slickGridInstanceGlobalObj[jsonOptions.InnerSlickId] = _slickThisObj.setSlickTable(jsonOptions); 
			}else{
				if(typeof slickGridInstanceGlobalObj[jsonOptions.InnerSlickId] == "undefined"){
					slickGridInstanceGlobalObj[jsonOptions.InnerSlickId] = _slickThisObj.setSlickTable(jsonOptions); 
				}else{
					_slickThisObj.appendDatatoSlickTable(jsonOptions);
				}
			}


		},appendDatatoSlickTable:function(jsonOptions){
			_slickThisObj = this;
			var gridObj = _slickThisObj.getSlickGridInstance(jsonOptions);
			var dataViewObject = gridObj.getData();
			dataViewObject.beginUpdate();
			var uniqueId = dataViewObject.getItems().length;
			var columnData = jsonOptions.ColumnData;
			for (var i = 0; i < columnData.length; i++) {
				uniqueId++;
				columnData[i]['id']=uniqueId+1;
				dataViewObject.insertItem(0,columnData[i]);
			} 
			dataViewObject.endUpdate();
			gridObj.invalidate();

		},setSlickTable:function(jsonOptions){

			_slickThisObj.slickStructureTable(jsonOptions);

			var columns = []; // columns array for SlickGrid Object
			var columnPicker = []; // columns array for SlickGrid Object

			_slickThisObj.checkBoxColumn(jsonOptions,columns);
			_slickThisObj.removeButtonColumn(jsonOptions,columns);
			_slickThisObj.partialButtonColumn(jsonOptions,columns);
			_slickThisObj.serialNumberColumn(jsonOptions,columns,columnPicker);

			var buttonsObjCol = _slickThisObj.headerButton(jsonOptions);

			var columnHead = jsonOptions.ColumnHead;
			var language = jsonOptions.Language;
			function sorterData(a,b){
				var regex = /^\d{2}[./-]\d{2}[./-]\d{4}$/
					if (regex.test(a[sortcol]) && regex.test(b[sortcol])) {
						var momentA = moment(a[sortcol],"DD/MM/YYYY");
						var momentB = moment(b[sortcol],"DD/MM/YYYY");
						if (momentA > momentB) return sortdir * 1;
						else if (momentA < momentB) return sortdir * -1;
						else return sortdir * 0;
					}
					else {// check if numeric should pass to numeric sorting else String comparison
						var x = a[sortcol], y = b[sortcol];
						if(parseInt(x)>0 ){
							return sorterNumeric(a,b);
						}else{
							return sortdir * (x === y ? 0 : (x > y ? 1 : -1));
						}
					}
			}
			function sorterNumeric(a,b){
				var x = (isNaN(a[sortcol]) || a[sortcol] === "" || a[sortcol] === null) ? -99e+10 : parseFloat(a[sortcol]);
				var y = (isNaN(b[sortcol]) || b[sortcol] === "" || b[sortcol] === null) ? -99e+10 : parseFloat(b[sortcol]);
				return sortdir * (x === y ? 0 : (x > y ? 1 : -1));
			}
			function requiredFieldValidator(value) {
				return {valid: true, msg: 'Should be less then current date'};
			}
			for (var i = 0; i < columnHead.length; i++) {
				if(columnHead[i].inputElement == 'button'){
					columns.push({ id:  columnHead[i].tableDtoName, name:language[columnHead[i].labelid], 
						minWidth: columnHead[i].columnMinWidth,searchFilter:false,listFilter:false,hasTotal:false,buttonCss:columnHead[i].buttonCSS,
						width: columnHead[i].columnWidth,formatter:Slick.Formatters.Button,cssClass:columnHead[i].columnCss,dataType:'button'});
					continue;
				}else  if(columnHead[i].dataType == 'input'){
					columns.push({ 
						id			:  	columnHead[i].tableDtoName,
						name		:	language[columnHead[i].labelid], 
						field		: 	columnHead[i].tableDtoName,
						minWidth	:	columnHead[i].columnMinWidth,
						width		: 	columnHead[i].columnWidth,
						sortable	: 	false,
						hasTotal	:	columnHead[i].showColumnTotal,
						cssClass	:	columnHead[i].columnCss,
						searchFilter:	false,
						listFilter	:	false,
						dataType	:	'text',
						printWidth	:	columnHead[i].columnWidthOnPrintInMM,
						valueType	:	columnHead[i].dataType,
						buttonCss	:	columnHead[i].buttonCSS,
						toolTip		:	language[columnHead[i].labelid],
						slickId		:	jsonOptions.InnerSlickId,
						sorter		:	sorterData,
						maxLength	: 	columnHead[i].maxLength,
						formatter	:	Slick.Formatters.Input,
						editor		: 	Slick.Editors.Input
					});
					
					columnPicker.push({
						id				:  	columnHead[i].tableDtoName,
						name			:	language[columnHead[i].labelid], 
						field			: 	columnHead[i].tableDtoName,
						minWidth		:	columnHead[i].columnMinWidth,
						width			: 	columnHead[i].columnWidth,
						sortable		: 	true,
						hasTotal		:	columnHead[i].showColumnTotal,
						cssClass		:	columnHead[i].columnCss,
						searchFilter	:	true,
						listFilter		:	true,
						dataType		:	'text',
						printWidth		:	columnHead[i].columnWidthOnPrintInMM,
						valueType		:	columnHead[i].dataType,
						toolTip			:	language[columnHead[i].labelid],
						slickId			:	jsonOptions.InnerSlickId,
						sorter			:	sorterData,
						formatter		:	Slick.Formatters.DefaultValues,
						validator		:	requiredFieldValidator,
						editor			:	eval(columnHead[i].Editor),
						header			: {buttons: buttonsObjCol}
					});
					continue;
				}
				if(eval(columnHead[i].Editor) == Slick.Editors.Date){
					var fieldPropertyStr = { 
							id				:  	columnHead[i].tableDtoName,
							name			:	language[columnHead[i].labelid], 
							field			: 	columnHead[i].tableDtoName,
							minWidth		:	columnHead[i].columnMinWidth,
							width			: 	columnHead[i].columnWidth,
							sortable		: 	true,
							hasTotal		:	columnHead[i].showColumnTotal,
							cssClass		:	columnHead[i].columnCss,
							searchFilter	:	true,
							listFilter		:	true,
							dataType		:	'text',
							printWidth		:	columnHead[i].columnWidthOnPrintInMM,
							valueType		:	columnHead[i].dataType,
							toolTip			:	language[columnHead[i].labelid],
							slickId			:	jsonOptions.InnerSlickId,
							sorter			:	sorterData,
							formatter		:	Slick.Formatters.DefaultValues,
							validator		:	requiredFieldValidator,
							editor			:	eval(columnHead[i].Editor),
							header			: {buttons: buttonsObjCol}
					} ;
				}else{

					var fieldPropertyStr = { 
							id				:  	columnHead[i].tableDtoName,
							name			:	language[columnHead[i].labelid], 
							field			: 	columnHead[i].tableDtoName,
							minWidth		:	columnHead[i].columnMinWidth,
							width			: 	columnHead[i].columnWidth,
							sortable		: 	true,
							hasTotal		:	columnHead[i].showColumnTotal,
							cssClass		:	columnHead[i].columnCss,
							searchFilter	:	true,
							listFilter		:	true,
							dataType		:	'text',
							printWidth		:	columnHead[i].columnWidthOnPrintInMM,
							valueType		:	columnHead[i].dataType,
							toolTip			:	language[columnHead[i].labelid],
							slickId			:	jsonOptions.InnerSlickId,
							sorter			:	sorterData,
							formatter		:	Slick.Formatters.DefaultValues,
							header			: {buttons: buttonsObjCol}
					} ;

				}

				if(columnHead[i].labelValue != "" && columnHead[i].labelValue != undefined){
					fieldPropertyStr.name = columnHead[i].labelValue;
				}

				if(columnHead[i].showColumnTotal){
					
					fieldPropertyStr.groupTotalsFormatter = _slickThisObj.sumTotalsFormatter;
				}

				if(typeof searchFilterConfiguration  != "undefined"){
					if(searchFilterConfiguration[columnHead[i].elementConfigKey]=='true'){
						fieldPropertyStr.searchFilter = true;
					}
				}

				if(typeof listFilterConfiguration  != "undefined"){
					if(listFilterConfiguration[columnHead[i].elementConfigKey]=='true'){
						fieldPropertyStr.listFilter = true;
					}
				}

				if(typeof columnHiddenConfiguration != "undefined"){
					if(columnHiddenConfiguration[columnHead[i].elementConfigKey]=='true'){
						columns.push(fieldPropertyStr);
					}
					columnPicker.push(fieldPropertyStr);
				}
				else{
					columns.push(fieldPropertyStr);
					columnPicker.push(fieldPropertyStr);
				}
			}

			var  dataView;
			var groupItemMetadataProvider = new Slick.Data.GroupItemMetadataProvider();
			var columnData = jsonOptions.ColumnData;
			for (var i = 0; i < columnData.length; i++) {
				columnData[i]['id']=parseInt(i)+1;
			} // adding row number to the table
			var old = JSON.stringify(columnData) //convert to JSON string
			columnData = JSON.parse(old);  // for removing null values in the jsonstring from webservice

			dataView = new Slick.Data.DataView({ inlineFilters: true ,groupItemMetadataProvider: groupItemMetadataProvider});
			dataView.setItems(columnData);

			grid = new Slick.Grid("#"+jsonOptions.InnerSlickId, dataView, columns, {
				enableCellNavigation	: 	true,
				multiColumnSort			: 	true,
				enableColumnReorder		: 	jsonOptions.EnableColumnReorder,
				frozenColumn			: 	-1,
				fullWidthRows			:	true,
				rowHeight				: 	30,
				enableAsyncPostRender	: 	true,
				showFooterRow			: 	true,
				autoHeight				:	jsonOptions.NoVerticalScrollBar,
				autoEdit				:	true,
				editable				:	true
			});

			grid.init();
			grid.registerPlugin(groupItemMetadataProvider);
			_slickThisObj.registerSlickGridPlugins(columnPicker,jsonOptions,grid,dataView,jsonOptions.InnerSlickId);

			if (jsonOptions.RemoveSelectAllCheckBox == 'true') {
				$("[title*='Select/Deselect All']").empty();
			}

			return grid;

		},registerSlickGridPlugins:function(columnPicker,jsonOptions,grid,dataView,innerSlickID){
			grid.setSelectionModel(new Slick.CellSelectionModel());

			if(jsonOptions.ShowCheckBox){
				grid.registerPlugin(jsonOptions.CheckboxSelector);
			}

			var totals = {};
			
			var pager = new Slick.Controls.Pager(dataView, grid, $("#pager_"+innerSlickID)); // for pager id
			grid.onFooterRowCellRendered.subscribe(function (e, args) {
				$(args.node).empty();
				if(args.column.hasTotal == true){
					$("<span id='columnTotal_"+innerSlickID+args.column.id+"' data-columnTotal="+innerSlickID+args.column.id+" class='footerTotal'>"+totals[args.column.id]+"</span>")
					.appendTo(args.node);
				}
			});

			grid.onPageChanged.subscribe(function() {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});

			grid.onSelectedRowsChanged.subscribe(function(e, args) {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});
			
			grid.onCellChange.subscribe(function(e, args) {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});
			grid.onBeforeCellEditorDestroy.subscribe(function(e, args) {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});
			grid.onCellCssStylesChanged.subscribe(function(e, args) {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});
			grid.onHeaderClick.subscribe(function(e, args) {
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});

			dataView.onRowCountChanged.subscribe(function (e, args) {
				grid.updateRowCount();
				grid.render();
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});

			dataView.beginUpdate();
			dataView.setFilter(filter);
			dataView.endUpdate();
			
			grid.onSort.subscribe(function (e, args) {
				var cols = args.sortCols;

				dataView.sort(function (dataRow1, dataRow2) {
					for (var i = 0, l = cols.length; i < l; i++) {
						sortdir = cols[i].sortAsc ? 1 : -1;
						sortcol = cols[i].sortCol.field;

						var result = cols[i].sortCol.sorter(dataRow1, dataRow2); // sorter property from column definition comes in play here
						if (result != 0) {
							return result;
						}
					}
					return 0;
				});
				args.grid.invalidateAllRows();
				args.grid.render();
			});
			
			var filterPlugin = new Ext.Plugins.HeaderFilter({});

			// This event is fired when a filter is selected
			filterPlugin.onFilterApplied.subscribe(function () {
				dataView.refresh();
				grid.resetActiveCell();
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
				grid.invalidate();
				// Excel like status bar at the bottom
				var status;

				if (dataView.getLength() === dataView.getItems().length) {
					status = "";
				} else {
					status = dataView.getLength() + ' OF ' + dataView.getItems().length + ' RECORDS FOUND';
				}
				$('#status-label_'+innerSlickID).text(status);
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});

			// Event fired when a menu option is selected
			filterPlugin.onCommand.subscribe(function (e, args) {
				dataView.fastSort(args.column.field, args.command === "sort-asc");
			});

			grid.registerPlugin(filterPlugin);
			
			var overlayPlugin = new Ext.Plugins.Overlays({});

			// Event fires when a range is selected
			overlayPlugin.onFillUpDown.subscribe(function (e, args) {
				var column = grid.getColumns()[args.range.fromCell];

				// Ensure the column is editable
				if (!column.editor) {
					return;
				}

				// Find the initial value
				var value = dataView.getItem(args.range.fromRow)[column.field];

				dataView.beginUpdate();

				// Copy the value down
				for (var i = args.range.fromRow + 1; i <= args.range.toRow; i++) {
					dataView.getItem(i)[column.field] = value;
					grid.invalidateRow(i);
				}

				dataView.endUpdate();
				grid.render();
			});

			grid.registerPlugin(overlayPlugin);
			
			// Filter the data (using userscore's _.contains)
			function filter(item) {
				var columns = grid.getColumns();
				var value = true;

				for (var i = 0; i < columns.length; i++) {
					var col = columns[i];
					var filterValues = col.filterValues;
					if (filterValues && filterValues.length > 0) {
						value = value & _.contains(filterValues, item[col.field]);
					}
				}
				return value;
			}
			
			var headerButtonsPlugin = new Slick.Plugins.HeaderButtons();
			headerButtonsPlugin.onCommand.subscribe(function(e, args) {
				var column = args.column;
				var button = args.button;
				var command = args.command;
				if (command == "toggle-pinToLeft") {
					if(pinCounter < 2){ // allow max three columns to be pinned

						pinCounter++;

						button.cssClass = "glyphicon glyphicon-pushpin";
						button.tooltip = "unpin";
						button.command='toggle-unPinToLeft';
						//column.cssClass="setBorderRight";
						var columns = grid.getColumns();
						var index = grid.getColumnIndex(column.id)
						for(var i = index ; i > pinCounter;i--){
							columns[i] = columns[i-1];
						}
						columns[pinCounter]=column;
						for(var i =0; i < pinCounter; i++){
							columns[i].cssClass="";
						}
						grid.setOptions({ 'frozenColumn':pinCounter });
						grid.setColumns(columns);
					}
					else{
						showMessage('warning','You cannot pin more than 3 columns ');
					}
				};
				if (command == "toggle-unPinToLeft") {
					button.cssClass = "fa fa-thumb-tack";
					button.tooltip = "pin to Left";
					button.command='toggle-pinToLeft';
					//column.cssClass="";
					var columns = grid.getColumns();
					var index = grid.getColumnIndex(column.id)
					for(var i = index ; i < pinCounter;i++){
						columns[i] = columns[i+1];
					}
					columns[pinCounter]=column;

					pinCounter--;
					/*if(pinCounter>= 0)
				{columns[pinCounter].cssClass="setBorderRight";}
				else{columns[0].cssClass="";}*/
					grid.setOptions({ 'frozenColumn':pinCounter })
					grid.setColumns(columns);
				};
			});
			grid.registerPlugin(headerButtonsPlugin);
			
			grid.onClick.subscribe(function (e,args) {
				var gridObj = args.grid;
				var dataView = gridObj.getData();
				var cell = gridObj.getCellFromEvent(e);
				if (gridObj.getColumns()[cell.cell].editor == Slick.Editors.Input) {
					this.editActiveCell();
				}
				var cell = gridObj.getCellFromEvent(e);
				if (gridObj.getColumns()[cell.cell].id == "removeButton") {
					dataView.deleteItem(dataView.getItem(args.row).id);
					e.stopPropagation();
					gridObj.invalidate();

				}
				if (gridObj.getColumns()[cell.cell].id == "PartialButton") {
					showLayer();
					var functionName = jsonOptions.CallBackFunctionForPartial;
					functionName(gridObj,dataView,args.row);
					e.stopPropagation();
					_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
				}

				var columnHead = jsonOptions.ColumnHead;

				for (var i = 0; i < columnHead.length; i++) {
					if(gridObj.getColumns()[cell.cell].id == columnHead[i].tableDtoName  && (columnHead[i].inputElement == 'button' || columnHead[i].inputElement == 'link')) {
						var functionname = columnHead[i].buttonCallback + "";
						window[functionname + ""](gridObj, dataView, args.row);
						e.stopPropagation();
						_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
					}
				}
			});
			
			grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow: false}));

			dataView.refresh();	  

			// Animate loader off screen

			_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);

			//grid.registerPlugin( new Slick.AutoColumnSize());
      
			dataView.syncGridSelection(grid, true);
			
			var printPlugin = new Slick.Plugins.Print();
			grid.registerPlugin(printPlugin);
			$('#btnprint_'+innerSlickID).on('click', function () {
				var strWindowFeatures = "menubar=yes,location=yes,resizable=yes,scrollbars=yes,status=yes";
				printPlugin.printToWindow(window.open('/ivcargo/resources/js/slickgrid/plugins/print-grid.html', 'print_window', strWindowFeatures));
			});

			dataView.onRowsChanged.subscribe(function (e, args) {
				grid.updateRowCount();
				grid.invalidateRows(args.rows);
				grid.render();
				_slickThisObj.updateColumnTotal(columnPicker,dataView,totals,innerSlickID);
			});
		},serialNumberColumn:function(jsonOptions,columns,columnPicker){
			_slickThisObj = this;
			buttonsObj = _slickThisObj.headerButton(jsonOptions);
			// for adding Sr No column to Table
			var idFieldPropertyStr = { id:  'id',  field:'id', name:jsonOptions.Language['serialnumberheader'], width: 50 ,sortable: false,
					formatter: Slick.Formatters.SerialNumber,cssClass:'column-data-left-align',
					searchFilter:false,listFilter:false, dataType:'text',hasTotal:false,printWidth:10,
					toolTip:jsonOptions.Language['serialnumberheader'],
					header: {
						buttons: buttonsObj
					}
			}
			if(typeof jsonOptions.SerialNo != "undefined"){
				if(jsonOptions.SerialNo[0].SearchFilter != "undefined"){
					if(jsonOptions.SerialNo[0].SearchFilter){
						idFieldPropertyStr.searchFilter = true;
					}
				}
				if(jsonOptions.SerialNo[0].ListFilter != "undefined"){
					if(jsonOptions.SerialNo[0].ListFilter){
						idFieldPropertyStr.listFilter = true;
					}
				}
				if(jsonOptions.SerialNo[0].showSerialNo != "undefined"){
					if(jsonOptions.SerialNo[0].showSerialNo){
						columns.push(idFieldPropertyStr);
						columnPicker.push(idFieldPropertyStr);
					}
				}
			}
		},partialButtonColumn:function(jsonOptions,columns){
			var language = jsonOptions.Language;
			if(jsonOptions.ShowPartialButton){
				columns.push({ id:'PartialButton',  field:'PartialButton', name:language['partialheader'], 
					width: 80 ,searchFilter:false,listFilter:false,hasTotal:false,buttonCss:'btn btn-primary btn-xs',
					formatter:Slick.Formatters.Button,cssClass:'column-data-left-align',dataType:'button',
				});
			}
		},removeButtonColumn:function(jsonOptions,columns){
			var language = jsonOptions.Language;
			if(jsonOptions.ShowDeleteButton){
				columns.push({id : 'removeButton',field :'removeButton',name :language['removeheader'], 
					width :80, searchFilter	:	false, listFilter :	false, hasTotal	:false,
					buttonCss :	'btn btn-danger btn-xs',formatter :	Slick.Formatters.Button,
					cssClass :	'column-data-left-align', dataType :'button'
				});
			}

		},checkBoxColumn:function(jsonOptions,columns){
			if(jsonOptions.ShowCheckBox){
				var checkboxSelector = new Slick.CheckboxSelectColumn({
					cssClass: "slick-cell-checkboxsel column-data-left-align"
				});
				jsonOptions.CheckboxSelector = checkboxSelector;
				columns.push(checkboxSelector.getColumnDefinition());
			}
		},headerButton:function(jsonOptions){
			var buttonsObj = [];
			if(jsonOptions.ShowSorting){
				/*buttonsObj.push( {
					cssClass: "fa fa-lock",
					command: "toggle-sortable",
					tooltip: "sorting locked"
				});*/
			}

			if(jsonOptions.ShowPinToLeft){
				buttonsObj.push({
					cssClass: "fa fa-thumb-tack",
					command: "toggle-pinToLeft",
					tooltip: "pin to Left"
				});
			}
			return buttonsObj;
		},slickStructureTable:function(jsonOptions){
			var $status			= $("<div id='status_"+jsonOptions.InnerSlickId+"'><label id='status-label_"+jsonOptions.InnerSlickId+"'></label></div>");
			var $print 			= $("<div id='print_"+jsonOptions.InnerSlickId+"'><button id='btnprint_"+jsonOptions.InnerSlickId+"' class='btn btn-primary'><i class='glyphicon glyphicon-print'></i> Print</button></div>");
			var $excel 			= $("<div id='excel_"+jsonOptions.InnerSlickId+"'style='padding-left: 5px;'><button id='btnexcel_"+jsonOptions.InnerSlickId+"' class='btn btn-primary'><i class='glyphicon glyphicon-download-alt'></i> Download Excel</button></div>");
			var $buttonDiv		= $("<div id='buttonDiv_"+jsonOptions.InnerSlickId+"' style='display: flex;'>");
			var $buttonCloseDiv	= $("</div>");
			var $mySlickGrid 	= $("<div id='"+jsonOptions.InnerSlickId+"' style='width: 100%;height:"+jsonOptions.InnerSlickHeight+";'></div>");


			if(jsonOptions.ShowPager){
				$("#"+jsonOptions.DivId).append($("<div id='pager_"+jsonOptions.InnerSlickId+"' style='width: 100%; height: "+jsonOptions.PagerHeight+"'/></div>"));
			}
			if(document.getElementById(jsonOptions.InnerSlickId) == null){

				$("#"+jsonOptions.DivId).append($buttonDiv);
				if(jsonOptions.ShowPrintButton){
					if(document.getElementById("btnprint_"+jsonOptions.InnerSlickId) == undefined){
						$("#buttonDiv_"+jsonOptions.InnerSlickId).append($print);
					}
				}
				if(jsonOptions.ShowExportExcelButton){
					if(document.getElementById("btnexcel_"+jsonOptions.InnerSlickId) == undefined){
						$("#buttonDiv_"+jsonOptions.InnerSlickId).append($excel);
					}
				}
				$("#"+jsonOptions.DivId).append($buttonCloseDiv);
				$("#"+jsonOptions.DivId).append($mySlickGrid);
				$("#"+jsonOptions.DivId).append($status);
			}
		},sumTotalsFormatter:function(totals, columnDef) {
			
			var val = totals.sum && totals.sum[columnDef.field];
			if (val != null) {
				return "<b> " + ((parseFloat(val)*100)/100)+"</b>";
			}
			return "";
		},getValueForSelectedData:function(jsonOptions, selectionMsg) {
			_slickThisObj = this;
			var griDobj = _slickThisObj.getSlickGridInstance(jsonOptions);
			var selectedData = [],
			selectedIndexes;
			selectedIndexes = griDobj.getSelectedRows();

			if(selectedIndexes == '' || selectedIndexes == null) {
				if(typeof selectionMsg == 'undefined') {
					selectionMsg	= ' Please, Select atleast one checkbox !'
				}
				
				showMessage('error', '<i class="fa fa-times-circle"></i>' + selectionMsg);
				return;
			}

			jQuery.each(selectedIndexes, function (index, value) {
				var data = griDobj.getData().getItem(value);
				if(typeof data != 'undefined'){
					selectedData.push(data);
				}
			});

			return selectedData;

		},updateColumnTotal:function (columnPicker,dataView,totals,innerSlickID){
			var columnsUpdate = columnPicker;
			var columnIdx = columnsUpdate.length;
			while (columnIdx--) {
				var column = columnsUpdate[columnIdx];
				if (!column.hasTotal) {
					continue;
				}
				var total = 0;
				var l = dataView.getLength() ;
				var array = [];
				for(var i = 0; i < l;i++){
					array.push(dataView.getItem(i)[column.field]);
				}

				function sum (array) {
					var total = 0;
					var i = array.length; 

					while (i--) {
						if(!isNaN(parseFloat(array[i]))){
							total += parseFloat(array[i]);
						}
					}

					return Math.round(total);
				}
				var total = sum(array);
				if(isNaN(total)){
					total = 0;
				}
				totals[column.id] =total;

				$('#columnTotal_'+innerSlickID+column.id).html(total);
				$('*[data-columnTotal='+innerSlickID+column.id+']').html(total);
				_slickThisObj.updatePaidAndTopayAmount(dataView,innerSlickID);
				$('*[data-columnTotal='+innerSlickID+'totalNumberofRows]').html(dataView.getLength());

			}  
		},updatePaidAndTopayAmount:function(dataView,innerSlickID){
			var l = dataView.getLength();
			var totalTopay 	= 0;
			var totalPaid 	= 0;
			var totalTbb 	= 0;
			var partialLR	= 0;
			for(var i = 0; i < l;i++){
				if(dataView.getItem(i)['wayBillTypeId'] == 2){
					totalTopay += parseInt(dataView.getItem(i)['bookingTotal']);
				}else if(dataView.getItem(i)['wayBillTypeId'] == 1){
					totalPaid += parseInt(dataView.getItem(i)['bookingTotal']);
				} else if(dataView.getItem(i)['wayBillTypeId'] == 4) {
					totalTbb += parseInt(dataView.getItem(i)['bookingTotal']);
				}
				if(dataView.getItem(i)['partial']){
					partialLR = partialLR + 1;
				}
			}
			if(isNaN(totalTopay)){
				totalTopay = 0;
			}
			if(isNaN(totalPaid)){
				totalPaid = 0;
			}
			if(isNaN(totalTbb)){
				totalTbb = 0;
			}

			$('*[data-columnTotal='+innerSlickID+'summarytotalPaidAmount]').html(totalPaid);
			$('*[data-columnTotal='+innerSlickID+'summarytotalToPayAmount]').html(totalTopay);
			$('*[data-columnTotal='+innerSlickID+'summarytotalTbbAmount]').html(totalTbb);
			$('*[data-columnTotal='+innerSlickID+'summarytotalAmount]').html(totalTopay+totalPaid+totalTbb);
			$('*[data-columnTotal='+innerSlickID+'summaryPartial]').html(partialLR);
		},updateRowColor:function(jsonOptions,dtoName,equateVal,cssClass){
			_slickThisObj = this;
			var slickgrid = _slickThisObj.getSlickGridInstance(jsonOptions);
			var indexArr = new Array();
			if(typeof slickgrid !== "undefined"){
				slickgrid.getData().getItemMetadata = function(index){
					var itemValue = slickgrid.getData().getItem(index);
					indexArr.push(index);
					if(typeof itemValue != 'undefined' && itemValue[dtoName] === equateVal && typeof itemValue[dtoName] != 'undefined' ) {
						return { cssClasses: cssClass };
					}
				};
				slickgrid.invalidate(indexArr);
			}
		},checkToAddRowInTable:function(jsonInOptions,columnData,checkDtoName){
			_slickThisObj = this;
			var gridObj = _slickThisObj.getSlickGridInstance(jsonInOptions);
			if(typeof gridObj == "undefined"){
				return false;
			}
			var originalCollection 	= gridObj.getData().getItems();
			var newCollection  		= columnData;
			var equals = false;
			jQuery.each(originalCollection, function (originalIndex, originalValue) {
				jQuery.each(newCollection, function (newIndex, newValue) {
					if(originalValue[checkDtoName] == newValue[checkDtoName]){
						equals = true;
						return equals;
					}
				});
				if(equals){
					return equals;
				}
			});
			return equals;
		},getSlickGridInstance:function(jsonInOptions){
			return slickGridInstanceGlobalObj[jsonInOptions.InnerSlickId];
		},setAggregateFunction:function(grid,column){
			var dataViewObject = grid.getData();
			var columnsArr = new Array();
			var columns = grid.getColumns();
			columns.forEach(function (col) {
				if(col.hasTotal){
					columnsArr.push(new Slick.Data.Aggregators.Sum(col.field));
				}
			});

			dataViewObject.setGrouping({
				getter: column,
				formatter: function (g) {
					return  g.value + "  <span style='color:green'>(" + g.count + " rows)</span>";
				},
				aggregators: columnsArr,
				aggregateCollapsed: false,
				lazyTotalsCalculation: true,
				comparer: function (a, b) {
					var x = a['value'], y = b['value'];
					return 1 * (x === y ? 0 : (x > y ? 1 : -1));
				    },
			});
		}
	} ;
} );