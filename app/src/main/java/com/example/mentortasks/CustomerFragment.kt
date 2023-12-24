package com.example.mentortasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mentortasks.databinding.FragmentCustomerBinding
import db.CustomerDao
import db.CustomerEntity
import db.RoomInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerFragment : Fragment() {
    private lateinit var binding: FragmentCustomerBinding
    private lateinit var customerDao: CustomerDao
    private lateinit var adapter:CustomerAdapter
    private  var customerUpdate:CustomerEntity?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCustomerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAdd()
        initRecyclerCustomer()
        btnDelete()
        btnUpdate()
    }

    private fun btnAdd() {
        binding.btnAdd.setOnClickListener {
            val customerEntity = CustomerEntity(
                0,
                binding.edtName.text.toString(),
                binding.edtSurname.text.toString(),
                binding.edtAge.text.toString(),
            )
            CoroutineScope(Dispatchers.IO).launch {
                val result = customerDao.insert(customerEntity)
                withContext(Dispatchers.Main) {
                    if (result != 1L) {
                        Toast.makeText(
                            requireContext(),
                            "Successfully saved information",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
    private fun btnDelete() {
        binding.btnRemove.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                customerDao.delete(binding.edtId.text.toString().toInt())
                withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "Successfully saved information", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun initRecyclerCustomer(){
        val db= RoomInstance.getInstance(requireContext())
        customerDao=db.customerDao()
        CoroutineScope(Dispatchers.IO).launch {
            val customers=db.customerDao().getAll()
            withContext(Dispatchers.Main){
                adapter= CustomerAdapter( customers){
                    customerUpdate=it
                }
                binding.recyclerCustomer.layoutManager= LinearLayoutManager(requireContext())
                binding.recyclerCustomer.adapter=adapter
            }
        }
    }

    private fun btnUpdate(){
        binding.btnUpdate.setOnClickListener {
            if (customerUpdate==null){
                Toast.makeText(requireContext(),"Select the item",Toast.LENGTH_SHORT).show()
            }
            val bundle=Bundle()
            bundle.putInt("id",customerUpdate!!.id)
            bundle.putString("name",customerUpdate!!.name)
            bundle.putString("surName",customerUpdate!!.surName)
            bundle.putString("age",customerUpdate!!.age)
        }
        binding.btnUpdate.setOnClickListener {
            val updatedName = binding.edtName.text.toString()
            val updatedSurname = binding.edtSurname.text.toString()
            val updatedAge = binding.edtAge.text.toString()
            if (customerUpdate != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    customerUpdate!!.name = updatedName
                    customerUpdate!!.surName = updatedSurname
                    customerUpdate!!.age = updatedAge

                    customerDao.update(customerUpdate!!)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "Successfully updated information",
                            Toast.LENGTH_SHORT
                        ).show()

                        binding.edtName.text.clear()
                        binding.edtSurname.text.clear()
                        binding.edtAge.text.clear()
                    }
                }
            }
        }
    }
}