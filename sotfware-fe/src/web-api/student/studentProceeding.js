import request from "@/util/request.js";

export function getClassTask(classId) {
	return request({
		url: "/student/task",
		method: "GET",
		params: {
			"classId": classId
		}
	})
}

export function getGroupProcess(groupId) {
	return request({
		url: "/student/process",
		method: "GET",
		params: {
			"groupId": groupId
		}
	})
}

export function getGroupSubtasks(groupId, taskId) {
	return request({
		url: "/student/task/subTasks",
		method: "GET",
		params: {
			"groupId": groupId,
			"taskId": taskId
		}
	})
}

export function postManagerComment(performanceDTO) {
	return request(({
		url: "/student/task/performance",
		method: "POST",
		data: performanceDTO
	}))
}

export function modifySubTaskDescription(subtask){
	return request({
		url: '/student/task/description',
		method: 'PUT',
		data: subtask
	})
}

export function submitSubtask(subtask){
	const _ = new FormData()
	_.append('recordId', subtask.recordId)
	_.append('submitLink', subtask.submitLink)
	return request({
		url: '/student/task',
		method: 'POST',
		data: _
	})
}

