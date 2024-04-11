<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/jquery.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/dataTables/buttons.dataTables.css" />">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addRenewalSubType.in"/>"> New Renewal
							SubType</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">

						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addRenewalSubtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddRenewalSubType"> Create
								Renewal Sub Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveRenewalSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Sub Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateRenewalSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Sub Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteRenewalSubType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Sub Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyRenewalSubType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Renewal Sub Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addRenewalSubtypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="RenewalSubType">New Renewal
								SubType</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label"><span id="Type">Renewal
										Type</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<select class="form-text selectType" name="renewal_id"
										style="width: 100%;" id="selectReType">
										<c:forEach items="${renewalType}" var="renewalType">
											<option value="${renewalType.renewal_id}">${renewalType.renewal_Type}
											</option>
										</c:forEach>
									</select> <label id="errorReType" class="error"></label>
									<input id="renewalTypeVal" name="renewal_Type" type="hidden" />
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label"><span id="SubType">Renewal
										SubType</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="text" class="form-text" id="SubReType"
										name="renewal_SubType" placeholder="Enter RenewalSub Type" />
									<label id="errorSubReType" class="error"></label>
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label"><span id="SubType">IsMandatory
										</span><abbr title="required">*</abbr></label>
								<div class="I-size">
									<input type="checkbox" id="isMandatory" name="isMandatory" />
								</div>
							</div>
							<br /> <br /> <br />
						</div>
						<div class="modal-footer">
							<button type="submit" onclick="saveRenewalSubType()" class="btn btn-primary">
								<span id="Save"><spring:message code="label.master.Save" /></span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
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
									<table id="SubTypeTable"
										class="table table-bordered table-striped">
										<thead>
											<tr>
												<th id="TypeName" class="icon">Type Name</th>
												<th id="SubTypeName" class="icon">SubType Name</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty renewalSubType}">
												<c:forEach items="${renewalSubType}" var="renewalSubType">
													<tr>
														<td><c:out value="${renewalSubType.renewal_Type}" /></td>
														<td><c:out value="${renewalSubType.renewal_SubType}" /></td>
														<td><a href="#.." rel="facebox"> RenewalSubs</a></td>
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
																				href="editRenewalSubType.in?renewal_Subid=${renewalSubType.renewal_Subid}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteRenewalSubType.in?renewal_Subid=${renewalSubType.renewal_Subid}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Sub Type ?')">
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/jquery.dataTables.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/dataTables.buttons.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.print.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/dataTables/buttons.flash.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubTypelanguage.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/RenewalSubType.validate.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
							$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
							$('#selectReType').on('change', function() {
								$('#renewalTypeVal').val($("#selectReType option:selected").text().trim());
							});

		});
	</script>
</div>