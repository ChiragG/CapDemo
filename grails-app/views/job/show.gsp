<%@ page import="capdemo.Job" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="layout" content="main"/>
    <r:require modules="bootstrap"/>
    <gvisualization:apiImport/>
    <title>Job Details</title>
</head>

<body role="document">
<nav class="navbar navbar-inverse navbar-fixed-top nav-transparent">
    <div class="container">
        <div class="row">

            <div class="navbar-header"></div>

            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li><g:link class="homeButton" controller="batch" action="index">Batches</g:link></li>
                    <li><g:link action="index" controller="job">Jobs</g:link></li>
                </ul>
            </div>

        </div>
    </div>
</nav>

<div class="container theme-showcase" role="main">
    <div class="row col-md-12">
        <h1 class="page-header">Result Analysis</h1>
        <section>
            <g:if test="${jobInstance.status == 'completed'}">
                <div class="row col-md-12">
                    <h3 class="Sub-header">Job Results Charts</h3>
                    <div class="row col-md-12 ">
                        <h4> Data Set Of Hours a Student Spent Outside School</h4>
                        <table class="table table-striped table-bordered table-condensed">
                            <thead>
                            <tr>
                                <td>
                                    Facebook Game Hours
                                </td>
                                <td>
                                    Sport Hours
                                </td>
                                <td>
                                    HomeWork Hours
                                </td>
                                <td>
                                    Phone Hours
                                </td>
                                <td>
                                    Friends Hours
                                </td>
                                <td>
                                    TV Hours
                                </td>
                                <td>
                                    Other Hours
                                </td>
                            </tr>

                            </thead>
                            <tbody>
                            <g:each in="${jobInstance.jobResults}" var="result">
                                <tr>
                                    <td>
                                        ${result.Facebook_Game_Hours}
                                    </td>
                                    <td>${result.Sport_Hours}</td>
                                    <td>${result.HomeWork_Hours}</td>
                                    <td>${result.Phone_Hours}</td>
                                    <td>${result.Friends_Hours}</td>
                                    <td>${result.TV_Hours}</td>
                                    <td>${result.Other_Hours}</td>

                                </tr>

                            </g:each>
                            </tbody>
                        </table>
                    </div>

                    <%
                        def myDailyActivitiesColumns = [['string', 'How out of School Hours Are Spent'], ['number',
                                                                                                          'Hours per Day']]
                    %>
                    <gvisualization:pieCoreChart elementId="piechart" title="How out of School Hours Are Spent"
                                                 width="${900}"
                                                 height="${300}"
                                                 columns="${myDailyActivitiesColumns}" data="${jobInstance.pieData}"/>
                    <div id="piechart" class="col-md-12 "></div>


                </div>

                <div class="row col-md-12">
                </div>
            </g:if>
            <g:else>
                <div class="jumbotron alert-success">
                    <Span class="center-block">
                        Please Wait For Job To Complete Processing To See Result Analytics For This Batch.
                    </Span>
                    <div class="progress ">
                    <div class= "${jobInstance.percent_completed == 100 ? ' progress-bar progress-bar-success' :
                            'progress-bar progress-bar-info' } "
                         role="progressbar"
                         aria-valuenow="${jobInstance.percent_completed}"
                         aria-valuemin="0" aria-valuemax="100" style="width:${jobInstance.percent_completed}%">
                        ${jobInstance.percent_completed}% Complete
                    </div>

                </div>
                    <div>
                        <g:link action="show" id="${jobInstance.id}" class="btn btn-info btn-lg ">
                            <span>Refresh </span> <span class="glyphicon glyphicon-refresh"> </span>
                        </g:link>
                        <span>Job Status: ${jobInstance.status.toUpperCase()} </span>
                    </div>

                </div>
            </g:else>

        </section>
    </div>
</div>

</body>
</html>