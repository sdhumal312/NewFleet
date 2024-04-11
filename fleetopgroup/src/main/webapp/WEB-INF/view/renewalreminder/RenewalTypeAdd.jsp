<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addRenewalType.in"/>"> New Renewal Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addRenewaltypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddRenewalType"> Create
								Renewal Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.Save eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.Update eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.Delete eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyRenewalType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addRenewaltypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveRenewalType" method="post"
						onsubmit="return validateReType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="RenewalType">New Renewal Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label">Renewal Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="ReType"
										name="renewal_Type" placeholder="Enter Renewal Type" /> <label
										id="errorReType" class="error"></label>
								</div>
							</div>
							<br />
							<c:if test="${configuration.tallyIntegrationRequired}">
								<div class="row1" id="grpmanufacturer" class="form-group">
									<label class="L-size control-label" for="manufacurer">Tally Expense Head :<abbr title="required">*</abbr></label>
									<div class="I-size">
										<input type="hidden" id="tallyExpenseId" name="expenseId" style="width: 100%;" value="0"
										  placeholder="Please Enter Tally Expense Name" />
									</div>
								</div>
							</c:if>
						</div>
						
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
								<span id="Save"><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="box">
						<sec:authorize access="hasAuthority('!VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="typeTable" class="table table-striped">
										<thead>
											<tr>
												<th id="TypeName" class="icon">Type Name</th>
												<c:if test="${configuration.tallyIntegrationRequired}">
													<th id="TallyExpense" class="icon">Tally Expense Head</th>
												</c:if>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty renewalType}">
												<c:forEach items="${renewalType}" var="renewalType">
													<tr>
														<td><c:out value="${renewalType.renewal_Type}" /></td>
														<td><c:out value="${renewalType.tallyExpenseName}" /></td>
														<td><a href="#.." rel="facebox"> Renewals</a></td>
														<td>
															<div class="btn-group">
																<a class="btn btn-Link dropdown-toggle"
																	data-toggle="dropdown" href="#"> <span
																	class="fa fa-cog"></span> <span class="caret"></span>
																</a>
																<ul class="dropdown-menu pull-right">
																	<li><sec:authorize
																			access="hasAuthority('EDIT_PRIVILEGE')">
																			<a
																				href="editRenewalTypes.in?renewal_id=${renewalType.renewal_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteRenewalType.in?renewal_id=${renewalType.renewal_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete  Type ?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalType.validate.js" />"></script>
</div>