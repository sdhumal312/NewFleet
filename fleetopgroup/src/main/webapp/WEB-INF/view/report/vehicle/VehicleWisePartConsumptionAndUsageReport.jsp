
<%@ include file="taglib.jsp"%>
<script type="text/javascript">	 
$(document).ready(function() {
	$("#renPT_option1").change(function(){
		if($(this).val() == 2){
			$("#warehouselocationdiv").addClass('hide');
		}
		
	})
	
	$("#submitButton").click(function(){
	 if(Number($('#workOrderGroup1').val()) <= 0){
			  showMessage('info','Please Select Group!');
			  return false;
		  }
		return true;
	})
	
});



function validateReport()
{
	$(".invoice").addClass("hide");	
	showMessage('errors','no records found');
		return false;
}  


</script>

<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css" />">
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
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Vehicle
						Wise Part Consumption And Usage Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vehicle Wise Part Consumption And Usage Report')">
						<span class="fa fa-file-excel-o"> Export to Excel</span>
					</button>
					<a href="<c:url value="/Report"/>">Cancel</a>

				</div>
			</div>
		</div>
	</section>
	<section class="content">
	
		<sec:authorize access="!hasAuthority('VIEW_VEHICLE')">
			<spring:message code="message.unauth"></spring:message>							
		</sec:authorize>
		<sec:authorize access="hasAuthority('VEHICLE_WISE_PART_CONSUMTION_REPORT')">
						<div class="tab-pane active" id="vehicleReport">
							<div class="panel box box-primary">
								<div class="box-header with-border">
									<h4 class="box-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapseOneTwo"> Vehicle Wise Part Consumption/Usage Report </a>
									</h4>
								</div>
								<!-- <div id="collapseOneTwo" class="panel-collapse collapse"> -->
									<div class="box-body">
										<form action="VehicleWisePartConsumptionAndUsageReport" method="post">
											<div class="form-horizontal ">
												<!-- vehicle Select -->
												<div class="row1">
													<label class="L-size control-label">Vehicle Name <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="ReportSelectVehicle1"
															name="repair_vid" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Or More Vehicle</p>
													</div>
												</div>
												<!-- service Type -->
												<div class="row1">
													<label class="L-size control-label">Service Type <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text select2" onchange="hideLocation();"
															name="repair_servicetype" id="renPT_option1"
															required="required">
															<option value="1">Work Orders</option>
															<option value="2">Service Entries</option>
															
															
														</select>
													</div>
												</div>
												
												<div class="row1" id="wloc">
													<label class="L-size control-label">Warehouse
												location : 
													</label>
													<div class="I-size">
														<input type="hidden" id="Partwarehouselocation2"
															name="locationId" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Location Name" />
														<p class="help-block">ALL</p>
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
															<input type="text" id="reportrange" class="form-text"
																name="repair_daterange" required="required"
																style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
														</div>
													</div>
												</div>
												<fieldset class="form-actions">
													<div class="row1">
														<label class="L-size control-label"></label>

														<div class="I-size">
															<div class="pull-left">
																<button type="submit" name="commit" 
																id="submitButton1"
																	class="btn btn-success" onclick="return validateVehicleWisePartConsumptionAndUsageReport();">
																	<i class="fa fa-search"> Search</i>
																</button>
															</div>
														</div>
													</div>
												</fieldset>
											</div>
										</form>
										<!-- end Repair Report -->
									</div>
								<!-- </div> -->
							</div>
						</div>
					</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div id="div_print">

				<section class="invoice">
					<div class="row invoice-info">
						<div class="col-sm-12 col-md-12 col-xs-12"
							style="padding-right: 80px;">
							<div class="table-responsive">
								<c:if test="${!empty vehicles}">
									<table class="table table-hover table-bordered table-striped">
										<tbody>
											<tr>
												<td><c:choose>
														<c:when test="${company.company_id != null}">
															<img
																src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
																class="img-rounded " alt="Company Logo" width="280"
																height="40" />

														</c:when>
														<c:otherwise>
															<i class="fa fa-globe"></i>
															<c:out value="${company.company_name}" />
														</c:otherwise>
													</c:choose></td>
												<td>Print By: ${company.firstName}_${company.lastName}</td>
											</tr>
											<tr>
												<td colspan="2">Branch :<c:out
														value=" ${company.branch_name}  , " /> Department :<c:out
														value=" ${company.department_name}" />
												</td>
											</tr>
										</tbody>
									</table>

									<div id="sorttable-div" align="center" style="font-size: 10px"
										class="table-responsive ">

										<div class="row invoice-info">
											<table style="width: 95%">
												<tbody>
													<tr>
														<td align="center"><span class="text-bold">
																Vehicle Wise Repair Report <c:out value="${SEARCHDATE}" />
														</span></td>
													</tr>
												</tbody>
											</table>
											<table id="advanceTable" style="width: 95%"
												class="table table-hover table-bordered table-striped">
												<thead>
													<tr>
														<th>SL No</th>
														<th>WO</th>
														<th>OdoMeter</th>
														<th>Start Date</th>
														<th>Due Date</th>
														<th>Part Location</th>
														<!-- <th>Assigned To</th> -->
														<th>Last Occurred On</th>
														<th>Part Details</th>
														<th>Total</th>





													</tr>
												</thead>
												<tbody>
													<%-- <c:if test="${!empty vehicles}"> --%>
													<%
												Integer hitsCount = 1;
												%>

													<c:forEach items="${vehicles}" var="vehicles">

														<!-- vehicle Name Show -->
														<tr>
															<td colspan="9"><a
																style="font-size: 20px; color: black;" target="_blank"
																href="showVehicle.in?vid=${vehicles.vid}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																		value="${vehicles.vehicle_registration}" />

															</a></td>
														</tr>

														<c:if test="${!empty WorkOrder}">
															<c:forEach items="${WorkOrder}" var="WorkOrder">

																<c:if test="${vehicles.vid == WorkOrder.vehicle_vid}">

																	<!--  display workOrder details Below  in one vehicle-->
																	<tr>
																		<td>
																			<%
																			out.println(hitsCount);
																			hitsCount += 1;
																			%>

																		</td>

																		<td><a target="_blank"
																			href="showWorkOrder?woId=${WorkOrder.workorders_id}"
																			data-toggle="tip"
																			data-original-title="Click work Order Info">WO-<c:out
																					value="${WorkOrder.workorders_Number}" /></a></td>
																		<td><c:out value="${WorkOrder.vehicle_Odometer}" /></td>

																		<td><abbr title="" data-original-title="Today"><c:out
																					value="${WorkOrder.start_date}" /></abbr></td>
																		<td><abbr title="" data-original-title="Today"><c:out
																					value="${WorkOrder.due_date}" /></abbr></td>
																		<td><c:out
																				value="${WorkOrder.workorders_location}" /></td>

																		<td><c:if test="${!empty workordertask}">
																				<c:forEach items="${workordertask}"
																					var="workordertask">
																					<c:if
																						test="${workordertask.workorders_id == WorkOrder.workorders_id}">

																						<c:choose>
																							<c:when
																								test="${workordertask.last_occurred_woId != 0}">
																								<samp style="color: red;"> Last occurred
																									on ${workordertask.last_occurred_date} this
																									task on Odometer =
																									${workordertask.last_occurred_odameter}. </samp>
																							</c:when>
																							<c:otherwise>
																								<samp style="color: blue;">Never logged
																									for is task</samp>
																							</c:otherwise>
																						</c:choose>

																					</c:if>
																				</c:forEach>
																			</c:if></td>


																		<td colspan="2">
																			<div class="media">
																				<div class="media-body">
																					<%-- <p>
																						Assigned :
																						<c:out value="${WorkOrder.assignee}" />
																					</p> --%>
																				</div>
																			</div>
																			<table width="100%">

																				<thead>
																					<tr class="breadcrumb">
																						<th width="55%" align="right" class="icon">Part</th>
																						<th width="15%" align="right" class="icon">Quantity</th>
																						<th width="15%" align="right" class="icon">Each</th>
																						<th width="15%" align="right" class="icon">Total</th>
																						<th class="fit"></th>
																					</tr>
																				</thead>
																				<tbody style="border-top: 0px;">
																					<!--  display workOrder Task  details Below  in match with work Order_id-->


																					<c:if test="${!empty workordertask}">


																						<c:forEach items="${workordertask}"
																							var="workordertask">

																							<c:if
																								test="${workordertask.workorders_id == WorkOrder.workorders_id}">

																								<tr width="100%">

																									<!-- <td class="work-orders-by-task-column"
																					colspan="2">
																					
																	
																					<table border="1" width="100%">
																					
																					<tbody style="border-top: 0px;"> -->
																									<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																									<c:if test="${!empty WorkOrderTaskPart}">

																										<c:forEach items="${WorkOrderTaskPart}"
																											var="WorkOrderTaskPart">
																											<c:if
																												test="${WorkOrderTaskPart.workordertaskid == workordertask.workordertaskid}">



																												<tr data-object-id="" class="ng-scope">

																													<td align="left"><c:out
																															value="${WorkOrderTaskPart.partname}" />
																														<c:out
																															value="${WorkOrderTaskPart.partnumber}" /></td>

																													<td align="left" class="icon ar">${WorkOrderTaskPart.quantity}</td>
																													<td align="left" class="icon ar"><i
																														class="fa fa-inr"></i>
																														${WorkOrderTaskPart.parteachcost}</td>
																													<td align="left"><c:out
																															value="${WorkOrderTaskPart.totalcost}" /></td>

																												</tr>
																											</c:if>
																										</c:forEach>
																									</c:if>
																									<!-- </tbody>
																					</table>
																					</td> -->


																								</tr>
																							</c:if>
																						</c:forEach>
																					</c:if>

																				</tbody>
																			</table>
																		</td>

																	</tr>
																	<tr class="workorder_repair_totals">
																		<th class="text-right" colspan="8"><b>Total
																				Amount : </b></th>
																		<td><i class="fa fa-inr" data-toggle="tip"
																			data-original-title="Total_WO_cost"></i>
																			${WorkOrder.totalworkorder_cost}</td>


																	</tr>
																</c:if>
															</c:forEach>
															<!-- close work orders for -->
														</c:if>
														<!-- close work orders if -->

													</c:forEach>
													<!-- close Vehicle for -->

													<!-- close Vehicle If -->
												</tbody>
												<tfoot>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="8"><b> Search
																Total Amount :</b></th>
														<td align="right"><i class="fa fa-inr"
															data-toggle="tip"
															data-original-title="Search_Total_WO_cost"></i>
															${TotalWOAmount}</td>

													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</c:if>
								
								<c:if test="${NotFound}">
									<script>								
											$(".invoice").addClass("hide");
											setTimeout(function() {validateReport();}, 500);
									</script>
								</c:if>	
								
								
							</div>
						</div>
					</div>
				</section>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
		<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateVehicleWisePartConsumptionAndUsageReport.js"/>"></script>
		
</div>