<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_VENDOR')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_VENDOR')">
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
				<div class="col-sm-12 col-xs-12 invoice-col">

					<h3 style="margin-top: -10px;">
						<c:out value="${vendor.vendorName}" />
						-
						<c:out value="${vendor.vendorType}" />
						Vendor
					</h3>
				</div>
			</div>
			<!-- /.row -->

			<!-- Table row -->
			<div class="row">
				<div class="col-xs-12 ">
					<div class="row">
						<div class="col-sm-5 col-xs-5 invoice-col">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Vendor Profile Information</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th>Vendor Type:</th>
												<td><c:out value="${vendor.vendorType}" /></td>
											</tr>
											<tr class="row">
												<th>Phone :</th>
												<td><c:out value="${vendor.vendorPhone}" /></td>
											</tr>
											<tr class="row">
												<th>Location :</th>
												<td><c:out value="${vendor.vendorLocation}" /></td>
											</tr>
											<tr class="row">
												<th>WebSite :</th>
												<td><c:out value="${vendor.vendorWebsite}" /></td>
											</tr>

											<tr class="row">
												<th>Address :</th>
												<td>
													<address class="no-margin">
														<c:out value="${vendor.vendorAddress1}" />
														,
														<c:out value="${vendor.vendorAddress2}" />
														<br>
														<c:out value="${vendor.vendorCity}" />
														,
														<c:out value="${vendor.vendorState}" />
														<br>
														<c:out value="${vendor.vendorCountry}" />
														-Pin :
														<c:out value="${vendor.vendorPincode}" />
													</address>

												</td>
											</tr>
											<tr class="row">
												<th>Contact Person :</th>
												<td>

													<div class="t-padded">
														<c:out value="${vendor.vendorFirName}" />
													</div>
													<div class="t-padded">
														<c:out value="${vendor.vendorFirPhone}" />
													</div>
													<div class="t-padded">
														<c:out value="${vendor.vendorFirEmail}" />
													</div>
												</td>
											</tr>
											<tr class="row">
												<th>Secondary Contact Person :</th>
												<td>

													<div class="t-padded">
														<c:out value="${vendor.vendorSecName}" />
													</div>
													<div class="t-padded">
														<c:out value="${vendor.vendorSecPhone}" />
													</div>
													<div class="t-padded">
														<c:out value="${vendor.vendorSecEmail}" />
													</div>

												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<!-- /.col -->
						<div class="col-sm-5 col-xs-5 invoice-col">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Vendor Payment Details</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th>Term :</th>
												<td><c:out value="${vendor.vendorTerm}" /></td>
											</tr>
											<tr class="row">
												<th>PAN Card No :</th>
												<td><c:out value="${vendor.vendorPanNO}" /></td>
											</tr>
											<tr>
												<th>GST No :</th>
												<td><c:out value="${vendor.vendorGSTNO}" /></td>
											</tr>
											<tr>
												<th>GST Registered :</th>
												<td><c:choose>
														<c:when
															test="${vendor.vendorGSTRegisteredId == 1}">

																Turnover Below 25 lakhs GST
															</c:when>
														<c:otherwise>
																Turnover Above 25 lakhs GST

															</c:otherwise>
													</c:choose></td>
											</tr>
											<tr class="row">
												<th>Credit Limit No :</th>
												<td><c:out value="${vendor.vendorCreditLimit}" /></td>
											</tr>
											<tr class="row">
												<th>Advance Paid :</th>
												<td><c:out value="${vendor.vendorAdvancePaid}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">Vendor Bank Details</h3>
								</div>
								<div class="box-body no-padding">
									<table class="table table-bordered table-striped">
										<tbody>
											<tr class="row">
												<th>Bank Name :</th>
												<td><c:out value="${vendor.vendorBankName}" /></td>
											</tr>
											<tr class="row">
												<th>Bank Branch :</th>
												<td><c:out value="${vendor.vendorBankBranch}" /></td>
											</tr>
											<tr class="row">
												<th>Bank A/c No :</th>
												<td><c:out value="${vendor.vendorBankAccno}" /></td>
											</tr>
											<tr class="row">
												<th>Bank IFSC No :</th>
												<td><c:out value="${vendor.vendorBankIfsc}" /></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- /.col -->

					<!-- /.row -->
					<div class="row">
						<!-- accepted payments column -->
						<div class="col-xs-12" align="center">
							<table class="table table-bordered table-striped">
								<tbody>
									<tr class="row">
										<th class="key">You Have to Pay :</th>
										<td class="value"><i class="fa fa-inr"></i> <c:out
												value="${fuelPayVendorTotal}" /></td>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</section>
	</sec:authorize>
</div>