package cn.szu.edu.app.api.remote;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.kymjs.kjframe.utils.KJLoger;

import android.net.Uri;
import android.text.TextUtils;
import cn.szu.edu.app.AppContext;
import cn.szu.edu.app.AppException;
import cn.szu.edu.app.api.ApiHttpClient;
import cn.szu.edu.app.bean.EventApplyData;
import cn.szu.edu.app.bean.NewsList;
import cn.szu.edu.app.bean.Report;
import cn.szu.edu.app.bean.Tweet;
import cn.szu.edu.app.team.bean.Team;
import cn.szu.edu.app.util.StringUtils;
import cn.szu.edu.app.util.TLog;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.networkbench.com.google.gson.Gson;

public class OSChinaApi {

    /**
     * 登陆
     * 
     * @param username
     * @param password
     * @param handler
     */
    public static void login(String username, String password,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("pwd", password);
        params.put("keep_login", 1);
        String loginurl = "action/api/login_validate";
        ApiHttpClient.post(loginurl, params, handler);
    }

    public static void getNoticeTopicList(String sign, String username,
            AsyncHttpResponseHandler handler) {
		Map<String,String> params = new HashMap<String,String>();
        params.put("sign", sign);
        params.put("username", username);
        Gson gson = new Gson();
        String parameters =  Uri.encode(gson.toJson(params));
        ApiHttpClient.client.get("http://192.168.1.172/szuapp/interface/getNoticeList.php?key="+parameters,null, handler);
    }

    public static void getPostTopicList(int catalog, int page,
            AsyncHttpResponseHandler handler) {
    	Map<String,Integer> params = new HashMap<String,Integer>();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("page", AppContext.PAGE_SIZE);
        Gson gson = new Gson();
        String parameters =  Uri.encode(gson.toJson(params));
        ApiHttpClient.client.get("http://192.168.1.172/szuapp/interface/getPostList.php?key="+parameters,null, handler);
    }
    
    /**
     * 获取新闻列表
     * 
     * @param catalog
     *            类别 （1，2，3）
     * @param page
     *            第几页
     * @param handler
     */
    public static void getNewsList(int catalog, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        if (catalog == NewsList.CATALOG_WEEK) {
            params.put("show", "week");
        } else if (catalog == NewsList.CATALOG_MONTH) {
            params.put("show", "month");
        }
        ApiHttpClient.get("action/api/news_list", params, handler);
    }

    public static void getBlogList(String type, int pageIndex,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("type", type);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blog_list", params, handler);
    }

    public static void getPostList(int catalog, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getPostListByTag(String tag, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", tag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/post_list", params, handler);
    }

    public static void getTweetList(int uid, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_list", params, handler);
    }

    public static void getTweetTopicList(int page, String topic,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", page);
        params.put("title", topic);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_topic_list", params, handler);
    }

    public static void getTweetLikeList(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("action/api/my_tweet_like_list", handler);
    }

    public static void pubLikeTweet(int tweetId, int authorId,
            AsyncHttpResponseHandler handler) {

        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", AppContext.getInstance().getLoginUid());
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_like", params, handler);
    }

    public static void pubUnLikeTweet(int tweetId, int authorId,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("uid", AppContext.getInstance().getLoginUid());
        params.put("ownerOfTweet", authorId);
        ApiHttpClient.post("action/api/tweet_unlike", params, handler);
    }

    public static void getTweetLikeList(int tweetId, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tweetid", tweetId);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/tweet_like_list", params, handler);

    }

    public static void getActiveList(int uid, int catalog, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("catalog", catalog);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/active_list", params, handler);
    }

    public static void getFriendList(int uid, int relation, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("relation", relation);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/friends_list", params, handler);
    }

    /**
     * 获取用户收藏
     * 
     * @param uid
     *            指定用户UID
     * @param type
     *            收藏类型: 0:全部收藏　1:软件　2:话题　3:博客　4:新闻　5:代码
     * @param page
     * @param handler
     */
    public static void getFavoriteList(int uid, int type, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/favorite_list", params, handler);
    }

    /**
     * 分类列表
     * 
     * @param tag
     *            第一级:0
     * @param handler
     */
    public static void getSoftwareCatalogList(int tag,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("tag", tag);
        ApiHttpClient.get("action/api/softwarecatalog_list", params, handler);
    }

    public static void getSoftwareTagList(int searchTag, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/softwaretag_list", params, handler);
    }

    /**
     * @param searchTag
     *            　　软件分类　　推荐:recommend 最新:time 热门:view 国产:list_cn
     * @param page
     * @param handler
     */
    public static void getSoftwareList(String searchTag, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("searchTag", searchTag);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_list", params, handler);
    }

    /**
     * 获取评论列表
     * 
     * @param id
     * @param catalog
     *            1新闻 2帖子 3动弹 4动态
     * @param page
     * @param handler
     */
    public static void getCommentList(int id, int catalog, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/comment_list", params, handler);
    }

    public static void getBlogCommentList(int id, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/blogcomment_list", params, handler);
    }

    public static void getUserInformation(int uid, int hisuid, String hisname,
            int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("hisname", hisname);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/user_information", params, handler);
    }

    @SuppressWarnings("deprecation")
    public static void getUserBlogList(int authoruid, final String authorname,
            final int uid, final int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("authoruid", authoruid);
        params.put("authorname", URLEncoder.encode(authorname));
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/userblog_list", params, handler);
    }

    public static void updateRelation(int uid, int hisuid, int newrelation,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("hisuid", hisuid);
        params.put("newrelation", newrelation);
        ApiHttpClient.post("action/api/user_updaterelation", params, handler);
    }

    public static void getMyInformation(int uid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get("action/api/my_information", params, handler);
    }

    /**
     * 获取新闻明细
     * 
     * @param newsId
     * @param handler
     */
    public static void getNewsDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/news_detail", params, handler);
    }

    public static void getBlogDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/blog_detail", params, handler);
    }

    /**
     * 获取软件详情
     * 
     * @param ident
     * @param handler
     */
    public static void getSoftwareDetail(String ident,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("ident",
                ident.replace(" ", ""));
        ApiHttpClient.get("action/api/software_detail", params, handler);
    }

    public static void getPostDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/post_detail", params, handler);
    }

    public static void getTweetDetail(int id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams("id", id);
        ApiHttpClient.get("action/api/tweet_detail", params, handler);
    }

    /**
     * 用户针对某个新闻，帖子，动弹，消息发表评论的接口，参数使用POST方式提交
     * 
     * @param catalog
     *            　　 1新闻　　2 帖子　　３　动弹　　４消息中心
     * @param id
     *            被评论的某条新闻，帖子，动弹或者某条消息的id
     * @param uid
     *            当天登陆用户的UID
     * @param content
     *            发表的评论内容
     * @param isPostToMyZone
     *            是否转发到我的空间，０不转发　　１转发到我的空间（注意该功能之对某条动弹进行评论是有效，其他情况下服务器借口可以忽略该参数）
     * @param handler
     */
    public static void publicComment(int catalog, int id, int uid,
            String content, int isPostToMyZone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("isPostToMyZone", isPostToMyZone);
        ApiHttpClient.post("action/api/comment_pub", params, handler);
    }

    public static void replyComment(int id, int catalog, int replyid,
            int authorid, int uid, String content,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("id", id);
        params.put("uid", uid);
        params.put("content", content);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_reply", params, handler);
    }

    public static void publicBlogComment(int blog, int uid, String content,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void replyBlogComment(int blog, int uid, String content,
            int reply_id, int objuid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("blog", blog);
        params.put("uid", uid);
        params.put("content", content);
        params.put("reply_id", reply_id);
        params.put("objuid", objuid);
        ApiHttpClient.post("action/api/blogcomment_pub", params, handler);
    }

    public static void pubTweet(Tweet tweet, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", tweet.getAuthorid());
        params.put("msg", tweet.getBody());

        // Map<String, File> files = new HashMap<String, File>();
        if (!TextUtils.isEmpty(tweet.getImageFilePath())) {
            try {
                params.put("img", new File(tweet.getImageFilePath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(tweet.getAudioPath())) {
            try {
                params.put("amr", new File(tweet.getAudioPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ApiHttpClient.post("action/api/tweet_pub", params, handler);
    }

    public static void pubSoftWareTweet(Tweet tweet, int softid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", tweet.getAuthorid());
        params.put("msg", tweet.getBody());
        params.put("project", softid);
        ApiHttpClient.post("action/api/software_tweet_pub", params, handler);
    }

    public static void deleteTweet(int uid, int tweetid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("tweetid", tweetid);
        ApiHttpClient.post("action/api/tweet_delete", params, handler);
    }

    public static void deleteComment(int id, int catalog, int replyid,
            int authorid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("id", id);
        params.put("catalog", catalog);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        ApiHttpClient.post("action/api/comment_delete", params, handler);
    }

    public static void deleteBlog(int uid, int authoruid, int id,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("authoruid", authoruid);
        params.put("id", id);
        ApiHttpClient.post("action/api/userblog_delete", params, handler);
    }

    public static void deleteBlogComment(int uid, int blogid, int replyid,
            int authorid, int owneruid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("blogid", blogid);
        params.put("replyid", replyid);
        params.put("authorid", authorid);
        params.put("owneruid", owneruid);
        ApiHttpClient.post("action/api/blogcomment_delete", params, handler);
    }

    /**
     * 用户添加收藏
     * 
     * @param uid
     *            用户UID
     * @param objid
     *            比如是新闻ID 或者问答ID 或者动弹ID
     * @param type
     *            1:软件 2:话题 3:博客 4:新闻 5:代码
     */
    public static void addFavorite(int uid, int objid, int type,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_add", params, handler);
    }

    public static void delFavorite(int uid, int objid, int type,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("objid", objid);
        params.put("type", type);
        ApiHttpClient.post("action/api/favorite_delete", params, handler);
    }

    public static void getSearchList(String catalog, String content,
            int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("catalog", catalog);
        params.put("content", content);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/search_list", params, handler);
    }

    public static void publicMessage(int uid, int receiver, String content,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiver", receiver);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void deleteMessage(int uid, int friendid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("friendid", friendid);
        ApiHttpClient.post("action/api/message_delete", params, handler);
    }

    public static void forwardMessage(int uid, String receiverName,
            String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("receiverName", receiverName);
        params.put("content", content);
        ApiHttpClient.post("action/api/message_pub", params, handler);
    }

    public static void getMessageList(int uid, int pageIndex,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/message_list", params, handler);
    }

    public static void updatePortrait(int uid, File portrait,
            AsyncHttpResponseHandler handler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("portrait", portrait);
        ApiHttpClient.post("action/api/portrait_update", params, handler);
    }

    public static void getNotices(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        ApiHttpClient.get("action/api/user_notice", params, handler);
    }

    /**
     * 清空通知消息
     * 
     * @param uid
     * @param type
     *            1:@我的信息 2:未读消息 3:评论个数 4:新粉丝个数
     * @return
     * @throws AppException
     */
    public static void clearNotice(int uid, int type,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        ApiHttpClient.post("action/api/notice_clear", params, handler);
    }

    public static void singnIn(String url, AsyncHttpResponseHandler handler) {
        ApiHttpClient.getDirect(url, handler);
    }

    /**
     * 获取软件的动态列表
     * 
     * @param softid
     * @param handler
     */
    public static void getSoftTweetList(int softid, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("project", softid);
        params.put("pageIndex", page);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/software_tweet_list", params, handler);
    }

    public static void checkUpdate(AsyncHttpResponseHandler handler) {
        ApiHttpClient.get("MobileAppVersion.xml", handler);
    }

    /**
     * 查找用户
     * 
     * @param username
     * @param handler
     */
    public static void findUser(String username,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("name", username);
        ApiHttpClient.get("action/api/find_user", params, handler);
    }

    /**
     * 获取活动列表
     * 
     * @param pageIndex
     * @param uid
     *            <= 0 近期活动 实际的用户ID 则获取用户参与的活动列表，需要已登陆的用户
     * @param handler
     */
    public static void getEventList(int pageIndex, int uid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("uid", uid);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_list", params, handler);
    }

    /**
     * 获取某活动已出席的人员列表
     * 
     * @param eventId
     * @param pageIndex
     * @param handler
     */
    public static void getEventApplies(int eventId, int pageIndex,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("pageIndex", pageIndex);
        params.put("event_id", eventId);
        params.put("pageSize", AppContext.PAGE_SIZE);
        ApiHttpClient.get("action/api/event_attend_user", params, handler);
    }

    /**
     * 举报
     * 
     * @param report
     * @param handler
     */
    public static void report(Report report, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("obj_id", report.getReportId());
        params.put("url", report.getLinkAddress());
        params.put("obj_type", report.getReason());
        if (report.getOtherReason() != null
                && !StringUtils.isEmpty(report.getOtherReason())) {
            params.put("memo", report.getOtherReason());
        } else {
            params.put("memo", "其他原因");
        }
        TLog.log("Test", report.getReportId() + "" + report.getLinkAddress()
                + report.getReason() + report.getOtherReason());
        ApiHttpClient.post("action/communityManage/report", params, handler);
    }

    /**
     * 摇一摇，随机数据
     * 
     * @param handler
     */
    public static void shake(AsyncHttpResponseHandler handler) {
        shake(-1, handler);
    }

    /**
     * 摇一摇指定请求类型
     */
    public static void shake(int type, AsyncHttpResponseHandler handler) {
        String inter = "action/api/rock_rock";
        if (type > 0) {
            inter = (inter + "/?type=" + type);
        }
        ApiHttpClient.get(inter, handler);
    }

    /**
     * 活动报名
     * 
     * @param data
     * @param handler
     */
    public static void eventApply(EventApplyData data,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("event", data.getEvent());
        params.put("user", data.getUser());
        params.put("name", data.getName());
        params.put("gender", data.getGender());
        params.put("mobile", data.getPhone());
        params.put("company", data.getCompany());
        params.put("job", data.getJob());
        ApiHttpClient.post("action/api/event_apply", params, handler);
    }

    private static void uploadLog(String data, String report,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("app", "1");
        params.put("report", report);
        params.put("msg", data);
        ApiHttpClient.post("action/api/user_report_to_admin", params, handler);
    }

    /**
     * BUG上报
     * 
     * @param data
     * @param handler
     */
    public static void uploadLog(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "1", handler);
    }

    /**
     * 反馈意见
     * 
     * @param data
     * @param handler
     */
    public static void feedback(String data, AsyncHttpResponseHandler handler) {
        uploadLog(data, "2", handler);
    }

    /**
     * team动态
     * 
     * @param team
     * @param page
     * @param handler
     */
    public static void teamDynamic(Team team, int page,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        // int uid = AppContext.getInstance().getLoginUid();
        // params.put("uid", uid);
        params.put("teamid", team.getId());
        params.put("pageIndex", page);
        params.put("pageSize", 20);
        params.put("type", "all");
        ApiHttpClient.get("action/api/team_active_list", params, handler);
    }

    /**
     * 获取team列表
     * 
     * @param handler
     */
    public static void teamList(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", AppContext.getInstance().getLoginUid());
        ApiHttpClient.get("action/api/team_list", params, handler);
    }

    /**
     * 获取team成员列表
     * 
     * @param handler
     */
    public static void getTeamMemberList(int teamid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        ApiHttpClient.get("action/api/team_member_list", params, handler);
    }

    /**
     * 获取team成员个人信息
     * 
     * @param handler
     */
    public static void getTeamUserInfo(String teamid, String uid,
            int pageIndex, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        ApiHttpClient.get("action/api/team_user_information", params, handler);
    }

    /**
     * 获取我的任务中进行中、未完成、已完成等状态的数量
     */
    public static void getMyIssueState(String teamid, String uid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_user_issue_information", params,
                handler);
    }

    /**
     * 获取指定用户的动态
     */
    public static void getUserDynamic(int teamid, String uid, int pageIndex,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("type", "git");
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_active_list", params, handler);
    }

    /**
     * 动态详情
     * 
     * @param activeid
     * @param teamid
     * @param uid
     * @param handler
     */
    public static void getDynamicDetail(int activeid, int teamid, int uid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("activeid", activeid);
        ApiHttpClient.get("action/api/team_active_detail", params, handler);
    }

    /**
     * 获取指定用户的任务
     */
    public static void getMyIssue(String teamid, String uid, int pageIndex,
            String type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("uid", uid);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", 20);
        params.put("state", type);
        params.put("projectid", "-1");
        ApiHttpClient.get("action/api/team_issue_list", params, handler);
    }

    /**
     * 获取指定周周报
     * 
     * @param teamid
     * @param year
     * @param week
     * @param handler
     */
    public static void getDiaryFromWhichWeek(int teamid, int year, int week,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("year", year);
        params.put("week", week);
        ApiHttpClient.get("action/api/team_diary_list", params, handler);
    }

    /**
     * 删除一个便签
     * 
     * @param id
     *            便签id
     * @param uid
     *            用户id
     */
    public static void deleteNoteBook(int id, int uid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("id", id); // 便签id
        ApiHttpClient
                .get("action/api/team_stickynote_recycle", params, handler);
    }

    public static void getNoteBook(int uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get("action/api/team_sticky_list", params, handler);
    }

    /**
     * 获取指定周报的详细信息
     * 
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryDetail(int teamid, int diaryid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("diaryid", diaryid);
        ApiHttpClient.get("action/api/team_diary_detail", params, handler);
    }

    /**
     * diary评论列表
     * 
     * @param teamid
     * @param diaryid
     * @param handler
     */
    public static void getDiaryComment(int teamid, int diaryid,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("teamid", teamid);
        params.put("id", diaryid);
        params.put("type", "diary");
        params.put("pageIndex", 0);
        params.put("pageSize", "20");
        KJLoger.debug(teamid + "==getDiaryComment接口=" + diaryid);
        ApiHttpClient
                .get("action/api/team_reply_list_by_type", params, handler);
    }

    /**
     * 周报评论（以后可改为全局评论）
     * 
     * @param uid
     * @param teamid
     * @param diaryId
     * @param content
     * @param handler
     */
    public static void sendComment(int uid, int teamid, int diaryId,
            String content, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("teamid", teamid);
        params.put("type", "118");
        params.put("tweetid", diaryId);
        params.put("content", content);
        ApiHttpClient.post("action/api/team_tweet_reply", params, handler);
    }

    /***
     * 客户端扫描二维码登陆
     * 
     * @author 火蚁 2015-3-13 上午11:45:47
     * 
     * @return void
     * @param url
     * @param handler
     */
    public static void scanQrCodeLogin(String url,
            AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        String uuid = url.substring(url.lastIndexOf("=") + 1);
        params.put("uuid", uuid);
        ApiHttpClient.getDirect(url, handler);
    }
}
