-- 就设置一个ccp
create table b_teacher
(
    teacher_id   int(8) not null primary key comment '教师id',
    teacher_name varchar(36)  not null comment '教师姓名',
    password     varchar(512) not null comment '加密的密码',
    avatar       varchar(512) not null comment '头像链接'
) comment '教师表';

-- 只考虑软件协同这一门课，学生只会出现在一个班级内
create table b_student
(
    student_id    int(15) primary key comment '学生id',
    student_name  varchar(36)  not null comment '学生姓名',
    student_class int(15) not null comment '学生归属教学班',
    student_role  char(1)      not null comment '学生角色(0表示未分配)' default '0',
    student_group int(15) null comment '学生小组',
    password      varchar(512) not null comment '加密的密码',
    avatar        varchar(512) not null comment '头像链接',
    foreign key (student_class) references b_class (class_id),
    foreign key (student_group) references b_group (group_id)
) comment '学生表';

-- 就放一节课
create table b_course
(
    course_id   int(15) not null primary key comment '课程id',
    course_name varchar(64) not null comment '课程名'
) comment '课程表';

-- 老师和课程一起构成教学班，可能会有带多个班情况
create table b_class
(
    class_id     varchar(36) not null comment '教学班代号',
    teacher_id   int(15) not null comment '开课教师id',
    teacher_name varchar(36) not null comment '教师姓名', -- 浅做个冗余减少查询次数
    course_id    int(15) not null comment '课程代号',
    course_name  varchar(64) not null comment '课程名',   -- 浅做个冗余减少查询次数
    class_term   varchar(36) null comment '开课学期',
    foreign key (teacher_id) references b_teacher (teacher_id),
    foreign key (course_id) references b_course (course_id)
) comment '教学班表';

-- 一个学生一个组，一个组多个学生
create table b_group
(
    group_id            int(15) primary key comment '队伍代号',
    group_name          varchar(36)  not null comment '队伍名称',
    group_avatar        varchar(512) not null comment '队伍头像链接',
    group_introduction  varchar(512) null comment '队伍介绍',
    group_leader_id     int(15) not null comment '队长id',
    -- 以下两项都是冗余的，减少查询次数
    group_leader_name   varchar(36) comment '队长姓名',
    group_leader_avatar varchar(512) comment '队长头像',
    foreign key (group_leader_id) references b_student (student_id)
) comment '小组表';

-- 课程基本设置表(应ccp的要求，课程提供一个基本骨架)
create table b_course_basic_settings
(
    course_id    int(15) not null comment '对应课程id',
    chapter_name varchar(512) not null comment '章节名称',
    foreign key (course_id) references b_course (course_id),
    primary key (course_id, chapter_name) -- 构成联合主键
) comment '课程基本设置表';

-- 课程设置信息
create table b_class_chapter_settings
(
    chapter_id   int(15) not null primary key comment '章节id',
    class_id     varchar(36) not null comment '课程id',
    chapter_name varchar(512) comment '章节标题',
    foreign key (class_id) references b_class (class_id)
) comment '课程章节设置';


-- 章节内容信息
create table b_class_chapter_content
(
    content_id    int(15) not null primary key comment '资源id',
    chapter_id    int(15) not null comment '章节id',
    resource_link varchar(1024) not null comment '资源预览下载链接',
    foreign key (chapter_id) references b_class_chapter_settings (chapter_id)
) comment '章节内容信息';


-- 教学班任务表
create table b_class_task
(
    task_id      int(15) not null comment '任务id',
    class_id     varchar(36)   not null comment '归属教学班id',
    task_content varchar(1024) not null comment '任务内容',
    deadline     datetime      not null comment '任务截止提交时间',
    task_order   int           not null comment '任务先后次序，需手动指定',
    primary key (task_id),
    foreign key (class_id) references b_class (class_id)
) comment '教学班任务表';

-- 学生任务分配及提交表
create table b_student_task_submit
(
    record_id         int(15) not null comment '分配记录id', -- 存在的意义是做到前端表里比较方便
    task_id           int(15) not null comment '归属任务id',
    task_handler      int(15) not null comment '任务接收者',
    task_handler_name int(15) not null comment '任务接受者姓名',
    task_handler_work varchar(1024) not null comment '任务接收者要做的事描述',
    submit_link       varchar(512) null comment '提交的成果',
    submit_time       datetime null comment '提交时间',
    primary key (record_id),
    foreign key (task_id) references b_class_task (task_id)
) comment '学生任务分配及提交表';

-- 用到的静态信息，如性别、是否、学生角色等
create table b_dict
(
    dict_name varchar(36) not null comment '字典名',
    tag       varchar(36) not null comment '字典标签，用于检索值',
    value     varchar(36) not null comment '字典值',
    primary key (dict_name, tag) -- 构成联合主键
) comment '字典表';

-- 学生成绩表-来自学生间互评
create table b_student_performance_student
(
    -- performance_id int(15) not null primary key comment '成绩id',
    performance          int(4) not null comment '成绩表现',
    performance_receptor int(15) not null comment '被评分者id',
    performance_class    int(15) not null comment '评分产生教学班',
    performance_maker    int(15) not null comment '评分生产者id',
    performance_stage    int(15) not null comment '评分位于任务的阶段',
    -- 项目内一共3个地方会产生评分
    -- 1. (小组成员)为(过程负责人)评分
    -- 2. (过程负责人)为(小组成员<除自己之外>)评分
    -- 3. (老师)为(团队)作品打分，分数由全队共享
    -- 评分事由：被教师评价分、被过程负责人评价分、被组员评价分
    performance_tag      char(1) not null comment '评分事由',
    -- performance_origin varchar(256) not null comment '成绩准确来源分析', -- 本项一定要填写准确(格式：课程代号-课程提交阶段id-评分者id-评分事由-时间戳)
    primary key (performance_receptor, performance_maker, performance_stage, performance_tag), -- 构成联合主键
    foreign key (performance_receptor) references b_student (student_id),
    foreign key (performance_maker) references b_student (student_id),
    foreign key (performance_stage) references b_class_task (task_id)
) comment '学生成绩表-互评';

-- 学生成绩表-来自老师打分
create table b_student_performance_teacher
(
    -- performance_id int(15) not null primary key comment '成绩id',
    performance          int(4) not null comment '成绩表现',
    performance_receptor int(15) not null comment '被评分者id',
    performance_class    int(15) not null comment '评分产生教学班',
    performance_maker    int(15) not null comment '评分生产者id',
    performance_stage    int(15) not null comment '评分位于任务的阶段',
    -- 项目内一共3个地方会产生评分
    -- 1. (小组成员)为(过程负责人)评分
    -- 2. (过程负责人)为(小组成员<除自己之外>)评分
    -- 3. (老师)为(团队)作品打分，分数由全队共享
    -- 评分事由：被教师评价分、被过程负责人评价分、被组员评价分
    performance_tag      char(1) not null comment '评分事由',
    -- performance_origin varchar(256) not null comment '成绩准确来源分析', -- 本项一定要填写准确(格式：课程代号-课程提交阶段id-评分者id-评分事由-时间戳)
    primary key (performance_receptor, performance_maker, performance_stage, performance_tag), -- 构成联合主键
    foreign key (performance_receptor) references b_student (student_id),
    foreign key (performance_maker) references b_teacher (teacher_id),
    foreign key (performance_stage) references b_class_task (task_id)
) comment '学生成绩表-师评';

-- 学生接收的通知，由系统和老师发布
create table b_notice_student
(
    notice_id               int(15) not null comment '通知id',
    notice_content          varchar(1024) not null comment '通知内容',
    notice_publisher        varchar(36)   not null comment '公告发布者',     -- 因为可能有系统直接发布公告的可能，这里直接用varchar
    notice_publisher_avatar varchar(512)  not null comment '公告发布者头像', -- 能把栏做好看点..
    notice_publish_snapshot datetime      not null comment '公告发布时间' default NOW(),
    primary key (notice_id)
) comment '学生消息提醒内容表';

-- 学生的消息提醒
create table b_notice_receive_student
(
    notice_id          int(15) not null comment '通知id',
    notice_receiver_id int(15) not null comment '通知接受者id',
    notice_status      char(1) not null default '0' comment '阅读状态0未读1已读',
    primary key (notice_id, notice_receiver_id), -- 构成联合主键，代表要接受的一个对象
    foreign key (notice_id) references b_notice_student (notice_id),
    foreign key (notice_receiver_id) references b_student (student_id)
) comment '学生消息提醒分送表';

-- 老师接收的通知，由系统发布
create table b_notice_teacher
(
    notice_id               int(15) not null comment '通知id',
    notice_content          varchar(1024) not null comment '通知内容',
    notice_publisher        varchar(36)   not null comment '公告发布者',     -- 因为可能有系统直接发布公告的可能，这里直接用varchar
    notice_publisher_avatar varchar(512)  not null comment '公告发布者头像', -- 能把栏做好看点..
    notice_publish_snapshot datetime      not null comment '公告发布时间' default NOW(),
    primary key (notice_id)
) comment '老师消息提醒内容表';

-- 老师的消息提醒
create table b_notice_receive_teacher
(
    notice_id          int(15) not null comment '通知id',
    notice_receiver_id int(8) not null comment '通知接受者id',
    notice_status      char(1) not null default '0' comment '阅读状态0未读1已读',
    primary key (notice_id, notice_receiver_id), -- 构成联合主键，代表要接受的一个对象
    foreign key (notice_id) references b_notice_teacher (notice_id),
    foreign key (notice_receiver_id) references b_teacher (teacher_id)
) comment '老师消息提醒分送表';

-- 剩下聊天记录表要做，放下一次迭代再说