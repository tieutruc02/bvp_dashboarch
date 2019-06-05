//package com.isofh.bvp.dashboard.web;
//
//import com.isofh.bvp.dashboard.common.DateUtils;
//import com.isofh.bvp.dashboard.model.*;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import java.math.BigDecimal;
//import java.util.*;
//
//@Service
//public class IndexService {
//    @Autowired
//    IndexRepository indexRepository;
//    public static TreeMap<Long,String> departments=new TreeMap<>();
//    public static TreeMap<Long,String> departmentsIP=new TreeMap<>();
////    public static TreeMap<Long,String> servicegroupLevel1=new TreeMap<>();
//
//
//    @PostConstruct
//    public void init(){
//        departments.put(Long.valueOf(1000241),"Khoa Vi sinh và Labo lao chuẩn Quốc gia");
//        departments.put(Long.valueOf(1000240),"Khoa Chẩn đoán hình ảnh");
//        departments.put(Long.valueOf(1000333),"Khoa Cấp cứu");
//        departments.put(Long.valueOf(1000264),"Khoa Bệnh phổi nghề nghiệp");
//        departments.put(Long.valueOf(1000262),"Trung tâm xạ trị");
//        departments.put(Long.valueOf(1000243),"Khoa Hóa sinh miễn dịch");
//        departments.put(Long.valueOf(1000268),"Khoa Khám bệnh ĐK TYC");
//        departments.put(Long.valueOf(1000271),"Khoa Thăm dò và Phục hồi chức năng");
//        departments.put(Long.valueOf(1000270),"Khoa Gây mê hồi sức");
//        departments.put(Long.valueOf(1000233),"Khoa Lao hô hấp");
//        departments.put(Long.valueOf(1000235),"Khoa Nội tổng hợp");
//        departments.put(Long.valueOf(1000242),"Khoa Huyết học");
//        departments.put(Long.valueOf(1000272),"Khoa Phẫu thuật lồng ngực");
//        departments.put(Long.valueOf(999999999),"Khác");
//
//        //danh sach khoa noi tru
//        departmentsIP.put(Long.valueOf(1000235),"NOI_TH");
//        departmentsIP.put(Long.valueOf(1000271),"TD&PHCN");
//        departmentsIP.put(Long.valueOf(1000553),"CNC");
//        departmentsIP.put(Long.valueOf(1000264),"BPNN");
//        departmentsIP.put(Long.valueOf(1000234),"HSTC");
//        departmentsIP.put(Long.valueOf(1000272),"PTLN");
//        departmentsIP.put(Long.valueOf(1000261),"UB");
//        departmentsIP.put(Long.valueOf(1000238),"NGOAI_TH");
//        departmentsIP.put(Long.valueOf(1000333),"KCC");
//        departmentsIP.put(Long.valueOf(1000270),"GMHS");
//        departmentsIP.put(Long.valueOf(1000233),"LAO");
//        departmentsIP.put(Long.valueOf(1000239),"NHI");
//        departmentsIP.put(Long.valueOf(1000236),"HOHAP");
//        departmentsIP.put(Long.valueOf(1000269),"BPMT");
//
//        //danh sach goi dich vu
////        servicegroupLevel1.put(Long.valueOf(1000057),"Xét nghiệm");
////        servicegroupLevel1.put(Long.valueOf(1000058),"Chẩn đoán hình ảnh");
////        servicegroupLevel1.put(Long.valueOf(1000059),"Phẫu thuật thủ thuật");
////        servicegroupLevel1.put(Long.valueOf(1000060),"Khám bệnh");
////        servicegroupLevel1.put(Long.valueOf(1000061),"Giường");
////        servicegroupLevel1.put(Long.valueOf(1000062),"Thuốc, dịch truyền");
////        servicegroupLevel1.put(Long.valueOf(1000063),"Vật tư y tế");
////        servicegroupLevel1.put(Long.valueOf(1000065),"Máu và chế phẩm máu");
////        servicegroupLevel1.put(Long.valueOf(1000067),"Các loại dịch vụ khác");
////        servicegroupLevel1.put(Long.valueOf(1000077),"Thăm dò và phục hồi chức năng");
//    }
//
//    public Optional<BieuDo3GiaTri> DoanhThuLePhiDV(){
//        List<DoiTuong12Thang> listOP=lephiDV(true).orElse(new ArrayList<>());
//        List<DoiTuong12Thang> listIP=lephiDV(false).orElse(new ArrayList<>());
//        for(DoiTuong12Thang item:listOP){
//            listIP.add(item);
//        }
//        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
//        genBieuDo12Thang(listIP,bieudo);
//        BieuDo3GiaTri bieuDo3GiaTri=new BieuDo3GiaTri();
//        bieuDo3GiaTri.setNames(bieudo.getNames());
//        bieuDo3GiaTri.setValues1(bieudo.getValues1());
//        bieuDo3GiaTri.setValues2(bieudo.getValues2());
//        List<BigDecimal> listvalue3=new ArrayList<>();
//        for(int i=0;i<bieudo.getNames().size();i++){
//            listvalue3.add(bieudo.getValues1().get(i).add(bieudo.getValues2().get(i)));
//        }
//        bieuDo3GiaTri.setValues3(listvalue3);
//        return Optional.ofNullable(bieuDo3GiaTri);
//    }
//    private Optional<List<DoiTuong12Thang>> lephiDV(boolean isOutPatient){
//        Date from=DateUtils.genDateFromOfLast12Month();
//        List<DoiTuong12Thang> list=new ArrayList<>();
//        if(isOutPatient){
//            list = indexRepository.lephiDVNgoaitru(from).orElse(new ArrayList<>());
//        }else{
//            list = indexRepository.lephiDVNoitru(from).orElse(new ArrayList<>());
//        }
//        return Optional.ofNullable(list);
//    }
//
////    private Optional<List<DoiTuong>> lephiDVKhoa(int year,int month,int week){
////        Date from=DateUtils.getFromAndToDate(year,month,week,true);
////        Date to=DateUtils.getFromAndToDate(year,month,week,false);
////        List<DoiTuong> list=indexRepository.lephiDVCacKhoa(from,to).orElse(new ArrayList<>());
////        return Optional.ofNullable(list);
////    }
//    private Optional<List<DoiTuong>> lephiDVKhoa(){
//        Date from=DateUtils.genFirstDayOfMonthCurrent();
//        List<DoiTuong> list=indexRepository.lephiDVCacKhoa(from).orElse(new ArrayList<>());
//        return Optional.ofNullable(list);
//    }
//
//    public Optional<BieuDo> TyLeLePhiKhoa(){
//        List<DoiTuong> items=lephiDVKhoa().orElse(new ArrayList<>());
//        BieuDo bieudo=new BieuDo();
//        genBieuDoTyLeLePhiKhoa(items,bieudo);
//        return Optional.ofNullable(bieudo);
//    }
//
////    public Optional<BieuDo> BNNoiTruKhoa(int year,int month,int week){
////        List<DoiTuong> items=BNNoiTruTaiCacKhoa(year,month,week).orElse(new ArrayList<>());
////        BieuDo bieudo=new BieuDo();
////        genBieuDoBNNoitruKhoa(items,bieudo);
////        return Optional.ofNullable(bieudo);
////    }
////
////    private Optional<List<DoiTuong>> BNNoiTruTaiCacKhoa(int year,int month,int week){
////        Date to=DateUtils.getFromAndToDate(year,month,week,false);
////        List<DoiTuong> list=indexRepository.BNNoiTruCacKhoa(to).orElse(new ArrayList<>());
////        return Optional.ofNullable(list);
////    }
//    public Optional<BieuDo> BNNoiTruKhoa(){
//        List<DoiTuong> items=BNNoiTruTaiCacKhoa().orElse(new ArrayList<>());
//        BieuDo bieudo=new BieuDo();
//        genBieuDoBNNoitruKhoa(items,bieudo);
//        return Optional.ofNullable(bieudo);
//    }
//
//    private Optional<List<DoiTuong>> BNNoiTruTaiCacKhoa(){
//        Date to=new Date();
//        List<DoiTuong> list=indexRepository.BNNoiTruCacKhoa(to).orElse(new ArrayList<>());
//        return Optional.ofNullable(list);
//    }
//
//
//
////    public Optional<BieuDo2GiaTri> LuotKhamBenh(int year, int month, int week){
////        List<DoiTuong> list=LuotKham(year,month,week).orElse(new ArrayList<>());
////        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
////        if(week>0){
////            genBieuDoTuan(list,bieudo);
////        }else if(month>0){
////            genBieuDoThang(list,bieudo,year,month);
////        }else{
////            genBieuDoNam(list,bieudo);
////        }
////
////        return Optional.ofNullable(bieudo);
////    }
////
////    private Optional<List<DoiTuong>> LuotKham(int year, int month, int week){
////        Date from=DateUtils.getFromAndToDate(year,month,week,true);
////        Date to=DateUtils.getFromAndToDate(year,month,week,false);
////        List<DoiTuong> list= indexRepository.LuotKhamTheoNgay(from,to).orElse(new ArrayList<>());
////        return Optional.ofNullable(list);
////    }
//
//    public Optional<BieuDo3GiaTri> LuotKhamBenh(){
//        List<DoiTuong12Thang> list=LuotKham().orElse(new ArrayList<>());
//        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
//        genBieuDo12Thang(list,bieudo);
//
//        BieuDo3GiaTri bieuDo3GiaTri=new BieuDo3GiaTri();
//        bieuDo3GiaTri.setNames(bieudo.getNames());
//        bieuDo3GiaTri.setValues1(bieudo.getValues1());
//        bieuDo3GiaTri.setValues2(bieudo.getValues2());
//        List<BigDecimal> listvalue3=new ArrayList<>();
//        for(int i=0;i<bieudo.getNames().size();i++){
//            listvalue3.add(bieudo.getValues1().get(i).add(bieudo.getValues2().get(i)));
//        }
//        bieuDo3GiaTri.setValues3(listvalue3);
//
//        return Optional.ofNullable(bieuDo3GiaTri);
//    }
//
//    private Optional<List<DoiTuong12Thang>> LuotKham(){
//        Date from=DateUtils.genDateFromOfLast12Month();
//        List<DoiTuong12Thang> list= indexRepository.LuotKhamTheoThang(from).orElse(new ArrayList<>());
//        return Optional.ofNullable(list);
//    }
//
//    public Optional<BieuDo2GiaTri> LuotKhamBenhKhoa(){
//        List<DoiTuong12Thang> list=LuotKhamKhoa().orElse(new ArrayList<>());
//        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
//        genBieuDoLuotKhamTheoKhoa(list,bieudo);
//        return Optional.ofNullable(bieudo);
//    }
//
//    private Optional<List<DoiTuong12Thang>> LuotKhamKhoa(){
//        Date from=DateUtils.genFirstDayOfMonthCurrent();
//        List<DoiTuong12Thang> list= indexRepository.LuotKhamTheoKhoa(from).orElse(new ArrayList<>());
//        return Optional.ofNullable(list);
//    }
//
//    private void genBieuDo12Thang(List<DoiTuong12Thang> list,BieuDo2GiaTri bieudo){
//        List<String> names=DateUtils.genListMonthName();
//        List<Integer> listthang=DateUtils.genListMonthValue();
//        List<BigDecimal> values1=new ArrayList<>();
//        List<BigDecimal> values2=new ArrayList<>();
//        HashMap<String,BigDecimal> listValue1=new HashMap<>();
//        HashMap<String,BigDecimal> listValue2=new HashMap<>();
//        for(String name:names){
//            listValue1.put(name,BigDecimal.valueOf(0));
//            listValue2.put(name,BigDecimal.valueOf(0));
//        }
//        list.stream().forEach(item->{
//            if (item.getType().equals("A")) {
//                for(int i=0;i<listthang.size();i++){
//                    if(item.getMonth()!=null && item.getMonth().intValue()==listthang.get(i).intValue()){
//                        BigDecimal value=listValue1.get(names.get(i));
//                        listValue1.put(names.get(i),value.add(item.getQuantity()));
//                        break;
//                    }
//                }
//            } else {
//                for(int i=0;i<listthang.size();i++){
//                    if(item.getMonth()!=null && item.getMonth().intValue()==listthang.get(i).intValue()){
//                        BigDecimal value=listValue2.get(names.get(i));
//                        listValue2.put(names.get(i),value.add(item.getQuantity()));
//                        break;
//                    }
//                }
//            }
//        });
//        bieudo.setNames(names);
//        for(String item:names){
//            BigDecimal soluong1=listValue1.get(item);
//            BigDecimal soluong2=listValue2.get(item);
//            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
//                values1.add(soluong1);
//            }else{
//                values1.add(BigDecimal.ZERO);
//            }
//            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
//                values2.add(soluong2);
//            }else{
//                values2.add(BigDecimal.ZERO);
//            }
//        }
//        bieudo.setValues1(values1);
//        bieudo.setValues2(values2);
//    }
//    private void genBieuDoLuotKhamTheoKhoa(List<DoiTuong12Thang> list,BieuDo2GiaTri bieudo){
//        List<String> names=new ArrayList<>();
//        List<Long> listGiaTri=new ArrayList<>();
//        HashMap<Long,String> map=new HashMap<>();
//        for(DoiTuong12Thang item:list){
//            if(!item.getName().equals("KHTH")){ // ko tinh phong KHTH
//                map.put(item.getId(),item.getName());
//            }
//        }
//        map.forEach((k,v)->{
//            names.add(v);
//            listGiaTri.add(k);
//        });
//
//        List<BigDecimal> values1=new ArrayList<>();
//        List<BigDecimal> values2=new ArrayList<>();
//        HashMap<String,BigDecimal> listValue1=new HashMap<>();
//        HashMap<String,BigDecimal> listValue2=new HashMap<>();
//        for(String name:names){
//            listValue1.put(name,BigDecimal.valueOf(0));
//            listValue2.put(name,BigDecimal.valueOf(0));
//        }
//        list.stream().forEach(item->{
//            if (item.getType().equals("A")) {
//                for(int i=0;i<listGiaTri.size();i++){
//                    if(item.getId()!=null && item.getId().intValue()==listGiaTri.get(i).intValue()){
//                        BigDecimal value=listValue1.get(names.get(i));
//                        listValue1.put(names.get(i),value.add(item.getQuantity()));
//                        break;
//                    }
//                }
//            } else {
//                for(int i=0;i<listGiaTri.size();i++){
//                    if(item.getId()!=null && item.getId().intValue()==listGiaTri.get(i).intValue()){
//                        BigDecimal value=listValue2.get(names.get(i));
//                        listValue2.put(names.get(i),value.add(item.getQuantity()));
//                        break;
//                    }
//                }
//            }
//        });
//        bieudo.setNames(names);
//        for(String item:names){
//            BigDecimal soluong1=listValue1.get(item);
//            BigDecimal soluong2=listValue2.get(item);
//            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
//                values1.add(soluong1);
//            }else{
//                values1.add(BigDecimal.ZERO);
//            }
//            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
//                values2.add(soluong2);
//            }else{
//                values2.add(BigDecimal.ZERO);
//            }
//        }
//        bieudo.setValues1(values1);
//        bieudo.setValues2(values2);
//    }
//
////    private void genBieuDoTuan(List<DoiTuong> list,BieuDo2GiaTri bieudo){
////        List<String> names=Arrays.asList("Thứ hai","Thứ ba","Thứ tư","Thứ năm","Thứ sáu","Thứ bảy","Chủ nhật");
////        List<BigDecimal> values1=new ArrayList<>();
////        List<BigDecimal> values2=new ArrayList<>();
////            Calendar cal=Calendar.getInstance();
////            HashMap<String,BigDecimal> listValue1=new HashMap<>();
////            HashMap<String,BigDecimal> listValue2=new HashMap<>();
////            list.stream().forEach(item->{
////                cal.setTime(item.getDate());
////                if (item.getName().equals("A")) {
////                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
////                        case Calendar.MONDAY:
////                            listValue1.put(names.get(0), item.getValue());
////                            break;
////                        case Calendar.TUESDAY:
////                            listValue1.put(names.get(1), item.getValue());
////                            break;
////                        case Calendar.WEDNESDAY:
////                            listValue1.put(names.get(2), item.getValue());
////                            break;
////                        case Calendar.THURSDAY:
////                            listValue1.put(names.get(3), item.getValue());
////                            break;
////                        case Calendar.FRIDAY:
////                            listValue1.put(names.get(4), item.getValue());
////                            break;
////                        case Calendar.SATURDAY:
////                            listValue1.put(names.get(5), item.getValue());
////                            break;
////                        default:
////                            listValue1.put(names.get(6), item.getValue());
////                            break;
////                    }
////                } else {
////                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
////                        case Calendar.MONDAY:
////                            listValue2.put(names.get(0), item.getValue());
////                            break;
////                        case Calendar.TUESDAY:
////                            listValue2.put(names.get(1), item.getValue());
////                            break;
////                        case Calendar.WEDNESDAY:
////                            listValue2.put(names.get(2), item.getValue());
////                            break;
////                        case Calendar.THURSDAY:
////                            listValue2.put(names.get(3), item.getValue());
////                            break;
////                        case Calendar.FRIDAY:
////                            listValue2.put(names.get(4), item.getValue());
////                            break;
////                        case Calendar.SATURDAY:
////                            listValue2.put(names.get(5), item.getValue());
////                            break;
////                        default:
////                            listValue2.put(names.get(6), item.getValue());
////                            break;
////                    }
////                }
////            });
////            bieudo.setNames(names);
////            for(String item:names){
////                BigDecimal soluong1=listValue1.get(item);
////                BigDecimal soluong2=listValue2.get(item);
////                if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
////                    values1.add(soluong1);
////                }else{
////                    values1.add(BigDecimal.ZERO);
////                }
////                if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
////                    values2.add(soluong2);
////                }else{
////                    values2.add(BigDecimal.ZERO);
////                }
////            }
////            bieudo.setValues1(values1);
////            bieudo.setValues2(values2);
////    }
////
////    private void genBieuDoThang(List<DoiTuong> list,BieuDo2GiaTri bieudo,int year,int month){
////        List<String> names=new ArrayList<>();
////        List<Integer> listday=DateUtils.genListDayOfMonth(year,month);
////        for(Integer day:listday){
////            names.add(day.toString());
////        }
////        List<BigDecimal> values1=new ArrayList<>();
////        List<BigDecimal> values2=new ArrayList<>();
////        Calendar cal=Calendar.getInstance();
////        HashMap<String,BigDecimal> listValue1=new HashMap<>();
////        HashMap<String,BigDecimal> listValue2=new HashMap<>();
////        list.stream().forEach(item->{
////            cal.setTime(item.getDate());
////            if (item.getName().equals("A")) {
////                for(int i=0;i<names.size();i++){
////                   if(listday.get(i)==cal.get(Calendar.DAY_OF_MONTH)){
////                       listValue1.put(names.get(i), item.getValue());
////                       break;
////                   }
////                }
////
////            } else {
////                for(int i=0;i<names.size();i++){
////                    if(listday.get(i)==cal.get(Calendar.DAY_OF_MONTH)){
////                        listValue2.put(names.get(i), item.getValue());
////                        break;
////                    }
////                }
////            }
////        });
////        bieudo.setNames(names);
////        for(String item:names){
////            BigDecimal soluong1=listValue1.get(item);
////            BigDecimal soluong2=listValue2.get(item);
////            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
////                values1.add(soluong1);
////            }else{
////                values1.add(BigDecimal.ZERO);
////            }
////            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
////                values2.add(soluong2);
////            }else{
////                values2.add(BigDecimal.ZERO);
////            }
////        }
////        bieudo.setValues1(values1);
////        bieudo.setValues2(values2);
////    }
////
////    private void genBieuDoNam(List<DoiTuong> list,BieuDo2GiaTri bieudo){
////        List<String> names=Arrays.asList("Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12");
////        List<Integer> listthang=Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
////        List<BigDecimal> values1=new ArrayList<>();
////        List<BigDecimal> values2=new ArrayList<>();
////        Calendar cal=Calendar.getInstance();
////        HashMap<String,BigDecimal> listValue1=new HashMap<>();
////        HashMap<String,BigDecimal> listValue2=new HashMap<>();
////        for(String name:names){
////            listValue1.put(name,BigDecimal.valueOf(0));
////            listValue2.put(name,BigDecimal.valueOf(0));
////        }
////        list.stream().forEach(item->{
////            cal.setTime(item.getDate());
////            if (item.getName().equals("A")) {
////                for(int i=0;i<listthang.size();i++){
////                    if(cal.get(Calendar.MONTH)==(listthang.get(i).intValue()-1)){
////                        BigDecimal value=listValue1.get(names.get(i));
////                        listValue1.put(names.get(i),value.add(item.getValue()));
////                        break;
////                    }
////                }
////            } else {
////                for(int i=0;i<listthang.size();i++){
////                    if(cal.get(Calendar.MONTH)==(listthang.get(i).intValue()-1)){
////                        BigDecimal value=listValue2.get(names.get(i));
////                        listValue2.put(names.get(i),value.add(item.getValue()));
////                        break;
////                    }
////                }
////            }
////        });
////        bieudo.setNames(names);
////        for(String item:names){
////            BigDecimal soluong1=listValue1.get(item);
////            BigDecimal soluong2=listValue2.get(item);
////            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
////                values1.add(soluong1);
////            }else{
////                values1.add(BigDecimal.ZERO);
////            }
////            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
////                values2.add(soluong2);
////            }else{
////                values2.add(BigDecimal.ZERO);
////            }
////        }
////        bieudo.setValues1(values1);
////        bieudo.setValues2(values2);
////    }
//
//
//    private void genBieuDoTyLeLePhiKhoa(List<DoiTuong> list,BieuDo bieudo){
//        List<String> names=new ArrayList<>();
//        List<BigDecimal> values=new ArrayList<>();
//        TreeMap<Long,BigDecimal> listValue=new TreeMap<>();
//        departments.forEach((k,v)->{
//            listValue.put(k,BigDecimal.valueOf(0));
//            names.add(v);
//        });
//        list.stream().forEach(item->{
//            String name=departments.get(item.getId());
//            if(StringUtils.isNotBlank(name)){
//                BigDecimal value=listValue.get(item.getId());
//                listValue.put(item.getId(),value.add(item.getValue()));
//            }else{
//                BigDecimal value=listValue.get(Long.valueOf(999999999));
//                listValue.put(Long.valueOf(999999999),value.add(item.getValue()));
//            }
//        });
//        listValue.forEach((k,v)->{
//            values.add(v);
//        });
//        bieudo.setNames(names);
//        bieudo.setValues(values);
//    }
//
//    private void genBieuDoBNNoitruKhoa(List<DoiTuong> list,BieuDo bieudo){
//        List<String> names=new ArrayList<>();
//        List<BigDecimal> values=new ArrayList<>();
//        TreeMap<Long,BigDecimal> listValue=new TreeMap<>();
//        departmentsIP.forEach((k,v)->{
//            listValue.put(k,BigDecimal.valueOf(0));
//            names.add(v);
//        });
//        list.stream().forEach(item->{
//            String name=departmentsIP.get(item.getId());
//            if(StringUtils.isNotBlank(name)){
//                BigDecimal value=listValue.get(item.getId());
//                listValue.put(item.getId(),value.add(item.getValue()));
//            }
//        });
//        listValue.forEach((k,v)->{
//            values.add(v);
//        });
//        bieudo.setNames(names);
//        bieudo.setValues(values);
//    }
//
//    //Bieu do le phi theo nhom dv
//    public Optional<BieuDoLePhiNhomDV> LePhiTheoNhomDV(){
//        List<DoiTuong12Thang> list=lePhiTheoNhomDV(true).orElse(new ArrayList<>());
//        List<DoiTuong12Thang> listIP=lePhiTheoNhomDV(false).orElse(new ArrayList<>());
//        for(DoiTuong12Thang item:listIP){
//            list.add(item);
//        }
//        BieuDoLePhiNhomDV bieudo=new BieuDoLePhiNhomDV();
//        genBieuDoLePhiNhomDV(list,bieudo);
//        return Optional.ofNullable(bieudo);
//    }
//
//    private Optional<List<DoiTuong12Thang>> lePhiTheoNhomDV(boolean outpatient){
//        Date from=DateUtils.genDateFromOfLast12Month();
//        if(outpatient){
//            List<DoiTuong12Thang> list= indexRepository.lephiDVNgoaitruNhomDV(from).orElse(new ArrayList<>());
//            return Optional.ofNullable(list);
//        }else{
//            List<DoiTuong12Thang> list= indexRepository.lephiDVNoitruNhomDV(from).orElse(new ArrayList<>());
//            return Optional.ofNullable(list);
//        }
//
//    }
//
//
//    private void genBieuDoLePhiNhomDV(List<DoiTuong12Thang> list,BieuDoLePhiNhomDV bieudo){
//        List<String> names=DateUtils.genListMonthName();
//        List<Integer> listthang=DateUtils.genListMonthValue();
//        List<BigDecimal> group_1000057=new ArrayList<>();
//        List<BigDecimal> group_1000058=new ArrayList<>();
//        List<BigDecimal> group_1000059=new ArrayList<>();
//        List<BigDecimal> group_1000060=new ArrayList<>();
//        List<BigDecimal> group_1000061=new ArrayList<>();
//        List<BigDecimal> group_1000062=new ArrayList<>();
//        List<BigDecimal> group_1000063=new ArrayList<>();
//        List<BigDecimal> group_1000065=new ArrayList<>();
//        List<BigDecimal> group_1000067=new ArrayList<>();
//        List<BigDecimal> group_1000077=new ArrayList<>();
//        HashMap<String,BigDecimal> listValue_1000057=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000058=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000059=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000060=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000061=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000062=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000063=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000065=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000067=new HashMap<>();
//        HashMap<String,BigDecimal> listValue_1000077=new HashMap<>();
//        for(String name:names){
//            listValue_1000057.put(name,BigDecimal.valueOf(0));
//            listValue_1000058.put(name,BigDecimal.valueOf(0));
//            listValue_1000059.put(name,BigDecimal.valueOf(0));
//            listValue_1000060.put(name,BigDecimal.valueOf(0));
//            listValue_1000061.put(name,BigDecimal.valueOf(0));
//            listValue_1000062.put(name,BigDecimal.valueOf(0));
//            listValue_1000063.put(name,BigDecimal.valueOf(0));
//            listValue_1000065.put(name,BigDecimal.valueOf(0));
//            listValue_1000067.put(name,BigDecimal.valueOf(0));
//            listValue_1000077.put(name,BigDecimal.valueOf(0));
//        }
//
//        for(DoiTuong12Thang item:list){
//            if(item.getId()!=null){
//                switch (item.getId().intValue()){
//                    case 1000057: SetValueListGroupLevel(item,listValue_1000057,names,listthang); break;
//                    case 1000058:  SetValueListGroupLevel(item,listValue_1000058,names,listthang); break;
//                    case 1000059:  SetValueListGroupLevel(item,listValue_1000059,names,listthang); break;
//                    case 1000060:  SetValueListGroupLevel(item,listValue_1000060,names,listthang); break;
//                    case 1000061:  SetValueListGroupLevel(item,listValue_1000061,names,listthang); break;
//                    case 1000062:  SetValueListGroupLevel(item,listValue_1000062,names,listthang); break;
//                    case 1000063:  SetValueListGroupLevel(item,listValue_1000063,names,listthang); break;
//                    case 1000065:  SetValueListGroupLevel(item,listValue_1000065,names,listthang); break;
//                    case 1000077:  SetValueListGroupLevel(item,listValue_1000077,names,listthang); break;
//                    default:  SetValueListGroupLevel(item,listValue_1000067,names,listthang); break;
//                }
//            }
//        }
//        bieudo.setNames(names);
//
//        for(String item:names){
//            BigDecimal soluong1=listValue_1000057.get(item);
//            BigDecimal soluong2=listValue_1000058.get(item);
//            BigDecimal soluong3=listValue_1000059.get(item);
//            BigDecimal soluong4=listValue_1000060.get(item);
//            BigDecimal soluong5=listValue_1000061.get(item);
//            BigDecimal soluong6=listValue_1000062.get(item);
//            BigDecimal soluong7=listValue_1000063.get(item);
//            BigDecimal soluong8=listValue_1000065.get(item);
//            BigDecimal soluong9=listValue_1000067.get(item);
//            BigDecimal soluong10=listValue_1000077.get(item);
//            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
//                group_1000057.add(soluong1);
//            }else{
//                group_1000057.add(BigDecimal.ZERO);
//            }
//            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
//                group_1000058.add(soluong2);
//            }else{
//                group_1000058.add(BigDecimal.ZERO);
//            }
//
//            if(soluong3!=null &&  soluong3.compareTo(BigDecimal.ZERO)>0){
//                group_1000059.add(soluong3);
//            }else{
//                group_1000059.add(BigDecimal.ZERO);
//            }
//
//            if(soluong4!=null &&  soluong4.compareTo(BigDecimal.ZERO)>0){
//                group_1000060.add(soluong4);
//            }else{
//                group_1000060.add(BigDecimal.ZERO);
//            }
//
//            if(soluong5!=null &&  soluong5.compareTo(BigDecimal.ZERO)>0){
//                group_1000061.add(soluong5);
//            }else{
//                group_1000061.add(BigDecimal.ZERO);
//            }
//
//            if(soluong6!=null &&  soluong6.compareTo(BigDecimal.ZERO)>0){
//                group_1000062.add(soluong6);
//            }else{
//                group_1000062.add(BigDecimal.ZERO);
//            }
//            if(soluong7!=null &&  soluong7.compareTo(BigDecimal.ZERO)>0){
//                group_1000063.add(soluong7);
//            }else{
//                group_1000063.add(BigDecimal.ZERO);
//            }
//            if(soluong8!=null &&  soluong8.compareTo(BigDecimal.ZERO)>0){
//                group_1000065.add(soluong8);
//            }else{
//                group_1000065.add(BigDecimal.ZERO);
//            }
//            if(soluong9!=null &&  soluong9.compareTo(BigDecimal.ZERO)>0){
//                group_1000067.add(soluong9);
//            }else{
//                group_1000067.add(BigDecimal.ZERO);
//            }
//            if(soluong10!=null &&  soluong10.compareTo(BigDecimal.ZERO)>0){
//                group_1000077.add(soluong10);
//            }else{
//                group_1000077.add(BigDecimal.ZERO);
//            }
//        }
//        bieudo.setGroup_1000057(group_1000057);
//        bieudo.setGroup_1000058(group_1000058);
//        bieudo.setGroup_1000059(group_1000059);
//        bieudo.setGroup_1000060(group_1000060);
//        bieudo.setGroup_1000061(group_1000061);
//        bieudo.setGroup_1000062(group_1000062);
//        bieudo.setGroup_1000063(group_1000063);
//        bieudo.setGroup_1000065(group_1000065);
//        bieudo.setGroup_1000067(group_1000067);
//        bieudo.setGroup_1000077(group_1000077);
//    }
//
//    public void SetValueListGroupLevel(DoiTuong12Thang item,HashMap<String,BigDecimal> listValue,List<String> names,List<Integer> listthang){
//        for(int i=0;i<listthang.size();i++){
//            if(item.getMonth()!=null && item.getMonth().intValue()==listthang.get(i).intValue()){
//                BigDecimal value=listValue.get(names.get(i));
//                listValue.put(names.get(i),value.add(item.getQuantity()));
//                break;
//            }
//        }
//    }
//}
