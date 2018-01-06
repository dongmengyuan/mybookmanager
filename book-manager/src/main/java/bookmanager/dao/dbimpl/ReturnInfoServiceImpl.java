package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.ReturnInfoService;
import bookmanager.model.po.ReturnInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class ReturnInfoServiceImpl implements ReturnInfoService {
    private JdbcOperations jdbcOperations;

    @Autowired
    public ReturnInfoServiceImpl(JdbcOperations jdbc) {
        this.jdbcOperations = jdbc;
    }

    private final static String DELETE_BOOK_BY_USERANDPK_ID = "DELETE FROM borrow_info WHERE cs_user_uid = ? AND book_info_pk_id = ?";
    private final static String RETURN_BOOK_BY_USERANDPK_ID = "INSERT INTO return_info(book_info_pk_id,cs_user_uid,return_date) VALUES(?,?,?)";

    public void returnBookByUserAndPk_id(ReturnInfoPO returnInfoPO) {
        jdbcOperations.update(DELETE_BOOK_BY_USERANDPK_ID,returnInfoPO.getCsUserId(),returnInfoPO.getBookInfoPkId());
        jdbcOperations.update(RETURN_BOOK_BY_USERANDPK_ID,returnInfoPO.getBookInfoPkId(),returnInfoPO.getCsUserId(),new Date());
    }

}
