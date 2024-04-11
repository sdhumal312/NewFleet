<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartManufacturer.in"/>">New Part
							Manufacturer</a> / <span id="NewPartManufacturer">Edit Part
							Manufacturer</span></small>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addPartManufacturer.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
				<c:if test="${!empty PartManufacturer}">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Edit Part Manufacturer</h1>
							</div>
							<div class="panel-body">
								<form action="uploadPartManufacturer.in" method="post"
									onsubmit="return validateStatusUpdate()">

									<input type="hidden" value="${PartManufacturer.pmid}"
										name="pmid" />

									<div class="row">
										<label class="L-size control-label" id="Status">Name :</label>
										<div class="I-size">
											<input type="text" class="form-text"
												value="${PartManufacturer.pmName}" name="pmName"
												placeholder="Enter Part Categorie" maxlength="50"
												id="pmName" onkeypress="return IspmName(event);"
												ondrop="return false;" required="required" /> <label
												id="errorpmName" class="error"></label>
										</div>
									</div>
									<div class="row">
										<label class="L-size control-label">Description :</label>
										<div class="I-size">
											<textarea class="text optional form-text"
												name="pmdescription" rows="3" maxlength="150"
												onkeypress="return IsVendorRemark(event);"
												ondrop="return false;">${PartManufacturer.pmdescription}
				                        </textarea>
											<label id="errorvStatus" class="error"></label>
										</div>
									</div>

									<div class="form-group">
										<label class="L-size control-label" for="vehicle_theft"></label>
										<div class="col-sm-5">
											<fieldset class="form-actions">
												<input class="btn btn-info" name="commit" type="submit"
													value="Update"> <a class="btn btn-link"
													href="addPartManufacturer.in">Cancel</a>
											</fieldset>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty PartManufacturer}">
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
				<%@include file="../vehicle/masterSideMenu.jsp"%>
			</div>
		</div>
	</section>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
</div>