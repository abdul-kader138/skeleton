<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="stylesheet" href="resources/node_modules/bootstrap/dist/css/bootstrap.min.css">
</head>
<body ng-app="loginApp">


</noscript>
<div id="mainContent" class="container-fluid" ng-controller="loginController">
    <div class="col-xs-4 col-xs-offset-4">
        <hr>
        <form action="<c:url value='j_spring_security_check' />" method="POST" role="form">
            <legend>Login Information</legend>
            <h5 style="color: red;text-decoration-style: double">${error}</h5>
            <c:if test="${error == null}">
                <h5 ng-show="successMsg" style="color: green;text-decoration-style: double">{{successMsg}}</h5>
                <h5 ng-show="errorMsg" style="color: red;text-decoration-style: double">{{errorMsg}}</h5>
            </c:if>

            <br/>


            <div class="form-group">
                <label for="Name">Email</label>
                <input type="text" class="form-control" name="j_username" placeholder="">
            </div>
            <div class="form-group">
                <label for="Password">Password</label>
                <input class="form-control" type="password" name="j_password" placeholder="">
            </div>
            <input class="btn btn-primary" type="submit" value="Login"/>
        </form>
        <hr>
    </div>
</div>
<script src="resources/node_modules/jquery/dist/jquery.min.js"></script>
<script src="resources/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="resources/node_modules/angular/angular.min.js"></script>
<script src="resources/node_modules/angular-route/angular-route.min.js"></script>
<script src="resources/node_modules/ngstorage/ngStorage.min.js"></script>
<script src="resources/node_modules/custom-js/app.js"></script>
<script src="resources/node_modules/custom-js/controller.js"></script>


<noscript>
    <div class="container">
        <br/>
        <br/>
        <br/>
        <br/>

        <div class="col-sm-6 col-sm-offset-3">
            <div class="panel panel-danger">
                <div class="panel-body">
                    For full functionality of this site it is necessary to enable JavaScript.<br/>
                    Here are the <a href="http://www.enable-javascript.com/" target="_blank">
                    instructions how to enable JavaScript in your web browser</a>.
                </div>
            </div>
        </div>
    </div>
    <style>
        #mainContent {
            display: none;
        }
    </style>
</noscript>

</body>
</html>
