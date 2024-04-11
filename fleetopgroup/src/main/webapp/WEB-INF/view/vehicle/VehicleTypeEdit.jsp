<%@ include file="taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css" />">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleTypes/1?id=${user.id}"/>"><spring:message
								code="label.master.NewVehicletype" /></a></small> / <span
						id="NewVehicleType">Edit Vehicle Type</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/addVehicleTypes/1?id=${user.id}"/>"> <spring:message
							code="label.master.Cancel" />
					</a>
				</div>
			</div>
		</div>
	</section>

	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<div class="main-body">
					<div class="panel panel-default">
						<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
							<c:if test="${!empty vehicletype}">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Vehicle Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateVehType.in" method="post"
										onsubmit="return validateTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Statues:</label>
											<div class="I-size">
												<input name="userID" type="hidden" value="${user.id}"
													required="required" /> <input name="lastModifiedBy"
													type="hidden" value="${user.email}" required="required" />
												<input name="vtype" value="${vehicletype.vtype}"
													id="vTypeUpdate" Class="form-text" /> <label
													id="errorEditType" class="error"></label> <input
													type="hidden" name="tid" value="${vehicletype.tid}">
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label" id="Type">Maximum Allowed Odometer :</label>
											<div class="I-size">
												<input
													type="number" class="form-text" id="maxAllowedOdometer" name="maxAllowedOdometer"
													placeholder="Enter Type Name" value="${vehicletype.maxAllowedOdometer}" /> <label id="errorvType"
													class="error"></label>
											</div>
										</div>
										<%-- <div class="row1" id="grprenewalType" class="form-group">
													<label class="L-size control-label" for="from">Service
														Program <abbr title="required">*</abbr>
													</label>
													<input type="hidden" id="typeCompanyId" value="${vehicletype.companyId}">
													<div class="I-size">
													 <input type="hidden" id="serviceId" value="${vehicletype.serviceProgramId }">
													 <input type="hidden" id="serviceName" value="${vehicletype.programName }">
														<input type="hidden" id="serviceProgramId" name="serviceProgramId"
															style="width: 100%;"
															placeholder="Please Enter 2 or more Job Name" />
													</div>
										</div> --%>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update" onclick="return validateVehicleTypeEdit()"> <a class="btn btn-link"
														href="<c:url value="/addVehicleTypes/1?id=${user.id}"/>">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</c:if>
						</sec:authorize>
						<c:if test="${empty vehicletype}">
							<div class="callout callout-danger">
								<h4>Warning!</h4>
								<p>
									The page no data to Show.., Please Don't Change any URL ID or
									Number.. <br> Don't Enter any Extra worlds in URL..
								</p>
							</div>
						</c:if>
					</div>
				</div>
			</div>
			<div class="col-sm-1 col-md-2">
				<div id="langSelect"></div>
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>		
		<script type="text/javascript"
			src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Type.validate.js" />"></script>
		<script>
			$(document).ready(function() {
				  $('#serviceProgramId').select2('data', {
		     			id : $('#serviceId').val(),
		     			text : $('#serviceName').val()
		     	  });
			});	  
			if(Number($('#typeCompanyId').val()) == 0){
				 $("#vTypeUpdate").attr('readonly','readonly');
				 $("#maxAllowedOdometer").attr('readonly','readonly');
			}
		</script>	
	</section>
</div>