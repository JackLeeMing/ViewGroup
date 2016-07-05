package smartbow.bss.com.androidcustomview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.animation.BaseAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smartbow.bss.com.androidcustomview.bean.Data;
import smartbow.bss.com.androidcustomview.utils.TLog;

public class MainLActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)RecyclerView  recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_l);
        ButterKnife.bind(this);

        List<Data>list = new ArrayList<>();
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str2),R.mipmap.ic_red_loudspeaker,Data.TYPE_MIX));
        list.add(new Data(getString(R.string.str3),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str4),R.mipmap.ic_red_loudspeaker,Data.TYPE_TEXT));
        list.add(new Data(getString(R.string.str5),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_red_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str4),R.mipmap.ic_red_loudspeaker,Data.TYPE_TEXT));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_MIX));
        list.add(new Data(getString(R.string.str2),R.mipmap.ic_red_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str8),R.mipmap.ic_red_loudspeaker,Data.TYPE_TEXT));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str2),R.mipmap.ic_red_loudspeaker,Data.TYPE_MIX));
        list.add(new Data(getString(R.string.str2),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str4),R.mipmap.ic_red_loudspeaker,Data.TYPE_MIX));
        list.add(new Data(getString(R.string.str1),R.mipmap.ic_loudspeaker,Data.TYPE_IMAGE));
        list.add(new Data(getString(R.string.str5),R.mipmap.ic_red_loudspeaker,Data.TYPE_TEXT));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MutiSample adapter = new MutiSample(list);

//        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int i) {
//                TLog.error(""+i);
//            }
//        });

        adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                TLog.error("----child");
                TLog.error(((Data)baseQuickAdapter.getItem(i)).name);

            }
        });

        adapter.openLoadAnimation(new BaseAnimation() {
            @Override
            public Animator[] getAnimators(View view) {
                return new Animator[]{
                        ObjectAnimator.ofFloat(view,"scaleY",1,1.1f,1),
                        ObjectAnimator.ofFloat(view,"scaleX",1,1.1f,1)
                };
            }
        });

        recyclerView.setAdapter(adapter);




        //textView.setText(Html.fromHtml("<font color=\"#ff0000\">红色</font>其它颜色"));
    }

    public class RecyclerAdapter extends BaseQuickAdapter<String>{

        public RecyclerAdapter(List<String> data) {
            super(R.layout.recy_item, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {

            baseViewHolder.setText(R.id.string_item,s);


        }

    }



    public class MutiSample extends BaseMultiItemQuickAdapter<Data>{

        public MutiSample(List<Data> data) {
            super(data);
            addItemType(Data.TYPE_IMAGE,R.layout.recy_item);
            addItemType(Data.TYPE_TEXT,R.layout.recy_item);
            addItemType(Data.TYPE_MIX,R.layout.recy_item);
        }

        @Override
        protected void convert(BaseViewHolder holder, Data data) {
            switch (holder.getItemViewType()){
                case Data.TYPE_IMAGE:
                    holder.setImageResource(R.id.image_item,data.image).getView(R.id.string_item).setVisibility(View.GONE);
                    holder.setOnClickListener(R.id.image_item,new OnItemChildClickListener());
                    break;
                case Data.TYPE_TEXT:
                    holder.setText(R.id.string_item,data.name).getView(R.id.image_item).setVisibility(View.GONE);


                    break;
                case Data.TYPE_MIX:
                    holder.setText(R.id.string_item,data.name).setImageResource(R.id.image_item,data.image);
                    break;
            }

        }
    }


}
