package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnreserve;
    Button btnreset;
    EditText name;
    EditText num;
    EditText size;
    CheckBox chkSmoke;
    EditText etDay;
    EditText etTime;


    final Calendar now = Calendar.getInstance();
    int cY = now.get(Calendar.YEAR);
    int cM = now.get(Calendar.MONTH);
    int cD = now.get(Calendar.DAY_OF_MONTH);

    int cH = now.get(Calendar.HOUR_OF_DAY);
    int cMin = now.get(Calendar.MINUTE);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnreserve = findViewById(R.id.buttonReserve);
        btnreset = findViewById(R.id.buttonReset);
        name = findViewById(R.id.editTextName);
        num = findViewById(R.id.editTextNumber);
        size = findViewById(R.id.editTextSize);
        chkSmoke = findViewById(R.id.checkBoxSmoke);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);



        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etDay.setText("Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                        cY = year;
                        cM = month;
                        cD = dayOfMonth;


                    }
                };
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, myDateListener, (now.get(Calendar.YEAR)), (now.get(Calendar.MONTH)), (now.get(Calendar.DAY_OF_MONTH)));
                myDateDialog.updateDate(cY, cM, cD);
                myDateDialog.show();
            }
        });


        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                        cH = hourOfDay;
                        cMin = minute;
                    }
                };
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, (now.get(Calendar.HOUR_OF_DAY)), (now.get(Calendar.MINUTE)), true);
                myTimeDialog.updateTime(cH, cMin);
                myTimeDialog.show();

            }
        });


        btnreserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                final EditText eTname = findViewById(R.id.editTextName);

                final EditText eTsize = findViewById(R.id.editTextSize);
                final CheckBox eTsmoke = findViewById(R.id.checkBoxSmoke);

                myBuilder.setTitle("Confirm your order");
                String name = eTname.getText().toString();
                String confirm = "";
                if(eTsmoke.isChecked()){
                    confirm = "YES";
                }
                else{
                    confirm = "NO";
                }
                String size = eTsize.getText().toString();
                String day = etDay.getText().toString();
                String time = etTime.getText().toString();


                myBuilder.setMessage("New Reservation" + "\n" + "Name: " + name + "\n" + "Smoking: " + confirm + "\n" + "Size: " + size + "\n" + day + "\n" + time);
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("CONFIRM", null);

                myBuilder.setNegativeButton("CANCEL", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });




        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR);




                etDay.setText("Date: " + date);

                String time = now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                etTime.setText("Time: " + time);

                SharedPreferences.Editor prefEdit = prefs.edit();

                prefEdit.clear().commit();


            }
        });



    }

    @Override
    protected void onPause() {
        super.onPause();
        String day = etDay.getText().toString();
        String time = etTime.getText().toString();
        String eTname = name.getText().toString();
        String eTNum = num.getText().toString();
        String eTSize = size.getText().toString();

        Boolean confirm;
        if(chkSmoke.isChecked()){
            confirm = true;
        }
        else{
            confirm = false;
        }


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("Day", day);
        prefEdit.putString("Time", time);
        prefEdit.putString("Num", eTNum);
        prefEdit.putString("Size", eTSize);
        prefEdit.putBoolean("smoke", confirm);
        prefEdit.putString("Name", eTname);


        prefEdit.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String day = prefs.getString("Day", "");
        String time = prefs.getString("Time", "");
        String Name = prefs.getString("Name", "");
        String Num = prefs.getString("Num", "");
        String eTsize = prefs.getString("Size", "");
        Boolean smoke = prefs.getBoolean("smoke", false);


        etTime.setText(time);
        etDay.setText(day);
        name.setText(Name);
        num.setText(Num);
        size.setText(eTsize);
        chkSmoke.setChecked(smoke);

    }
}
