package com.example.projectsample.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectsample.Adapter.CategorizedProductAdapter
import com.example.projectsample.Adapter.CategoryListAdapter
import com.example.projectsample.R
import com.example.projectsample.ViewModel.CategoryViewModel
import com.example.projectsample.databinding.FragmentCategoryBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment(),CategoryListAdapter.Listener {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: CategoryViewModel
    private lateinit var adapter: CategoryListAdapter
    private var categorizedAdapter: CategorizedProductAdapter? = null
    private lateinit var layoutManager : RecyclerView.LayoutManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        binding.recyclerViewCategory.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        layoutManager = GridLayoutManager(context,2)
        binding.recyclerViewProduct.layoutManager = layoutManager

        runBlocking {
            launch {
                viewModel.getCategoryFromAPI()
                viewModel.getFirstCategoryFromAPI()
            }
        }
        viewModel.categoryList.observe(viewLifecycleOwner, Observer {

            println(it)
            viewModel.categoryList.value?.let {
                adapter = viewModel.categoryList.value?.let { it1 ->
                    CategoryListAdapter(
                        it1,
                        requireContext(),
                        this@CategoryFragment
                    )
                }!!
                binding.recyclerViewCategory.adapter = adapter
            }
            println("viewModel")
            println("size " + viewModel.categoryList.value?.size)
            println("observer")
        })
        viewModel.categorizedProducts.observe(viewLifecycleOwner, Observer {
            viewModel.categorizedProducts.value?.let {
                categorizedAdapter = CategorizedProductAdapter(it, requireContext())
                binding.recyclerViewProduct.adapter = categorizedAdapter
            }
        })
    }


    override fun onItemClick(position: Int, holder: CategoryListAdapter.PlaceHolder) {
        viewModel.getCategorizedProductFromAPI(holder.binding.buttonEachCategory.text.toString())
    }

}