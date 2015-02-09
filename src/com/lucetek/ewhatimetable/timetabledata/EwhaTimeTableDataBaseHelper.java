package com.lucetek.ewhatimetable.timetabledata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EwhaTimeTableDataBaseHelper extends SQLiteOpenHelper {
	
	private Context mContext= null;
	
	private static final String DBName= "EwhaTimeTableDB";
	private static final int DBVersion= 1;
	
	private final String KEY_ID= "_id";
	private final String KEY_CREATED_AT= "created_at";
	private final String TABLE= "TIMETABLE";
	
	// key
	private final String DAY= "_day";
	private final String TIME= "_time";
	private final String SUBNAME= "_subname";
	private final String SUBNUM= "_subnum";
	private final String CLASSNUM= "_classnum";
	private final String SUBKIND= "_subkind";
	private final String MAJOR= "_major";
	private final String GRADE= "_grade";
	private final String PROFESSOR= "_professor";
	private final String GRADEVALUE= "_gradevalue";
	private final String FULLTIME= "_time";
	private final String LECTURE= "_lecture";
	private final String CLASSNAME= "_classname";
	private final String ISENGLISH= "_isenglish";
	private final String STUDENT= "_student";
	private final String ETCMSG= "_etcmsg";
	private final String KORLECTUREPLAN= "_korlectureplan";
	private final String ENGLECTUREPLAN= "_englectureplan";
	
	private String CREATE_TABLE= "CREATE TABLE IF NOT EXISTS "+TABLE+"(_day INTEGER,_time INTEGER,_subname TEXT,_subnum TEXT,_classnum TEXT,"
			+ "_subkind TEXT,_major TEXT,_grade TEXT,_professor TEXT,_gradevalue TEXT,_time TEXT,_lecture,+"
			+"_classname TEXT,_ienglish TEXT,_stuent TEXT,_etcmsg TEXT,_korlectureplan TEXT,_englectureplan TEXT);";
	
	public EwhaTimeTableDataBaseHelper(Context context){
		super(context, DBName, null, DBVersion);
		mContext= context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}
	
	public void addCellContent(EwhaTimeTableCell cell){
		SQLiteDatabase db= getWritableDatabase();
		ContentValues value= new ContentValues();
		value.put(DAY, cell.getDay());
		value.put(TIME, cell.getTime());
		value.put(SUBNAME, cell.getRawData().getSubName());
		value.put(SUBNUM, cell.getRawData().getSubNum());
		value.put(CLASSNUM, cell.getRawData().getClassNum());
		value.put(SUBKIND, cell.getRawData().getSubKind());
		value.put(MAJOR, cell.getRawData().getMaj());
		value.put(GRADE, cell.getRawData().getGrade());
		value.put(PROFESSOR, cell.getRawData().getProf());
		value.put(GRADEVALUE, cell.getRawData().getGradeValue());
		value.put(FULLTIME, cell.getRawData().getTime());
		value.put(LECTURE, cell.getRawData().getLecture());
		value.put(CLASSNAME, cell.getRawData().getClassName());
		value.put(ISENGLISH, cell.getRawData().getIsEng());
		value.put(STUDENT, cell.getRawData().getStudent());
		value.put(ETCMSG, cell.getRawData().getEtcmsg());
		value.put(KORLECTUREPLAN, cell.getRawData().getKorLecturePlan());
		value.put(ENGLECTUREPLAN, cell.getRawData().getEngLecturePlan());
		
		db.insert(TABLE, null, value);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE);
		onCreate(db);
	}
}
