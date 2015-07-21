package com.rgb0101.ewhatimetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.rgb0101.ewhatimetable.searchdata.EwhaAdapter;
import com.rgb0101.ewhatimetable.searchdata.EwhaParse;
import com.rgb0101.ewhatimetable.searchdata.EwhaResult;
import com.rgb0101.ewhatimetable.searchdata.EwhaServer;
import com.rgb0101.ewhatimetable.searchdata.SearchData;

public class EwhaTimeTableActivity extends Activity {
	private static SharedPreferences pref= null;
	private static boolean isUserFinish= false;
	
	private static final String TAG= "EwhaTimeTable";
	public static final String site= "http://eureka.ewha.ac.kr/eureka/hs/sg/openHssg504021q.do?popupYn=Y";
	private static long backPressedTime= 0;
	private static Toast toast= null;
	
	private ViewAnimator menu= null;
	private TranslateAnimation anim= null;
	
	private EditText yearEdit= null;
	private Spinner semesterSpin= null;
	private Spinner semKindSpin= null;
	private Spinner univSpin= null;
	private Spinner majSpin= null;
	private Spinner subKindSpin= null;
	private Spinner daySpin= null;
	private Spinner timeSpin= null;
	private Spinner gradeSpin= null;
	private EditText subNumberEdit= null;
	private EditText subNameEdit= null;
	private EditText profNameEdit= null;
	
	private TextView mSearch= null;
	private TextView mShowMenu= null;
	private ListView mList= null;
	
	private boolean isMenuVisible= true;
	
	
	private ArrayList<String> semesterList= new ArrayList<String>();
	private ArrayList<String> semKindList= new ArrayList<String>();
	private ArrayList<String> univList= null;
	private ArrayList<ArrayList<String>> majList= new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> majValueList= new ArrayList<ArrayList<String>>();
	private ArrayList<String> subKindList= null;
	private ArrayList<String> subKindValueList= null;
	private ArrayList<String> dayList= null;
	private ArrayList<String> timeList= null;
	private ArrayList<String> gradeList= null;
	private ArrayList<String> defaultList= new ArrayList<String>();
	
	private ArrayList<SpinnerAdapter> majAdapterList= new ArrayList<SpinnerAdapter>();
	private ArrayList<String> majPostValue= null;
	private String[] majValue= {"01", "12", "02", "13", "20", "21", "06", "15", "08", "18", "10", "19", "17", "03", "04", "14", "05", "09", "11", "16"};
	private int[] isMajListExist= {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -1, -1, 12, -1, -1, -1, -1};
	private SpinnerAdapter defaultAdapter= null;
	private PopupWindow mPopup= null;
	
	private EwhaServer mServer= null;
	private ProgressDialog dialog= null;
	
	private EwhaParse mParse= null;
	private SearchData searchData= null;
	private ArrayList<EwhaResult> mResult= null;
	
	private InputMethodManager imm= null;
	
	private EwhaAdapter resultContent= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ewha_time_talbe);
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	makeView();
        makeResource();
        loadPreferences();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	if(dialog != null) dialog.dismiss();
    	if(mResult != null && mResult.size() > 0) savePreferences(false);
    	else savePreferences(true);
    }
    
    @Override
    protected void onDestroy(){
    	Log.d(TAG, "on destroy");
    	super.onDestroy();
    }
    
    private void makeView(){
    	menu= (ViewAnimator)findViewById(R.id.animmator_menu);
    	yearEdit= (EditText)findViewById(R.id.yearCondition);
    	semesterSpin= (Spinner)findViewById(R.id.semesterCondition);
    	semKindSpin= (Spinner)findViewById(R.id.semKindCondition);
    	univSpin= (Spinner)findViewById(R.id.univCondition);
    	majSpin= (Spinner)findViewById(R.id.majorCondition);
    	subKindSpin= (Spinner)findViewById(R.id.subjectCondition);
    	subNumberEdit= (EditText)findViewById(R.id.subjectNumber);
    	subNameEdit= (EditText)findViewById(R.id.subjectName);
    	profNameEdit= (EditText)findViewById(R.id.professorName);
    	daySpin= (Spinner)findViewById(R.id.dayCondition);
    	timeSpin= (Spinner)findViewById(R.id.timeCondition);
    	gradeSpin= (Spinner)findViewById(R.id.gradeCondition);
    	mSearch= (TextView)findViewById(R.id.search);
    	mShowMenu= (TextView)findViewById(R.id.showmenu);
    	mList= (ListView)findViewById(R.id.resultlist);
    	
    	yearEdit.setText(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
    	
    	menu.bringToFront();
//    	yearEdit.bringToFront();
//    	semesterSpin.bringToFront();
//    	semKindSpin.bringToFront();
    	
    	menu.setVisibility(View.INVISIBLE);
    	menu.setOnClickListener(click);
    	mSearch.setOnClickListener(click);
    	mShowMenu.setOnClickListener(click);
    	mList.setOnItemClickListener(resultSel);
    	mList.setOnScrollListener(new OnScrollListener(){
    		@Override
    		public void onScroll(AbsListView view, int firstVisible, int visibleItemCount, int totalItemCount){}
    		
    		@Override
    		public void onScrollStateChanged(AbsListView view, int pos){
    			if(isMenuVisible) hideMenu();
    		}
    	});
    	
    	imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    	mParse= new EwhaParse(this);
    }
    
    private void makeResource(){
    	semesterList.add(Integer.toString(1)+getResources().getString(R.string.semesterText));
    	semesterList.add(Integer.toString(2)+getResources().getString(R.string.semesterText));
    	semKindList.add(getResources().getString(R.string.semesterKind01));
    	semKindList.add(getResources().getString(R.string.semesterKind02));
    	defaultList.add(getResources().getString(R.string.defaultspinner));
    	univList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.universe)));
    	subKindList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.subjectdiv)));
    	subKindValueList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.subjectdivvalue)));
    	gradeList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.grade)));
    	dayList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.days)));
    	timeList= new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.time)));
    	defaultAdapter= new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, defaultList);
    	
    	int listId= R.array.univ01;
    	for(int i=0; i<majValue.length; i++){
    		int index= isMajListExist[i];
    		if(index != -1){
    			majList.add(new ArrayList<String>(Arrays.asList(getResources().getStringArray(listId++))));
    			majValueList.add(new ArrayList<String>(Arrays.asList(getResources().getStringArray(listId++))));
    			majAdapterList.add(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, majList.get(index)));
    		}
    		else{
    			majList.add(defaultList);
    			majAdapterList.add(defaultAdapter);
    		}
    	}
    	
    	semesterSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, semesterList));
    	semKindSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, semKindList));
    	univSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, univList));
    	majSpin.setAdapter(defaultAdapter);
    	subKindSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, subKindList));
    	gradeSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, gradeList));
    	daySpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, dayList));
    	timeSpin.setAdapter(new SpinnerAdapter(this, R.layout.spinner_layout, R.id.spinner_text, timeList));
    	univSpin.setOnItemSelectedListener(listener);
    	
    	searchData= new SearchData(this);
    	mServer= new EwhaServer(EwhaTimeTableActivity.this);
    }
    
    private void viewPopup(String subName, String subNum, String classNum, String subKind, 
    		String maj, String grade, String prof, String gradeValue, String time, String lecture, String classroom, String isEng, String student, String etcmsg){
    	View popup= getLayoutInflater().inflate(R.layout.popup_view, null);
    	mPopup= new PopupWindow(popup, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    	mPopup.setAnimationStyle(0);
    	mPopup.setFocusable(true);
    	mPopup.setOutsideTouchable(false);
    	
    	
    	((TextView)popup.findViewById(R.id.subNamePopup)).setText(subName);
    	((TextView)popup.findViewById(R.id.profPopup)).setText(prof);
    	((TextView)popup.findViewById(R.id.subNumPopup)).setText(subNum+"-"+classNum);
    	((TextView)popup.findViewById(R.id.subKindPopup)).setText(subKind);
    	((TextView)popup.findViewById(R.id.majorPopup)).setText(maj);
    	((TextView)popup.findViewById(R.id.gradePopup)).setText(gradeValue + " / " + time);
    	((TextView)popup.findViewById(R.id.lecturePoup)).setText(lecture);
    	((TextView)popup.findViewById(R.id.isEnglishPopup)).setText(isEng);
    	((TextView)popup.findViewById(R.id.studentCountPopup)).setText(student);
    	((TextView)popup.findViewById(R.id.etcmsgPopup)).setText(etcmsg);
    	
    	((TextView)popup.findViewById(R.id.close)).setOnClickListener(click);
    	
    	mPopup.showAtLocation(popup, Gravity.CENTER, 0, 0);
    }
    
    public ProgressDialog makeDialog(){
    	ProgressDialog dlg= new ProgressDialog(this);
		dlg.setMessage("Loading ...");
		dlg.setIndeterminate(false);
		dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dlg.setCancelable(false);
		
		return dlg;
    }
    public ProgressDialog getDialog(){
    	return dialog;
    }
    
    View.OnClickListener click= new View.OnClickListener(){
    	@Override
    	public void onClick(View v){
    		String str= null;
    		int id= v.getId();
    		
    		if(id == R.id.showmenu){
    			showMenu();
    		} else if(id != R.id.animmator_menu && id != R.id.relative_menu){
    			if(id == R.id.search){
    				mResult= null;
    				
    				int idx;
    				searchData.setYearCd(yearEdit.getText().toString());
    				searchData.setSemester(semesterSpin.getSelectedItemPosition()+1);
    				searchData.setSemKind(semKindSpin.getSelectedItemPosition());
    				idx= univSpin.getSelectedItemPosition();
    				searchData.setUniv(idx == 0 ? null : majValue[univSpin.getSelectedItemPosition()-1]);
    				idx= majSpin.getSelectedItemPosition();
    				searchData.setMaj(idx == 0 ? null : majPostValue.get(majSpin.getSelectedItemPosition()-1));
    				idx= subKindSpin.getSelectedItemPosition();
    				searchData.setSubKind(idx == 0 ? null : subKindValueList.get(subKindSpin.getSelectedItemPosition()-1));
    				str= subNumberEdit.getText().toString();
    				searchData.setSubNumber(str);
    				str= subNameEdit.getText().toString();
    				searchData.setSubName(str);
        			str= profNameEdit.getText().toString();
        			searchData.setProfessor(str);
        			idx= gradeSpin.getSelectedItemPosition();
        			if(idx != 0) searchData.setGrade(Integer.toString(idx));
        			else searchData.setGrade("");
    				searchData.setDay(daySpin.getSelectedItemPosition());
    				searchData.setTime(timeSpin.getSelectedItemPosition());
    				
    				if(imm.isAcceptingText()) imm.hideSoftInputFromWindow(yearEdit.getWindowToken(), 0);
    				if(isMenuVisible) hideMenu();
    				
    				if((searchData.getUniv() == null || searchData.getUniv().equalsIgnoreCase("")) && (searchData.getMaj() == null || searchData.getMaj().equalsIgnoreCase("")) &&
    						(searchData.getSubKind() == null || searchData.getSubKind().equalsIgnoreCase("")) && searchData.getDay() == 0 &&
    						searchData.getTime() == 0 && (searchData.getSubNumber() == null || searchData.getSubNumber().equalsIgnoreCase("")) &&
    						(searchData.getSubName() == null || searchData.getSubName().equalsIgnoreCase("")) && (searchData.getProfessor() == null || searchData.getProfessor().equalsIgnoreCase(""))){
    					Toast.makeText(EwhaTimeTableActivity.this.getApplicationContext(), getResources().getString(R.string.err), Toast.LENGTH_SHORT).show();
    				}
    				else{
    					dialog= makeDialog();
        				dialog.show();
        				mServer.parse(site, searchData);
    				}
    			} else if(id == R.id.close){
    				if(mPopup != null) mPopup.dismiss();
    			}
    			else if(id != R.id.relativePopup){
    				if(isMenuVisible) hideMenu();    				
    			}
    		}
    		
    	}
    };
    
    AdapterView.OnItemClickListener resultSel= new AdapterView.OnItemClickListener() {
    	@Override
    	public void onItemClick(AdapterView<?> adapter, View v, int position, long id){
    		if(adapter.getId() == R.id.resultlist){
    			EwhaResult temp= mResult.get(position);
    			EwhaTimeTableActivity.this.viewPopup(temp.getSubName(), temp.getSubNum(), temp.getClassNum(), temp.getSubKind()
    					, temp.getMaj(), temp.getGrade(), temp.getProf(), temp.getGradeValue(), temp.getTime()
    					, temp.getLecture(), temp.getClassName(), temp.getIsEng(), temp.getStudent(), temp.getEtcmsg());
    			if(isMenuVisible) hideMenu();
    		}
    	}
	};
    
    AdapterView.OnItemSelectedListener listener= new AdapterView.OnItemSelectedListener() {
    	@Override
    	public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id){
    		Spinner sel= (Spinner)adapter;
    		if(sel.getId() == R.id.univCondition){
    			if(pos != 0){
        			majPostValue= isMajListExist[pos] != -1 ? majValueList.get(pos-1) : null;
            		majSpin.setAdapter(majAdapterList.get(pos-1));
        		} else {
        			majPostValue= null;
        			majSpin.setAdapter(defaultAdapter);
        		}
    		}
    	}
    	
    	@Override
    	public void onNothingSelected(AdapterView<?> adapter){}
	};

    private void showMenu(){
    	isMenuVisible= true;
    	anim= new TranslateAnimation(0, 0, -menu.getHeight(), 0);
    	anim.setDuration(200);
    	anim.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation arg0) { menu.setVisibility(View.VISIBLE); }
			@Override public void onAnimationRepeat(Animation arg0) {}
			@Override public void onAnimationEnd(Animation arg0) {}
		});
    	menu.startAnimation(anim);
    }
    
    private void hideMenu(){
    	isMenuVisible= false;
    	anim= new TranslateAnimation(0, 0, 0, -menu.getHeight());
    	anim.setDuration(200);
    	anim.setAnimationListener(new Animation.AnimationListener() {
			@Override public void onAnimationStart(Animation arg0) {}
			@Override public void onAnimationRepeat(Animation arg0) {}
			@Override public void onAnimationEnd(Animation arg0) { menu.setVisibility(View.INVISIBLE); }
		});
    	menu.startAnimation(anim);
    }
    
    public void setParse(EwhaParse parse){ mParse= parse; }
    public void setResult(ArrayList<EwhaResult> result){ mResult= result; }
    public void setResultAdpater(EwhaAdapter adapter){ resultContent= null; resultContent= adapter; }
    public EwhaParse getParse(){
    	if(mParse != null) return mParse;
    	else return null; 
    }
    public ArrayList<EwhaResult> getResult(){
    	if(mResult != null) return mResult;
    	else return null;
    }
    public EwhaAdapter getAdapter(){
    	if(resultContent != null) return resultContent;
    	else return null;
    }
    public ListView getListView(){
    	return mList;
    }
    
    @Override
    public void onBackPressed(){
    	if(System.currentTimeMillis() > backPressedTime + 2000){
    		backPressedTime= System.currentTimeMillis();
    		toast= Toast.makeText(getApplicationContext(), getResources().getString(R.string.back_finish), Toast.LENGTH_SHORT);
    		toast.show();
    		return ;
    	}
    	if(System.currentTimeMillis() <= backPressedTime+2000){
    		toast.cancel();
    		isUserFinish= true;
    		super.onBackPressed();
    	}
    }
    
    private void loadPreferences(){
		pref= this.getSharedPreferences("saving", MODE_PRIVATE);
		
		int count= pref.getInt("lastCount", 0);
		
		Log.d(TAG, "count : "+Integer.toString(count));
		
		if(count > 0){
			for(int i=0; i<count; i++){
				String str= "lastValue"+Integer.toString(i);
				EwhaResult result= new EwhaResult(this);
				result.setSubName(pref.getString(str+"subName", ""));
				result.setSubNum(pref.getString(str+"subNum", ""));
				result.setClassNum(pref.getString(str+"classNum", ""));
				result.setSubKind(pref.getString(str+"subKind", ""));
				result.setMaj(pref.getString(str+"Major", ""));
				result.setGrade(pref.getString(str+"grade", ""));
				result.setProf(pref.getString(str+"prof", ""));
				result.setTime(pref.getString(str+"time", ""));
				result.setLecture(pref.getString(str+"lecture", ""));
				result.setClassName(pref.getString(str+"className", ""));
				result.setIsEng(pref.getString(str+"isEnglish", ""));
				result.setStudent(pref.getString(str+"student", ""));
				result.setEtcmsg(pref.getString(str+"etcmsg", ""));
				result.setKorLecturePlan(pref.getString(str+"korLecturePlan", ""));
				result.setEngLecturePlan(pref.getString(str+"engLecturePlan", ""));
				
				if(mResult == null) mResult= new ArrayList<EwhaResult>();
				mResult.add(result);
			}
			
			mList.setAdapter(new EwhaAdapter(this, R.layout.listitem, mResult));
		}
	}
	private void savePreferences(boolean dataEmpty){
		pref= this.getSharedPreferences("saving", MODE_PRIVATE);
		SharedPreferences.Editor edit= pref.edit();
		if(!dataEmpty && !isUserFinish) {
			edit.putInt("lastCount", mResult.size());
			for(int i=0; i< mResult.size(); i++){
				EwhaResult result= mResult.get(i);
				String str= "lastValue"+Integer.toString(i);
				edit.putString(str+"subName", result.getSubName());
				edit.putString(str+"subNum", result.getSubNum());
				edit.putString(str+"classNum", result.getClassNum());
				edit.putString(str+"subKind", result.getSubKind());
				edit.putString(str+"Major", result.getMaj());
				edit.putString(str+"grade", result.getGrade());
				edit.putString(str+"prof", result.getProf());
				edit.putString(str+"time", result.getTime());
				edit.putString(str+"lecture", result.getLecture());
				edit.putString(str+"className", result.getClassName());
				edit.putString(str+"isEnglish", result.getIsEng());
				edit.putString(str+"student", result.getStudent());
				edit.putString(str+"etcmsg", result.getEtcmsg());
				edit.putString(str+"korLecturePlan", result.getKorLecturePlan());
				edit.putString(str+"engLecturePlan", result.getEngLecturePlan());
			}
		}
		else edit.clear();
		edit.commit();
	}
}
