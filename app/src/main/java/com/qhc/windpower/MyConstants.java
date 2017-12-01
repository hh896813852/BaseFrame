package com.qhc.windpower;

/**
 * Created by any on 17/6/14.
 */

public class MyConstants {
    public static final int PAGE_SIZE = 20;
    public static String HTTP_URL = "http://api.share.cn/";

    /**
     * 上传图片
     */
    public static final String UPLOAD_IMAGE_URL = HTTP_URL + "index/uploadImg";
    /**
     * 注册
     */
    public static final String USER_REGISTER = HTTP_URL + "user/reg";
    /**
     * 登录
     */
    public static final String USER_LOGIN = HTTP_URL + "user/login";
    /**
     * 发送验证码
     */
    public static final String USER_SEND_SMS = HTTP_URL + "user/sendSms";
    /**
     * 修改密码/忘记密码
     */
    public static final String USER_PASSWORD = HTTP_URL + "user/password";
    /**
     * 授权码更新
     */
    public static final String USER_CODE_UPDATE = HTTP_URL + "user/updateCode";
    /**
     * 个人资料更新
     */
    public static final String USER_INFO_UPDATE = HTTP_URL + "user/updateUserInfo";
    /**
     * 模板设置
     */
    public static final String USER_TEMPLATE = HTTP_URL + "user/template";
    /**
     * 客户查询
     */
    public static final String MEMBER_LIST = HTTP_URL + "member/memberList";
    /**
     * 客户管理
     */
    public static final String MEMBER_MANAGE = HTTP_URL + "member/memberManage";
    /**
     * 客户详情
     */
    public static final String MEMBER_DETAIL = HTTP_URL + "member/detail";
    /**
     * 添加更改客户
     */
    public static final String MEMBER_UPDATE = HTTP_URL + "member/addMember";
    /**
     * 删除客户
     */
    public static final String MEMBER_DELETE = HTTP_URL + "member/delMember";
    /**
     * 区域查询
     */
    public static final String MEMBER_AREA = HTTP_URL + "member/area";
    /**
     * 分享生成
     */
    public static final String MEMBER_SHARE = HTTP_URL + "member/shareCreate";
    /**
     * 系统消息
     */
    public static final String MESSAGE_LIST = HTTP_URL + "message/index";
    /**
     * 意见反馈
     */
    public static final String ADVICE_FEED = HTTP_URL + "index/opinion";
    /**
     * 获取最新版本号
     */
    public static final String VERSION_CODE = HTTP_URL + "index/versions";
}
