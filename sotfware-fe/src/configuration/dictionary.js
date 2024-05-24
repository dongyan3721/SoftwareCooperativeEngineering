/**
 * @author Santa Antilles
 * @description 存放字典值
 * ※※※※※※※※※※※时时刻刻与数据库同步※※※※※※※※※※※
 * @date 2024/5/10-10:48:15
 */

export const sys_user_role = {
    TEACHER: '1',
    STUDENT: '0'
}

export const sys_student_role = {
    PRODUCT_MANAGER: '2',
    DEVELOPMENT_MANAGER: '1',
    UNASSIGNED: '0',
    TEST_MANAGER: '4',
    PLANNING_MANAGER: '3'
}

export const sys_task_submit_status = {
    // 通过
    PASS: '2',
    // 重做
    REDO: '3',
    // 未分配
    UNASSIGNED: '4',
    // 等待教师批阅
    WAIT_FOR_TEACHER_MARK: '1',
    // 等待提交
    WAIT_FOR_STUDENT_HAND_ON: '0',
    // 表示现在的进度还没有到这个任务，后端不需要存
    UNREACHED: '5'
}

export const sys_class_phase = {
    INIT: 0,
    TEAMING: 1,
    TASKING: 2,
    OVER: 3
}

