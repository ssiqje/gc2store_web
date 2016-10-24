package com.syz.dataPackage.bean;

import org.json.JSONObject;

public class ResponseMessage {

		private String action;
		private String result;
		private Object json_message;
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String tip) {
			this.result = tip;
		}
		public Object getJson_message() {
			return json_message;
		}
		public void setJson_message(Object json_message) {
			this.json_message = json_message;
		}
		@Override
		public String toString() {
			return "ResponseMessage [action=" + action + ", tip=" + result + ", json_message=" + json_message + "]";
		}
		
}
