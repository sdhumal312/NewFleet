<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <small><a
						href="<c:url value="/addPartCategories.in"/>">New Part
							Categories</a> / <span id="NewPartCategories">Edit Part
							Categories</span></small>
				</div>
				<div class="pull-right">
					<sec:authorize access="hasAuthority('ADD_PRIVILEGE')">
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addPartCategories" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddPartCategories"> Add Part
								Categories</span>
						</button>

					</sec:authorize>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty PartCategories}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddVehicle">Edit Part Categories</h1>
								</div>
								<div class="panel-body">
									<form action="uploadPartCategories.in" method="post"
										onsubmit="return validateStatusUpdate()">

										<input type="hidden" value="${PartCategories.pcid}"
											name="pcid" />
										<input type="hidden" id="IncissueCategory" value="${PartCategories.incPartIssueCateory}">
										<div class="row1">
											<label class="L-size control-label" id="Status">Name
												:</label>
											<div class="I-size">
												<input type="text" class="form-text"
													value="${PartCategories.pcName}" name="pcName"
													placeholder="Enter Part Categorie" maxlength="50"
													id="pcName" onkeypress="return IspcName(event);"
													ondrop="return false;" required="required" /> <label
													id="errorpcName" class="error"></label>
											</div>
										</div>
										<div class="row1">
											<label class="L-size control-label">Description :</label>
											<div class="I-size">
												<textarea class="text optional form-text"
													name="pcdescription" rows="3" maxlength="150"
													onkeypress="return IsVendorRemark(event);"
													ondrop="return false;">${PartCategories.pcdescription}
				                        </textarea>
												<label id="errorvStatus" class="error"></label>
											</div>
										</div>
										
										<c:if test="${configuration.inIssueCategory}">
										<div class="row1">
											<label class="L-size control-label">Include In Issues Category</label>
											<div class="I-size">
												<input type="checkbox" id="inIssueCat" name="incPartIssueCateory">
											</div>
										</div>
										</c:if>
										
										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														onclick="return validatePartName()" value="Update"> <a class="btn btn-link"
														href="addPartCategories.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty PartCategories}">
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
	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/Parts.validate.js" />"></script>
</div>
<!-- /.content-wrapper -->