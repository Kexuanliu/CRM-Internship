package com.xuebei.crm.journal;

import com.xuebei.crm.user.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Rong Weicheng
 */
@RunWith(MockitoJUnitRunner.class)
public class JournalCommentControllerTest {

    @InjectMocks
    private JournalCommentController commentController;

    private MockMvc mockMvc;

    @Mock
    private JournalMapper journalMapper;

    @Mock
    private UserMapper userMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    public void testAddComment() throws Exception {
        String url = "/journal/comment/addComment";

        mockMvc.perform(post(url)
                .param("comment", "comment")
                .param("journalId", "2")
                .sessionAttr("userId", "userId"))
                .andExpect(jsonPath("successFlg").value(true))
                .andExpect(jsonPath("comment").exists());
    }
}