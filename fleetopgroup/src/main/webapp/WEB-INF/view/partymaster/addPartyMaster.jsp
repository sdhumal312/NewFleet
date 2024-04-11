<%@ include file="../../taglib.jsp"%>
<%@page import="org.springframework.security.core.authority.SimpleGrantedAuthority"%>
<%@page import="org.springframework.security.core.GrantedAuthority"%>
<%@page import="java.util.Collection"%>
<%@page import="org.springframework.web.servlet.ModelAndView"%>
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/select/select2.min.css">
<link rel="stylesheet" href="resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css">
<div class="content-wrapper">
	<section class="panel panel-success">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
					<a href="open" >Home</a>/ <a href="partyMaster.in">New Party Master</a>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" onclick="showAddPartyMaster();">
						<span class="fa fa-plus" >Add Party Master</span>
					</button>
				</div>
			</div>
		</div>
	</section>
	<section class="panel panel-success">
		<div class="row" id="searchData">
			<div id="countDiv" style="display : none;" class="col-md-4 col-sm-4 col-xs-12">
				<div class="info-box">
					<span class="info-box-icon bg-green"><i class="fa fa-cubes"></i></span>
					<div class="info-box-content">
						<span class="info-box-text" >Total Party Master</span> 
						<span id="countId" class="info-box-number"></span>
					</div>
				</div>
			</div>
		</div>
	</section>
		
	<div class="content" >
	
	<div class="modal fade" id="addPartyMaster" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >New Party Master</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="name"> Corporate Name :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="50" name="expenseName" id="corporateName"
									placeholder="Enter  Name" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Mobile Number :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="text" class="form-text" id="mobileNumber" 
									maxlength="10" name="description" onkeypress="return isNumberKey(event,this);"
									onblur="return isMobileNum(this);" placeholder="Enter Mobile Number" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Address :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="address"
									maxlength="150" name="description"
									placeholder="Enter Address" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Alternate Mobile Number :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="alternateMobileNumber"
									onkeypress="return isNumberKey(event,this);"
									maxlength="10" name="description" onblur="return isMobileNum(this);"
									placeholder="Enter Alternate Mobile Number" /> 
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Gst Number :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="gstNumber"
									maxlength="15" name="description"
									placeholder="Enter Gst Number" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Per KM Rate :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="perKMRate" value="0"
									onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									maxlength="6" name="description"
									placeholder="Enter KM Rate" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="savePartyMaster();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="editPartyMaster" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" >New Party Master</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<input type ="hidden" id="editCorporateAccountId">
							<label class="L-size control-label" id="name"> Corporate Name :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="50" name="expenseName" id="editCorporateName"
									placeholder="Enter  Name" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Mobile Number :<abbr title="required">*</abbr></label>
							<div class="I-size">
								<input type="text" class="form-text" id="editMobileNumber"
									maxlength="10" name="description" onkeypress="return isNumberKey(event,this);"
									onblur="return isMobileNum(this);" placeholder="Enter Mobile Number" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Address :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editAddress"
									maxlength="150" name="description"
									placeholder="Enter Address" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Alternate Mobile Number :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editAlternateMobileNumber"
									maxlength="10" name="description" onkeypress="return isNumberKey(event,this);"
									 placeholder="Enter Alternate Mobile Number" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Gst Number :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editGstNumber"
									maxlength="15" name="description"
									placeholder="Enter Gst Number" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Per KM Rate :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="editPerKMRate"
									maxlength="6" name="description" onkeypress="return isNumberKeyWithDecimal(event,this.id);"
									placeholder="Enter per KM Rate" />
							</div>
						</div>
						<br />
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="updatePartyMaster();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
			</div>
		</div>
	</div>
	<div class="main-body">
		<div class="box">
			<div class="box-body">

				<div class="table-responsive">
					<table id="partyMasterTable" class="table table-hover table-bordered">

					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/partymaster/addPartyMaster.js"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js" />"></script>