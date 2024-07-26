package com.brenomotta.myfinances.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brenomotta.myfinances.databinding.RowAccountBinding
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.ui.viewholder.AccountViewHolder

class AccountAdapter : RecyclerView.Adapter<AccountViewHolder>() {

    private var listAccount: List<AccountModel> = arrayListOf()
    private lateinit var listener: RecyclerListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = RowAccountBinding.inflate(inflater, parent, false)
        return AccountViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bindData(listAccount[position])
    }

    override fun getItemCount(): Int {
        return listAccount.count()
    }

    fun updateTransactions(list: List<AccountModel>) {
        listAccount = list
        notifyDataSetChanged()
    }

    fun attachListener(listener: RecyclerListener) {
        this.listener = listener
    }
}