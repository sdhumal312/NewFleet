<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a href="<c:url value="/Report"/>">Report</a>
					<sec:authorize access="hasAuthority('IMPORTANT_REPORT')">
						/ <a href="<c:url value="/ImportantReport"/>">Important Report</a>
					</sec:authorize>
					/ <a href="<c:url value="/VR.in"/>">Vehicle Report</a> / <span>Vehicle
						Wise Repair Report</span>
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
										<table id="advanceTable" style="width: 95%" class="table table-hover table-bordered table-striped">
											<caption>Vehicle Wise Repair Report</caption>

											<thead>
												<tr>
													<th>SL No</th>
													 <th>SE</th> 
													<th>OdoMeter</th>
													<th>Invoice Number</th>
													<th>Invoice Date</th>
													
													<!-- <th>Assigned To</th> -->
													
													 <th>Part Details</th> 
													 <th colspan="2">Total</th> 
													
												</tr>
											</thead>
											<tbody>
												<c:if test="${!empty vehicles}">
												
												<%
												Integer hitsCount = 1;
												%>
												
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
															<c:forEach items="${ServiceEntries}" var="ServiceEntries">

																<c:if test="${vehicles.vid == ServiceEntries.vid}">

																	<!--  display workOrder details Below  in one vehicle-->
																	<tr>
																		<td>
																		<%
																			out.println(hitsCount);
																			hitsCount += 1;
																			%>
																		</td>
																		<td>
																		<a target="_blank"
																			href="ServiceEntriesParts.in?SEID=${ServiceEntries.serviceEntries_id}"
																			data-toggle="tip" data-original-title="Click Info">SE-<c:out
																					value="${ServiceEntries.serviceEntries_Number}" /></a>
																		</td>
																		<td>
																		<c:out
																				value="${ServiceEntries.vehicle_Odometer}" /></td>

																		<td><abbr title="" data-original-title="Today"><c:out
																					value="${ServiceEntries.invoiceNumber}" /></abbr></td>
																		<td><abbr title="" data-original-title="Today"><c:out
																					value="${ServiceEntries.invoiceDate}" /></abbr></td>
																		
																					
																		<td colspan="2">
																			<%-- <div class="media">
																				<div class="media-body">
																					 <p>
																						 Vendor :
																						<c:out value="${ServiceEntries.vendor_name}" /> 
																					</p> 
																				</div>
																			</div> --%>
																			<table width="100%">
																				
																				<thead>
																						<tr class="breadcrumb">
																							<th width="50%" align="right" class="icon">Part</th> 
																							<th width="10%" align="right" class="icon">Quantity</th> 
																							<th width="10%" align="right" class="icon">Each</th>
																							<th width="10%" align="right" class="icon">Dis</th>
																							<th width="10%" align="right" class="icon">Tax</th>
																							<th width="10%" align="right" class="icon">Total</th> 
																							<th  class="fit"></th>
																						</tr>
																				</thead>
																				
																				
																				<tbody style="border-top: 0px;"> 
																				
																					<!--  display workOrder Task  details Below  in match with work Order_id-->

																					<c:if test="${!empty ServiceEntriesTask}">
																						<c:forEach items="${ServiceEntriesTask}"
																							var="ServiceEntriesTask">

																							<c:if
																								test="${ServiceEntriesTask.serviceEntries_id == ServiceEntries.serviceEntries_id}">

																								<tr width="100%">

		
																		<c:if test="${!empty ServiceEntriesTaskPart}">
										
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
								                             <td align="left" class="fit ar"><i class="fa fa-inr"></i>
									                               ${ServiceEntriesTaskPart.parteachcost}</td>
																	 <td align="left" class="fit ar">
																		${ServiceEntriesTaskPart.partdisc} %</td>
																	<td align="left" class="fit ar">
																		${ServiceEntriesTaskPart.parttax} % </td> 
																	<td align="left" class="fit ar" ><i class="fa fa-inr"></i>
																		${ServiceEntriesTaskPart.totalcost}</td>
									                                	
																</tr>
															</c:if>
														</c:forEach>
														
													</c:if>
	
													<!-- </div> -->

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
																		<th class="text-right" colspan="7"><b>Total
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
													<th class="text-right" colspan="7"><b> Search
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/Print/print.js" />"></script>
</div>