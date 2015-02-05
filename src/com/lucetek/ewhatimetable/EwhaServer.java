package com.lucetek.ewhatimetable;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.lucetek.ewhatimetable.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class EwhaServer {
	// constant
	private static final String TAG= "EwhaTimeTableParse";
	private Context mContext= null;
	private EwhaHTTP mHTTP= null;
	
	public EwhaServer(Context context){
		mContext= context;
	}
	
	public void parse(String url, SearchData search){
		parse(url, search, false, 1);
	}
	
	public void parse(String url, SearchData search, boolean isUpdate, int page){
		mHTTP= new EwhaHTTP(mContext, url, search, isUpdate, page);
		(new ParseURL(mContext, mHTTP)).execute();
	}
	
	private class ParseURL extends AsyncTask<Void, Void, EwhaAdapter>{
		private Context mContext= null;
		private ProgressDialog dlg= null;
		private EwhaHTTP mHTTP= null;
		
		public ParseURL(Context context, EwhaHTTP http){
			mContext= context;
			mHTTP= http;
		}
		
		@Override
		protected void onPreExecute(){
			super.onPreExecute();
//			dlg= new ProgressDialog(mContext);
//			dlg.setMessage("Loading ...");
//			dlg.setIndeterminate(false);
//			dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			dlg.setCancelable(false);
//			dlg.show();
		}
		
		@Override
		protected EwhaAdapter doInBackground(Void... params){
			String result= null;
			SearchData search= mHTTP.getSearch();
		    try {
		    	HttpClient httpclient = new DefaultHttpClient();
			    HttpPost httppost = new HttpPost(mHTTP.getURL());
			    
			    ArrayList<NameValuePair> postparams= new ArrayList<NameValuePair>();
			    result= search.getYearCd().toString() + Integer.toString(search.getSemester()) + Integer.toString(search.getSemKind());
			    postparams.add(new BasicNameValuePair("yearTermCd", result));
			    result= search.getSubKind();
			    if(result != null && result.length() >= 3){
			    	postparams.add(new BasicNameValuePair("viewKindCd", "01"));
			    	postparams.add(new BasicNameValuePair("areaCd", result));
			    }
			    else{
			    	postparams.add(new BasicNameValuePair("viewKindCd", result));
			    	postparams.add(new BasicNameValuePair("areaCd", ""));
			    }
			    
			    if(search.getSemKind() == 1) postparams.add(new BasicNameValuePair("univCd", "U"));
			    else postparams.add(new BasicNameValuePair("univCd", search.getUniv()));
			    postparams.add(new BasicNameValuePair("clsMajCd", search.getMaj()));
			    result= search.getSubNumber();
			    if(result != null) postparams.add(new BasicNameValuePair("subjectCd", result));
			    result= search.getSubName();
			    if(result != null) postparams.add(new BasicNameValuePair("subjectNm", result));
			    result= search.getProfessor();
			    if(result != null) postparams.add(new BasicNameValuePair("profName", result));
			    int idx= search.getDay();
			    if(idx != 0) postparams.add(new BasicNameValuePair("lectureDay", Integer.toString(idx)));
			    idx= search.getTime();
			    if(idx != 0) postparams.add(new BasicNameValuePair("lectureHour", Integer.toString(idx)));
			    
			    postparams.add(new BasicNameValuePair("groupCd", "1500"));
			    postparams.add(new BasicNameValuePair("excelYn", ""));
			    postparams.add(new BasicNameValuePair("searchYn", "Y"));
			    
			    httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
			    httppost.setEntity(new UrlEncodedFormEntity(postparams, "utf-8"));
			    
			    HttpResponse response= httpclient.execute(httppost);
			    HttpEntity entity= response.getEntity();
			    result= EntityUtils.toString(entity);
			    
			    if(result != null) {
					EwhaParse parse= new EwhaParse(mContext, result);
					((EwhaTimeTableActivity)mContext).setParse(parse);
					
					EwhaAdapter adapter= new EwhaAdapter(mContext, R.layout.listitem, ((EwhaTimeTableActivity)mContext).getResult());
					return adapter;
				} else Toast.makeText(mContext, "404 not found", Toast.LENGTH_SHORT).show();
			    
		    } catch (ClientProtocolException e) { e.printStackTrace();
		    } catch (IOException e) { e.printStackTrace(); }
			return null;
		}
		
		@Override
		protected void onPostExecute(EwhaAdapter result){
			super.onPostExecute(result);
			dlg= ((EwhaTimeTableActivity)mContext).getDialog(); 
			if(dlg != null && dlg.isShowing()) dlg.dismiss();
//			((EwhaTimeTableActivity)mContext).getAdapter().notifyDataSetChanged();
			((EwhaTimeTableActivity)mContext).getListView().setAdapter(result);
//			((EwhaTimeTableActivity)mContext).getListView().invalidate();
		}
	}
}
