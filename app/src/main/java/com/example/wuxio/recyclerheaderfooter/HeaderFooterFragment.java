package com.example.wuxio.recyclerheaderfooter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import tech.threekilogram.overscroll.HeaderFooterLayout;

/**
 * @author wuxio 2018-04-08:13:12
 */
public class HeaderFooterFragment extends Fragment {

    protected View               rootView;
    protected RecyclerView       mRecycler;
    protected HeaderFooterLayout mHeaderFooter;
    private   MainAdapter        mAdapter;


    @SuppressWarnings("UnnecessaryLocalVariable")
    public static HeaderFooterFragment newInstance() {

        HeaderFooterFragment fragment = new HeaderFooterFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_header_footer, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View rootView) {

        mRecycler = rootView.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MainAdapter();
        mRecycler.setAdapter(mAdapter);

        mHeaderFooter = rootView.findViewById(R.id.headerFooter);
        mHeaderFooter.setHeader(R.layout.item_main_recycler);
        mHeaderFooter.setFooter(R.layout.item_main_recycler);

        mHeaderFooter.setOverScrollListener(new HeaderFooterLayout.OnOverScrollListener() {
            @Override
            public void onScrollOverTop(View header, int scrollY) {

                ((TextView) header).setText(String.valueOf(scrollY));
                if (scrollY < -200) {
                    mHeaderFooter.stopScrollBack();
                }
            }


            @Override
            public void onOverTopTouchUp(View header, int scrollY) {

                ((TextView) header).setText(" refreshing ");

                if (scrollY < -200) {

                    int dy = scrollY - -200;
                    mHeaderFooter.scrollBack(-dy);
                }

                mHeaderFooter.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mHeaderFooter.scrollBack();
                    }
                }, 3000);
            }


            @Override
            public void onScrollOverBottom(View footer, int scrollY) {

                ((TextView) footer).setText(String.valueOf(scrollY));
                mHeaderFooter.stopScrollBack();
            }


            @Override
            public void onOverBottomTouchUp(View footer, int scrollY) {

                if (scrollY < 200) {
                    mHeaderFooter.scrollBack();
                    return;
                }

                if (scrollY > 200) {
                    int dy = scrollY - 200;
                    mHeaderFooter.scrollBack(-dy);
                    ((TextView) footer).setText(" refreshing ");
                }

                mHeaderFooter.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        List< Integer > list = mAdapter.getList();

                        final int size = list.size();

                        for (int i = 0; i < 10; i++) {
                            list.add(size + i);
                        }
                        mAdapter.notifyDataSetChanged();

                        mHeaderFooter.reLayout();
                    }
                }, 3000);
            }
        });

    }

    //============================ recycler need ============================

    private class MainAdapter extends RecyclerView.Adapter< MainTextHolder > {

        private LayoutInflater mInflater;

        List< Integer > mList = new ArrayList<>();

        {
            for (int i = 0; i < 50; i++) {
                mList.add(i);
            }

        }

        public List< Integer > getList() {

            return mList;
        }


        @NonNull
        @Override
        public MainTextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (mInflater == null) {
                mInflater = LayoutInflater.from(parent.getContext());
            }

            View view = mInflater.inflate(R.layout.item_main_recycler, parent, false);
            return new MainTextHolder(view);
        }


        @Override
        public void onBindViewHolder(@NonNull MainTextHolder holder, int position) {

            holder.bind(mList.get(position));
        }


        @Override
        public int getItemCount() {

            return mList.size();
        }
    }

    private class MainTextHolder extends RecyclerView.ViewHolder {


        private final TextView mTextView;


        public MainTextHolder(View itemView) {

            super(itemView);
            mTextView = (TextView) itemView;
        }


        public void bind(int position) {

            String s = String.format(Locale.CHINA, "Item %d", position);
            mTextView.setText(s);
        }
    }
}
