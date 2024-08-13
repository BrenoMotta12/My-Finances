package com.brenomotta.myfinances.ui.viewholder

import android.app.AlertDialog
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.RowAccountBinding
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.util.FinancesFormatter

class AccountViewHolder(
    private val itemBinding: RowAccountBinding,
    val listener: RecyclerListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bindData(account: AccountModel) {

        itemBinding.textRowAccount.text = account.description
        itemBinding.textValueRowAccount.text = FinancesFormatter.maskMonetaryValue(account.value)

        itemBinding.root.setOnClickListener(View.OnClickListener {
            listener.onListClick(account)
        })

        itemBinding.root.setOnLongClickListener(View.OnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Exclusão de Conta")
                .setMessage("Deseja excluir a conta \"${account.description}\" ?")
                .setPositiveButton("Sim") { dialog, which ->
                    listener.onDeleteClick(account.id!!)
                }
                .setNeutralButton("Cancelar", null)
                .show()

            true
        })
    }

}