package bookmanager.web.mybooks;

import bookmanager.dao.dbservice.BookInfoService;
import bookmanager.dao.dbservice.ReturnInfoService;
import bookmanager.dao.dbservice.UserService;
import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.ReturnInfoPO;
import bookmanager.utilclass.BookUserMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by dongmengyuan on 18-1-3.
 */


@Controller
@RequestMapping(value = "/mybook")
public class Mybook {
    private UserService userService;
    private BookInfoService bookInfoService;
    private ReturnInfoService returnInfoService;

    @Autowired
    public Mybook(UserService userService,BookInfoService bookInfoService,ReturnInfoService returnInfoService) {
        this.userService = userService;
        this.bookInfoService = bookInfoService;
        this.returnInfoService = returnInfoService;
    }

    @RequestMapping(value = "/getBook",method = RequestMethod.GET)
    public String getBook(Model model,HttpSession session){
        Object obj = session.getAttribute("uid");
        int uid = Integer.parseInt(obj.toString());
        //System.out.println(uid.toString());
        List<BookInfoPO> bookInfoPOS = bookInfoService.getBorrowBookByUser(uid);
        List<BookInfoPO> returnBookInfo = bookInfoService.getReturnBookByUser(uid);
        Map<BookInfoPO, String> BorrowBookInfo = BookUserMapUtil.getOnePageBookInfo(bookInfoPOS, userService);
        Map<BookInfoPO, String> ReturnBookInfo = BookUserMapUtil.getOnePageBookInfo(returnBookInfo, userService);
        //model.addAttribute("MyBorrowBook",bookInfoPOS);
        model.addAttribute("MyBorrowBook",BorrowBookInfo);
        model.addAttribute("MyReturnBook",ReturnBookInfo);
        return "mybooks";
    }

    @RequestMapping(value = "/returnBook",method = RequestMethod.POST)
    public String returnBook(ReturnInfoPO returnInfoPO){
        //System.out.println(uid+"...."+pk_id);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//        returnInfoPO.setReturnDate(simpleDateFormat.format(new Date()));
        returnInfoService.returnBookByUserAndPk_id(returnInfoPO);
        return "mybooks";
    }

    @RequestMapping(value = "/deleteBook",method = RequestMethod.POST)
    public String deleteBook(BookInfoPO bookInfoPO){
        bookInfoService.deleteBook(bookInfoPO);
        return "mybooks";
    }






}
