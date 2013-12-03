package com.quizzingbricks.activities.answerQuestion;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quizzingbricks.R;


import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;
import com.quizzingbricks.tools.SimplePopupWindow;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
 
public class QuestionPromptActivity extends ListActivity implements OnTaskCompleteAsync {
	private int gameId;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.questionprompt);
//        md = new QuestionPromptAdapter(this, new ArrayString[] {  "Question","Answer 1","Answer 2","Answer 3","Answer 4" });
//		setListAdapter(md);
        
//      gameId = getIntent().getExtras().getInt("id");
//		GamesThreadedAPI gt = new GamesThreadedAPI(this);
//		gt.getQuestion(gameId, this);
        this.gameId = 0; //Remove this later
        new GamesThreadedAPI(this).getQuestion(gameId, this);
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	new GamesThreadedAPI(this).sendAnswer(gameId, position, this);
	}
	@Override
	public void onComplete(AsyncTaskResult<JSONObject> result) {
		if(result.hasException())	{
			result.getException().printStackTrace();
		}
		else if(result.getResult().has("question"))	{
			handleQuestion(result.getResult());
		}
		else if(result.getResult().has("isCorrect"))	{
			handleAnswer(result.getResult());
		}
		
	}
	
	private void handleQuestion(JSONObject questionObject)	{
		try {
			ArrayList<String> questionAlternatives = new ArrayList<String>();
			JSONArray jsonAlternatives = questionObject.getJSONArray("alternatives");
			for(int i=0; i<=jsonAlternatives.length()-1; i++)	{
				questionAlternatives.add(jsonAlternatives.getString(i));
			}
			String question = questionObject.getString("question");
			QuestionPromptAdapter md = new QuestionPromptAdapter(this, questionAlternatives, question);
			ListView listView = getListView();
			TextView textView = new TextView(this);
			textView.setTextSize(18);
			textView.setText(question);
			int whiteSpacePadding = 20;
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, question, false);
			setListAdapter(md);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void handleAnswer(JSONObject answerObject)	{
		System.out.println("Yay handling questions");
		try	{
			String isAnswerCorrect = answerObject.getString("isCorrect");
			if(isAnswerCorrect.equals("true"))	{ 
				new SimplePopupWindow(this).createPopupWindow("Correct Answer", getApplicationContext().getString(R.string.correct_answer));
			}
			else if(isAnswerCorrect.equals("false"))	{
				new SimplePopupWindow(this).createPopupWindow("Incorrect Answer", getApplicationContext().getString(R.string.incorrect_answer));
			}
			else	{
				new SimplePopupWindow(this).createPopupWindow("Error", getApplicationContext().getString(R.string.unknow_server_answer));
			}
		}
		catch (Exception e)	{
			e.printStackTrace();
		}
	}
}