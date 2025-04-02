import { acceptHMRUpdate, createPinia, defineStore } from 'pinia'
import todoService from '@/api/services/ToDoListServices'
import type { ToDoListRequest } from './ToDoListRequest'
import type { ToDoListResponse } from './ToDoListResponse'
import type { ToDoList } from './ToDoList'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

export const useToDoListStore = defineStore('toDoListStore', {
  state: () => ({
    allToDoListState: [] as ToDoList[], // Initialize as an empty array
    toDoListResp: {} as ToDoListResponse, // Define as a plain object
    sizeToDoList: 0, // Initialize sizeToDoList
  }),
  // To cache the state in localStorage
  persist: true,
  actions: {
    // Get all toDoList from API
    async getAllToDoLists(req: ToDoListRequest) {
      const response = (await todoService.getAllToDoLists(req.isTest)) as ToDoListResponse
      this.toDoListResp = response // Directly assign the response
      this.allToDoListState = response.toDoLists // Update the state
      this.sizeToDoList = response.toDoLists.length // Update the size
    },
    // Get one ToDoList from API
    async getToDoListById(req: ToDoListRequest) {
      const response = (await todoService.getToDoListById(
        req.idsList,
        req.isTest,
      )) as ToDoListResponse
      this.toDoListResp = response // Directly assign the response
    },
    // Update one ToDoList from API
    async updateToDoList(req: ToDoListRequest) {
      console.log('updateToDoList', req)
      const response = (await todoService.updateToDoList(req)) as ToDoListResponse
      this.toDoListResp = response // Directly assign the response
      await this.getAllToDoLists(req) // Refresh the list
    },
    // Add one ToDoList
    async addToDoList(req: ToDoListRequest) {
      const response = (await todoService.addToDoList(req)) as ToDoListResponse
      this.toDoListResp = response // Directly assign the response
      await this.getAllToDoLists(req) // Refresh the list
    },
    // Delete one ToDoList from API
    async deleteToDoListById(req: ToDoListRequest) {
      const response = (await todoService.deleteToDoListById(
        req.idsList,
        req.isTest,
      )) as ToDoListResponse
      this.toDoListResp = response // Directly assign the response
      await this.getAllToDoLists(req) // Refresh the list
    },
  },
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useToDoListStore, import.meta.hot))
}
