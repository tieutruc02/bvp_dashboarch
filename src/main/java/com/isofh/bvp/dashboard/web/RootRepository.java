package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.model.BieuDo;
import com.isofh.bvp.dashboard.model.BieuDo2GiaTri;
import com.isofh.bvp.dashboard.model.BieuDo3GiaTri;
import com.isofh.bvp.dashboard.model.BieuDoLePhiNhomDV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Component
public class RootRepository {
    public BieuDo3GiaTri luotkham=new BieuDo3GiaTri();
    public BieuDo2GiaTri luotkhamkhoa=new BieuDo2GiaTri();
    public BieuDo3GiaTri lephidv=new BieuDo3GiaTri();
    public BieuDo lephikhoa=new BieuDo();
    public BieuDo bnNoitru=new BieuDo();
    public BieuDoLePhiNhomDV lePhiNhomDV=new BieuDoLePhiNhomDV();
    public Date lastUpdated=new Date();

//    @Autowired
//    IndexService service;

    @PostConstruct
    public void init(){
        loadReportObject();
    }

    public synchronized void loadReportObject(){
        try{
            System.out.println("begin load Object : "+new Date());
//            luotkham=service.LuotKhamBenh().orElse(new BieuDo3GiaTri());
//            luotkhamkhoa=service.LuotKhamBenhKhoa().orElse(new BieuDo2GiaTri());
//            bnNoitru=service.BNNoiTruKhoa().orElse(new BieuDo());
//            lephikhoa=service.TyLeLePhiKhoa().orElse(new BieuDo());
//            lephidv=service.DoanhThuLePhiDV().orElse(new BieuDo3GiaTri());
            System.out.println("End 4 cai dau :"+new Date());
//            lePhiNhomDV=service.LePhiTheoNhomDV().orElse(new BieuDoLePhiNhomDV());
            System.out.println("end get data:"+new Date());
            lastUpdated=new Date();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
// link cron : https://www.freeformatter.com/cron-expression-generator-quartz.html
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void refreshWhenNewDay(){
        System.out.println("Bat dau scheduled 0h" +new Date());
        loadReportObject();
    }

    @Scheduled(cron = "0 0 12 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void refreshWhenNewDay1(){
        loadReportObject();
    }
    @Scheduled(cron = "0 50 6 * * *")
    @Transactional(rollbackFor = Exception.class)
    public void refreshWhenNewDay3(){
        loadReportObject();
    }


    public  BieuDo3GiaTri getLuotkham(){
        return luotkham;
    }
    public  BieuDo2GiaTri getLuotkhamKhoa(){
        return luotkhamkhoa;
    }

    public  BieuDo3GiaTri getLephidv(){
        return lephidv;
    }
    public  BieuDo getLephikhoa(){
        return lephikhoa;
    }
    public  BieuDo getBnNoitru(){
        return bnNoitru;
    }
    public BieuDoLePhiNhomDV getBieuDoLePhiNhomDV(){
        return lePhiNhomDV;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
