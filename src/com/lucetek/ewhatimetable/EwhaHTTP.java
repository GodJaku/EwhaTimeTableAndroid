package com.lucetek.ewhatimetable;

import android.content.Context;

public class EwhaHTTP {

	private Context mContext= null;
	private String mURL= null;
	private SearchData mSearch= null;
	private boolean isUpdate= false;
	private int mPage= 1;
	
	public EwhaHTTP(Context context, String url, SearchData search, boolean isUpdate, int page){
		mContext= context;
		mURL= url;
		mSearch= search;
		this.isUpdate= isUpdate;
		mPage= page;
	}
	
	
	public void setURL(String url){ mURL= url; }
	public void setSearch(SearchData search){ mSearch= search; }
	public void setIsUpdate(boolean update){ isUpdate= update; }
	public void setPage(int page){ mPage= page; }
	
	public String getURL(){ return mURL; }
	public SearchData getSearch(){ return mSearch; }
	public boolean getIsUpdate(){ return isUpdate; }
	public int getPage(){ return mPage; }
}
