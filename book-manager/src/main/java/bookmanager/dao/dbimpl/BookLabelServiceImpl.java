package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BookLabelService;
import bookmanager.dao.rowmapper.JdbcRowMapper;
import bookmanager.model.po.BookLabelPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class BookLabelServiceImpl implements BookLabelService {
    private JdbcOperations jdbcOperations;
    public static volatile List<BookLabelPO> bookLabeList = null;

    private static final String GET_BOOKLABEL_BY_ID = "SELECT * FROM book_label where parent_id = ?";

    @Autowired
    public BookLabelServiceImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public List<BookLabelPO> getBookLabelById(int id) {
        bookLabeList = jdbcOperations.query(GET_BOOKLABEL_BY_ID, JdbcRowMapper.newInstance(BookLabelPO.class), id);
        return bookLabeList;
    }

    public List<BookLabelPO> getBaseLabel() {
        if (bookLabeList == null) {
            bookLabeList = getBookLabelById(0);
        }

        return bookLabeList;
    }
}
