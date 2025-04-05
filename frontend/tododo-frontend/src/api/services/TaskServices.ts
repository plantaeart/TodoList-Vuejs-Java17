import { defaultTaskAPIPath } from '@/constants/api'
import type { TaskRequest } from '@/features/task/TaskRequest'
import http from '../http'
import type { AxiosResponse } from 'axios'

export default {
  async getAllTasksByToDoListId(
    idsList: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<AxiosResponse> {
    const { data } = await http.get(`/${defaultTaskAPIPath}/getAllTasksByToDoListId`, {
      params: { idsList, isTest },
    })
    return data
  },

  async getTaskById(
    idsList: Partial<number[]>,
    idsTask: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<AxiosResponse> {
    // Send payload as query parameters for GET requests
    const { data } = await http.get(`/${defaultTaskAPIPath}/getTaskById`, {
      params: { idsList, idsTask, isTest },
    })
    return data
  },

  async updateTaskById(payload: Partial<TaskRequest>): Promise<AxiosResponse> {
    return http.post(`/${defaultTaskAPIPath}/updateTaskById`, payload)
  },

  async addTask(payload: Partial<TaskRequest>): Promise<AxiosResponse> {
    return http.post(`/${defaultTaskAPIPath}/addTask`, payload)
  },

  async deleteTaskById(
    idsList: Partial<number[]>,
    idsTask: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<AxiosResponse> {
    return http.delete(`/${defaultTaskAPIPath}/deleteTaskById`, {
      params: { idsList, idsTask, isTest },
    })
  },
}
