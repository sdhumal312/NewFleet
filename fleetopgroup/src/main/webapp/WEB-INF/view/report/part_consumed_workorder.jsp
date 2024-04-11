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
<script>
 function validateReport()
{
	
	showMessage('errors','no records found');
		return false;
}  
 </script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Depot Wise Part Consumed workorder Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Depot Wise Part Consumed workorder Report')">
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
		
		<sec:authorize
						access="hasAuthority('VIEW_DEPOT_WISE_PARTCONSUMED_REPORT')">
						<div class="tab-pane active" id="PartsConsumedReport">
							<div class="panel box box-primary">
								<div class="box-header with-border">
									<h4 class="box-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#depotWise">Depot wise Parts consumed report </a>
									</h4>
								</div>
								<!-- <div id="depotWise" class="panel-collapse collapse"> -->
									<div class="box-body">
										<form action="PartsConsumedReport" method="post">
											<div class="form-horizontal ">
												<!-- vehicle Select -->
												<!-- <div class="row1">
													<label class="L-size control-label">Group : <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="workOrderGroup"
															name="VEHICLE_GROUP_ID" style="width: 100%;"
															required="required"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Vehicle Group</p>
													</div>
												</div> -->
												<div class="row1">
													<label class="L-size control-label">Group: <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<input type="hidden" id="workOrderGroup1"
															name="WORKORDER_GROUP" style="width: 100%;"
															placeholder="Please Enter 2 or more Vehicle Name" />
														<p class="help-block">Select One Vehicle Group</p>
													</div>
												</div>

												<!-- service Type -->
												<div class="row1">
													<label class="L-size control-label">Service Type <abbr
														title="required">*</abbr>
													</label>
													<div class="I-size">
														<select class="form-text select2"
															name="repair_servicetype" id="renPT_option1"
															required="required">
															<option value="1">Work Orders</option>
															<option value="2">Service Entries</option>
															<option value="3">All</option>
														</select>
													</div>
												</div>
												<div id="warehouselocationdiv" class="row1">
													<label class="L-size control-label">Warehouse
														location : </label>
													<div class="I-size">
														<input type="hidden" name="locationId"
															id="Partwarehouselocation2" style="width: 100%;"
															placeholder="All" />

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
																<button type="submit" name="commit" id="submitButton"
																	class="btn btn-success">
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
							<!-- 	</div> -->
							</div>
						</div>
					</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div id="div_print">
				<c:if test="${!empty vehicles}">
					<section class="invoice">
						<div class="row invoice-info">
							<div class="col-sm-12 col-md-12 col-xs-12"
								style="padding-right: 80px;">
								<div class="table-responsive">
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
														<th>SL NO</th>
														<th>WO</th>

														<th>OdoMeter</th>
														<th>Start Date</th>
														<th>Due Date</th>
														<th>Last Occurred On</th>
														<th>WareHouse Location</th>
														<th>Vehicle No</th>
														<th>Group</th>
														<th>Parts Details</th>
														<th>Total</th>

													</tr>
												</thead>
												<tbody>

													<%
														Integer hitsCount = 1;
													%>

													<c:forEach items="${vehicles}" var="vehicles">

														<!-- vehicle Name Show -->
														<%-- <tr>
															<td colspan="9"><a
																style="font-size: 20px; color: black;" target="_blank"
																href="showVehicle.in?vid=${vehicles.vid}"
																data-toggle="tip"
																data-original-title="Click Vehicle Info"> <c:out
																		value="${vehicles.vehicle_registration}-${vehicles.vehicle_Location} " />
															</a></td>
														</tr> --%>

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
																			</c:if> <%-- <c:if test="${empty vehicle}">
																					<script>
																							//validateReport();	
																							console.log("inside empty")
																								alert("no record found");
																					</script>											
																				</c:if> --%></td>
																		<td><c:out
																				value="${WorkOrder.workorders_location}" /></td>

																		<td><c:out
																				value="${WorkOrder.vehicle_registration}" /></td>
																		<td><c:out value="${vehicles.vehicle_Location}" /></td>
																		<td colspan="4">
																			<div class="media">
																				<div class="media-body">
																					<%-- <p>
																						Assigned :
																						<c:out value="${WorkOrder.assignee}" />
																					</p> --%>
																				</div>
																			</div>
																			<table border="0" width="100%">

																				<tbody style="border-top: 0px;">
																					<!--  display workOrder Task  details Below  in match with work Order_id-->


																					<c:if test="${!empty workordertask}">
																						<c:forEach items="${workordertask}"
																							var="workordertask">

																							<c:if
																								test="${workordertask.workorders_id == WorkOrder.workorders_id}">

																								<trwidth100%>

																									<td class="work-orders-by-task-column"
																										colspan="2">
																										
																										<table border="0" width="100%">
																											<tbody style="border-top: 0px;">
																												<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																												<c:if test="${!empty WorkOrderTaskPart}">
																												<thead>
																													<tr class="breadcrumb">
																														<th width="70%" align="right" class="icon">Part</th> 
																														<th align="right" class="icon">Quantity</th> 
																														<th align="right" class="icon">Each</th>
																														<th align="right" class="icon">Total</th> 
																														<th class="fit"></th>
																													</tr>
																												</thead>
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
																																		<td align="left" class="icon ar"><i class="fa fa-inr"></i>
																																					${WorkOrderTaskPart.parteachcost}</td>
																																<td align="left">
																																<c:out value="${WorkOrderTaskPart.totalcost}"/></td>
																																<%-- <td >
																																<c:out value="${WorkOrderTaskPart.partlocation_name}"/></td> --%>
																																
																																	
																																	
																															</tr>
																														</c:if>
																													</c:forEach>
																												</c:if>
																												
																											</tbody>
																										</table>
																									</td>

																									
																								</tr>
																							</c:if>
																						</c:forEach>
																					</c:if>


																				</tbody>
																			</table>
																		</td>

																	</tr>
																	<tr class="workorder_repair_totals">
																		<th class="text-right" colspan="10"><b>Total
																				Amount :</b></th>

																		<td align="right"><i class="fa fa-inr"
																			data-toggle="tip" data-original-title="Total_WO_cost"></i>
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
															data-toggle="tip" data-original-title="Total_WO_cost"></i>
															${TotalWOAmount}</td>
														<%-- <td><i class="fa fa-inr"></i> ${TotalWOAmount}</td> --%>
													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
					</section>
				</c:if>
				
				<c:if test="${NotFound}">
							<script>								
								$(".invoice").addClass("hide");
								setTimeout(function() {validateReport();}, 500);
							</script>
				</c:if>	
				
			</div>
		</sec:authorize>
	</section>
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

	<script type="text/javascript">
		$(document).ready(function() {
			$("#datemask").inputmask("yyyy-mm-dd", {
				placeholder : "yyyy-mm-dd"
			}), $("[data-mask]").inputmask()
		});
	
		$(document).ready(function() {
			$("#renPT_option1").change(function(){
			if($(this).val() == 2){
			$("#wloc").addClass('hide');
			}
			})
			});
	   
	
		
	
	</script>
	
		
		
</div>