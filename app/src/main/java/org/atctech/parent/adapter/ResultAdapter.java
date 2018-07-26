package org.atctech.parent.adapter;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.atctech.parent.R;
import org.atctech.parent.fragments.ViewBookFragment;
import org.atctech.parent.model.LiveResults;
import org.atctech.parent.utils.ConstantValue;

import java.util.List;

/**
 * Created by Jacky on 3/13/2018.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder>{

    private Context context;
    private List<LiveResults> liveResults;


    public ResultAdapter(Context context, List<LiveResults> liveResults) {
        this.context = context;
        this.liveResults = liveResults;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_result,null,false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, final int position) {

        holder.subjectName.setText(liveResults.get(position).getSubject());
        holder.date.setText(liveResults.get(position).getDate());

        int size = liveResults.get(position).getSize();
        int data = 1024;
        int KB = size/data;
        int MB = KB/data;
        holder.size.setText(MB+" MB");

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DownloadTask downloadTask = new DownloadTask(context);
//                downloadTask.execute("http://www.itp.uzh.ch/~suzanne/ebooks/The%20Web%20Book-A4-HM.pdf");

                String url = ConstantValue.PDF_URL+liveResults.get(position).getFile_name();

                String servicestring = Context.DOWNLOAD_SERVICE;
                DownloadManager downloadmanager;
                downloadmanager  = (DownloadManager)context.getSystemService(servicestring);
                Uri uri = Uri
                        .parse(url);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                long reference = downloadmanager.enqueue(request);
                Toast.makeText(context.getApplicationContext(),
                        "Your file is now downloading...", Toast.LENGTH_LONG).show();

            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewBookFragment viewBookFragment = new ViewBookFragment();
                Bundle bundle = new Bundle();
                bundle.putString("FILE_NAME",liveResults.get(position).getFile_name());
                viewBookFragment.setArguments(bundle);
                Activity activity = (Activity) view.getContext();
                activity.getFragmentManager().beginTransaction().replace(R.id.main_fragment,viewBookFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != liveResults ? liveResults.size() : 0);
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder{

        private TextView subjectName,date,size;
        private Button view,download;

        public ResultViewHolder(View itemView) {
            super(itemView);

            subjectName = itemView.findViewById(R.id.subjectName);
            date = itemView.findViewById(R.id.uploadDate);
            size = itemView.findViewById(R.id.fileSize);
            view = itemView.findViewById(R.id.viewBtn);
            download = itemView.findViewById(R.id.downloadBtn);

        }
    }

//    public class DownloadTask extends AsyncTask<String, Integer, String> {
//
//        private Context context;
//        private PowerManager.WakeLock mWakeLock;
//        private ProgressDialog mProgressDialog;
//
//
//        public DownloadTask(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//
//        @Override
//        protected String doInBackground(String... sUrl) {
//            InputStream input = null;
//            OutputStream output = null;
//            HttpURLConnection connection = null;
//            try {
//                URL url = new URL(sUrl[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.connect();
//
//                // expect HTTP 200 OK, so we don't mistakenly save error report
//                // instead of the file
//                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                    return "Server returned HTTP " + connection.getResponseCode()
//                            + " " + connection.getResponseMessage();
//                }
//
//                // this will be useful to display download percentage
//                // might be -1: server did not report the length
//                int fileLength = connection.getContentLength();
//
//                // download the file
//                input = connection.getInputStream();
//                output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath());
//
//                byte data[] = new byte[4096];
//                long total = 0;
//                int count;
//                while ((count = input.read(data)) != -1) {
//                    // allow canceling with back button
//                    if (isCancelled()) {
//                        input.close();
//                        return null;
//                    }
//                    total += count;
//                    // publishing the progress....
//                    if (fileLength > 0) // only if total length is known
//                        publishProgress((int) (total * 100 / fileLength));
//                    output.write(data, 0, count);
//                }
//            } catch (Exception e) {
//                return e.toString();
//            } finally {
//                try {
//                    if (output != null)
//                        output.close();
//                    if (input != null)
//                        input.close();
//                } catch (IOException ignored) {
//                }
//
//                if (connection != null)
//                    connection.disconnect();
//            }
//            return null;
//        }
//
//
//
//        @Override
//        protected void onProgressUpdate(Integer... progress) {
//            super.onProgressUpdate(progress);
//            // if we get here, length is known, now set indeterminate to false
//
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//
//          if (result != null)
//                Toast.makeText(context,"Download error: "+result, Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(context,"File downloaded", Toast.LENGTH_SHORT).show();
//        }
//
//    }


}
