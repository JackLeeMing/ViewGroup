package smartbow.bss.com.androidcustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbow.bss.com.androidcustomview.widget.MyView;

public class MainLActivity extends AppCompatActivity {

    @BindView(R.id.textTest)MyView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_l);
        ButterKnife.bind(this);
        //textView.setText(Html.fromHtml("<font color=\"#ff0000\">红色</font>其它颜色"));
    }
}
