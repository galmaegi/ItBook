package com.example.itbook.ui.detail.memo

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.example.itbook.databinding.DialogMemoBinding

class MemoDialog(
    context: Context,
    var memo: String
) : Dialog(context) {

    var onSaveClickListener: ((String) -> Unit)? = null
    val binding: DialogMemoBinding by lazy {
        DialogMemoBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        binding.memo = this.memo
        setTitle("MEMO")

        setCancelable(false)

        binding.saveButton.setOnClickListener {
            binding.memoEdittext.isEnabled = false
            onSaveClickListener?.invoke(binding.memoEdittext.text.toString())
            dismiss()
        }
    }
}