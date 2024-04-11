<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_SERVICE_ENTRIES')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_SERVICE_ENTRIES')">
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="pull-left">
							<a href="<c:url value="/open"/>"><spring:message
									code="label.master.home" /></a> / <a
								href="<c:url value="/newServiceEntries/1/1.in"/>"> Service
								Entries</a> / <span>
								SE- ${ServiceEntries.serviceEntries_Number }</span>
						</div>
						<div class="col-md-off-5">
							<div class="col-md-3">
								<form action="<c:url value="/searchServiceEntriesShow.in"/>"
									method="post">
									<div class="input-group">
										<span class="input-group-addon"> <span
											aria-hidden="true">SE-</span></span> <input class="form-text"
											id="vehicle_registrationNumber" name="Search" type="number"
											required="required" min="1" placeholder="ID eg: 1234">
										<span class="input-group-btn">
											<button type="submit" name="search" id="search-btn"
												class="btn btn-success btn-sm">
												<i class="fa fa-search"></i>
											</button>
										</span>
									</div>
								</form>
							</div>
							<a href="<c:url value="/newServiceEntries/1/1.in"/>">Cancel</a>
						</div>
					</div>
				</div>
			</div>
			<div class="box">
				<div class="box-body">
					<div class="row">
						<div class="col-md-7 col-sm-7 col-xs-7">

							<h4 align="center">
								<a
									href="<c:url value="/VehicleServiceEntriesDetails.in?vid=${ServiceEntries.vid}"/>"
									data-toggle="tip" data-original-title="Click Vehicle Info">
									<c:out value="${ServiceEntries.vehicle_registration}" />
								</a>
							</h4>
							<div class="secondary-header-title">
								<ul class="breadcrumb">
									<li><span class="fa fa-user"> Odometer :</span> <a
										data-toggle="tip" data-original-title="Odometer "><c:out
												value="${ServiceEntries.vehicle_Odometer}" /></a></li>
									<li><i class="fa fa-bitcoin"> Job Number :</i> <a
										data-toggle="tip" data-original-title="Job Number"><c:out
												value="${ServiceEntries.jobNumber}" /></a></li>
									<li><span class="fa fa-user"> Invoice Number :</span> <a
										data-toggle="tip" data-original-title="Invoice Number"><c:out
												value="${ServiceEntries.invoiceNumber}" /></a></li>
									<li><span class="fa fa-user"> Invoice Date :</span> <a
										data-toggle="tip" data-original-title="Invoice Date"><c:out
												value="${ServiceEntries.invoiceDate}" /></a></li>

									<li><span class="fa fa-user"> Driver Name :</span> <a
										data-toggle="tip" data-original-title="Driver Name"><c:out
												value="${ServiceEntries.driver_name}" /></a></li>
									<li><span class="fa fa-user"> Vendor Name :</span> <a
										data-toggle="tip" data-original-title="Vendor Name"><c:out
												value="${ServiceEntries.vendor_name}" /></a></li>
									<li><i class="fa fa-bitcoin"> Vendor Location :</i> <a
										data-toggle="tip" data-original-title="Location"><c:out
												value="${ServiceEntries.vendor_location}" /></a></li>
									<li><span class="fa fa-user"> Payment Mode :</span> <a
										data-toggle="tip" data-original-title="Pay Number"><c:out
												value="${ServiceEntries.service_paymentType} " /></a></li>			
									<li><span class="fa fa-user"> ${ServiceEntries.service_paymentType} Receipt No :</span> <a
										data-toggle="tip" data-original-title="Pay Number"><c:out
												value="${ServiceEntries.service_PayNumber}" /></a></li>
									<li><span class="fa fa-user"> Paid Date :</span> <a
										data-toggle="tip" data-original-title="Paid Date"><c:out
												value="${ServiceEntries.service_paiddate}" /></a></li>
									<li><span class="fa fa-user"> Paid By :</span> <a
										data-toggle="tip" data-original-title="Cashier"><c:out
												value="${ServiceEntries.service_paidby}" /></a></li>
									<c:if test="${config.TallyCompanyMasterInSE}">			
										<li><span class="fa fa-user"> Tally company :</span> <a
											data-toggle="tip" data-original-title="Tally"><c:out
													value="${ServiceEntries.tallyCompanyName}" /></a></li>
									</c:if>
									<c:if test="${config.showTripSheet}">			
										<li><span class="fa fa-user"> TripSheet Number :</span> <a
											data-toggle="tip" data-original-title="TripSheet"><c:out
													value="${ServiceEntries.tripSheetNumber}" /></a></li>
									</c:if>	
									<c:if test="${ServiceEntries.gpsOdometer != null && ServiceEntries.gpsOdometer > 0}">
										<li><span class="fa fa-user"> GPS Odometer :</span> <a
											data-toggle="tip" data-original-title="Cashier"><c:out
													value="${ServiceEntries.gpsOdometer}" /></a></li>
									</c:if>		
									<c:if test="${configuration.workshopInvoiceAmount}">
										<li><span class="fa fa-user"> WorkshopInvoice Amount :</span> <a
											data-toggle="tip" data-original-title="Cashier"><c:out
													value="${ServiceEntries.workshopInvoiceAmount}" /></a></li>
									</c:if>			
								</ul>
							</div>
						</div>
						<div class="col-md-3 col-sm-3 col-xs-3">
							<div class="row">
								<input type="hidden" id="statues" name="statues"
									value="${ServiceEntries.serviceEntries_status}">
								<div id="work-order-statuses">
									<c:if test="${reOpenSE}">
									<div id="work-order-statuses">
										<sec:authorize access="hasAuthority('REOPEN_SERVICE_ENTRIES')">
											<a data-method="post" data-remote="true"
												href="ServiceEntries_INPROCESS.in?serviceEntries_id=${ServiceEntries.serviceEntries_id}"
												rel="nofollow"> <span id="status-in-progress"
												class="status-led"> <i class="fa fa-circle"></i> <span
													class="status-text">RE-OPEN</span>
											</span>
											</a>
										</sec:authorize>
									</div>
									</c:if>
									<div class="row">
										<h4>Closed on ${ServiceEntries.completed_date}</h4>
										<sec:authorize
											access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
											<a style="width: 15%"
												href="PrintServiceEntries?id=${ServiceEntries.serviceEntries_id}"
												target="_blank" class="btn btn-default "><i
												class="fa fa-print"></i> Print</a>
											<c:choose>
												<c:when test="${ServiceEntries.service_document == true}">
													<sec:authorize
														access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
														<a class="btn btn-default" style="width: 25%"
															href="${pageContext.request.contextPath}/download/serviceDocument/${ServiceEntries.service_document_id}.in">
															<span class="fa fa-download"> Document</span>
														</a>
													</sec:authorize>
												</c:when>
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
							<c:if test="${deleteFristParts}">
								<div class="alert alert-danger">
									<button class="close" data-dismiss="alert" type="button">x</button>
									Should be Delete First Task Parts and Technician
								</div>
							</c:if>
							<c:if test="${param.SUCCESSFULLY_ROUND eq true}">
								<div class="alert alert-success">
									<button class="close" data-dismiss="alert" type="button">x</button>
									This Service Entries Successfully Rounded the Total cost.
								</div>
							</c:if>
							<div class="col-md-11 col-sm-11 col-xs-11">
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
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty ServiceEntriesTask}">
												<c:forEach items="${ServiceEntriesTask}"
													var="ServiceEntriesTask">
													<tr data-object-id="" class="ng-scope">
														<c:if test="${configuration.showServRemindWhileCreatingWO}">		
														<td>
														<c:if test="${ServiceEntriesTask.service_Number == null}">
															<c:out value="-"> </c:out>
														</c:if>
														<c:if test="${ServiceEntriesTask.service_Number != null}">
														<a href="ShowService.in?service_id=${ServiceEntriesTask.service_id}" target="_blank"> <span style="font-size: 12px;"> S-${ServiceEntriesTask.service_Number} </span> </a>
 														</c:if>	
														</td>
													</c:if>	
														<td class="fit"><c:if
																test="${ServiceEntriesTask.mark_complete == 1}">
																<h4>
																	<i class="fa fa-check" style="color: green"></i>
																</h4>
															</c:if> <c:if test="${ServiceEntriesTask.mark_complete != 1}">
																<h4>
																	<i class="fa fa-wrench" style="color: red"></i>
																</h4>
															</c:if></td>
														<td><h4>
																<c:out value="${ServiceEntriesTask.service_typetask} - " />
																<c:out value="${ServiceEntriesTask.service_subtypetask}" />
															</h4>
															<div class="row">
																<div class="col-md-11 col-sm-11 col-xs-11">
																	<table class="table">
																		<c:if test="${!empty ServiceEntriesTaskPart}">
																			<thead>
																				<tr class="breadcrumb">
																					<th class="icon">Part</th> 
																					<th class="icon ar">Qty</th>
																					<th class="icon ar">Each</th>
																					<th class="icon ar">Dis</th>
																					<th class="icon ar">Tax</th>
																					<th class="icon ar">Total</th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach items="${ServiceEntriesTaskPart}"
																					var="ServiceEntriesTaskPart">
																					<c:if
																						test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																						<tr data-object-id="" class="ng-scope">
																							<td class="fit"><c:out
																									value="${ServiceEntriesTaskPart.partname}" />
																								<c:out
																									value="${ServiceEntriesTaskPart.partnumber}" /></td>
																							<td class="fit ar">${ServiceEntriesTaskPart.quantity}</td>
																							<td class="fit ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskPart.parteachcost}</td>
																							<td class="fit ar">
																								${ServiceEntriesTaskPart.partdisc} %</td>
																							<td class="fit ar">
																								${ServiceEntriesTaskPart.parttax} %</td>
																							<td class="fit ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskPart.totalcost}</td>
																						</tr>
																					</c:if>
																				</c:forEach>
																			</tbody>
																		</c:if>
																		<!-- labor cost add -->
																		<c:if test="${!empty ServiceEntriesTaskLabor}">
																			<thead>
																				<tr class="breadcrumb">
																					<th class="icon">Technician</th>
																					<th class="icon ar">Hours</th>
																					<th class="icon ar">Rate</th>
																					<th class="icon ar">Dis</th>
																					<th class="icon ar">Tax</th>
																					<th class="icon ar">Total</th>
																				</tr>
																			</thead>
																			<tbody>
																				<c:forEach items="${ServiceEntriesTaskLabor}"
																					var="ServiceEntriesTaskLabor">
																					<c:if
																						test="${ServiceEntriesTaskLabor.servicetaskid == ServiceEntriesTask.servicetaskid}">
																						<tr data-object-id="" class="ng-scope">
																							<td class="icon"><c:out
																									value="${ServiceEntriesTaskLabor.labername}" /></td>
																							<td class="icon ar">${ServiceEntriesTaskLabor.laberhourscost}</td>
																							<td class="icon ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskLabor.eachlabercost}</td>
																							<td class="icon ar">
																								${ServiceEntriesTaskLabor.laberdiscount} %</td>
																							<td class="icon ar">
																								${ServiceEntriesTaskLabor.labertax} %</td>
																							<td class="icon ar"><i class="fa fa-inr"></i>
																								${ServiceEntriesTaskLabor.totalcost}</td>

																						</tr>
																					</c:if>
																				</c:forEach>
																			</tbody>
																		</c:if>
																	</table>
																</div>
															</div></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totalpart_cost}"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totallaber_cost}"></c:out></td>
														<td class="fit ar"><i class="fa fa-inr"></i> <c:out
																value="${ServiceEntriesTask.totaltask_cost}"></c:out></td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
							<div class="col-md-11">
								<div class="col-md-offset-8">
									<table class="table">
										<tbody>
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><h4>Total</h4></td>
												<td class="fit ar">:</td>
												<td class="fit ar"><h4>
														<i class="fa fa-inr"></i>
														${ServiceEntries.totalservice_cost}
													</h4></td>
												<td class="fit"></td>
											</tr>
											<c:if test="${configuration.workshopInvoiceAmount}">
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><h4>Workshop Amount</h4></td>
												<td class="fit ar">:</td>
												<td class="fit ar"><h4>
														<i class="fa fa-inr"></i>
														${ServiceEntries.workshopInvoiceAmount}
													</h4></td>
												<td class="fit"></td>
											</tr>
											</c:if>
											<tr data-object-id="" class="ng-scope">
												<td class="fit ar"><h4>Total Round of Value</h4></td>
												<td class="fit ar">:</td>
												<td class="fit ar"><h4>
														<i class="fa fa-inr"></i>
														${ServiceEntries.totalserviceROUND_cost}
													</h4></td>
												<td class="fit"></td>
											</tr>
										</tbody>
									</table>
								</div>
								<!-- <table class="table">
								<tfoot>
									<tr class="breadcrumb">
										<th colspan="6"><a href=""><i class="fa fa-plus"></i>
												Add Notes </a></th>
									</tr>
								</tfoot>
							</table> -->
							</div>
						</div>
					</fieldset>

				</div>
			</div>
			<div class="row">
				<small class="text-muted"><b>Created by :</b> <c:out
						value="${ServiceEntries.createdBy}" /></small> | <small
					class="text-muted"><b>Created date: </b> <c:out
						value="${ServiceEntries.created}" /></small> | <small class="text-muted"><b>Last
						updated by :</b> <c:out value="${ServiceEntries.lastModifiedBy}" /></small> |
				<small class="text-muted"><b>Last updated date:</b> <c:out
						value="${ServiceEntries.lastupdated}" /></small>
			</div>
		</sec:authorize>
	</section>
</div>
<script type="text/javascript">
	$(function() {
		$('[data-toggle="popover"]').popover()
	})
</script>
