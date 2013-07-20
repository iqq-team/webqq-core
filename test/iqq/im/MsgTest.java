package iqq.im;

import iqq.im.bean.QQMsg;
import iqq.im.bean.content.CFaceItem;
import iqq.im.bean.content.FaceItem;
import iqq.im.bean.content.FontItem;
import iqq.im.bean.content.OffPicItem;
import iqq.im.bean.content.TextItem;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionEventArgs;
import iqq.im.event.QQActionFuture;
import iqq.im.module.ChatModule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ProgressMonitor;

/**
 * @author ChenZhiHui
 * @create-time 2013-2-25
 */
public class MsgTest {
	private static JFrame frame = MyWebQQClientTest.getFrame();
	private static WebQQClient client = MyWebQQClientTest.getClient();
	private final static ChatModule msgModule = (ChatModule) client
			.getModule(QQModule.Type.CHAT);

	public static void test() {
		JButton sendButton = new JButton("sendMsg");
		frame.add(sendButton);
		sendButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uin = JOptionPane.showInputDialog(frame, "uin");
				final QQMsg msg = new QQMsg();
				msg.setTo(client.getStore().getBuddyByUin(Long.parseLong(uin)));
				TextItem t = new TextItem();
				t.setContent("消息测试。From: IQQ");
				FaceItem f = new FaceItem();
				f.setId(40);
				final FontItem font = new FontItem();
				font.setBold(false);
				font.setColor(000000);
				font.setItalic(false);
				font.setSize(12);
				font.setName("微软雅黑");
				font.setUnderline(false);
				msg.addContentItem(f);
				msg.addContentItem(t);
				msg.setType(QQMsg.Type.BUDDY_MSG);

				try {
					msgModule.uploadOffPic(client.getBuddyByUin(Long.parseLong(uin)), new File(this
							.getClass().getResource("/test.bmp").toURI()),
							new QQActionListener() {
								@Override
								public void onActionEvent(QQActionEvent event) {
									// TODO Auto-generated method stub
									System.out
											.println("uploadOffPic: " + event);
									if (event.getType() == QQActionEvent.Type.EVT_OK) {
										OffPicItem pic = (OffPicItem) event
												.getTarget();
										msg.addContentItem(pic);
										msg.addContentItem(font);
										sendMsg(msg);
									}
								}
							});
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton sendGroupButton = new JButton("sendGroupMsg");
		frame.add(sendGroupButton);
		sendGroupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uin = JOptionPane.showInputDialog(frame, "uin");
				final QQMsg msg = new QQMsg();
				// msg.setTo(client.getStore().getBuddyByUin(Long.parseLong(uin)));
				msg.setGroup(client.getStore().getGroupByCode(
						Long.parseLong(uin)));
				TextItem t = new TextItem();
				t.setContent("消息测试。From: IQQ");
				FaceItem f = new FaceItem();
				f.setId(40);
				final FontItem font = new FontItem();
				font.setBold(false);
				font.setColor(000000);
				font.setItalic(false);
				font.setSize(12);
				font.setName("微软雅黑");
				font.setUnderline(false);
				msg.addContentItem(f);
				msg.addContentItem(t);
				msg.setType(QQMsg.Type.GROUP_MSG);
				// 加载cface sig
				msgModule.getCFaceSig(new QQActionListener() {

					@Override
					public void onActionEvent(QQActionEvent event) {
						if (event.getType() == QQActionEvent.Type.EVT_OK) {
							try {
								msgModule
										.uploadCFace(
												new File(this.getClass().getResource("/test.jpg").toURI()),
												new QQActionListener() {
													@Override
													public void onActionEvent(
															QQActionEvent event) {
														// TODO Auto-generated
														// method stub
														System.out
																.println("uploadCFace: "
																		+ event);
														if (event.getType() == QQActionEvent.Type.EVT_OK) {
															CFaceItem pic = (CFaceItem) event
																	.getTarget();
															msg.addContentItem(pic);
															msg.addContentItem(font);
															sendMsg(msg);
														}
													}
												});
							} catch (URISyntaxException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
			}
		});
		
		JButton btnUploadOffPic = new JButton("Upload Offpic");
		frame.add(btnUploadOffPic);
		btnUploadOffPic.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String uin = JOptionPane.showInputDialog(frame, "uin");
				if(uin==null || uin.isEmpty()){
					return;
				}
				File file = null;
				try {
					file = new File(this.getClass().getResource("/test.jpg").toURI());
				} catch (URISyntaxException e) {
					e.printStackTrace();
					return;
				}
				final ProgressMonitor pm = new ProgressMonitor(frame, "正在上传图片，请稍候...", "0%", 0, 100); 
				final QQActionFuture future = msgModule.uploadOffPic(client.getBuddyByUin(Long.parseLong(uin)), file, new QQActionListener() {
					@Override
					public void onActionEvent(QQActionEvent event) {
						// TODO Auto-generated method stub
						System.out.println("uploadOffPic: " + event);
						if(event.getType() == QQActionEvent.Type.EVT_WRITE){
							QQActionEventArgs.ProgressArgs progress = (QQActionEventArgs.ProgressArgs ) event.getTarget();
							pm.setMaximum((int)progress.total);
							pm.setProgress((int)progress.current);
							pm.setNote(String.format("已上传 %.2f %%..", (progress.current/(double)progress.total * 100)));
							
							if(pm.isCanceled()){
								try {
									event.cancelAction();
									System.out.println("canceled!!!!!!!!!!!!!!!!");
								} catch (QQException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}else if(event.getType()==QQActionEvent.Type.EVT_OK){
							pm.close();
							System.out.println("upload OK!!!!");
						}
						
					}
				});
				
				
				 
			}
		});
	}

	protected static void uploadOffPic(long uin, File file) {
		msgModule.uploadOffPic(client.getBuddyByUin(uin), file, new QQActionListener() {
			@Override
			public void onActionEvent(QQActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("msgModule: " + event);
			}
		});
	}

	public static void sendMsg(QQMsg msg) {
		client.sendMsg( msg, new QQActionListener() {
			@Override
			public void onActionEvent(QQActionEvent event) {
				System.out.println(event.getType());
				System.out.println(event.getTarget());
			}
		});
	}
}
