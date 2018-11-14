package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.common.DateUtils;
import com.isofh.bvp.dashboard.model.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    IndexService service;
    @Autowired
    RootRepository repository;
    @Autowired
    IndexRepository indexRepository;

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @GetMapping("/checkup")
//    public ResponseEntity<BieuDo2GiaTri> luotkham(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
//            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
//        try{
//            BieuDo2GiaTri item=service.LuotKhamBenh(year,month,week).orElse(new BieuDo2GiaTri());
//            return new ResponseEntity<BieuDo2GiaTri>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<BieuDo2GiaTri>(new BieuDo2GiaTri(),HttpStatus.OK);
//    }
    @GetMapping("/checkup")
    public ResponseEntity<BieuDo2GiaTri> luotkham(){
        try{
            BieuDo2GiaTri item=repository.getLuotkham();
            return new ResponseEntity<BieuDo2GiaTri>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo2GiaTri>(new BieuDo2GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/checkup-department")
    public ResponseEntity<BieuDo2GiaTri> luotkhamKhoa(){
        try{
            BieuDo2GiaTri item=repository.getLuotkhamKhoa();
            return new ResponseEntity<BieuDo2GiaTri>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo2GiaTri>(new BieuDo2GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-dv")
    public ResponseEntity<BieuDo3GiaTri> lephi(){
        try{
            BieuDo3GiaTri item=repository.getLephidv();
            return new ResponseEntity<BieuDo3GiaTri>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo3GiaTri>(new BieuDo3GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-khoa")
    public ResponseEntity<BieuDo> lephiCacKhoa(){
        try{
            BieuDo item=repository.getLephikhoa();
            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
    }

    @GetMapping("/list-department-name")
    public ResponseEntity<List<String>> listDepartmentName(){
        try{
            List<String> names=new ArrayList<>();
            IndexService.departments.forEach((k,v)->{
                names.add(v);
            });
            return new ResponseEntity<List<String>>(names,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.OK);
    }

    @GetMapping("/list-department-ip")
    public ResponseEntity<List<String>> listDepartmentInpatient(){
        try{
            List<String> names=new ArrayList<>();
            IndexService.departmentsIP.forEach((k,v)->{
                names.add(v);
            });
            return new ResponseEntity<List<String>>(names,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.OK);
    }

//    @GetMapping("/bnnoitru-khoa")
//    public ResponseEntity<BieuDo> CountPatientInDepartmentIP(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
//            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
//        try{
//            BieuDo item=service.BNNoiTruKhoa(year,month,week).orElse(new BieuDo());
//            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
//    }

    @GetMapping("/bnnoitru-khoa")
    public ResponseEntity<BieuDo> CountPatientInDepartmentIP(){
        try{
            BieuDo item=repository.getBnNoitru();
            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
    }

    @GetMapping("/lephidvnhom")
    public ResponseEntity<BieuDoLePhiNhomDV> LePhiDVNhomDV(){
        try{
            BieuDoLePhiNhomDV item=repository.getBieuDoLePhiNhomDV();
            return new ResponseEntity<BieuDoLePhiNhomDV>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDoLePhiNhomDV>(new BieuDoLePhiNhomDV(),HttpStatus.OK);
    }


    @GetMapping("/open")
    public void downloadGeneralSaleSmsSearch(HttpServletResponse response) {
//    public ResponseEntity<BieuDoLePhiNhomDV> openfile(){

        try{

            Date date=DateUtils.genFirstDayOfMonthCurrent();
            List<Object[]> LuotKhamTest=indexRepository.LuotKhamTest(date).orElse(new ArrayList<>());
//            BieuDo2GiaTri item=repository.getLuotkham();
//            List<DoiTuong12Thang> list= indexRepository.LuotKhamTheoKhoa(date).orElse(new ArrayList<>());
//            for(DoiTuong12Thang item:list){
//                item.setName("Thế giới, to thật to;'");
//            }
//            Writer writer = Files.newBufferedWriter(Paths.get("myfile.csv"));

//            String[] columns = new String[]{"id", "name", "type","quantity","month","year"};
//            mapStrategy.setColumnMapping(columns);

//            StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(response.getWriter())
//                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
////                    .withMappingStrategy(mapStrategy)
//                    .withSeparator(',')
//                    .build();
//            btcsv.write(LuotKhamTest);


            int i=LuotKhamTest.get(0).length;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void countField(Object[] item) {
        if (item == null) {
            return;
        }

        System.out.println("Tong so field:" +item.length);
    }
}
