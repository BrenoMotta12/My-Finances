package com.brenomotta.myfinances.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brenomotta.myfinances.databinding.RowTransactionBinding
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.ui.viewholder.TransactionViewHolder

class TransactionAdapter : RecyclerView.Adapter<TransactionViewHolder>() {

    private var listTransaction: List<TransactionModel> = arrayListOf()
    private lateinit var listener: RecyclerListener
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(itemBinding, listener, context)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindData(listTransaction[position])
    }

    override fun getItemCount(): Int {
        return listTransaction.count()
    }

    fun update(list: List<TransactionModel>) {
        listTransaction = list
        notifyDataSetChanged()
    }


    fun attachListener(listener: RecyclerListener) {
        this.listener = listener
    }

    fun attachContext(context: Context) {
        this.context = context
    }
}