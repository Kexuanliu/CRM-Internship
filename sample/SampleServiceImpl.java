package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sampleService")
public class SampleServiceImpl implements SampleService {
    @Autowired
    private SampleMapper sampleMapper;

    @Override
    public List<User> searchUser(String keyword) {
        return sampleMapper.searchUser(keyword);
    }

    @Override
    public void insertUser(User user) { sampleMapper.insertUser(user);
    }

    @Override
    public void editUser(User user) {
        sampleMapper.editUser(user);
    }

    @Override
    public String getUserNameById(String userId){
       return sampleMapper.getUserNameById(userId);
    }

    /**
     * 通过名字模糊搜索ID列表
     *
     * @param keyWord:人名
     * @return
     * @throws
     * @author zxl
     * @date 11:38 2019/3/22
     * @since
     */
    @Override
    public List<String> getUserIdsByUserName(String keyWord) {
        if (StringUtils.isBlank(keyWord)) {
            return null;
        }
        List<String> res = sampleMapper.getUserIdsByUserName(keyWord);
        if (res.size() == 0) {
            res = null;
        }
        return res;
    }
}