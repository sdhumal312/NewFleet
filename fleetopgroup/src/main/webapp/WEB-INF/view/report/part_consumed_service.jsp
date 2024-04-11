<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a>
					<%-- <sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
								/ <a href="<c:url value="/ImportantReport"/>">Important Report</a>
							</sec:authorize> --%>
					/ <a href="<c:url value="/Report"/>">Report</a> / <a
						href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Depot
						Wise Part Consumed Service Report</span>
				</div>
				<div class="pull-right">
					<button class="btn btn-default btn-sm"
						onclick="printDiv('div_print')">
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
											<caption>Group Wise Service Report ${SEARCHDATE}</caption>

											<thead>
												<tr>
													<th>SL NO</th>
													<th>SE</th>
													<th>OdoMeter</th>

													<th>Invoice Date</th>
													<!-- <th>Last Occurred On</th>
													<th>warehouse Location</th> -->
													<th>Vehicle No</th>
													<th>Group</th>
													<th>Parts Details</th>
													<th colspan="2">Total</th>

												</tr>
											</thead>
											<tbody>

												<c:if test="${!empty ServiceEntries}">
													<% Integer hitsCount =1; %>
													<c:forEach items="${ServiceEntries}" var="ServiceEntries">

														<!--  display workOrder details Below  in one vehicle-->
														<tr>
															<td>
																<% out.println(hitsCount);
																	hitsCount +=1; %>
															</td>
															<td><a target="_blank"
																href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"
																data-toggle="tip" data-original-title="Click Info">SE-<c:out
																		value="${ServiceEntries.serviceEntries_Number}" /></a></td>
															<td><br> <c:out
																	value="${ServiceEntries.vehicle_Odometer}" /></td>


															<td><abbr title="" data-original-title="Today"><c:out
																		value="${ServiceEntries.invoiceDate}" /></abbr></td>
															<!-- <td>         </td>
																		<td>         </td> -->
															<td><abbr title="" data-original-title="Today"><c:out
																		value="${ServiceEntries.vehicle_registration }" /></abbr></td>
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

																<table border="0" width="100%">

																	<tbody style="border-top: 0px;">
																		<!--  display workOrder Task  details Below  in match with work Order_id-->

																		<c:if test="${!empty ServiceEntriesTask}">
																			<c:forEach items="${ServiceEntriesTask}"
																				var="ServiceEntriesTask">
																				<c:if test="${ServiceEntriesTask.serviceEntries_id == ServiceEntries.serviceEntries_id}">
																					<tr>
																						<td class="work-orders-by-task-column" colspan="2">
																							<table border="0" width="100%">
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
																								<tbody style="border-top: 0px;">
																									<!--  display workOrder Task  details Below  in match work Parts details with work Order_id-->
																									<c:if test="${!empty ServiceEntriesTaskPart}">
																										<c:forEach items="${ServiceEntriesTaskPart}"
																											var="ServiceEntriesTaskPart">
																											<c:if
																												test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																												<tr data-object-id="" class="ng-scope"
																													border="1" width="100%">
																													<td align="left" class="fit"><c:out
																															value="${ServiceEntriesTaskPart.partname}" />
																														<c:out
																															value="${ServiceEntriesTaskPart.partnumber}" /></td>
																													<td align="left" class="fit ar">${ServiceEntriesTaskPart.quantity}</td>
																													<td align="left" class="fit ar" width="15%"><i
																														class="fa fa-inr"></i>
																														${ServiceEntriesTaskPart.parteachcost}</td>
																													<td class="fit ar">
																															${ServiceEntriesTaskPart.partdisc}</td>
																															<td class="fit ar">
																															${ServiceEntriesTaskPart.parttax} </td>
																													<td align="left" class="fit ar"><iclass="fa fa-inr"></i>
																														${ServiceEntriesTaskPart.totalcost}</td>
																												</tr>
																											</c:if>
																										</c:forEach>

																									</c:if>
																								</tbody>
																							</table>
																						</td>

																						<%-- <td><i class="fa fa-inr" data-toggle="tip"
																							data-original-title="Part_cost"></i> <c:out
																								value="${ServiceEntriesTask.totalpart_cost}"></c:out></td>
																						<td><i class="fa fa-inr" data-toggle="tip"
																							data-original-title="Labor_cost"></i> <c:out
																								value="${ServiceEntriesTask.totallaber_cost}"></c:out></td>
																						<td><i class="fa fa-inr" data-toggle="tip"
																							data-original-title="Total_Task_cost"></i> <c:out
																								value="${ServiceEntriesTask.totaltask_cost}"></c:out></td> --%>
																					</tr>
																				</c:if>
																			</c:forEach>
																		</c:if>

																	</tbody>
																</table>
															</td>

														</tr>
														<tr width100% class="workorder_repair_totals">
															<th width100% class="text-right" colspan="8"><b>Total
																	Amount :</b></th>

															<td><i class="fa fa-inr" data-toggle="tip"
																data-original-title="Total_SE_cost"></i>
																${ServiceEntries.totalservice_cost}</td>
														</tr>

													</c:forEach>
												</c:if>
											</tbody>
											<tfoot>
												<tr width100% class="workorder_repair_search_totals">
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
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js"/>"></script>
</div>