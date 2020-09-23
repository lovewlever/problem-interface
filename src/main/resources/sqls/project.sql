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
    user_id varchar(255) comment '添加此项目的人',
    constraint fk_project_add_user_id foreign key(user_id) references T_USER(id)
);

#项目操作记录表
#不与项目表关联 防止项目删除后影响此表
create table T_PROJECT_OPERATE_RECORDER(
    id varchar(255) primary key comment '主键',
    tpor_timestamp bigint comment '记录的时间',
    tpor_operate_type enum('DELETE','CREATE','MODIFY') not null comment '操作的类型',
    tpor_operate_content varchar(255) not null comment '操作的内容',
    project_name varchar(255) not null comment '项目/问题/接口名称',
    project_id varchar(255) comment '项目id(不与项目表关联 防止项目删除后影响此表)',
    project_problem_id varchar(255) comment '对应的项目问题id(不与项目问题表关联 防止项目删除后影响此表)',
    project_interface_id varchar(255) comment '对应的项目接口Id(不与项目接口表关联 防止项目删除后影响此表)',
    user_id varchar(255) comment '修改人ID',
    constraint fk_tpor_mod_user_id foreign key (user_id) references T_USER(id) #记录修改人的ID
);

#-----------------------------------------------------------------------------------------------------------------------

#系统设备终端表
create table T_PROJECT_SYSTEM_DEVICES(
    id varchar(255) primary key comment '主键',
    system_devices_name varchar(255) not null unique comment '设备名称'
);

insert into T_PROJECT_SYSTEM_DEVICES(id,system_devices_name) values ('078e276d71e941cfa94b8dadeac3fc3e','IOS');
insert into T_PROJECT_SYSTEM_DEVICES(id,system_devices_name) values ('078e276d71e942cfa94b8dadeac3fc3e','Android');
insert into T_PROJECT_SYSTEM_DEVICES(id,system_devices_name) values ('078e276d71e943cfa94b8dadeac3fc3e','后台');

#-----------------------------------------------------------------------------------------------------------------------

#项目问题列表
create table T_PROJECT_PROBLEM(
    id varchar(255) primary key comment '主键',
    pp_choose_timestamp bigint not null default 0 comment '选择修改此问题的时间',
    pp_complete_timestamp bigint not null default 0  comment '此问题修改完成的时间',
    pp_add_timestamp bigint not null comment '添加此问题的时间',
    pp_complete_schedule integer not null default 0 comment '此问题的进度',
    pp_module_page varchar(255) not null comment '模块或页面名称',
    pp_content varchar(255) not null comment '问题内容描述',
    pp_transfer_flow varchar(255) comment '转让流A=>B=>C',
    project_id varchar(255) comment '问题所属的项目ID',
    user_id_for_choose varchar(255) comment '选择修改此问题的用户ID',
    user_id_for_add varchar(255) comment '添此问题的人ID',
    system_devices_id varchar(200) comment '出现问题的终端',
    constraint fk_pc_project_id foreign key (project_id) references T_PROJECT(id) on delete cascade , #所属项目的ID
    constraint fk_project_choose_user_id foreign key(user_id_for_choose) references T_USER(id),#选择此问题的用户ID
    constraint fk_pc_add_user_id foreign key(user_id_for_add) references T_USER(id),#添加此问题的人ID
    constraint fk_system_devices_id foreign key(system_devices_id) references T_PROJECT_SYSTEM_DEVICES(id)#对应的设备表ID
);

#-----------------------------------------------------------------------------------------------------------------------
#项目接口表
create table T_PROJECT_INTERFACE(
    id varchar(255) primary key comment '主键',
    pi_add_timestamp bigint comment '添加时间',
    pi_last_mod_timestamp bigint not null default 0 comment '最后修改时间',
    pi_name varchar(255) not null comment '接口名',
    pi_is_abandoned enum('N','Y') default 'N' comment '是否废弃',
    pi_data_json json not null comment '接口链接/参数/返回值、JSON格式存储',
    pi_mod_transfer_flow varchar(255) comment '修改流A->B->C',
    user_id_for_add varchar(255) comment '添加人',
    project_id varchar(255) comment '所属的项目id',
    constraint fk_pi_add_user_id foreign key (user_id_for_add) references T_USER(id), #所属用户ID
    constraint fk_pi_project_id foreign key (project_id) references T_PROJECT(id) on delete cascade #所属项目的ID

);

#项目接口评分表(差强人意，褒贬不一，好评如潮)
create table T_PROJECT_INTERFACE_SCORE(
    id varchar(255) primary key comment '主键',
    tis_points integer not null comment '分值',
    tis_assess_time bigint not null comment '评价时间',
    interface_id varchar(255) comment '接口表外键',
    constraint fk_interface_id foreign key (interface_id) references T_PROJECT_INTERFACE(id) #所属接口id
)