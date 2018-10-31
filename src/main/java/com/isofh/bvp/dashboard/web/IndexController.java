package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.common.DateUtils;
import com.isofh.bvp.dashboard.model.BieuDo;
import com.isofh.bvp.dashboard.model.BieuDo2GiaTri;
import com.isofh.bvp.dashboard.model.BieuDo3GiaTri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    IndexService service;

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/checkup")
    public ResponseEntity<BieuDo2GiaTri> luotkham(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        try{
            BieuDo2GiaTri item=service.LuotKhamBenh(year,month,week).orElse(new BieuDo2GiaTri());
            return new ResponseEntity<BieuDo2GiaTri>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo2GiaTri>(new BieuDo2GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-dv")
    public ResponseEntity<BieuDo3GiaTri> lephi(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        try{
            BieuDo3GiaTri item=service.DoanhThuLePhiDV(year,month,week).orElse(new BieuDo3GiaTri());
            return new ResponseEntity<BieuDo3GiaTri>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo3GiaTri>(new BieuDo3GiaTri(),HttpStatus.OK);
    }

    @GetMapping("/lephi-khoa")
    public ResponseEntity<BieuDo> lephiCacKhoa(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        try{
            BieuDo item=service.TyLeLePhiKhoa(year,month,week).orElse(new BieuDo());
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

    @GetMapping("/bnnoitru-khoa")
    public ResponseEntity<BieuDo> CountPatientInDepartmentIP(int year, @RequestParam(value = "month", required = false, defaultValue = "0")int month
            , @RequestParam(value = "week", required = false, defaultValue = "0") int week){
        try{
            BieuDo item=service.BNNoiTruKhoa(year,month,week).orElse(new BieuDo());
            return new ResponseEntity<BieuDo>(item,HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<BieuDo>(new BieuDo(),HttpStatus.OK);
    }



}
