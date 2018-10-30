/**
 * Created by Admin on 8/13/2018.
 */
app.controller('qmsCtrl',['$scope','$http','$filter','$window','$timeout','$q'
    ,function ($scope,$http,$filter,$window,$timeout,$q) {
        $scope.name="";
        $scope.dataLoaded=false;
        $scope.page=page;
        $http.get(preUrl+"/admin/category/door/list-all",{params:{name:$scope.name}})
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.page=response.data;
                    $scope.page.pageCount=getPageCount($scope.page);
                    $scope.page.pageList=getPageList($scope.page);
                }
            });
        
        $scope.search=function () {
            $scope.page=page;
            $http.get(preUrl+"/admin/category/door/list-all",{params:{name:$scope.name}})
                .then(function (response) {
                    if(response!=null && response!='undefined' && response.status==200){
                        $scope.page=response.data;
                        $scope.page.pageCount=getPageCount($scope.page);
                        $scope.page.pageList=getPageList($scope.page);
                    }
                });
        };

        /*----------------------------------------------------------------------------------*/

        $scope.loadPage=function (pageNumber) {
            if(pageNumber>=1){
                $http.get(preUrl+"/admin/category/door/list-all", {params: {p:pageNumber,name:$scope.name}})
                    .then(function (response) {
                        $scope.page=response.data;
                        $scope.page.pageList=getPageList($scope.page);
                        $scope.page.pageCount=getPageCount($scope.page);
                    });
            }
        };

        //load for add and edit
        $scope.areas="";
        $scope.objectTypes="";
        $scope.item_type={};

        var object={id:null,name:"",area:"",reception_type_value:0,description:"",disable:false}
        $scope.item={}
        $http.get(preUrl+"/area/list")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.areas=response.data;
                }
            });
        $http.get(preUrl+"/admin/category/object-type/list-active")
            .then(function (response) {
                if(response!=null && response!='undefined' && response.status==200){
                    $scope.objectTypes=response.data;
                }
            });

        $scope.addClick=function () {
            $scope.item_type={};
            $scope.item=object;
        };
        $scope.editClick=function (item) {
            $scope.item_type={};

            $scope.item.id=item[0];
            $scope.item.name=item[1];
            $scope.item.area=item[6];
            $scope.item.description=item[4];
            $scope.item.disable=item[5];
            $scope.item.reception_type_value=item[7];
            angular.forEach($scope.objectTypes,function (ob) {
                if((ob.value & item[7])>0){
                    $scope.item_type[ob.value]=true;
                }
            })
        };
        $scope.addItem=function () {
            if(!$scope.validate()){
                $scope.enableBtn();
            }else{
                $scope.item.reception_type_value=0;
                angular.forEach($scope.item_type,function (selected,value) {
                   if(selected){
                       $scope.item.reception_type_value+=parseInt(value);
                   }
                });
                var data=JSON.parse(JSON.stringify($scope.item));
                $http.post(preUrl+"/admin/category/door/add",data,{headers: {'Content-Type': 'application/json;charset=utf-8;'}})
                    .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.messageStatus="Thêm thành công!";
                            $scope.loadPage(1);
                        }
                        $scope.enableBtn();
                        $scope.confirmShow();
                    },function(response){
                        if(response.status==403){
                            $scope.messageError="Không đủ quyền thực hiện!";
                        }else if(response.status==404){
                            $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                        }else if(response.status=409){
                            $scope.messageError="Tên hoặc tiền tố đã bị trùng lặp dữ liệu!";
                        }else if(response.status==417){
                            $scope.messageError="Thông tin gửi lên server chưa đầy đủ!";
                        }else{$scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";}
                        $scope.enableBtn();
                    });
            }

        };

        $scope.editItem=function () {
            if(!$scope.validate()){
                $scope.enableBtn();
            }else{
                $scope.item.reception_type_value=0;
                angular.forEach($scope.item_type,function (selected,value) {
                    if(selected){
                        $scope.item.reception_type_value+=parseInt(value);
                    }
                });
                var data=JSON.parse(JSON.stringify($scope.item));
                $http.put(preUrl+"/admin/category/door/edit",data,{headers: {'Content-Type': 'application/json;charset=utf-8;'}})
                    .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.messageStatus="Cập nhật thành công!";
                            $scope.loadPage(1);
                        }
                        $scope.enableBtn();
                        $scope.confirmShow();
                    },function(response){
                        if(response.status==403){
                            $scope.messageError="Không đủ quyền thực hiện!";
                        }else if(response.status==404){
                            $scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";
                        }else if(response.status=409){
                            $scope.messageError="Tên hoặc tiền tố đã bị trùng lặp dữ liệu!";
                        }else if(response.status==417){
                            $scope.messageError="Thông tin gửi lên server chưa đầy đủ!";
                        }else{$scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";}
                        $scope.enableBtn();
                    });
            }

        };

        $scope.validate=function () {
            $scope.messageStatus="";
            $scope.messageError="";
            $scope.disableBtn();
            if($scope.item.name=="" || $scope.item.area=="" || !($scope.item.reception_type_value>=0)){
                $scope.messageError="Điền đầy đủ thông tin đối tượng.";
                return false;
            }
            return true;
        };


        $scope.deleteId=0;
        $scope.deleteClick=function (id) {
            $scope.deleteId=0;
            $scope.messageStatus="";
            $scope.messageError="";
            if(id>0){
                $scope.deleteId=id;
            }
        };
        
        $scope.deleteItem=function () {
            if($scope.deleteId>0){
                $http.delete(preUrl+"/admin/category/door/delete/"+$scope.deleteId,{headers: {'Content-Type': 'application/json;charset=utf-8;'}})
                    .then(function (response) {
                        if(response!=null && response!='undefined' && response.status==200){
                            $scope.messageStatus="Đã xóa thành công!";
                            $scope.loadPage(1);
                        }
                        $scope.enableBtn();
                        $scope.confirmShow();
                    },function(response){
                        if(response.status==403){
                            $scope.messageError="Không đủ quyền thực hiện!";
                        }else if(response.status=409){
                            $scope.messageError="Cửa này đã tiếp đón đối tượng, không thể xóa!";
                        }else{$scope.messageError="Có lỗi xảy ra, hãy thử lại sau!";}
                        $scope.enableBtn();
                    });
            }
        };

        $scope.disableBtn=function () {
            var btnCancel = angular.element( document.querySelector( '#btnCancel' ) );
            var btnAdd = angular.element( document.querySelector( '#btnAdd' ) );
            var btnCancelEdit = angular.element( document.querySelector( '#btnCancelEdit' ) );
            var btnEdit = angular.element( document.querySelector( '#btnEdit' ) );
            var btnCencelDel = angular.element( document.querySelector( '#btnCencelDel' ) );
            var btnDel = angular.element( document.querySelector( '#btnDel' ) );
            btnCancel.addClass('disabled');
            btnAdd.addClass('disabled');
            btnCancelEdit.addClass('disabled');
            btnEdit.addClass('disabled');
            btnCencelDel.addClass('disabled');
            btnDel.addClass('disabled');
        };

        $scope.enableBtn=function () {
            var btnCancel = angular.element( document.querySelector( '#btnCancel' ) );
            var btnAdd = angular.element( document.querySelector( '#btnAdd' ) );
            var btnCancelEdit = angular.element( document.querySelector( '#btnCancelEdit' ) );
            var btnEdit = angular.element( document.querySelector( '#btnEdit' ) );
            var btnCencelDel = angular.element( document.querySelector( '#btnCencelDel' ) );
            var btnDel = angular.element( document.querySelector( '#btnDel' ) );
            btnCancel.removeClass('disabled');
            btnAdd.removeClass('disabled');
            btnCancelEdit.removeClass('disabled');
            btnEdit.removeClass('disabled');
            btnCencelDel.removeClass('disabled');
            btnDel.removeClass('disabled');
        };


        $scope.confirmShow=function () {
            $('#addItem').modal('hide');
            $('#editItem').modal('hide');
            $('#deleteItem').modal('hide');
            $('#confirm').modal('show');
        };

    }]);