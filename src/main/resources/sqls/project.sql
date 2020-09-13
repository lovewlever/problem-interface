#用户表
create table T_USER(
    id varchar(255) primary key comment '主键',
    u_login_account varchar (255) unique comment '登录账号',
    u_bind_email varchar(255) comment '绑定邮箱',
    u_bind_phone varchar(255) comment '绑定手机号',
    u_login_pwd varchar (255) not null comment '登录密码',
    u_nickname varchar (255)  not null unique comment '用户昵称',
    u_level integer default 0 comment '用户等级',
    u_age integer (3) comment '用户年龄',
    u_avatar varchar(255) comment '用户头像',
    u_gender enum('保密','男','女','女变男','男变女','同体') default '保密' comment '用户性别',
    register_timestamp bigint comment '注册时间',
    register_devices varchar(255) comment '注册设备',
    last_login_timestamp bigint comment '最后登录时间',
    last_login_devices varchar(255) comment '最后登录设备',
    register_devices_user_agent_string varchar(255) comment 'User Agent String'
);

#-----------------------------------------------------------------------------------------------------------------------
#项目表
create table T_PROJECT(
    id varchar(255) primary key comment '主键',
    project_name varchar(255) not null unique comment '项目名称',
    project_level integer not null default 0 comment '优先级',
    project_desc varchar(255) comment '项目描述',
    project_add_timestamp bigint not null comment '添加时间',
    project_is_public enum('Y','N') default 'Y' comment '是否公开项目',
    project_complete_schedule double default 0 comment '完成进度',
    project_add_user_id varchar(255) comment '添加此项目的人',
    constraint fk_project_add_user_id foreign key(project_add_user_id) references T_USER(id)
);

#项目操作记录表
#不与项目表关联 防止项目删除后影响此表
create table T_PROJECT_OPERATE_RECORDER(
    id varchar(255) primary key comment '主键',
    tpor_timestamp bigint comment '记录的时间',
    tpor_operate_type enum('DELETE','CREATE','MODIFY') not null comment '操作的类型',
    tpor_operate_content varchar(255) not null comment '操作的内容',
    tpor_project_id varchar(255) not null comment '项目id(不与项目表关联 防止项目删除后影响此表)',
    tpor_mod_user_id varchar(255) comment '修改人ID',
    constraint fk_tpor_mod_user_id foreign key (tpor_mod_user_id) references T_USER(id) #记录修改人的ID
);

#-----------------------------------------------------------------------------------------------------------------------
#项目问题列表
create table T_PROJECT_PROBLEM(
    id varchar(255) primary key comment '主键',
    pc_timestamp bigint comment '选择修改此问题的时间',
    pc_complete_timestamp bigint comment '此问题修改完成的时间',
    pc_add_timestamp bigint comment '添加此问题的时间',
    pc_complete_schedule integer default 0 comment '此问题的进度',
    pc_devices enum('IOS','Android','后台') comment '出现问题的终端',
    pc_module_page varchar(255) not null comment '模块或页面名称',
    pc_content varchar(255) not null comment '问题内容描述',
    pc_transfer_flow varchar(255) comment '转让流A->B->C',
    pc_project_id varchar(255) comment '问题所属的项目ID',
    pc_user_id varchar(255) comment '选择修改此问题的用户ID',
    pc_add_user_id varchar(255) comment '添此问题的人ID',
    pc_transfer_user_id varchar(255) comment '转让的最终用户ID',
    constraint fk_pc_project_id foreign key (pc_project_id) references T_PROJECT(id) on delete cascade , #所属项目的ID
    constraint fk_project_choose_user_id foreign key(pc_user_id) references T_USER(id),#选择此问题的用户ID
    constraint fk_project_choose_transfer_user_id foreign key(pc_transfer_user_id) references T_USER(id),#被转让的用户ID
    constraint fk_pc_add_user_id foreign key(pc_add_user_id) references T_USER(id)#添加此问题的人ID
);

#项目问题操作记录表
#不与项目问题表关联 防止项目删除后影响此表
create table T_PROJECT_PROBLEM_OPERATE_RECORDER(
      id varchar(255) primary key comment '主键',
      tpmr_timestamp bigint comment '记录的时间',
      tpmr_operate_type enum('DELETE','CREATE','MODIFY') not null comment '操作的类型',
      tpmr_operate_content varchar(255) not null comment '操作的内容记录',
      tpmr_project_id varchar(255) not null comment '项目问题的id(不与项目问题表关联 防止项目删除后影响此表)',
      tpmr_mod_user_id varchar(255) comment '修改人ID',
      constraint fk_tpmr_mod_user_id foreign key (tpmr_mod_user_id) references T_USER(id) #记录修改人的ID
);

#-----------------------------------------------------------------------------------------------------------------------
#项目接口表
create table T_PROJECT_INTERFACE(
    id varchar(255) primary key comment '主键',
    pi_add_timestamp bigint comment '添加时间',
    pi_last_mod_timestamp bigint comment '最后修改时间',
    pi_name varchar(255) not null comment '接口名',
    pi_data_json json not null comment '接口链接/参数/返回值、JSON格式存储',
    pi_mod_transfer_flow varchar(255) comment '修改流A->B->C',
    pi_add_user_id varchar(255) comment '添加人',
    pi_project_id varchar(255) comment '所属的项目id',
    constraint fk_pi_add_user_id foreign key (pi_add_user_id) references T_USER(id), #所属用户ID
    constraint fk_pi_project_id foreign key (pi_project_id) references T_PROJECT(id) on delete cascade #所属项目的ID

)