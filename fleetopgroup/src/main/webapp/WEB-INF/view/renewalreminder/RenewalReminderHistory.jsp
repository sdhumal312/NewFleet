<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/css/dataTables/buttons.dataTables.css" />">
<script type="text/javascript"
	src="<c:url value="/resources/js/dataTables/jquery.dataTables.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/dataTables/dataTables.buttons.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/dataTables/buttons.print.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/dataTables/buttons.flash.min.js" />"></script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="row">
					<div class="pull-left">
						<a href="<c:url value="/open"/>"><spring:message code="label.master.home" /></a> / <small><a
							href="<c:url value="/RenewalReminder/1/1.in"/>">Renewal Reminders</a></small>
						 / <small>History Renewal Reminders</small>
					</div>
					<div class="pull-right">
						<sec:authorize access="hasAuthority('ADD_RENEWAL')">
							<a class="btn btn-success btm-sm" href="addRenewalReminder.in">
								<span class="fa fa-plus"></span> Add Renewal
							</a>
						</sec:authorize>
						<a class="btn btn-link" href="<c:url value="/RenewalReminder/1/1.in"/>">Cancel</a>

					</div>
				</div>
			</div>
		</div>
	</section>
	<!-- Main content -->
	<section class="content">
		<script type="text/javascript" src="<c:url value="/resources/js/DriverNewlanguage.js" />"></script>
		
		<c:if test="${param.danger eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver was Already Updated.
			</div>
		</c:if>
		<!-- alert in delete messages -->
		<c:if test="${param.deleteRenewalReminder eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Reminder History  Deleted successfully .
			</div>
		</c:if>
		
		<div class="row">
			<sec:authorize access="!hasAuthority('VIEW_RENEWAL')">
				<spring:message code="message.unauth"></spring:message>
			</sec:authorize>
			<sec:authorize access="hasAuthority('VIEW_RENEWAL')">
				<div class="main-body">
					<div class="box">
						<div class="box-header">
							<div class="pull-right">
								<div id="langSelect"></div>
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="DriverTable"
								class="table table-bordered table-striped">
								<thead>
									<tr>
										<th class="icon">ID</th>
										<th class="icon">Vehicle Name</th>
										<th class="icon">Renewal Name</th>
										<th class="icon">Receipt No</th>
										<th class="icon">Validity From</th>
										<th class="icon">Validity To</th>
										<th class="icon">Amount</th>

										<th class="icon">Download</th>
										<th class="icon">Actions</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${!empty renewalHis}">

										<c:forEach items="${renewalHis}" var="renewalHis">

											<tr data-object-id="" class="ng-scope">
												<td class="icon"><a href="" data-toggle="tip"
													data-original-title="Click this Renewal Details"><c:out
															value="${renewalHis.renewalhis_id}" /> </a></td>
												<td class="icon"><a
													href="showVehicle.in?vid=${renewalHis.vid}"
													data-toggle="tip"
													data-original-title="Click this Vehicle Details"> <span
														class="badge"> <c:out
																value="${renewalHis.vehicle_registration}" />
													</span>
												</a></td>
												<td><c:out value="${renewalHis.renewalhis_type}" />

													<ul class="list-inline no-margin">

														<li><span class="fa fa-earphone" aria-hidden="true"
															data-toggle="tipDown"
															data-original-title="Work Phone Number"> </span> <c:out
																value="${renewalHis.renewalhis_subtype}" /></li>


													</ul></td>

												<td><c:out value="${renewalHis.renewalhis_receipt}" /></td>
												<td><c:out value="${renewalHis.renewalhis_from}" /></td>

												<td><c:out value="${renewalHis.renewalhis_to}" /></td>

												<td><span class="badge"> <c:out
															value="${renewalHis.renewalhis_Amount}" />
												</span></td>
												<td>
												<c:choose>
													<c:when test="${renewalHis.renewal_document == true}">
													<sec:authorize access="hasAuthority('DOWNLOND_RENEWAL')">
														<a
															href="${pageContext.request.contextPath}/download/RenewalReminderHis/${renewalHis.renewal_document_id}.in">
															<span class="fa fa-download"></span>
														</a>
													</sec:authorize>
													</c:when>
													</c:choose></td>
												<td><sec:authorize access="hasAuthority('DELETE_RENEWAL')">
														<a
															href="deleteRenewalReminderHis.in?renewalhis_id=${renewalHis.renewalhis_id}&vid=${renewalHis.vid}">
															<span class="fa fa-trash"></span>
														</a>
													</sec:authorize></td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</sec:authorize>
		</div>
	</section>
</div>