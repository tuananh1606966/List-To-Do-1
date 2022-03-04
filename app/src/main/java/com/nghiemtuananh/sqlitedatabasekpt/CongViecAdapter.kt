package com.nghiemtuananh.sqlitedatabasekpt

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class CongViecAdapter(var context: MainActivity, var layout: Int, var congViecList: List<CongViec>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return congViecList.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder: ViewHolder
        var view: View
        if (convertView == null) {
            var inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        var congViec: CongViec = congViecList.get(position)

        holder.txtTen.setText(congViec.tenCV)

        // bắt sự kiện xoá và sửa
        holder.imgEdit.setOnClickListener {
            context.dialogSuaCongViec(congViec.tenCV, congViec.idCV)
        }

        holder.imgDelete.setOnClickListener {
            context.dialogXoaCV(congViec.tenCV, congViec.idCV)
        }

        return view
    }

    inner class ViewHolder(view: View) {
        var txtTen: TextView
        var imgDelete: ImageView
        var imgEdit: ImageView

        init {
            txtTen = view.findViewById(R.id.tv_ten)
            imgDelete = view.findViewById(R.id.iv_delete)
            imgEdit = view.findViewById(R.id.iv_edit)
        }
    }
}