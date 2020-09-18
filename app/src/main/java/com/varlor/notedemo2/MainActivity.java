package com.varlor.notedemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etext ;
    private ImageButton btn_save;
    private ImageButton btn_cancel;
    File file;//声明一个文件对象，用来指定外部存储文件的
    //全局字节数组
    private byte[] buffer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etext = findViewById(R.id.editText);
        btn_save = findViewById(R.id.btn_save);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        file = new File(Environment.getExternalStorageDirectory(),
                "Text.txt");
        //读取保存的文件信息
        //声明文件输入流对象
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            //实例化字节数组
            buffer = new byte[fis.available()];
            fis.read(buffer);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fis!=null){
                try {
                    fis.close();
                    //把字节数组中的数据转换为字符串
                    String data = new String(buffer);
                    //显示读取的内容
                    etext.setText(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }}
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                /**
                 * 保存填写的备忘录信息
                 */
                //声明文件输出流
                FileOutputStream fos = null;
                //获取输入的备忘内容
                String text = etext.getText().toString();
                try {
                    /**
                     * 获得文件输出流对象
                     */
                    fos = new FileOutputStream(file);
                    fos.write(text.getBytes());
                    fos.flush();//清除缓存

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (fos!=null){
                        //关闭输出流
                        try {
                            fos.close();
                            Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}
                }
                break;
            case R.id.btn_cancel:
                //退出应用
                finish();
                break;

        }
    }
}