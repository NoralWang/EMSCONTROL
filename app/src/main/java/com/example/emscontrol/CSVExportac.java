package com.example.emscontrol;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.emscontrol.Foo;
import com.xpacer.csvexport.CsvExport;
import com.xpacer.csvexport.OnCsvExportResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVExport extends AppCompatActivity implements OnCsvExportResult {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csvexport);

        Button exportBtn = findViewById(R.id.btn);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export();
            }
        });


    }

    void export() {
        List<Foo> foos = new ArrayList<>();
        Foo foo1 = new Foo();
        foo1.bar = "hhh 1";
        foo1.man = "mmm 1";

        Foo foo2 = new Foo();
        foo2.bar = "hhh 2";
        foo2.man = "Man 2";

        foos.add(foo1);
        foos.add(foo2);

        String directory = Environment.getExternalStorageDirectory() + "/downloads";

        try {
            CsvExport<Foo> export = new CsvExport<>();
            export.with(this)
                    .setDirectory(directory)
                    .setFileName("Datacsv.csv")
                    .setList(foos)
                    .setStorageWritePermissionRequestCode(1000)
                    .setExportResult(this)
                    .export();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            export();
        }
    }

    @Override
    public void onExportSuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onExportError(String errorMessage) {
        Toast.makeText(this, "Failed. Error Message: ".concat(errorMessage), Toast.LENGTH_LONG).show();
    }
}
