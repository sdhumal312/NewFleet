<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehiclePhoType.in"/>">New Photo Type</a> /
						<span id="NewVehiclePhoType">Edit Vehicle Photo Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/addVehiclePhoType.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty vehiclePhoType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Vehicle PhoType</h1>
								</div>
								<div class="panel-body">
									<form action="updateVehPhoType.in" method="post"
										onsubmit="return validatePhoTypeUpdate()">
										<div class="row">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Photo Type :</label>
											<div class="I-size">
												<select class="form-text" id="vehiclePhotoTypeId" name="vehiclePhotoTypeId">
													<option value="${vehiclePhoType.vehiclePhotoTypeId}" selected>
														<c:choose>
															<c:when test="${vehiclePhoType.vehiclePhotoTypeId == 1}">
																<c:out value=" Vehicle Type"> Vehicle Type</c:out>
															</c:when>
															<c:otherwise><c:out value=" Vehicle Accident Type"> Vehicle Accident Type</c:out></c:otherwise>
														</c:choose>
													</option>
													<option value="1">Vehicle Type</option>
													<option value="2">Vehicle Accident Type</option>
												</select>
											</div>
										</div>
										<br>
										<div class="row">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Photo Type Name:</label>
											<div class="I-size">
												<input name="vPhoType" value="${vehiclePhoType.vPhoType}"
													id="vPhoTypeUpdate" Class="form-text" /> <label
													id="errorEditPhoType" class="error"></label> <input
													type="hidden" name="ptid" value="${vehiclePhoType.ptid}">
											</div>
										</div>
										<div class="form-PhoType">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit" onclick="return validatePhotoType()"
														value="Update"> <a class="btn btn-link"
														href="addVehiclePhoType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty vehiclePhoType}">
					<div class="callout callout-danger">
						<h4>Warning!</h4>
						<p>
							The page no data to Show.., Please Don't Change any URL ID or
							Number.. <br> Don't Enter any Extra worlds in URL..
						</p>
					</div>
				</c:if>
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/PhoType.validate.js" />"></script>
</div>