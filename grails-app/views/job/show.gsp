
<%@ page import="capdemo.Job" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta name="layout" content="main"/>
	<r:require modules="bootstrap"/>
	<title>Captricity Demo</title>
</head>

<body role="document">

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="active"><g:link class="homeButton" action="index"  controller="batch">Batches</g:link></li>
				<li><g:link class="homeButton" action="index" controller="job">Jobs</g:link></li>
				<li><g:link class="homeButton" action="index" controller="result">Results</g:link></li>
			</ul>
		</div>
	</div>
	<!--/.nav-collapse -->
</nav>
</body>
</html>