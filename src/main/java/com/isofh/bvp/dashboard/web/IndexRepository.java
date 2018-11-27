package com.isofh.bvp.dashboard.web;

import com.isofh.bvp.dashboard.model.DoiTuong;
import com.isofh.bvp.dashboard.model.DoiTuong12Thang;
import com.isofh.bvp.dashboard.model.LuotKham;
import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.*;
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
        doanhthu.append(" select HIS_DEPARTMENT_NAME,his_department_id,SUM(AMOUNTINVOICE-AMOUNTBILL) from ( ")
                .append("  select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE,hrv.AMOUNTBILL ")
                .append("   from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" AND hrv.INVOICESERVICETYPE is null  and hrv.ISSERVICEINHOSPITAL = 'Y' ");
        doanhthu.append(" union all ")
                .append(" select HIS_DEPARTMENT_NAME,his_department_id,hrv.AMOUNTINVOICE,hrv.AMOUNTBILL  ")
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
        doanhthu.append(" select sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE-hrv.AMOUNTBILL) tien, ")
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
        doanhthu.append(" select sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE-hrv.AMOUNTBILL) tien, ")
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
        doanhthu.append(" select HIS_SERVICEGROUPLEVEL1_ID,sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE-hrv.AMOUNTBILL) tien,hrv.HIS_SERVICEGROUPLEVEL1_ID, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_OP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" AND hrv.INVOICESERVICETYPE is null and hrv.ISSERVICEINHOSPITAL = 'Y'  ")
                .append(" group by hrv.paytime,hrv.HIS_SERVICEGROUPLEVEL1_ID ) ")
//                .append(" where tien > 0  ")
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
        doanhthu.append(" select HIS_SERVICEGROUPLEVEL1_ID, sum(tien),thang,nam from (select SUM(hrv.AMOUNTINVOICE-hrv.AMOUNTBILL) tien,hrv.HIS_SERVICEGROUPLEVEL1_ID, ")
                .append(" to_char(hrv.paytime,'MM') thang,to_char(hrv.paytime,'yyyy') nam ")
                .append(" from HIS_RV_IP_CHITIET_LEPHIDICHVU hrv ")
                .append(" WHERE hrv.PAYTIME between ? and sysdate ")
                .append(" group by hrv.paytime,hrv.HIS_SERVICEGROUPLEVEL1_ID ) ")
//                .append(" where tien > 0  ")
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
        sql.append("select his_patienttype_id,count(his_patienttype_id),thang,nam from (select kb.his_patienttype_id,kb.regdate, to_char(kb.regdate,'MM') thang,to_char(kb.regdate,'yyyy') nam from ")
                .append(" (select HIS_PATIENTHISTORY_ID,HIS_DEPARTMENT_ID,REGDATE,HIS_PATIENTTYPE_ID ")
                .append(" from HIS_RV_LISTPATIENT L  ")
                .append(" where regdate between ? and sysdate) kb) ")
                .append(" group by his_patienttype_id,thang,nam ")
                .append(" order by nam, thang");
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
        sql.append("select kb.his_department_id,kb.value2 makhoa,kb.his_patienttype_id,count(his_patienthistory_id) soluong from ")
                .append(" (Select hr.his_patienthistory_id,hr.REGDATE,hr.HIS_DEPARTMENT_ID,hr.HIS_PATIENTTYPE_ID ,hd.value2 ")
                .append(" from  HIS_RV_LISTPATIENT hr ")
                .append(" inner join HIS_DEPARTMENT hd on hr.HIS_DEPARTMENT_ID=hd.HIS_DEPARTMENT_ID ")
                .append(" where hr.REGDATE between ? and sysdate) kb ")
                .append("   group by kb.his_patienttype_id,kb.his_department_id,kb.value2 ")
                .append("order by his_department_id ");
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
                .append(" where HIS_DEPARTMENT_ID = hd.HIS_DEPARTMENT_ID and TIMEGOIN < ? and (TIMEGOOUT is null or TIMEGOOUT > ? ) and ISINPATIENT = 'Y' and inpatientstate='InHospital') as Soluong ")
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
    public Optional<List<Object[]>> SQLquery(String queryStr){
        List<Object[]> listOb=null;
        Statement st=null;
        ResultSet rs=null;
        try{
            EntityManagerFactoryInfo info = (EntityManagerFactoryInfo) entityManager.getEntityManagerFactory();
            Connection connection = info.getDataSource().getConnection();
            st=connection.createStatement();
            rs=st.executeQuery(queryStr);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            Object[] objectName=new Object[columnCount];
            for (int i = 0; i < columnCount; i++ ) {
                objectName[i]=rsmd.getColumnName(i+1);
            }
            listOb=new ArrayList<>();
            listOb.add(objectName);
            listOb=load(rs,listOb);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(rs!=null) rs.close();
                if(st!=null) st.close();
            }catch (Exception e){}
        }
        return Optional.ofNullable(listOb);
    }

    private List<Object[]>  load(ResultSet resultSet,List<Object[]> listOb) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        while(resultSet.next()){
            Object[] result = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                result[i] = resultSet.getObject(i + 1);
            }
            listOb.add(result);
        }

        return listOb;
    }

}
