import android.accounts.Account
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.brenomotta.myfinances.databinding.ModalNewAccountLayoutBinding
import com.brenomotta.myfinances.service.model.AccountModel
import com.brenomotta.myfinances.service.util.FinancesFormatter
import com.brenomotta.myfinances.ui.accounts.AccountsViewModel
import com.google.android.material.snackbar.Snackbar

class ModalAccount(context: Context, private val account: AccountModel?, private val viewModel: AccountsViewModel) : Dialog(context) {

    private lateinit var binding: ModalNewAccountLayoutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ModalNewAccountLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Definir propriedades da janela para posicionar a dialog na parte inferior
        val window = window
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.BOTTOM
        window.attributes = layoutParams


        binding.buttonCloseModalNewAccount.setOnClickListener {
            handleSave()
        }

        // Adicionar TextWatcher para formatar o EditText
        FinancesFormatter.maskMonetaryValue(binding.editAccountValue)

        if (account != null) {
            binding.modalTitle.text = "Editar Conta"
            binding.editAccountDescription.setText(account.description)
            binding.editAccountValue.setText(account.value.toString())

        } else {
            binding.modalTitle.text = "Nova Conta"
        }

    }

    private fun handleSave() {

        if (binding.editAccountDescription.text.toString() == "") {
            val snack = Snackbar.make(binding.root, "O nome da conta precisa ser preenchido", Snackbar.LENGTH_SHORT)
            val snackbarView = snack.view
            val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snack.show()
            return
        }

        val account: AccountModel = AccountModel().apply {
            this.description = binding.editAccountDescription.text.toString()
            this.value = FinancesFormatter.maskToDouble(binding.editAccountValue.text.toString())
        }
        viewModel.save(account)
        dismiss()
    }
}
