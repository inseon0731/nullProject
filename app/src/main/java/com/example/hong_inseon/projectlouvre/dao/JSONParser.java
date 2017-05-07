//package com.example.hong_inseon.projectlouvre.dao;
//
//import android.util.Log;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class JSONParser {
//	private static final String TAG = "MainActivity";
//	public static Exhibition getParser(String jsonString){
//		Exhibition exhiData = null;
//		JSONObject obj = null;
//		JSONObject json = null;
//		try{
//			obj = new JSONObject(jsonString);
//			json = obj.getJSONObject("exhi");
//			exhiData = new Exhibition();
//			exhiData.setExhi_name(json.getString("exhi_name"));
//			exhiData.setExhi_no(json.getInt("exhi_no"));
//			exhiData.setExhi_intro(json.getString("exhi_intro"));
//			exhiData.setExhi_start(json.getString("exhi_start"));
//			exhiData.setExhi_finish(json.getString("exhi_finish"));
//
//			JSONObject workbooks = json.getJSONObject("workbooks");
//
//			JSONArray workbookArr = workbooks.getJSONArray("workbook");
//			int cnt = workbookArr.length();
//			Log.v(TAG, "����  : " + cnt);
//			Workbook workbook = null;
//			JSONObject tempWorkbook = null;
//// museum=melon - exhi=song - workbook=artist
////			ArrayList<Artist> artists ;
////			int aCnt = 0;
////			JSONArray artistArr = null;
////			JSONObject art;
////			JSONObject tempArtist = null;
////			Artist artist = null;
//
//			for(int i = 0; i < cnt ; i++){
//				tempWorkbook = workbookArr.getJSONObject(i);
//				workbook = new Workbook();
//				workbook.setWb_story(tempWorkbook.getString("wb_story"));
////				artists = new ArrayList<Artist>();
////				art = tempSong.getJSONObject("artists");
////				artistArr = art.getJSONArray("artist");
////				aCnt = artistArr.length();
////				for(int j = 0; j < aCnt ; j++){
////					tempArtist = artistArr.getJSONObject(j);
////					artist = new Artist();
////					artist.setArtistId(tempArtist.getInt("artistId"));
////					artist.setArtistName(tempArtist.getString("artistName"));
////					artists.add(artist);
////				}
////				song.setArtists(artists);
//
//				workbook.setWb_ms_no(tempWorkbook.getInt("wb_ms_no"));
//				//가져올데이터 set함수
//
//				exhiData.getWorkbooks().add(workbook);
//			}
//			Log.v(TAG, "JSON Parser success cnt : "  + cnt);
//		}catch(Exception e){
//			Log.v(TAG, "errr : " + e);
//		}
//		return exhiData;
//	}
//}
