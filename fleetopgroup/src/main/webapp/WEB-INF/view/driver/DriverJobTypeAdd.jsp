<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<script type="text/javascript">
		$('.btn').on('click', function() {
			var $this = $(this);
			$this.button('loading');
			setTimeout(function() {
				$this.button('reset');
			});
		});
	</script>
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addDriverJobType.in"/>">Driver Job Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addDrivertypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddDriverJobType"> Create
								Driver JobType</span>
						</button>

					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveDriverJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Job Types Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateDriverJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Job Types Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteDriverJobType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Job Types Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyDriverJobType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Job Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addDrivertypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveDriJobType.in" method="post"
						onsubmit="return validateJobType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="DriverJobType">New Driver
								JobType</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="JobType">Job
									Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="dJobType"
										name="driJobType" placeholder="Enter JobType Name" /> <label
										id="errordJobType" class="error"></label>
								</div>
							</div>
							<div class="row1">
								<label class="L-size control-label">Remarks</label>
								<div class="I-size">
									<input type="text" class="form-text" id="dJobType"
										name="driJobRemarks" placeholder="Enter Remarks Name" /> <label
										id="errordJobType" class="error"></label>
								</div>
							</div>
							<br /> <br /> <br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return driverJobTypeValidate()">
								<span><spring:message code="label.master.Save" /></span>
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
						<sec:authorize access="!hasAuthority('VIEW_PRIVILEGE')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('VIEW_PRIVILEGE')">
							<div class="box-body">
								<div class="table-responsive">
									<table id="JobTypeTable" class="table  table-striped">
										<thead>
											<tr>
												<th id="JobTypeName" class="icon">Job Type Name</th>
												<th class="icon">Remarks</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty driverJobType}">
												<c:forEach items="${driverJobType}" var="driverJobType">
													<tr>
														<td><c:out value="${driverJobType.driJobType}" /></td>
														<td><c:out value="${driverJobType.driJobRemarks}" /></td>
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
																				href="editDriverJobType.in?driJobId=${driverJobType.driJobId}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteDriverJobType.in?driJobId=${driverJobType.driJobId}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Jobument Type ?')">
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
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/DriverMasterValidate.js" />"></script>
</div>