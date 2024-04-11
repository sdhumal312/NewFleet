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
						<a href="open" >Home</a>/ <a href="viewCommentType.in">Comment Type Master</a>
				</div>
				<div class="pull-right">
  						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Add Comment Type </span>
						</button>
				</div>
			</div>
		</div>
	</section>
		
	<div class="content" >
	
	<div class="modal fade" id="addManufacturer" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<input type="hidden"  id="accessToken" value="${accessToken }">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Comment Type 
								</h4>
						</div>
					<div class="modal-body">
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type"> Comment
								Type Name : <abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="150" name="commentTypeName" id="commentTypeName"
									placeholder="Enter Comment Type Name" />
							</div>
						</div>
						<br />
						<div class="row">
							<label class="L-size control-label" id="Type">Description
								:</label>
							<div class="I-size">
								<input type="text" class="form-text" id="description"
									maxlength="249" name="description"
									placeholder="Enter description" />
							</div>
						</div>

					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							onclick="saveCommentType();">
							<span><spring:message code="label.master.Save" /></span>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span id="Close"><spring:message code="label.master.Close" /></span>
						</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="editCommentType" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						
						<div class="modal-header">
						<input type="hidden" id="editCommentTypeId">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Comment Type </h4>
					</div>
						<div class="modal-body">

						
						<br/>
						<div class="row">
								<label class="L-size control-label" id="Type"> Comment Type Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="editCommentTypeName" id="editCommentTypeName"
										placeholder="Enter Comment Type Name" />
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
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="updateCommentType();">
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
						<table id="commentTypeDetailsTable" class="table table-hover table-bordered">

						</table>
					</div>
				</div>
			</div>
		</div>
		<br>
		<div class="text-center">
						<ul id="navigationBar" class="pagination pagination-lg pager">
							
						</ul>
					</div>

	</div>
</div>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/select2.full.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/commentType/commentType.js"></script>