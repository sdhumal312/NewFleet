<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('DOWNLOAD_WORKORDER')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('DOWNLOAD_WORKORDER')">
		<!-- Main content -->
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
							${company.firstName}_${company.lastName} I.</small> <small>Branch
							:<c:out value=" ${company.branch_name}  , " /> Department :<c:out
								value=" ${company.department_name}" />
						</small>
					</h2>
				</div>
				<!-- /.col -->
			</div>
			<!-- info row -->
			<div class="row invoice-info">
				<h3>Work Orders ${WorkOrder.workorders_Number }</h3>
			</div>

			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr class="row">
									<th>Vehicle :</th>
									<td colspan="3"><c:out
											value="${WorkOrder.vehicle_registration}" /></td>
								</tr>
								<tr class="row">
									<th>Odometer :</th>
									<td><c:out value="${WorkOrder.vehicle_Odometer}" /></td>

									<th>Driver Name :</th>
									<td><c:out value="${WorkOrder.workorders_drivername}" /></td>
								</tr>
								<tr class="row">
									<c:if test="${!configuration.storeLocation}">
										<th class="key">Work Location :</th>
									</c:if>
									<c:if test="${configuration.storeLocation}">
										<th class="key">Store Location :</th>
									</c:if>
									<td>${WorkOrder.workorders_location}
										<c:if test="${showSubLocation && WorkOrder.subLocation != null}">
												-(${WorkOrder.subLocation})
												</c:if>
									</td>

									<th>Assigned:</th>
									<td><c:out value="${WorkOrder.assignee}" /></td>
								</tr>
								<c:if test="${configuration.workLocation}">
									<tr class="row">
										<th class="key">Work Location :</th>
										<td class="value">${WorkOrder.workLocation}</td>
									</tr>
								</c:if>
								<tr class="row">
									<th>Start Date:</th>
									<td><c:out value="${WorkOrder.start_date}" /></td>
									<th>Due Date:</th>
									<td><c:out value="${WorkOrder.due_date}" /></td>
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
								<c:if test="${!configuration.HideAmountInWorkOrderPrint}">
									<th>Parts Cost</th>
								</c:if>	
								<c:if test="${!configuration.removeFeildFromLaborTab}">
									<c:if test="${!configuration.driverSignatureInWorkOrderPrint}">
										
										<th>Labour Cost</th>
										<th>Total</th>
									</c:if>	
								</c:if>
									<!-- <th>Actions</th> -->
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
														<span style="font-size: 12px;"> ${WorkOrderTask.service_Number} </span>
													</c:if>
												</td>
											</c:if>
											<td><c:if test="${WorkOrderTask.mark_complete == 1}">
													<h4>
														<i class="fa fa-check" style="color: green"></i>
													</h4>
												</c:if> <c:if test="${WorkOrderTask.mark_complete != 1}">
													<h4>
														<i class="fa fa-wrench" style="color: red"></i>
													</h4>
												</c:if>
											</td>
											<!-- Tast table to assing part value table -->
											<td><h4>
													<c:out value="${WorkOrderTask.job_typetask} - "></c:out>
													<c:out value="${WorkOrderTask.job_subtypetask}"></c:out>
												</h4>
												<div class="row">
													<c:choose>
														<c:when test="${WorkOrderTask.last_occurred_woId != 0}">
															<samp style="color: red;"> Last occurred on ${WorkOrderTask.last_occurred_date} this task on Odometer = ${WorkOrderTask.last_occurred_odameter}. 
																<a href="showWorkOrder?woId=${WorkOrderTask.last_occurred_woId}" target="_blank"><i class="fa fa-external-link"></i></a>
															</samp>
														</c:when>
														<c:otherwise>
															<samp style="color: blue;">Never logged for is task</samp>
														</c:otherwise>
													</c:choose>
												</div>
											</td>
											<c:if test="${!configuration.HideAmountInWorkOrderPrint}">
											<td><i class="fa fa-inr"></i> <c:out
													value="${WorkOrderTask.totalpart_cost}"></c:out></td>
											</c:if>
											
										<c:if test="${!configuration.removeFeildFromLaborTab}">
											<c:if test="${!configuration.HideAmountInWorkOrderPrint}">
											<td><i class="fa fa-inr"></i> <c:out
													value="${WorkOrderTask.totallaber_cost}"></c:out></td>
											</c:if>
										</c:if>
										<c:if test="${!configuration.HideAmountInWorkOrderPrint}">
											<td><i class="fa fa-inr"></i> <c:out
													value="${WorkOrderTask.totaltask_cost}"></c:out></td>	
										</c:if>
												</tr>
										<c:if test="${configuration.showJobTypeRemarkCol}">
												<tr>
													<td colspan="1">Task Remark : </td>
													<td colspan="4">${WorkOrderTask.jobTypeRemark}</td>
												</tr>
										</c:if>		
											
												<tr>
												<td colspan="5"><div class="row">
													<div class="col-xs-11">
														<table class="table">
															<c:if test="${!empty WorkOrderTaskPart}">
																<thead>
																	<tr class="breadcrumb">
																		<th class="fit">Part</th>
																		<th class="fit">Qty</th>
																		
																		<c:if test="${!configuration.driverSignatureInWorkOrderPrint}">
																			<th class="fit">Each</th>
																			<th class="fit">Total</th>
																		</c:if>
																	</tr>
																</thead>
																<tbody style="border-top: 0px;">

																	<c:forEach items="${WorkOrderTaskPart}"
																		var="WorkOrderTaskPart">
																		<c:if
																			test="${WorkOrderTaskPart.workordertaskid == WorkOrderTask.workordertaskid}">
																			<tr data-object-id="" class="ng-scope">
																				<td style="text-align: left;" class="fit"><c:out
																						value="${WorkOrderTaskPart.partname}" /> <c:out
																						value="${WorkOrderTaskPart.partnumber}" /></td>
																				<td style="text-align: left;" class="fit">${WorkOrderTaskPart.quantity}</td>
																				
																				<c:if test="${!configuration.driverSignatureInWorkOrderPrint}">
																				<td style="text-align: left;" class="fit"><i class="fa fa-inr"></i>
																					${WorkOrderTaskPart.parteachcost}</td>
																				<td style="text-align: left;" class="fit"><i class="fa fa-inr"></i>
																					${WorkOrderTaskPart.totalcost}</td>
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
																		<th>Technician</th>
																		<th>Hours</th>
																	<c:if test="${!configuration.removeFeildFromLaborTab}">	
																		<th>RateS</th>
																		<th>Dis</th>
																		<th>GST</th>
																		<th>Total</th>
																	</c:if>	
																	</tr>
																</thead>
																<tbody>

																	<c:forEach items="${WorkOrderTaskLabor}"
																		var="WorkOrderTaskLabor">
																		<c:if
																			test="${WorkOrderTaskLabor.workordertaskid == WorkOrderTask.workordertaskid}">
																			<tr data-object-id="" class="ng-scope">
																				<td style="text-align: left;"><c:out
																						value="${WorkOrderTaskLabor.labername}" /></td>
																				<td style="text-align: left;">${WorkOrderTaskLabor.laberhourscost}</td>
																		<c:if test="${!configuration.removeFeildFromLaborTab}">
																				<td style="text-align: left;"><i class="fa fa-inr"></i>
																					${WorkOrderTaskLabor.eachlabercost}</td>
																				<td style="text-align: left;">${WorkOrderTaskLabor.laberdiscount}%</td>
																				<td style="text-align: left;">${WorkOrderTaskLabor.labertax}%</td>
																				<td style="text-align: left;"><i class="fa fa-inr"></i>
																					${WorkOrderTaskLabor.totalcost}</td>
																		</c:if>		
																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</c:if>
														</table>
													</div>
												</div></td>
											
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
					<div class="row">
						<div class="col-xs-7">
							<table>
								<tbody style="border-top: 0px;">

									<tr class="row">
										<th class="key">Initial_Note :</th>
										<td class="value">${WorkOrder.initial_note}</td>
									</tr>
									<c:if test="${configuration.driverSignatureInWorkOrderPrint}">
										<tr class="row"> 
											<th style="padding-top:55px;padding-left:25px;" class="key">Driver Signature</th>
										</tr>
									</c:if>
								</tbody>
							</table>
						</div>
						<div class="col-xs-3">

							<table>
								<tbody>
									<c:if test="${!configuration.driverSignatureInWorkOrderPrint}">
									<tr class="row">
										<th class="key"><h4>SubTotal :</h4></th>
										<td class="value"><h4>
												<i class="fa fa-inr"></i> ${WorkOrder.totalsubworktask_cost}
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
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</section>
		<!-- /.content -->
	</sec:authorize>
</div>