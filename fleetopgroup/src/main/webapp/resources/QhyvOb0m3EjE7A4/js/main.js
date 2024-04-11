
var jQuery = window.jQuery,
    // check for old versions of jQuery
    oldjQuery = jQuery && !!jQuery.fn.jquery.match(/^1\.[0-4](\.|$)/),
    localJqueryPath = 'resources/QhyvOb0m3EjE7A4/js/jquery/jquery-2.1.4.min',
    paths = {},
    noConflict;

// check for jQuery 
if (!jQuery || oldjQuery) {
    // load if it's not available or doesn't meet min standards
    paths.jquery = localJqueryPath;
    noConflict = !!oldjQuery;
} else {
    // register the current jQuery
    define('jquery', [], function() { return jQuery; });
}


require.config( {
	waitSeconds: 200,
	paths: {
		jquery						:'jquery/jquery.min',
		JsonUtility 				: 'json/jsonutility',
		JsonUtilityConstant 		: 'json/jsonutilityconstant',
		// SlickGrid related imports for working with large dataset 
		dragEvent					:'slickgrid/lib/jquery.event.drag-2.2',
		slickCore					:'slickgrid/slick.core',
		slickGrid					:'slickgrid/slick.grid',
		slickDataView				:'slickgrid/slick.dataview',
		slickPager 					:'slickgrid/controls/slick.pager',
		slickGridGroupMetaData		:'slickgrid/slick.groupitemmetadataprovider',
		slickColumnPicker 			:'slickgrid/controls/slick.columnpicker',
		jqueryUiSortable 			:'slickgrid/jquery_dependant/jquery.ui.sortable',
		bootPaging  	  			:'jquery/jquery.bootpag.min',
		// SlickGrid related imports for working with large dataset
		slickCellRangeSelector 		:'slickgrid/plugins/slick.cellrangeselector',
		slickCellSelectionModel 	:'slickgrid/plugins/slick.cellselectionmodel',
		slickCellRangeDecorator 	:'slickgrid/plugins/slick.cellrangedecorator',
		extHeaderFilter 			:'slickgrid/ext.headerfilter',
		extOverlays 				:'slickgrid/ext.overlays',
		slickHeaderButtons 			:'slickgrid/plugins/slick.headerbuttons',
		slickGridPrint 				:'slickgrid/plugins/slickgrid-print-plugin',
		slickGridExcel 				:'slickgrid/plugins/slickgrid-excel-plugin',
		slickGridRowSelectionModel	:'slickgrid/plugins/slick.rowselectionmodel',
		slickGridCheckBox			:'slickgrid/plugins/slick.checkboxselectcolumn',
		slickGridFormatter			:'slickgrid/slick.formatters',
		slickGridEditor				:'slickgrid/slick.editors',
		slickGridTotalsPlugin		:'slickgrid/TotalsPlugin',
		slickGridGetscrollbarwidth	:'slickgrid/jquery_dependant/jquery.getscrollbarwidth',
		// the wrapper class for using slickgrid functionality
		slickGridWrapper			:'slickgrid/slickgridwrapper',
		slickGridWrapper2			:'slickgrid/slickgridwrapper2',
		slickGridWrapper3			:'slickgrid/slickgridwrapper3',
		moment						:'datepicker/moment.min',
		bootstrapSwitch				: 'bootstrap-switch',
	
	},
	shim: {
		dragevent		:   	 ['jquery'],
		slickGrid		:  		 ['slickCore', 'dragEvent','slickDataView','slickPager','slickGridGroupMetaData',
		         		   		  'slickColumnPicker','slickCellRangeSelector','slickCellSelectionModel',
		         		   		  'slickCellRangeDecorator','extHeaderFilter','extOverlays','slickHeaderButtons',
		         		   		  ,'bootstrapSwitch','JsonUtility','bootPaging','slickGridPrint','slickGridExcel','slickGridCheckBox','slickGridRowSelectionModel'
		         		   		  ,'slickGridFormatter','jqueryUiSortable','slickGridEditor','slickGridTotalsPlugin','slickGridGetscrollbarwidth']
	},
	baseURL: 'resources/QhyvOb0m3EjE7A4/js'
});
//baseurl defines the js which starts from specific directory
