package com.xuebei.crm.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

/**
 * @author Rong Weicheng
 */
@Mapper
public interface UserMapper {
    User getCompanyUserById(@Param("userId") String userId);

    List<User> getRealNameById(@Param("userIds") Set<String> userIds);
}
