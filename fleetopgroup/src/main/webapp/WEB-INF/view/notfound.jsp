<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="//fonts.googleapis.com/css?family=Open+Sans:300,400,600,900"
	rel="stylesheet" type="text/css">

<style type="text/css">
body {
	font-family: Circular, "Helvetica Neue", Helvetica, Arial, sans-serif;
	font-weight: 400;
	color: #565a5c;
	background-color: #f5f5f5;
	padding-top: 80px;
}

.text-error {
	color: #e3302c;
}
</style>
</head>

<body>
	<div class="container text-center">
		<p class="text-center">
			<img src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/FleetopLogo.png"/>" width="260" height="80"
				alt="Fleetop">
		</p>
		<hr>
		<div class="row col-md-11 col-sm-11 ">
			<div class="col-md-3">
				<h2 class="text-error">Oops!</h2>
				<p class="lead">We can't seem to find the page you're looking
					for.</p>
				<p>Error code: 404</p>
				<ul class="list-unstyled">
					<li>Here are some helpful links instead:</li>
					<li>.</li>
					<li><a class="btn btn-info btn-sm"
						href="javascript:history.go(-1)">Go back</a></li>
				</ul>
			</div>
			<div class="col-md-4 col-middle text-center">
				<img
					src="<c:url value="/resources/QhyvOb0m3EjE7A4/images/404-satheesh-notFound.gif" />"
					width="313" height="428" alt="Girl has dropped her ice cream. design fleetop">
			</div>
			<div class="col-md-3">
				<h2 class="text-error">Not Found</h2>

				<p class="lead">The requested URL was not found on this server.</p>

				<p>You may have mistyped the address or the page may have moved.</p>

				<p>
					<a class="btn btn-info btn-sm" href="javascript:history.go(-1)">Go
						back</a> and try again or <a class="btn" href="#">contact us </a> to
					report an issue.
				</p>
			</div>
		</div>
	</div>
</body>
</html>