package com.problem.pl.model.entities

import cn.afterturn.easypoi.excel.annotation.Excel

class ExcelInterfaceEntity {

    @Excel(name = "接口名称" )
    var interfaceName: String = ""

    @Excel(name = "接口链接" )
    var interfaceLink: String = ""

    @Excel(name = "返回值" )
    var interfaceParams: String = ""

    @Excel(name = "其他说明" )
    var interfaceDescription: String = ""


}