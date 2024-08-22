package com.brenomotta.myfinances.ui.viewholder

import android.app.AlertDialog
import android.content.Context
import android.view.View
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


        // click de deleção
        itemBinding.root.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Exclusão de Transação")
                .setMessage("Deseja excluir a transação \"${transaction.description}\" ?")
                .setPositiveButton("Sim") { dialog, which ->
                    listener.onDeleteClick(transaction.codTransaction)
                }
                .setNeutralButton("Cancelar", null)
                .show()

            true
        })

        // Click de edição
        itemBinding.root.setOnClickListener(View.OnClickListener {
            listener.onListClick(transaction.id!!)
        })
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