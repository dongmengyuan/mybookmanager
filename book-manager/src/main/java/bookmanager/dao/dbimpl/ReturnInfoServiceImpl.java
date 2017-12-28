package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.ReturnInfoService;
import bookmanager.model.po.ReturnInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

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

    public void save(ReturnInfoPO returnInfo) {
    }
}
