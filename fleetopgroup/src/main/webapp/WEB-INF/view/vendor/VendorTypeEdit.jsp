<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addVendorType.in"/>"> New Vendor Type</a> / <span
						id="NewVehicleSt">Edit Vendor Type</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addVendorType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty vendorType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Vendor Tyre</h1>
								</div>
								<div class="panel-body">
									<form action="updateVendorType.in" method="post"
										onsubmit="return validateStatusUpdate()">
										<div class="row1">
											<label class="L-size control-label" for="vehicle_theft">Edit
												Name :</label>
											<div class="I-size">
												<input name="vendor_TypeName"
													value="${vendorType.vendor_TypeName}" id="vStatusUpdate"
													Class="form-text" /> <label id="errorEditStatus"
													class="error"></label> <input type="hidden"
													name="vendor_Typeid" value="${vendorType.vendor_Typeid}">
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addVendorType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty vendorType}">
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
				<%-- <%@include file="masterSideMenu.jsp"%> --%>
			</div>
		</div>
	</section>
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/status.validate.js" />"></script>
</div>