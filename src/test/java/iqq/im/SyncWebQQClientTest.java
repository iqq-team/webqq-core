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
import iqq.im.actor.ThreadActorDispatcher;
import iqq.im.bean.QQBuddy;
import iqq.im.bean.QQGroup;
import iqq.im.bean.QQGroupMember;
import iqq.im.bean.QQMsg;
import iqq.im.bean.QQStatus;
import iqq.im.core.QQModule;
import iqq.im.event.QQActionEvent;
import iqq.im.event.QQActionEvent.Type;
import iqq.im.event.QQActionFuture;
import iqq.im.event.QQNotifyEvent;
import iqq.im.event.QQNotifyEventArgs;
import iqq.im.module.ChatModule;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * 
 * 
 * @author solosky <solosky772@qq.com>
 * 
 */
public class SyncWebQQClientTest {

	private static QQClient client;
	/**
	 * @param args
	 * @throws QQException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws QQException, IOException {
		client = new WebQQClient("1070772010","xu1234", new QQNotifyListener() {
			public void onNotifyEvent(QQNotifyEvent event) {
				System.out.println("QQNotifyEvent: " + event.getType() +", " + event.getTarget());
				if(event.getType() ==  QQNotifyEvent.Type.CHAT_MSG){
					QQMsg msg = (QQMsg) event.getTarget();
					try {
						System.out.println("好友消息: " + msg.packContentList());
					} catch (QQException e) {
						e.printStackTrace();
					}
				}else if(event.getType() ==  QQNotifyEvent.Type.KICK_OFFLINE){
					System.out.println("被踢下线: " + (String)event.getTarget());
				}else if(event.getType() == QQNotifyEvent.Type.CAPACHA_VERIFY){
					try {
						QQNotifyEventArgs.ImageVerify verify = (QQNotifyEventArgs.ImageVerify) event.getTarget();
						ImageIO.write(verify.image, "png", new File("verify.png"));
						System.out.println(verify.reason);
						System.out.print("请输入在项目根目录下verify.png图片里面的验证码:");
						String code = new BufferedReader(new InputStreamReader(System.in)).readLine();
						client.submitVerify(code, event);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}, new ThreadActorDispatcher());
		
		//测试同步模式登录
		QQActionFuture future = client.login(QQStatus.ONLINE, null);
		QQActionEvent event = future.waitFinalEvent();
		if(event.getType() == Type.EVT_OK){
			System.out.println("登录成功！！！！");
			
			event = client.getUserInfo(client.getAccount(), null).waitFinalEvent();
			
			System.out.println("用户信息:" + event.getType() +" - " + event.getTarget());
//			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//			String line = null;
//			while( (line = reader.readLine())!= null){
//				MsgModule m = (MsgModule)client.getModule(QQModule.Type.MSG);
//				QQMsg msg = new QQMsg();
//				try {
//					QQActionEvent e = m.sendMsg(null, msg).waitFinalEvent();;
//					System.out.println("发送消息:" + e.toString());
//				} catch (Exception e) {
//					System.out.println("错误！！！");
//					e.printStackTrace();
//				}
//				
//			}
			
			client.getBuddyList(null).waitFinalEvent();
			System.out.println("Buddy count: " + client.getBuddyList().size());
			
//			int i = 0;
//			for(QQBuddy buddy: client.getBuddyList()){
//				QQActionFuture f = client.getUserQQ(buddy, null);
//				QQActionEvent e = f.waitFinalEvent();
//				System.out.println("# " +(i++) + " QQ: " + buddy.getNickname() +" : " + buddy.getQQ() +" ["+e.getType()+"]");
//			}
//			
//			client.getGroupList(null).waitFinalEvent();
//			System.out.println("Buddy count: " + client.getGroupList().size());
//			for(QQGroup group: client.getGroupList()){
//				client.getGroupInfo(group, null).waitFinalEvent();
//				System.out.println("group member count:" + group.getMembers().size());
//				for(QQGroupMember mem: group.getMembers()){
//					QQActionFuture f = client.getUserQQ(mem, null);
//					QQActionEvent e = f.waitFinalEvent();
//					System.out.println("# " +(i++) + "Group:"+group.getName()+", QQ: " + mem.getNickname() +" : " + mem.getQQ() +" ["+e.getType()+"]");
//				}
//			}
			
			
			//所有的逻辑完了后，启动消息轮询
			client.beginPollMsg();
			
		}else if(event.getType() == Type.EVT_ERROR){
			QQException ex = (QQException) event.getTarget();
			if(ex.getError() == QQErrorCode.NEED_CAPTCHA){
				//TODO ..
				System.out.println("需要验证码！！！");
			}
		}
	}

}
