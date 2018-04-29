package com.example.ortel.tagnet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.ACCESSIBILITY_SERVICE;
import static com.example.ortel.tagnet.MainActivityOld.userInfoHashmap;

public class ImageAdapter extends BaseAdapter {
    private InstagramApp mApp;
    private ListView lvRelationShipAllUser;
    public ArrayList<HashMap<String, String>> usersInfo;
    private ArrayList<HashMap<String, String>> usersInfo1;
    private ArrayList<HashMap<String, String>> usersInfoOne;

    private List<String> originalData = null;
    private ArrayList<HashMap<String, String>> filteredData = null;
    private LayoutInflater mInflater;
    private ItemFilter mFilter = new ItemFilter();
    private LayoutInflater inflater;
    private LayoutInflater inflater1;
    private ArrayList<HashMap<String, String>> originalUsersInfo;

    //lvRelationShipAllUser = (ListView) findViewById(R.id.lvRelationShip);
    Context context;

    public ImageAdapter(Context context){inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater1 = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d("OkHttp", "debug4");
        this.context = context;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = inflater.inflate(R.layout.nav_header_main, null);
        Log.d("usersInfonlist", "ImHere");
        Log.d("OkHttpCONTEXT", String.valueOf(context));
        final Holder holder = new Holder();
        holder.ivPhoto = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(context)
                .load(userInfoHashmap.get(InstagramApp.TAG_PROFILE_PICTURE))
                .into(holder.ivPhoto);
        return view;
    }




    private class Holder {
        private ImageView ivPhoto;
        private TextView tvFullName;
        private TextView tvFullName1;
        private Button button;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return usersInfo.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("OkHttpNList", "here1");
            //MAKE AT THE BEGINING
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            ArrayList<HashMap<String, String>> list = usersInfoOne;

            int count = list.size();
            ArrayList<HashMap<String, String>> nlist = new ArrayList(count);

            String filterableString;
            Log.d("OkHttpNList2", String.valueOf(list));

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).get("username");
                Log.d("OkHttpNList", String.valueOf(filterableString));


                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }
            Log.d("OkHttpNList", String.valueOf(nlist));

            results.values = nlist;


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            usersInfo = (ArrayList<HashMap<String, String>>) results.values;
            Log.d("OkHttpNList", String.valueOf(usersInfo));

            notifyDataSetChanged();
        }

    }

    private void connectOrDisconnectUser(final int position) {
        Log.d("Testingi", String.valueOf(usersInfo.size()));
        Log.d("Testingi", String.valueOf(usersInfo));




    }
}

