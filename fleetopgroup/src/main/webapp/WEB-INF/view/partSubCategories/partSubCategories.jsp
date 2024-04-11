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
						<a href="open" >Home</a>/ <a href="addPartSubCategories.in">Part Sub Category Master</a>
				</div>
				<div class="pull-right">
  						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#addManufacturer" data-whatever="@mdo">
							<span class="fa fa-plus" id="AddJobType"> Add Part Sub </span>
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
					
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Create Part Sub Category
								</h4>
						</div>
						<div class="modal-body">

						<div class="row" id="grppartCategory" class="form-group">
							<label class="L-size control-label" for="partCategory">Category
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<select class="form-text" name="categoryId" style="width: 100%;"
									id="partCategory" >
									<option value=""><!-- Please select --></option>
									<c:forEach items="${PartCategories}" var="PartCategories">
										<option value="${PartCategories.pcid}"><c:out
												value="${PartCategories.pcName}" /></option>
									</c:forEach>
								</select> <input type='hidden' id='partCategoryText' name="category"
									value='' /> <span id="partCategoryIcon" class=""></span>
								<div id="partCategoryErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br/>
						<div class="row">
								<label class="L-size control-label" id="Type"> Part Sub Category Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="partSubCategory" id="partSubCategory"
										placeholder="Enter Part Sub Category Name" />
								</div>
							</div>
							
							<br/>
							
							<div class="row">
								<label class="L-size control-label" id="Type">Description :</label>
								<div class="I-size">
									<input type="text" class="form-text" id="description"
										maxlength="249" name="description"
										placeholder="Enter description" />
								</div>
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="savePartSubCategories();">
								<span><spring:message code="label.master.Save" /></span>
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
		<div class="modal fade" id="editSubCategory" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						
						<div class="modal-header">
						<input type="hidden" id="editSubCategoryId">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Part Sub Category </h4>
					</div>
						<div class="modal-body">

						<div class="row" id="grppartCategory" class="form-group">
							<label class="L-size control-label" for="partCategory">Category
								:<abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<select class="form-text" name="editPartCategory" style="width: 100%;"
									id="editPartCategory" >
									<option value=""><!-- Please select --></option>
									<c:forEach items="${PartCategories}" var="PartCategories">
										<option value="${PartCategories.pcid}"><c:out
												value="${PartCategories.pcName}" /></option>
									</c:forEach>
								</select> <input type='hidden' id='editpartCategoryText' name="editpartCategoryText"
									value='' /> <span id="partCategoryIcon" class=""></span>
								<div id="partCategoryErrorMsg" class="text-danger"></div>
							</div>
						</div>
						<br/>
						<div class="row">
								<label class="L-size control-label" id="Type"> Part Sub Category Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150" name="editTallyCompany" id="editPartSubCategory"
										placeholder="Enter Part Sub Category Name" />
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
							<button type="submit" class="btn btn-primary" onclick="updatePartSubCategory();">
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
						<table id="partSubCategoryTable" class="table table-hover table-bordered">

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
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/partSubCategories/addPartSubCategories.js"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$("#partCategory").change(function () {
			    $("#partCategoryText").val($("#partCategory").find(":selected").text());
			});
			$("#editPartCategory").change(function () {
			    $("#editpartCategoryText").val($("#editPartCategory").find(":selected").text());
			});
		});
	</script>