package web;



import util.jspUse.TermUtil;
import util.jspUse.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TimeTableServlet",value = "/TimeTableServlet")
public class TimeTableServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        //得到客户端上传的学号
        String stu_num=request.getParameter("stu_num");
        System.out.println("输入的学号："+stu_num);
        // 根据学号去教务在线上得到这学期的学期名

        String term= TermUtil.getTermName("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table");

        StringBuilder json=new StringBuilder();
        //json字符串的开始部分
        String begin="{ \"term\": \""+term +"\" ,"+
                    "\"stuNum\": \""+stu_num+"\" ,"+
                "\"data\":[";
        String end="] }";
        // 因为教务在线上有的课一种有多节，
        // jspUse.Tool.getTimetableFirst先得到大部分信息
        //jspUse.Tool.getTimetableRest再得到两节三节课的其它节课的信息
       json.append(begin)
               .append(Tool.getTimetableFirst("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
                .append(" ,")
                .append(Tool.getTimetableRest("http://jwzx.cqupt.edu.cn/jwzxtmp/kebiao/kb_stu.php?xh="+stu_num+"#kbStuTabs-table"))
               .append(end);
        System.out.println(json);



        out.print(json);

        out.flush();
        out.close();

    }
}
