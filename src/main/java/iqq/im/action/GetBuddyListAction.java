
package iqq.im.action;

import iqq.im.QQActionListener;
import iqq.im.QQException;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQCategory;
import iqq.im.bean.QQClientType;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQConstants;
import iqq.im.core.QQContext;
import iqq.im.core.QQService;
import iqq.im.core.QQSession;
import iqq.im.core.QQStore;
import iqq.im.event.QQActionEvent;
import iqq.im.http.QQHttpCookie;
import iqq.im.http.QQHttpRequest;
import iqq.im.http.QQHttpResponse;
import iqq.im.service.HttpService;
import iqq.im.util.QQEncryptor;

import iqq.im.util.URLUtils;
import org.slf4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

/**
 * <p>GetBuddyListAction class.</p>
 *
 * @author ChenZhiHui
 * @since 2013-2-21
 */
public class GetBuddyListAction extends AbstractHttpAction {
    private static final Logger LOG = LoggerFactory.getLogger(GetBuddyListAction.class);

    /**
     * <p>Constructor for GetBuddyListAction.</p>
     *
     * @param context  a {@link iqq.im.core.QQContext} object.
     * @param listener a {@link iqq.im.QQActionListener} object.
     */
    public GetBuddyListAction(QQContext context, QQActionListener listener) {
        super(context, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QQHttpRequest onBuildRequest() throws QQException, JSONException {
        QQSession session = getContext().getSession();
        QQAccount account = getContext().getAccount();

        JSONObject json = new JSONObject();
        json.put("vfwebqq", session.getVfwebqq());
        json.put("hash", QQEncryptor.hash(account.getUin() + "", session.getPtwebqq()));

        QQHttpRequest req = createHttpRequest("POST", QQConstants.URL_GET_USER_CATEGORIES);
        req.addPostValue("r", json.toString());

        req.addHeader("Referer", QQConstants.REFERER_S);
        req.addHeader("Origin", URLUtils.getOrigin(QQConstants.URL_GET_USER_CATEGORIES));

        System.out.println(json.toString());

        return req;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onHttpStatusOK(QQHttpResponse response) throws QQException, JSONException {
        LOG.info(response.getResponseString());
        JSONObject json = new JSONObject(response.getResponseString());
        int retcode = json.getInt("retcode");
        QQStore store = getContext().getStore();
        if (retcode == 0) {
            // 处理好友列表
            JSONObject results = json.getJSONObject("result");
            // 获取JSON列表信息
            JSONArray jsonCategories = results.getJSONArray("categories");
            // 获取JSON好友基本信息列表 flag/uin/categories
            JSONArray jsonFriends = results.getJSONArray("friends");
            // face/flag/nick/uin
            JSONArray jsonInfo = results.getJSONArray("info");
            // uin/markname/
            JSONArray jsonMarknames = results.getJSONArray("marknames");
            // vip_level/u/is_vip
            JSONArray jsonVipinfo = results.getJSONArray("vipinfo");

            // 默认好友列表
            QQCategory c = new QQCategory();
            c.setIndex(0);
            c.setName("我的好友");
            c.setSort(0);
            store.addCategory(c);
            // 初始化好友列表
            for (int i = 0; i < jsonCategories.length(); i++) {
                JSONObject jsonCategory = jsonCategories.getJSONObject(i);
                QQCategory qqc = new QQCategory();
                qqc.setIndex(jsonCategory.getInt("index"));
                qqc.setName(jsonCategory.getString("name"));
                qqc.setSort(jsonCategory.getInt("sort"));
                store.addCategory(qqc);
            }
            // 处理好友基本信息列表 flag/uin/categories
            for (int i = 0; i < jsonFriends.length(); i++) {
                QQBuddy buddy = new QQBuddy();
                JSONObject jsonFriend = jsonFriends.getJSONObject(i);
                long uin = jsonFriend.getLong("uin");
                buddy.setUin(uin);
                buddy.setStatus(QQStatus.OFFLINE);
                buddy.setClientType(QQClientType.UNKNOWN);
                // 添加到列表中
                int index = jsonFriend.getInt("categories");
                QQCategory category = store.getCategoryByIndex(index);
                buddy.setCategory(category);
                category.getBuddyList().add(buddy);

                // 记录引用
                store.addBuddy(buddy);
            }
            // face/flag/nick/uin
            for (int i = 0; i < jsonInfo.length(); i++) {
                JSONObject info = jsonInfo.getJSONObject(i);
                long uin = info.getLong("uin");
                String nick = info.getString("nick");
                QQBuddy buddy = store.getBuddyByUin(uin);
                buddy.setNickname(nick);
            }
            // uin/markname
            for (int i = 0; i < jsonMarknames.length(); i++) {
                JSONObject jsonMarkname = jsonMarknames.getJSONObject(i);
                long uin = jsonMarkname.getLong("uin");
                String name = jsonMarkname.getString("markname");
                QQBuddy buddy = store.getBuddyByUin(uin);
                buddy.setMarkname(name);
            }
            // vip_level/u/is_vip
            for (int i = 0; i < jsonVipinfo.length(); i++) {
                JSONObject vipInfo = jsonVipinfo.getJSONObject(i);
                long uin = vipInfo.getLong("u");
                int vip = vipInfo.getInt("is_vip");
                int vipLevel = vipInfo.getInt("vip_level");

                QQBuddy buddy = store.getBuddyByUin(uin);
                buddy.setVipLevel(vipLevel);
                if (vip != 0) {
                    buddy.setVip(true);
                } else {
                    buddy.setVip(false);
                }
            }

            notifyActionEvent(QQActionEvent.Type.EVT_OK, store.getCategoryList());

        } else {
            LOG.warn("unknown retcode: " + retcode);
            notifyActionEvent(QQActionEvent.Type.EVT_ERROR, null);
        }

    }

}
