<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="<c:url value="/open.html"/>"><spring:message
							code="label.master.home" /></a> / <a
						href="<c:url value="/newCompany.in"/>">Company</a>
				</div>
				<%-- <div class="pull-right">
						<a class="btn btn-success" href="<c:url value="/masterCompany/1.in"/>"> <span
							class="fa fa-plus"> Cancel</span>
						</a>
				</div> --%>
			</div>
		</div>
	</section>
	<section class="content">
		<div class="row">
			<div class="col-md-offset-1 col-md-9">
				<div class="main-body">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h1 id="AddVehicle">Create Sub Company Details</h1>
						</div>
						<div class="panel-body">
							<div class="form-horizontal ">
								<div class="row1" class="form-group">
									<label class="L-size control-label">SUB COMPANY NAME :<abbr title="required">*</abbr>
									</label>
									<div class="I-size">
										<input type="text" class="form-text" placeholder="Enter Sub Company Name" maxlength="250" id="subCompanyName" />
									</div>
								</div>
								<div class="row1" class="form-group">
									<label class="L-size control-label">Sub Company Type :</label>
									<div class="I-size">
										<input type="text" class="form-text" placeholder="Enter Sub Company Type" maxlength="200" id="subCompanyType"/>
									</div>
								</div>
								<!-- <div class="row" id="grpcompanyLogo" class="form-group">
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
								</div> -->

								<fieldset>
									<legend>Sub Company Address</legend>
									<div class="row1">
										<label class="L-size control-label">Address: :</label>
										<div class="I-size">
											<input type="text" class="form-text" placeholder="Enter Address" maxlength="200" id="subCompanyAddress"  />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Country :</label>
										<div class="I-size">
											<select name="company_country" class="select2 countries form-text " size="1" id="subCompanyCountry" name="region">
												<option value="" selected="selected">SELECT REGION</option>
												<option value=""></option>
											</select> 
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">State :</label>
										<div class="I-size">
											<select name="company_state" class="select2 states form-text" id="subCompanyState" size="1">
											</select> <label id="errorCITY" class="error"></label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">City :</label>
										<div class="I-size">
											<select name="company_city" class="select2 cities form-text" size="1" id="subCompanyCity">
											</select> <label id="errorCITY" class="error"></label>
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Pin code :</label>
										<div class="I-size">
											<input type="text" class="form-text" placeholder="Enter pincode" maxlength="6" id="subCompanyPinCode"
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
											<input type="text" class="form-text" value="http://" placeholder="Enter Website"
												maxlength="150" id="subCompanyWebsite" />
											<label id="errorWEBSITE" class="error"></label>
										</div>
									</div>

									<div class="row1" id="grpcompanyEmail" class="form-group">
										<label class="L-size control-label">Email :</label>
										<div class="I-size">
											<input type="email" class="form-text" placeholder="Enter Email" maxlength="100" id="subCompanyEmail" /> 
										</div>
									</div>
									<div class="row1" id="grpcompanyMobile" class="form-group">
										<label class="L-size control-label">Mobile Number :</label>
										<div class="I-size">
											<input type="text" class="form-text" placeholder="Enter Mobile" maxlength="15" id="subCompanyMobileNumber" />
										</div>
									</div>

									<div class="row1">
										<label class="L-size control-label">Sub Company TAN No :</label>
										<div class="I-size">
											<input type="text" class="form-text" placeholder="Enter TAN No" maxlength="50" id="subCompanyTanNo"  />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Sub Company PAN No :</label>
										<div class="I-size">
											<input type="text" class="form-text"  placeholder="Enter GST NO" maxlength="50" id="subCompanyPanNo" />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Sub Company GST NO :</label>
										<div class="I-size">
											<input type="text" class="form-text"  placeholder="Enter GST NO" maxlength="50" id="subCompanyTaxNo"
												onkeypress="return TAXNO(event);" ondrop="return false;" />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Sub Company TIN No :</label>
										<div class="I-size">
											<input type="text" class="form-text"  placeholder="Enter TIN No" maxlength="50" id="subCompanyTinNo"
												onkeypress="return TINNO(event);" ondrop="return false;" />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">Sub Company CIN No :</label>
										<div class="I-size">
											<input type="text" class="form-text" placeholder="Enter CIN No" maxlength="50" id="subCompanyCinNo"
												onkeypress="return cinNO(event);" ondrop="return false;" />
										</div>
									</div>
									<div class="row1">
										<label class="L-size control-label">About US :</label>
										<div class="I-size">
											<textarea class="text optional form-text" rows="4" maxlength="500" id="subCompanyAbout"> 
		                                </textarea>
										</div>
									</div>
								</fieldset>

								<div class="form-group">
									<label class="L-size control-label" for="vehicle_theft"></label>
									<div class="col-sm-5">
										<fieldset class="form-actions">
											<button type="submit" id="saveSubCompany" class="btn btn-success">Create Sub Company</button>
											<a class="btn btn-link" href="<c:url value="/newCompany.in"/>">Cancel</a>
										</fieldset>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>	
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/MA/AddSubCompany.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/MA/SubCompanyValidate.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/location.js" />"></script>