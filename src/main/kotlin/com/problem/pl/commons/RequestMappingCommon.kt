package com.problem.pl.commons

object RequestMappingCommon {

    const val REQUEST_ATTRIBUTE_KEY_USER_ID = "uid"
    const val REQUEST_ATTRIBUTE_KEY_TOKEN = "token"

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
    const val MAPPING_PC_QUERY_PROJECT_BY_USER_ID = "/queryProjectsByUserId"
    const val MAPPING_PC_QUERY_PROJECT_OPERATE_RECORDER = "/queryProjectOperateRecorders" //查询项目的操作记录

    //通用相关
    const val MAPPING_UNIVERSAL = "/uni"
    const val MAPPING_UNIVERSAL_VERIFY_TOKEN = "/verifyToken"


    //项目问题相关
    const val MAPPING_PPC = "/ppc"
    const val MAPPING_PPC_RECOMMEND_PROJECT_LABEL_FOR_PROBLEM = "/recommendProjectLabelsForProblem"
    const val MAPPING_PPC_PROJECT_SYSTEM_DEVICES = "/queryProjectSystemDevices"
    const val MAPPING_PPC_PROJECT_SAVE_PROBLEM_LIST = "/saveProjectSaveProblemList"
    const val MAPPING_PPC_QUERY_PROBLEM_LIST_BY_PROJECT_ID = "/queryProblemListByProjectId"
    const val MAPPING_PPC_QUERY_PROBLEM_LIST_NEW_TIME_COUNT = "/queryProblemListNewTimeCount"
}