package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.model.DoiTuong;
import com.isofh.bvp.dashboard.model.DoiTuong12Thang;
import com.isofh.bvp.dashboard.model.LuotKham;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class IndexRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<List<Object[]>> listTest(){
        StringBuffer sql=new StringBuffer("select * from his_department");
        List<Object[]> list=(List<Object[]>)entityManager.createNativeQuery(sql.toString()).setMaxResults(20).getResultList();
        if(list!=null && list.size()>0){
            System.out.println("");
        }
        return Optional.ofNullable(list);
    }

    public Optional<List<DoiTuong>> lephiDVCacKhoa(Date fromDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select HIS_DEPARTMENT_NAME,his_department_id,SUM(AMOUNTINVOICE) from ( ")
                .append("  select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE ")
                .append("   from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" AND hrv.INVOICESERVICETYPE is null  and hrv.ISSERVICEINHOSPITAL = 'Y' ");
        doanhthu.append(" union all ")
                .append(" select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE  ")
                .append(" from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" )   group by HIS_DEPARTMENT_NAME,his_department_id ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        query.setParameter(2,fromDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                Long id=Long.valueOf(0);
                String name="";
                if(StringUtils.isNotBlank((String)item[0])){
                    id=Long.valueOf(((BigDecimal)item[1]).longValue());
                    name=(String)item[0];
                }
                items.add(new DoiTuong(id,name,null,(BigDecimal)item[2]));
            });
        }
        return Optional.ofNullable(items);
    }

//    public Optional<List<DoiTuong>> lephiDVNgoaitru(Date fromDate,Date toDate){
//        StringBuffer doanhthu=new StringBuffer();
//        doanhthu.append(" select SUM(hrv.AMOUNTINVOICE) tien,trunc(paytime) paytime ")
//                .append(" from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
//                .append(" WHERE hrv.PAYTIME between ? and ? ")
//                .append(" AND hrv.INVOICESERVICETYPE is null and hrv.ISSERVICEINHOSPITAL = 'Y' ")
//                .append(" group by trunc(paytime) ")
//                .append(" order by paytime ");
//        Query query=entityManager.createNativeQuery(doanhthu.toString());
//        query.setParameter(1,fromDate);
//        query.setParameter(2,toDate);
//        List<Object[]> list=query.getResultList();
//        List<DoiTuong> items=new ArrayList<>();
//        if(list!=null && list.size()>0){
//            list.stream().forEach(item->{
//                items.add(new DoiTuong(Long.valueOf(0),"A",(Date)item[1],(BigDecimal)item[0]));
//            });
//        }
//        return Optional.ofNullable(items);
//    }
//
//    public Optional<List<DoiTuong>> lephiDVNoitru(Date fromDate,Date toDate){
//        StringBuffer doanhthu=new StringBuffer();
//        doanhthu.append(" select SUM(hrv.AMOUNTINVOICE) tien,trunc(paytime) paytime ")
//                .append("  from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv  ")
//                .append(" WHERE hrv.PAYTIME between ? and ? ")
//                .append(" group by trunc(paytime) ")
//                .append(" order by paytime ");
//        Query query=entityManager.createNativeQuery(doanhthu.toString());
//        query.setParameter(1,fromDate);
//        query.setParameter(2,toDate);
//        List<Object[]> list=query.getResultList();
//        List<DoiTuong> items=new ArrayList<>();
//        if(list!=null && list.size()>0){
//            list.stream().forEach(item->{
//                items.add(new DoiTuong(Long.valueOf(1),"S",(Date)item[1],(BigDecimal)item[0]));
//            });
//        }
//        return Optional.ofNullable(items);
//    }

    public Optional<List<DoiTuong12Thang>> lephiDVNgoaitru(Date fromDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE) tien, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" AND hrv.INVOICESERVICETYPE is null and hrv.ISSERVICEINHOSPITAL = 'Y'  ")
                .append(" group by hrv.paytime) ")
                .append(" group by thang,nam ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong12Thang> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                items.add(new DoiTuong12Thang("S",(BigDecimal)item[0],Integer.valueOf((String)item[1]),Integer.valueOf((String)item[2])));//S thay cho ngoai tru
            });
        }
        return Optional.ofNullable(items);
    }

    public Optional<List<DoiTuong12Thang>> lephiDVNoitru(Date fromDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE) tien, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" group by hrv.paytime) ")
                .append(" group by thang,nam ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong12Thang> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                items.add(new DoiTuong12Thang("A",(BigDecimal)item[0],Integer.valueOf((String)item[1]),Integer.valueOf((String)item[2])));//A thay cho noi tru
            });
        }
        return Optional.ofNullable(items);
    }

    /**
     * le phi dich vu theo tung nhom dich vu
     * @param fromDate
     * @return
     */
    public Optional<List<DoiTuong12Thang>> lephiDVNgoaitruNhomDV(Date fromDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select HIS_SERVICEGROUPLEVEL1_ID,sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE) tien,hrv.HIS_SERVICEGROUPLEVEL1_ID, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" AND hrv.INVOICESERVICETYPE is null and hrv.ISSERVICEINHOSPITAL = 'Y'  ")
                .append(" group by hrv.paytime,hrv.HIS_SERVICEGROUPLEVEL1_ID) ")
                .append(" where tien > 0  ")
                .append(" group by thang,nam,HIS_SERVICEGROUPLEVEL1_ID ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong12Thang> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                if(item[0]!=null){
                    items.add(new DoiTuong12Thang(Long.valueOf(((BigDecimal)item[0]).longValue()),"",(BigDecimal)item[1],Integer.valueOf((String)item[2]),Integer.valueOf((String)item[3])));
                }else{
                    items.add(new DoiTuong12Thang(Long.valueOf(0),"",(BigDecimal)item[1],Integer.valueOf((String)item[2]),Integer.valueOf((String)item[3])));
                }

            });
        }
        return Optional.ofNullable(items);
    }

    public Optional<List<DoiTuong12Thang>> lephiDVNoitruNhomDV(Date fromDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select HIS_SERVICEGROUPLEVEL1_ID, sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE) tien,hrv.HIS_SERVICEGROUPLEVEL1_ID, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" group by hrv.paytime,hrv.HIS_SERVICEGROUPLEVEL1_ID) ")
                .append(" where tien > 0  ")
                .append(" group by thang,nam,HIS_SERVICEGROUPLEVEL1_ID ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong12Thang> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                if(item[0]!=null){
                    items.add(new DoiTuong12Thang(Long.valueOf(((BigDecimal)item[0]).longValue()),"",(BigDecimal)item[1],Integer.valueOf((String)item[2]),Integer.valueOf((String)item[3])));
                }else{
                    items.add(new DoiTuong12Thang(Long.valueOf(0),"",(BigDecimal)item[1],Integer.valueOf((String)item[2]),Integer.valueOf((String)item[3])));
                }
            });
        }
        return Optional.ofNullable(items);
    }

//    public Optional<List<DoiTuong>> LuotKhamTheoNgay(Date fromDate, Date toDate){
//        StringBuffer sql=new StringBuffer();
//        sql.append("select his_patienttype_id,regdate,count(his_patienthistory_id) soluong from (Select his_patienthistory_id,trunc(regdate) regdate,his_patienttype_id from HIS_RV_DS_BN_KHAMBENH_KHONG_BA  ")
//                .append(" where REGDATE between ? and ? ) ")
//                .append(" group by regdate,his_patienttype_id ")
//                .append(" order by regdate ");
//        Query query=entityManager.createNativeQuery(sql.toString());
//        query.setParameter(1,fromDate);
//        query.setParameter(2,toDate);
//        List<DoiTuong> list=new ArrayList<>();
//        List<Object[]> listOb=query.getResultList();
//        if(listOb!=null && listOb.size()>0){
//            listOb.stream().forEach(item->{
//                list.add(new DoiTuong(null,(String)item[0],(Date)item[1],(BigDecimal)item[2]));
//            });
//        }
//        return Optional.ofNullable(list);
//    }
    public Optional<List<DoiTuong12Thang>> LuotKhamTheoThang(Date fromDate){
        StringBuffer sql=new StringBuffer();
        sql.append("select his_patienttype_id,count(soluong),thang,nam from (select kb.his_patienttype_id,kb.regdate,count(his_patienthistory_id) soluong, ")
                .append(" to_char(kb.regdate,'MM') thang, ")
                .append(" to_char(kb.regdate,'yyyy') nam from  ")
                .append(" (Select ph.HIS_PATIENTHISTORY_ID,ph.HIS_DEPARTMENT_ID,ph.REGDATE,ph.HIS_PATIENTTYPE_ID ")
                .append(" from HIS_CHECKUP hc ")
                .append(" inner join HIS_PATIENTHISTORY ph on ph.HIS_PATIENTHISTORY_ID = hc.HIS_PATIENTHISTORY_ID ")
                .append(" where  hc.ISACTIVE     = 'Y' and ph.ISINPATIENT  = 'N' and hc.ISNOTCOUNTED = 'N' ")
                .append(" and ph.ISOUTPATIENTTREATMENT = 'N' and ph.ISRECHECKUPPATIENT = 'N' and NVL(hc.STATUS, 'WT')!= 'Canceled'and ph.ISNOTPATIENT='N') kb ")
                .append(" where kb.REGDATE between ? and sysdate  ")
                .append(" group by kb.regdate,kb.his_patienttype_id ) ")
                .append(" group by his_patienttype_id,thang,nam ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(sql.toString());
        query.setParameter(1,fromDate);
        List<DoiTuong12Thang> list=new ArrayList<>();
        List<Object[]> listOb=query.getResultList();
        if(listOb!=null && listOb.size()>0){
            listOb.stream().forEach(item->{
                list.add(new DoiTuong12Thang((String)item[0],(BigDecimal)item[1],Integer.valueOf((String)item[2]),Integer.valueOf((String)item[3])));
            });
        }
        return Optional.ofNullable(list);
    }

    public Optional<List<DoiTuong12Thang>> LuotKhamTheoKhoa(Date fromDate){
        StringBuffer sql=new StringBuffer();
        sql.append("select kb.his_department_id,kb.value2 makhoa,kb.his_patienttype_id,count(his_patienthistory_id) soluong from  ")
                .append(" (Select ph.his_patienthistory_id,ph.REGDATE,hd.HIS_DEPARTMENT_ID,ph.HIS_PATIENTTYPE_ID ,hd.value2 ")
                .append(" from HIS_CHECKUP hc  ")
                .append(" inner join HIS_PATIENTHISTORY ph on ph.HIS_PATIENTHISTORY_ID = hc.HIS_PATIENTHISTORY_ID  ")
                .append(" inner join HIS_DEPARTMENT hd on hc.his_department_id=hd.his_department_id ")
                .append(" where  hc.ISACTIVE     = 'Y' and ph.ISINPATIENT  = 'N' and hc.ISNOTCOUNTED = 'N' ")
                .append(" and ph.ISOUTPATIENTTREATMENT = 'N' and ph.ISRECHECKUPPATIENT = 'N' and NVL(hc.STATUS, 'WT')!= 'Canceled'and ph.ISNOTPATIENT='N' ) kb ")
                .append(" where kb.REGDATE between ? and sysdate  ")
                .append(" group by kb.his_patienttype_id,kb.his_department_id,kb.value2  ")
                .append(" order by his_department_id ");
        Query query=entityManager.createNativeQuery(sql.toString());
        query.setParameter(1,fromDate);
        List<DoiTuong12Thang> list=new ArrayList<>();
        List<Object[]> listOb=query.getResultList();
        if(listOb!=null && listOb.size()>0){
            listOb.stream().forEach(item->{
                list.add(new DoiTuong12Thang(Long.valueOf(((BigDecimal)item[0]).longValue()),(String)item[1],(String)item[2],(BigDecimal)item[3]));
            });
        }
        return Optional.ofNullable(list);
    }

    public Optional<List<DoiTuong>> BNNoiTruCacKhoa(Date toDate){
        StringBuffer sql=new StringBuffer();
        sql.append("select hd.value2, hd.HIS_DEPARTMENT_ID, ")
                .append(" (Select COUNT(HIS_PATIENTHISTORY_ID) from HIS_PATIENTHISTORY  ")
                .append(" where HIS_DEPARTMENT_ID = hd.HIS_DEPARTMENT_ID and TIMEGOIN < ? and (TIMEGOOUT is null or TIMEGOOUT > ? ) and ISINPATIENT = 'Y') as Soluong ")
                .append(" from HIS_DEPARTMENT hd ")
                .append(" left join HIS_PATIENTHISTORY ph on hd.HIS_DEPARTMENT_ID = ph.HIS_DEPARTMENT_ID where ph.ISINPATIENT='Y' ")
                .append(" group by hd.HIS_DEPARTMENT_ID, hd.value2 ");
        Query query=entityManager.createNativeQuery(sql.toString());
        query.setParameter(1,toDate);
        query.setParameter(2,toDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                Long id=Long.valueOf(0);
                String name="";
                if(StringUtils.isNotBlank((String)item[0])){
                    id=Long.valueOf(((BigDecimal)item[1]).longValue());
                    name=(String)item[0];
                }
                items.add(new DoiTuong(id,name,null,(BigDecimal)item[2]));
            });
        }
        return Optional.ofNullable(items);
    }


    /**
     * Test export excell
     */
    public Optional<List<Object[]>> LuotKhamTest(Date fromDate){
        StringBuffer sql=new StringBuffer();
        sql.append("select his_patienttype_id,count(soluong),thang,nam from (select kb.his_patienttype_id,kb.regdate,count(his_patienthistory_id) soluong, ")
                .append(" to_char(kb.regdate,'MM') thang, ")
                .append(" to_char(kb.regdate,'yyyy') nam from  ")
                .append(" (Select ph.HIS_PATIENTHISTORY_ID,ph.HIS_DEPARTMENT_ID,ph.REGDATE,ph.HIS_PATIENTTYPE_ID ")
                .append(" from HIS_CHECKUP hc ")
                .append(" inner join HIS_PATIENTHISTORY ph on ph.HIS_PATIENTHISTORY_ID = hc.HIS_PATIENTHISTORY_ID ")
                .append(" where  hc.ISACTIVE     = 'Y' and ph.ISINPATIENT  = 'N' and hc.ISNOTCOUNTED = 'N' ")
                .append(" and ph.ISOUTPATIENTTREATMENT = 'N' and ph.ISRECHECKUPPATIENT = 'N' and NVL(hc.STATUS, 'WT')!= 'Canceled'and ph.ISNOTPATIENT='N') kb ")
                .append(" where kb.REGDATE between ? and sysdate  ")
                .append(" group by kb.regdate,kb.his_patienttype_id ) ")
                .append(" group by his_patienttype_id,thang,nam ")
                .append(" order by nam,thang ");
        Query query=entityManager.createNativeQuery(sql.toString());
        query.setParameter(1,fromDate);
//        ResultSet resultSet=query.getResultList();
        List<Object[]> listOb=query.getResultList();
        return Optional.ofNullable(listOb);
    }


}
