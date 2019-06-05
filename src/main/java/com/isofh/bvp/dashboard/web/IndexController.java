package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.model.*;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.apache.commons.jexl2.UnifiedJEXL;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
//    @Autowired
//    IndexService service;
//    @Autowired
//    RootRepository repository;
//    @Autowired
//    IndexRepository indexRepository;

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
//    @GetMapping("/last-update")
//    public ResponseEntity<Date> lastUpdate(){
//        Date date=repository.getLastUpdated();
//        return new ResponseEntity<Date>(date,HttpStatus.OK);
//    }

    @GetMapping("/checkup")
    public ResponseEntity<BieuDo3GiaTri> luotkham(){
//        try{
//            BieuDo3GiaTri item=repository.getLuotkham();
//            return new ResponseEntity<BieuDo3GiaTri>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDo3GiaTri>(new BieuDo3GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/checkup-department")
    public ResponseEntity<BieuDo2GiaTri> luotkhamKhoa(){
//        try{
//            BieuDo2GiaTri item=repository.getLuotkhamKhoa();
//            return new ResponseEntity<BieuDo2GiaTri>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDo2GiaTri>(new BieuDo2GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-dv")
    public ResponseEntity<BieuDo3GiaTri> lephi(){
//        try{
//            BieuDo3GiaTri item=repository.getLephidv();
//            return new ResponseEntity<BieuDo3GiaTri>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDo3GiaTri>(new BieuDo3GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-khoa")
    public ResponseEntity<BieuDo> lephiCacKhoa(){
//        try{
//            BieuDo item=repository.getLephikhoa();
//            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
    }

    @GetMapping("/list-department-name")
    public ResponseEntity<List<String>> listDepartmentName(){
//        try{
//            List<String> names=new ArrayList<>();
//            IndexService.departments.forEach((k,v)->{
//                names.add(v);
//            });
//            return new ResponseEntity<List<String>>(names,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.OK);
    }

    @GetMapping("/list-department-ip")
    public ResponseEntity<List<String>> listDepartmentInpatient(){
//        try{
//            List<String> names=new ArrayList<>();
//            IndexService.departmentsIP.forEach((k,v)->{
//                names.add(v);
//            });
//            return new ResponseEntity<List<String>>(names,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<List<String>>(new ArrayList<>(),HttpStatus.OK);
    }


    @GetMapping("/bnnoitru-khoa")
    public ResponseEntity<BieuDo> CountPatientInDepartmentIP(){
//        try{
//            BieuDo item=repository.getBnNoitru();
//            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
    }

    @GetMapping("/lephidvnhom")
    public ResponseEntity<BieuDoLePhiNhomDV> LePhiDVNhomDV(){
//        try{
//            BieuDoLePhiNhomDV item=repository.getBieuDoLePhiNhomDV();
//            return new ResponseEntity<BieuDoLePhiNhomDV>(item,HttpStatus.OK);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return new ResponseEntity<BieuDoLePhiNhomDV>(new BieuDoLePhiNhomDV(),HttpStatus.OK);
    }


//    @GetMapping("/download")
//    public void download(HttpServletResponse response,String query) {
//        boolean error=false;
//        try{
//            if(StringUtils.isNotBlank(query)){
//                query=query.replaceAll(";"," ");
//            }
//            List<Object[]> list=indexRepository.SQLquery(query).orElse(new ArrayList<>());
//            if(list.size()==0) throw new Exception();
//            Resource resource = new ClassPathResource("myfile.xlsx");
//            InputStream fileIn = resource.getInputStream();
//            Workbook wb = WorkbookFactory.create(fileIn);
//            Sheet sheet = wb.getSheetAt(0);
//            int colummNum=1;
//            try{
//                colummNum = list.get(0).length;
//            }catch (Exception e){
//
//            }
//            if(colummNum==1){
//                for(int i=0;i<list.size();i++){
//                    Object item=list.get(i);
//                    Row row=sheet.createRow(0+i);
//                    row.createCell(0);
//                    if(item!=null){
//                        row.getCell(0).setCellValue(String.valueOf(item));
//                    }
//                }
//            }else{
//                for(int i=0;i<list.size();i++){
//                    Object[] item=list.get(i);
//                    Row row=sheet.createRow(0+i);
//                    for(int j=0;j<colummNum;j++){
//                        row.createCell(j);
//                    }
//                    if(item!=null){
//                        for(int j=0;j<colummNum;j++){
//                            if(item[j]!=null)
//                                row.getCell(j).setCellValue(String.valueOf(item[j]));
//                        }
//                    }
//                }
//            }
//
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename=" + "BaoCao.xlsx");
//            ServletOutputStream out = response.getOutputStream();
//            wb.write(out);
//            out.flush();
//            out.close();
//
//        }catch (Exception e){
//            error=true;
//        }
//
//        if(error){
//            try{
////                Writer writer = Files.newBufferedWriter(Paths.get("myfile.csv"));
//                String errorStr="Cau lenh SQL khong dung nen gay ra loi chuong trinh";
//                List<ErrorExport> errors=new ArrayList<>();
//                errors.add(new ErrorExport(errorStr));
//                StatefulBeanToCsv btcsv = new StatefulBeanToCsvBuilder(response.getWriter())
//                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
////                        .withMappingStrategy(mapStrategy)
////                        .withSeparator(',')
//                        .build();
//                btcsv.write(errors);
//            }catch (Exception e){
//
//            }
//
//        }
//    }
//    @GetMapping("/export")
//    public String report(){
//        return "export";
//    }
}
