<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.grid.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.pager.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/smoothness/jquery-ui-1.11.3.custom.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/examples.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/slickgrid/slick.columnpicker.css"/>">
	
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/TSR.in"/>">Tripsheet Report</a>  / <span>TripSheet Loading Sheet Details Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm hide"
						 id="printBtn">
						<span class="fa fa-print"> Print</span>
					</button>
								
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_FUEL')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VE_FU_MI_REPORT')">
						<div class="panel box box-danger">
							<div class="box-header with-border">
								<h4 class="box-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapseTwo"> TripSheet Loading Sheet Details Report </a>
								</h4>
							</div>
							<!-- <div id="collapseTwo" class="panel-collapse collapse"> -->
								<div class="box-body">
										<div class="form-horizontal ">
											<!-- vehicle Select -->
											
										<div class="row1">
											<label class="L-size control-label">Vehicle Group : <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="hidden" id="vehicleGrpId7"
													name="vehicleGrpId7" style="width: 100%;"
													required="required" placeholder="Please Select Group"
													value="0" />
												<p class="help-block">Select One Vehicle Group</p>
											</div>
										</div>
											<div class="row1">
												<label class="L-size control-label">Vehicle Name <abbr
													title="required">*</abbr>
												</label>
												<div class="I-size">
													
														<select style="width: 100%;" id="Vehicleofdepot7"
														 name="fuelmileage_vid"  >
														</select> <span id="tyreModelIcon" class="">
														
												</div>
											</div>

											<!-- Date range -->
											<div class="row1">
												<label class="L-size control-label">Date range: <abbr
													title="required">*</abbr></label>
												<div class="I-size">
													<div class="input-group">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" id="dispatchDateRange" class="form-text"
															name="fuelmileage_daterange" required="required"
															style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
													</div>
												</div>
											</div>

											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>

													<div class="I-size">
														<div class="pull-left">
															 <button type="submit"  name="commit" id="validatedata" 
																class="btn btn-success">
																<i class="fa fa-search"> Search</i>
															</button> 
															
															<!-- <input type='button' value='Search' name="commit" class="btn btn-success" > 
															<i class="fa fa-search"> </i>
															</input> -->
														</div>
													</div>
												</div>
											</fieldset>

										</div>
								</div>
						<!-- 	</div> -->
						</div>
					</sec:authorize>
	<section class="content" id="ResultContent" style="display: none;">
			<div class="box-body">
			<div id="div_print">

				<div id="div_print">
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-xs-12">
								<div class="table-responsive">
									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">
									<table class="table table-hover table-bordered table-striped" id="companyTable" style="display: none;">
										<tbody id="companyHeader">
											
										</tbody>
									</table>
										
										<div class="row invoice-info" id="reportHeader" style="font-size: 15px;font-weight: bold;">
											
										</div>
										<div id="selectedData" style="text-align: left;">
													<table>
															<tr>
																<td style="display: none; font-weight: bold;" id="companyName"> </td>
															</tr>
														<tbody id="selectedReportDetails">
														</tbody>
													</table>
											</div>
											<br/>
											<br/>
										<div class="row invoice-info">
											<table width="100%">
												  <tr>
												    <td valign="top" width="100%">
												    
														<div id="gridContainer">
														      <div id="myGrid"></div>
														      <div id="pager" style="width:100%;height:20px;"></div>
														      
														 </div>     
												    </td>
												   
												  </tr>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						</div>
					</section>
					
		<div class="modal fade" id="loadingSheetDetailsModal" role="dialog">
			<div id="lDetails" class="modal-dialog modal-lg" style="width:1000px;">
				<div class="modal-content">
					<div class="modal-header">
					<button type="button" class="close btn btn-danger" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Dispatch Details</h4>
					</div>
					<div class="modal-body">
					
					<fieldset>
					<div class="box">
						<!-- <table border="1" width="100%">
							 <tr>
									<td valign="top" width="100%">
											<div id="dispatchContainer">
												 <div id="dispatchPopup"></div>
												 <div id="pager2" style="width:100%;height:20px;"></div>
														      
											</div>     
									</td>
							</tr>
						</table> -->
						<table border="1" width="100%" class="table">
						<thead>
							 <tr>
							 	<th>LS Number</th>
							 	<th>LS Date</th>
							 	<th>LR Number</th>
							 	<th>Freight</th>
							 	<th>Booking Total</th>
							</tr>
						</thead>
						<tbody id="dispatchTableBody">
						</tbody>
							
						</table>
					</div>
					</fieldset>
					<br />
					</div>
					<div class="modal-footer">
					<button type="button" class="btn btn-default"
					data-dismiss="modal">
					<span id="Close">Close</span>
					</button>
					</div>
					</div>
					</div>
			</div>

	</section>
	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery-ui-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/lib/jquery.event.drag-2.3.0.js"></script>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
	
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
	<script type="text/javascript" 
		src="resources/QhyvOb0m3EjE7A4/js/fleetop/jspdf/jspdf.min.js"></script>	
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.core.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.formatters.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.editors.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangedecorator.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellrangeselector.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slick.cellselectionmodel.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.grid.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.groupitemmetadataprovider.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/plugins/slickgrid-print-plugin.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slick.dataview.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.pager.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/controls/slick.columnpicker.js"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/slickgrid/slickgridwrapper2.js"></script>		
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TSR/getTripSheetWwithoutLoadingsheet.js"/>"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
</div>