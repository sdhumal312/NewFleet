<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> /  <span>Depot wise WO-SE Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default" onclick="printDiv('div_print')">
						<span class="fa fa-print"> Print</span>
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
		<sec:authorize access="hasAuthority('VIEW_VEHICLE')">
			<div id="div_print">

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
								<div class="row invoice-info">
									<table class="table table-hover table-bordered table-striped">
										<caption>Vehicle Wise Repair Report</caption>
										<thead>
											<tr>
													<th>SL NO</th>
													<th>WO/SE</th>
													
													<th>OdoMeter</th>
													<th>Start Date</th>
													<th>Due Date / Invoice Date</th>
													<th>Last Occurred On</th>
													<th>WareHouse Location</th>
													<th>Vehicle No</th>
													<th>Group</th>
													<th>Parts Details</th>
													<th>Total</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty vehicles}">
											<%
														Integer hitsCount = 1;
													%>
												<c:forEach items="${vehicles}" var="vehicles">

													<!-- vehicle Name Show -->
													

													<c:if test="${!empty WorkOrder}">
														<c:forEach items="${WorkOrder}" var="WorkOrder">

															<c:if test="${vehicles.vid == WorkOrder.vehicle_vid}">

																<!--  display workOrder details Below  in one vehicle-->
																<tr>
																<td><%
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
																					<c:when test="${workordertask.last_occurred_woId != 0}">
																					<samp style="color: red;">
																					Last occurred on ${workordertask.last_occurred_date}
																					this task on Odometer =
																					${workordertask.last_occurred_odameter}
																					</samp>
																					</c:when>
																					<c:otherwise>
																					<samp style="color: blue;">Never logged for is
																					task</samp>
																					</c:otherwise>
																					</c:choose>
																					
																					</c:if>
																					</c:forEach>
																					</c:if> </td>
																				<td><c:out value="${WorkOrder.workorders_location}"/></td>
																				<td><abbr title="" data-original-title="Today"><c:out
																				value="${WorkOrder.vehicle_registration}" /></abbr></td>
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
																		<table width="100%">
																			
																			<tbody style="border-top: 0px;">
																				<!--  display workOrder Task  details Below  in match with work Order_id-->
																				
																				<c:if test="${!empty workordertask}">
																					<c:forEach items="${workordertask}"
																						var="workordertask">
																						<c:if
																							test="${workordertask.workorders_id == WorkOrder.workorders_id}">
																							<tr>
																								<td class="work-orders-by-task-column"
																									colspan="2">
																									
																									<table   width="100%">
																										<tbody style="border-top: 0px;">
																											<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																											<c:if test="${!empty WorkOrderTaskPart}">
																											<thead border="1">
																													<tr class="breadcrumb">
																													<th width="60%" class="fit ar">Part </th>
																													<th class="icon ar">Qty </th> 
																													<th class="icon ar">Each </th> 
																													<!-- <th class="icon ar">Dis </th> 
																													<th class="icon ar">Tax </th>  -->
																													<th class="icon ar">Total </th>
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
																<tr class="vehicle_repair_total">
																	<th class="text-right" colspan="10"><b>Total
																			Amount :</b></th>

																	<td><i class="fa fa-inr" data-toggle="tip"
																		data-original-title="Total_WO_cost"></i>
																		${WorkOrder.totalworkorder_cost}</td>
																</tr>
															</c:if>
														</c:forEach>
														<!-- close work orders for -->
													</c:if>
													<!-- close work orders if -->
													<%-- <tr class="workorder_repair_totals">
														<th class="text-right" colspan="10"><b> WorkOrders
																Total Amount :</b></th>

														<td><i class="fa fa-inr"></i> ${TotalWOAmount}</td>
													</tr> --%>

													<c:if test="${!empty ServiceEntries}">
														<c:forEach items="${ServiceEntries}" var="ServiceEntries">

															<c:if test="${vehicles.vid == ServiceEntries.vid}">

																<!--  display workOrder details Below  in one vehicle-->
																<tr>
																<td><%
																				out.println(hitsCount);
																							hitsCount += 1;
																			%></td>
																	<td><a target="_blank"
																		href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"
																		data-toggle="tip" data-original-title="Click Info">SE-<c:out
																				value="${ServiceEntries.serviceEntries_Number}" /></a></td>
																
																	<td><c:out
																			value="${ServiceEntries.vehicle_Odometer}" /></td>
																	<td></td>
																	<%-- <td><abbr title="" data-original-title="Today"><c:out
																				value="${ServiceEntries.invoiceNumber}" /></abbr></td> --%>
																	<td><abbr title="" data-original-title="Today"><c:out
																				value="${ServiceEntries.invoiceDate}" /></abbr></td>
																				<td>-</td>
																				<td>-</td>
																				<td><abbr title="" data-original-title="Today"><c:out
																				value="${ServiceEntries.vehicle_registration}" /></abbr></td>
																				<td><abbr title="" data-original-title="Today"><c:out
																		value="${ServiceEntries.vehicle_Group}" /></abbr></td>
																	<td colspan="4">
																		<div class="media">
																			<div class="media-body">
																				<%-- <p>
																					Vendor :
																					<c:out value="${ServiceEntries.vendor_name}" />
																				</p> --%>
																			</div>
																		</div>
																		<table width="100%">
																		
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
																								
																									<table width="100%">
																										<tbody style="border-top: 0px;">
																											<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																											<c:if test="${!empty ServiceEntriesTaskPart}">
																											<thead border="1">
																											<tr class="breadcrumb">
																										<th width="50%" align="right" >Part</th>
																										<th width="10%" align="left" class="icon ar">Qty
																										</th>
																										<th width="10%" align="left" class="icon ar">Each
																										</th>
																										<th width="10%" align="left" class="icon ar">Discount
																										</th>
																										<th width="10%" align="left" class="icon ar">Tax
																										</th>
																										<th width="10%" align="left" class="icon ar">Total
																										</th>
																										
																									</tr>
																											</thead>
																												<c:forEach
																												items="${ServiceEntriesTaskPart}"
																												var="ServiceEntriesTaskPart">
																												<c:if
																												test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																												<tr data-object-id="" class="ng-scope" border="1" width="100%">
																												<td align="left" class="fit"><c:out
																							                          value="${ServiceEntriesTaskPart.partname}" />
																							                            <c:out
																							                         value="${ServiceEntriesTaskPart.partnumber}" /></td>
																							                            <td align="left" class="fit ar">${ServiceEntriesTaskPart.quantity}</td>
																							                            <td align="left" class="fit ar" width="15%"><i class="fa fa-inr"></i>
																							                              ${ServiceEntriesTaskPart.parteachcost}</td>
																															 <td class="fit ar">
																															${ServiceEntriesTaskPart.partdisc}</td>
																															<td class="fit ar">
																															${ServiceEntriesTaskPart.parttax} </td>
																															<td align="left" class="fit ar" ><i class="fa fa-inr"></i>
																															${ServiceEntriesTaskPart.totalcost}</td>
																												                               	
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
											<tr class="vehicle_repair_total">
												<th class="text-right" colspan="10"><b>
														ServiceEntries Total Amount :</b></th>
												<td><i class="fa fa-inr"></i> ${TotalSEAmount}</td>
											</tr>
										</tbody>
										<tfoot>
										<tr class="workorder_repair_search_totals">
													<th class="text-right" colspan="10"><b> Work Order
															Total Amount :</b></th>
													<td align="right"><i class="fa fa-inr" data-toggle="tip"
																			data-original-title="Total_WO_cost"></i>
																			${TotalWOAmount}</td>
													<%-- <td><i class="fa fa-inr"></i> ${TotalWOAmount}</td> --%>
												</tr>
											<tr class="workorder_repair_search_totals">
												<th class="text-right" colspan="10"><b> Total Amount WOSE
														:</b></th>
												<td><i class="fa fa-inr"></i> ${TotalWOSEAmount}</td>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>

				</section>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>