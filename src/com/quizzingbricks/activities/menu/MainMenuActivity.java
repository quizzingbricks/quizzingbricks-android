package com.quizzingbricks.activities.menu;
import java.util.ArrayList;

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
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.quizzingbricks.R;
import com.quizzingbricks.authentication.AuthenticationManager;
import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.LobbyThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.communication.apiObjects.UserThreadedAPI;
import com.quizzingbricks.tools.AsyncTaskResult;

public class MainMenuActivity extends FragmentActivity implements ActionBar.TabListener, OnTaskCompleteAsync{
		private ViewPager viewPager;
//	    private TabsPagerAdapter mAdapter;
	    private ActionBar actionBar;
	    // Tab titles
	    private String[] tabs = { "Games","Lobbys", "Friends" };
	    ArrayList<String> list;
	    private Adapter ad;
	    ArrayList<String> friendslist;
	    ArrayList<Fragment> fragmentList;
	    FragmentAdapter fragmentAdapter;
	    LobbyFragment lobbyfragment;
	    GameListFragment gamelistfragment;
	    FriendsFragment friendsfragment;
	    
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
	        gamelistfragment = new GameListFragment();
	        fragmentList.add(gamelistfragment);
//	        fragmentList.add(new CreateGame());
	        lobbyfragment = new LobbyFragment();
	        fragmentList.add(lobbyfragment);
	        friendsfragment = new FriendsFragment();
	        fragmentList.add(friendsfragment);
	        
	      //New Adapter
	        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragmentList);
	      //Set adapter
	        viewPager.setAdapter(fragmentAdapter); 
	     
	        
	        actionBar.setTitle("Home");
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
	    
	    public void onLogoutPressed(MenuItem view)	{
			new AuthenticationManager(this).logout();
		}
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu items for use in the action bar
	        getMenuInflater().inflate(R.menu.main_menu_activity, menu);
	        return super.onCreateOptionsMenu(menu);
	    }
	
		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			System.out.println("ON TAB RESELECTED");
			int whattab = arg0.getPosition();
			if (whattab==1) {
				LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(this);
				lobbyThreadedAPI.getGameLobbies(lobbyfragment);
			} else if (whattab==2) {
				UserThreadedAPI userThreadedAPI = new UserThreadedAPI(this);
				 userThreadedAPI.getFriendsList(friendsfragment);
			} else if (whattab==0) {
				GamesThreadedAPI lt = new GamesThreadedAPI(this);
				lt.getActiveGames(gamelistfragment);
			}

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
		
			//If you need to take results from different fragments, use different requestCodes
			System.out.println(requestCode);
			 if (requestCode == 196609) {
			     if(resultCode == RESULT_OK){
//			    	 viewPager.setAdapter(fragmentAdapter);
//			    	 viewPager.setCurrentItem(2);
			    	 UserThreadedAPI userThreadedAPI = new UserThreadedAPI(this);
					 userThreadedAPI.getFriendsList(friendsfragment);
			    	 Toast.makeText(this, "Friend was added", 2).show();
			    	
			     }
			     else if (resultCode == RESULT_CANCELED) {    
			    	 try {
			    		 if (data.getBooleanExtra("back", false)) {
				    		 Toast.makeText(this, "Friend not found Or already exists", 2).show();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
			    	 
			     }
			 } else if (requestCode == 131073) {
			     if(resultCode == RESULT_OK){      
			    	 
//			    	 ArrayList<Fragment> fragmentLists = new ArrayList<Fragment>();
//			    	 fragmentLists.add(new GameListFragment());
////				        fragmentList.add(new CreateGame());
//			    	 fragmentLists.add(new LobbyFragment());
//			    	 fragmentLists.add(new FriendsFragment());
//			    	 FragmentAdapter fragmentAdapters = new FragmentAdapter(getSupportFragmentManager(), fragmentLists);
//			    	 viewPager.setAdapter(fragmentAdapters);
//			    	 viewPager.setCurrentItem(1);
			    	 LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(this);
			    	 lobbyThreadedAPI.getGameLobbies(lobbyfragment);
			    	 Toast.makeText(this, "Game created", 2).show();
			     }
			     else if (resultCode == RESULT_CANCELED) {    
			    	 Toast.makeText(this, "Could not make game", 2).show();
			         //Write your code if there's no result
			     }
			 } else if (requestCode == 131074) {
			     if(resultCode == RESULT_OK){     
			    	 LobbyThreadedAPI lobbyThreadedAPI = new LobbyThreadedAPI(this);
			    	 lobbyThreadedAPI.getGameLobbies(lobbyfragment);
			    	 Toast.makeText(this, "Game started", 2).show();
			     }
			 }
		}
		@Override
		public void onBackPressed() {
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
