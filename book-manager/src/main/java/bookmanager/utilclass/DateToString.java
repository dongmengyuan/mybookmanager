package bookmanager.utilclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dela on 7/22/17.
 */

//日期转换类
//说明: 数据库里面的datetime类型实质上时String类型, 所以我们需要在后台将所有的Date类型的时间按照datetime的格式转换成字符串
//这个类就是将时间戳转换成datetime类型的字符串
public class DateToString {
    public static String getStringDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date).toString();
    }
}