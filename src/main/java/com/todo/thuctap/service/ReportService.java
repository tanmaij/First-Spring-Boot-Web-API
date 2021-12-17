package com.todo.thuctap.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    List<Map<String, Object>> filterList(List<Map<String, Object>> list) {
        int s = list.size();
        for (Map<String, Object> item : list) {
            Timestamp a = parseTimeStamp(item.get("createdAt").toString());
            item.put("createdAt", a);
            item.put("total", s);
        }
        return list;
    }

    Timestamp parseTimeStamp(String o) {
        System.out.print(o);
        final String DEFAULT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        SimpleDateFormat f = new SimpleDateFormat(DEFAULT_FORMAT);
        Date d = null;
        try {
            if (o.length() < 19)
                o += ":00";
            d = f.parse(o);
        } catch (ParseException e) {

            e.printStackTrace();
        }
        long milliseconds = d.getTime();
        return new Timestamp(milliseconds);
    }

    public byte[] getReportDemo(List<Map<String, Object>> list) {
        list = filterList(list);
        JasperReport jasperReport = null;
        try {

            jasperReport = JasperCompileManager.compileReport(new ClassPathResource("templates/Blank_A4.jrxml").getInputStream());
//            String sourceFileName = "E:\\thuctap\\src\\main\\resources\\templates\\Blank_A4.jrxml";
//            JasperCompileManager.compileReportToFile(sourceFileName);

        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(list);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list, false);
        Map<String, Object> parameters = new HashMap<>();
        try {
            return (JasperExportManager.exportReportToPdf(JasperFillManager.fillReport(jasperReport, parameters, dataSource)));
        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
