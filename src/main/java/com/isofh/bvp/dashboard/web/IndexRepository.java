package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.model.DoiTuong;
import com.isofh.bvp.dashboard.model.LuotKham;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
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

    public Optional<List<DoiTuong>> lephiDVCacKhoa(Date fromDate,Date toDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select HIS_DEPARTMENT_NAME,his_department_id,SUM(AMOUNTINVOICE) from ( ")
                .append("  select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE ")
                .append("   from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and ? ")
                .append(" AND hrv.INVOICESERVICETYPE is null  and hrv.ISSERVICEINHOSPITAL = 'Y' ");
        doanhthu.append(" union all ")
                .append(" select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE  ")
                .append(" from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and ? ")
                .append(" )   group by HIS_DEPARTMENT_NAME,his_department_id ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        query.setParameter(2,toDate);
        query.setParameter(3,fromDate);
        query.setParameter(4,toDate);
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

    public Optional<List<DoiTuong>> lephiDVNgoaitru(Date fromDate,Date toDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select SUM(hrv.AMOUNTINVOICE) tien,trunc(paytime) paytime ")
                .append(" from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and ? ")
                .append(" AND hrv.INVOICESERVICETYPE is null and hrv.ISSERVICEINHOSPITAL = 'Y' ")
                .append(" group by trunc(paytime) ")
                .append(" order by paytime ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        query.setParameter(2,toDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                items.add(new DoiTuong(Long.valueOf(0),"A",(Date)item[1],(BigDecimal)item[0]));
            });
        }
        return Optional.ofNullable(items);
    }

    public Optional<List<DoiTuong>> lephiDVNoitru(Date fromDate,Date toDate){
        StringBuffer doanhthu=new StringBuffer();
        doanhthu.append(" select SUM(hrv.AMOUNTINVOICE) tien,trunc(paytime) paytime ")
                .append("  from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv  ")
                .append(" WHERE hrv.PAYTIME between ? and ? ")
                .append(" group by trunc(paytime) ")
                .append(" order by paytime ");
        Query query=entityManager.createNativeQuery(doanhthu.toString());
        query.setParameter(1,fromDate);
        query.setParameter(2,toDate);
        List<Object[]> list=query.getResultList();
        List<DoiTuong> items=new ArrayList<>();
        if(list!=null && list.size()>0){
            list.stream().forEach(item->{
                items.add(new DoiTuong(Long.valueOf(1),"S",(Date)item[1],(BigDecimal)item[0]));
            });
        }
        return Optional.ofNullable(items);
    }
    public Optional<List<DoiTuong>> LuotKhamTheoNgay(Date fromDate, Date toDate){
        StringBuffer sql=new StringBuffer();
        sql.append("select his_patienttype_id,regdate,count(his_patienthistory_id) soluong from (Select his_patienthistory_id,trunc(regdate) regdate,his_patienttype_id from HIS_RV_DS_BN_KHAMBENH_KHONG_BA  ")
                .append(" where REGDATE between ? and ? ) ")
                .append(" group by regdate,his_patienttype_id ")
                .append(" order by regdate ");
        Query query=entityManager.createNativeQuery(sql.toString());
        query.setParameter(1,fromDate);
        query.setParameter(2,toDate);
        List<DoiTuong> list=new ArrayList<>();
        List<Object[]> listOb=query.getResultList();
        if(listOb!=null && listOb.size()>0){
            listOb.stream().forEach(item->{
                list.add(new DoiTuong(null,(String)item[0],(Date)item[1],(BigDecimal)item[2]));
            });
        }
        return Optional.ofNullable(list);
    }




}
