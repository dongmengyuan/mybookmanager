package bookmanager.dao.dbservice;

import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.PagePO;
import bookmanager.model.po.UserPO;

import java.util.List;
import java.util.Map;

/**
 * Created by dela on 11/23/17.
 */
public interface BookInfoService {
    // 得到某一级分类下的所有书
    List<BookInfoPO> getBookInfoByBookLabelParentId(int bookParentId);

//    // 得到最新的10条图书记录
//    List<BookInfoPO> getTenBookInfo();
//
//    // 得到最新的10条图书记录的uid
//    List<Integer> getTenBookInfoUid();

    // 得到书籍总记录数
    Integer getBookCount();

    // 得到一页的图书信息
    List<BookInfoPO> getBookByPage(PagePO page);

    // 得到一页的图书信息的uid
    List<Integer> getBookInfoUidByPage(PagePO pagePO);


    List<BookInfoPO> getBorrowBookByUser(int uid);

    List<BookInfoPO> getReturnBookByUser(int uid);


    void deleteBook(BookInfoPO bookInfoPO);
}
