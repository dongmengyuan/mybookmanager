package bookmanager.web.main;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.dbservice.UserService;
import bookmanager.model.po.BookInfoPO;
import bookmanager.utilclass.BookUserMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;


/**
 * Created by dela on 12/25/17.
 */

@Controller
@RequestMapping(value = "/label")
public class GetLabelBook {
    private BookInfoService bookInfoService;
    private UserService userService;

    @Autowired
    public GetLabelBook(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getBookByLabelId(@PathVariable int id, Model model) {
        System.out.println(id);
        List<BookInfoPO> bookInfoPOS = bookInfoService.getBookInfoByBookLabelParentId(id);
        System.out.println(bookInfoPOS);

        if (bookInfoPOS == null) {
            System.out.println("bookInfoPOS为null");
            return "index";
        }

        Map<BookInfoPO, String> bookInfoPOStringMap = BookUserMapUtil.getOnePageBookInfo(bookInfoPOS, userService);

        model.addAttribute("books", bookInfoPOStringMap);

        return "index";
    }

}
