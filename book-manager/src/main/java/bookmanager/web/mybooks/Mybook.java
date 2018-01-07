package bookmanager.web.mybooks;

import bookmanager.dao.dbservice.*;
import bookmanager.model.po.BookCommentPO;
import bookmanager.model.po.BookInfoPO;
import bookmanager.model.po.BookLabelPO;
import bookmanager.model.po.ReturnInfoPO;
import bookmanager.model.vo.mybook.editBook;
import bookmanager.utilclass.BookUserMapUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
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
    private BookCommentService bookCommentService;
    private BorrowInfoService borrowInfoService;

    @Autowired
    public Mybook(UserService userService,BookInfoService bookInfoService,ReturnInfoService returnInfoService,BookCommentService bookCommentService,BorrowInfoService borrowInfoService) {
        this.userService = userService;
        this.bookInfoService = bookInfoService;
        this.returnInfoService = returnInfoService;
        this.bookCommentService = bookCommentService;
        this.borrowInfoService = borrowInfoService;
    }

    //我的书籍部分
    @RequestMapping(value = "/getBook")
    public String getBook(Model model,HttpSession session){
        //拿到当前登录者的id
        Object obj = session.getAttribute("uid");
        int uid = Integer.parseInt(obj.toString());
        //获取我所借阅的书和我所上传的书
        List<BookInfoPO> bookInfoPOS = bookInfoService.getBorrowBookByUser(uid);
        List<BookInfoPO> uploadBookInfo = bookInfoService.getUploadBookByUser(uid);
        //用工具类根据上传者的id找到上传者的名字
        Map<BookInfoPO, String> BorrowBookInfo = BookUserMapUtil.getOnePageBookInfo(bookInfoPOS, userService);
        Map<BookInfoPO, String> ReturnBookInfo = BookUserMapUtil.getOnePageBookInfo(uploadBookInfo, userService);

        //获取被借次数
        Map<BookInfoPO, Integer> borrowCount = BookUserMapUtil.getBorrowCount(bookInfoPOS, borrowInfoService);
        //获取我上传的书的被借次数
        Map<BookInfoPO, Integer> returnBorrowCount = BookUserMapUtil.getBorrowCount(uploadBookInfo, borrowInfoService);

        //把数据封装起来传给前端页面
        model.addAttribute("borrowCount",borrowCount);
        model.addAttribute("returnBorrowCount",returnBorrowCount);
        model.addAttribute("MyBorrowBook",BorrowBookInfo);
        model.addAttribute("MyReturnBook",ReturnBookInfo);
        return "mybooks";
    }

    //归还图书
    @RequestMapping(value = "/returnBook",method = RequestMethod.POST)
    public String returnBook(ReturnInfoPO returnInfoPO){
        returnInfoService.returnBookByUserAndPk_id(returnInfoPO);
        return "mybooks";
    }

    //下架图书
    @RequestMapping(value = "/deleteBook",method = RequestMethod.POST)
    public String deleteBook(BookInfoPO bookInfoPO){
        bookInfoService.deleteBook(bookInfoPO);
        return "mybooks";
    }

    //编辑图书信息
    @RequestMapping(value = "/editBook",method = RequestMethod.GET)
    public void editBook(Integer pk_id, Model model,HttpServletResponse response) throws IOException {
        //获取图书信息
        BookInfoPO book = bookInfoService.getBookByPk_id(pk_id);
        String parentBookClass = bookInfoService.getParentBookLabel(pk_id);
        String childBookClass = bookInfoService.getChildBookLabel(pk_id);

        //自己封装的vo
        editBook edit = new editBook();

        edit.setBookInfoPO(book);
        edit.setParentBookClass(parentBookClass);
        edit.setChildBookClass(childBookClass);

        //将返回的数据转换成json格式
        Gson gson = new Gson();
        System.out.println(gson);
        String json = gson.toJson(edit);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        //将json字符串返回到前端
        writer.append(json);

    }

    //更新图书信息
    @RequestMapping(value = "/updateBook",method = RequestMethod.POST)
    public String updateBook(BookInfoPO bookInfoPO) {
        System.out.println(bookInfoPO);
        bookInfoService.updateBook(bookInfoPO);
        return "mybooks";
    }




}
