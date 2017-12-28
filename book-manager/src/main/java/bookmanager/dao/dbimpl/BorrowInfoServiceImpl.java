package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BorrowInfoService;
import bookmanager.model.po.BorrowInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class BorrowInfoServiceImpl implements BorrowInfoService {
    private JdbcOperations jdbcOperations;

    @Autowired
    public BorrowInfoServiceImpl(JdbcOperations jdbc) {
        this.jdbcOperations = jdbc;
    }

    public void save(BorrowInfoPO borrowInfo) {
    }

}
