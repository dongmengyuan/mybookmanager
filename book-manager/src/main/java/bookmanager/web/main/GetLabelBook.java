package bookmanager.web.main;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.model.po.BookInfoPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


/**
 * Created by dela on 12/25/17.
 */

@Controller
@RequestMapping(value = "/label")
public class GetLabelBook {
    private BookInfoService bookInfoService;

    @Autowired
    public GetLabelBook(BookInfoService bookInfoService) {
        this.bookInfoService = bookInfoService;
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public String getBookByLabelId(@PathVariable int id, Model model) {
        System.out.println(id);
        List<BookInfoPO> bookInfoPOS = bookInfoService.getBookInfoByBookLabelParentId(id);
        model.addAttribute("books", bookInfoPOS);

        return "redirect:index.jsp";
    }

}
