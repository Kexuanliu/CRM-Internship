package com.xuebei.crm.journal;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rong Weicheng
 */
@Controller
@RequestMapping("/journal/comment")
public class JournalCommentController {

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/addComment")
    public GsonView addComment(@RequestParam("journalId") Integer journalId, @RequestParam("comment") String comment, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            GsonView faileView = new GsonView();
            faileView.addStaticAttribute("successFlg", false);
            faileView.addStaticAttribute("errMsg", "会话已过期，请重新登录");
            return faileView;
        }

        JournalComment journalComment = new JournalComment();
        journalComment.setComment(comment);
        journalComment.setJournalId(journalId);
        journalMapper.addComment(journalComment, userId);
        journalComment.setCreator(userMapper.getCompanyUserById(userId));
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("comment", journalComment);
        return gsonView;
    }
}
