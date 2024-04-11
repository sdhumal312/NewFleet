<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message
								code="label.master.home" /></a> / <a
							href="<c:url value="/viewWorkOrder.in"/>">OPEN</a> / <a
							href="<c:url value="/WorkOrdersINPROCESS/1.in"/>">IN PROCESS</a>
						/ <a href="<c:url value="/WorkOrdersONHOLD/1.in"/>">ON HOLD</a> /
						<a href="<c:url value="/WorkOrdersCOMPLETE/1.in"/>">COMPLETED</a>
						/ <span id="AllVehicl">Open Work Orders</span>
					</div>
					<div class="col-md-off-5">
						<div class="col-md-3">
							<form action="<c:url value="/searchWorkOrderShow.in"/>"
								method="post">
								<div class="input-group">
									<span class="input-group-addon"> <span
										aria-hidden="true">WO-</span></span> <input class="form-text"
										id="vehicle_registrationNumber" name="Search" type="number"
										min="1" required="required" placeholder="WO-NO eg:6878">
									<span class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
						<a href="<c:url value="/viewWorkOrder.in"/>">Cancel</a>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="box">
			<sec:authorize access="!hasAuthority('VIEW_WORKORDER')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_WORKORDER')">
				<div class="row">
					<!-- get client side validate only  of get make name -->
					<div class="hidden" id='hidden'>
						<!-- get current Selecting part number -->
						<select class="form-control" id="inventory_name">
							<c:forEach items="${Inventory}" var="Inventory">
								<option value="${Inventory.partid}"><c:out
										value="${Inventory.unitprice}" /></option>
							</c:forEach>
						</select> <select class="form-control" id="inventory_maxQuantity">
							<c:forEach items="${Inventory}" var="Inventory">
								<option value="${Inventory.partid}"><c:out
										value="${Inventory.quantity}" /></option>
							</c:forEach>
						</select>
					</div>
					<!-- <script type="text/javascript" src="js/WorkOrderslanguage.js" />"></script> -->
					<div class="col-md-6">
						<h4>
							Work Order ${WorkOrder.workorders_Number } <span
								class="label label-pill label-warning"><c:out
									value="${WorkOrder.priority}" /></span>
						</h4>
						<h4>
							<a href="showVehicle.in?vid=${WorkOrder.vehicle_vid}"
								data-toggle="tip" data-original-title="Click Vehicle Info">
								<c:out value="${WorkOrder.vehicle_registration}" />
							</a>
						</h4>
						<div class="secondary-header-title">
							<ul class="breadcrumb">
								<li><span class="fa fa-user"> Assignee:</span> <a
									data-toggle="tip" data-original-title="Assignee "><c:out
											value="${WorkOrder.assignee}" /></a></li>
								<li><i class="fa fa-bitcoin"> Start date:</i> <a
									data-toggle="tip" data-original-title="Start date"><c:out
											value="${WorkOrder.start_date}" /></a></li>
								<li><span class="fa fa-user"> Due date:</span> <a
									data-toggle="tip" data-original-title="Due date"><c:out
											value="${WorkOrder.due_date}" /></a></li>
								<li><span class="fa fa-user"> Indent | PO No:</span> <a
									data-toggle="tip" data-original-title="Indent | PO No"><c:out
											value="${WorkOrder.indentno}" /></a></li>
								<c:if test="${configuration.TallyCompanyMasterInWO}">
									<li><span class="fa fa-user"> Tally Company Master:</span>
										<a data-toggle="tip" data-original-title="Tally Company Master"><c:out
												value="${WorkOrder.tallyCompanyName}" /></a></li>
								</c:if>

							</ul>
						</div>
					</div>
					<div class="col-md-5">
						<div class="row">
							<input type="hidden" id="statues" name="statues"
								value="${WorkOrder.workorders_status}">
							<div id="work-order-statuses">
								<c:if test="${reOpenWO}">
								<div id="work-order-statuses">
									<sec:authorize access="hasAuthority('REOPEN_WORKORDER')">
										<a data-method="post" data-remote="true"
											href="work_orders_INPROCESS.in?workorders_id=${WorkOrder.workorders_id}"
											rel="nofollow"> <span id="status-in-progress"
											class="status-led"> <i class="fa fa-circle"></i>
												<div class="status-text">RE-OPEN</div>
										</span>
										</a>
									</sec:authorize>
								</div>
								</c:if>

								<div class="row">
									<h4>Closed on ${WorkOrder.completed_date}</h4>
									<sec:authorize access="hasAuthority('DOWNLOAD_WORKORDER')">
										<c:choose>
											<c:when test="${WorkOrder.workorders_document == true}">
												<a class="btn btn-default" style="width: 20%"
													href="${pageContext.request.contextPath}/download/workorderDocument/${WorkOrder.workorders_id}.in">
													<span class="fa fa-download"> Document</span>
												</a>
											</c:when>
										</c:choose>
										<c:choose>
										    <c:when test="${companyWisePrint}">
										    	<input type="button" class="btn btn-default fa fa-print" 
										    		onclick="getCompanyWiseWorkOrderPrint(${WorkOrder.workorders_id});" 
										    		value="Print" />
										    </c:when>    
										    <c:otherwise>
										    	<a style="width: 35%"
												href="PrintWorkOrders?id=${WorkOrder.workorders_id}"
												target="_blank" class="btn btn-default "><i
												class="fa fa-print"></i> Print</a>
										    </c:otherwise>
										</c:choose>
									</sec:authorize>
								</div>
							</div>
						</div>
					</div>
				</div>
				<br>
				<fieldset>
					<div class="row">
						<div class="col-md-11">
							<div class="table-responsive">
								<table class="table">
									<thead>
										<tr class="breadcrumb">
										<c:if test="${configuration.showServRemindWhileCreatingWO}">
											<th>Service Number</th>
										</c:if>
											<th class="fit"></th>
											<th>Task</th>
											<th class="fit ar">Parts Cost</th>
											<th class="fit ar">Labour Cost</th>
											<th class="fit ar">Total</th>
											<!-- <th class="fit">Actions</th> -->
										</tr>
									</thead>
									<tbody style="border-top: 0px;">
										<c:if test="${!empty WorkOrderTask}">
											<c:forEach items="${WorkOrderTask}" var="WorkOrderTask">
												<tr data-object-id="" class="ng-scope">
												<c:if test="${configuration.showServRemindWhileCreatingWO}">		
														<td>
														<c:if test="${WorkOrderTask.service_Number == null}">
															<c:out value="-"> </c:out>
														</c:if>
														<c:if test="${WorkOrderTask.service_Number != null}">
														<a href="ShowService.in?service_id=${WorkOrderTask.service_id}" target="_blank"> <span style="font-size: 12px;"> S-${WorkOrderTask.service_Number} </span> </a>
 													</c:if>	
														</td>
												</c:if>	
													<td class="fit"><c:if
															test="${WorkOrderTask.mark_complete == 1}">
															<h4>
																<i class="fa fa-check" style="color: green"></i>
															</h4>
														</c:if> <c:if test="${WorkOrderTask.mark_complete != 1}">
															<h4>
																<i class="fa fa-wrench" style="color: red"></i>
															</h4>
														</c:if></td>
													<!-- Tast table to assing part value table -->
													<td><h4>
															<c:out value="${WorkOrderTask.job_typetask} - "></c:out>
															<c:out value="${WorkOrderTask.job_subtypetask}"></c:out>
														</h4>
														<c:if test="${showEditJobTypeRemark}">
															<h5>
																<b>Remark : </b><c:out value="${WorkOrderTask.jobTypeRemark}"></c:out>
															</h5>														
														</c:if>
														<div class="row">
															<c:choose>
																<c:when test="${WorkOrderTask.last_occurred_woId != 0}">
																	<samp style="color: red;">
																		Last occurred on ${WorkOrderTask.last_occurred_date}
																		this task on Odometer =
																		${WorkOrderTask.last_occurred_odameter}. <a
																			href="showWorkOrder?woId=${WorkOrderTask.last_occurred_woId}"
																			target="_blank"><i class="fa fa-external-link"></i></a>
																	</samp>
																</c:when>
																<c:otherwise>
																	<samp style="color: blue;">Never logged for is
																		task</samp>
																</c:otherwise>
															</c:choose>
														</div>
														<div class="row">
															<div class="col-md-11">
																<table class="table">
																	<c:if test="${!empty WorkOrderTaskPart}">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="icon ar"  width="50%">Part</th>
																				<th class="icon ar"  width="15%">Qty</th>
																				<th class="icon ar"  width="10%">Each</th>
																				<th class="icon ar"  width="10%">Total</th>
																				<c:if test="${configuration.partsCreatedBy}">
																				<th class="icon ar">Part Added By</th>
																				</c:if>
																			</tr>
																		</thead>
																		<tbody style="border-top: 0px;">

																			<c:forEach items="${WorkOrderTaskPart}"
																				var="WorkOrderTaskPart">
																				<c:if
																					test="${WorkOrderTaskPart.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon"><c:out
																								value="${WorkOrderTaskPart.partname}" /> <c:out
																								value="${WorkOrderTaskPart.partnumber}" /></td>
																						<td class="icon ar">${WorkOrderTaskPart.quantity}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.parteachcost}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskPart.totalcost}</td>
																						<c:if test="${configuration.partsCreatedBy}">
																						<td class="icon ar">${WorkOrderTaskPart.firstName}</td>
																						</c:if>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</c:if>
																	<!-- labor cost add -->
																	<c:if test="${!empty WorkOrderTaskLabor}">
																		<thead>
																			<tr class="breadcrumb">
																				<th class="icon">Technician</th>
																				<th class="icon ar">Hours</th>
																				<th class="icon ar">RateS</th>
																				<th class="icon ar">Dis</th>
																				<th class="icon ar">GST</th>
																				<th class="icon ar">Total</th>
																				<th class="fit"></th>
																			</tr>
																		</thead>
																		<tbody>

																			<c:forEach items="${WorkOrderTaskLabor}"
																				var="WorkOrderTaskLabor">
																				<c:if
																					test="${WorkOrderTaskLabor.workordertaskid == WorkOrderTask.workordertaskid}">
																					<tr data-object-id="" class="ng-scope">
																						<td class="icon"><c:out
																								value="${WorkOrderTaskLabor.labername}" /></td>
																						<td class="icon ar">${WorkOrderTaskLabor.laberhourscost}</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskLabor.eachlabercost}</td>
																						<td class="icon ar">
																							${WorkOrderTaskLabor.laberdiscount} %</td>
																						<td class="icon ar">
																							${WorkOrderTaskLabor.labertax} %</td>
																						<td class="icon ar"><i class="fa fa-inr"></i>
																							${WorkOrderTaskLabor.totalcost}</td>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</tbody>
																	</c:if>
																</table>
															</div>
														</div></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totalpart_cost}"></c:out></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totallaber_cost}"></c:out></td>
													<td class="fit ar"><i class="fa fa-inr"></i> <c:out
															value="${WorkOrderTask.totaltask_cost}"></c:out></td>
												</tr>
											</c:forEach>
										</c:if>
									</tbody>
								</table>
							</div>
						</div>
						<div class="col-md-11">
							<div class="row">
								<div class="col-md-8">
									<table class="table">
										<tbody style="border-top: 0px;">
											<tr class="row">
												<th class="key">Driver Name :</th>
												<td class="value"><a
													href="showDriver.in?driver_id=${WorkOrder.workorders_driver_id}">${WorkOrder.workorders_drivername}</a></td>
											</tr>
											<c:if test="${configuration.showDriverNumberCol}">
												<tr class="row">
													<th class="key">Driver Number :</th>
													<td class="value">${WorkOrder.workorders_driver_number}</td>
												</tr>
											</c:if>
											<c:if test="${configuration.showOutWorkStationCol}">
												<tr class="row">
													<th class="key">Work Station :</th>
													<td class="value">${WorkOrder.out_work_station}</td>
												</tr>
											</c:if>
											<tr class="row">
												<th class="key">Work Location :</th>
												<td class="value">${WorkOrder.workorders_location}</td>
											</tr>
											<c:if test="${WorkOrder.gpsWorkLocation != null}">
												<tr class="row">
													<th class="key">GPS Work Location :</th>
													<td class="value">${WorkOrder.gpsWorkLocation}</td>
												</tr>
											</c:if>
											<c:if test="${WorkOrder.gpsOdometer != null && WorkOrder.gpsOdometer > 0}">
												<tr class="row">
												<th class="key">GPS Odometer :</th>
												<td class="value">${WorkOrder.gpsOdometer}</td>
											</tr>
											</c:if>
											<c:if test="${configuration.showRouteCol}">
												<tr class="row">
													<th class="key">Route :</th>
													<td class="value">${WorkOrder.workorders_route}</td>
												</tr>
											</c:if>
											<tr class="row">
												<th class="key">Odometer :</th>
												<td class="value">${WorkOrder.vehicle_Odometer}</td>
											</tr>
											<c:if test="${configuration.showDieselCol}">
												<tr class="row">
													<th class="key">Diesel :</th>
													<td class="value">${WorkOrder.workorders_diesel}</td>
												</tr>
											</c:if>
											<tr class="row">
												<th class="key">Initial_Note :</th>
												<td class="value">${WorkOrder.initial_note}</td>
											</tr>						
											
										</tbody>
									</table>
								</div>
								<div class="col-md-3">

									<table class="table">
										<tbody>
											<tr class="row">
												<th class="key"><h4>SubTotal :</h4></th>
												<td class="value"><h4>
														<i class="fa fa-inr"></i>
														${WorkOrder.totalsubworktask_cost}
													</h4></td>
											</tr>
											<tr class="row">
												<th class="key"><h4>GST Cost :</h4></th>
												<td class="value"><h4>
														<i class="fa fa-inr"></i> ${WorkOrder.totalworktax_cost}
													</h4></td>
											</tr>
											<tr class="row">
												<th class="key"><h4>Total :</h4></th>
												<td class="value"><h4>
														<i class="fa fa-inr"></i> ${WorkOrder.totalworkorder_cost}
													</h4></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<table class="table">
								<tfoot>
									<tr class="breadcrumb">
										<th colspan="6"><a href=""><i class="fa fa-plus"></i>
										</a></th>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</fieldset>
			</sec:authorize>
		</div>
		<div class="row">
			<small class="text-muted"><b>Created by :</b> <c:out
					value="${WorkOrder.createdBy}" /></small> | <small class="text-muted"><b>Created
					date: </b> <c:out value="${WorkOrder.created}" /></small> | <small
				class="text-muted"><b>Last updated by :</b> <c:out
					value="${WorkOrder.lastModifiedBy}" /></small> | <small class="text-muted"><b>Last
					updated date:</b> <c:out value="${WorkOrder.lastupdated}" /></small>
		</div>
	</section>
</div>

<script type="text/javascript">
	function getCompanyWiseWorkOrderPrint(workorders_id) {
		childwin = window.open('PrintCompanyWiseWorkOrders?workorders_id='+workorders_id,'newwindow', config='height=300,width=425, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, directories=no, status=no');
	}
</script>