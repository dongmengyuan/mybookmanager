package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.rowmapper.JdbcRowMapper;
import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.PagePO;
import bookmanager.model.po.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class BookInfoServiceImpl implements BookInfoService {
    private JdbcOperations jdbcOperations;

    private final static String GET_BOOKINFO_BY_BOOKLABEL_PARENT_ID =
                    "SELECT * FROM book_info where pk_id IN (" +
                            "SELECT book_info_pk_id FROM book_relation_label WHERE book_label_pk_id IN (" +
                            "SELECT pk_id FROM book_label WHERE parent_id = ?))";

//    private final static String GET_TEN_BOOKINFO = "SELECT * FROM book_info LIMIT 10";
//    private final static String GET_TEN_BOOKINFO_UID = "SELECT ugk_uid FROM book_info LIMIT 10";
    private final static String GET_BOOK_COUNT = "SELECT COUNT(*) AS COUNT FROM book_info";
    private final static String GET_ONE_PAGE_BOOKINFO = "SELECT * FROM book_info LIMIT ? , ?";
    private final static String GET_ONE_PAGE_BOOKINFO_UID = "SELECT pk_id FROM book_info LIMIT ?, ?";


    private final static String GET_BORROWBOOK_BY_USER = "SELECT * FROM book_info WHERE pk_id IN (SELECT book_info_pk_id FROM borrow_info WHERE cs_user_uid = ?)";
    private final static String GET_UPLOAD_BOOK_BY_USER = "SELECT * FROM book_info WHERE ugk_uid = ?";
    private final static String DELETE_BOOK_BY_BOOKINFOPO = "DELETE FROM book_info WHERE pk_id = ? AND ugk_uid = ?";
    private final static String GET_PARENT_BOOK_CLASS_BY_PK_ID = "SELECT name FROM book_label WHERE pk_id IN (SELECT parent_id FROM book_label WHERE pk_id IN (SELECT label_tree_pk_id FROM book_relation_label WHERE book_info_pk_id = ?))";

    private final static String GET_CHILD_BOOK_CLASS_BY_PK_ID = "SELECT name FROM book_label WHERE pk_id IN (SELECT label_tree_pk_id FROM book_relation_label WHERE book_info_pk_id = ?)";

    private final static String GET_BOOK_BY_PKID = "SELECT * FROM book_info WHERE pk_id = ?";

    private final static String UPDATE_BOOK_BY_ID = "UPDATE book_info SET ugk_name = ?,author = ?,amount = ?,describ = ? WHERE pk_id = ?";

    @Autowired
    public BookInfoServiceImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    // 通过一级分类的ID查询该一级分类下的所有的书
    public List<BookInfoPO> getBookInfoByBookLabelParentId(int bookParentId) {
        return jdbcOperations.query(GET_BOOKINFO_BY_BOOKLABEL_PARENT_ID,
                JdbcRowMapper.newInstance(BookInfoPO.class), bookParentId);
    }

//    // 得到最新的10条图书记录
//    public List<BookInfoPO> getTenBookInfo() {
//        return jdbcOperations.query(GET_TEN_BOOKINFO,
//                JdbcRowMapper.newInstance(BookInfoPO.class));
//    }
//
//
//    public List<Integer> getTenBookInfoUid() {
//        return jdbcOperations.queryForList(GET_TEN_BOOKINFO_UID, Integer.class);
//    }

    public Integer getBookCount() {
        return jdbcOperations.queryForObject(GET_BOOK_COUNT, Integer.class);
    }

    public List<BookInfoPO> getBookByPage(PagePO page) {
        return jdbcOperations.query(GET_ONE_PAGE_BOOKINFO,
                JdbcRowMapper.newInstance(BookInfoPO.class), page.getBeginIndex(), page.getEveryPage());
    }

    public List<Integer> getBookInfoUidByPage(PagePO pagePO) {
        return jdbcOperations.queryForList(GET_ONE_PAGE_BOOKINFO_UID,
                Integer.class, pagePO.getBeginIndex(), pagePO.getEveryPage());
    }






    public List<BookInfoPO> getBorrowBookByUser(int uid) {
        return jdbcOperations.query(GET_BORROWBOOK_BY_USER,JdbcRowMapper.newInstance(BookInfoPO.class),uid);
    }

    public List<BookInfoPO> getUploadBookByUser(int uid) {
        return jdbcOperations.query(GET_UPLOAD_BOOK_BY_USER,JdbcRowMapper.newInstance(BookInfoPO.class),uid);
    }

    public void deleteBook(BookInfoPO bookInfoPO) {
        jdbcOperations.update(DELETE_BOOK_BY_BOOKINFOPO,bookInfoPO.getPkId(),bookInfoPO.getUgkUid());
    }

    public BookInfoPO getBookByPk_id(Integer bookInfoPkId) {
        List<BookInfoPO> query = jdbcOperations.query(GET_BOOK_BY_PKID, JdbcRowMapper.newInstance(BookInfoPO.class), bookInfoPkId);
        return query.get(0);
    }




    public String getParentBookLabel(Integer pk_id) {
        return jdbcOperations.queryForObject(GET_PARENT_BOOK_CLASS_BY_PK_ID,String.class,pk_id);
    }

    public String getChildBookLabel(Integer pk_id) {
        return jdbcOperations.queryForObject(GET_CHILD_BOOK_CLASS_BY_PK_ID,String.class,pk_id);
    }

    public void updateBook(BookInfoPO bookInfoPO) {
        jdbcOperations.update(UPDATE_BOOK_BY_ID,bookInfoPO.getUgkName(),bookInfoPO.getAuthor(),bookInfoPO.getAmount(),bookInfoPO.getDescrib(),bookInfoPO.getPkId());
    }


}
