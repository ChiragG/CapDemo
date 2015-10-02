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

<div class="container theme-showcase" role="main">
    <div class="row">
        <div class="col-md-12 main">
            <h1 class="page-header">Job List</h1>
        </div>
        <div class="row">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>Job Id</th>
                    <th>Name</th>
                    <th>No Of Forms In Job</th>
                    <th>Created Date</th>
                    <th>Last Update Date</th>
                    <th>Status</th>
                    <th>Percentage Complete</th>
                    <th>Finished Date</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${jobList}" var="jobInstance">
                    <tr>

                        <td name="id">
                            <g:link controller="job" action="show" id="${jobInstance.id}">
                                ${jobInstance.id}
                            </g:link>
                        </td>
                        <td name="name">${fieldValue(bean: jobInstance, field: "name")}</td>
                        <td name="instance_set_count">${fieldValue(bean: jobInstance, field: "instance_set_count")}</td>
                        <td name="started">${fieldValue(bean: jobInstance, field: "started")}</td>
                        <td name="modified">${fieldValue(bean: jobInstance, field: "modified")}</td>
                        <td name="status">${fieldValue(bean: jobInstance, field: "status")}</td>
                        <td name="percent_completed">${fieldValue(bean: jobInstance, field: "percent_completed")}</td>
                        <td name="finished">${fieldValue(bean: jobInstance, field: "finished")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>