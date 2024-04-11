<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="wrapper">
	<sec:authorize access="!hasAuthority('VIEW_INVENTORY')">
		<spring:message code="message.unauth"></spring:message>
	</sec:authorize>
	<sec:authorize access="hasAuthority('VIEW_INVENTORY')">
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
				<h3>Tyre Invoice ${TyreInvoice.ITYRE_ID}</h3>
			</div>

			<div class="row invoice-info">
				<div class="col-sm-12 col-xs-12 invoice-col">
					<div class="box-body no-padding">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr class="row">
									<th class="key">Invoice Date :</th>
									<td class="value"><c:out
											value="${TyreInvoice.INVOICE_DATE}" /></td>
								</tr>
								<tr class="row">
									<th class="key">PO Number :</th>
									<td class="value"><c:out value="${TyreInvoice.PO_NUMBER}" /></td>
								</tr>
								<tr class="row">
									<th class="key">Invoice Number :</th>
									<td class="value"><c:out
											value="${TyreInvoice.INVOICE_NUMBER}" /></td>
								</tr>
								
								<c:if test="${!configuration.roundupAmount}">
									<tr class="row">
									<th class="key">Invoice Amount:</th>
									<td class="value"><c:out
											value="${TyreInvoice.INVOICE_AMOUNT}" /></td>
									</tr>
									</c:if>
								
								<c:if test="${configuration.roundupAmount}">
									
									
									<tr class="row">
									<th class="key">Invoice Amount:</th>
									<td class="value"><c:out
											value="${TyreInvoice.INVOICE_AMOUNT}" /></td>
									</tr>
									
									
									
									<tr class="row">
									<th class="key">Roundup Amount:</th>
									<td class="value"><c:out
											value="${totalAmount}" /></td>
								</tr>
								</c:if>
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
						<table class="table">
							<thead>
								<tr class="breadcrumb">
									<th class="fit"></th>
									<th>Manufacturer &amp; Model</th>
									<th>Size</th>
									<th class="fit ar">Qty</th>
									<th class="fit ar">Each</th>
									<th class="fit ar">Dis</th>
									<th class="fit ar">GST</th>
									<th class="fit ar">Total</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${!empty TyreAmount}">
									<%
										Integer hitsCount = 1;
									%>
									<c:forEach items="${TyreAmount}" var="TyreAmount">
										<tr data-object-id="" class="ng-scope">
											<td class="fit">
												<%
													out.println(hitsCount);
																hitsCount += 1;
												%>
											</td>

											<td><c:out value="${TyreAmount.TYRE_MANUFACTURER}" /> <c:out
													value="${TyreAmount.TYRE_MODEL}" /></td>
											<td><c:out value="${TyreAmount.TYRE_SIZE}" /></td>
											<td class="fit ar">${TyreAmount.TYRE_QUANTITY}</td>
											<td class="fit ar">${TyreAmount.UNIT_COST}</td>
											<td class="fit ar">${TyreAmount.DISCOUNT}%</td>
											<td class="fit ar">${TyreAmount.TAX}%</td>
											<td class="fit ar"><i class="fa fa-inr"></i>
												${TyreAmount.TOTAL_AMOUNT}</td>

										</tr>
										<tr>
											<td colspan="7">
												<div class="row">
													<div class="col-md-11">
														<table class="table">
															<c:if test="${!empty Tyre}">
																<thead>
																	<tr class="breadcrumb">
																		<th class="icon">No</th>
																		<th class="icon ar">Tyre Serial Number</th>
																		<th class="icon ar">Amount</th>
																	</tr>
																</thead>
																<tbody>
																	<%
																		Integer tyreCount = 1;
																	%>
																	<c:forEach items="${Tyre}" var="Tyre">
																		<c:if
																			test="${Tyre.ITYRE_AMOUNT_ID == TyreAmount.ITYRE_AMD_ID}">
																			<tr data-object-id="" class="ng-scope">
																				<td class="fit">
																					<%
																						out.println(tyreCount);
																												tyreCount += 1;
																					%>
																				</td>
																				<td class="icon"><c:out
																						value="${Tyre.TYRE_NUMBER}" /></td>
																				<td class="icon ar"><i class="fa fa-inr"></i>
																					${Tyre.TYRE_AMOUNT}</td>
																			</tr>
																		</c:if>
																	</c:forEach>
																</tbody>
															</c:if>
														</table>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:if>
								<c:if test="${empty TyreAmount}">
									<tr data-object-id="" class="ng-scope">
										<td colspan="8">
											<h5 align="center">Tyre Inventory is Empty</h5>
										</td>
									</tr>
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
										<th>Description :</th>
										<td><c:out value="${TyreInvoice.DESCRIPTION}" /></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="col-xs-3">

							<table>
								<tbody>
									<tr class="row">
										<th class="key">SubTotal :</th>
										<td class="value"><i class="fa fa-inr"></i>
											${TyreInvoice.INVOICE_AMOUNT}</td>
									</tr>

									<tr class="row">
										<th class="key"><a>Total :</a></th>
										<td class="value"><a><i class="fa fa-inr"></i>
												${TyreInvoice.INVOICE_AMOUNT}</a></td>
									</tr>
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