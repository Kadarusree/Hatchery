package shiva.com.hatchery.feedingData;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import shiva.com.hatchery.Constants;
import shiva.com.hatchery.R;

public class DailyFeedingData extends AppCompatActivity {
    EditText date, initials;

    TextView Header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_feeding_data);

        date = findViewById(R.id.fd_date);
        initials = findViewById(R.id.fd_initials);
        Header = findViewById(R.id.DFD_Header);

        final Calendar currentDate = Calendar.getInstance();
        final Calendar date_ = Calendar.getInstance();

        DateFormat fmt = new SimpleDateFormat("MMMM dd/ yyyy", Locale.US);
        date.setText(fmt.format(date_.getTime()));
        initials.setText(Constants.username);
        initials.setEnabled(false);

        Header.setText("Tank Number : "+Constants.TANK_NUMBER+" ");

    }
}
