package first.winning.com.xutilscenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import first.winning.com.lrouter_annotation.BindView;
import first.winning.com.lrouter_api.InjectHelper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv1)
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectHelper.inject(this);
        tv1.setText("你好啊，annotationParse");
    }
    private void click(View view){
        Toast.makeText(this, view.getId()+"......", Toast.LENGTH_SHORT).show();
    }

}
