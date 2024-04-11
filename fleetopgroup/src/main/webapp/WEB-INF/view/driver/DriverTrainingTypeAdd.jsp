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
						href="<c:url value="/addDriverTrainingType.in"/>">Driver
							Training Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addDrivertypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddDriverTrainingType">
								Create Driver Training Type</span>
						</button>

					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveDriverTrainingType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Training Types Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.uploadDriverTrainingType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Training Types Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteDriverTrainingType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Training Types Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyDriverTrainingType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Driver Training Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addDrivertypes" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<form action="saveDriTrainingType" method="post"
						onsubmit="return validateTrainingType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="DriverTrainingType">Driver
								Training Type</h4>
						</div>
						<div class="modal-body">
							<div class="row1">
								<label class="L-size control-label" id="TrainingType">Training
									Type</label>
								<div class="I-size">
									<input type="text" class="form-text" id="dTrainingType"
										name="dri_TrainingType" placeholder="Enter TrainingType Name" />
									<label id="errordTrainingType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary">
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
									<table id="TrainingTypeTable" class="table table-striped">
										<thead>
											<tr>
												<th id="TrainingTypeName" class="icon">Training Type
													Name</th>
												<th id="Usage" class="icon">Usage</th>
												<th id="Actions" class="icon">Actions</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${!empty driverTrainingType}">
												<c:forEach items="${driverTrainingType}"
													var="driverTrainingType">
													<tr>
														<td><c:out
																value="${driverTrainingType.dri_TrainingType}" /></td>
														<td><a href="#.." rel="facebox"> Drivers</a></td>
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
																				href="editDriverTrainingType.in?dri_id=${driverTrainingType.dri_id}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteDriverTrainingType.in?dri_id=${driverTrainingType.dri_id}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete Trainingument Type ?')">
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