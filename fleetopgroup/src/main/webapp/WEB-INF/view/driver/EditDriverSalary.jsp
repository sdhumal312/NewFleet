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
						href="<c:url value="/getDriversList"/>">Driver</a> / <span>Edit Salary</span>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/getDriversList"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<c:if test="${param.salaryUpdateError eq true}">
			<div class="alert alert-danger">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Some Error Occured Salary Details Not Updated ! .
			</div>
		</c:if>
		<c:if test="${param.salaryUpdated eq true}">
			<div class="alert alert-success">
				<button class="close" data-dismiss="alert" type="button">x</button>
				Salary updated successfully !.
			</div>
		</c:if>
		<sec:authorize access="!hasAuthority('VIEW_DRIVER')">
			<spring:message code="message.unauth"></spring:message>
		</sec:authorize>
		<sec:authorize access="hasAuthority('VIEW_DRIVER')">
			<div class="row">
				<div class="col-md-offset-1 col-md-9">
					<fieldset>
						<legend>Edit Salary Details</legend>
						<div class="row">
							<div class="box box-success">
								<div class="box-header"></div>
								<div class="box-body no-padding">
									<form action="saveDriverSalaryEdit.in" method="post">
										<div class="form-horizontal ">
											<fieldset>
											<div class="row1">
													<label class="L-size control-label" id="VehicleGroup">
														Group :</label>
													<div class="I-size">
														<select class="form-control select2" name="vehicleGroupId"
															style="width: 100%" id="vehicleGroupId" required="required">
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
													<label class="L-size control-label">Job Title :</label>
													<div class="I-size">
														<select class="select2" name="driJobId"
															style="width: 100%" required="required" id="driJobId" >
															<option value=""><!-- Search Job Title --></option>
															<c:forEach items="${driverJobType}" var="driverJobType">
																<option value="${driverJobType.driJobId}">
																	<c:out value="${driverJobType.driJobType}" /></option>
															</c:forEach>
														</select>
													</div>
												</div>
												
											<div class="row1">
												<div class="form-group">
													<label class="L-size control-label">Per day Salary:
														<abbr title="required">*</abbr>
													</label>
													<div class="I-size">   <!--onkeypress="return IsNumericMob(event);"  Original  -->
														<input type="text" class="form-text"
															name="driver_perdaySalary"
															ondrop="return false;" value="" maxlength="5" required="required" id="driver_perdaySalary"
															onkeypress="return isNumberKeyWithDecimal(event,this.id)"
															onpaste="return false"
															> <label
															class="error" id="errorMob" style="display: none">
														</label>
													</div>
												</div>
											</div>
												
											</fieldset>
											<fieldset class="form-actions">
												<div class="row1">
													<label class="L-size control-label"></label>
													<div class="I-size">
														<div class="pull-left">
															<input class="btn btn-success"
																	onclick="validateEditInput();"
																	name="commit" type="submit" value="Edit">
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/DR/editDriversalary.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script> 
		
	<script type="text/javascript">
		$(document).ready(function() {
			$(".select2").select2();
			$("#VehicleTODriverFuel").select2({minimumInputLength:3,minimumResultsForSearch:10,ajax:{url:"getDriverALLList.in",dataType:"json",type:"POST",contentType:"application/json",quietMillis:50,data:function(e){return{term:e}},results:function(e){return{results:$.map(e,function(e){return{text:e.driver_empnumber+" - "+e.driver_firstname,slug:e.slug,id:e.driver_id}})}}}})
		});
	</script>
	<script src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
</div>