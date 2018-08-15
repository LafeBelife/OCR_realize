//package com.baidu.ai.character.retain.test;
////package com.spring.controller;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import jxl.Workbook;
//import jxl.write.Label;
//import jxl.write.WritableSheet;
//import jxl.write.WritableWorkbook;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import th.entity.unity.AlarmContent;
//import th.entity.unity.DeviceTypeNameBean;
//import th.mapper.unify.DeviceTypeNameBeanMapper;
//import th.signal.train.ModelTrainResultDiffBay;
//import th.signal.train.TimeTableDaoChange;
//import th.tools.ContentHelp;
//import th.tools.DocumentHandler;
//import th.tools.SqlMap;
//import th.tools.TimeStringChangeHelp;
//import th.tools.UnRepairString;
//import th.tools.UserInfoTool;
//import th.weather.pre.DataPreDoMethod;
//
//import com.google.gson.Gson;
//import com.spring.dbservice.DBTemplate;
//
///*
// * 获取数据类
// */
//@Controller
//@RequestMapping("/getNewData")
//public class getNewDataController {
//    public static Map<String, String> sqlMap = new HashMap<String, String>();
//    // sql配置
//    static {
//        sqlMap.put(
//                "report_dianliu_all",
//                "select st_id,sum(DOWN_NUMBER) as 越下限数,sum(UP_NUMBER) as 越上限数,OCCUR_TIME as 时间 from (select distinct a.st_id,ST_NAME,UP_NUMBER,DOWN_NUMBER,OCCUR_TIME,A.ST_ID,B.DWMC as AREA_NAME from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where A.ST_ID = B.ST_ID and "
//                        + "/ selectDataArea#A.DATA_OF_AREA = '?'"
//                        + "/ areaName# and B.DWMC='?'"
//                        + "/ timeStart# and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ timeEnd# and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?')"
//                        + "/ GROUP BY AREA_NAME,OCCUR_TIME,st_id order by to_char(OCCUR_TIME, 'yyyy-mm-dd') desc");
//        // 20161221修改地区电流获得
//        sqlMap.put(
//                "report_dianliu_all2",
//                "select st_id,sum(DOWN_NUMBER) as 越下限数,sum(UP_NUMBER) as 越上限数,OCCUR_TIME as 时间 from (select distinct ST_NAME,UP_NUMBER,DOWN_NUMBER,OCCUR_TIME,A.ST_ID,B.DWMC as AREA_NAME from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where A.ST_ID = B.ST_ID and "
//                        + "/ selectDataArea#A.DATA_OF_AREA = '?'"
//                        + "/ areaName# and B.DWMC='?'"
//                        + "/ dydj#and b.dydj = '?'"
//                        + "/ st_id#and a.st_id = '?'"
//                        + "/ startDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ endDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ ) GROUP BY AREA_NAME,OCCUR_TIME,st_id order by to_char(OCCUR_TIME, 'yyyy-mm-dd') desc");
//
//        sqlMap.put("report_dianliu_all2_resp", "select r.st_id, sum(r.up_number) as 越上限数, date as 时间, d.area_jc, d.area_value from ("
//                + "select t.*, regexp_substr(t.dwmc, '[^省地]+', 1, 1) as area "
//                + "from bdms.current_count_resp_area t left join ems.bdms_substation s on t.st_id = s.st_id where 1=1 "
//                + "/ areaName# and instr(t.dwmc, '?') > 0" + "/ startDate#and date>='?'" + "/ endDate#and date<='?'" + "/ dydj#and s.dydj = '?'"
//                + "/ st_id#and t.st_id = '?'" + "/ resp_area#and resp_area & ? > 0" + "/) r "
//                + "left join (select name, area from binfo.ems_subcontrolarea) a on r.area = a.name "
//                + "left join (select area, area_value, area_jc from binfo.ems_resp_area_def) d "
//                + "on d.area = a.area and d.area_value & r.resp_area > 0 " + "group by r.st_id, r.date, d.area_jc, d.area_value ORDER BY date desc");
//
//        // 20161221修改地区电流获得省调
//        sqlMap.put(
//                "report_dianliu_all2_sheng",
//                "SELECT ST_ID,ST_NAME,OCCUR_TIME as 时间,sum(DOWN_NUMBER) as 越下限数,sum(UP_NUMBER) as 越上限数 FROM (select a.st_id,B.DWMC as AREA_NAME,ST_NAME,OCCUR_TIME,UP_NUMBER,DOWN_NUMBER from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where  A.DATA_OF_AREA = '省调' and "
//                        + "/ startDate#to_char(A.OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ endDate#and to_char(A.OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ st_id#and a.st_id = '?'"
//                        + "/ dydj#and b.dydj = '?'"
//                        + "/ AND A.ST_ID=B.ST_ID) GROUP BY OCCUR_TIME,ST_ID,ST_NAME ORDER BY OCCUR_TIME desc");
//
//        sqlMap.put("report_dianliu_all2_sheng_resp", "select  ST_ID,ST_NAME,date as 时间,sum(UP_NUMBER) as 越上限数 , d.area_jc, d.area_value from ("
//                + "select t.*, regexp_substr(t.dwmc, '[^省地]+', 1, 1) as area "
//                + "from bdms.current_count_resp_area t left join ems.bdms_substation s on t.st_id = s.st_id where t.dwmc = '江苏省调' "
//                + "/ startDate#and date>='?'" + "/ endDate#and date<='?'" + "/ dydj#and s.dydj = '?'" + "/ st_id#and t.st_id = '?'"
//                + "/ resp_area#and resp_area & ? > 0" + "/) r " + "left join (select name, area from binfo.ems_subcontrolarea) a on r.area = a.name "
//                + "left join (select area, area_value, area_jc from binfo.ems_resp_area_def) d "
//                + "on d.area = a.area and d.area_value & r.resp_area > 0 "
//                + "group by r.st_id, r.st_name, r.date, d.area_jc, d.area_value ORDER BY date desc");
//
//        sqlMap.put("report_dianliu",
//                "select ST_ID,DOWN_NUMBER as 越下限数,UP_NUMBER as 越上限数,OCCUR_TIME as 时间 from BDMS.CURRENT_COUNT_TABLE where 1=1 and "
//                        + "/ selectDataArea#DATA_OF_AREA = '?'" + "/ stationId# and ST_ID='?'"
//                        + "/ timeStart# and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'" + "/ timeEnd# and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ order by to_char(OCCUR_TIME, 'yyyy-mm-dd') desc");
//
//        sqlMap.put("report_dianliu2",
//                "select ST_ID,DOWN_NUMBER as 越下限数,UP_NUMBER as 越上限数,OCCUR_TIME as 时间 from BDMS.CURRENT_COUNT_TABLE where 1=1 and "
//                        + "/ selectDataArea#DATA_OF_AREA = '?'" + "/ stationId# and ST_ID='?'"
//                        + "/ startDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'" + "/ endDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ order by to_char(OCCUR_TIME, 'yyyy-mm-dd') desc");
//        // 20161221表格数据接口修改
//        // modf 2018-04-19修改，where后面添加1=1，DWMC前面添加and
//        sqlMap.put(
//                "report_dianliu_t2",
//                "SELECT ST_ID,STATION_NAME as 场站名,sum(DOWN_NUMBER) as 越下限总数,sum(UP_NUMBER) as 越上限总数  FROM BDMS.CURRENT_COUNT_TABLE where st_id in (SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION WHERE 1=1 "
//                        + "/ areaName#and DWMC = '?'"
//                        + "/ dydj#and dydj = '?'"
//                        + "/ st_id#and st_id = '?'"
//                        + "/ ) "
//                        + "/ startDate#and OCCUR_TIME>=to_date('?','yyyy-mm-dd')"
//                        + "/ endDate#and OCCUR_TIME<=to_date('?','yyyy-mm-dd')"
//                        + "/ group by STATION_NAME,ST_ID  order by (sum(DOWN_NUMBER) + sum(UP_NUMBER)) desc");
//
//        sqlMap.put("report_dianliu_t2_resp", "select r.st_id, r.st_name as 场站名, sum(r.up_number) as 越上限总数, d.area_jc, d.area_value from ("
//                + "select t.*, regexp_substr(t.dwmc, '[^省地]+', 1, 1) as area "
//                + "from bdms.current_count_resp_area t left join ems.bdms_substation s on t.st_id = s.st_id where 1=1 "
//                + "/ areaName# and instr(t.dwmc, '?') > 0" + "/ startDate#and date>='?'" + "/ endDate#and date<='?'" + "/ dydj#and s.dydj = '?'"
//                + "/ st_id#and t.st_id = '?'" + "/ resp_area#and resp_area & ? > 0" + "/) r "
//                + "left join (select name, area from binfo.ems_subcontrolarea) a on r.area = a.name "
//                + "left join (select area, area_value, area_jc from binfo.ems_resp_area_def) d "
//                + "on d.area = a.area and d.area_value & r.resp_area > 0 "
//                + "group by r.st_id, r.st_name, d.area_jc, d.area_value ORDER BY 越上限总数 desc");
//        // 选择省调的表格数据接口
//        sqlMap.put(
//                "report_dianliu_t2_sheng",
//                "SELECT ST_ID,AREA_NAME,ST_NAME,sum(DOWN_NUMBER) as 越下限总数,sum(UP_NUMBER) as 越上限总数 FROM (select a.st_id,B.DWMC as AREA_NAME,ST_NAME,OCCUR_TIME,UP_NUMBER,DOWN_NUMBER from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where  "
//                        + "/ A.DATA_OF_AREA = '省调' and "
//                        + "/ startDate#to_char(A.OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ endDate#and to_char(A.OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ st_id#and a.st_id = '?'"
//                        + "/ dydj#and b.dydj = '?'"
//                        + "/ AND A.ST_ID=B.ST_ID) GROUP BY AREA_NAME,ST_NAME,ST_ID order by (sum(DOWN_NUMBER) + sum(UP_NUMBER)) desc");
//
//        sqlMap.put("report_dianliu_t2_sheng_resp", "select r.st_id, r.st_name as 场站名, sum(r.up_number) as 越上限总数, d.area_jc, d.area_value from ("
//                + "select t.*, regexp_substr(t.dwmc, '[^省地]+', 1, 1) as area "
//                + "from bdms.current_count_resp_area t left join ems.bdms_substation s on t.st_id = s.st_id where t.dwmc = '江苏省调' "
//                + "/ startDate#and date>='?'" + "/ endDate#and date<='?'" + "/ dydj#and s.dydj = '?'" + "/ st_id#and t.st_id = '?'"
//                + "/ resp_area#and resp_area & ? > 0" + "/) r " + "left join (select name, area from binfo.ems_subcontrolarea) a on r.area = a.name "
//                + "left join (select area, area_value, area_jc from binfo.ems_resp_area_def) d "
//                + "on d.area = a.area and d.area_value & r.resp_area > 0 "
//                + "group by r.st_id, r.st_name, d.area_jc, d.area_value ORDER BY 越上限总数 desc");
//
//        sqlMap.put(
//                "report_dianliu_t1",
//                "SELECT a.ST_ID,AREA_NAME AS 地区,STATION_NAME as 场站名,sum(DOWN_NUMBER) as 越下限总数,sum(UP_NUMBER) as 越上限总数 FROM BDMS.CURRENT_COUNT_TABLE AS A,(SELECT DISTINCT DWMC as AREA_NAME,ST_ID FROM EMS.BDMS_SUBSTATION WHERE ST_ID IN (select distinct ST_ID from BDMS.CURRENT_COUNT_TABLE where "
//                        + "/ selectDataArea#DATA_OF_AREA = '?'"
//                        + "/ timeStart# and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ timeEnd# and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + ")) AS B WHERE A.ST_ID=B.ST_ID "
//                        + "/ timeStart# and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ timeEnd# and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ group by STATION_NAME,AREA_NAME,ST_ID order by AREA_NAME");
//        sqlMap.put("report_dianliu_s",
//                "select distinct A.STATION_NAME,B.DYDJ,B.ST_ID from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where A.ST_ID = B.ST_ID and "
//                        + "A.ST_ID IN (SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION where" + "/ areaName#DWMC ='?')" + "order by B.DYDJ desc");
//        // 20161221修改场站数据获取接口
//        // modf 2018-04-19修改，where后面添加1=1，DWMC前面添加and
//        sqlMap.put("report_dianliu_s2", "select st_name as STATION_NAME,DYDJ,ST_ID||'' as st_id from EMS.BDMS_SUBSTATION where 1=1 "
//                + "/ areaName#and DWMC ='?' ");
//
//        sqlMap.put("report_dianliu_s2_sheng",
//                "select st_name as station_name,DYDJ,ST_ID||'' as st_id from EMS.BDMS_SUBSTATION where dwmc = '江苏' order by length(DYDJ) desc, DYDJ desc, st_name");
//        sqlMap.put("report_dianliu_a", "select substr(dwmc,1,length(dwmc)-2) as area_name from bdms.conf_dept_area where dwmc not like '%省%'");
//
//        sqlMap.put(
//                "report_dianliu_count_sheng",
//                "SELECT to_char(OCCUR_TIME,'yyyy-mm') as 时间,sum(DOWN_NUMBER) as 越下限数,sum(UP_NUMBER) as 越上限数 FROM (select a.st_id,B.DWMC as AREA_NAME,ST_NAME,OCCUR_TIME,UP_NUMBER,DOWN_NUMBER from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where  A.DATA_OF_AREA = '省调' and "
//                        + "/ startDate#to_char(A.OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ endDate#and to_char(A.OCCUR_TIME,'yyyy-mm-dd')<='?'"
//                        + "/ st_id#and a.st_id = '?'" + "/ dydj#and b.dydj = '?'" + "/ AND A.ST_ID=B.ST_ID) GROUP BY to_char(OCCUR_TIME,'yyyy-mm')");
//        // modf 2018-04-19修改，将where A.ST_ID = B.ST_ID后面的and移到了A.DATA_OF_AREA的前面
//        sqlMap.put(
//                "report_dianliu_count",
//                "select sum(DOWN_NUMBER) as 越下限数,sum(UP_NUMBER) as 越上限数,to_char(OCCUR_TIME,'yyyy-mm') as 时间 from (select distinct ST_NAME,UP_NUMBER,DOWN_NUMBER,OCCUR_TIME,A.ST_ID,B.DWMC as AREA_NAME from BDMS.CURRENT_COUNT_TABLE A,EMS.BDMS_SUBSTATION B where A.ST_ID = B.ST_ID "
//                        + "/ selectDataArea# and A.DATA_OF_AREA = '?'"
//                        + "/ areaName# and B.DWMC='?'"
//                        + "/ dydj#and b.dydj = '?'"
//                        + "/ st_id#and a.st_id = '?'"
//                        + "/ startDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')>='?'"
//                        + "/ endDate#and to_char(OCCUR_TIME,'yyyy-mm-dd')<='?'" + "/ ) GROUP BY to_char(OCCUR_TIME,'yyyy-mm') ");
//
//        // sqlMap.put("report_resp_area","/areaName# select area_value as id, area_jc as name from ( select name, area from binfo.ems_subcontrolarea ) a left join ( select area, area_value, area_jc from binfo.ems_resp_area_def ) d on a.area = d.area where decode(a.name, '江苏', '省调', a.name) = '?'");
//        // modf 2018-04-16修改查询责任区
//        sqlMap.put("report_resp_area", "/areaName# select area_value as id, area_jc as name from EMS.RESP_AREA_DEF where DWMC = '?'");
//        // 根据输入的时间查询到有降雨的日期，根据地区及查询到的日期查询信号每天出现的总次数
//        sqlMap.put(
//                "report_rain_signal_everydaymessage",
//                "SELECT OCCURDAY,SIGNALID,SIGNALNAME,COUNT(*) AS OCCURNUMBER FROM (SELECT substr(OCCURTIME,0,10) AS OCCURDAY,SIGNALID,SIGNALNAME from BDMS.INPUTDATA_TABLE_T where status = '28' and STATIONID IN (SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION WHERE"
//                        + "/ areaName#area_name = '?'"
//                        + " AND ST_ID is not null) "
//                        + " AND substr(OCCURTIME,0,10) IN (select DISTINCT TIME from BDMS.RAIN_MESSAGE_INFO WHERE "
//                        + "/ areaName#AREA = '?' "
//                        + "/ timeStart# AND TIME >= '?'"
//                        + "/ timeEnd# AND TIME <= '?')"
//                        + ") GROUP BY OCCURDAY,SIGNALID,SIGNALNAME order by OCCURDAY");
//        // 根据输入的时间查询到有降雨的日期，根据地区及查询到的日期查询信号每天出现的总次数
//        sqlMap.put("report_rain_signal_everydaymessage2", "SELECT a.OCCURDAY,a.SIGNALID ,a.SIGNALNAME,COUNT(*) AS OCCURNUMBER FROM"
//                + "(SELECT to_char(OCCURTIME, 'yyyy-mm-dd') AS OCCURDAY,SIGNALID,SIGNALNAME from BDMS.INPUTDATA_TABLE_T t,"
//                + "EMS.BDMS_SUBSTATION b,BDMS.RAIN_MESSAGE_INFO c where status= '28' " + "/ timeStart#and t.OCCURTIME >='?' "
//                + "/ timeEnd#and t.OCCURTIME<='? 23:59:59' " + "/ areaName#and b.area_name='?' and t.STATIONID=b.ST_ID "
//                + "/ areaName#and c.AREA = '?' and to_char(t.OCCURTIME, 'yyyy-MM-dd')=to_char(c.TIME, 'yyyy-mm-dd')"
//                + ")a,(select top 5 SIGNALID FROM (SELECT to_char(OCCURTIME, 'yyyy-mm-dd') AS OCCURDAY,SIGNALID,"
//                + " SIGNALNAME from BDMS.INPUTDATA_TABLE_T t,EMS.BDMS_SUBSTATION b ,BDMS.RAIN_MESSAGE_INFO c "
//                + " where status= '28' / timeStart#and t.OCCURTIME >='?' / timeEnd#and t.OCCURTIME<='? 23:59:59' "
//                + "/ areaName#and b.area_name ='?' and t.STATIONID=b.ST_ID / areaName#and c.AREA = '?' "
//                + " and to_char(t.OCCURTIME, 'yyyy-MM-dd')=to_char(c.TIME, 'yyyy-mm-dd')) GROUP BY SIGNALID order by"
//                + " count(0) DESC) b where a.SIGNALID=b.SIGNALID GROUP BY a.OCCURDAY,a.SIGNALID,a.SIGNALNAME order by" + " a.OCCURDAY");
//
//        /*
//         * //根据输入的时间查询到有降雨的日期，根据地区及查询到的日期查询信号每天出现的总次数
//         * sqlMap.put("report_rain_signal_everydaymessage2",
//         * "SELECT OCCURDAY,SIGNALID,SIGNALNAME,COUNT(*) AS OCCURNUMBER FROM (SELECT to_char(OCCURTIME,'yyyy-mm-dd') AS OCCURDAY,SIGNALID,SIGNALNAME FROM BDMS.INPUTDATA_TABLE_T t WHERE status = '28' and exists (SELECT 1 FROM EMS.BDMS_SUBSTATION WHERE "
//         * +
//         * "/ areaName#area_name = '?' AND ST_ID is not null and st_id = t.stationid) AND exists (select 1 from BDMS.RAIN_MESSAGE_INFO WHERE "
//         * +
//         * "/ areaName#AREA = '?' AND to_char(t.OCCURTIME,'yyyy-mm-dd') = to_char(TIME,'yyyy-mm-dd') AND "
//         * + "/ timeStart#time >= to_date('?', 'yyyy-mm-dd')" +
//         * "/ timeEnd# AND time <= to_date('? 23:59:59', 'yyyy-mm-dd HH24:mi:ss')) ) where SIGNALID IN (select top 5 SIGNALID FROM BDMS.INPUTDATA_TABLE_T r WHERE status = '28' and exists (SELECT 1 FROM EMS.BDMS_SUBSTATION WHERE 1=1 AND "
//         * +
//         * "/ areaName#area_name = '?' AND ST_ID is not null and st_id = r.stationid) AND exists (select 1 from BDMS.RAIN_MESSAGE_INFO WHERE 1=1 AND "
//         * +
//         * "/ areaName#AREA = '?' AND to_char(r.OCCURTIME,'yyyy-mm-dd') = to_char(TIME,'yyyy-mm-dd') AND "
//         * + "/ timeStart#time >= to_date('?', 'yyyy-mm-dd') AND " +
//         * "/ timeEnd#time <= to_date('? 23:59:59', 'yyyy-mm-dd HH24:mi:ss')) GROUP BY SIGNALID order by count(0) DESC) GROUP BY OCCURDAY,SIGNALID,SIGNALNAME order by OCCURDAY"
//         * );
//         */
//        // 雨量信息接口
//        sqlMap.put("report_rain_message", "select RAINFALL,TIME from BDMS.RAIN_MESSAGE_INFO WHERE 1=1 " + "/ areaName# AND AREA = '?'"
//                + "/ timeStart# AND TIME >= '?'" + "/ timeEnd# AND TIME <= '?'" + " order by TIME");
//        // 降雨时间段信息总数接口
//        sqlMap.put(
//                "report_rain_signal_allnumber",
//                "SELECT SIGNALNAME,SIGNALID,count(*) AS SUMNUMBER from BDMS.INPUTDATA_TABLE_T where status = '28' and STATIONID IN (SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION WHERE 1=1"
//                        + "/ areaName# AND area_name = '?'"
//                        + " AND ST_ID is not null) AND substr(OCCURTIME,0,10) IN (select DISTINCT TIME from BDMS.RAIN_MESSAGE_INFO WHERE 1=1"
//                        + "/ areaName# AND AREA = '?' "
//                        + "/ timeStart# AND TIME >= '?' "
//                        + "/ timeEnd# AND TIME <= '?'"
//                        + ") GROUP BY SIGNALNAME,SIGNALID order by SUMNUMBER DESC");
//        // 降雨时间段信息总数接口
//        sqlMap.put("report_rain_signal_allnumber2", "SELECT SIGNALNAME,SIGNALID,SUMNUMBER, 设备类型 AS DEVICETYPE FROM "
//                + "(SELECT SIGNALNAME,SIGNALID,count(*) AS SUMNUMBER from BDMS.INPUTDATA_TABLE_T t,EMS.BDMS_SUBSTATION  b,"
//                + " BDMS.RAIN_MESSAGE_INFO c where status = '28' " + "/ timeStart#and t.OCCURTIME>='?' " + "/ timeEnd#and t.OCCURTIME<='? 23:59:59' "
//                + "/ areaName#and b.area_name='?' " + " and t.STATIONID=b.ST_ID " + "/ areaName#and c.AREA  = '?' "
//                + "and to_char(t.OCCURTIME,'yyyy-MM-dd')=to_char(c.TIME,'yyyy-mm-dd') GROUP BY SIGNALNAME,SIGNALID"
//                + ")  AS AA,BDMS.关联分析信号配置表T AS BB WHERE AA.SIGNALID = BB.信号编号  order by SUMNUMBER DESC");
//
//        /*
//         * //降雨时间段信息总数接口 sqlMap.put("report_rain_signal_allnumber2",
//         * "SELECT SIGNALNAME,SIGNALID,SUMNUMBER,设备类型  AS DEVICETYPE FROM (SELECT SIGNALNAME,SIGNALID,count(*) AS SUMNUMBER from BDMS.INPUTDATA_TABLE_T where status = '28' and STATIONID IN (SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION WHERE 1=1 "
//         * +
//         * "/ areaName#AND area_name = '?' AND ST_ID is not null) AND substr(OCCURTIME,0,10) IN (select DISTINCT TIME from BDMS.RAIN_MESSAGE_INFO WHERE 1=1 "
//         * + "/ areaName#AND AREA = '?'" + "/ timeStart# AND TIME >= '?'" +
//         * "/ timeEnd# AND TIME <= '?'" +
//         * ") GROUP BY SIGNALNAME,SIGNALID) AS AA,BDMS.关联分析信号配置表T AS BB WHERE AA.SIGNALID = BB.信号编号 order by SUMNUMBER DESC"
//         * );
//         */
//
//        sqlMap.put("report_rain_area", "select substr(dwmc,1,length(dwmc)-2) as area_name from bdms.conf_dept_area where dwmc not like '%省%'");
//
//        // 各个地区降雨总量信息接口
//        sqlMap.put("report_allrain_area",
//                "select AREA,SUM(RAINFALL) AS ALLRAINNUMBER, avg(high_temp) as high_temp, avg(wind) as wind from BDMS.RAIN_MESSAGE_INFO WHERE 1=1 "
//                        + "/ timeStart# and TIME >= '?'  " + "/ timeEnd# AND TIME <= '?'"
//                        + "/ areaName# AND TIME = (select sp_date from bdms.恶劣天气日期配置 t where area = '?'" + "/ type# and type = '?')"
//                        + "/ GROUP BY AREA order by high_temp");
//
//        sqlMap.put("report_allrain_area_forecast",
//                "select AREA,SUM(RAINFALL) AS ALLRAINNUMBER, avg(high_temp) as high_temp, avg(wind) as wind from BDMS.v_weather_forecast WHERE 1=1 "
//                        + "/ timeStart# and TIME >= '?'  " + "/ timeEnd# AND TIME <= '?'"
//                        + "/ areaName# AND TIME = (select sp_date from bdms.恶劣天气日期配置 t where area = '?'" + "/ type# and type = '?')"
//                        + "/ GROUP BY AREA order by high_temp");
//
//        sqlMap.put("allarea_wind", "select date_time as time, city, location, max_wind_velocity as wind, rain, x, y "
//                + "from (select station_code, date_time, max_wind_velocity, rain from weather.weather_history_data) a "
//                + "left join (select city, location, station_code, x, y from weather.weather_station_def) b " + "on a.station_code = b.station_code "
//                + " where 1=1 " + "/ timeStart# and date_time >= '?' " + "/ timeEnd# AND date_time <= '?' "
//                + "/ areaName# AND date_time = (select sp_date from bdms.恶劣天气日期配置 t where area = '?' " + "/ type# and type = '?')");
//
//        // 查询所有存在数据预处理的地区 关联分析
//        sqlMap.put("analysis_importdatalog_area", "select DISTINCT DWMC from BDMS.INPUTDATA_TABLE_T_LOG where DWMC != 'NULL'");
//
//        // 查询所有存在数据训练的地区 关联分析
//        sqlMap.put("analysis_traindatalog_area", "select DISTINCT DWMC from BDMS.ASSOCIATION_ANALYSIS_RESULT_LOG where DWMC != 'NULL'");
//
//        // 查询所有存在数据预处理的地区 家族型缺陷预处理
//        sqlMap.put("family_importdatalog_area", "select DISTINCT DWMC from BDMS.STATAND_INPUT_DATA_LOG where DWMC != 'NULL'");
//
//        // 柱状图(无雨、有雨、预测值)
//        // sqlMap.put("weather_sp_default","with tmp_count as (select count(0) as count, to_char(occurtime, 'yyyy-mm-dd') as occur_time "
//        // +
//        // "from bdms.恶劣天气关联信号 t left join EMS.BDMS_SUBSTATION s on t.stationid = s.st_id where 1=1"
//        // + "/ areaName# and s.area_name='?'"
//        // + "/ signal# and signalid = '?'"
//        // +
//        // "/ type# and signalid in (select id from bdms.恶劣天气关联信号配置 where type='?') "
//        // + "/ group by to_char(occurtime, 'yyyy-mm-dd')), "
//        // + "tmp_rain as (select * from bdms.rain_message_info where 1=1 "
//        // + "/ areaName# and area = '?' "
//        // + "/) select sp.*, "
//        // +
//        // "(select count from tmp_count where occur_time = sp.normal_date) as normal_count, "
//        // +
//        // "(select count from tmp_count where occur_time = sp.sp_date) as sp_count "
//        // +
//        // "from (select normal_date, sp_date, (select rainfall from tmp_rain where time = normal_date and area = t.area) as normal_rain,"
//        // +
//        // " (select high_temp from tmp_rain where time = normal_date and area = t.area) as normal_temp,"
//        // +
//        // "(select rain_level from tmp_rain where time = normal_date) as normal_rain_level, "
//        // +
//        // "(select temp_level from tmp_rain where time = normal_date) as normal_temp_level, "
//        // +
//        // " (select rainfall from tmp_rain where time = sp_date and area = t.area) as sp_rain,"
//        // +
//        // " (select high_temp from tmp_rain where time = sp_date and area = t.area) as sp_temp,"
//        // +
//        // "(select rain_level from tmp_rain where time = sp_date) as sp_rain_level, "
//        // +
//        // "(select temp_level from tmp_rain where time = sp_date) as sp_temp_level, "
//        // + " (select round(max(count)) from tmp_count "
//        // + "where occur_time in (select time from tmp_rain "
//        // + "where time != sp_date and (rain_level, temp_level) in "
//        // +
//        // "(select rain_level, temp_level from tmp_rain where time = sp_date and area = t.area) "
//        // +
//        // "and datediff(d, time, sp_date) between -30 and 30)) as sp_predict_count "
//        // + "from bdms.恶劣天气日期配置 t where 1=1 "
//        // + "/ type# and type='?' "
//        // + "/ areaName# and area='?'"
//        // + "/ ) sp");
//        sqlMap.put(
//                "weather_sp_default",
//                "with tmp_date as ("
//                        + "select * from bdms.恶劣天气日期配置 t where 1=1 "
//                        + "/type# and type='?' "
//                        + "/areaName# and area='?'"
//                        + "/ ) select * from (with "
//                        // + "tmp_count as ("
//                        // + "select occur_time, count(0) as count from ("
//                        // +
//                        // "select distinct yx_id, to_char(time, 'yyyy-mm-dd') as occur_time from ("
//                        // +
//                        // "select yx_id, st_id, time from bdms.FREQUENT_SIGNAL where "
//                        // +
//                        // "time = (select sp_date from tmp_date) or time = (select normal_date from tmp_date)) t "
//                        // +
//                        // "left join EMS.BDMS_SUBSTATION s on t.st_id = s.st_id where 1=1 "
//                        // + "/areaName# and s.area_name='?'"
//                        // + "/) group by occur_time),"
//                        + "tmp_rain as ("
//                        + "select * from bdms.rain_message_info where 1=1 "
//                        + "/areaName# and area = '?'"
//                        + "/) "
//
//                        + ", tmp_predict as (select * from bdms.恶劣天气历史预测值 where 1=1 "
//                        + "/areaName# and area = '?')"
//
//                        + "select sp.*, (select value from tmp_predict where date = sp.normal_date) as normal_count, "
//                        + "(select value from tmp_predict where date = sp.sp_date) as sp_count, "
//                        + "(select avg(value) from tmp_predict) as avg, "
//                        //20180728 修正历史预测发信量 rensz修改 注释379行，放开373-378行
//                        +
//                        "(select round(sp.sp_rain * (select param_value from bdms.恶劣天气参数表 where param_name='rain_param' and area = sp.area)"
//                        +
//                        " + sp.sp_wind * (select param_value from bdms.恶劣天气参数表 where param_name='wind_param' and area = sp.area)"
//                        +
//                        " + (select param_value from bdms.恶劣天气参数表 where param_name='const' and area = sp.area)) from dual) as sp_predict_count "
//                        //+ "(select predict from tmp_predict where date = sp.sp_date) as sp_predict_count "
//                        + "from (select normal_date, sp_date, t.area, (select rainfall from tmp_rain where time = normal_date and area = t.area ) as normal_rain, "
//                        + "(select high_temp from tmp_rain where time = normal_date and area = t.area) as normal_temp, "
//                        + "(select wind from tmp_rain where time = normal_date and area = t.area) as normal_wind, "
//                        + "(select rain_level from tmp_rain where time = normal_date) as normal_rain_level, "
//                        + "(select temp_level from tmp_rain where time = normal_date) as normal_temp_level, "
//                        + "(select wind from tmp_rain where time = sp_date and area = t.area) as sp_wind, "
//                        + "(select rainfall from tmp_rain where time = sp_date and area = t.area) as sp_rain, "
//                        + "(select high_temp from tmp_rain where time = sp_date and area = t.area) as sp_temp, "
//                        + "(select rain_level from tmp_rain where time = sp_date) as sp_rain_level, "
//                        + "(select temp_level from tmp_rain where time = sp_date) as sp_temp_level from tmp_date t) sp)");
//
//        // sqlMap.put("weather_sp_date","with tmp_count as (select count(0) as count, to_char(occurtime, 'yyyy-mm-dd') as occur_time "
//        // +
//        // "from bdms.恶劣天气关联信号 t left join EMS.BDMS_SUBSTATION s on t.stationid = s.st_id where 1=1"
//        // + "/ areaName# and s.area_name='?'"
//        // + "/ signal# and signalid = '?'"
//        // +
//        // "/ type# and signalid in (select id from bdms.恶劣天气关联信号配置 where type='?') "
//        // + "/ group by to_char(occurtime, 'yyyy-mm-dd')), "
//        // + "tmp_rain as ("//天气数据
//        // +
//        // "(select AREA, TIME, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '1' as forecast "//预报
//        // + "from bdms.V_WEATHER_FORECAST where 1=1 "
//        // + "/ areaName# and area = '?' "
//        // + "/) union all ("
//        // +
//        // "select AREA, TIME, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '0' as forecast "//历史
//        // + "from bdms.rain_message_info where 1=1 "
//        // + "/ areaName# and area = '?'"
//        // + "/ ))"
//        // + "select sp.*, "
//        // +
//        // "(select count from tmp_count where occur_time = sp.normal_date) as normal_count, "//对照日信号计数
//        // +
//        // "(select count from tmp_count where occur_time = sp.sp_date) as sp_count "//特征日信号计数
//        // + "from (select normal_date, sp_date, "
//        // +
//        // "(select rainfall from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_rain, "
//        // +
//        // "(select high_temp from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_temp, "
//        // +
//        // "(select rain_level from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_rain_level, "
//        // +
//        // "(select temp_level from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_temp_level, "
//        // +
//        // "(select rainfall from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_rain, "
//        // +
//        // "(select high_temp from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_temp, "
//        // +
//        // "(select rain_level from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_rain_level, "
//        // +
//        // "(select temp_level from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_temp_level, "
//        // +
//        // "(select round(max(count)) from tmp_count where occur_time in ("//取告警量平均
//        // +
//        // "select time from tmp_rain where time != sp_date and forecast = '0' "
//        // +
//        // "and (rain_level, temp_level) in (select rain_level, temp_level from tmp_rain where time = sp_date and forecast= t.sp_forecast ) "//取相似气象历史
//        // +
//        // "and datediff(d, time, sp_date) between -365 and 365)) as sp_predict_count, "//取前后一年
//        // + "(case when normal_forecast = '1' then "//当对照日为预报时, 取预测值
//        // + "(select round(max(count)) from tmp_count where occur_time in ("
//        // +
//        // "select time from tmp_rain where time != normal_date and forecast = '0' "
//        // +
//        // "and (rain_level, temp_level) in (select rain_level, temp_level from tmp_rain where time = normal_date and forecast= '1')"//区别
//        // +
//        // " and datediff(d, time, sp_date) between -30 and 30)) else null end) as normal_predict_count "
//        // + "from ( select "
//        // //以下为必要参数
//        // + "/ spDate# '?' as sp_date, "
//        // + "/ spForecast# '?' as sp_forecast, "//是否预报标志位
//        // + "/ normalDate# '?' as normal_date, "
//        // + "/ normalForecast# '?' as normal_forecast, "
//        // + "/ areaName# '?' as area from dual) t) sp");
//        sqlMap.put(
//                "weather_sp_date",
//                "with tmp_param as ("
//                        + "select "
//                        + "/ spDate# '?' as sp_date, "
//                        + "/ spForecast# '?' as sp_forecast, "
//                        + "/ normalDate# '?' as normal_date, "
//                        + "/ normalForecast# '?' as normal_forecast, "
//                        + "/ areaName# '?' as area from dual) "
//                        + "select * from (with "
//                        // + "tmp_count as ("
//                        // + "select occur_time, count(0) as count from ("
//                        // +
//                        // "select distinct yx_id, to_char(time, 'yyyy-mm-dd') as occur_time from ("
//                        // +
//                        // "select yx_id, st_id, time from bdms.FREQUENT_SIGNAL where "
//                        // +
//                        // "time = (select sp_date from tmp_param) or time = (select normal_date from tmp_param)) t "
//                        // +
//                        // "left join EMS.BDMS_SUBSTATION s on t.st_id = s.st_id where "
//                        // +
//                        // "s.area_name= (select area from tmp_param)) group by occur_time), "
//                        + "tmp_rain as (("
//                        + "select AREA, TIME, WIND, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '1' as forecast "
//                        + "from bdms.V_WEATHER_FORECAST where area = (select area from tmp_param)) union all ("
//                        + "select AREA, TIME, WIND, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '0' as forecast "
//                        + "from bdms.rain_message_info where area = ( select area from tmp_param))) "
//
//                        + ", tmp_predict as (select * from bdms.恶劣天气历史预测值 where area = ( select area from tmp_param)) "
//
//                        + "select sp.*, (select value from tmp_predict where date = sp.normal_date) as normal_count, "
//                        + "(select value from tmp_predict where date = sp.normal_date) as normal_level, "
//                        + "(select value from tmp_predict where date = sp.sp_date) as sp_count, "
//                        + "(select avg(value) from tmp_predict) as avg, "
//                        //20180728 修正历史预测发信量 rensz修改 注释490、494、496、500行
//                        //+ "(case when sp.sp_forecast = '1' then"//
//                        + "(select round(sp.sp_rain * (select param_value from bdms.恶劣天气参数表 where param_name='rain_param' and area = ( select area from tmp_param)) "
//                        + "+ sp.sp_wind * (select param_value from bdms.恶劣天气参数表 where param_name='wind_param' and area = ( select area from tmp_param)) "
//                        + "+ (select param_value from bdms.恶劣天气参数表 where param_name='const' and area = ( select area from tmp_param))) from dual) "
//                        //+ "else (select predict from tmp_predict where date = sp.sp_date) end)"//
//                        + "as sp_predict_count, "
//                        //+ "(case when sp.normal_forecast = '1' then"//
//                        + "(select round(sp.normal_rain * (select param_value from bdms.恶劣天气参数表 where param_name='rain_param' and area = ( select area from tmp_param)) "
//                        + "+ sp.normal_wind * (select param_value from bdms.恶劣天气参数表 where param_name='wind_param' and area = ( select area from tmp_param)) "
//                        + "+ (select param_value from bdms.恶劣天气参数表 where param_name='const' and area = ( select area from tmp_param))) from dual) "
//                        //+ "else (select predict from tmp_predict where date = sp.normal_date) end)"//
//                        + "as normal_predict_count "
//                        + "from (select t.*, (select wind from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_wind, "
//                        + "(select rainfall from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_rain, "
//                        + "(select high_temp from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_temp, "
//                        + "(select rain_level from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_rain_level, "
//                        + "(select temp_level from tmp_rain where time = normal_date and forecast= t.normal_forecast) as normal_temp_level, "
//                        + "(select wind from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_wind, "
//                        + "(select rainfall from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_rain, "
//                        + "(select high_temp from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_temp, "
//                        + "(select rain_level from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_rain_level, "
//                        + "(select temp_level from tmp_rain where time = sp_date and forecast= t.sp_forecast) as sp_temp_level "
//                        + "from tmp_param t) sp)");
//
//        // 24小时天气值
//        sqlMap.put("weather_sp_24_default",
//                "select time, round(avg(rainfall), 2) as rain, round(avg(temperature)) as temperature, round(avg(wind), 2) as wind "
//                        + "from ( select sp_date, type , area from bdms.恶劣天气日期配置 where 1=1 " + "/areaName# and area = '?' "
//                        + "/type# and type = '?' " + "/) t left join ( select time , rainfall, temperature, wind from bdms.v_weather_real where 1=1"
//                        + "/areaName# and area = '?' " + ") w on substr(w.time, 1, 10) = sp_date group by t.area, time order by time");
//
//        sqlMap.put("weather_sp_24_date",
//                "select time, round(avg(rainfall),2) as rain, round(avg(temperature)) as temperature, round(avg(wind), 2) as wind "
//                        + "from bdms.v_weather_real where 1=1 " + "/date# and substr(time, 1, 10) = '?' " + "/areaName# and area = '?' "
//                        + "/ group by area, time order by time");
//
//        sqlMap.put(
//                "weather_sp_24_forecast",
//                "select city as area , date_time as time, "
//                        + "round(avg(case when rain = '-99.00' then 0 else rain end), 2) as rain, round(avg(temperature)) as temperature, round(avg(wind_velocity), 2) as wind "
//                        + "from (select city, location, station_code from weather.weather_station_def) as A, "
//                        + "(select station_code, date_time , rain ,wind_velocity, (case when temperature > 40 then round(40+2*rand()-1.6) else temperature end) as temperature from weather.WEATHER_FORECAST_DATA) as B "
//                        + "where a.station_code = b.station_code " + "/date# and substr(date_time, 1, 10) = '?' " + "/areaName# and city = '?' "
//                        + "/group by city, date_time order by date_time");
//
//        // 24小时告警记录
//        sqlMap.put("signal_sp_24_default", "select occurtime as time from ( select occurtime, signalname from bdms.恶劣天气关联信号 t "
//                + "left join ems.bdms_substation s on t.stationid = s.st_id " + "where 1=1 " + "/areaName# and area_name = '?' "
//                + "/signal# and signalid = '?' " + "/type# and signalid in (select id from bdms.恶劣天气关联信号配置 where type='?') "
//                + "/ ) s right join ( select sp_date from bdms.恶劣天气日期配置 where 1=1 " + "/areaName# and area = '?' " + "/type# and type = '?' "
//                + "/ ) d on to_char(s.occurtime, 'yyyy-mm-dd') = d.sp_date where s.signalname is not null order by time");
//
//        sqlMap.put("signal_sp_24_date", "select occurtime as time from ( select occurtime, signalname from bdms.恶劣天气关联信号 t "
//                + "left join ems.bdms_substation s on t.stationid = s.st_id " + "where 1=1 " + "/areaName# and area_name = '?' "
//                + "/signal# and signalid = '?' " + "/type# and signalid in (select id from bdms.恶劣天气关联信号配置 where type='?') " + "/ ) s where 1=1 "
//                + "/date# and substr(occurtime, 1, 10) = '?' " + " order by time");
//
//        // 恶劣天气关联信号
//        sqlMap.put("weather_signal", "select * from bdms.恶劣天气关联信号配置 order by name");
//
//        sqlMap.put("weather_area_predict", "with tmp_param as (" + "select " + "/ spDate# '?' as sp_date from dual) " + "select * from (with "
//                + "tmp_rain as ((" + "select AREA, TIME, WIND, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '1' as forecast "
//                + "from bdms.V_WEATHER_FORECAST where time = (select sp_date from tmp_param)) union all ("
//                + "select AREA, TIME, WIND, RAINFALL, HIGH_TEMP, RAIN_LEVEL, TEMP_LEVEL, '0' as forecast "
//                + "from bdms.rain_message_info where time = (select sp_date from tmp_param))) "
//
//                + ", tmp_predict as (select * from bdms.恶劣天气历史预测值) "
//
//                + "select AREA," + "(select value from tmp_predict where date = sp.sp_date and area = sp.area) as sp_count, "
//                + "(case when sp.sp_date not in (select date from tmp_predict where area = sp.area) "
//                + "then (select round(sp.sp_rain * (select param_value from bdms.恶劣天气参数表 where param_name='rain_param' and area = sp.area) "
//                + "+ sp.sp_wind * (select param_value from bdms.恶劣天气参数表 where param_name='wind_param' and area = sp.area) "
//                + "+ (select param_value from bdms.恶劣天气参数表 where param_name='const' and area = sp.area)) from dual) "
//                + "else (select predict from tmp_predict where date = sp.sp_date and area = sp.area)end) as sp_predict_count "
//                + "from (select t.*, wind as sp_wind, rainfall as sp_rain, high_temp as sp_temp, AREA from tmp_param t "
//                + "left join tmp_rain r on time = sp_date and forecast="
//                + "(case when t.sp_date not in (select date from tmp_predict where area = r.area) then '1' else '0' end)) sp)");
//    }
//
//    @RequestMapping(value = "/newSearch.action")
//    public @ResponseBody
//    void getEvents(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        request.setCharacterEncoding("utf-8");
//
//        String viewid = request.getParameter("viewid");
//        String userKey = request.getParameter("key");
//        String sql = sqlMap.get(viewid);
//        String location = "";
//        if (request.getParameter("areaName") != null && !request.getParameter("areaName").trim().equals("")) {
//            location = java.net.URLDecoder.decode(request.getParameter("areaName"), "UTF-8");
//        }
//        boolean isProvince = false;
//        if ("report_dianliu_a".equals(viewid)) {
//            // if (userKey != null && !"".equals(userKey)) {
//            // List<OrgBean> orgs = (List<OrgBean>)
//            // UserInfoTool.getInfo(userKey, "OrgBeanLs");
//            // if (orgs != null && !orgs.isEmpty()) {
//            // String where = "";
//            // String orgIds = "";
//            // for (OrgBean org : orgs) {
//            // if (org.getOrgName().contains("省")) {
//            // where = " and 1=1";
//            isProvince = true;
//            // break;
//            // }
//            // if ("".equals(orgIds)) {
//            // orgIds += "'" + org.getOrgID() + "'";
//            // } else {
//            // orgIds += ",'" + org.getOrgID() + "'";
//            // }
//            // }
//            // if ("".equals(where)) {
//            // where = " and depid in (" + orgIds + ")";
//            // }
//            // sql += where;
//            // } else {
//            // return;
//            // }
//            // } else {
//            // return;
//            // }
//        } else if ("report_dianliu_s2".equals(viewid)) {
//            if (userKey != null && !"".equals(userKey)) {
//                String where = (String) UserInfoTool.getInfo(userKey, "BConditionStr2");
//                if ("all".equals(where)) {
//                    where = "";
//                } else if (!"".equals(where)) {
//                    where = " and " + where;
//                }
//                sql += where + "order by length(DYDJ) desc, DYDJ desc, st_name";
//            } else {
//                return;
//            }
//        }
//        if (sql == null)
//            return;
//        String[] s = sql.split("/");
//        sql = s[0];
//        for (int i = 1; i < s.length; i++) {
//            int index = s[i].indexOf('#');
//            if (index == -1) {
//                sql += " " + s[i];
//                continue;
//            }
//            String key = s[i].substring(0, index).trim(), value = null;
//            if (request.getParameter(key) != null && !request.getParameter(key).trim().equals("")) {
//                value = java.net.URLDecoder.decode(request.getParameter(key), "UTF-8");
//            }
//            // System.out.println(key+"="+value);
//            if (value != null && !value.equals("")) {
//                sql += " " + s[i].substring(index + 1).replaceAll("\\?", value);
//            }
//            sql = sql.replace('\\', '/');
//        }
//        // System.out.println("SQL:"+sql);
//        long staTime = System.currentTimeMillis();
//        if ("report_resp_area".equals(viewid)) {
//            if ("省调".equals(location)) {
//                location = "江苏省调";
//                sql = "select AREA_VALUE  ID,AREA_JC NAME from EMS.RESP_AREA_DEF where DWMC = '" + location + "'";
//            } else if ("全部".equals(location)) {
//                location = "全部";
//                sql = "select AREA_VALUE  ID,AREA_JC NAME from EMS.RESP_AREA_DEF ";
//            } else {
//                location = location + "地调";
//                sql = "select AREA_VALUE  ID,AREA_JC NAME from EMS.RESP_AREA_DEF where DWMC = '" + location + "'";
//            }
//        }
//        List<Map<String, Object>> eventList = DBTemplate.getInstance().getResultMapList(sql);
//        System.out.println("耗时:" + viewid + ":" + (System.currentTimeMillis() - staTime) / 1000 + "秒");
//        try {
//            java.lang.reflect.Type types = new com.google.gson.reflect.TypeToken<Object>() {
//            }.getType();
//            Gson gson = new Gson();
//
//            if (eventList != null) {
//                if ("report_dianliu_a".equals(viewid)) {
//                    if (isProvince) {
//                        Map<String, Object> area = new HashMap<String, Object>();
//                        area.put("AREA_NAME", "省调");
//                        eventList.add(0, area);
//                    }
//                    Map<String, Object> area1 = new HashMap<String, Object>();
//                    area1.put("AREA_NAME", "全部");
//                    eventList.add(0, area1);
//                }
//                String json = gson.toJson(eventList, types);
//                response.setCharacterEncoding("utf-8");
//                response.setContentType("text/html");
//                response.getWriter().write(json);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "/weatherSignalImport.action")
//    public void weatherSignalImport(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String DoResult = "yes";
//        String areaRestrict = "";
//        String areaStr = request.getParameter("area");
//        if (areaStr != null && !"".equals(areaStr)) {
//            areaStr = URLDecoder.decode(areaStr, "utf-8");
//            areaStr = URLDecoder.decode(areaStr, "utf-8");
//            String[] areaArray = areaStr.split(",");
//            areaRestrict = " and dwmc in (";
//            for (int i = 0; i < areaArray.length; i++) {
//                if (i != 0) {
//                    areaRestrict += ",";
//                }
//                areaRestrict += "'" + areaArray[i] + "'";
//            }
//            areaRestrict += ")";
//        }
//
//        try {
//            String DataImportManualStartDate = request.getParameter("DataImportManualStartDate");// 开始时间
//            String DataImportManualEndDate = request.getParameter("DataImportManualEndDate");// 截止时间
//            // 设置数据预处理的最早时间点
//
//            Date needImportManualDayTime = TimeStringChangeHelp.StrToDate2(DataImportManualStartDate);
//            Date needImportManualEndDate = TimeStringChangeHelp.StrToDate2(DataImportManualEndDate);
//
//            while (needImportManualDayTime.before(needImportManualEndDate) || needImportManualDayTime.equals(needImportManualEndDate)) {
//
//                System.out.println("需要进行数据预处理的时间:" + DataImportManualStartDate);
//                DataPreDoMethod dpdm = new DataPreDoMethod();
//                try {
//                    dpdm.DataPreDoByDay(DataImportManualStartDate);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                DataImportManualStartDate = TimeStringChangeHelp.getSpecifiedDayAfter(DataImportManualStartDate);
//                needImportManualDayTime = TimeStringChangeHelp.StrToDate2(DataImportManualStartDate);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            DoResult = "no";
//        }
//
//        try {
//            Gson gson = new Gson();
//            String json = gson.toJson(DoResult);
//
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "/buChongModelTrain.action")
//    public @ResponseBody
//    void buChongModelTrain(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String DoResult = "yes";
//
//        try {
//            // 补充关联分析训练开始时间
//            String starttime = request.getParameter("starttime");
//            // 补充关联分析训练截止时间
//            String endtime = request.getParameter("endtime");
//            TimeTableDaoChange ttdc = new TimeTableDaoChange();
//
//            String needModelTrainTime = starttime;
//            Date needModelTrainTimeDate = TimeStringChangeHelp.StrToDate2(needModelTrainTime);
//            Date endModelTrainTimeDate = TimeStringChangeHelp.StrToDate2(endtime);
//            String timeCell = ttdc.getTimeTableMessage().getTimeCell();
//
//            while (needModelTrainTimeDate.before(endModelTrainTimeDate) || needModelTrainTimeDate.equals(endModelTrainTimeDate)) {
//                System.out.println("需要进行数据模型训练的时间:" + needModelTrainTime);
//
//                // ModelTarinMethod mtm = new ModelTarinMethod();
//                // //执行模型训练算法
//                // mtm.modelTrainByDay(needModelTrainTime, timeCell);
//                try {
//                    ModelTrainResultDiffBay mtrb = new ModelTrainResultDiffBay();
//                    mtrb.modelTrainByDay(needModelTrainTime, timeCell);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                needModelTrainTime = TimeStringChangeHelp.getSpecifiedDayAfter(needModelTrainTime);
//                needModelTrainTimeDate = TimeStringChangeHelp.StrToDate2(needModelTrainTime);
//            }
//
//            System.out.println("关联分析模型训练完成");
//        } catch (Exception e) {
//            e.printStackTrace();
//            DoResult = "no";
//        }
//
//        try {
//            Gson gson = new Gson();
//            String json = gson.toJson(DoResult);
//
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().write(json);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @RequestMapping(value = "/getSignalLine.action")
//    public @ResponseBody
//    void getSignalLine(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//
//        String sql = "";
//        String month = "";
//        String viewid = request.getParameter("viewid");
//        String date = request.getParameter("date");
//        String areaName = URLDecoder.decode(request.getParameter("areaName"), "utf-8");
//        String type = request.getParameter("type");
//
//        if (date != null && !"".equals(date)) {
//            String[] dateArr = date.split("-");
//            month = dateArr[0] + "_" + dateArr[1];
//        }
//        // 24小时告警记录
//        if ("signal_sp_24_default".equals(viewid)) {
//            sql = "select sp_date from bdms.恶劣天气日期配置 where area = '" + areaName + "' and type = '" + type + "'";
//            List<Map<String, Object>> dateList = DBTemplate.getInstance().getResultMapList(sql);
//
//            if (dateList != null && dateList.size() > 0) {
//                date = (String) dateList.get(0).get("SP_DATE");
//                if (date != null) {
//                    String[] dateArr = date.split("-");
//                    month = dateArr[0] + "_" + dateArr[1];
//                }
//            }
//        }
//        sql = "select occur_time || ':00:00' as time, count(0) as count "
//                + "from (select distinct to_char(occur_time, 'yyyy-mm-dd hh24') as occur_time, yx_id " + "from hisdb.yx_bw_" + month + " where 1=1 "
//                + "and status = '28' and restrain_flag = '0' " + "and instr(content, '调试') = 0 and instr(content, '检修') = 0 "
//                + "and occur_time >= to_date('" + date + "', 'yyyy-mm-dd') " + "and occur_time < dateadd(d, 1, to_date('" + date
//                + "', 'yyyy-mm-dd')) " + "and st_id in (select st_id from ems.bdms_substation where area_name='" + areaName + "')) "
//                + "group by occur_time order by time";
//        List<Map<String, Object>> eventList = DBTemplate.getInstance().getResultMapList(sql);
//        try {
//            Gson gson = new Gson();
//            if (eventList != null) {
//                String json = gson.toJson(eventList);
//                response.setCharacterEncoding("utf-8");
//                response.setContentType("text/html");
//                response.getWriter().write(json);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(value = "/exportTableData.action")
//    public @ResponseBody void exportTableData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//        String dwmc=request.getParameter("areavalue");
//        String startDate=request.getParameter("startDate");
//        String endDate=request.getParameter("endDate");
//        String respArea=request.getParameter("resp_area");
//        String dydj=request.getParameter("dydj");
//        String stationId=request.getParameter("st_id");
//
//        // modf 2018-06-05注释掉dwmc的判断
//        if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)/*||StringUtils.isEmpty(dwmc)*/){
//            return;
//        }
//        try {
//            dwmc = URLDecoder.decode(dwmc, "utf-8");
//            dwmc = URLDecoder.decode(dwmc, "utf-8");
//            if ("省调".equals(dwmc)) {
//                dwmc = "江苏";
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        String addColumn = "";
//        if (StringUtils.isNotEmpty(stationId)) {
//            addColumn = "occur_time,";
//        }
//
//        String resultSql = "SELECT " + addColumn
//                + "STATION_NAME as st_name , sum(UP_NUMBER) as sum "
//
//                // modf 2018-06-05添加
//                + ", (select dwmc from EMS.BDMS_SUBSTATION s where s.st_id = t.st_id) as dwmc "
//
//                + "FROM BDMS.CURRENT_COUNT_TABLE t where st_id in ("
//                + "SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION "
//
//                // modf 2018-06-05注释，并添加修改
////			+ "WHERE DWMC = '" + dwmc  + "' "
//                + "WHERE 1 = 1 " + (StringUtils.isNotEmpty(dwmc) ? " and DWMC = '" + dwmc  + "' " : "")
//
//                + (StringUtils.isNotEmpty(dydj) ? " and dydj = '" + dydj + "' " : "")
//                + ") "
//                + (StringUtils.isNotEmpty(stationId) ? "' and st_id = '" + stationId + "' " : "")
//                + "and OCCUR_TIME>=to_date('" + startDate + "', 'yyyy-mm-dd') "
//                + "and OCCUR_TIME<=to_date('" + endDate + "', 'yyyy-mm-dd') "
//                + "group by " + addColumn + "STATION_NAME, ST_ID order by sum desc";
//
//        if (StringUtils.isNotEmpty(respArea)) {
//            if (StringUtils.isNotEmpty(stationId)) {
//                addColumn = "date,";
//            }
//            resultSql = "SELECT " + (!addColumn.equals("") ? "date as occur_time, " : "")
//                    + "st_name , sum(UP_NUMBER) as sum "
//
//                    // modf 2018-06-05添加
//                    + ", (select dwmc from EMS.BDMS_SUBSTATION s where s.st_id = t.st_id) as dwmc "
//
//                    + "FROM BDMS.CURRENT_COUNT_TABLE t where st_id in ("
//                    + "SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION "
//
//                    // modf 2018-06-05注释，并添加修改
////              + "WHERE DWMC = '" + dwmc + "' "
//                    + "WHERE 1 = 1 " + (StringUtils.isNotEmpty(dwmc) ? " and DWMC = '" + dwmc  + "' " : "")
//
//                    + (StringUtils.isNotEmpty(dydj) ? " and dydj = '" + dydj + "' " : "")
//                    + ") "
//                    + (StringUtils.isNotEmpty(stationId) ? "' and st_id = '" + stationId + "' " : "")
//                    + "and resp_area='" + respArea + "' "
//                    + "and date>=to_date('" + startDate + "', 'yyyy-mm-dd') "
//                    + "and date<=to_date('" + endDate + "', 'yyyy-mm-dd') "
//                    + "group by " + addColumn + "st_name, ST_ID order by sum desc";
//        }
//
//        List<Map<String, Object>> countList = DBTemplate.getInstance().getResultMapList(resultSql);
//
//        String dateStr = "";
//        if (startDate.equals(endDate)) {
//            dateStr = startDate;
//        } else {
//            dateStr = startDate + "至" + endDate;
//        }
//
//        try {
//            response.setContentType("application/msexcel; charset=utf-8");
//
//            // modf 2018-06-05注释，并添加新的代码
////			response.setHeader("Content-Disposition", "attachment;filename=\""
////						+ new String((dwmc+" "+startDate+"至"+endDate+ "电流越限详情" + ".xls").getBytes("utf-8"),"ISO-8859-1")+"\"");
//            String fileName = (StringUtils.isEmpty(dwmc) ? "全省" : dwmc) + " " + startDate + "至" + endDate + "电流越限详情" + ".xls";
//            response.setHeader("Content-Disposition", "attachment;filename=\"" + new String((fileName).getBytes("utf-8"), "ISO-8859-1") + "\"");
//
//            OutputStream ostream = response.getOutputStream();
//
//            WritableWorkbook book=Workbook.createWorkbook(ostream);
//            WritableSheet sheet=book.createSheet("电流越限", 0);
//            String[] columnName = new String[]{"序号", "时间", "地区", "变电站名", "越上限次数"};
//            for (int i = 0; i < columnName.length; i ++) {
//                Label columnLabel=new Label(i, 0, columnName[i]);
//                sheet.addCell(columnLabel);
//            }
//            int j=1;
//            for(Map<String, Object> map:countList){
//                Label data=new Label(0, j, j+"");
//                sheet.addCell(data);
//                Label data1=new Label(1, j, (map.get("OCCUR_TIME") != null ? map.get("OCCUR_TIME").toString() : dateStr));
//                sheet.addCell(data1);
//
//                // modf 2018-06-05注释，并添加新的代码
////				Label data2=new Label(2, j, dwmc);
//                Label data2=new Label(2, j, map.get("DWMC").toString());
//
//                sheet.addCell(data2);
//                Label data3=new Label(3, j, map.get("ST_NAME").toString());
//                sheet.addCell(data3);
//                Label data4=new Label(4, j,map.get("SUM").toString());
//                sheet.addCell(data4);
//                j++;
//            }
//            book.write();
//            book.close();
//            ostream.flush();
//            ostream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //20180727 rensz修改 	导出南通电流越限告警分析报表
//    @RequestMapping(value = "/exportTable2Data.action")
//    public @ResponseBody void exportTable2Data(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
//        String dwmc=request.getParameter("areavalue");
//        String startDate=request.getParameter("startDate");
//        String endDate=request.getParameter("endDate");
//        Map<String, Object> dataMap = new HashMap<String, Object>();
//        String name="";
//
//        if(StringUtils.isEmpty(startDate)||StringUtils.isEmpty(endDate)/*||StringUtils.isEmpty(dwmc)*/){
//            return;
//        }
//        try {
//            dwmc = URLDecoder.decode(dwmc, "utf-8");
//            dwmc = URLDecoder.decode(dwmc, "utf-8");
//            if (!"全部".equals(dwmc)) {
//                return;
//            }
//            String whereSql=" and a.occurtime >='"+startDate+"' and a.occurtime <='"+endDate+" 23:59:59' and a.st_id=b.st_id ";
//
//            String[] DayTimeArray = startDate.split("-");
//            String tableHelp = DayTimeArray[0]+"_"+DayTimeArray[1];
//            String tableName1="HISDB.YC_OVER_"+tableHelp;
//            String tableName2="HISDB.DEV_STATE_WARN_"+tableHelp;	//江苏省调,徐州地调,镇江地调
//            String Sql1="select a.*,b.OBJ_ID,b.ST_NAME,b.DYDJ,b.AREA_NAME from "+tableName1+" a,EMS.BDMS_SUBSTATION b where a.ST_ID=b.ST_ID and a.content like '%电流%越上限%' and a.dwmc not in ('江苏省调','镇江地调','徐州地调')"+whereSql;
//            String Sql2="select a.*,b.OBJ_ID,b.ST_NAME,b.DYDJ,b.AREA_NAME from "+tableName2+" a,EMS.BDMS_SUBSTATION b where a.ST_ID=b.ST_ID and a.content like '%电流%越上限%' and a.dwmc in ('江苏省调','镇江地调','徐州地调') "+whereSql;
//            List<Map<String, Object>> ycData = DBTemplate.getInstance().getResultMapList(Sql1);
//            List<Map<String, Object>> devWarnData = DBTemplate.getInstance().getResultMapList(Sql2);
//
//            List<Map<String, Object>> deviceList = DBTemplate.getInstance()
//                    .getResultMapList("select OBJ_ID as DEVICE_ID, to_char(ccrq, 'yyyy-mm-dd hh24:mi:ss') CCRQ, "
//                            + "to_char(tyrq, 'yyyy-mm-dd hh24:mi:ss') TYRQ, ssbdz, sbmc from BDMS.V_PMS_YCSB "
//                            + "where zt = '1' and yxbm is not null and dydj is not null and sccj is not null and ssbdz is not null");
//
//            List<Map<String,Object>> voltageList=DBTemplate.getInstance().getResultMapList("select id,name from BDMS.BASEVOLTAGE group by id,name;");
//            Map<String,Object> volMap=new HashMap<String,Object>();
//            for(Map<String,Object> voltage:voltageList){
//                if(!volMap.containsKey(voltage.get("ID").toString())){
//                    volMap.put(voltage.get("ID").toString(), voltage.get("NAME").toString());
//                }
//            }
//            ContentHelp contenthelp = new ContentHelp();
//            String DeviceTypeSql = "select * from BDMS.DEVICE_TYPE_FAMILY";
//            List<DeviceTypeNameBean> listDeviceTypeNameBean = DBTemplate.getInstance().getResultRowMapperList(DeviceTypeSql,new DeviceTypeNameBeanMapper());
//            int ZBSum=0,XLSum=0;
//            int ZB500Num=0,ZB220Num=0,ZB110Num=0,ZB35Num=0;
//            int XL500Num=0,XL220Num=0,XL110Num=0,XL35Num=0,XL20Num=0,XL10Num=0;
//            for(int i=0;i<ycData.size();i++){
//                String contentHelp = ycData.get(i).get("CONTENT").toString();//content
//                contentHelp = UnRepairString.removerepeatedcharx(contentHelp);
//                String contentHelptemp[] = contentHelp.split(" ");
//                String occureTime = contentHelptemp[0]+" "+contentHelptemp[1];//时间
//
//                String vlname=volMap.get(ycData.get(i).get("VLTY_ID").toString()).toString();
//
//                AlarmContent alarmContent = contenthelp.getNumberNameOfDevice(contentHelp,listDeviceTypeNameBean);//编号与设备类型名称
//                String nameStr = "";
//                nameStr = alarmContent.getName();	//设备类型
//                if("主变".equals(nameStr)){
//                    ZBSum++;
//                    if(vlname.indexOf("500")!=-1){
//                        ZB500Num++;
//                        //if()
//                    }
//                }
//
//            }
//            // 输出文件
//            name = "电流越限分析报告";
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + new String((name + ".doc").getBytes(), "iso-8859-1"));
//            response.setContentType("application/msword");
//            OutputStream ostream = response.getOutputStream();
//
//            DocumentHandler mdoc = new DocumentHandler();
//            mdoc.createDoc(dataMap, ostream, "frequentlyAnaly1.ftl");
//            ostream.close();
//
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    // modf 2018-06-06注释备份
////	@RequestMapping(value = "/exportTableData.action")
////	public @ResponseBody
////	void exportTableData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
////		String dwmc = request.getParameter("areavalue");
////		String startDate = request.getParameter("startDate");
////		String endDate = request.getParameter("endDate");
////		String respArea = request.getParameter("resp_area");
////		String dydj = request.getParameter("dydj");
////		String stationId = request.getParameter("st_id");
////
////		if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(dwmc)) {
////			return;
////		}
////		try {
////			dwmc = URLDecoder.decode(dwmc, "utf-8");
////			dwmc = URLDecoder.decode(dwmc, "utf-8");
////			if ("省调".equals(dwmc)) {
////				dwmc = "江苏";
////			}
////		} catch (UnsupportedEncodingException e) {
////			e.printStackTrace();
////		}
////
////		String addColumn = "";
////		if (StringUtils.isNotEmpty(stationId)) {
////			addColumn = "occur_time,";
////		}
////
////		String resultSql = "SELECT " + addColumn + "STATION_NAME as st_name , sum(UP_NUMBER) as sum "
////				+ "FROM BDMS.CURRENT_COUNT_TABLE where st_id in (" + "SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION " + "WHERE DWMC = '" + dwmc
////				+ "' " + (StringUtils.isNotEmpty(dydj) ? " and dydj = '" + dydj + "' " : "") + ") "
////				+ (StringUtils.isNotEmpty(stationId) ? "' and st_id = '" + stationId + "' " : "") + "and OCCUR_TIME>=to_date('" + startDate
////				+ "', 'yyyy-mm-dd') " + "and OCCUR_TIME<=to_date('" + endDate + "', 'yyyy-mm-dd') " + "group by " + addColumn
////				+ "STATION_NAME, ST_ID order by sum desc";
////
////		if (StringUtils.isNotEmpty(respArea)) {
////			if (StringUtils.isNotEmpty(stationId)) {
////				addColumn = "date,";
////			}
////			resultSql = "SELECT " + (!addColumn.equals("") ? "date as occur_time, " : "") + "st_name , sum(UP_NUMBER) as sum "
////					+ "FROM BDMS.CURRENT_COUNT_TABLE where st_id in (" + "SELECT DISTINCT ST_ID FROM EMS.BDMS_SUBSTATION " + "WHERE DWMC = '" + dwmc
////					+ "' " + (StringUtils.isNotEmpty(dydj) ? " and dydj = '" + dydj + "' " : "") + ") "
////					+ (StringUtils.isNotEmpty(stationId) ? "' and st_id = '" + stationId + "' " : "") + "and resp_area='" + respArea + "' "
////					+ "and date>=to_date('" + startDate + "', 'yyyy-mm-dd') " + "and date<=to_date('" + endDate + "', 'yyyy-mm-dd') " + "group by "
////					+ addColumn + "st_name, ST_ID order by sum desc";
////		}
////
////		List<Map<String, Object>> countList = DBTemplate.getInstance().getResultMapList(resultSql);
////
////		String dateStr = "";
////		if (startDate.equals(endDate)) {
////			dateStr = startDate;
////		} else {
////			dateStr = startDate + "至" + endDate;
////		}
////
////		try {
////			response.setContentType("application/msexcel; charset=utf-8");
////			response.setHeader("Content-Disposition", "attachment;filename=\""
////					+ new String((dwmc + " " + startDate + "至" + endDate + "电流越限详情" + ".xls").getBytes("utf-8"), "ISO-8859-1") + "\"");
////			OutputStream ostream = response.getOutputStream();
////
////			WritableWorkbook book = Workbook.createWorkbook(ostream);
////			WritableSheet sheet = book.createSheet("电流越限", 0);
////			String[] columnName = new String[] { "序号", "时间", "地区", "变电站名", "越上限次数" };
////			for (int i = 0; i < columnName.length; i++) {
////				Label columnLabel = new Label(i, 0, columnName[i]);
////				sheet.addCell(columnLabel);
////			}
////			int j = 1;
////			for (Map<String, Object> map : countList) {
////				Label data = new Label(0, j, j + "");
////				sheet.addCell(data);
////				Label data1 = new Label(1, j, (map.get("OCCUR_TIME") != null ? map.get("OCCUR_TIME").toString() : dateStr));
////				sheet.addCell(data1);
////				Label data2 = new Label(2, j, dwmc);
////				sheet.addCell(data2);
////				Label data3 = new Label(3, j, map.get("ST_NAME").toString());
////				sheet.addCell(data3);
////				Label data4 = new Label(4, j, map.get("SUM").toString());
////				sheet.addCell(data4);
////				j++;
////			}
////			book.write();
////			book.close();
////			ostream.flush();
////			ostream.close();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
//
//}
//
