package com.brenomotta.myfinances.ui.viewholder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.RowTransactionBinding
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.TransactionModel
import com.brenomotta.myfinances.service.util.FinancesFormatter

class TransactionViewHolder(
    private val itemBinding: RowTransactionBinding,
    val listener: RecyclerListener,
    val context : Context
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(transaction: TransactionModel) {

        handleTypeTransaction(transaction)
        itemBinding.textNameTransactionRowTransaction.text = transaction.description
        itemBinding.textValueTransaction.text = FinancesFormatter.maskMonetaryValue(transaction.value)
        itemBinding.textNameAccountRowTransaction.text = transaction.accountName
        itemBinding.textDateTransaction.text =  transaction.dateOfMonth
    }

    private fun handleTypeTransaction(transaction: TransactionModel) {
        if (transaction.typeTransaction == FinancesConstants.TRANSACTIONS.INCOME) {
            itemBinding.imageTypeTransaction.setImageResource(R.drawable.ic_arrow_up_24)
            itemBinding.imageTypeTransaction.setColorFilter(context.getColor(R.color.green))
        } else {
            itemBinding.imageTypeTransaction.setImageResource(R.drawable.ic_arrow_down_24)
            itemBinding.imageTypeTransaction.setColorFilter(context.getColor(R.color.red))
        }
    }

}