package com.example.unidad5_casopractico1fecha;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class a {

        private void InitDatePicker() {
            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month +1;
                    String date = makeDataString(day, month, year);
                }
            };

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            int style = AlertDialog.THEME_HOLO_LIGHT;

            datePickerDialog = new datePickerDialog(this, style, dateSetListener, year, month, day);

        }


        private String makeDataString(int day, int month, int year) {
            return day + " " + getMonthFormat(month) + " " + year;
        }

        private String getMonthFormat(int month) {
            if(month == 1)
                return "Enero";
            if(month == 2)
                return "Febrero";
            if(month == 3)
                return "Marzo";
            if(month == 4)
                return "Abril";
            if(month == 5)
                return "Mayo";
            if(month == 6)
                return "Junio";
            if(month == 7)
                return "Julio";
            if(month == 8)
                return "Agosto";
            if(month == 9)
                return "Septiembre";
            if(month == 10)
                return "Octubre";
            if(month == 11)
                return "Noviembre";
            if(month == 12)
                return "Diciembre";
            return "Enero";
        }

        public void openDatePicker(View view) {
            datePickerDialog.show();
        }
}
