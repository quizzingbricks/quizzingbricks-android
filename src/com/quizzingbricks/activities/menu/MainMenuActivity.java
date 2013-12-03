package com.quizzingbricks.activities.menu;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class MainMenuActivity extends FragmentActivity implements ActionBar.TabListener, OnTaskCompleteAsync{
		private ViewPager viewPager;
//	    private TabsPagerAdapter mAdapter;
	    private ActionBar actionBar;
	    // Tab titles
	    private String[] tabs = { "Game List","New Game", "Friends" };
	    ArrayList<String> list;
	    private Adapter ad;
	    ArrayList<String> friendslist;
	    ArrayList<Fragment> fragmentList;
	    FragmentAdapter fragmentAdapter;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.lobby_layout);
	        
	        list = new ArrayList<String>();
	        list.add("first game");
	        list.add("second game");
	        ad = new Adapter(this, list);
	        
	     // Initilization
	        viewPager = (ViewPager) findViewById(R.id.pager);
	        actionBar = getActionBar();
	        
	     //Old Adapter
//	        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), ad);
//	        viewPager.setAdapter(mAdapter);
	        
	     //Create Fragments
	        fragmentList = new ArrayList<Fragment>();
	        fragmentList.add(new GameListFragment());
	        fragmentList.add(new CreateGame());
	        fragmentList.add(new FriendsFragment());
	        
	      //New Adapter
	        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
	      //Set adapter
	        viewPager.setAdapter(fragmentAdapter);
	     
	        
	        
	        actionBar.setHomeButtonEnabled(false);
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
	 

	        for (String tab_name : tabs) {
	            actionBar.addTab(actionBar.newTab().setText(tab_name)
	                    .setTabListener(this));
	        }
	        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	        	 
	            @Override
	            public void onPageSelected(int position) {
	                // on changing the page
	                // make respected tab selected
	                actionBar.setSelectedNavigationItem(position);
	            }
	         
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }
	         
	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        });
	    }
	
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(tab.getPosition());
			
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onComplete(AsyncTaskResult<JSONObject> result) {

			
			
		}
		
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			//This is called by AddFriend Activity when it has added a friend
			
//			   super.onActivityResult(requestCode, resultCode, data);
//			   System.out.println("THIS IS PRINT AND: "+requestCode);
//			   if (requestCode == 1) {
//				   System.out.println(resultCode);
		     if(resultCode == RESULT_OK){      
		    	 viewPager.setAdapter(fragmentAdapter);
		    	 viewPager.setCurrentItem(2);
		    	 Toast.makeText(this, "Friend was added", 2).show();
		     }
		     else if (resultCode == RESULT_CANCELED) {    
		    	 Toast.makeText(this, "Failed to add friend", 2).show();
		         //Write your code if there's no result
		     }
		}

	}
	class FragmentAdapter extends FragmentPagerAdapter {
		 ArrayList<Fragment> list;
		public FragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list=list;
			// TODO Auto-generated constructor stub
		}
		 @Override
		    public Fragment getItem(int index) {
			 	
		      
		 
		        return list.get(index);
		    }
		 
		    @Override
		    public int getCount() {
		        // get item count - equal to number of tabs
		        return  list.size();
		    }
		
	}
	 
//	class TabsPagerAdapter extends FragmentPagerAdapter {
//	Adapter ad;
//	    public TabsPagerAdapter(FragmentManager fm, Adapter ad) {
//	        super(fm);
//	        this.ad=ad;
//	    }
//	 
//	    @Override
//	    public Fragment getItem(int index) {
//	 
//	        switch (index) {
//	        case 0:
//	            // Top Rated fragment activity
////	        	ListFragment create_game = new GamesList();
////	        	create_game.setListAdapter(ad);
////	        	return create_game;
//	        	return new GamesList();
//	        case 1:
////	            // Games fragment activity
//	        	
//	            return new CreateGame();
//	        case 2:
////	            // Movies fragment activity
////	            return new FriendsList();
//	        }
//	 
//	        return null;
//	    }
//	 
//	    @Override
//	    public int getCount() {
//	        // get item count - equal to number of tabs
//	        return 3;
//	    }
//	 
//	}
	 
	class CreateGame extends Fragment {
	 
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	 
	        View rootView = inflater.inflate(R.layout.create_game, container, false);
	         
	        return rootView;
	    }
	}
	
	


