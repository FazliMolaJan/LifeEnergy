package cn.dennishucd.result;

import android.view.View;

public class CornerCellResult {
		
		private String title;
		private String value;
		private boolean isArrow;
		private View view;
		
		public CornerCellResult(String title){
			this(title, null, false);
		}
		
		public CornerCellResult(String title, boolean isArrow){
			this(title, null, isArrow);
		}
		
		public CornerCellResult(String title, String value, boolean isArrow){
			this.title = title;
			this.value = value;
			this.isArrow = isArrow;
		}
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public boolean isArrow() {
			return isArrow;
		}
		public void setArrow(boolean isArrow) {
			this.isArrow = isArrow;
		}
		public View getView() {
			return view;
		}
		public void setView(View view) {
			this.view = view;
		}
		
	}