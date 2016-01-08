package com.example.tebakkata;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Hangman extends Activity {
	
	ImageView imageChance;
	TextView textViewHiddenWord, textViewCategory;
	String[] arrayCategory;
	char arrayHiddenWord[];
	int tries;
	int maxTries = 7;
	char arrayClue[];
	
	private static final int[] imageResource = new int[]
	{
		R.drawable.image_07, R.drawable.image_06, R.drawable.image_05, R.drawable.image_04,
		R.drawable.image_03, R.drawable.image_03, R.drawable.image_02, R.drawable.image_01, R.drawable.image_00
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hangman_layout);
		
		imageChance = (ImageView) findViewById(R.id.imageChance);
		imageChance.setImageResource(R.drawable.image_07);
		
		Intent it = getIntent();
		int index = it.getIntExtra("index", 0);
		arrayCategory = getResources().getStringArray(R.array.category);
		
		textViewCategory = (TextView) findViewById(R.id.textViewCategory);
		textViewCategory.setText (arrayCategory[index]);
		
		textViewHiddenWord = (TextView) findViewById(R.id.textViewHiddenWord);
		textViewHiddenWord.setText("_");
		
		listwords = getResources().getStringArray(category[index]);
		hiddenWord = listwords[(int)Math.floor(Math.random() * listwords.length)].toUpperCase();
		
		arrayHiddenWord = new char [hiddenWord.length()];
		arrayClue = new char[hiddenWord.length()];
		
		arrayHiddenWord = hiddenWord.toCharArray();


//		for (int i; i < arrayHiddenWord.length; i++)
//		{
//			if (arrayHiddenWord[i]!=" ")
//			{
//				arrayClue[i]="_";
//			}
//		}
		
	}
	
	public void onClickLetter (View v)
	{
		String key = v.getTag().toString();
		v.setEnabled(false);
		textViewHiddenWord.setText(key);
	}
	
	private String hiddenWord = "";
	
	private String[] listwords = {};
	
	private static final int[] category = new int[]
	{
		
	};
	
	public String printClue (char[] a)
	{
		String stringClue = "";
		
		return stringClue;
	}
	
	public void onBackPressed()
	{
		
	}

}
