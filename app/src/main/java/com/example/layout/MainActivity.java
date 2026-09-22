package com.example.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Xml;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button btnXml, btnJson;
    TextView txtXml, txtJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnXml = findViewById(R.id.btnXml);
        btnJson = findViewById(R.id.btnJson);

        txtXml = findViewById(R.id.txtXml);
        txtJson = findViewById(R.id.txtJson);

        btnXml.setOnClickListener(v -> parseXML());
        btnJson.setOnClickListener(v -> parseJSON());
    }

    private void parseXML() {

        try {

            InputStream inputStream = getResources().openRawResource(R.raw.student_xml);

            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();

            String name = "";
            String department = "";
            String age = "";

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tag = parser.getName();

                switch (eventType) {

                    case XmlPullParser.START_TAG:

                        if ("name".equals(tag)) {
                            parser.next();
                            name = parser.getText();
                        }

                        if ("department".equals(tag)) {
                            parser.next();
                            department = parser.getText();
                        }

                        if ("age".equals(tag)) {
                            parser.next();
                            age = parser.getText();
                        }

                        break;
                }

                eventType = parser.next();
            }

            txtXml.setText("Name : " + name +
                    "\nDepartment : " + department +
                    "\nAge : " + age);

        } catch (Exception e) {
            txtXml.setText(e.getMessage());
        }
    }

    private void parseJSON() {

        try {

            InputStream is = getResources().openRawResource(R.raw.student_xml);

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            JSONObject object = new JSONObject(builder.toString());

            String name = object.getString("name");
            String department = object.getString("department");
            int age = object.getInt("age");

            txtJson.setText("Name : " + name +
                    "\nDepartment : " + department +
                    "\nAge : " + age);

        } catch (Exception e) {
            txtJson.setText(e.getMessage());
        }
    }
}