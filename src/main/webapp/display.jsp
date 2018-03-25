<%@ page import="util.jspUse.TermUtil" %>
<%@ page import="util.jspUse.Tool" %>
<%@ page import="bean.jspBean.Course" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.google.gson.reflect.TypeToken" %>
<%@ page import="util.jspUse.ComparatorUtil" %>

<%@ page  contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<html>
<body>


<%
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=UTF-8");

    String stu_num=request.getParameter("stu_num");
    System.out.println("输入的学号："+stu_num);

    String term= TermUtil.getTermName("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table");
    System.out.println("得到学期名字："+term);
    StringBuilder json=new StringBuilder();
    String begin="{ \"term\": \""+term +"\" ,"+
            "\"stuNum\": \""+stu_num+"\" ,"+
            "\"data\":[";
    String end="] }";
    json.append(begin)
            .append(Tool.getTimetableFirst("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
            .append(" ,")
            .append(Tool.getTimetableRest("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
            .append(end);
    System.out.println("已经扒下课表了："+json);
    JSONObject jsonObject=JSONObject.fromObject(json.toString());
    Gson gson=new Gson();
    // 得到学期
    String termName=jsonObject.getString("term");
    JSONArray jsonArray=jsonObject.getJSONArray("data");
    String array=jsonArray.toString();
    List<Course> courseList=gson.fromJson(array,new TypeToken<List<Course>>(){}.getType());
    Collections.sort(courseList,new ComparatorUtil());
%>
<h2>TimeTable</h2>
<h2><% out.print(termName);
        out.print("  学号："+stu_num);%></h2>
<table class="table table-striped table-hover table-condensed">
    <thead>
    <tr>
        <th>教学班</th>
        <th>课程号-课程名</th>
        <th>类别</th>
        <th>教学班分类</th>
        <th>教师</th>
        <th>上课时间</th>
        <th>地点</th>
    </tr>
    </thead>
    <tfboody>
        <% for (Course course:courseList) { %>

        <tr>
            <td><% out.print(course.getCourse_num()); %></td>
            <td><% out.print(course.getCourse()); %></td>
            <td><% out.print(course.getType()); %></td>
            <td><% out.print(course.getCourse_type()); %></td>
            <td><% out.print(course.getTeacher()); %></td>
            <td><% out.print(course.getTime()); %></td>
            <td><% out.print(course.getClassroom()); %></td>
        </tr>
        <% } %>
    </tfboody>
</table>
</body>
</html>
