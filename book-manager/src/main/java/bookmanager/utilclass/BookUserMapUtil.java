package bookmanager.utilclass;

import bookmanager.dao.dbservice.BorrowInfoService;
import bookmanager.dao.dbservice.UserService;
import bookmanager.model.po.BookCommentPO;
import bookmanager.model.po.BookInfoPO;

import java.util.*;

/**
 * Created by dela on 1/2/18.
 */
public class BookUserMapUtil {

    //把多个图书的上传者id对应为用户名
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

    //将一个书的上传者id对应为名字
    public static Map<BookInfoPO, String> getOnePageBookInfo(BookInfoPO bookInfoPOS, UserService userService) {


        String userNames;
        Map<BookInfoPO, String> bookMap = new TreeMap<BookInfoPO, String>();



        userNames=userService.getUsernameById(bookInfoPOS.getUgkUid());


        bookMap.put(bookInfoPOS, userNames);

        return bookMap;
    }


    //将多个评论的评论者id转换成对应的评论者名字
    public static Map<BookCommentPO, String> getUsername(List<BookCommentPO> bookCommentPOS, UserService userService) {

        List<String> userNames = new ArrayList<String>();
        Map<BookCommentPO, String> bookMap = new HashMap<BookCommentPO, String>();


        for (BookCommentPO bookCommentPO : bookCommentPOS) {
            userNames.add(userService.getUsernameById(bookCommentPO.getCsUserId()));
        }

        for (int i = 0; i < bookCommentPOS.size(); i++) {
            bookMap.put(bookCommentPOS.get(i), userNames.get(i));
        }
        return bookMap;
    }

    //获取多个书的被借次数
    public static Map<BookInfoPO, Integer> getBorrowCount(List<BookInfoPO> bookInfoPOS, BorrowInfoService borrowInfoService) {

        List<Integer> BorrowCount = new ArrayList<Integer>();
        Map<BookInfoPO, Integer> CountMap = new HashMap<BookInfoPO, Integer>();


        for (BookInfoPO bookInfoPO : bookInfoPOS) {
            BorrowCount.add(borrowInfoService.getBorrowCount(bookInfoPO.getPkId()));
        }

        for (int i = 0; i < bookInfoPOS.size(); i++) {
            CountMap.put(bookInfoPOS.get(i), BorrowCount.get(i));
        }
        return CountMap;
    }
}
