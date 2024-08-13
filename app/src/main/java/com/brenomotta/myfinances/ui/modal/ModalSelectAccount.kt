package com.brenomotta.myfinances.ui.modal


import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenomotta.myfinances.databinding.ModalSelectAccountBinding
import com.brenomotta.myfinances.service.listener.AccountSelectionListener
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.ui.adapter.AccountAdapter


class ModalSelectAccount(
    context: Context,
    val accounts: List<AccountModel>,
    val listener: AccountSelectionListener
) : BaseModal(context) {

    private lateinit var binding: ModalSelectAccountBinding
    private val adapter = AccountAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setWindowConfig()

        binding = ModalSelectAccountBinding.inflate(layoutInflater)
        binding.recyclerAccountsModalSelectAccount.adapter = adapter
        binding.recyclerAccountsModalSelectAccount.layoutManager = LinearLayoutManager(context)

        for (account in accounts) {
            if (account.description.length > 11) {
                account.description = account.description.take(11) + "..."
            }
        }

        adapter.updateTransactions(accounts)
        adapter.attachListener(object : RecyclerListener {
            override fun onListClick(accountModel: AccountModel) {
                listener.onAccountSelected(accountModel)
                dismiss()
            }

            override fun onDeleteClick(id: Int) {
            }

        })

        setContentView(binding.root)
    }


}