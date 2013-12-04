package com.quizzingbricks.activities.answerQuestion;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.quizzingbricks.R;
import com.quizzingbricks.communication.apiObjects.GamesThreadedAPI;
import com.quizzingbricks.communication.apiObjects.OnTaskCompleteAsync;
import com.quizzingbricks.tools.AsyncTaskResult;
import com.quizzingbricks.tools.SimplePopupWindow;
 
public class QuestionPromptActivity extends ListActivity implements OnTaskCompleteAsync {
	private int gameId;
	private CountDownTimer aCounter;
    
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gameId = getIntent().getIntExtra("gameID", 0); 
        new GamesThreadedAPI(this).getQuestion(gameId, this);
    }
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
    	new GamesThreadedAPI(this).sendAnswer(gameId, position-1, this);
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

			int whiteSpacePadding = 20;

			TextView textView = new TextView(this);
			textView.setTextSize(18);
			textView.setText(question);
			textView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(textView, question, false);
			
			final TextView counterTextView = new TextView(this);
			counterTextView.setTextSize(18);
			counterTextView.setPadding(whiteSpacePadding, whiteSpacePadding, whiteSpacePadding, whiteSpacePadding+20);
			listView.addHeaderView(counterTextView, R.string.counter_start, false);
			setListAdapter(md);
			
			final Context contextForTimer = this;
			aCounter = new CountDownTimer(10000 , 1000) {
			    public void onTick(long millisUntilFinished) {
			    	long secondsLef = millisUntilFinished / 1000;
			    	if(secondsLef == 3)	{
			    		counterTextView.setTextColor(new Color().RED);
			    	}
			        counterTextView.setText("Seconds remaining: " + millisUntilFinished / 1000);
			    }

			    public void onFinish() {
			    	counterTextView.setText("Seconds remaining: 0");
			    	new SimplePopupWindow(contextForTimer).createPopupWindowWithResult("Time exceded", 
							getApplicationContext().getString(R.string.question_time_exceded),
							RESULT_CANCELED);
			    }
			};
			aCounter.start();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void handleAnswer(JSONObject answerObject)	{
		try	{
			String isAnswerCorrect = answerObject.getString("isCorrect");
			aCounter.cancel();
			if(isAnswerCorrect.equals("true"))	{ 
				new SimplePopupWindow(this).createPopupWindowWithResult("Correct Answer", 
						getApplicationContext().getString(R.string.correct_answer),
						RESULT_OK);
			}
			else if(isAnswerCorrect.equals("false"))	{
				new SimplePopupWindow(this).createPopupWindowWithResult("Incorrect Answer", 
						getApplicationContext().getString(R.string.incorrect_answer),
						RESULT_CANCELED);
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