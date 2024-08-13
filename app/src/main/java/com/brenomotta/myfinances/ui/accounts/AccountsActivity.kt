package com.brenomotta.myfinances.ui.accounts

import com.brenomotta.myfinances.ui.modal.ModalAccount
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brenomotta.myfinances.R
import com.brenomotta.myfinances.databinding.ActivityAccountsBinding
import com.brenomotta.myfinances.service.listener.RecyclerListener
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.ui.adapter.AccountAdapter

class AccountsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityAccountsBinding
    private lateinit var viewModel: AccountsViewModel
    private val adapter = AccountAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AccountsViewModel::class.java)
        binding = ActivityAccountsBinding.inflate(layoutInflater)

        // Instanciação da Recycler View
        binding.recyclerAccountsScreenAccounts.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerAccountsScreenAccounts.adapter = adapter

        supportActionBar?.title = "Contas"

        binding.buttonNewAccountScreenAccounts.setOnClickListener(this)

        observer()

        adapter.attachListener(object : RecyclerListener {
            override fun onListClick(accountModel: AccountModel) {
                openModal(accountModel)
            }

            override fun onDeleteClick(id: Int) {
                viewModel.deleteAccount(id)
                viewModel.loadData()
            }

        })

        viewModel.loadData()

        /**
         * Ao criar a tela, se não houver nenhuma conta no banco de dados, o sistema abre diretamente
         * a tela para cadastrar uma nova conta
         * */
        if (viewModel.listAccount.value?.isEmpty() == true) {
            openModal()
        }

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_new_account_screen_accounts -> {
                openModal()
            }
        }
    }

    // Chamada caso seja uma edição de conta
    private fun openModal(account: AccountModel?) {
        val modal = ModalAccount(
            this,
            account,
            viewModel
        )
        modal.setOnDismissListener {
            viewModel.loadData()
        }
        modal.show()
    }

    // Chamada caso seja uma criação de conta
    private fun openModal() {
        val modal = ModalAccount(
            this,
            null,
            viewModel
        )
        modal.setOnDismissListener {
            viewModel.loadData()
        }
        modal.show()

    }

    private fun observer() {
        viewModel.listAccount.observe(this) {
            adapter.updateTransactions(it)
        }
        viewModel
    }
}

