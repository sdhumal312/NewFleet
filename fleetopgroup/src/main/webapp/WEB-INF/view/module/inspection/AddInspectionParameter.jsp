<%@page import="java.util.HashMap"%>
<%@ include file="taglib.jsp"%>
<link rel='stylesheet' id='style'
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/photoView/photofream.css"/>"
	type='text/css' media='all' />
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.in"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addInspectionParameter.in"/>">Inspection Parameter
						</a>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addParameter" data-whatever="@mdo">
							<span class="fa fa-plus"> Create Inspection Parameter</span>
						</button>
					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.saveInspectionParameter eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Inspection Parameter Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.addInspectionParameterDocument eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Inspection Parameter Created Successfully.
			</div>
		</c:if>
		<c:if test="${param.updateDriverDocument eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Inspection Parameter Updated Successfully.
			</div>
		</c:if>
		<c:if test="${param.emptyDocument eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				 ${VMandatory}<br>
				Inspection Parameter Couldn't Update .
			</div>
		</c:if>
		<c:if test="${param.alreadyInspectionParameter eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Inspection Parameter Already Exists.
			</div>
		</c:if>
		<c:if test="${param.alreadyAssignInSheet eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				This Inspection Parameter Already Used In InspectionSheet Please Remove First.
			</div>
		</c:if>
		<!-- Modal  and create the javaScript call modal -->
		<div class="modal fade" id="addParameter" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<form action="saveInspectionParameter.in" method="post" enctype="multipart/form-data" name="vehicleType">
						
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="VehicleType">Create Parameter</h4>
						</div>
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Parameter Name
									: <abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" min="0" max="1000" class="form-text" id="parameterName"
										required="required" name="parameterName"
										placeholder="Enter Paramter Name" /> <label id="errorvType"
										class="error"></label>
								</div>
							</div>
							<div class="row">
								<label class="L-size control-label">Photo</label>
								<div class="I-size">
									<input type="file" class="form-text" id="photo" accept="image/png, image/jpeg, image/gif, image/jpg"
										name="parameterPhoto" placeholder="Select Photo" /> <label
										id="errordJobType" class="error"></label>
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="return inspectionParameterValidate()">
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
					<div class="main-body">
						<sec:authorize access="!hasAuthority('ADD_VEHICLE_INSPECTION_PARAMETER')">
							<spring:message code="message.unauth"></spring:message>
						</sec:authorize>
						<sec:authorize access="hasAuthority('ADD_VEHICLE_INSPECTION_PARAMETER')">
							<c:if test="${!empty InspectionParameter}">

								<div class="box">
									<div class="box-body">
										<table id="VehicleTable"
											class="table table-hover table-striped">
											<thead>
												<tr>
													<th>Document Name</th>
													<th>File Name</th>
													<th>Download</th>
													<sec:authorize access="hasAuthority('EDIT_INSPECTION_PARAMETER')">
													<th>Edit</th>
													</sec:authorize>
													<sec:authorize access="hasAuthority('DELETE_INSPECTION_PARAMETER')">
													<th>Remove</th>
													</sec:authorize>

												</tr>
											</thead>
											<tbody>

												<c:forEach items="${InspectionParameter}" var="InspectionParameter">


													<tr data-object-id="" class="ng-scope">

														<td><c:out
																value="${InspectionParameter.parameterName}" /></td>
																
														<td>
														<c:if test="${InspectionParameter.parameterPhotoId != null}">
														
														<c:forEach items="${ParamterPhoto}" var="ParamterPhoto">
															<c:if test="${InspectionParameter.parameterPhotoId == ParamterPhoto.id}">
															<c:out
																value="${ParamterPhoto.filename}" />
															</c:if>	
														</c:forEach>
															
														</c:if>
															
														<c:if test="${InspectionParameter.parameterPhotoId == null}">	
														<%-- <c:out value="-" /> --%>
														</c:if>
														</td>

														<td>
														<c:if test="${InspectionParameter.parameterPhotoId != null}">
														<sec:authorize
																access="hasAuthority('DOWNLOND_DRIVER')">
																<a
																	href="${pageContext.request.contextPath}/download/parameterDocument/${InspectionParameter.parameterPhotoId}.in">
																	<span class="fa fa-download"> Document</span>
																</a>
															</sec:authorize>
														</c:if>
														<c:if test="${InspectionParameter.parameterPhotoId == null}">
														</c:if>
														</td>
														
														<td>
														<sec:authorize access="hasAuthority('EDIT_INSPECTION_PARAMETER')">
																<a
																	href="editParameterDocument.in?parameterPhotoId=${InspectionParameter.parameterPhotoId}&inspectionParameterId=${InspectionParameter.inspectionParameterId}">
																	<span class="fa fa-pencil"> Edit</span>
																</a>
															</sec:authorize>
														</td>
														
														<td>
														<sec:authorize access="hasAuthority('DELETE_INSPECTION_PARAMETER')">
																<a
																	href="deleteParameterDocument.in?parameterPhotoId=${InspectionParameter.parameterPhotoId}&inspectionParameterId=${InspectionParameter.inspectionParameterId}"
																	class="confirmation"
																	onclick="return confirm('Are you sure you want to Delete this file ?')">
																	<span class="fa fa-trash"></span>
																</a>
															</sec:authorize>
														</td>

													</tr>

												</c:forEach>
											</tbody>

										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${empty InspectionParameter}">
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
	<script>
	
	function inspectionParameterValidate(){
		
		if($("#parameterName").val() == undefined || ($("#parameterName").val()).trim() == "" ){
			showMessage('info','Please Parameter Name')
			return false;
		}
	}
	
	</script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>
</div>