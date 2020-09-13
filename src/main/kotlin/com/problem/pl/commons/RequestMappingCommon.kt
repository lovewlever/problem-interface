package com.problem.pl.commons

object RequestMappingCommon {

    //用户相关
    const val MAPPING_USER = "/usr"
    const val MAPPING_USER_REGISTER = "/register"
    const val MAPPING_USER_LOGIN = "/login"
    const val MAPPING_USER_GET_VERIFY_CODE = "/getVerifyCode"

    //项目相关
    const val MAPPING_PC = "/pc"
    const val MAPPING_PC_PROJECT_LIST = "/queryPlist"
    const val MAPPING_PC_ADD_NEW_PROJECT = "/addNewProject"
    const val MAPPING_PC_MODIFY_PROJECT_PRIORITY = "/modifyProjectPriority"
    const val MAPPING_PC_MODIFY_PROJECT_NAME = "/modifyProjectName"
    const val MAPPING_PC_DELETE_PROJECT_BY_ID = "/deleteProjectById"
    const val MAPPING_PC_QUERY_PROJECT_BY_ID = "/queryProjectById"

    //通用相关
    const val MAPPING_UNIVERSAL = "/uni"
    const val MAPPING_UNIVERSAL_VERIFY_TOKEN = "/verifyToken"


    //项目问题相关
    const val MAPPING_PPC = "/ppc"
}