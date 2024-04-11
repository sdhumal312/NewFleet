<%@ include file="../../taglib.jsp"%>
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-header">
				<div class="pull-left">
					<a href="<c:url value="/open"/>">
						<spring:message code="label.master.home" />
					</a> /  <span> Labour Master</span>
				</div>
				<div class="pull-right">
					<button type="button" class="btn btn-success" data-toggle="modal"
						data-target="#addLabour" data-whatever="@mdo">
						<span class="fa fa-plus" id="AddJobType"> Add Labour </span>
					</button>
				</div>
				<div class="pull-right">
					<a class="btn btn-link" href="labour.in"> Cancel</a>
				</div>
			</div>
		</div>
	</section>
	<input type="hidden" id="companyId" value="${companyId}">
	<input type="hidden" id="userId" value="${userId}">
	<div class="content">
		<div class="modal fade" id="addLabour" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="JobType">Add Labour</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label class="L-size control-label" id="Type"> Labour Name : <abbr title="required">*</abbr>
							</label>
							<div class="I-size">
								<input type="text" class="form-text" required="required"
									maxlength="150" id="labourName"
									placeholder="Enter Labour Name" />
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
						<div class="modal-footer">
							<button type="submit" class="btn btn-primary" onclick="saveLabour();">
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
		</div>
		<div class="modal fade" id="editLabour" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
						<input type="hidden" id="editLabourId">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="JobType">Edit Labour</h4>
						</div>
						
						<div class="modal-body">
							<div class="row">
								<label class="L-size control-label" id="Type"> Labour Name :
								<abbr title="required">*</abbr>
								</label>
								<div class="I-size">
									<input type="text" class="form-text" required="required"
										maxlength="150"  id="editLabourName"
										placeholder="Enter Labour Name" />
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
							<button type="submit" class="btn btn-primary" onclick="updateLabour();" >
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
						<fieldset>
								<div class="">
									<table class="table">
										<thead>
											<tr class="breadcrumb">
												<th class="fit ar">No</th>
												<th class="fit ar">Labour Name</th>
												<th class="fit ar">Description</th>
												<th class="fit ar">Actions</th>
											</tr>
										</thead>
										<tbody id="labourTable">
										</tbody>
									</table>
								</div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js" />"></script>
	<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/labour/Labour.js"></script>
</div>