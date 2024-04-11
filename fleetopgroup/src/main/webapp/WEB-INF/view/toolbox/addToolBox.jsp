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
					<a href="open" >Home</a>/ <a href="addToolBox.in">Tool Box Master</a>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal"
						data-target="#addToolBox" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType"> Add ToolBox </span>
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
						 <input type="hidden" value="${location}" id="statues">
							<span class="info-box-text" id="countId">Total Count</span> 
							<span id="totalToolBox" class="info-box-number"></span>
						</div>
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
						<h4 class="modal-title" id="JobType">Create Tool Box</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type"> ToolBox Name :
							<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required" maxlength="150" name="toolBoxName" id="toolBoxName" placeholder="Enter ToolBox Name" />
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
						<button type="submit" class="btn btn-primary" onclick="saveToolBox();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="editToolBox" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="editToolBoxId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Tool
								Box</h4>
						</div>
						
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Tool Box Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="editToolBox" id="editToolBoxName"
										placeholder="Enter ToolBox Name" />
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
							<button type="submit" class="btn btn-primary" onclick="updateToolBox();" >
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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/toolbox/addToolBox.js"></script>