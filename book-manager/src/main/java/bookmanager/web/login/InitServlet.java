package bookmanager.web.login;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.dbservice.BookLabelService;
import bookmanager.dao.dbservice.UserService;
import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.BookLabelPO;
import bookmanager.model.po.PagePO;
import bookmanager.utilclass.BookUserMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by dela on 12/26/17.
 */

@Controller
public class InitServlet {
    private UserService userService;
    private BookInfoService bookInfoService;
    private BookLabelService bookLabelService;

    @Autowired
    public InitServlet(UserService userService,
            BookInfoService bookInfoService, BookLabelService bookLabelService) {
        this.bookInfoService = bookInfoService;
        this.userService = userService;
        this.bookLabelService = bookLabelService;
    }

    @RequestMapping(value = "/bookmanager", method = RequestMethod.GET)
    public String init(HttpServletRequest httpServletRequest, HttpSession httpSession) {
        PagePO page = new PagePO(5, 1, 0, false, false);
        int bookCount = bookInfoService.getBookCount();
        page.setTotalCount(bookCount);
        page.setTotalPage((bookCount % 5 == 0) ? bookCount / 5 : bookCount / 5 + 1);

        System.out.println("init中:" + page.getTotalPage());


        List<BookLabelPO> bookLabelPOS = bookLabelService.getBookLabelById(0);
        List<BookInfoPO> bookInfoPOS = bookInfoService.getBookByPage(page);
//        List<Integer> uidList = bookInfoService.getBookInfoUidByPage(page);

        Map<BookInfoPO, String> bookMap = BookUserMapUtil.getOnePageBookInfo(bookInfoPOS, userService);



//        List<String> userNames = new ArrayList<String>();
//        Map<BookInfoPO, String> bookMap = new TreeMap<BookInfoPO, String>();
//
//
//        for (BookInfoPO bookInfoPO : bookInfoPOS) {
//            userNames.add(userService.getUsernameById(bookInfoPO.getUgkUid()));
//        }
//
//        for (int i = 0; i < bookInfoPOS.size(); i++) {
//            bookMap.put(bookInfoPOS.get(i), userNames.get(i));
//        }
//
//        httpServletRequest.setAttribute("labels", bookLabelPOS);

        httpSession.setAttribute("labels", bookLabelPOS);
        httpServletRequest.setAttribute("books", bookMap);
        httpServletRequest.setAttribute("pageInfo", page);

        return "index";
    }
}
