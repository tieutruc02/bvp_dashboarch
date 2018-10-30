package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.common.DateUtils;
import com.isofh.bvp.dashboard.model.BieuDo;
import com.isofh.bvp.dashboard.model.BieuDo3GiaTri;
import com.isofh.bvp.dashboard.model.BieuDo2GiaTri;
import com.isofh.bvp.dashboard.model.DoiTuong;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.*;

@Service
public class IndexService {
    @Autowired
    IndexRepository indexRepository;
    public static TreeMap<Long,String> departments=new TreeMap<>();

    @PostConstruct
    public void init(){
        departments.put(Long.valueOf(1000241),"Khoa Vi sinh và Labo lao chuẩn Quốc gia");
        departments.put(Long.valueOf(1000240),"Khoa Chẩn đoán hình ảnh");
        departments.put(Long.valueOf(1000333),"Khoa Cấp cứu");
        departments.put(Long.valueOf(1000264),"Khoa Bệnh phổi nghề nghiệp");
        departments.put(Long.valueOf(1000262),"Trung tâm xạ trị");
        departments.put(Long.valueOf(1000243),"Khoa Hóa sinh miễn dịch");
        departments.put(Long.valueOf(1000268),"Khoa Khám bệnh ĐK TYC");
        departments.put(Long.valueOf(1000271),"Khoa Thăm dò và Phục hồi chức năng");
        departments.put(Long.valueOf(1000270),"Khoa Gây mê hồi sức");
        departments.put(Long.valueOf(1000233),"Khoa Lao hô hấp");
        departments.put(Long.valueOf(1000235),"Khoa Nội tổng hợp");
        departments.put(Long.valueOf(1000242),"Khoa Huyết học");
        departments.put(Long.valueOf(1000272),"Khoa Phẫu thuật lồng ngực");
        departments.put(Long.valueOf(999999999),"Khác");
    }

    public Optional<List<Object[]>> test(){
        return indexRepository.listTest();
    }

    public Optional<BieuDo3GiaTri> DoanhThuLePhiDV(int year, int month, int week){
        List<DoiTuong> listOP=lephiDV(year,month,week,true).orElse(new ArrayList<>());
        List<DoiTuong> listIP=lephiDV(year,month,week,false).orElse(new ArrayList<>());
        for(DoiTuong item:listOP){
            listIP.add(item);
        }
        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
        if(week>0){
            genBieuDoTuan(listIP,bieudo);
        }else if (month>0){
            genBieuDoThang(listIP,bieudo,year,month);
        }else{
            genBieuDoNam(listIP,bieudo);
        }
        BieuDo3GiaTri bieuDo3GiaTri=new BieuDo3GiaTri();
        bieuDo3GiaTri.setNames(bieudo.getNames());
        bieuDo3GiaTri.setValues1(bieudo.getValues1());
        bieuDo3GiaTri.setValues2(bieudo.getValues2());
        List<BigDecimal> listvalue3=new ArrayList<>();
        for(int i=0;i<bieudo.getNames().size();i++){
            listvalue3.add(bieudo.getValues1().get(i).add(bieudo.getValues2().get(i)));
        }
        bieuDo3GiaTri.setValues3(listvalue3);
        return Optional.ofNullable(bieuDo3GiaTri);
    }
    private Optional<List<DoiTuong>> lephiDV(int year,int month,int week,boolean isOutPatient){
        Date from=DateUtils.getFromAndToDate(year,month,week,true);
        Date to=DateUtils.getFromAndToDate(year,month,week,false);
        List<DoiTuong> list=new ArrayList<>();
        if(isOutPatient){
            list = indexRepository.lephiDVNgoaitru(from,to).orElse(new ArrayList<>());
        }else{
            list = indexRepository.lephiDVNoitru(from,to).orElse(new ArrayList<>());
        }
        return Optional.ofNullable(list);
    }

    public Optional<BieuDo> TyLeLePhiKhoa(int year,int month,int week){
        List<DoiTuong> items=lephiDVKhoa(year,month,week).orElse(new ArrayList<>());
        BieuDo bieudo=new BieuDo();
        genBieuDoTyLeLePhiKhoa(items,bieudo);
        return Optional.ofNullable(bieudo);
    }

    private Optional<List<DoiTuong>> lephiDVKhoa(int year,int month,int week){
        Date from=DateUtils.getFromAndToDate(year,month,week,true);
        Date to=DateUtils.getFromAndToDate(year,month,week,false);
        List<DoiTuong> list=indexRepository.lephiDVCacKhoa(from,to).orElse(new ArrayList<>());
        return Optional.ofNullable(list);
    }
    public Optional<BieuDo2GiaTri> LuotKhamBenh(int year, int month, int week){
        List<DoiTuong> list=LuotKham(year,month,week).orElse(new ArrayList<>());
        BieuDo2GiaTri bieudo=new BieuDo2GiaTri();
        if(week>0){
            genBieuDoTuan(list,bieudo);
        }else if(month>0){
            genBieuDoThang(list,bieudo,year,month);
        }else{
            genBieuDoNam(list,bieudo);
        }

        return Optional.ofNullable(bieudo);
    }



    private Optional<List<DoiTuong>> LuotKham(int year, int month, int week){
        Date from=DateUtils.getFromAndToDate(year,month,week,true);
        Date to=DateUtils.getFromAndToDate(year,month,week,false);
        List<DoiTuong> list= indexRepository.LuotKhamTheoNgay(from,to).orElse(new ArrayList<>());
        return Optional.ofNullable(list);
    }

    private void genBieuDoTuan(List<DoiTuong> list,BieuDo2GiaTri bieudo){
        List<String> names=Arrays.asList("Thứ hai","Thứ ba","Thứ tư","Thứ năm","Thứ sáu","Thứ bảy","Chủ nhật");
        List<BigDecimal> values1=new ArrayList<>();
        List<BigDecimal> values2=new ArrayList<>();
            Calendar cal=Calendar.getInstance();
            HashMap<String,BigDecimal> listValue1=new HashMap<>();
            HashMap<String,BigDecimal> listValue2=new HashMap<>();
            list.stream().forEach(item->{
                cal.setTime(item.getDate());
                if (item.getName().equals("A")) {
                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY:
                            listValue1.put(names.get(0), item.getValue());
                            break;
                        case Calendar.TUESDAY:
                            listValue1.put(names.get(1), item.getValue());
                            break;
                        case Calendar.WEDNESDAY:
                            listValue1.put(names.get(2), item.getValue());
                            break;
                        case Calendar.THURSDAY:
                            listValue1.put(names.get(3), item.getValue());
                            break;
                        case Calendar.FRIDAY:
                            listValue1.put(names.get(4), item.getValue());
                            break;
                        case Calendar.SATURDAY:
                            listValue1.put(names.get(5), item.getValue());
                            break;
                        default:
                            listValue1.put(names.get(6), item.getValue());
                            break;
                    }
                } else {
                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
                        case Calendar.MONDAY:
                            listValue2.put(names.get(0), item.getValue());
                            break;
                        case Calendar.TUESDAY:
                            listValue2.put(names.get(1), item.getValue());
                            break;
                        case Calendar.WEDNESDAY:
                            listValue2.put(names.get(2), item.getValue());
                            break;
                        case Calendar.THURSDAY:
                            listValue2.put(names.get(3), item.getValue());
                            break;
                        case Calendar.FRIDAY:
                            listValue2.put(names.get(4), item.getValue());
                            break;
                        case Calendar.SATURDAY:
                            listValue2.put(names.get(5), item.getValue());
                            break;
                        default:
                            listValue2.put(names.get(6), item.getValue());
                            break;
                    }
                }
            });
            bieudo.setNames(names);
            for(String item:names){
                BigDecimal soluong1=listValue1.get(item);
                BigDecimal soluong2=listValue2.get(item);
                if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
                    values1.add(soluong1);
                }else{
                    values1.add(BigDecimal.ZERO);
                }
                if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
                    values2.add(soluong2);
                }else{
                    values2.add(BigDecimal.ZERO);
                }
            }
            bieudo.setValues1(values1);
            bieudo.setValues2(values2);
    }

    private void genBieuDoThang(List<DoiTuong> list,BieuDo2GiaTri bieudo,int year,int month){
        List<String> names=new ArrayList<>();
        List<Integer> listday=DateUtils.genListDayOfMonth(year,month);
        for(Integer day:listday){
            names.add(day.toString());
        }
        List<BigDecimal> values1=new ArrayList<>();
        List<BigDecimal> values2=new ArrayList<>();
        Calendar cal=Calendar.getInstance();
        HashMap<String,BigDecimal> listValue1=new HashMap<>();
        HashMap<String,BigDecimal> listValue2=new HashMap<>();
        list.stream().forEach(item->{
            cal.setTime(item.getDate());
            if (item.getName().equals("A")) {
                for(int i=0;i<names.size();i++){
                   if(listday.get(i)==cal.get(Calendar.DAY_OF_MONTH)){
                       listValue1.put(names.get(i), item.getValue());
                       break;
                   }
                }

            } else {
                for(int i=0;i<names.size();i++){
                    if(listday.get(i)==cal.get(Calendar.DAY_OF_MONTH)){
                        listValue2.put(names.get(i), item.getValue());
                        break;
                    }
                }
            }
        });
        bieudo.setNames(names);
        for(String item:names){
            BigDecimal soluong1=listValue1.get(item);
            BigDecimal soluong2=listValue2.get(item);
            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
                values1.add(soluong1);
            }else{
                values1.add(BigDecimal.ZERO);
            }
            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
                values2.add(soluong2);
            }else{
                values2.add(BigDecimal.ZERO);
            }
        }
        bieudo.setValues1(values1);
        bieudo.setValues2(values2);
    }

    private void genBieuDoNam(List<DoiTuong> list,BieuDo2GiaTri bieudo){
        List<String> names=Arrays.asList("Tháng 1","Tháng 2","Tháng 3","Tháng 4","Tháng 5","Tháng 6","Tháng 7","Tháng 8","Tháng 9","Tháng 10","Tháng 11","Tháng 12");
        List<Integer> listthang=Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12);
        List<BigDecimal> values1=new ArrayList<>();
        List<BigDecimal> values2=new ArrayList<>();
        Calendar cal=Calendar.getInstance();
        HashMap<String,BigDecimal> listValue1=new HashMap<>();
        HashMap<String,BigDecimal> listValue2=new HashMap<>();
        for(String name:names){
            listValue1.put(name,BigDecimal.valueOf(0));
            listValue2.put(name,BigDecimal.valueOf(0));
        }
        list.stream().forEach(item->{
            cal.setTime(item.getDate());
            if (item.getName().equals("A")) {
                for(int i=0;i<listthang.size();i++){
                    if(cal.get(Calendar.MONTH)==(listthang.get(i).intValue()-1)){
                        BigDecimal value=listValue1.get(names.get(i));
                        listValue1.put(names.get(i),value.add(item.getValue()));
                        break;
                    }
                }
            } else {
                for(int i=0;i<listthang.size();i++){
                    if(cal.get(Calendar.MONTH)==(listthang.get(i).intValue()-1)){
                        BigDecimal value=listValue2.get(names.get(i));
                        listValue2.put(names.get(i),value.add(item.getValue()));
                        break;
                    }
                }
            }
        });
        bieudo.setNames(names);
        for(String item:names){
            BigDecimal soluong1=listValue1.get(item);
            BigDecimal soluong2=listValue2.get(item);
            if(soluong1!=null && soluong1.compareTo(BigDecimal.ZERO)>0){
                values1.add(soluong1);
            }else{
                values1.add(BigDecimal.ZERO);
            }
            if(soluong2!=null &&  soluong2.compareTo(BigDecimal.ZERO)>0){
                values2.add(soluong2);
            }else{
                values2.add(BigDecimal.ZERO);
            }
        }
        bieudo.setValues1(values1);
        bieudo.setValues2(values2);
    }


    private void genBieuDoTyLeLePhiKhoa(List<DoiTuong> list,BieuDo bieudo){
        List<String> names=new ArrayList<>();
        List<BigDecimal> values=new ArrayList<>();
        TreeMap<Long,BigDecimal> listValue=new TreeMap<>();
        departments.forEach((k,v)->{
            listValue.put(k,BigDecimal.valueOf(0));
            names.add(v);
        });
        list.stream().forEach(item->{
            String name=departments.get(item.getId());
            if(StringUtils.isNotBlank(name)){
                BigDecimal value=listValue.get(item.getId());
                listValue.put(item.getId(),value.add(item.getValue()));
            }else{
                BigDecimal value=listValue.get(Long.valueOf(999999999));
                listValue.put(Long.valueOf(999999999),value.add(item.getValue()));
            }
        });
        listValue.forEach((k,v)->{
            values.add(v);
        });
        bieudo.setNames(names);
        bieudo.setValues(values);
    }

}
