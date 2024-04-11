<%@ include file="taglib.jsp"%>
<style>
/* layout.css Style */
#filedrag {
	border-width: 2px;
	margin-bottom: 20px;
	display: none;
	font-weight: bold;
	text-align: center;
	padding: 1em 0;
	margin: 1em 0;
	color: #555;
	border: 2px dashed #555;
	border-radius: 7px;
	cursor: default;
}

}
#filedrag.hover {
	color: #f00;
	border-color: #f00;
	border-style: solid;
	box-shadow: inset 0 3px 4px #888;
}

#messages {
	padding: 0 10px;
	margin: 1em 0;
	border: 0px solid #999;
}

#progress p {
	display: block;
	width: 240px;
	padding: 2px 5px;
	margin: 2px 0;
	border: 1px inset #446;
	border-radius: 5px;
	background: #eee url("progress.png") 100% 0 repeat-y;
}

#progress p.success {
	background: #0c0 none 0 0 no-repeat;
}

#progress p.failed {
	background: #c00 none 0 0 no-repeat;
}
</style>

<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/TS/TripValidate.js" />"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.css" />">
<script type="text/javascript"
	src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js" />"></script>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/masterCompany/1.in"/>">New Company</a>
				</div>
				<div class="pull-right">
						<a class="btn btn-success" href="<c:url value="/masterCompany/1.in"/>"> <span
							class="fa fa-plus"> Cancel</span>
						</a>
				</div>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<sec:authorize access="hasAuthority('ADD_COMPANY')">
					<div class="main-body">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h1 id="AddVehicle">Create Company Details</h1>
							</div>
							<div class="panel-body">
								<form id="formCreateCompany"
									action="<c:url value="/saveMasterCompany.in"/>" method="post"
									enctype="multipart/form-data" name="formCreateCompany"
									role="form" class="form-horizontal">

									<div class="form-horizontal ">

										<div class="row1" id="grpcompanyName" class="form-group">
											<label class="L-size control-label">COMPANY
												NAME :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="company_name"
													placeholder="Enter company_name" maxlength="250"
													id="companyName" onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="companyNameIcon"
													class=""></span>
												<div id="companyNameErrorMsg" class="text-danger"></div>
												<label id="errorRouteName" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpcompanyType" class="form-group">
											<label class="L-size control-label">Company Type :<abbr
												title="required">*</abbr></label>
											<div class="I-size">
												<input type="text" class="form-text" name="company_type"
													placeholder="Enter ComType" maxlength="200"
													id="companyType" onkeypress="return ComType(event);"
													ondrop="return false;" /><span id="companyTypeIcon"
													class=""></span>
												<div id="companyTypeErrorMsg" class="text-danger"></div>
												<label id="errorComType" class="error"></label>
											</div>
										</div>
										<div class="row1" id="grpcompanyCode" class="form-group">
											<label class="L-size control-label">Company
												Login Code :<abbr title="required">*</abbr>
											</label>
											<div class="I-size">
												<input type="text" class="form-text" name="companyCode"
													placeholder="Enter Company Code" maxlength="20"
													id="companyCode" onkeypress="return RouteName(event);"
													ondrop="return false;" /><span id="companyCodeIcon"
													class=""></span>
												<div id="companyCodeErrorMsg" class="text-danger"></div>
												<label id="errorCompanyCode" class="error"></label>
											</div>
										</div>
										<div class="row" id="grpcompanyLogo" class="form-group">
											<div class="form-group">

												<label class="col-md-3">Logo <abbr title="required">*</abbr></label>
												<div class="col-md-6">
													<input type="file" name="fileUpload" accept="image/*"
														id="fileselect"></input> <span id="companyLogoIcon"
														class=""></span>
													<div id="companyLogoErrorMsg" class="text-danger"></div>
												</div>

											</div>
											<div id="messages"></div>
										</div>

										<fieldset>
											<legend>Company Address Details</legend>
											<div class="row1">
												<label class="L-size control-label">Address: :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_address"
														placeholder="Enter ADDRESS" maxlength="200" id="pcName"
														onkeypress="return ADDRESS(event);" ondrop="return false;" />
													<label id="errorRouteNo" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Country :</label>
												<div class="I-size">
													<select name="company_country"
														class="select2 countries form-text " size="1"
														name="region">
														<option value="" selected="selected">SELECT
															REGION</option>

														<option value=""></option>
													</select> <label id="errorCOUNTRY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">State :</label>
												<div class="I-size">

													<select name="company_state"
														class="select2 states form-text" id="stateId" size="1">
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">City :</label>
												<div class="I-size">
													<select name="company_city"
														class="select2 cities form-text" size="1" id="cityId">
													</select> <label id="errorCITY" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Pin code :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pincode"
														placeholder="Enter pincode" maxlength="6" id="pcName"
														onkeypress="return Pincode(event);" ondrop="return false;" />
													<label id="errorPincode" class="error"></label>
												</div>
											</div>
										</fieldset>
										<fieldset>
											<legend>Company </legend>
											<div class="row1">
												<label class="L-size control-label">WebSite :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_website"
														value="http://" placeholder="Enter WEBSITE"
														maxlength="150" id="pcName"
														onkeypress="return WEBSITE(event);" ondrop="return false;" />
													<label id="errorWEBSITE" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyEmail" class="form-group">
												<label class="L-size control-label">Email :</label>
												<div class="I-size">
													<input type="email" class="form-text" name="company_email"
														placeholder="Enter Email" maxlength="100"
														id="companyEmail" onkeypress="return Email(event);"
														ondrop="return false;" /> <span id="companyEmailIcon"
														class=""></span>
													<div id="companyEmailErrorMsg" class="text-danger"></div>
													<label id="errorEmail" class="error"></label>
												</div>
											</div>

											<div class="row1" id="grpcompanyMobile" class="form-group">
												<label class="L-size control-label">Mobile Number :</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="company_mobilenumber" placeholder="Enter mobile"
														maxlength="15" id="companyMobile"
														onkeypress="return mobile(event);" ondrop="return false;" />
													<span id="companyMobileIcon" class=""></span>
													<div id="companyMobileErrorMsg" class="text-danger"></div>
													<label id="errormobile" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company ESI
													Calculation Days :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<input type="text" class="form-text"
														name="company_esi_pf_days" required="required"
														placeholder="Enter number of days" maxlength="50"
														id="pcName" onkeypress="return TANNO(event);"
														ondrop="return false;" /> <label id="errorTANNO"
														class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company Extra
													Active :<abbr title="required">*</abbr>
												</label>
												<div class="I-size">
													<select class="form-text" name="company_esi_pf_disable"
														required="required">
														<option value="0">ON</option>
														<option value="1">OFF</option>
													</select>
												</div>
											</div>

											<div class="row1">
												<label class="L-size control-label">Company TAN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tan_no"
														placeholder="Enter TAN no" maxlength="50" id="pcName"
														onkeypress="return TANNO(event);" ondrop="return false;" />
													<label id="errorTANNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company PAN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_pan_no"
														placeholder="Enter GST NO" maxlength="50" id="pcName"
														onkeypress="return TAXNO(event);" ondrop="return false;" />
													<label id="errorTAXNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company GST NO :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tax_no"
														placeholder="Enter GST NO" maxlength="50" id="pcName"
														onkeypress="return TAXNO(event);" ondrop="return false;" />
													<label id="errorTAXNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company TIN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_tin_no"
														placeholder="Enter tin no" maxlength="50" id="pcName"
														onkeypress="return TINNO(event);" ondrop="return false;" />
													<label id="errorTINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">Company CIN No :</label>
												<div class="I-size">
													<input type="text" class="form-text" name="company_cin_no"
														placeholder="Enter cin no" maxlength="50" id="pcName"
														onkeypress="return cinNO(event);" ondrop="return false;" />
													<label id="errorCINNO" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<label class="L-size control-label">About US :</label>
												<div class="I-size">
													<textarea class="text optional form-text"
														name="company_abount" rows="4" maxlength="500"
														onkeypress="return RouteRemarks(event);"
														ondrop="return false;"> 
				                                </textarea>
													<label id="errorRouteRemarks" class="error"></label>
												</div>
											</div>
											<div class="row1">
												<input type="hidden" class="form-text" name="company_status"
													value="MAINCOMPANY" required="required" /> <input
													type="hidden" class="form-text" name="createdBy"
													value="${createdBy}" required="required" />
											</div>

										</fieldset>

										<div class="form-group">
											<label class="L-size control-label" for="vehicle_theft"></label>
											<div class="col-sm-5">
												<fieldset class="form-actions">
													<button type="submit" class="btn btn-success">Create
														Company</button>
													<a class="btn btn-link"
														href="<c:url value="/masterCompany/1.in"/>">Cancel</a>
												</fieldset>
											</div>
										</div>

									</div>
								</form>
							</div>
						</div>
					</div>
				</sec:authorize>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		(function() {

			// getElementById
			function $id(id) {
				return document.getElementById(id);
			}

			// output information
			function Output(msg) {
				var m = $id("messages");
				//m.innerHTML = msg + m.innerHTML;
				$(m).html(msg);
			}

			// file drag hover
			function FileDragHover(e) {
				e.stopPropagation();
				e.preventDefault();
				e.target.className = (e.type == "dragover" ? "hover" : "");
			}

			// file selection
			function FileSelectHandler(e) {

				// cancel event and hover styling
				FileDragHover(e);

				// fetch FileList object
				var files = e.target.files || e.dataTransfer.files;

				// process all File objects
				for (var i = 0, f; f = files[i]; i++) {
					ParseFile(f);
				}

			}

			// output file information
			function ParseFile(file) {
				Output("File information: <strong>" + file.name
						+ "</strong> size: <strong>" + file.size
						+ "</strong> bytes");

			}
			// initialize
			function Init() {

				var fileselect = $id("fileselect"), filedrag = $id("filedrag"), submitbutton = $id("submitbutton");

				// file select
				fileselect.addEventListener("change", FileSelectHandler, false);

				// is XHR2 available?
				var xhr = new XMLHttpRequest();
				if (xhr.upload) {
					// file drop
					filedrag.addEventListener("dragover", FileDragHover, false);
					filedrag
							.addEventListener("dragleave", FileDragHover, false);
					filedrag.addEventListener("drop", FileSelectHandler, false);
					filedrag.style.display = "block";

					// remove submit button
					submitbutton.style.display = "none";
				}

			}

			// call initialization file
			if (window.File && window.FileList && window.FileReader) {
				Init();
			}

		})();

		$("#company_country")
				.click(
						function() {
							$
									.getJSON(
											"getCountryList.in",
											{
												ajax : "true"
											},
											function(e) {
												for (var n = '<option value="0">Please Select</option>', t = e.length, r = 0; t > r; r++)
													n += '<option value="' + e[r].country_name + '">'
															+ e[r].country_name
															+ "</option>";
												n += "</option>", $("#to" + o)
														.html(n)
											})
						})
	</script>

	<script type="text/javascript"
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/MA/CompanyValidate.js" />"></script>
	<script
		src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>
	<!-- <script type="text/javascript">
			setRegions(this);
	</script> -->

</div>