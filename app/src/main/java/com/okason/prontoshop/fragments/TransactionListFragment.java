package com.okason.prontoshop.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.okason.prontoshop.R;
import com.okason.prontoshop.core.ProntoShopApplication;
import com.okason.prontoshop.core.ShoppingCart;
import com.okason.prontoshop.core.listeners.OnTransactionSelectedListener;
import com.okason.prontoshop.models.Customer;
import com.okason.prontoshop.models.SalesTransaction;
import com.okason.prontoshop.adapter.TransactionAdapter;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionListFragment extends Fragment
        implements OnTransactionSelectedListener{
    private View mRootView;
    private TransactionAdapter mAdapter;


    @BindView(R.id.transaction_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.empty_text) TextView mEmptyTextView;
    @Inject ShoppingCart mCart;
    @Inject Bus mBus;

    public TransactionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_transaction_list, container, false);
        ButterKnife.bind(this, mRootView);
        ProntoShopApplication.getInstance().getAppComponent().inject(this);

        List<SalesTransaction> transactions = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mAdapter = new TransactionAdapter(transactions, this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            mBus.register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            mBus.unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectTransaction(SalesTransaction transaction) {

    }

    @Override
    public Customer getCustomer(long id) {
        //return mPresenter.getCustomerById(id);
        return null;
    }

    public void showSalesTransaction(List<SalesTransaction> transactions) {
        mAdapter.replaceData(transactions);
    }


    public void showEmptyText() {
        mEmptyTextView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }


    public void hideEmptyText() {
        mEmptyTextView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }




    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


}
