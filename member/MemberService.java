package com.xuebei.crm.member;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
public interface MemberService {
    List<Member> searchMemberList(String companyId);

    List<Member> searchSiblingsList(String memberId);

    List<Member> searchSubMemberList(String userId);

    List<String> getSubMemberList(String userId);

    /**
    *   获取所有成员
    */
    List<Member> getAllMember();

    /**
    *   获取所有下属
     * @param containSelf:是否包含自身 true 包含
    */
    List<Member> searchSubMemberList(String userId,boolean containSelf);
}
