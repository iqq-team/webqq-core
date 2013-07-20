/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Project  : WebQQCoreAsync
 * Package  : iqq.im
 * File     : WebQQClientTest.java
 * Author   : solosky < solosky772@qq.com >
 * Created  : 2012-9-6
 * License  : Apache License 2.0 
 */
package iqq.im;

import iqq.im.QQException.QQErrorCode;
import iqq.im.actor.SwingActorDispatcher;
import iqq.im.bean.QQAccount;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQCategory;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionEvent.Type;
import iqq.im.event.QQNotifyEvent;
import iqq.im.module.UserModule;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * 
 * @author chenzhihui
 * 
 */
public class MyWebQQClientTest {

	private static JFrame frame;
	private static WebQQClient client;

	public static JFrame getFrame() {
		return frame;
	}

	public static WebQQClient getClient() {
		return client;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws QQException {
		client = new WebQQClient("2560435719", "xu1234",
				new QQNotifyListener() {
					public void onNotifyEvent(QQNotifyEvent event) {
						System.out.println("QQNotifyEvent: " + event.getType()
								+ ", " + event.getTarget());
						if (event.getType() == QQNotifyEvent.Type.CHAT_MSG) {
							QQMsg msg = (QQMsg) event.getTarget();
							try {
								System.out.println("好友消息: "
										+ msg.packContentList());
							} catch (QQException e) {
								e.printStackTrace();
							}
						} else if (event.getType() == QQNotifyEvent.Type.KICK_OFFLINE) {
							System.out.println("被踢下线: "
									+ (String) event.getTarget());
						} else if(event.getType() == QQNotifyEvent.Type.CAPACHA_VERIFY){
							System.out.println("需要输入验证码: " + event.getTarget());
							
						}
					}
				}, new SwingActorDispatcher()); // 试试swing
		doLogin(client, null);
		showTestUI();
	}

	public static void doLogin(final WebQQClient client, String code) {
		final QQActionListener listener = new QQActionListener() {
			public void onActionEvent(QQActionEvent event) {
				System.out.println("LOGIN_STATUS:" + event.getType() + ":"
						+ event.getTarget());
				if (event.getType() == Type.EVT_OK) {
					// 到这里就算是登录成功了

					// 获取下用户信息
					client.getUserInfo(client.getAccount(), new QQActionListener() {
						public void onActionEvent(QQActionEvent event) {
							System.out.println("LOGIN_STATUS:"
									+ event.getType() + ":" + event.getTarget());
						}
					});

					// 获取好友列表, 群列表等等..TODO.
					// 不一定调用，可能会有本地缓存
					client.getBuddyList(new QQActionListener() {

						@Override
						public void onActionEvent(QQActionEvent event) {
							// TODO Auto-generated method stub
							System.out.println("******** " + event.getType()
									+ " ********");
							if (event.getType() == QQActionEvent.Type.EVT_OK) {
								System.out.println("******** 好友列表  ********");
								List<QQCategory> qqCategoryList = (List<QQCategory>) event
										.getTarget();

								for (QQCategory c : qqCategoryList) {
									System.out.println("分组名称:" + c.getName());
									List<QQBuddy> buddyList = c.getBuddyList();
									for (QQBuddy b : buddyList) {
										System.out.println("---- QQ nick:"
												+ b.getNickname()
												+ " markname:"
												+ b.getMarkname() + " uin:"
												+ b.getUin() + " isVip:"
												+ b.isVip() + " vip_level:"
												+ b.getVipLevel());
									}

								}
							} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
								System.out.println("** 好友列表获取失败，处理重新获取");
							}
						}
					});

					// 获取群列表名称
					client.getGroupList(new QQActionListener() {

						@Override
						public void onActionEvent(QQActionEvent event) {
							if (event.getType() == QQActionEvent.Type.EVT_OK) {
								System.out.println("******** 群列表  ********");
								List<QQGroup> groupList = (List<QQGroup>) event
										.getTarget();
								for (QQGroup g : groupList) {
									System.out.println("-- name:" + g.getName()
											+ " gid: " + g.getGin() + "code: " + g.getCode()) ;
								}
							}
						}
					});

					// 所有的逻辑完了后，启动消息轮询
					client.beginPollMsg();

				} else if (event.getType() == Type.EVT_ERROR) {
					QQException e = (QQException) event.getTarget();
					if (e.getError() == QQErrorCode.NEED_CAPTCHA) {
						doVerify(client);
					}
				}
			}
		};

		client.login(QQStatus.ONLINE, listener);

	}

	public static void doVerify(final WebQQClient client) {
		client.getCaptcha(new QQActionListener() {
			public void onActionEvent(QQActionEvent event) {
				if (event.getType() == Type.EVT_OK) {
					byte[] data = (byte[]) event.getTarget();
					try {
						OutputStream out = new FileOutputStream(new File(
								"verify.png"));
						out.write(data);
						out.close();
						System.out.print("请输入验证码:");
						String code = new BufferedReader(new InputStreamReader(
								System.in)).readLine();
						doLogin(client, code);
					} catch (IOException e) {
					}
				}
			}
		});

	}

	public static void showTestUI() {
		frame = new JFrame("Test...");
		frame.setSize(200, 400);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new FlowLayout());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing Frame...");
				client.logout(new QQActionListener() {

					@Override
					public void onActionEvent(QQActionEvent event) {
						if (event.getType() == QQActionEvent.Type.EVT_OK) {
							System.out.println("WebQQ Logout Success...");
						} else if (event.getType() == QQActionEvent.Type.EVT_ERROR) {
							System.out.println("WebQQ Logout fail!");
						}
					}
				});
			}
		});

		// 获取用户头像
		getFace();
		// Test Buddy
		BuddyTest.test();
		MsgTest.test();
	}

	/**
	 * 获取用户头像
	 */
	public static void getFace() {
		JButton b = new JButton("Get User Face");
		frame.add(b);

		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UserModule um = (UserModule) client
						.getModule(QQModule.Type.USER);
				um.getUserFace(client.getAccount(), new QQActionListener() {

					@Override
					public void onActionEvent(QQActionEvent event) {
						// TODO Auto-generated method stub
						if (event.getType() == QQActionEvent.Type.EVT_OK) {
							QQAccount user = (QQAccount) event.getTarget();
							Icon icon = new ImageIcon(user.getFace());
							JOptionPane.showMessageDialog(frame,
									"nick:" + user.getNickname(),
									"QQ:" + user.getUsername(),
									JOptionPane.OK_OPTION, icon);
						}
					}
				});
			}
		});

	}

}
