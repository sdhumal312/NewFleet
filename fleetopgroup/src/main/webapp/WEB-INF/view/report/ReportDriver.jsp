<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<link rel="stylesheet" href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/getDriversList"/>">Driver</a> / <span>Search
						Report</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/getDriversList"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Driver Search</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="DriverReport.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
												<div class="row1">
													<label class="L-size control-label">Driver Name :</label>
													<div class="I-size">
														<input type="hidden" id="VehicleTODriverFuel"
														name="driver_id" value="0" style="width: 100%;" required="required"
														placeholder="Please Enter 3 or more Driver Name" />
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Job Title :</label>
													<div class="I-size">
														<select class="select2" name="driJobId"
															style="width: 100%">
															<option value=""><!-- Search Job Title --></option>
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}">
																	<c:out value="${driverJobType.driJobType}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Group :</label>
													<div class="I-size">
														<select class="form-control select2" name="vehicleGroupId"
															style="width: 100%" id="vehicleGroupId">
															<option value=""></option>
															<c:forEach items="${vehiclegroup}" var="vehiclegroup">
																<option value="${vehiclegroup.gid}">
																	<c:out value="${vehiclegroup.vGroup}" />
																</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Driver Status :</label>
													<div class="I-size">
														<select class=" select2" name="driverStatusId"
															style="width: 100%">
															<option value=""></option>
															<option value="1">ACTIVE</option>
															<option value="2">INACTIVE</option>
															<option value="3">TRIPROUTE</option>
															<option value="6">SUSPEND</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">License Class :</label>
													<div class="I-size">
														<input class="form-text" id="driver_dlclass"
															name="driver_dlclass" type="text">
													</div>
												</div>
												<div class="row1">
													<label class="string required L-size control-label">Languages
														: </label>
													<div class="I-size">
														<select class=" select2" name="driver_languages"
															multiple="multiple" data-placeholder="Select a languages"
															style="width: 100%;">
															<option>English</option>
															<option>Hindi</option>
															<option>Tamil</option>
															<option>Kannada</option>
															<option>Telugu</option>
															<option>Marathi</option>
															<option>Malayalam</option>
															<option>Odia</option>
															<option>Gujarati</option>
															<option>Punjabi</option>
														</select>
													</div>
												</div>
												<div class="row1">
													<label class="L-size control-label">Training /
														Specialization:</label>
													<div class="I-size">
														<select class=" select2" name="driver_trainings"
															multiple="multiple"
															data-placeholder="Select a Certificate Max 3"
															style="width: 100%;">
															<c:forEach items="${driverTrainingType}"
																var="driverTrainingType">
																<option value="${driverTrainingType.dri_TrainingType}"><c:out
																		value="${driverTrainingType.dri_TrainingType}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="this.style.visibility = 'hidden'"
																	name="commit" type="submit" value="Search All">
															<a href="<c:url value="/getDriversList"/>" class="btn btn-info">
																<span id="Can">Cancel</span>
															</a>
														</div>
													</div>
												</div>
											</fieldset>
										</div>
									</form>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
			</div>
		</sec:authorize>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#VehicleTODriverFuel").select2({minimumInputLength:3,minimumResultsForSearch:10,ajax:{url:"getDriverALLListOfCompany.in",dataType:"json",type:"POST",contentType:"application/json",quietMillis:50,data:function(e){return{term:e}},results:function(e){return{results:$.map(e,function(e){return{text:e.driver_empnumber+" - "+e.driver_firstname+" "+e.driver_Lastname+" - "+e.driver_fathername,slug:e.slug,id:e.driver_id}})}}}})
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>