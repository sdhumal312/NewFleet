<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
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
			</div>
			<div class="row invoice-info">
				<h3>Approval No ${approval.approvalNumber}</h3>
			</div>
			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr class="row">
									<th>Vendor :</th>
									<td colspan="3"><c:out
											value="${approval.approvalvendorName}" /></td>
								</tr>

								<tr class="row">
									<th>Location :</th>
									<td>${approval.approvalvendorLocation }</td>

									<th>Type :</th>
									<td><c:out value="${vendor.vendorType}" /></td>
								</tr>

								<tr class="row">
									<th>Phone :</th>
									<td><c:out value="${vendor.vendorPhone}" /></td>

									<%-- <th>GST NO :</th>
									<td><c:out value="${vendor.vendorTaxNO}" /></td> --%>
								</tr>
								<tr class="row">
									<th>PAN No :</th>
									<td><c:out value="${vendor.vendorPanNO}" /></td>

									<%-- <th>VAT No :</th>
									<td><c:out value="${vendor.vendorVatNo}" /></td> --%>
								</tr>
								<tr class="row">
									<th>Address :</th>
									<td colspan="3"><address>
											<c:out value="${vendor.vendorAddress1}" />
											,
											<c:out value="${vendor.vendorAddress2}" />
											<br>
											<c:out value="${vendor.vendorCity}" />
											,
											<c:out value="${vendor.vendorState}" />
											,
											<c:out value="${vendor.vendorCountry}" />
											-Pin :
											<c:out value="${vendor.vendorPincode}" />
										</address></td>
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
						<c:if test="${!empty ServiceEntries}">
							<table class="table table-bordered table-striped"">
								<thead>
									<tr class="breadcrumb">
										<th class="fit">No</th>
										<th class="fit ar">ID</th>
										<th class="fit ar">Invoice/Job Number</th>
										<th class="fit ar">Invoice Date</th>
										<th class="fit ar">Vendor/Vehicle</th>
										<th class="fit ar">Invoice Cost</th>
										<th class="fit ar">Approved Cost</th>
										<th class="fit ar">Payment Status</th>
									</tr>
								</thead>
								<tbody>
									<%
										Integer hitsCount = 1;
									%>
									<c:forEach items="${ServiceEntries}" var="ServiceEntries">
																<tr data-object-id="" class="ng-scope">
																	<td class="fit">
																		<%
																			out.println(hitsCount);
																						hitsCount += 1;
																		%>
																	</td>
																	<td class="fir ar"><a href="${ServiceEntries.transactionUrl}" target="_blank">${ServiceEntries.transactionNumber}</a></td>
																	<td class="fir ar">${ServiceEntries.invoiceNumber}</td>
																	<td class="fir ar">${ServiceEntries.invoiceDateStr}</td>
																	
																	<c:choose>
																		<c:when test="${ServiceEntries.vehicleNumber != null}">
																			<td class="fir ar">${ServiceEntries.vehicleNumber}</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${approval.approvalvendorName}" />
																			</td>
																		</c:otherwise>
																	</c:choose>	
																	<td class="fir ar">${ServiceEntries.subApprovalTotal}</td>
																	<td class="fir ar">${ServiceEntries.subApprovalPaidAmount}</td>
																	<c:choose>
																		<c:when test="${ServiceEntries.approvedPaymentStatus == 'PAID'}">
																			<td class="fir ar">CLEAR</td>
																		</c:when>
																		<c:otherwise>
																			<td class="fir ar">
																				<c:out value="${ServiceEntries.approvedPaymentStatus}" />
																			</td>
																		</c:otherwise>
																	</c:choose>		
																	
																</tr>
															</c:forEach>
								</tbody>
								<tfoot>
									<tr class="breadcrumb">
									</tr>
								</tfoot>
							</table>
						</c:if>
					</div>
					
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
			<!-- /.row -->
			<div class="row">
				<div class="col-xs-12 table-responsive">
					<div class="row">
						<div class="col-xs-7">
							<table>
								<tbody style="border-top: 0px;">

									<tr class="row">
										<th>Approved By :</th>
										<td><c:out value="${approval.approvalCreateBy}" /></td>
									</tr>

									<tr class="row">
										<th>Approved Date :</th>
										<td><c:out value="${approval.approvalCreateDateStr}" /></td>
									</tr>

									<tr class="row">
										<th>Modes of Payment :</th>
										<td><c:out value="${approval.approvalPaymentType}" /></td>
									</tr>
									<tr class="row">
										<th><c:out
												value="${approval.approvalPaymentType} Transaction No :" /></th>
										<td><c:out value="${approval.approvalPayNumber}" /></td>
									</tr>

									<tr class="row">
										<th>Date Of Payment :</th>
										<td><c:out value="${approval.approvalDateofpayment}" /></td>
									</tr>

									<tr class="row">
										<th>Cashier | Paid By :</th>
										<td><c:out value="${approval.approvalpaidby}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="col-xs-3">

							<table>
								<tbody>
									<tr class="row">
										<th><h4>Total :</h4></th>
										<td><h4>
												<i class="fa fa-inr"></i> ${totalPaidApprovalAmount}
											</h4></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- /.col -->
			</div>
		</section>
	</sec:authorize>
</div>