<%@ include file="taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/addTyreModelType"/>">New Tyre
						Manufacturers Type</a> / <span id="NewJobType">Edit
						Manufacturers Model</span>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="addTyreModelType.in">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-xs-9">
				<sec:authorize access="hasAuthority('EDIT_PRIVILEGE')">
					<c:if test="${!empty TyreModelType}">
						<div class="main-body">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h1 id="AddJob">Edit Tyre Manufacturers Type</h1>
								</div>
								<div class="panel-body">
									<form action="updateTyreModelType.in" method="post"
										onsubmit="return validateReTypeUpdate()">
										<div class="row">
											<label class="L-size control-label" id="Type">Tyre
												Manufacturers Name :</label>
											<div class="I-size">
												<input type="hidden" class="form-text" name="TYRE_MT_ID"
													value="${TyreModelType.TYRE_MT_ID}" /> <input type="text"
													class="form-text" id="ReType" required="required"
													maxlength="150" name="TYRE_MODEL"
													value="${TyreModelType.TYRE_MODEL}"
													placeholder="Enter Model Name" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										<div class="row">
											<label class="L-size control-label" id="Type">Remarks
												:</label>
											<div class="I-size">
												<input type="text" class="form-text" id="ReType"
													name="TYRE_MODEL_DESCRITION" maxlength="250"
													value="${TyreModelType.TYRE_MODEL_DESCRITION}"
													placeholder="Enter notes" /> <label id="errorReType"
													class="error"></label>
											</div>
										</div>
										<div class="form-group">
											<label class="L-size control-label" for="Job_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<input class="btn btn-info" name="commit" type="submit"
														value="Update"> <a class="btn btn-link"
														href="addTyreModelType.in">Cancel</a>
												</fieldset>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</c:if>
				</sec:authorize>
				<c:if test="${empty TyreModelType}">
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
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/JobTypeValidate.js" />"></script>
</div>