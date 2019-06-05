/**
 * Created by Admin on 12/22/2017.
 */
app.controller('dashCtrl',['$scope','$http',"$filter"
    ,function ($scope,$http,$filter) {
        $scope.showLuotKham=true;
        $scope.showLuotKhamKhoa=true;
        $scope.showLephi=true;
        $scope.show_lephinhomdv=true;
        $scope.showTyLeKhoa=true;
        $scope.showNoitruCacKhoa=true;
        $scope.listColor=["#3498DB","#9B59B6","#26B99A","#dbcb34","#db3445","#db7734","#db3498","#34db77","#5e0d70","#0f3582","#0f6e82","#0f8235","#82520f","#BDC3C7","#0b3501","#030f00"];
        $scope.lastUpdate="";
        //Get month list
        var date = new Date();
        date.setMonth(date.getMonth() - 11);
        $scope.months = [];
        for(var i = 0; i < 12; i++) {
            $scope.months.push($filter('date')(date, "MM/yyyy"));
            date.setMonth(date.getMonth() + 1);
        }
        /*THOI GIAN CAP NHAT CUOI*/
        // $http.get(preUrl+"/last-update")
        //     .then(function (response) {
        //         if(response!=null && response!='undefined' && response.status==200){
        //             $scope.lastUpdate=response.data;
        //         }
        //     });
        /*BIEU DO TRON TY LE DOANH THU LE PHI CAC KHOA*/
        $scope.doanhthulephikhoa={values:[5434343224,8347359355,9753473345,7934734342,6375634545,8346352634,2548647675,15343429789],id:0};
        $scope.listDeparmentName=['Khoa nội tổng hợp','Khoa Cấp cứu','Khoa Gây mê hồi sức','Khoa khám bệnh','Khoa chuẩn đoán hình ảnh','Khoa phẫu thuật chỉnh hình','Khoa thần kinh','Khoa nhi','Khoa Nội soi chẩn đoán và can thiệp','Khoa Chức năng','Khoa khác'];

        function bieudoDoanhthuLePhiKhoa(){
            if( typeof (Chart) === 'undefined'){ return; }
            if ($('.canvasDoughnutDoanhThuLePhiKhoa').length){

                var chart_doughnut_settings1 = {
                    type: 'doughnut',
                    tooltipFillColor: "rgba(51, 51, 51, 0.55)",
                    data: {
                        labels: $scope.listDeparmentName,
                        // labels: ['Khoa nội tổng hợp','Khoa khám bệnh','Khoa chuẩn đoán hình ảnh','Khoa phẫu thuật chỉnh hình','Khoa thần kinh','Khoa khác'],
                        datasets: [{
                            data: [5434343224,8347359355,9753473345,7934734342,6375634545,8346352634,53234312,23234312,1534342989,1375634545,15343429789],
                            // data: $scope.doanhthulephikhoa.values,
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
                                // "#0f6e82",
                                // "#0f8235",
                                // "#82520f",
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
                                // "#0f6e82",
                                // "#0f8235",
                                // "#82520f",
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

        $scope.loadBieuDoTronDoanhThuLePhiKhoa();

        /*BIEU DO TRON BENH NHAN NOI TRU TAI CAC KHOA*/
        $scope.bnnoitrukhoa="";
        // $http.get(preUrl+"/bnnoitru-khoa")
        //     .then(function (response) {
        //         if(response!=null && response!='undefined' && response.status==200){
        //             $scope.bnnoitrukhoa=response.data;
        //             // $scope.loadBieuDoTronBNNoitruKhoa();
        //             $scope.bieudoDSBNNoiTru();
        //             $scope.showNoitruCacKhoa=true;
        //         }
        //     });
        $scope.bieudoDSBNNoiTru=function(){
            Highcharts.chart('dsnoitru', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'SL BN nội trú các khoa hiện có'
                },
                subtitle: {
                    text: 'Source: isofh.com'
                },
                xAxis: {
                    // categories: $scope.bnnoitrukhoa.names,
                    categories: ['KB','CDHA','CC','Nội 1','Nội 2','TK','PTCH','PTLN','CC24','DK TYC'],
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
                    data: [2340,5430,3425,1234,5483,9761,3432,895,763,1991],
                    color:'#9B59B6'
                }]
            });
        };
        $scope.bieudoDSBNNoiTru();

        /*BIEU DO DOANH THU LE PHI DICH VU*/
        $scope.doanhthulephi="";
        // $http.get(preUrl+"/lephi-dv")
        //     .then(function (response) {
        //         if(response!=null && response!='undefined' && response.status==200){
        //             $scope.doanhthulephi=response.data;
        //             $scope.bieudodoanhthulephi();
        //             $scope.showLephi=true;
        //         }
        //     });
        $scope.bieudodoanhthulephi=function(){
            Highcharts.chart('bieudochong', {

                chart: {
                    type: 'column'
                },

                title: {
                    text: 'Doanh thu lệ phí dịch vụ nội trú - ngoại trú'
                },

                xAxis: {
                    // categories: ['07/2018','08/2018','09/2018','10/2018','11/2018','12/2018','01/2019','02/2019','03/2019','04/2019','05/2019','06/2019']
                    categories: $scope.months
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
                    data: [23253822424,47253822424,52253822424,77253822424,56253822424,43253822424,51253822424,58253822424,32253822424,64253822424,39253822424,12253822424],
                    color:'#f7a35c',
                    stack: 'lephi'
                }, {
                    name: 'Ngoại trú',
                    data: [7253822424,4253822424,11253822424,14253822424,21253822424,9253822424,13253822424,7253822424,12253822424,6253822424,15253822424,3253822424],
                    color:'#26B99A',
                    stack: 'lephi'
                },{
                    type: 'spline',
                    name: 'Tổng',
                    data: [30507644848,47253822424+4253822424,52253822424+11253822424,77253822424+14253822424,56253822424+21253822424,43253822424+9253822424,51253822424+13253822424,
                        58253822424+7253822424,32253822424+12253822424,64253822424+6253822424,39253822424+15253822424,12253822424+3253822424],
                    color:'#9B59B6'
                    // stack: 'lephi'
                }]
            });
        };
        $scope.bieudodoanhthulephi();

        /*BIEU DO LUOT KHAM*/
        $scope.checkup="";
        $scope.linetong="";
        // $http.get(preUrl+"/checkup")
        //     .then(function (response) {
        //         if(response!=null && response!='undefined' && response.status==200){
        //             $scope.checkup=response.data;
        //             $scope.bieudoluotkham();
        //             $scope.showLuotKham=true;
        //         }
        //     });
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
                subtitle: {
                    text: 'Source: isofh.com'
                },
                xAxis: {
                    // categories:  ['07/2018','08/2018','09/2018','10/2018','11/2018','12/2018','01/2019','02/2019','03/2019','04/2019','05/2019','06/2019'],
                    categories:  $scope.months,
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
                    data: [34232,53423,64252,34265,33531,62452,44564,34534,24562,46373,25246,25343],
                    color:'#f15c80'

                }, {
                    type: 'spline',
                    name: 'Dịch vụ',
                    data: [73564,76563,85645,71233,52452,75342,68454,61343,49454,57545,50756,47454]

                }, {
                    type: 'spline',
                    name: 'Tổng',
                    data: [34232+73564,53423+76563,64252+85645,34265+71233,33531+52452,62452+75342,44564+68454,34534+61343,24562+49454,46373+57545,25246+50756,25343+47454]

                }]
            });
        };
        $scope.bieudoluotkham();

        /*BIEU DO LUOT KHAM THEO KHOA*/
        // $scope.checkupDepartment="";
        // $http.get(preUrl+"/checkup-department")
        //     .then(function (response) {
        //         if(response!=null && response!='undefined' && response.status==200){
        //             $scope.checkupDepartment=response.data;
        //             $scope.bieudoluotkhamKhoa();
        //             $scope.showLuotKhamKhoa=true;
        //         }
        //     });
        $scope.bieudoluotkhamKhoa=function(){
            Highcharts.chart('luotkhamcackhoa', {
                chart: {
                    type: 'column'
                },
                title: {
                    text: 'Lượt khám bệnh các khoa'
                },
                subtitle: {
                    text: 'Source: isofh.com'
                },
                xAxis: {
                    categories: ['KB','CDHA','CC','KHTH','TK','CC24','DK TYC','HSCC'],
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
                    data: [4532,2342,1234,734,1872,1241,989,783],
                    color:'#f15c80'

                }, {
                    // type: 'spline',
                    name: 'Dịch vụ',
                    data: [6332,3142,1982,1732,2185,2241,1984,1788]

                }]
            });
        };
        $scope.bieudoluotkhamKhoa();


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
                subtitle: {
                    text: 'Source: isofh.com'
                },
                xAxis: {
                    // categories: ['07/2018','08/2018','09/2018','10/2018','11/2018','12/2018','01/2019','02/2019','03/2019','04/2019','05/2019','06/2019'],
                    categories: $scope.months,
                    crosshair: false
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
                    data: [9307148640,6269108090,10231132305,9424141990,10560219810,9234766635,9782949260,10427576230,8700204400,10307198400,9680960165,7764611520]
                    // color:'#f15c80'

                }, {
                    type: 'spline',
                    name: 'Chẩn đoán hình ảnh',
                    data: [5394933100,3980802450,6664700725,6323112500,6413214400,6156937250,6223043575,6521522165,5298878440,6365301100,5936872610,4146916100]

                }, {
                    type: 'spline',
                    name: 'Phẫu thuật thủ thuật',
                    data: [4681601180,3423889190,4438333800,4665885990,4796382240,5678524980,5728789766,6024760860,5019867460,5935559865,5139995980,3890548590]

                }, {
                    type: 'spline',
                    name: 'Khám bệnh',
                    data: [1025929500,650197150,1242650850,1080271950,1208341300,1019372200,1088427840,1257522885,1027260140,1190982860,1118746185,726583440]

                }, {
                    type: 'spline',
                    name: 'Giường',
                    data: [5754744550,4173034725,6118312135,6850404325,7380970840,6987561825,7239815630,7304110400,6214903650,7487616934,6599089812,6183221299.5]

                }, {
                    type: 'spline',
                    name: 'Thuốc, dịch truyền',
                    data: [14749691979.61,12021137941.74,15571273955.26,14298874963.25,15968851382.62,14608253445.07,16912923057.995,18369287552.335,15026052624.40,17546817063.425,16816969632.01,12917018301.485]

                }, {
                    type: 'spline',
                    name: 'Vật tư y tế',
                    data: [2127013110,1714392932,1503290211.04,2693500367.07,2424314864.9,3267030240.5,2460795866.2,2979133849.45,3219458968.2,3835892512.72,2879229544.08,1717979237.52]

                }, {
                    type: 'spline',
                    name: 'Máu và chế phẩm máu',
                    data: [375065500,295161000,482691500,495566500,486925000,435450000,461599500,457266500,292709000,470720000,405820000,256959000]

                }, {
                    type: 'spline',
                    name: 'Các loại dịch vụ khác',
                    data: [1062210000,660330840,1266819720,1184719840,1216543280,860727680,1034322000,1278424840,870482120,1020576080,1049050560,780063360]

                }, {
                    type: 'spline',
                    name: 'Thăm dò và phục hồi chức năng',
                    data: [1076746050,730951200,1247208605,1245844265,1341609630,1132666955,1168601140,1309520925,1111642020,1428917390,1318808545,1266373520]

                }]
            });
        };


    }]);