#用户表
create table T_USER(
    id varchar(255) primary key comment '主键',
    u_login_account varchar (255) unique comment '登录账号',
    u_login_email varchar(255) unique comment '登录邮箱',
    u_login_phone varchar(255) unique comment '用户登录手机号',
    u_login_pwd varchar (255) not null comment '登录密码',
    u_nickname varchar (255)  not null comment '用户昵称',
    u_level integer default 0 comment '用户等级',
    u_age integer (3) comment '用户年龄',
    u_gender enum('保密','男','女','女变男','男变女') default '保密' comment '用户性别',
    register_timestamp bigint comment '注册时间',
    register_devices varchar(255) comment '注册设备',
    last_login_timestamp bigint comment '最后登录时间',
    last_login_devices varchar(255) comment '最后登录设备',
    register_devices_user_agent_string varchar(255) comment 'User Agent String'
);

#项目表
create table T_PROJECT(
    id varchar(255) primary key comment '主键',
    project_name varchar(255) not null unique comment '项目名称',
    project_level integer not null default 0 comment '优先级',
    project_add_timestamp bigint not null comment '添加时间',
    project_complete_schedule double default 0 comment '完成进度',
    project_add_user_id varchar(255) comment '添加此项目的人',
    constraint fk_project_add_user_id foreign key(project_add_user_id) references T_USER(id)
);

#项目问题列表
create table T_PROJECT_PROBLEM(
    id varchar(255) primary key comment '主键',
    pc_timestamp bigint comment '选择修改此问题的时间',
    pc_complete_timestamp bigint comment '此问题修改完成的时间',
    pc_complete_schedule integer default 0 comment '此问题的进度',
    pc_transfer_flow varchar(255) comment '转让流A->B->C',
    pc_project_id varchar(255) comment '问题所属的项目ID',
    pc_user_id varchar(255) comment '选择修改此问题的用户',
    pc_transfer_user_id varchar(255) comment '转让的最终用户',
    constraint fk_pc_project_id foreign key (pc_project_id) references T_PROJECT(id) on delete cascade , #所属项目的ID
    constraint fk_project_choose_user_id foreign key(pc_user_id) references T_USER(id),#选择此问题的用户ID
    constraint fk_project_choose_transfer_user_id foreign key(pc_transfer_user_id) references T_USER(id)#被转让的用户ID
);

#项目接口表
create table T_PROJECT_INTERFACE(
    id varchar(255) primary key comment '主键',
    pi_add_timestamp bigint comment '添加时间',
    pi_mod_timestamp bigint comment '修改时间',
    pi_name varchar(255) not null comment '接口名',
    pi_data_json json not null comment '接口链接/参数/返回值、JSON格式存储',
    pi_mod_transfer_flow varchar(255) comment '修改流A->B->C',
    pi_add_user_id varchar(255) comment '添加人',
    pi_project_id varchar(255) comment '所属的项目id',
    constraint fk_pi_add_user_id foreign key (pi_add_user_id) references T_USER(id), #所属用户ID
    constraint fk_pi_project_id foreign key (pi_project_id) references T_PROJECT(id) on delete cascade #所属项目的ID

)