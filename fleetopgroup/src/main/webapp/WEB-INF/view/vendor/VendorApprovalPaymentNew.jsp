<%@ include file="taglib.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/ApprovalPaymentList/1.in"/>">Vendor
						Approved Payment</a>

				</div>
				<div class="pull-right">
					<a href="<c:url value="/findPendingVendorPayment.in"/>" class="btn btn-primary"><em class="fa fa-search"></em> Pending Vendor Payment</a>
					<a href="<c:url value="/findApprovalForPayment.in"/>" class="btn btn-success"><em class="fa fa-search"></em>Search Approval</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
				<div class="col-md-3 col-sm-4 col-xs-12">
					<div class="info-box">
						<span class="info-box-icon bg-green"><i
							class="fa fa-thumbs-o-up"></i></span>
						<div class="info-box-content">
							<span class="info-box-text">Total Approved</span> <span
								class="info-box-number">${approvalCount}</span>
						</div>
					</div>
				</div>
				<div class="col-md-4 col-sm-5 col-xs-12">
					<div class="info-box">
						<div class="info-box-center">
							<span class="info-box-text">Search Payment</span>
							<form action="<c:url value="/searchVendorApproval.in"/>"
								method="post">
								<div class="input-group">
									<input class="form-text" id="vehicle_registrationNumber"
										name="approvalvendorName" type="text" required="required"
										placeholder="A-ID, A-Name,A-Type, Amount"> <span
										class="input-group-btn">
										<button type="submit" name="search" id="search-btn"
											class="btn btn-success btn-sm">
											<i class="fa fa-search"></i>
										</button>
									</span>
								</div>
							</form>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
		<c:if test="${param.saveapproval eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${param.deleteapproval eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Canceled successfully .
			</div>
		</c:if>
		<c:if test="${saveapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Created successfully .
			</div>
		</c:if>
		<c:if test="${deleteapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Canceled successfully .
			</div>
		</c:if>
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Already Updated.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${updateapproval}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Updated successfully .
			</div>
		</c:if>
		<c:if test="${dangerapproval}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This approval Not Deleted.
			</div>
		</c:if>
		<div class="row">
			<div class="main-body">
				<div class="box">
					<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
						<spring:message code="message.unauth"></spring:message>
					</sec:authorize>
					<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
						<div class="box-body">

							<div class="row">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation"><a
										href="<c:url value="/VendorApprovalCreated/1.in"/>">Created
											Entries</a></li>
									
									<li role="presentation" class="active"><a
										href="<c:url value="/ApprovalPaymentList/1.in"/>">Approved
											Entries</a></li>
									<li role="presentation"><a
										href="<c:url value="/ApprovalCompleted/1.in"/>">Completed
											Approval</a></li>
								</ul>
							</div>
						</div>
					</sec:authorize>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="main-body">
				<sec:authorize access="!hasAuthority('VIEW_APPROVEL_VENDOR')">
					<spring:message code="message.unauth"></spring:message>
				</sec:authorize>
				<sec:authorize access="hasAuthority('VIEW_APPROVEL_VENDOR')">
					<c:if test="${!empty approval}">
						<div class="box">
							<div class="box-body">
								<table id="approvalTablePayment" class="table  table-hover">
									<thead>
										<tr>
											<th class="fit">Approval Id</th>
											<th>Vendor Name</th>
											<th class="fit ar">Vendor Type</th>
											<th class="fit ar">Create Date</th>
											<th class="fit ar">Approved By</th>
											<th class="fit ar">Amount</th>
											<th class="fit ar">Status</th>
											<th class="fir">Actions</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${approval}" var="approval">

											<tr data-object-id="" class="ng-scope">
												<td class="fit"><a
													href="<c:url value="/ShowApprovalPayment.in?approvalId=${approval.approvalId}"/>"><c:out
															value="A-${approval.approvalNumber}" /></a></td>
												<td><a
													href="<c:url value="/ShowApprovalPayment.in?approvalId=${approval.approvalId}"/>"
													data-toggle="tip"
													data-original-title="Click this Vendor Details"> <c:out
															value="${approval.approvalvendorName} - ${approval.approvalvendorLocation}" />

												</a></td>
												<td class="fit ar"><c:out
														value="${approval.approvalvendorType}" /></td>
												<td class="fit ar"><c:out value="${approval.created}" /></td>

												<td class="fir ar"><c:out
														value="${approval.approvalCreateBy}" /></td>

												<c:if test="${configuration.vendorPaymentFlavor3}">
												<td class="fir ar"><fmt:formatNumber type="number" pattern="#.##" value="${approval.totalApprovalPaidAmount}" /></td>
												</c:if>
												
												<c:if test="${!configuration.vendorPaymentFlavor3}">
												<td class="fir ar"><fmt:formatNumber type="number" pattern="#.##" value="${approval.approvalTotal}" /></td>
												</c:if>

												<td class="fir ar"><c:choose>

														<c:when test="${approval.approvalStatusId == 3 || approval.approvalStatusId == 4}">
															<span class="label label-pill label-warning"><c:out
																	value="${approval.approvalStatus}" /></span>
														</c:when>
														<c:otherwise>
															<span class="label label-pill label-success"><c:out
																	value="${approval.approvalStatus}" /></span>
														</c:otherwise>
													</c:choose></td>

												<td class="fir"><c:choose>

														<c:when test="${approval.approvalStatusId == 3 || approval.approvalStatusId == 4}">
															<sec:authorize
																access="hasAuthority('PAYMENT_APPROVEL_VENDOR')">
																<a class="btn btn-success btn-sm"
																	href="<c:url value="/approvedPayment.in?approvalId=${approval.approvalId}"/>"
																	class="confirmation"
																	onclick="return confirm('Are you sure? Payment  ')">
																	<span class="fa fa-cog"> Make Payment</span>
																</a>
															</sec:authorize>
														</c:when>
													</c:choose></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<c:url var="firstUrl" value="/ApprovalPaymentList/1" />
						<c:url var="lastUrl"
							value="/ApprovalPaymentList/${deploymentLog.totalPages}" />
						<c:url var="prevUrl"
							value="/ApprovalPaymentList/${currentIndex - 1}" />
						<c:url var="nextUrl"
							value="/ApprovalPaymentList/${currentIndex + 1}" />
						<div class="text-center">
							<ul class="pagination pagination-lg pager">
								<c:choose>
									<c:when test="${currentIndex == 1}">
										<li class="disabled"><a href="#">&lt;&lt;</a></li>
										<li class="disabled"><a href="#">&lt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${firstUrl}">&lt;&lt;</a></li>
										<li><a href="${prevUrl}">&lt;</a></li>
									</c:otherwise>
								</c:choose>
								<c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
									<c:url var="pageUrl" value="/ApprovalPaymentList/${i}" />
									<c:choose>
										<c:when test="${i == currentIndex}">
											<li class="active"><a href="${pageUrl}"><c:out
														value="${i}" /></a></li>
										</c:when>
										<c:otherwise>
											<li><a href="${pageUrl}"><c:out value="${i}" /></a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
								<c:choose>
									<c:when test="${currentIndex == deploymentLog.totalPages}">
										<li class="disabled"><a href="#">&gt;</a></li>
										<li class="disabled"><a href="#">&gt;&gt;</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${nextUrl}">&gt;</a></li>
										<li><a href="${lastUrl}">&gt;&gt;</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</c:if>
					<c:if test="${empty approval}">
						<div class="main-body">
							<p class="lead text-muted text-center t-padded">
								<spring:message code="label.master.noresilts" />
							</p>
						</div>
					</c:if>
				</sec:authorize>
			</div>
		</div>
	</section>
</div>