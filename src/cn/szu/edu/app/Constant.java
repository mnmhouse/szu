package cn.szu.edu.app;

public interface Constant {

    public static final int LOAD_SUCCESS = 0;
    
    public static final String USER_INFO = "user_info";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_PASS = "user_pass";

    public static final String HTTP_SERVER = "http://qingqula.com/";//服务器地址
//    public static final String HTTP_SERVER = "http://beyond.besaba.com/";//服务器地址

    public static final String HTTP_NAV = "/my-api/getNavList.php?";//服务器地址
    public static final String HTTP_NAV_IMG = "/my-api/getDaoHangPostList.php?";//广告
    public static final String HTTP_ARTICLES = "/my-api/controller/getPostList.php?";//文章列表
    public static final String SEND_COMMENT = "/my-api/addComment.php?";//发表评论
    public static final String ADD_COMMENTLIKE = "/my-api/addCommentLike.php?";//评论点赞
    public static final String HTTP_PICS = "/my-api/getwelfarelist.php?";//图片列表
    public static final String HTTP_COMMENTS = "/my-api/getCommentList.php?";//评论列表
    public static final String HTTP_VERSION = "/my-api/getVersion.php?";//APP最新版本

    public static final String HTTP_TABS = "/xinwen/index.xml"; //栏目
    public static final String HTTP_ABOUT = ""; //关于
    public static final String HTTP_REGISTER = "/my-api/registerUser.php?";//注册
    public static final String HTTP_LOGIN = "/my-api/userLogin.php?";//登录
    public static final String GET_COMMENT = "/my-api/getDongTanList.php?";//获取评论
    public static final String ADD_COMMENT_LIKE = "/my-api/addCommentLike.php?";//点赞
    
}

