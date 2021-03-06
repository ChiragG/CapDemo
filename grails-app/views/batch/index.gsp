<%--
  Created by IntelliJ IDEA.
  User: chirag.ghelani
  Date: 9/28/2015
  Time: 7:27 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
    <asset:stylesheet src="batch.css"/>
    <title>Captricity Demo</title>
</head>

<body role="document">

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li class="active"><g:link class="homeButton" action="index">Batches</g:link></li>
                <li><g:link class="homeButton" action="index" controller="job">Jobs</g:link></li>
            </ul>
        </div>
    </div>
    <!--/.nav-collapse -->
</nav>

<div class="container theme-showcase" role="main">
    <g:render template="/message"/>
    <div class="row">
        <div class="col-md-12 main">
            <h1 class="page-header">Batch Dashboard</h1>
        </div>
    </div>

    <div class="row col-md-8">
        <h3 class="sub-header">Add New Batch</h3>
        <g:form controller="batch" action="save" method="post" enctype="multipart/form-data" autocomplete="off"
                id="${batchInstance}">
            <div class="form-group form-horizontal">
                <label for="usr">Batch Name:</label>
                <input type="text" class="form-control" id="usr" name="name" placeholder="Enter A Batch Name"/>

            </div>

            <g:actionSubmit id="${batchInstance.id}" class="btn btn-group-sm btn-primary" controller="batch"
                            action="Create"
                            value="Create Batch"/>
        </g:form>
    </div>

    <div class="row col-md-12">
        <h3 class="sub-header">Batch List</h3>
        <div class="row">
            <table class="table table-striped table-bordered table-condensed">
                <thead>
                <tr>
                    <th>Batch Id</th>
                    <th>Batch Name</th>
                    <th>File Count</th>
                    <th>Last Upload Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${results}" var="batchInstance">
                    <tr>

                        <td name="id">
                            <g:link controller="batch" action="show" id="${batchInstance.id}">
                                ${batchInstance.id}
                            </g:link>
                        </td>
                        <td name="name">${fieldValue(bean: batchInstance, field: "name")}</td>
                        <td name="file_count">${fieldValue(bean: batchInstance, field: "file_count")}</td>
                        <td name="last_upload_date">${fieldValue(bean: batchInstance, field: "last_upload_date")}</td>
                        <td name="status">${fieldValue(bean: batchInstance, field: "status")}</td>

                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>