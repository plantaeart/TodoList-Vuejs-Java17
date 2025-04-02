import { defaultToDoListAPIPath } from '@/constants/api'
import type { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import type { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import http from '../http'

export default {
  async getAllToDoLists(isTest: Partial<boolean>): Promise<ToDoListResponse> {
    const { data } = await http.get(`/${defaultToDoListAPIPath}/getAllToDoLists`, {
      params: { isTest },
    })
    return data
  },

  async getToDoListById(
    idsList: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<ToDoListResponse> {
    // Send payload as query parameters for GET requests
    const { data } = await http.get(`/${defaultToDoListAPIPath}/getToDoListById`, {
      params: { idsList, isTest },
    })
    return data
  },

  async updateToDoList(payload: Partial<ToDoListRequest>): Promise<ToDoListResponse> {
    return http.post(`/${defaultToDoListAPIPath}/updateToDoList`, payload)
  },

  async addToDoList(payload: Partial<ToDoListRequest>): Promise<ToDoListResponse> {
    return http.post(`/${defaultToDoListAPIPath}/addToDoList`, payload)
  },

  async deleteToDoListById(
    idsList: Partial<number[]>,
    isTest: Partial<boolean>,
  ): Promise<ToDoListResponse> {
    return http.delete(`/${defaultToDoListAPIPath}/deleteToDoListById`, {
      params: { idsList, isTest },
    })
  },
}
