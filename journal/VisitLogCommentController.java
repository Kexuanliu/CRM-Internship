package com.xuebei.crm.journal;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Xinyu Liu
 * 拜访评论Controller
 */
@Controller
@RequestMapping("/journal/visitlogcomment")
public class VisitLogCommentController {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/addVisitComment")
    public GsonView addVisitComment(@RequestParam("visitLogId") Integer visitLogId, @RequestParam("comment") String content, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            GsonView faileView = new GsonView();
            faileView.addStaticAttribute("successFlg", false);
            faileView.addStaticAttribute("errMsg", "会话已过期，请重新登录");
            return faileView;
        }

        VisitLogComment visitLogComment= new VisitLogComment();
        visitLogComment.setContent(content);
        visitLogComment.setVisitLogId(visitLogId);
        journalMapper.addVisitComment( visitLogComment,userId);
        visitLogComment.setCreator(userMapper.getCompanyUserById(userId));
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("comment", visitLogComment);
        return gsonView;
    }


}
