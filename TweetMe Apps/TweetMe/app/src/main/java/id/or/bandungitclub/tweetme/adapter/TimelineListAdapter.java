package id.or.bandungitclub.tweetme.adapter;

import id.or.bandungitclub.tweetme.R;
import id.or.bandungitclub.tweetme.model.Timeline;
import id.or.bandungitclub.tweetme.utils.Singleton;

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineListAdapter extends BaseAdapter {

	private Activity activity;
	private Vector<Timeline> data;
	private LayoutInflater inflater = null;
	
	public TimelineListAdapter(Activity a, Vector<Timeline> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_row_timeline, null);

		TextView username = (TextView) vi.findViewById(R.id.tvRowTimelineUsername);
		TextView tweetMsg = (TextView) vi.findViewById(R.id.tvRowTimelineTweetMsg);
		TextView tweetDate = (TextView) vi.findViewById(R.id.tvRowTimelineTweetDate);
		ImageView ivReplyTweet = (ImageView) vi.findViewById(R.id.ivReplyTweet);
		ImageView ivRetweet = (ImageView) vi.findViewById(R.id.ivRetweet);
		
		Timeline tl = new Timeline();
		tl = data.get(position);

		// Setting all values in listview	
		/*if(Singleton.getInstance().getStringPreferences(activity, "uid").equals(tl.getUserId())){
			username.setText("@" + tl.getUsername());			
		} else {
			username.setText("Retweet @" + tl.getUsername());
		}*/
		username.setText("@" + tl.getUsername());
		tweetMsg.setText(tl.getTweetMsg());
		tweetDate.setText(tl.getTweetDate());
		
		ivReplyTweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Reply tweet", Toast.LENGTH_SHORT).show();				
			}
		});
		
		ivRetweet.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "Retweet", Toast.LENGTH_SHORT).show();				
			}
		});
		
		return vi;
	}

}
