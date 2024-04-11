<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_APPROVEL_RENEWAL')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_APPROVEL_RENEWAL')">
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
				<h3>Approval No ${approval.renewalApproval_Number}</h3>
			</div>
			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr class="row">
									<th>Approved Date :</th>
									<td colspan="3"><c:out
											value="${approval.approvalCreated_Date}" /></td>
								</tr>

								<tr class="row">
									<th>Approved By :</th>
									<td>${approval.approvalCreated_By}</td>

									<th>Payment Type :</th>
									<td><c:out value="${approval.approvalPayment_Type}" /></td>
								</tr>

								<tr class="row">
									<th>Payment Number :</th>
									<td><c:out value="${approval.approvalPay_Number}" /></td>

									<th>Payment By :</th>
									<td><c:out value="${approval.approvalPayment_By}" /></td>
								</tr>
								<tr class="row">
									<th>Payment Date :</th>
									<td><c:out value="${approval.approvalPayment_Date}" /></td>

									<th>Created Date :</th>
									<td><c:out value="${approval.approvalCreated_Date}" /></td>
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
						<c:if test="${!empty renewal}">
							<table class="table">
								<thead>
									<tr class="breadcrumb">
										<th class="fit">ID</th>
										<th>RRID</th>
										<th>Vehicle Name</th>
										<th>Renewal Types</th>
										<th>Validity From</th>
										<th>Validity To</th>
										<th>Amount</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<%
										Integer hitsCount = 1;
									%>
									<c:forEach items="${renewal}" var="renewal">

										<tr data-object-id="" class="ng-scope">
											<td class="fit">
												<%
													out.println(hitsCount);
																hitsCount += 1;
												%>
											</td>
											<td class="fit"><c:out value="RR-${renewal.renewal_id}" /></td>
											<td><c:out value="${renewal.vehicle_registration}" /></td>
											<td><c:out value="${renewal.renewal_type}" /> <c:out
													value="  ${renewal.renewal_subtype}" /></td>

											<td><c:out value="${renewal.renewal_from}" /></td>
											<td><c:out value="${renewal.renewal_to}" /></td>
											<td><span class="badge"> <c:out
														value="${renewal.renewal_Amount}" />
											</span></td>
											<td><span class="label label-success"><i
													class="fa fa-check-square-o"></i> <c:out
														value=" ${renewal.renewal_status}" /></span></td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr class="breadcrumb">
									</tr>
									<tr data-object-id="" class="ng-scope">
										<td colspan="5"></td>
										<td><h4>Total :</h4></td>
										<td><h4>
												<i class="fa fa-inr"></i> ${approvalTotal}
											</h4></td>
										<td></td>
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
				<div class="col-xs-12">
					<div class="row">
						<div class="table-responsive">
							<table class="table">
								<tbody>

									<tr>
										<td colspan="5"></td>
										<td><h4>Total Payment Amount :</h4></td>
										<td><h4>
												<i class="fa fa-inr"></i> ${approvalTotal}
											</h4></td>
										<td></td>
									</tr>
									<tr>
										<td colspan="5"></td>
										<td><h4>Total Approved Amount :</h4></td>
										<td><h4>
												<i class="fa fa-inr"></i> ${ApprovedAmount}
											</h4></td>
										<td></td>
									</tr>
									<tr>
										<td colspan="5"></td>
										<td><h4>Pending Payment Amount :</h4></td>
										<td><h4>
												<i class="fa fa-inr"></i> ${PendingAmount}
											</h4></td>
										<td></td>
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