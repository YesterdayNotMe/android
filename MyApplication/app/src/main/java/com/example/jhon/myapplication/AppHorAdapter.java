package com.example.jhon.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.util.List;

public class AppHorAdapter extends RecyclerView.Adapter<AppHorAdapter.ViewHolder> {

    private Context mContext;
    private List<AppHor> mAppHorList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appImg;
        TextView appName;
        TextView appSize;
        Button download ;
        ProgressBar progressBar ;

        public ViewHolder(View itemView) {
            super(itemView);
            appImg = itemView.findViewById(R.id.app_img_hor);
            appName = itemView.findViewById(R.id.app_name_hor);
            appSize = itemView.findViewById(R.id.app_size_hor);
            download = itemView.findViewById(R.id.btn_down) ;
            progressBar = itemView.findViewById(R.id.pb) ;
        }
    }

    public AppHorAdapter(List<AppHor> appHorList) {
        mAppHorList = appHorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item_hor, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final AppHor appHor = mAppHorList.get(position);

        Glide.with(mContext).load(appHor.getImageUrl()).skipMemoryCache(false).into(holder.appImg);
        holder.appName.setText(appHor.getName());
        holder.appSize.setText(appHor.getSize());

        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseDownloadTask task = createDownloadTask(appHor, holder);
                task.setForceReDownload(true) ;
                task.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppHorList.size();
    }

    public BaseDownloadTask createDownloadTask(AppHor entity, final ViewHolder holder) {
        String path = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "tmpdir1" + File.separator +
                "weichao.apk" ;
        BaseDownloadTask task = FileDownloader.getImpl().create(entity.getDownloadUrl())
                 .setPath(path, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(holder)
                .setListener(new FileDownloadSampleListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        holder.download.setVisibility(View.GONE);
                        holder.progressBar.setVisibility(View.VISIBLE);
                        //((ViewHolder) task.getTag()).updatePending(task);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
//                        ((ViewHolder) task.getTag()).updateProgress(soFarBytes, totalBytes,
//                                task.getSpeed());
                        if (totalBytes == -1) {
                            // chunked transfer encoding data
                            holder.progressBar.setIndeterminate(true);
                        } else {
                            holder.progressBar.setMax(totalBytes);
                            holder.progressBar.setProgress(soFarBytes);
                        }
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        // ((ViewHolder) task.getTag()).updateError(e, task.getSpeed());
                        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                        holder.progressBar.setVisibility(View.GONE);
                        holder.download.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        //((ViewHolder) task.getTag()).updateConnected(etag, task.getFilename());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        //((ViewHolder) task.getTag()).updatePaused(task.getSpeed());
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        //((ViewHolder) task.getTag()).updateCompleted(task);
                        Log.e("weichao", "complete " + task.getTargetFilePath());
                        install(task.getTargetFilePath()) ;
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        //((ViewHolder) task.getTag()).updateWarn();
                    }
                });

        return task;
    }

    private void install(String filePath) {
        File apkFile = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(
                    mContext
                    , "com.example.jhon.myapplication.fileprovider"
                    , apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
    }

}
