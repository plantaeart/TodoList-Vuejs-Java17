import { defaultSubTaskAPIPath } from '@/constants/api'
import type { TaskRequest } from '@/features/task/TaskRequest'
import http from '../http'
import type { AxiosResponse } from 'axios'
import type { SubTaskRequest } from '@/features/subTask/SubTaskRequest'

export default {
  async getSubTaskById(
    idsList: Partial<number[]>,
    idsTask: Partial<number[]>,
    idsSubTask: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<AxiosResponse> {
    const { data } = await http.get(`/${defaultSubTaskAPIPath}/getSubTasksById`, {
      params: { idsList, idsTask, idsSubTask, isTest },
    })
    return data
  },

  async updateSubTaskById(payload: Partial<TaskRequest>): Promise<AxiosResponse> {
    return http.post(`/${defaultSubTaskAPIPath}/updateSubTaskById`, payload)
  },

  async addSubTask(payload: Partial<SubTaskRequest>): Promise<AxiosResponse> {
    return http.post(`/${defaultSubTaskAPIPath}/addSubTask`, payload)
  },

  async deleteSubTaskById(
    idsList: Partial<number[]>,
    idsTask: Partial<number[]>,
    idsSubTask: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<AxiosResponse> {
    return http.delete(`/${defaultSubTaskAPIPath}/deleteSubTaskById`, {
      params: { idsList, idsTask, idsSubTask, isTest },
    })
  },
}
