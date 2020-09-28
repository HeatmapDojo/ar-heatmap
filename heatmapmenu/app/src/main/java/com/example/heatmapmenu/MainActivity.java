package com.example.heatmapmenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    String generator, filePath, colorscheme, heatmapName;
    int width, height, tileRatX, tileRatY, columns, rows;
    EditText widthInput, heightInput, tileRatXInput, tileRatYInput, columnsInput, rowsInput,
            colorschemeInput, heatmapInput;
    Button generateButton, filepathButton;
    TextView showFilepath;
    Intent getFile;

    private static final String TAG = "heatmapmenu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the heatmap binary if it isn't initialized already
        File genCheck = new File(getFilesDir().getPath(), "heatmap_gen");
        if(!genCheck.exists()) {
            try {
                InputStream istream = getResources().openRawResource(R.raw.heatmap_gen);
                byte[] buffer = new byte[istream.available()];
                istream.read(buffer);
                istream.close();

                FileOutputStream fostream = openFileOutput("heatmap_gen", MODE_PRIVATE);
                fostream.write(buffer);
                fostream.close();

                File genFile = getFileStreamPath("heatmap_gen");
                genFile.setExecutable(true);
                Log.i(TAG, "Initialized heatmap_gen.\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Initializing EditTexts
        widthInput = (EditText) findViewById(R.id.widthInput);
        heightInput = (EditText) findViewById(R.id.heightInput);
        tileRatXInput = (EditText) findViewById(R.id.tileRatioXInput);
        tileRatYInput = (EditText) findViewById(R.id.tileRatioYInput);
        columnsInput = (EditText) findViewById(R.id.columnsInput);
        rowsInput = (EditText) findViewById(R.id.rowsInput);
        colorschemeInput = (EditText) findViewById(R.id.colorschemeInput);
        heatmapInput = (EditText) findViewById(R.id.heatmapNameInput);

        //Initializing Buttons
        filepathButton = (Button) findViewById(R.id.fileInput);
        generateButton = (Button) findViewById(R.id.generateButton);

        //Initializing TextView
        showFilepath = (TextView) findViewById(R.id.showFile);

        //Initializing heatmap generator
        generator = getFilesDir().getPath() + "/heatmap_gen";

        //Get Filepath
        filepathButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFile = new Intent(Intent.ACTION_GET_CONTENT);
                getFile.setType("*/*");
                getFile = Intent.createChooser(getFile, "Choose Datafile");
                startActivityForResult(getFile, 1);
            }
        });

        //Generate Heatmap
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get the user-entered values
                width = Integer.parseInt(widthInput.getText().toString());
                height = Integer.parseInt(heightInput.getText().toString());
                tileRatX = Integer.parseInt(tileRatXInput.getText().toString());
                tileRatY = Integer.parseInt(tileRatYInput.getText().toString());
                columns = Integer.parseInt(columnsInput.getText().toString());
                rows = Integer.parseInt(rowsInput.getText().toString());
                colorscheme = colorschemeInput.getText().toString();
                heatmapName = heatmapInput.getText().toString();


                //Generate the heatmap from the heatmap_gen
                try {
                    Process p = Runtime.getRuntime().exec(generator + " " + width + " " +
                            height + " " + tileRatX + " " + tileRatY + " " + columns + " " + rows +
                            " " + filePath);
                    //Read the data output from the generator
                    InputStreamReader i = new InputStreamReader(p.getInputStream());
                    FileOutputStream fos = openFileOutput(heatmapName, MODE_PRIVATE);
                    byte line = 0;
                    while ((line = (byte) i.read()) != -1){
                        fos.write(line);
                        fos.flush();
                    }
                    fos.close();
                    Log.i(TAG, "output at " + getFilesDir().getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri fileUri = data.getData();
                    filePath = fileUri.getPath();
                    showFilepath.setText(filePath);
                }
                break;
        }
    }
}