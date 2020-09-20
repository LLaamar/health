import com.itheima.service.impl.utils.DateUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author LLaamar
 * @date 2020/9/19 15:09
 */
public class TestTime {

    @Test
    public void testTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        Date time = calendar.getTime();
        try {
            System.out.println(DateUtils.parseDate2String(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
