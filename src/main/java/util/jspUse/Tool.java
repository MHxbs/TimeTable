package util.jspUse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tool {
    // 发送url得到教务处课表页面的源代码
    public static String getData(String strUrl) throws IOException {
        //创建一个url

        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //得到连接中输入流，缓存到bufferedReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), "UTf-8"));
        StringBuilder builder = new StringBuilder();
        String line = null;
        //每行每行地读出
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        connection.disconnect();
        //返回字符串
        return builder.toString();
    }
    // 先得到一节课的信息，如果这一周还有他的课，jspUse.Tool.getTimetableRest方法来得到
    public static String getTimetableFirst(String url) throws IOException {

        StringBuilder sb = new StringBuilder();
        //得到教务在线源代码数据
        String data = Tool.getData(url);
        System.out.println(data);
        //String data=jspUse.Tool.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");

        //创建一个JSONObject
        JSONObject jsonObject = new JSONObject();
        // 正则表达式，来匹配课程
        Pattern pattern = Pattern.compile("<tr ><td r(.*?)>(.*?)</td>                    <td(.*?)>(.*?)</td>" +
                "                    <td(.*?)>(.*?)</td><td(.*?)>(.*?)</td>" +
                "<td(.*?)>(.*?)</td><td>(.*?)</td>                     <td>(.*?)</td>" +
                "<td>(.*?)</td>                    <td(.*?)>" +
                "<a href='(.*?)' target=_blank>名单</a></td>                    <td(.*?)></td>                    </tr>");
        Matcher matcher = pattern.matcher(data);
        // while循环，每种课生成一个jsonObject对象，然后用StringBuilder拼接在一起
        while (matcher.find()) {

            //System.out.println(matcher.group(2));
            //System.out.println(matcher.group(4));
            //System.out.println(matcher.group(6));
            //System.out.println(matcher.group(8));
            //System.out.println(matcher.group(11));
            //System.out.println(matcher.group(12));
            //System.out.println(matcher.group(13));
            //System.out.println(matcher.group(15));
            jsonObject.put("course_num", matcher.group(2));
            jsonObject.put("course", matcher.group(4));
            jsonObject.put("type", matcher.group(6));
            jsonObject.put("course_type", matcher.group(8));
            jsonObject.put("teacher", matcher.group(11));
            jsonObject.put("time", matcher.group(12));
            //time 的格式如：星期一第9-10节1-16周，调用
            //getDay()和getLesson()和getWeek()来分别得到
            //得到是星期几
            String day = TimeUtil.getDay(matcher.group(12));
            System.out.println(day);
            jsonObject.put("day", day);
            //得到是第几节课
            String lesson = TimeUtil.getLesson(matcher.group(12));
            System.out.println(lesson);
            jsonObject.put("lesson", lesson);
            //得到是那几周
            jsonObject.put("rawWeek", TimeUtil.getWeekName(matcher.group(12)));
            TreeSet totalWeek = TimeUtil.getWeek(matcher.group(12));
            //创建一盒JSONArray
            JSONArray jsonArray = new JSONArray();
            // 把得到的周数的TreeSet变成jsonArray
            jsonArray.addAll(totalWeek);
            jsonObject.put("week", jsonArray);

            jsonObject.put("classroom", matcher.group(13));
            jsonObject.put("roll_call", matcher.group(15));
            System.out.println(jsonObject.toString());
            if (sb.length() > 1) {
                sb.append(" ,").append(jsonObject.toString());
            } else {
                sb.append(jsonObject.toString());
            }
        }
        return sb.toString();
    }

    // 得到剩下的课表，因为有些课一周有两节或者三节，用该方法得到剩下的课
    public static String getTimetableRest(String url) throws IOException {

        StringBuilder sb = new StringBuilder();
        //得到数据
        String data = Tool.getData(url);
        //String data=jspUse.Tool.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");
        //创建一个JSONObject
        //JSONObject jsonObject = new JSONObject();

        Pattern pattern = Pattern.compile("<tr><td >(.*?) </td>                    <td>(.*?) </td><td>(.*?)</td></tr>");
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {

           String teacher=matcher.group(1);
            // 得到上课老师的名字
           String time=matcher.group(2);
           String classroom=matcher.group(3);
           // getTimeTable 是通过老师的名字在扒一次数据得到该老师上课的全部信息，但是这里有bug，如果一个老师交两门课
           String json=getTimeTable(url,teacher);
            // 把得到的信息转换为jsonobject
           JSONObject jsonObject=JSONObject.fromObject(json);
           jsonObject.put("classroom",classroom);
           jsonObject.put("teacher",teacher);
            jsonObject.put("time", time);
            //time 的格式如：星期一第9-10节1-16周，调用
            //getDay()和getLesson()和getWeek()来分别得到
            //得到是星期几
            String day = TimeUtil.getDay(time);

            jsonObject.put("day", day);
            //得到是第几节课
            String lesson = TimeUtil.getLesson(time);

            jsonObject.put("lesson", lesson);
            //得到是那几周
            jsonObject.put("rawWeek", TimeUtil.getWeekName(time));
            TreeSet totalWeek = TimeUtil.getWeek(time);
            //创建一盒JSONArray
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(totalWeek);
            jsonObject.put("week", jsonArray);
            if (sb.length() > 1) {
                sb.append(" ,").append(jsonObject.toString());
            } else {
                sb.append(jsonObject.toString());
            }

        }
        return sb.toString();
    }
        // 剩下的课表通过这个方法得到全部信息
    public static String getTimeTable(String url,String teacher) throws IOException {

        StringBuilder sb = new StringBuilder();
        //得到数据
        String data = Tool.getData(url);
        //String data=jspUse.Tool.getData("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");

        //创建一个JSONObject
        JSONObject jsonObject = new JSONObject();

        Pattern pattern = Pattern.compile("<tr ><td r(.*?)>(.*?)</td>                    <td(.*?)>(.*?)</td>" +
                "                    <td(.*?)>(.*?)</td><td(.*?)>(.*?)</td>" +
                "<td(.*?)>(.*?)</td><td>(.*?)</td>                     <td>(.*?)</td>" +
                "<td>(.*?)</td>                    <td(.*?)>" +
                "<a href='(.*?)' target=_blank>名单</a></td>                    <td(.*?)></td>                    </tr>");
        Matcher matcher = pattern.matcher(data);

       while (matcher.find()) {

            if (teacher.equals(matcher.group(11))) {
                jsonObject.put("course_num", matcher.group(2));
                jsonObject.put("course", matcher.group(4));
                jsonObject.put("type", matcher.group(6));
                jsonObject.put("course_type", matcher.group(8));
                //  jsonObject.put("teacher",matcher.group(11));
                jsonObject.put("roll_call", matcher.group(15));

                if (sb.length() > 1) {
                    sb.append(" ,").append(jsonObject.toString());
                } else {
                    sb.append(jsonObject.toString());
                }

            }
        }
        return sb.toString();
    }
}
