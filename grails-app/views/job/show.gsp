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
                    <li><g:link class="homeButton" action="index">Batches</g:link></li>
                    <li><g:link action="index" controller="job">Jobs</g:link></li>
                </ul>
            </div>

        </div>
    </div>
</nav>

<div class="container theme-showcase" role="main">
    <div class="row col-md-12">
        <h1 class="page-header">Job Details</h1>
        <section>
            <div class="row col-md-12">
                <span class="from-label col-md-3">
                    <B>Percentage Complete :</B>
                </span>
                <span class="col-md-9">
                    <div class="progress ">
                        <div class="${jobInstance.percent_completed == 100 ? ' progress-bar progress-bar-success' :
                                'progress-bar progress-bar-info'} "
                             role="progressbar"
                             aria-valuenow="${jobInstance.percent_completed}"
                             aria-valuemin="0" aria-valuemax="100" style="width:${jobInstance.percent_completed}%">
                            ${jobInstance.percent_completed}% Complete
                        </div>
                    </div>

                </span>
            </div>

            <div class="row col-md-12">
                <%
                    def myDailyActivitiesColumns = [['string', 'How out of School Hours Are Spent'], ['number',
                                                                                              'Hours per Day']]
                %>

                <gvisualization:pieCoreChart elementId="piechart" title="How out of School Hours Are Spent" width="${900}"
                                             height="${300}"
                                             columns="${myDailyActivitiesColumns}" data="${jobInstance.pieData}"/>
                <div id="piechart" class="col-md-12 "></div>
            </div>

            <div class="row col-md-12">
                <table class="table table-striped table-bordered table-condensed">
                    <thead>
                    <tr>
                        <td>
                            Facebook_Game_Hours
                        </td>
                    <td>
                        Sport_Hours
                    </td>
                    <td>
                        HomeWork_Hours
                    </td>
                    <td>
                        Phone_Hours
                    </td>
                    <td>
                        Friends_Hours
                    </td>
                    <td>
                        TV_Hours
                    </td>
                    <td>
                        Other_Hours
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
        </section>
    </div>
</div>

</body>
</html>