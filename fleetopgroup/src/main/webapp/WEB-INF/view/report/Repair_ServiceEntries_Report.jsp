<%@ include file="taglib.jsp"%>
<style>
.closeAmount th, td {
	text-align: center;
}

.closeRouteAmount td {
	text-align: center;
	font-weight: bold;
	color: blue;
}

.closeGroupAmount td {
	text-align: center;
	font-weight: bold;
}

.actualkm {
	float: center;
}

.columnDaily {
	text-align: center;
}
</style>
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
					<%-- <a href="<c:url value="/open"/>"><spring:message 
					code="label.master.home" />
					</a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> /<span>Vehicle
					Wise Repair Report</span> --%>
					<a href="<c:url value="/open"/>"><spring:message
					code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> /<span>Vehicle
					Wise Repair Service Report</span>
													 
				</div>
				<div class="pull-right">
					
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
					</button>
					<button class="btn btn-default btn-sm"
						onclick="advanceTableToExcel('advanceTable', 'Vehicle Wise Repair Service Report')">
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
	<sec:authorize access="hasAuthority('VIEW_VE_WI_RE_REPORT')">
						<div class="tab-pane active" id="vehicleReport">
							<div class="panel box box-primary">
								
								<!--Do Not Use Only for  Exp Start  -->
								
								<!--Do Not Use Only for Exp End  -->
								
								
								<div>
									<div class="box-body">
										<form action="ALLVehicleRepairReport" method="post">
											<div class="form-horizontal ">
												<!-- vehicle Select -->
												<div class="row1">
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
												</div>
												<div class="row1">
													<label class="L-size control-label">Date range: <abbr
														title="required">*</abbr></label>
													<div class="I-size">
														<div class="input-group">
															<div class="input-group-addon">
																<i class="fa fa-calendar"></i>
															</div>
															<input type="text" id="reportrange" class="form-text"
																name="VEHICLE_DATERANGE" required="required"
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
																	class="btn btn-success" onclick="return validateVehicleWiseRepairServiceReport();">
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
								</div>
							</div>
						</div>
					</sec:authorize>
	
		
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div id="div_print">

				<div id="div_print">
					<c:if test="${!empty ServiceEntries}">
						<section class="invoice">
							<div class="row invoice-info">
								<div class="col-sm-12 col-md-12 col-xs-12"
									style="padding-right: 80px;">
									<div class="table-responsive">
										<table id="advanceTable"
										class="table table-hover table-bordered table-striped">
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
										<div class="row invoice-info">
											<table  class="table table-hover table-bordered table-striped">
												<caption>Vehicle Wise Repair Report</caption>

												<thead>
													<tr>
														<th>SE</th>
														<th>Status</th>
														<th>OdoMeter</th>
														<th>Invoice No</th>
														<th>Invoice Date</th>
														<th>Details</th>
														<th>Parts Subtotal</th>
														<th>Labor Subtotal</th>
														<th>Total</th>

													</tr>
												</thead>
												<tbody>
													<c:if test="${!empty vehicles}">
														<c:forEach items="${vehicles}" var="vehicles">

															<!-- vehicle Name Show -->
															<tr>
																<td colspan="9"><a target="_blank"
																	style="font-size: 20px; color: black;"
																	href="showVehicle.in?vid=${vehicles.vid}"
																	data-toggle="tip"
																	data-original-title="Click Vehicle Info"> <c:out
																			value="${vehicles.vehicle_registration}" />
																</a></td>
															</tr>

															<c:if test="${!empty ServiceEntries}">
																<c:forEach items="${ServiceEntries}"
																	var="ServiceEntries">

																	<c:if test="${vehicles.vid == ServiceEntries.vid}">

																		<!--  display workOrder details Below  in one vehicle-->
																		<tr>
																			<td><a target="_blank"
																				href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"
																				data-toggle="tip" data-original-title="Click Info">SE-<c:out
																						value="${ServiceEntries.serviceEntries_Number}" /></a></td>
																			<td><c:choose>
																					<c:when
																						test="${ServiceEntries.serviceEntries_status =='OPEN' }">
																						<span class="no-wrap"> <span
																							class="text-green"><i
																								class="fa fa-circle icon-circle"></i></span> Open
																						</span>
																					</c:when>
																					<c:when
																						test="${ServiceEntries.serviceEntries_status =='INPROCESS' }">
																						<span class="no-wrap"> <span
																							class="text-orange"><i
																								class="fa fa-circle icon-circle"></i></span> In
																							Progress
																						</span>
																					</c:when>

																					<c:otherwise>
																						<span class="no-wrap"> <span
																							class="text-blue"><i
																								class="fa fa-circle icon-circle"></i></span> Completed
																						</span>
																					</c:otherwise>
																				</c:choose></td>
																			<td><c:out
																					value="${ServiceEntries.vehicle_Odometer}" /></td>

																			<td><abbr title="" data-original-title="Today"><c:out
																						value="${ServiceEntries.invoiceNumber}" /></abbr></td>
																			<td><abbr title="" data-original-title="Today"><c:out
																						value="${ServiceEntries.invoiceDate}" /></abbr></td>
																			<td colspan="4">
																				<div class="media">
																					<div class="media-body">
																						<p>
																							Vendor :
																							<c:out value="${ServiceEntries.vendor_name}" />
																						</p>
																					</div>
																				</div>
																				<table>
																					<%
																					Integer hitsCount = 1;
																				%>
																					<tbody style="border-top: 0px;">
																						<!--  display workOrder Task  details Below  in match with work Order_id-->
																						<c:if test="${!empty ServiceEntriesTask}">
																							<c:forEach items="${ServiceEntriesTask}"
																								var="ServiceEntriesTask">

																								<c:if
																									test="${ServiceEntriesTask.serviceEntries_id == ServiceEntries.serviceEntries_id}">

																									<tr>

																										<td class="work-orders-by-task-column"
																											colspan="2">
																											<%
																											out.println(hitsCount);
																																				hitsCount += 1;
																										%>. <c:out
																												value="${ServiceEntriesTask.service_typetask} - " />
																											<c:out
																												value="${ServiceEntriesTask.service_subtypetask}" />
																											<table>
																												<tbody style="border-top: 0px;">
																													<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																													<c:if
																														test="${!empty ServiceEntriesTaskPart}">
																														<c:forEach
																															items="${ServiceEntriesTaskPart}"
																															var="ServiceEntriesTaskPart">
																															<c:if
																																test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																																<tr data-object-id="" class="ng-scope">
																																	<td><c:out
																																			value="${ServiceEntriesTaskPart.partname}" />
																																		<c:out
																																			value="${ServiceEntriesTaskPart.partnumber}" /></td>
																																	<c:out
																																		value="${ServiceEntriesTaskPart.quantity}" />
																																	<!-- </td> -->  <!-- Commented this line for expy -->
																																	<%-- <td class="work-orderPart-by-repir-Cost"><i
																																	class="fa fa-inr"></i>
																																	${ServiceEntriesTaskPart.totalcost}</td> --%>

																																</tr>
																															</c:if>
																														</c:forEach>
																													</c:if>
																												</tbody>
																											</table>
																										</td>

																										<td><i class="fa fa-inr"
																											data-toggle="tip"
																											data-original-title="Part_cost"></i> <c:out
																												value="${ServiceEntriesTask.totalpart_cost}"></c:out></td>
																										<td><i class="fa fa-inr"
																											data-toggle="tip"
																											data-original-title="Labor_cost"></i> <c:out
																												value="${ServiceEntriesTask.totallaber_cost}"></c:out></td>
																										<td><i class="fa fa-inr"
																											data-toggle="tip"
																											data-original-title="Total_Task_cost"></i> <c:out
																												value="${ServiceEntriesTask.totaltask_cost}"></c:out></td>

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
																					Amount :</b></th>

																			<td><i class="fa fa-inr" data-toggle="tip"
																				data-original-title="Total_SE_cost"></i>
																				${ServiceEntries.totalservice_cost}</td>
																		</tr>
																	</c:if>
																</c:forEach>
																<!-- close work orders for -->
															</c:if>
															<!-- close work orders if -->

														</c:forEach>
														<!-- close Vehicle for -->
													</c:if>
													<!-- close Vehicle If -->
												</tbody>
												<tfoot>
													<tr class="workorder_repair_search_totals">
														<th class="text-right" colspan="8"><b> Search
																Total Amount :</b></th>

														<td><i class="fa fa-inr"></i> ${TotalSEAmount}</td>
													</tr>
												</tfoot>
											</table>
										</div>
									</div>
								</div>
							</div>
						</section>
					</c:if>					
					<c:if test="${NotFound}">
							<script>								
								setTimeout(function() {validateReport();}, 500);
							</script>
					</c:if>					
				</div>
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
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/report/ValidateVehicleWiseRepairServiceReport.js"/>"></script>
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

<%-- <script type="text/javascript">
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
 --%>