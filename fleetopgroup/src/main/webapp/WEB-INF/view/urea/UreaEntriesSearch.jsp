<%@ include file="../../taglib.jsp"%>
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/select/select2.min.AJAX.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/datepicker.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/QhyvOb0m3EjE7A4/css/datepicker/daterangepicker-bs3.css"/>">
	
<div class="content-wrapper">
	<section class="content-header">
		<div class="box">
			<div class="box-body">
				<div class="pull-left">
						<a href="open" >Home</a>/ <a href="UreaEntriesShowList.in">New Urea Entry</a>
				</div>
				<div class="pull-right">
					<a href="<c:url value="/UreaEntriesShowList.in"/>">Cancel</a>
				</div>
			</div>
		</div>
	</section>
	
	<section class="content">
		<div class="panel box box-primary">
			<div class="box-body" id="ElementDiv">
				<div class="form-horizontal ">

					<div class="row1">
						<label class="L-size control-label">UE No</label>
						<div class="I-size">
							<input type="text" class="form-text" id="ureaNumber" style="width: 100%;"  placeholder="All" />
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label">Vehicle : </label>
						<div class="I-size">
							<input type="hidden" id="vehicleId" style="width: 100%;" placeholder="ALL" />
						</div>
					</div>
					<div class="row1">
						<label class="L-size control-label">Date range: <abbr
							title="required">*</abbr></label>
						<div class="I-size">
							<div class="input-group">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" id="ureaDateRange1" class="form-text"
									name="fuelmileage_daterange" required="required" readonly="readonly"
									style="cursor: pointer; padding: 5px 5px; border: 1px solid #ccc; width: 100%">
							</div>
						</div>
					</div>
					<br />
					<div class="row1">
						<label class="L-size control-label"></label>

						<div class="I-size">
							<div class="pull-left">
								<button type="submit" id="searchUreaEntries" name="commit" class="btn btn-success"
									id="btn-save">
									<i class="fa fa-search"> Search</i>
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	
	<div class="main-body">
		<div class="box">
			<div class="box-body hide" id="ureaSearchTable" >

				<div class="table-responsive">
					<table class="table" >
					<thead>
						<tr>
							<th class="fit ar">Sr No</th>
							<th class="fit ar">UE NO</th>
							<th class="fit ar">Vehicle</th>
							<th class="fit ar">Open KM</th>
							<th class="fit ar">Close KM</th>
							<th class="fit ar">Urea Date</th>
							<th class="fit ar">Liters</th>
							<th class="fit ar">Amount</th>
							<th class="fit ar">Trip No</th>
							<th class="fit ar">Action</th>
							
						</tr>
					</thead>
					<tbody id="ureaEntriesSerachModelTable">
					
					</tbody>

					</table>
				</div>
				<div class="text-center">
					<ul id="navigationBar" class="pagination pagination-lg pager"> </ul>
				</div>
			</div>
		</div>
	</div>
	
</div>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/moment.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/daterangepickerSearch.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/select2.min.AJAX.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.date.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/inputMask/inputmask.extensions.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/datepicker/datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/QhyvOb0m3EjE7A4/js/select/lodash.min.js"/>"></script>
<script type="text/javascript" src="resources/QhyvOb0m3EjE7A4/js/fleetop/urea/UreaEntriesSearch.js"></script>				