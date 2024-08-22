package com.brenomotta.myfinances.ui.modal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.brenomotta.myfinances.databinding.ModalAccountLayoutBinding
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.util.FinancesFormatter
import com.brenomotta.myfinances.ui.accounts.AccountsViewModel
import com.google.android.material.snackbar.Snackbar

class ModalAccount(
    context: Context,
    private val viewModel: AccountsViewModel,
    private val id: Int? = null
) : BaseModal(context) {

    private lateinit var binding: ModalAccountLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ModalAccountLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir propriedades da janela para posicionar a dialog na parte inferior
        setWindowConfig()


        binding.buttonSaveModalNewAccount.setOnClickListener {
            handleSave()
        }

        // Adicionar TextWatcher para formatar o EditText
//        FinancesFormatter.maskMonetaryValue(binding.editAccountValue)

        if (id != null) {
            val account = viewModel.getAccount(id)
            binding.modalTitle.text = "Editar Conta"
            binding.editAccountDescription.setText(account.description)
            binding.editAccountValue.setText(account.value.toString())

        } else {
            binding.modalTitle.text = "Nova Conta"
        }

    }

    private fun handleSave() {

        if (binding.editAccountDescription.text.toString() == "") {
            val snack = Snackbar.make(
                binding.root,
                "O nome da conta precisa ser preenchido",
                Snackbar.LENGTH_SHORT
            )
            val snackbarView = snack.view
            val textView =
                snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snack.show()
            return
        }

        val account = AccountModel()
        account.id = id
        account.description = binding.editAccountDescription.text.toString()
        account.value = FinancesFormatter.monetaryToDouble(binding.editAccountValue.text.toString())

        viewModel.save(account)
        dismiss()
    }
}
