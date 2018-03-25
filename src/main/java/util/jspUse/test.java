package util.jspUse;

import bean.jspBean.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class test {
    public static void main(String[] args) throws IOException{
        /*TreeSet set= jspUse.TimeUtil.getWeek("星期1第9-10节1-16周,17-20双周");
        System.out.println(set);*/
        /*
        String a=jspUse.Tool.getTimetableFirst("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");
        String b=jspUse.Tool.getTimetableRest("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");
        String c=a+b;*/

       /* String asd= TermUtil.getTermName("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh=2017210247#kbStuTabs-table");
        System.out.println(asd);*/
        String stu_num="2017210247";
        String term= TermUtil.getTermName("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table");
        System.out.println("得到学期名字："+term);
        StringBuilder json=new StringBuilder();
        String begin="{\"term\": \""+term +"\" ,"+
                "\"stuNum\": \""+stu_num+"\" ,"+
                "\"data\":[";
        String end="] }";
        json.append(begin)
                .append(Tool.getTimetableFirst("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
                .append(" ,")
                .append(Tool.getTimetableRest("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
                .append(end);
        System.out.println("已经扒下课表了："+json);

        JSONObject jsonObject= JSONObject.fromObject(json.toString());
        System.out.println(jsonObject);
        Gson gson=new Gson();
        // 得到学期

        String termName= jsonObject.getString("term");
        System.out.println(termName);
        JSONArray jsonArray=jsonObject.getJSONArray("data");
        String array=jsonArray.toString();
        List<Course> courseList=gson.fromJson(array,new TypeToken<List<Course>>(){}.getType());
        Collections.sort(courseList,new ComparatorUtil());
        for (Course course:courseList){
            System.out.println(course.getDay());
        }
    }
}
