package com.lyeng.notekeeper

data class TeaTypeInfo(val tesTypeId: String, val teaTypeName: String) {
    override fun toString(): String {
        return teaTypeName
    }
}

//class TeaInfo(var textTeaType: TeaTypeInfo, var textTeaName: String, var textMessage: String) // to string generated automatically
//Only properties for data class
data class TeaInfo(
    var textTeaType: TeaTypeInfo? = null,
    var textTeaName: String? = null,
    var textMessage: String? = null
)