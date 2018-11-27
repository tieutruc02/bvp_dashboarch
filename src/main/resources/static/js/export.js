app.controller('reportCtrl',['$scope','$http','$window','$timeout','$q'
    ,function ($scope,$http,$window,$timeout,$q) {
    $scope.query="";
    $scope.download=function () {
        window.open(preUrl+"/download?query="+$scope.query.replace(/\r\n|\r|\n|;/g," "), '_blank');
    };
}]);