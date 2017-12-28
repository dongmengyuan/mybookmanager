package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.rowmapper.JdbcRowMapper;
import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.PagePO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
