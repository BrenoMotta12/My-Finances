package com.brenomotta.myfinances.ui.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.RowTransactionBinding
import com.brenomotta.myfinances.service.constants.FinancesConstants
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.TransactionModel
import java.text.SimpleDateFormat

class TransactionViewHolder(
    private val itemBinding: RowTransactionBinding,
    val listener: RecyclerListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(transaction: TransactionModel) {

        if (transaction.typeTransaction == FinancesConstants.TRANSACTIONS.INCOME) {
            itemBinding.imageTypeTransaction.setImageResource(R.drawable.ic_arrow_up_24)
            itemBinding.imageTypeTransaction.setColorFilter(R.color.green)
        } else {
            itemBinding.imageTypeTransaction.setImageResource(R.drawable.ic_arrow_down_24)
            itemBinding.imageTypeTransaction.setColorFilter(R.color.red)
        }
        itemBinding.textNameTransaction.text = transaction.description
        itemBinding.textValueTransaction.text = "R$" + transaction.value

        val date = SimpleDateFormat("yyyy-MM-dd").parse(transaction.dateOfMonth)
        itemBinding.textDateTransaction.text = SimpleDateFormat("dd/MM/yyyy").format(date)
    }

}