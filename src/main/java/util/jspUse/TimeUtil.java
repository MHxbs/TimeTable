package util.jspUse;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    //传入这样的格式：：星期一第9-10节1-16周，解析得到有哪些周要上课，返回TreeSet
    public static TreeSet getWeek(String time){
        String time1=time.replaceAll(" ","");

        time1+=" ";
        TreeSet allWeek=new TreeSet();
        //把周数按逗号分开，然后分别判断其中的周数
        Pattern pattern=Pattern.compile("星期\\d第(.*?)节(.*?) ");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){

            String[] ss=matcher.group(2).split(",");
            for (int i=0;i<ss.length;i++) {

                allWeek.addAll(getIntWeek(ss[i]));

            }
        }
        return allWeek;

    }

    // 把传过来的String类型的周数转化为set类型
    public static Set getIntWeek(String week){
        TreeSet weeks=new TreeSet();

        Pattern pattern1=Pattern.compile("(\\d*?)-(\\d*?)周");
        Matcher matcher1=pattern1.matcher(week);
        Pattern pattern2=Pattern.compile("(\\d*?)-(\\d*?)双周");
        Matcher matcher2=pattern2.matcher(week);
        Pattern pattern3=Pattern.compile("(\\d*?)-(\\d*?)单周");
        Matcher matcher3=pattern3.matcher(week);
        Pattern pattern4=Pattern.compile("(\\d*?)周");
        Matcher matcher4=pattern4.matcher(week);
        //是(\d*？)-(\d*？)周
        if (matcher1.matches()){
            int i;
            int weekBegin=Integer.parseInt(matcher1.group(1));
            int weekEnd=Integer.parseInt(matcher1.group(2));
            for (i=weekBegin;i<=weekEnd;i++) {
                weeks.add(i);
            }

            return weeks;
        }


        //是(\d*？)-(\d*？)双周
        if (matcher2.matches()){


            int weekBegin = Integer.parseInt(matcher2.group(1));
            int weekEnd = Integer.parseInt(matcher2.group(2));
            for (int i = weekBegin; i <= weekEnd; i++) {
                if (i % 2 == 0) {
                    weeks.add(i);
                }
            }
            return weeks;
        }

        // 是(\d*？)-(\d*？)单周
        if (matcher3.matches()){

            int weekBegin=Integer.parseInt(matcher3.group(1));
            int weekEnd=Integer.parseInt(matcher3.group(2));
            for (int i=weekBegin;i<=weekEnd;i++){
                if (i%2!=0){
                    weeks.add(i);
                }
            }
            return weeks;
        }

        // 是(\d*？)周
        if (matcher4.matches()){

            int singleWeek=Integer.parseInt(matcher4.group(1));
            weeks.add(singleWeek);
            return weeks;

        }
        return weeks;
    }

    //传入这样的格式：：星期一第9-10节1-16周，得到是星期几
    public static String getDay(String time){
        //把字符串中空格全部去掉
        String time1=time.replaceAll(" ","");

        String result=null;
        Pattern pattern=Pattern.compile("星期\\d");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){
            result=matcher.group();
        }
        return  result;
    }
    //传入这样的格式：：星期一第9-10节1-16周，解析得到是第几节课
    public static String getLesson(String time){
        String time1=time.replaceAll(" ","");

        String result=null;
        Pattern pattern=Pattern.compile("第(.*?)节");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){
            result=matcher.group();
        }
        return  result;
    }
    // 得到是那几周的字符串
    public static String getWeekName(String time) {
        String time1 = time.replaceAll(" ", "");
        String result = null;
        time1 += " ";
        Pattern pattern = Pattern.compile("星期\\d第(.*?)节(.*?) ");
        Matcher matcher = pattern.matcher(time1);
        if (matcher.find()){

            result=matcher.group(2);
        }
        return result;
    }
}
