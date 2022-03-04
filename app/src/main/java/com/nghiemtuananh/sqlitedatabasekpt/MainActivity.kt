package com.nghiemtuananh.sqlitedatabasekpt

import android.app.Dialog
import android.content.DialogInterface
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var arrayCongViec: ArrayList<CongViec> = arrayListOf()
    lateinit var database: Database
    lateinit var adapter: CongViecAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec)
        lv_congviec.adapter = adapter

        // Tạo database ghichu
        database = Database(this, "ghichu.sqlite", null, 1)
        // Tạo bảng CongViec
        database.queryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))")
        // insert data
//        database.queryData("INSERT INTO CongViec VALUES(null, 'Viết ứng dụng ghi chú')")
        getDataCongViec()
    }

    fun dialogXoaCV(tenCV: String, id: Int) {
        var dialogXoa: AlertDialog.Builder = AlertDialog.Builder(this)
        dialogXoa.setMessage("Bạn có muốn xoá công việc $tenCV không?")
        dialogXoa.setPositiveButton("Có", DialogInterface.OnClickListener { dialog, which ->
            database.queryData("DELETE FROM CongViec WHERE ID = '$id'")
            Toast.makeText(this, "Đã xoá $tenCV", Toast.LENGTH_LONG).show()
            getDataCongViec()
        })
        
        dialogXoa.setNegativeButton("Không", DialogInterface.OnClickListener { dialog, which ->  })
        dialogXoa.show()
    }

    fun dialogSuaCongViec(ten: String, id: Int) {
        var dialog: Dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_sua_cong_viec)
        var edtTenCV: EditText = dialog.findViewById(R.id.edt_sua_cong_viec)
        var btnXacNhan: Button = dialog.findViewById(R.id.btn_xacnhan)
        var btnHuy: Button = dialog.findViewById(R.id.btn_huy_edit)
        edtTenCV.setText(ten)
        btnXacNhan.setOnClickListener {
            var tenMoi = edtTenCV.text.toString().trim()
            database.queryData("UPDATE CongViec SET TenCV = '$tenMoi' WHERE Id = '$id'")
            Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_LONG).show()
            dialog.dismiss()
            getDataCongViec()
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_congviec, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAdd -> dialogThem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogThem() {
        var dialog: Dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_them_cong_viec)
        var edtTen = dialog.findViewById(R.id.edt_them_cv) as EditText
        var btnThem = dialog.findViewById(R.id.btn_them) as Button
        var btnHuy = dialog.findViewById(R.id.btn_huy) as Button
        btnThem.setOnClickListener {
            var tenCV: String = edtTen.text.toString()
            if (tenCV.equals("")) {
                Toast.makeText(this, "Vui lòng nhập tên công việc", Toast.LENGTH_SHORT).show()
            } else {
                database.queryData("INSERT INTO CongViec VALUES(null, '$tenCV')")
                Toast.makeText(this, "Đã thêm.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                getDataCongViec()
            }
        }
        btnHuy.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getDataCongViec() {
        // select data
        var dataCongViec: Cursor = database.getData("SELECT * FROM CongViec")
        arrayCongViec.clear()
        while (dataCongViec.moveToNext()) {
            var ten = dataCongViec.getString(1)
            var id = dataCongViec.getInt(0)
//            Toast.makeText(this, ten, Toast.LENGTH_SHORT).show()
            arrayCongViec.add(CongViec(id, ten))
        }

        adapter.notifyDataSetChanged()
    }
}