package com.chrhc.project.sc.kinship.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SortByTime implements Comparator<PersonRecord> {

	@Override
	public int compare(PersonRecord o1, PersonRecord o2) {
		String createTime1 = o1.getCreateTime();
		String createTime2 = o2.getCreateTime();
		if(StringUtils.isNotBlank(createTime1) && StringUtils.isNotBlank(createTime2)){
			int i = SortByTime.compare_date(createTime1, createTime2);
			return i;
		}
		return 0;
	}

	public static int compare_date(String DATE1, String DATE2) {
        
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
               
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
              
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
