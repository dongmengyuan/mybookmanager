package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BorrowInfoService;
import bookmanager.dao.rowmapper.JdbcRowMapper;
import bookmanager.model.po.BorrowInfoPO;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class BorrowInfoServiceImpl implements BorrowInfoService {
    private JdbcOperations jdbcOperations;

    private final static String BORROWBOOK_BY_USERANDPK_ID = "INSERT INTO borrow_info (book_info_pk_id, cs_user_uid, borrow_date) VALUES (?,?,?)";
    private final static String CHECK_BOOK_IS_BORROW = "SELECT * FROM borrow_info WHERE book_info_pk_id = ? AND cs_user_uid = ? LIMIT 1";
    private final static String GET_BORROW_COUNT_BY_PK_ID = "SELECT COUNT(*) FROM borrow_info WHERE book_info_pk_id = ?";
    @Autowired
    public BorrowInfoServiceImpl(JdbcOperations jdbc) {
        this.jdbcOperations = jdbc;
    }

    public void save(BorrowInfoPO borrowInfo) {
        jdbcOperations.update(BORROWBOOK_BY_USERANDPK_ID,borrowInfo.getBookInfoPkId(),borrowInfo.getCsUserId(),borrowInfo.getBorrowDate());

    }

    public BorrowInfoPO checkBookisBorrow(BorrowInfoPO borrowInfoPO) {
        List<BorrowInfoPO> query = jdbcOperations.query(CHECK_BOOK_IS_BORROW, JdbcRowMapper.newInstance(BorrowInfoPO.class), borrowInfoPO.getBookInfoPkId(), borrowInfoPO.getCsUserId());
        if(query.size()>0){
            return query.get(0);
        }else{
            return null;
        }

    }

    public int getBorrowCount(Integer bookInfoPkId) {
        return jdbcOperations.queryForObject(GET_BORROW_COUNT_BY_PK_ID, Integer.class,bookInfoPkId);

    }

}
