package bookmanager.utilclass;

import bookmanager.dao.dbservice.UserService;
import bookmanager.model.po.BookInfoPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dela on 1/2/18.
 */
public class BookUserMapUtil {

    public static Map<BookInfoPO, String> getOnePageBookInfo(List<BookInfoPO> bookInfoPOS, UserService userService) {
//        List<BookInfoPO> bookInfoPOS = bookInfoService.getBookByPage(page);
//        List<Integer> uidList = bookInfoService.getBookInfoUidByPage(page);

        List<String> userNames = new ArrayList<String>();
        Map<BookInfoPO, String> bookMap = new TreeMap<BookInfoPO, String>();


        for (BookInfoPO bookInfoPO : bookInfoPOS) {
            userNames.add(userService.getUsernameById(bookInfoPO.getUgkUid()));
        }

        for (int i = 0; i < bookInfoPOS.size(); i++) {
            bookMap.put(bookInfoPOS.get(i), userNames.get(i));
        }
        return bookMap;
    }
}
