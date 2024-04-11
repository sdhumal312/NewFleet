<%@ include file="taglib.jsp"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
<style>
.box-body .affix {
	border-radius: 3px;
	background: #FFF;
	margin-bottom: 5px;
	padding: 5px;
}
.Headerbox
{
	padding:5px;
	background-color:#7DCEA0 ;
	border-radius:2.5px;
	width:32%;
	margin-top:0.5%;
	margin-bottom: 1.5%;
	color:Black;
	font-size: 20px;
}
.box-body
{
width:100%;
}
</style>

<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/WOR.in"/>">WorkOrder Report</a> /<span>Latest Vehicle Wise WorkOrder Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('WorkOrderTable', 'Vehicle Wise WorkOrder Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>					
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	
			<sec:authorize access="hasAuthority('VIEW_VE_WO_REPORT')">
					<div class="panel box box-primary">
						<div class="box-header with-border">
							<h4 class="box-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#WorkOrderTwo"> Vehicle Wise WorkOrder Report </a>
							</h4>
						</div>
							<div class="box-body">
									<div class="form-horizontal ">
									
										<div class="row1">
											<label class="L-size control-label">Group:
											</label>
											<div class="I-size">
												<input type="hidden" id="workOrderGroup"
													name="GROUP_ID" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Vehicle Group</p>
											</div>
										</div>
										
									    <div class="row1">
				                            <label class="L-size control-label">Vehicle :</label>
				                              <div class="I-size">
				                                
				                              	  <div class="input-group">
					                               <span class="input-group-addon">
					                              	 <i class="fa fa-bus"></i>
					                               </span>
					                              	 <input type="hidden" id="SelectVehicle" name="VEHICLE_ID" style="width: 100%;" 
					                              	 	placeholder="select one or more vehicles" />
					                               </div>
				                               </div>
				                            
					                     </div>
										
										<div class="row1">
											<label class="L-size control-label">Date range: <abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<div class="input-group">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" id="VehWorkOrder" class="form-text"
														name="WORKORDER_DATERANGE" required="required"
														style="cursor: pointer; padding: 4px 1px; border: 1px solid #ccc; width: 100%">
												</div>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label" id="Type">Vehicle Type</label>
											<div class="I-size">

												<input type="hidden" id="SelectVehicleType"
													name="VEHICLE_TYPE_ID" style="width: 100%;"
													placeholder="Please Enter 2 or more Type Name" />
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Work Order Type <abbr
												title="required">*</abbr>
											</label>
											<div class="I-size">
												<select class="form-text" name="WORKORDER_ID" id="workOrderType" required="required">
													<option value="0">All</option>
													<option value="1">Issue Regarding WO</option>
													<option value="2">Issue Regarding DSE</option>
													<option value="3">Service Reminder Regarding WO</option>
													<option value="4">Service Reminder Regarding DSE</option>
													
												</select>
											</div>
										</div>
										
										<div class="row1">
											<label class="L-size control-label">Work Location:
											</label>
											<div class="I-size">
												<input type="hidden" id="workOrderLocation2"
													name="LOCATION_ID" style="width: 100%;"
													required="required"
													placeholder="Please Enter 2 or more Vehicle Name" />
												<p class="help-block">Select One Work Location</p>
											</div>
										</div>
										
										<fieldset class="form-actions">
											<div class="row1">
												<label class="L-size control-label"></label>

												<div class="I-size">
													<div class="pull-left">
														<button type="submit" name="commit" id="submit"
															class="btn btn-success">
															<i class="fa fa-search"> Search</i>
														</button>
													</div>
												</div>
											</div>
									   </fieldset>
								</div>
						 </div>
					</div>
				</sec:authorize>
			
			<div id="div_print">
				<section class="content hide" id="ResultContent">
						<div class="box-body" style="background-color:white;">
								<div id="sorttable-div" align="center" style="font-size: 10px"
									class="table-responsive ">
									
								<div class="Headerbox" id="reportHeader"></div>	
									<section class="content">
											<div class="row invoice-info">
												<table id="WorkOrderTable" style="width: 100%;" class="table-hover table-bordered">
												</table>
											</div>
									</section>	
								</div>
						</div>
			   </section>
			</div>			
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>
<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/Report.js"/>"></script>
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
	
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/getVehicleWiseWorkOrderReportDetails.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	</script>
