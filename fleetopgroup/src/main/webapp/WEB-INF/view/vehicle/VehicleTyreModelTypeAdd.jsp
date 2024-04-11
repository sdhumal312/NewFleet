<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addTyreModelType"/>">Tyre Manufacturers
							Type</a></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addJobtypes" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Create Tyre
								Manufacturers Type</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveTyreModelType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Manufacturers Type Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateTyreModelType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Manufacturers Type Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.deleteTyreModelType eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Manufacturers Type Deleted Successfully.
			</div>
		</c:if>
		<c:if test="${param.alreadyTyreModelType eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Tyre Manufacturers Type Already Exists.
			</div>
		</c:if>
		<div class="modal fade" id="addJobtypes" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveTyreModelType.in" method="post"
						onsubmit="return validateReType()">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">New Tyre Manufacturers
								Type</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type">Tyre
									Manufacturers Name :*</label>
								<div class="I-size">
									<input type="text" class="form-text"  required="required"
										maxlength="150" name="TYRE_MODEL" id="Maunfacturerid"
										placeholder="Enter Model Name" /> <label id="errorReType"
										class="error"></label>
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label" id="Type">Remarks :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="ReType"
										maxlength="249" name="TYRE_MODEL_DESCRITION"
										placeholder="Enter notes" /> <label id="errorReType"
										class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return tyreManufacturesValidate()">
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
									<c:if test="${!empty TyreModelType}">
										<table id="typeTable"
											class="table table-bordered table-striped">

											<thead>
												<tr>
													<th id="TypeName" class="icon">Manufacturers Name</th>
													<th id="Usage" class="icon">Remarks</th>
													<c:if test="${!isOwnType}">
														<th id="Actions" class="icon">Actions</th>
													</c:if>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${TyreModelType}" var="TyreModelType">
													<tr>
														<td><c:out value="${TyreModelType.TYRE_MODEL}" /></td>
														<td><c:out
																value="${TyreModelType.TYRE_MODEL_DESCRITION}" /></td>
														<c:if test="${!isOwnType}">
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
																				href="editTyreModelType?Id=${TyreModelType.TYRE_MT_ID}">
																				<span class="fa fa-pencil"></span> Edit
																			</a>
																		</sec:authorize></li>
																	<li><sec:authorize
																			access="hasAuthority('DELETE_PRIVILEGE')">
																			<a
																				href="deleteTyreModelType?Id=${TyreModelType.TYRE_MT_ID}"
																				class="confirmation"
																				onclick="return confirm('Are you sure you Want to delete TYRE Model ?')">
																				<span class="fa fa-trash"></span> Delete
																			</a>
																		</sec:authorize></li>
																</ul>
															</div>
														</td>
														</c:if>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</c:if>
									<c:if test="${empty TyreModelType}">
										<div class="main-body">
											<p class="lead text-muted text-center t-padded">
												<spring:message code="label.master.noresilts" />
											</p>

										</div>
									</c:if>
								</div>
							</div>
						</sec:authorize>
					</div>
				</div>
			</div>			
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
		
		<script type="text/javascript">
		
		function tyreManufacturesValidate(){
			if($("#Maunfacturerid").val() == undefined || ($("#Maunfacturerid").val()).trim() == "" ){
				showMessage('info','Please Enter Manufactures Name')
				return false;
			}
		}
		</script>
</div>