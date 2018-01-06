package bookmanager.web.showbook;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.dbservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dongmengyuan on 18-1-3.
 */

@Controller
@RequestMapping(value = "/showbook")
public class showbook {
    private UserService userService;
    private BookInfoService bookInfoService;

    @Autowired
    public showbook() {

    }
}
