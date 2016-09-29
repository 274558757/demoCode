package com.example.commen.baseprogress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.example.commen.R;


/**
 * 作者： WangZL on 2016/8/26 0026.
 */
public class NetworkProgressDialog {
    private Context context;
    private Dialog mDialog;
    private CanceledListener listener;
    public NetworkProgressDialog(Context context){
        this.context=context;
        initDialog();
    }

    private void initDialog(){
        mDialog=new Dialog(context, R.style.custom_dialog);
        View mView= LayoutInflater.from(context).inflate(R.layout.dialog_network_progress,null);
        mDialog.setContentView(mView);
       /*点击外面不消失*/
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (listener!=null){
                    listener.onCancel(dialog);
                }
            }
        });

    }


    public void show(){
        mDialog.show();

    }

    public void dismiss(){
        mDialog.dismiss();
    }

    public Dialog getDialog(){
        return  mDialog;
    }

    public void setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    public boolean isShowing(){
        return mDialog.isShowing();
    }


    public void setCanceledListeener(CanceledListener listeener){
        this.listener=listeener;
    }

    public interface  CanceledListener{
        void onCancel(DialogInterface dialog);
    }

}
