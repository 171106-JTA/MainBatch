package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.businessobject.BusinessObject;
import com.revature.businessobject.CodeList;
import com.revature.businessobject.Message;
import com.revature.businessobject.Recipient;
import com.revature.businessobject.User;
import com.revature.businessobject.UserInfo;
import com.revature.businessobject.view.MessageView;
import com.revature.businessobject.view.UserView;
import com.revature.dao.DAOBusinessObject;

public class GetMessage {
	private static List<CodeList> status = GetCodeList.getCodeListByCode("MESSAGE-STATUS");
	private static List<CodeList> priority = GetCodeList.getCodeListByCode("PRIORITY");
	
	public static List<MessageView> getMessages(Integer userId, UserView user) {
		List<BusinessObject> records = null;
		List<MessageView> views = new ArrayList<>();	
		Message message = new Message();
		
		// Prepare query data
		message.setFromId(userId);
		
		// Attempt to get messages 
		if ((records = DAOBusinessObject.load(message)).size() > 0) {
			// Build each view per message 
			for (BusinessObject record : records) 
				views.add(createMessageView((Message)record, user));
		}
		
		return views;
	}
	
	///
	//	PRIVATE METHODS 
	///

	private static MessageView createMessageView(Message message, UserView userView) {
		List<BusinessObject> records;
		MessageView view = null;
		Recipient recipient = new Recipient();
		User user;
		UserInfo info;
		
		// Prepare query data
		recipient.setMessageId(message.getId()); 
		
		// Attempt to get recipients
		if ((records = DAOBusinessObject.load(recipient)).size() > 0) {
			view = new MessageView();
			
			// set known data 
			view.setFromEmail(userView.getEmail());
			view.setFromUserName(userView.getUsername());
			view.setTitle(message.getTitle());
			view.setMessage(message.getMessage());
			view.setPriority(GetCodeList.findCodeListNyId(priority, message.getMessagePriorityId()).getValue());
			view.setStatus(GetCodeList.findCodeListNyId(status, message.getStatusId()).getValue());
			
			// Assign recipient data 
			for (BusinessObject record : records) {
				recipient = (Recipient)record;
				
				// if user and there info found
				if ((user = GetUser.getUserById(recipient.getRecipientId())) != null && (info = GetUserInfo.getUserInfo(user)) != null)
					view.addRecipientInfo(user.getUsername(), info.getEmail());
			}
			
			
			// TODO : Pull attachments 
		}
		
		return view;
	}
}
