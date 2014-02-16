package com.example.lbsapitest;

import yl.demo.rock.lbs.datatype.PointF;
import yl.demo.rock.lbs.process.ILocationUtil;
import yl.demo.rock.lbs.process.ILocationUtil.OnFixLocatoinFinishedListener;
import yl.demo.rock.lbs.process.ILocationUtil.OnGetLocationResultListener;
import yl.demo.rock.lbs.process.LocationUtil;
import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {

	LocationUtil locationUtil;
	TextView positionTv,area;
	Button startBt, modeBt;
	Context mContext;
    String location[] = {};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		positionTv = (TextView)findViewById(R.id.position);
		modeBt = (Button)findViewById(R.id.modeChange);
		startBt = (Button)findViewById(R.id.start);
        area = (TextView)findViewById(R.id.area);
        final List<String[]> list = loaddata();

        final String []floor ={"Ground"," Floor 1","Floor 2","Floor 3","Floor 4","Floor 5","Floor 6","Floor 7","LG1","LG3","LG4","LG5","LG7"};

		//create a LocationUtil object
		locationUtil = new LocationUtil(this, "hkust", Environment.getExternalStorageDirectory() + "/wherami");

        locationUtil.setOnGetScanResultListener(new ILocationUtil.OnGetScanResultListener() {
            @Override
            public void onGetScanResult(List<ScanResult> scanResults) {
                System.out.println("scan result:" + scanResults.size());
            }
        });

        //set listener called when a localization result obtained
		locationUtil.setOnGetLocationResultListener(new OnGetLocationResultListener() {
			@Override
			public void onGetLocationResult(String arg0, PointF[] arg1, Integer[] arg2,
					String arg3) {
				final String areaId = arg0;
				final PointF[] positions = arg1;

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
                        if(positions != null){
                            if(positions.length>0){
                                positionTv.setText("position:" + "(" + areaId + ", " + positions[0].x + ", " + positions[0].y + ")");

                                int aid = Integer.valueOf(areaId);
                                String s = getLocation(areaId, positions[0].x,positions[0].y, list);

                                if(s != null)
                                    area.setText(floor[aid-1000] + s);
                                else
                                    area.setText(floor[aid-1000] + ", unkonwn");
                            }
                            else{
                                int aid = Integer.valueOf(areaId);
                                positionTv.setText("position:" + "(" + areaId + ", position length: null " +")");
                                area.setText(floor[aid-1000] + ", unkonwn");
                            }
                        }
                        else{
                            positionTv.setText("position:" + "(" + areaId + ", position: null " +")");
                            area.setText("unknown , unknown");
                        }
                    }
				});
			}
		});




		//set listener called when one fix localization finishes
		locationUtil.setOnFixLocationFinishedListener(new OnFixLocatoinFinishedListener() {
			
			@Override
			public void onFixLocationFinished() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(mContext, "fix mode localization finished", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		startBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(startBt.getText().toString().equals("start")) {
					//start localization
					locationUtil.startLocation();
					startBt.setText("stop");
				}
				else {
					//stop localization
					locationUtil.stopLocation();
					startBt.setText("start");
				}
				
			}
		});
		
		modeBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(modeBt.getText().toString().equals("continueMode")) {
					//change to continue mode
					locationUtil.switchToContinueLocationMode();
					modeBt.setText("fixMode");
				}
				else {
					//change to fix mode
					locationUtil.switchToFixLocationMode();
					modeBt.setText("continueMode");
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		System.exit(0);
	}


    public String getLocation(String aid, float x, float y, List<String[]> list){

        System.out.println();


        for(String[] s : list){
            if(s[0].compareTo(aid)==0){
                int ix = Integer.valueOf(s[2].trim());
                int iy = Integer.valueOf(s[3].trim());
                int rx = Integer.valueOf(s[4].trim());
                int ry = Integer.valueOf(s[5].trim());

                if(isInrigion(ix,iy,rx,ry,x,y)){
                    System.out.println("true");
                    System.out.println(s[1]);
                    return s[1];
                }
            }
        }
        return null;
    }

    public boolean isInrigion(int ix, int iy, int rx, int ry, float x, float y){

        System.out.println("    "+ix+"  "+iy+"  "+rx+"  "+ry+"  "+x+"   "+y);

        if(x > ix && y > iy)
            if(x - ix < rx && y - iy < ry)
                return  true;
            else
                return false;
        else return false;
    }


    public List loaddata(){

        Log.i("load ","data");
        File file  = new File(Environment.getExternalStorageDirectory() + "/wherami/locations.txt");
        Log.i("data: ", file.toString());

        List<String[]> list = new ArrayList();
        try {

            FileReader read = new FileReader(file);
            BufferedReader br = new BufferedReader(read);
            String row;
            while((row = br.readLine())!=null){
                System.out.println(row);
                String s[] = row.split(",");
                list.add(s);
            }
            br.close();
            read.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

}
