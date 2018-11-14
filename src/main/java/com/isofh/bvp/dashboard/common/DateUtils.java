package com.isofh.bvp.dashboard.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class DateUtils {
    public static Date genDateWeek(int year, int week, boolean isFrom){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.setFirstDayOfWeek(2);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        if(isFrom){
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cal.set(Calendar.MILLISECOND,0);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.HOUR_OF_DAY,0);
            return cal.getTime();
        }else{
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            cal.set(Calendar.MILLISECOND,999);
            cal.set(Calendar.SECOND,59);
            cal.set(Calendar.MINUTE,59);
            cal.set(Calendar.HOUR_OF_DAY,23);
            return cal.getTime();
        }
    }
    public static Date genFromAndToCurrentDay(boolean isFrom){
        if(isFrom){
            LocalDate currentDate = LocalDate.now();
            LocalDateTime from = currentDate.atTime(0,0,0);
            return Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        }else{
            LocalDateTime to=LocalDateTime.now();
            return Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
        }
    }
    public static Date getFromAndToDate(int year, int month, int week,boolean isFrom){
        Date date=null;
        if(week>0){
            date=DateUtils.genDateWeek(year,week,isFrom);
        }else if ( month>0){
            date=DateUtils.genDateMonth(year,month,isFrom);
        }else{
            date=DateUtils.genDateYear(year,isFrom);
        }
        return date;
    }

    public static Date genDateMonth(int year, int month,boolean isFrom){
        LocalDate date = LocalDate.of(year, month,1);
        if(isFrom){
            LocalDateTime from = date.atTime(0,0,0);
            return Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        }else{
            LocalDate toDate=date.with(TemporalAdjusters.lastDayOfMonth());
            LocalDateTime to = toDate.atTime(23,59,59);
            return Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
        }
    }
    public static Date genDateYear(int year,boolean isFrom){
        LocalDate date = LocalDate.of(year, 1,1);
        if(isFrom){
            LocalDateTime from = date.atTime(0,0,0);
            return Date.from(from.atZone(ZoneId.systemDefault()).toInstant());
        }else{
            LocalDate toDate=date.with(TemporalAdjusters.lastDayOfYear());
            LocalDateTime to = toDate.atTime(23,59,59);
            return Date.from(to.atZone(ZoneId.systemDefault()).toInstant());
        }
    }
    public static List<Integer> genListDayOfMonth(int year,int month){
        List<Integer> list=new ArrayList<>();
        LocalDate date = LocalDate.of(year, month,1);
        LocalDate toDate=date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime to = toDate.atTime(23,59,59);
        int lastDay=to.getDayOfMonth();
        for(int i=1;i<=lastDay;i++){
            list.add(i);
        }
        return list;
    }
    public static Date genFirstDayOfMonthCurrent(){
        LocalDate date = LocalDate.now();
        LocalDate firstDayOfMonth=date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime fromDate=firstDayOfMonth.atTime(0,0,0,0);
        return Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date genDateFromOfLast12Month(){
        LocalDate date = LocalDate.now();
        LocalDate earlier=date.minusYears(1);
        LocalDate firstDayOfMonth=earlier.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime fromDate=firstDayOfMonth.atTime(0,0,0,0);
        return Date.from(fromDate.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static List<Integer> genListMonthValue(){
        LocalDate date = LocalDate.now();
        LocalDate earlier=date.minusYears(1);
        List<Integer> list=new ArrayList<>();
        for(int i=0;i<12;i++){
            list.add(earlier.plusMonths(i+1).getMonthValue());
        }
        return list;
    }
    public static List<String> genListMonthName(){
        LocalDate date = LocalDate.now();
        LocalDate earlier=date.minusYears(1);
        List<String> list=new ArrayList<>();
        for(int i=0;i<12;i++){
            list.add(earlier.plusMonths(i+1).getMonthValue()+"/"+earlier.plusMonths(i+1).getYear());
        }
        return list;
    }

//    public static void main(String[] args) {
//        LocalDate date = LocalDate.now();
//        LocalDate earlier=date.minusYears(1);
//        LocalDate firstDayOfMonth=earlier.plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
//        LocalDateTime fromDate=firstDayOfMonth.atTime(0,0,0,0);
//        System.out.println(fromDate);
//        System.out.println(fromDate.getMonth().getValue());
//
//
//        LocalDate date1 = LocalDate.now();
//        List<Integer> list=new ArrayList<>();
//        HashMap<Integer,String> map=new HashMap<>();
//        for(int i=0;i<12;i++){
//            list.add(date1.minusMonths(i).getMonthValue());
//            map.put(date1.minusMonths(i).getMonthValue(),date1.minusMonths(i).getMonthValue()+"/"+date1.minusMonths(i).getYear());
//        }
//        System.out.println(list);
//        System.out.println(map);
//    }
}
