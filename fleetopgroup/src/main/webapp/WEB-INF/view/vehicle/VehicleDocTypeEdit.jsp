<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVehicleDocType.in"/>">New Vehicle Doc
							Type</a> / <span id="NewVehicleDocType">Edit Vehicle Doc Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link btn-sm"
						href="<c:url value="/addDriverDocType.in"/>"> Cancel </a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<!-- main body in vehicle page -->
				<div class="main-body">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1 id="AddVehicle">Edit Vehicle DocType</h1>
						</div>
						<div class="panel-body">
							<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
								<c:if test="${!empty vehicleDocType}">
									<form action="updateVehDocType.in" method="post"
										onsubmit="return validateDocTypeUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Document Type:</label>
											<div class="I-size">
												<input name="vDocType" value="${vehicleDocType.vDocType}"
													id="vDocTypeUpdate" Class="form-text" /> <label
													id="errorEditDocType" class="error"></label> <input
													type="hidden" name="dtid" value="${vehicleDocType.dtid}">
											</div>
										</div>
										<div class="form-DocType">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit" onclick="return validateDocType()"
														value="Update"> <a class="btn btn-link"
														href="addVehicleDocType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</c:if>
							</sec:authorize>
							<c:if test="${empty vehicleDocType}">
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
			</div>
			<div class="col-sm-1 col-md-2">
				<%@include file="masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/DocType.validate.js" />"></script>
</div>