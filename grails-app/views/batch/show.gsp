<%--
  Created by IntelliJ IDEA.
  User: chirag.ghelani
  Date: 9/29/2015
  Time: 11:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
    <asset:stylesheet src="batch.css"/>

</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top nav-transparent">
    <div class="container">
        <div class="row">

            <div class="navbar-header"></div>

            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><g:link class="homeButton" action="index">Batches</g:link></li>
                    <li><g:link action="index" controller="job">Jobs</g:link></li>
                    <li><g:link class="homeButton" action="index" controller="result">Jobs</g:link></li>
                </ul>
            </div>

        </div>
    </div>
</nav>

<div class="container theme-showcase" role="main">
    <div class="row col-md-12">
        <h1 class="page-header">Batch Details</h1>
        <section>

            <div class="row col-md-4">
                <span class="from-label ">
                    <B>Batch Name:</B>
                </span>
                <span class="from-label">
                    ${batchInstance.name}
                </span>
            </div>

            <div class="row col-md-4">
                <span class="from-label ">
                    <B>File Count:</B>
                </span>
                <span class="from-label">
                    ${batchInstance.file_count}
                </span>
            </div>

            <div class="row col-md-4">
                <span class="from-label ">
                    <B>Batch Status:</B>
                </span>
                <span class="from-label">
                    ${batchInstance.status}
                </span>
            </div>

        </section>
        <section>
            <div class="row col-md-12">
                <h3 class="Sub-header">Batch Cost Details</h3>

                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>User Subscription Fields Left:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.price.user_subscription_fields}
                    </span>
                </div>

                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>Page Count:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.price.page_count}
                    </span>
                </div>


                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>The included fields in each page:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.price.user_included_fields_per_page}
                    </span>
                </div>

            </div>
        </section>
        <section>
            <div class="row col-md-12">
                <h3 class="Sub-header">Batch Readiness</h3>

                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>Correct Number Of Pages:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.readiness.valid_page_count}
                    </span>
                </div>

                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>Has Empty Documents:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.readiness.has_empty_documents}
                    </span>
                </div>

                <div class="row col-md-4">
                    <span class="from-label ">
                        <B>Has Atleast One Document:</B>
                    </span>
                    <span class="from-label">
                        ${batchInstance.readiness.has_documents}
                    </span>
                </div>

            </div>

            <div class="row col-md-12">

                <div class="col-md-6 ">
                    <div class="panel panel-warning">
                        <div class="panel panel-heading">
                            <div>Batch Warnings</div>
                        </div>

                        <div class="=panel panel-body">
                            <div class="row col-md-6">
                                <g:if test="${batchInstance.readiness.warnings.size() == 0}">
                                    <span class="from-label ">
                                        <B>No Warnings!</B>
                                    </span>
                                </g:if>
                                <g:else>
                                    <ul>
                                        <g:each in="${batchInstance.readiness.warnings}" var="warning">
                                            <li>${warning}</li>
                                        </g:each>
                                    </ul>
                                </g:else>

                            </div>
                        </div>

                    </div>

                </div>

                <div class="col-md-6">
                    <div class="panel panel-danger">
                        <div class="panel panel-heading">
                            <div class="Sub-header">Batch Errors</div>
                        </div>

                        <div class="panel-body">
                            <div >
                                <g:if test="${batchInstance.readiness.errors.size() == 0}">
                                    <span class="from-label ">
                                        <B>No Errors!</B>
                                    </span>
                                </g:if>
                                <g:else>
                                    <ul>
                                        <g:each in="${batchInstance.readiness.errors}" var="error">
                                            <li>${error}</li>
                                        </g:each>
                                    </ul>
                                </g:else>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </section>
        <section>
            <div class="row col-md-12">
                <h3 class="Sub-header">Batch Actions</h3>
                <fieldset>
                    <g:link id="${batchInstance.id}"
                            class="${batchInstance.status == 'setup' ? 'btn btn-default btn-success' :
                                    'btn btn-default btn-success disabled'}"
                            controller="batch"
                            action="start">Start Batch</g:link>
                    <g:link id="${batchInstance.id}"
                            class="${batchInstance.status == 'setup' ? 'btn btn-default btn-warning' :
                                    'btn btn-default btn-warning disabled'}"
                            controller="batch"
                            action="start">Show Job</g:link>
                </fieldset>
            </div>
        </section>
    </div>

</div>
</body>
</html>