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
					<a href="open" >Home</a>/ <a href="addToolBox.in">Vehicle Toll Master</a>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal"
						data-target="#addToolBox" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType"> Add Vehicle Toll Details </span>
					</button>
				</div>
			</div>
		</div>
	</section>
	
		
	<div class="content" >
	
	<div class="modal fade" id="addToolBox" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Add Toll Details </h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type"> Customer Id :
							<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text"  class="form-text" required="required" maxlength="150" name="customerId" id="customerId" placeholder="Enter customerId" />
							</div>
						</div>
						<br/>
						<div class="row">
							<label class="L-size control-label" id="Type"> Wallet Id :
							<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text"  class="form-text" required="required" maxlength="150" name="walletId" id="walletId" placeholder="Enter Wallet Id" />
							</div>
						</div>
						<br/>
						
						<div class="row">
							<label class="L-size control-label" id="Type">Description :</label>
							<div class="I-size">
								<input type="text" class="form-text" id="description" maxlength="249" name="description" placeholder="Enter description" />
							</div>
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" onclick="saveTollDetails();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="editVehicleTollDetails" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="editVehicleTollDetailsId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Toll Details</h4>
						</div>
						
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Customer Id :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="editCustomerId" id="editCustomerId"
										placeholder="Enter ToolBox Name" />
								</div>
							</div>
							<br/>
						<div class="row">
							<label class="L-size control-label" id="Type"> Wallet Id :
							<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text"  class="form-text" required="required" maxlength="150" name="editWalletId" id="editWalletId" placeholder="Enter Wallet Id" />
							</div>
						</div>
						<br/>
							<div class="row">
								<label class="L-size control-label" id="Type">Description :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="editDescription"
										maxlength="249" name="editDescription"
										placeholder="Enter description" />
								</div>
							</div>
							<br />
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateVehicleToll();" >
								<span>Update</span>
							</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<span id="Close"><spring:message
										code="label.master.Close" /></span>
							</button>
						</div>
				</div>
			</div>
		</div>


		<div class="main-body">
			<div class="box">
				<div class="box-body">

					<div class="table-responsive">
						<table id="VendorPaymentTable" class="table table-hover table-bordered">

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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/module/vehicle/addVehicleToll.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/genericfunctions.js"></script>