package util.jspUse;



import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//得到这学期的字符串
public class TermUtil {
    public static String getTermName(String Url) throws IOException {
        String result=null;
        String data= Tool.getData(Url);
        Pattern pattern= Pattern.compile("<li>〉〉(.*?)学生课表>>(.*?)</li>");
        Matcher matcher=pattern.matcher(data);
        System.out.println("!");
        if (matcher.find()){
            result=matcher.group(1);
        }
        return result;

    }
}
