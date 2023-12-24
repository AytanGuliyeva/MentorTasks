package com.example.mentortasks

import android.system.Os.remove
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mentortasks.databinding.ItemCustomerBinding
import db.CustomerDao
import db.CustomerDb
import db.CustomerEntity
import db.RoomInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerAdapter(
    private val customers:MutableList<CustomerEntity>,
    private val itemClick: (item:CustomerEntity) -> Unit
):RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding=ItemCustomerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return customers.size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        holder.bind(customers[position])
    }

    inner class CustomerViewHolder(private val binding: ItemCustomerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: CustomerEntity) {
            binding.txtName.text = item.name
            binding.txtSurName.text = item.surName
            binding.txtAge.text = item.age
            binding.txtId.text = item.id.toString()

            itemView.setOnClickListener {
                itemClick(item)
            }
        }


    }
}