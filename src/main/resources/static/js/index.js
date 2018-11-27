/**
 * Created by Admin on 12/22/2017.
 */
app.controller('dashCtrl',['$scope','$http'
    ,function ($scope,$http) {
        $scope.showLuotKham=false;
        $scope.showLuotKhamKhoa=false;
        $scope.showLephi=false;
        $scope.show_lephinhomdv=false;
        $scope.showTyLeKhoa=false;
        $scope.showNoitruCacKhoa=false;
        $scope.listColor=["#3498DB","#9B59B6","#26B99A","#dbcb34","#db3445","#db7734","#db3498","#34db77","#5e0d70","#0f3582","#0f6e82","#0f8235","#82520f","#BDC3C7","#0b3501","#030f00"];
        $scope.lastUpdate="";

        /*THOI GIAN CAP NHAT CUOI*/
        $http.get(preUrl+"/last-update")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.lastUpdate=response.data;
                }
            });
        $scope.refresh=function(){
            $http.get(preUrl+"/refresh")
                .then(function (response) {
                });
        };
/*BIEU DO TRON TY LE DOANH THU LE PHI CAC KHOA*/
        $scope.doanhthulephikhoa="";
        $http.get(preUrl+"/lephi-khoa")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.doanhthulephikhoa=response.data;
                    $scope.loadBieuDoTronDoanhThuLePhiKhoa();
                    $scope.showTyLeKhoa=true;
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
                        datasets: [{
                            data: $scope.doanhthulephikhoa.values,
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
        $scope.listDeparmentName="";
        $http.get(preUrl+"/list-department-name")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.listDeparmentName=response.data;
                }
            });
        /*BIEU DO TRON BENH NHAN NOI TRU TAI CAC KHOA*/
        $scope.bnnoitrukhoa="";
        $http.get(preUrl+"/bnnoitru-khoa")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.bnnoitrukhoa=response.data;
                    // $scope.loadBieuDoTronBNNoitruKhoa();
                    $scope.bieudoDSBNNoiTru();
                    $scope.showNoitruCacKhoa=true;
                }
            });
        $scope.bieudoDSBNNoiTru=function(){
            Highcharts.chart('dsnoitru', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'SL BN nội trú các khoa hiện có'
                },
                // subtitle: {
                //     text: 'Source: isofh.com'
                // },
                xAxis: {
                    categories: $scope.bnnoitrukhoa.names,
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
                    '<td style="padding:0"><b>{point.y:,0.f} BN</b></td></tr>',
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
                    name: 'SL',
                    data: $scope.bnnoitrukhoa.values,
                    color:'#9B59B6'
                }]
            });
        };

        /*BIEU DO DOANH THU LE PHI DICH VU*/
        $scope.doanhthulephi="";
        $http.get(preUrl+"/lephi-dv")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.doanhthulephi=response.data;
                    $scope.bieudodoanhthulephi();
                    $scope.showLephi=true;
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
                    name: 'Nội trú',
                    data: $scope.doanhthulephi.values1,
                    color:'#f7a35c',
                    stack: 'lephi'
                }, {
                    name: 'Ngoại trú',
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
        $scope.linetong="";
        $http.get(preUrl+"/checkup")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.checkup=response.data;
                    $scope.bieudoluotkham();
                    $scope.showLuotKham=true;
                }
            });
        Highcharts.setOptions({
            lang: {
                // numericSymbols: null //otherwise by default ['k', 'M', 'G', 'T', 'P', 'E']
                numericSymbols:  [' ngàn', ' triệu', ' tỉ', ' ngàn tỉ', ' triệu tỉ']
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
                // subtitle: {
                //     text: 'Source: isofh.com'
                // },
                xAxis: {
                    categories: $scope.checkup.names,
                    crosshair: true
                },
                yAxis: {
                    allowDecimals: false,
                    min: 0,
                    title: {
                        text: 'Số lượng'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:,0.f} lượt</b></td></tr>',
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
                    type: 'spline',
                    name: 'BHYT',
                    data: $scope.checkup.values1,
                    color:'#f15c80'

                }, {
                    type: 'spline',
                    name: 'Dịch vụ',
                    data: $scope.checkup.values2

                }, {
                    type: 'spline',
                    name: 'Tổng',
                    data: $scope.checkup.values3

                }]
            });
        };

        /*BIEU DO LUOT KHAM THEO KHOA*/
        $scope.checkupDepartment="";
        $http.get(preUrl+"/checkup-department")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.checkupDepartment=response.data;
                    $scope.bieudoluotkhamKhoa();
                    $scope.showLuotKhamKhoa=true;
                }
            });
        $scope.bieudoluotkhamKhoa=function(){
            Highcharts.chart('luotkhamcackhoa', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Lượt khám bệnh các khoa'
                },
                // subtitle: {
                //     text: 'Source: isofh.com'
                // },
                xAxis: {
                    categories: $scope.checkupDepartment.names,
                    crosshair: true
                },
                yAxis: {
                    allowDecimals: false,
                    min: 0,
                    title: {
                        text: 'Số lượng'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:,0.f} lượt</b></td></tr>',
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
                    // type: 'spline',
                    name: 'BHYT',
                    data: $scope.checkupDepartment.values1,
                    color:'#f15c80'

                }, {
                    // type: 'spline',
                    name: 'Dịch vụ',
                    data: $scope.checkupDepartment.values2

                }]
            });
        };


        /*BIEU DO LE PHI DICH VU THEO LOAI DV*/
        $scope.lephidvnhom="";
        $http.get(preUrl+"/lephidvnhom")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.lephidvnhom=response.data;
                    $scope.bieudolephidvnhom();
                    $scope.show_lephinhomdv=true;
                }
            });
        $scope.bieudolephidvnhom=function(){
            Highcharts.chart('lephinhomdv', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Lệ phí DV theo nhóm DV'
                },
                // subtitle: {
                //     text: 'Source: isofh.com'
                // },
                xAxis: {
                    categories: $scope.lephidvnhom.names,
                    crosshair: false
                },
                yAxis: {
                    allowDecimals: false,
                    min: 0,
                    title: {
                        text: 'Số lượng'
                    }
                },
                // formatter: function () {
                //     var result= '<b>' + this.x + '</b><br/>' +
                //         this.series.name + ': ' + Highcharts.numberFormat(this.y, 1, '.', ',') + '<br/>' ;
                //     if(this.point.stackTotal!=null && this.point.stackTotal!= 'undefined'){
                //         result=result+'Tổng: ' + Highcharts.numberFormat(this.point.stackTotal, 1, '.', ',');
                //     }
                //     return result;
                // }
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y:,.1f} </b></td></tr>',
                    footerFormat: '</table>',
                    shared: false,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: [{
                    type: 'spline',
                    name: 'Xét nghiệm',
                    data: $scope.lephidvnhom.group_1000057
                    // color:'#f15c80'

                }, {
                    type: 'spline',
                    name: 'Chẩn đoán hình ảnh',
                    data: $scope.lephidvnhom.group_1000058

                }, {
                    type: 'spline',
                    name: 'Phẫu thuật thủ thuật',
                    data: $scope.lephidvnhom.group_1000059

                }, {
                    type: 'spline',
                    name: 'Khám bệnh',
                    data: $scope.lephidvnhom.group_1000060

                }, {
                    type: 'spline',
                    name: 'Giường',
                    data: $scope.lephidvnhom.group_1000061

                }, {
                    type: 'spline',
                    name: 'Thuốc, dịch truyền',
                    data: $scope.lephidvnhom.group_1000062

                }, {
                    type: 'spline',
                    name: 'Vật tư y tế',
                    data: $scope.lephidvnhom.group_1000063

                }, {
                    type: 'spline',
                    name: 'Máu và chế phẩm máu',
                    data: $scope.lephidvnhom.group_1000065

                }, {
                    type: 'spline',
                    name: 'Các loại dịch vụ khác',
                    data: $scope.lephidvnhom.group_1000067

                }, {
                    type: 'spline',
                    name: 'Thăm dò và phục hồi chức năng',
                    data: $scope.lephidvnhom.group_1000077

                }]
            });
        };


    }]);