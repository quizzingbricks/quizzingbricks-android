//package com.quizzingbricks.activities.inactive;
//
//
//	
//
//	import org.json.JSONObject;
//
//import com.quizzingbricks.R;
//import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
//import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
//import com.quizzingbricks.tools.AsyncTaskResult;
//
//	import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
//import android.app.FragmentTransaction;
//import android.app.ListFragment;
//import android.content.Context;
//
//	import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//	import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.view.ViewPager;
//
//	public class ActionbarActivity  extends FragmentActivity implements ActionBar.TabListener, OnTaskCompleteAsync{
//		private ViewPager viewPager;
//	    private TabsPagerAdapter1 mAdapter;
//	    private ActionBar actionBar;
//	  
//	    // Tab titles
//	    private String[] tabs = { "Create Game","Second" };
//	 
//	    @Override
//	    protected void onCreate(Bundle savedInstanceState) {
//	        super.onCreate(savedInstanceState);
//	       
//	        setContentView(R.layout.lobby_layout);
//	        UserThreadedAPI lt = new UserThreadedAPI(this);
//			lt.getActiveGamesList(this);
//			
//		 
//	 
//	        // Initilization
//	        viewPager = (ViewPager) findViewById(R.id.pager);
//	        actionBar = getActionBar();
//	        mAdapter = new TabsPagerAdapter1(getSupportFragmentManager());
//	 
//	        viewPager.setAdapter(mAdapter);
//	        actionBar.setHomeButtonEnabled(false);
//	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
//	 
//	        // Adding Tabs
//	        for (String tab_name : tabs) {
//	            actionBar.addTab(actionBar.newTab().setText(tab_name)
//	                    .setTabListener(this));
//	        }
//	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//	        	 
//	            @Override
//	            public void onPageSelected(int position) {
//	                // on changing the page
//	                // make respected tab selected
//	                actionBar.setSelectedNavigationItem(position);
//	            }
//	         
//	            @Override
//	            public void onPageScrolled(int arg0, float arg1, int arg2) {
//	            }
//	         
//	            @Override
//	            public void onPageScrollStateChanged(int arg0) {
//	            }
//	        });
//	    }
//		@Override
//		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
//			// TODO Auto-generated method stub
//			viewPager.setCurrentItem(tab.getPosition());
//			
//		}
//
//		@Override
//		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
//			// TODO Auto-generated method stub
//			
//		}
//		@Override
//		public void onComplete(AsyncTaskResult<JSONObject> result) {
//			// TODO Auto-generated method stub
//			
//		}
//
//	}
//
//	 
//	class TabsPagerAdapter1 extends FragmentPagerAdapter {
//	 
//	    public TabsPagerAdapter1(FragmentManager fm) {
//	        super(fm);
//	    }
//	 
//	    @Override
//	    public Fragment getItem(int index) {
//	 
//	        switch (index) {
//	        case 0:
//	            // Top Rated fragment activity
//	            return new TopRatedFragment1();
//	        case 1:
////	            // Games fragment activity
//	            return new GamesFragment1();
////	        case 2:
////	            // Movies fragment activity
////	            return new MoviesFragment();
//	        }
//	 
//	        return null;
//	    }
//	 
//	    @Override
//	    public int getCount() {
//	        // get item count - equal to number of tabs
//	        return 2;
//	    }
//	 
//	}
//	 
//	class TopRatedFragment1 extends Fragment {
//	 
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	            Bundle savedInstanceState) {
//	 
//	        View rootView = inflater.inflate(R.layout.create_game, container, false);
//	         
//	        return rootView;
//	    }
//	}
//	class GamesFragment1 extends Fragment {
////		public GamesFragment(Context context){
////			
////		}
//		 
//	    @Override
//	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//	            Bundle savedInstanceState) {
//	 
//	        View rootView = inflater.inflate(R.layout.list_fragment, container, false);
////	        setListAdapter(new Adapter(mContext, new String[] {"hej"}));
//	         
//	        return rootView;
//	    }
//	}
