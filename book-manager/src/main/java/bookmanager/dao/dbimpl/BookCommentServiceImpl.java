package bookmanager.dao.dbimpl;

import bookmanager.dao.dbservice.BookCommentService;
import bookmanager.model.po.BookCommentPO;
import org.springframework.stereotype.Repository;

import java.util.TreeSet;

/**
 * Created by dela on 11/23/17.
 */

@Repository
public class BookCommentServiceImpl implements BookCommentService {
    // 该数据库操作方法还没有实现
    public void save(BookCommentPO bookComment) {

    }

    TreeSet<String> treeSet = new TreeSet<String>();

}
