package com.problem.pl.commons

/**
 * Controller的RequestMapping
 */
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
    const val MAPPING_PPC_PROJECT_LABEL_FOR_PROBLEM_BY_PAGINATION = "/queryProjectLabelsForProblemByPagination"
    const val MAPPING_PPC_PROJECT_SYSTEM_DEVICES = "/queryProjectSystemDevices"
    const val MAPPING_PPC_PROJECT_SAVE_PROBLEM_LIST = "/saveProjectSaveProblemList"
    const val MAPPING_PPC_QUERY_PROBLEM_LIST_BY_PROJECT_ID = "/queryProblemListByProjectId"
    const val MAPPING_PPC_QUERY_PROBLEM_LIST_NEW_TIME_COUNT = "/queryProblemListNewTimeCount"
    const val MAPPING_PPC_CHOOSE_OR_CANCEL_PROBLEM = "/queryChooseOrCancelProblem"
    const val MAPPING_PPC_UPDATE_MODIFY_PROBLEM_PROGRESS = "/updateProblemProgress" //修改问题进度 100为完成
    const val MAPPING_PPC_QUERY_MINE_COMPLETED_OR_NOT_COMPLETED_PROBLEMS = "/queryMineCompletedOrNotProblems" //查询我的 选中未修改完或者已修改完成的问题
    const val MAPPING_PPC_UPDATE_TRANSFER_ISSUES = "/updateTransferIssues" //转让某个问题给用户

    //项目接口相关
    const val MAPPING_PIF = "/pif"
    const val MAPPING_PIF_REQUEST_INTERFACE = "/requestInterface" //请求接口数据并返回到客户端
    const val MAPPING_PIF_SAVE_A_INTERFACE = "/saveInterface" // 新增一个接口
    const val MAPPING_PIF_QUERY_INTERFACES_BY_PROJECT_ID = "/queryInterfacesByProjectId" // 分页查询项目下的接口
    const val MAPPING_PIF_QUERY_INTERFACE_BY_ID = "/queryInterfaceById" // 查询接口详情
}