package com.mirea.kotov.mireaproject.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DatabaseViewModel: ViewModel() {
    companion object{
        private val data = MutableLiveData<String?>()
        private val db: AppDatabase? = App.getInstance().getDatabase()
        private val employeeDao = db!!.employeeDao()
        private var index = 1
    }

    fun insertData(name: String, salary: Int){
        var employee = Employee(index, name, salary)
        employeeDao!!.insert(employee)
        index++

        setData(employeeDao.getAll())
    }

    fun getData(): LiveData<String?> {
        return data
    }

    fun setData(all: List<Employee?>?) {
        var result = ""
        if (all != null) {
            for(employee in all){
                result += employee.toString() + "\n"
            }

            data.value = result
        }
    }
}