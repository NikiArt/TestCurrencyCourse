package com.nikitaboyko.testcurrencycourse.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.nikitaboyko.testcurrencycourse.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    lateinit var adapter: ArrayAdapter<String>

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        initView()
    }

    fun initView() {
        mainFragmentTextValue.setText("1")
        adapter = ArrayAdapter(context,
            R.layout.support_simple_spinner_dropdown_item,
            viewModel.courses.map { it.currency })
        mainFragmentSpinnerFrom.adapter = adapter
        mainFragmentSpinnerTo.adapter = adapter
        mainFragmentButtonConvert.setOnClickListener {
            viewModel.getResult(
                mainFragmentSpinnerFrom.selectedItemPosition,
                mainFragmentSpinnerTo.selectedItemPosition,
                mainFragmentTextValue.text.toString().toDouble()
            )
        }

        viewModel.updateAdapters.observeForever {
            adapter.clear()
            adapter.addAll(viewModel.courses.map { it.currency })
        }

        viewModel.convertOperation.observeForever { result ->
            mainFragmentTextResult.setText(result.toString())
        }
    }

}
