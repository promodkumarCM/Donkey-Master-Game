package com.sparklingapps.cardgamefrog.model;

import com.sparklingapps.cardgamefrog.adapters.MyCardAdapter;

import java.util.ArrayList;

public class CardListAndAdapterModel {

    private ArrayList<Integer> mCardList;
    private MyCardAdapter mAdapter;

    public CardListAndAdapterModel(ArrayList<Integer> mCardList, MyCardAdapter mAdapter) {
        this.mCardList = mCardList;
        this.mAdapter = mAdapter;
    }

    public ArrayList<Integer> getmCardList() {
        return mCardList;
    }

    public void setmCardList(ArrayList<Integer> mCardList) {
        this.mCardList = mCardList;
    }

    public MyCardAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(MyCardAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }
}
