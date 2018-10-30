/**
 * Created by Admin on 12/22/2017.
 */
app.controller('dashCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.year="";
        $scope.month="";
        $scope.week="";
        $scope.showMonth=false;
        $scope.showWeek=false;
        $scope.showYear=false;
        $scope.type="";
        $scope.listYear=[];
        $scope.nameTitle="tuần này";
        var date=new Date();
        var week = getWeekNumber(date);
        var fullYear=date.getFullYear();
        for(var i=0;i<5;i++){
            $scope.listYear.push(fullYear);
            fullYear--;
        }
        $scope.showLuotKham=true;
        $scope.showLephi=true;
        $scope.showTyLeKhoa=true;


/*BIEU DO TRON TY LE DOANH THU LOAI GIAO DICH*/
        $scope.doanhthulephikhoa="";
        $http.get(preUrl+"/lephi-khoa",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.doanhthulephikhoa=response.data;
                    $scope.loadBieuDoTronDoanhThuLePhiKhoa();
                }
            });

        function bieudoDoanhthuLePhiKhoa(){
            if( typeof (Chart) === 'undefined'){ return; }
            if ($('.canvasDoughnutDoanhThuLePhiKhoa').length){

                var chart_doughnut_settings1 = {
                    type: 'doughnut',
                    tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                    data: {
                        labels: $scope.doanhthulephikhoa.names,
                        //     [
                        //     "Đăng ký",
                        //     "Gia Hạn",
                        //     "Xác thực",
                        //     "Gia hạn xác thực"
                        // ],
                        datasets: [{
                            data: $scope.doanhthulephikhoa.values,
                            // data: [53437770, 6000000, 3400000, 14323333],
                            backgroundColor: [
                                "#3498DB",
                                "#9B59B6",
                                "#26B99A",
                                "#dbcb34",
                                "#db3445",
                                "#db7734",
                                "#db3498",
                                "#34db77",
                                "#5e0d70",
                                "#0f3582",
                                "#0f6e82",
                                "#0f8235",
                                "#82520f",
                                "#BDC3C7"
                            ],
                            hoverBackgroundColor: [
                                "#3498DB",
                                "#9B59B6",
                                "#26B99A",
                                "#dbcb34",
                                "#db3445",
                                "#db7734",
                                "#db3498",
                                "#34db77",
                                "#5e0d70",
                                "#0f3582",
                                "#0f6e82",
                                "#0f8235",
                                "#82520f",
                                "#BDC3C7"
                            ]
                        }]
                    },
                    options: {
                        legend: false,
                        responsive: false,
                        hover:false,//huy bo hover
                        tooltips:false//huy bo tooltips boi khi dung angularjs load lai thi bi ghi de hinh anh luc hover
                    }
                };

                $('.canvasDoughnutDoanhThuLePhiKhoa').each(function(){
                    var chart_element1 = $(this);
                    var chart_doughnut = new Chart( chart_element1, chart_doughnut_settings1);
                });

            }

        }
        $scope.loadBieuDoTronDoanhThuLePhiKhoa=function () {
            bieudoDoanhthuLePhiKhoa();
        };
        $scope.listColor=["#3498DB","#9B59B6","#26B99A","#dbcb34","#db3445","#db7734","#db3498","#34db77","#5e0d70","#0f3582","#0f6e82","#0f8235","#82520f","#BDC3C7"];
        $scope.listDeparmentName="";
        $http.get(preUrl+"/list-department-name")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listDeparmentName=response.data;
                }
            });

        /*BIEU DO DOANH THU LE PHI DICH VU*/
        $scope.doanhthulephi="";
        $http.get(preUrl+"/lephi-dv",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.doanhthulephi=response.data;
                    $scope.bieudodoanhthulephi();
                }
            });
        $scope.bieudodoanhthulephi=function(){
            Highcharts.chart('bieudochong', {

                chart: {
                    type: 'column'
                },

                title: {
                    text: 'Doanh thu lệ phí dịch vụ nội trú - ngoại trú'
                },

                xAxis: {
                    categories: $scope.doanhthulephi.names
                },

                yAxis: {
                    allowDecimals: false,
                    min: 0,
                    title: {
                        text: 'Số lượng'
                    }
                },

                tooltip: {
                    // formatter: function () {
                    //     var result= '<b>' + this.x + '</b><br/>' +
                    //         this.series.name + ': ' + this.y + '<br/>' ;
                    //     console.log(this.point.stackTotal);
                    //     if(this.point.stackTotal!=null && this.point.stackTotal!= 'undefined'){
                    //         result=result+'Tổng: ' + this.point.stackTotal;
                    //     }
                    //     return result;
                    // }
                    formatter: function () {
                        var result= '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + Highcharts.numberFormat(this.y, 1, '.', ',') + '<br/>' ;
                        if(this.point.stackTotal!=null && this.point.stackTotal!= 'undefined'){
                            result=result+'Tổng: ' + Highcharts.numberFormat(this.point.stackTotal, 1, '.', ',');
                        }
                        return result;
                    }
                },

                plotOptions: {
                    column: {
                        stacking: 'normal'
                    }
                },

                series: [{
                    name: 'Ngoại trú',
                    data: $scope.doanhthulephi.values1,
                    color:'#f7a35c',
                    stack: 'lephi'
                }, {
                    name: 'Nội trú',
                    data: $scope.doanhthulephi.values2,
                    color:'#26B99A',
                    stack: 'lephi'
                },{
                    type: 'spline',
                    name: 'Tổng',
                    data: $scope.doanhthulephi.values3,
                    color:'#9B59B6'
                    // stack: 'lephi'
                }]
            });
        };

        /*BIEU DO LUOT KHAM*/
        $scope.checkup="";
        $http.get(preUrl+"/checkup",{params:{year:date.getFullYear(),week:week}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.checkup=response.data;
                    $scope.bieudoluotkham();
                }
            });
        $scope.bieudoluotkham=function(){
            Highcharts.chart('luotkham', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Lượt khám theo thời gian'
                },
                subtitle: {
                    text: 'Source: p.produce.isofh.vn'
                },
                xAxis: {
                    categories: $scope.checkup.names,
                    // categories: ['haha','mama','kaka'],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: 'Số lượng'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:.f} lượt</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{

                    name: 'BHYT',
                    data: $scope.checkup.values1,
                    color:'#f15c80'

                }, {
                    name: 'Dịch vụ',
                    data: $scope.checkup.values2

                }]
            });
        };


        /*CHO MUC CHON CHI TIEU*/

        $scope.changeType=function () {
            switch($scope.type){
                case "0":
                    $scope.showWeek=true;
                    $scope.showYear=true;
                    $scope.showMonth=false;
                    break;
                case "1":
                    $scope.showWeek=false;
                    $scope.showYear=true;
                    $scope.showMonth=true;
                    break;
                case "2":
                    $scope.showWeek=false;
                    $scope.showYear=true;
                    $scope.showMonth=false;
                    break;
                default:
                    break;
            }
        };

        $scope.typeError="";
        $scope.yearError="";
        $scope.monthError="";
        $scope.weekError="";
        $scope.nameTitleSwrap="";
        $scope.refreshStatus=function () {
            $scope.typeError="";
            $scope.yearError="";
            $scope.monthError="";
            $scope.weekError="";
        };
        $scope.checkBeforeSearch=function () {
            $scope.refreshStatus();
          if($scope.type==null || $scope.type=='undefined' || $scope.type.length==0){
              $scope.typeError="Bạn phải chọn chỉ tiêu thống kê.";
              return false;
          }
          if($scope.year==null ||$scope.year=='undefined' || $scope.year.length==0){
              $scope.yearError="Bạn phải chọn năm.";
              return false;
          }
          switch ($scope.type){
              case "0":
                  $scope.month="";
                  if($scope.week==null ||$scope.week=='undefined' || $scope.week.length==0){
                      $scope.weekError="Bạn phải chọn tuần.";
                      return false;
                  }
                  $scope.nameTitleSwrap="tuần "+$scope.week+" của năm "+$scope.year;
                  break;
              case "1":
                  $scope.week="";
                  if($scope.month==null ||$scope.month=='undefined' || $scope.month.length==0){
                      $scope.monthError="Bạn phải chọn tháng.";
                      return false;
                  }
                  $scope.nameTitleSwrap="tháng "+$scope.month+"/"+$scope.year;
                  break;
              case "2":
                  $scope.month="";
                  $scope.week="";
                  $scope.nameTitleSwrap="năm "+$scope.year;
                  break;
              default:break;
          }
          return true;
        };

        $scope.search=function () {
            if($scope.checkBeforeSearch()){
                $scope.showLuotKham=false;
                $scope.showLephi=false;
                $scope.showTyLeKhoa=false;
                $scope.reloadAllChart();
            }
        };
        
        $scope.reloadAllChart=function () {
            $http.get(preUrl+"/checkup",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.checkup=response.data;
                        $scope.bieudoluotkham();
                        $scope.showLuotKham=true;
                    }
                });
            $http.get(preUrl+"/lephi-dv",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.doanhthulephi=response.data;
                        $scope.bieudodoanhthulephi();
                        $scope.showLephi=true;
                    }
                });
            $http.get(preUrl+"/lephi-khoa",{params:{year:$scope.year,month:$scope.month,week:$scope.week}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.doanhthulephikhoa=response.data;
                        $scope.loadBieuDoTronDoanhThuLePhiKhoa();
                        $scope.showTyLeKhoa=true;
                    }
                });

            $scope.nameTitle=$scope.nameTitleSwrap;
        }


    }]);