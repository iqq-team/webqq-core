package iqq.im;

import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQStatus;
import iqq.im.bean.QQUser;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionEvent;
import iqq.im.module.BuddyModule;
import iqq.im.module.UserModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author ChenZhiHui
 * @create-time 2013-2-23
 */
public class BuddyTest {
	private static JFrame frame = MyWebQQClientTest.getFrame();
	private static WebQQClient client = MyWebQQClientTest.getClient();
	private final static UserModule userModule = (UserModule) client.getModule(QQModule.Type.USER);

	/**
	 * buddy
	 */
	public static void test() {
		// 获取好友头像
		JButton btnGetBuddyFace = new JButton("Get Buddy Face");
		frame.add(btnGetBuddyFace);
		btnGetBuddyFace.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uinStr = JOptionPane.showInputDialog("Uin");
				getBuddyFace(Long.parseLong(uinStr));
			}
		});

		JButton btnGetBuddyInfo = new JButton("Get Buddy Info");
		frame.add(btnGetBuddyInfo);
		btnGetBuddyInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uinStr = JOptionPane.showInputDialog("Uin");
				getBuddyInfo(Long.parseLong(uinStr));
			}
		});

		JButton btnGetBuddyAccout = new JButton("Get Buddy Accout");
		frame.add(btnGetBuddyAccout);
		btnGetBuddyAccout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uinStr = JOptionPane.showInputDialog("Uin");
				getBuddyAccout(Long.parseLong(uinStr));
			}
		});

		JButton btnGetBuddySign = new JButton("Get Buddy Sign");
		frame.add(btnGetBuddySign);
		btnGetBuddySign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uinStr = JOptionPane.showInputDialog("Uin");
				try {
					getBuddySign(Long.parseLong(uinStr));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnGetOnlineBuddy = new JButton("Get Online Buddy");
		frame.add(btnGetOnlineBuddy);
		btnGetOnlineBuddy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getOnlineBuddy();
			}
		});
		
		JButton btnChangeBuddyStatus = new JButton("Change User status");
		frame.add(btnChangeBuddyStatus);
		btnChangeBuddyStatus.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				client.changeStatus(QQStatus.AWAY, new QQActionListener() {
					
					@Override
					public void onActionEvent(QQActionEvent event) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
	}

	/**
	 * 
	 */
	protected static void getOnlineBuddy() {
		BuddyModule mod = client.getModule(QQModule.Type.BUDDY);
		mod.getOnlineBuddy(new QQActionListener() {
			
			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					List list = (List) event.getTarget();
					JOptionPane.showMessageDialog(frame, "Online Count:" + list.size());
				}
			}
		});
	}

	/**
	 * 
	 */
	protected static void getBuddyAccout(long uin) {
		QQUser user = client.getStore().getBuddyByUin(uin);
		userModule.getUserAccount(user, new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				System.out.println("getBuddyAccout:" + event.getType());
			}
		});
	}

	/**
	 * 
	 */
	protected static void getBuddyInfo(long uin) {
		QQUser user = client.getStore().getBuddyByUin(uin);
		userModule.getUserInfo(user, new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				System.out.println("getBuddyInfo:" + event.getType());
			}
		});
	}

	/**
	 * 获取好友头像
	 */
	public static void getBuddyFace(final long uin) {
		QQUser user = client.getStore().getBuddyByUin(uin);
		userModule.getUserFace(user, new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					QQBuddy buddy = (QQBuddy) event.getTarget();
					Icon icon = new ImageIcon(buddy.getFace());
					JOptionPane.showMessageDialog(frame,
							"nick:" + buddy.getNickname(),
							"UIN:" + buddy.getUin(), JOptionPane.OK_OPTION,
							icon);
				}
			}
		});
	}

	/**
	 * 获取签名
	 * 
	 * @throws QQException
	 */
	public static void getBuddySign(final long uin) throws QQException {
		QQUser user = client.getStore().getBuddyByUin(uin);
		userModule.getUserSign(user, new QQActionListener() {

			@Override
			public void onActionEvent(QQActionEvent event) {
				System.out.println("getBuddySign:" + event.getType());
				if (event.getType() == QQActionEvent.Type.EVT_OK) {
					QQBuddy buddy = (QQBuddy) event.getTarget();
					JOptionPane.showMessageDialog(frame, buddy.getSign());
				}
			}
		});
	}
}
