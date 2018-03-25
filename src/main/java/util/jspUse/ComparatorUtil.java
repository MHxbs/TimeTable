package util.jspUse;



import bean.jspBean.Course;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public  class ComparatorUtil implements Comparator {


        public int compare(Object object1 ,Object object2){
            Course course1= (Course) object1;
            Course course2= (Course) object2;
            String day1=course1.getDay();
            String lesson1=course1.getLesson();
            String day2=course2.getDay();
            String lesson2=course2.getLesson();
            Pattern pattern1=Pattern.compile("星期(\\d)");
            Matcher matcher1_day=pattern1.matcher(day1);
            Matcher matcher2_day=pattern1.matcher(day2);
            Pattern pattern2=Pattern.compile("第(\\d)-(\\d)节");
            Matcher matcher1_lesson=pattern2.matcher(lesson1);
            Matcher matcher2_lesson=pattern2.matcher(lesson2);
            if (matcher1_day.find()&&matcher2_day.find()){
               //
                int Fday=Integer.parseInt(matcher1_day.group(1));
                int Sday=Integer.parseInt(matcher2_day.group(1));
                if (Fday>Sday){
                    return 1;
                }else if (Fday<Sday){
                    return -1;
                }else if (Fday==Sday){
                    if (matcher1_lesson.find()&&matcher2_lesson.find()){
                        int Flesson=Integer.parseInt(matcher1_lesson.group(1));
                        int Slesson=Integer.parseInt(matcher2_lesson.group(1));
                        if (Flesson>Slesson){
                            return -1;
                        }else if (Flesson<Slesson){
                            return 1;
                        }else {
                            return 0;
                        }
                    }
                }
            }
            return -2;
        }

}

