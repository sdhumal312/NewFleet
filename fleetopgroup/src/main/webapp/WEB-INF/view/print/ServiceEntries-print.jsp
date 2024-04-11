<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('DOWNLOAD_SERVICE_ENTRIES')">
		<section class="invoice">
			<!-- title row -->
			<div class="row">
				<div class="col-xs-12">
					<h2 class="page-header">
						<c:choose>
							<c:when test="${company.company_id != null}">
								<img
									src="${pageContext.request.contextPath}/downloadlogo/${company.company_id_encode}.in"
									class="img-rounded" alt="Company Logo" width="280" height="40" />

							</c:when>
							<c:otherwise>
								<i class="fa fa-globe"></i>
								<c:out value="${company.company_name}" />

							</c:otherwise>
						</c:choose>
						<small class="pull-right"> Print By:
							${company.firstName}_${company.lastName} I. </small> <small>Branch
							:<c:out value=" ${company.branch_name}  , " /> Department :<c:out
								value=" ${company.department_name}" />
						</small>
					</h2>
				</div>
				<!-- /.col -->
			</div>
			<!-- info row -->
			<div class="row invoice-info">
				<h3>Service Entries ${ServiceEntries.serviceEntries_Number }</h3>
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr class="row">
									<th>Vehicle :</th>
									<td colspan="3"><c:out
											value="${ServiceEntries.vehicle_registration}" /></td>
								</tr>
								<tr class="row">
									<th>Odometer :</th>
									<td><c:out value="${ServiceEntries.vehicle_Odometer}" /></td>

									<th>Driver :</th>
									<td><c:out value="${ServiceEntries.driver_name}" /></td>
								</tr>
								<tr class="row">
									<th>Job Number :</th>
									<td><c:out value="${ServiceEntries.jobNumber}" /></td>

									<th>Cash Receipt No:</th>
									<td><c:out value="${ServiceEntries.service_PayNumber}" /></td>
								</tr>
								<tr class="row">
									<th>Invoice Number :</th>
									<td><c:out value="${ServiceEntries.invoiceNumber}" /></td>

									<th>Paid By:</th>
									<td><c:out value="${ServiceEntries.service_paidby}" /></td>
								</tr>
								<tr class="row">
									<th>Invoice Date :</th>
									<td><c:out value="${ServiceEntries.invoiceDate}" /></td>

									<th>Paid Date:</th>
									<td><c:out value="${ServiceEntries.service_paiddate}" /></td>
								</tr>
								<tr class="row">
									<th>Vendor :</th>
									<td colspan="3"><c:out
											value="${ServiceEntries.vendor_name}" /> - <c:out
											value="${ServiceEntries.vendor_location}" /></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- /.row -->

			<!-- Table row -->
			<div class="row">
				<div class="col-xs-12 ">
					<div class="table-responsive">
						<table class="table table-bordered table-striped">
							<thead>
								<tr class="breadcrumb">
								<c:if test="${configuration.showServRemindWhileCreatingWO}">
									<th>Service Number</th>
								</c:if>
									<th></th>
									<th>Task</th>
									<th>Parts Cost</th>
								<c:if test="${!configuration.removeFeildFromLaborTab}">
									<th>Labour Cost</th>
								</c:if>
									<th>Total</th>
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
														 <span style="font-size: 12px;"> S-${ServiceEntriesTask.service_Number} </span> 
 														</c:if>	
														</td>
										</c:if>	
											<td><c:if
													test="${ServiceEntriesTask.mark_complete == 1}">
													<h4>
														<i class="fa fa-check" style="color: green"></i>
													</h4>
												</c:if> <c:if test="${ServiceEntriesTask.mark_complete != 1}">
													<h4>
														<i class="fa fa-wrench" style="color: red"></i>
													</h4>
												</c:if></td>
											<!-- Tast table to assing part value table -->
											<td><h4>
													<c:out value="${ServiceEntriesTask.service_typetask} - " />
													<c:out value="${ServiceEntriesTask.service_subtypetask}" />
												</h4>
												<div class="row">
													<div class="col-md-11">
														<table class="table">
															<c:if test="${!empty ServiceEntriesTaskPart}">
																<thead>
																	<tr class="breadcrumb">
																		<th>Part</th>
																		<th>Qty</th>
																		<th>Each</th>
																		<th>Dis</th>
																		<th>GST</th>
																		<th>Total</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${ServiceEntriesTaskPart}"
																		var="ServiceEntriesTaskPart">
																		<c:if
																			test="${ServiceEntriesTaskPart.servicetaskid == ServiceEntriesTask.servicetaskid}">
																			<tr data-object-id="" class="ng-scope">
																				<td><c:out
																						value="${ServiceEntriesTaskPart.partname}" /> <c:out
																						value="${ServiceEntriesTaskPart.partnumber}" /></td>
																				<td>${ServiceEntriesTaskPart.quantity}</td>
																				<td><i class="fa fa-inr"></i>
																					${ServiceEntriesTaskPart.parteachcost}</td>
																				<td>${ServiceEntriesTaskPart.partdisc}%</td>
																				<td>${ServiceEntriesTaskPart.parttax}%</td>
																				<td><i class="fa fa-inr"></i>
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
																		<th>Technician</th>
																		<th>Hours</th>
																	<c:if test="${!configuration.removeFeildFromLaborTab}">
																		<th>Rate</th>
																		<th>Dis</th>
																		<th>GST</th>
																	</c:if>
																		<th>Total</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${ServiceEntriesTaskLabor}"
																		var="ServiceEntriesTaskLabor">
																		<c:if
																			test="${ServiceEntriesTaskLabor.servicetaskid == ServiceEntriesTask.servicetaskid}">
																			<tr data-object-id="" class="ng-scope">
																				<td><c:out
																						value="${ServiceEntriesTaskLabor.labername}" /></td>
																				<td><fmt:formatNumber maxFractionDigits="2" value="${ServiceEntriesTaskLabor.laberhourscost}"/> </td>
																			<c:if test="${!configuration.removeFeildFromLaborTab}">
																				<td><i class="fa fa-inr"></i>
																					<fmt:formatNumber maxFractionDigits="2" value="${ServiceEntriesTaskLabor.eachlabercost}"/> </td>
																				<td> <fmt:formatNumber maxFractionDigits="2" value="${ServiceEntriesTaskLabor.laberdiscount}"/>%</td>
																				<td><fmt:formatNumber maxFractionDigits="2" value="${ServiceEntriesTaskLabor.labertax}" /> %</td>
																			</c:if>
																				<td><i class="fa fa-inr"></i>
																					<fmt:formatNumber maxFractionDigits="2" value="${ServiceEntriesTaskLabor.totalcost}"/> </td>

																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</c:if>
														</table>
													</div>
												</div></td>
											<td><i class="fa fa-inr"></i> <c:out
													value="${ServiceEntriesTask.totalpart_cost}"></c:out></td>
										<c:if test="${!configuration.removeFeildFromLaborTab}">
											<td><i class="fa fa-inr"></i> <c:out
													value="${ServiceEntriesTask.totallaber_cost}"></c:out></td>
										</c:if>
											<td><i class="fa fa-inr"></i> <c:out
													value="${ServiceEntriesTask.totaltask_cost}"></c:out></td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-striped">
						<tbody>
							<tr data-object-id="" class="ng-scope">
								<th class="key"><h4>Total :</h4></th>
								<td class="value"><h4>
										<i class="fa fa-inr"></i> ${ServiceEntries.totalservice_cost}
									</h4></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</section>
	</sec:authorize>
</div>
<!-- ./wrapper -->