import { acceptHMRUpdate, createPinia, defineStore } from 'pinia'
import todoService from '@/api/services/ToDoListServices'
import type { ToDoListRequest } from './ToDoListRequest'
import type { ToDoListResponse } from './ToDoListResponse'
import type { ToDoList } from './ToDoList'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { ref } from 'vue'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

export const useToDoListStore = defineStore('toDoListStore', {
  state: () => ({
    allToDoListState: {} as ToDoList[],
    toDoListResp: ref({} as ToDoListResponse),
  }),
  // To cache the state in localStorage
  persist: true,
  actions: {
    // Get all toDoList from API
    async getAllToDoLists(req: ToDoListRequest) {
      this.toDoListResp = (await todoService.getAllToDoLists(req.isTest)) as ToDoListResponse
      this.allToDoListState = this.toDoListResp.toDoLists
    },
    // Get one ToDoList from API
    async getToDoListById(req: ToDoListRequest) {
      this.toDoListResp = (await todoService.getToDoListById(
        req.idsList,
        req.isTest,
      )) as ToDoListResponse
    },
    // Update one ToDoList from API
    async updateToDoList(req: ToDoListRequest) {
      this.toDoListResp = (await todoService.updateToDoList(req)) as ToDoListResponse
      await this.getAllToDoLists(req)
    },
    // add one ToDoList
    async addToDoList(req: ToDoListRequest) {
      this.toDoListResp = (await todoService.addToDoList(req)) as ToDoListResponse
      await this.getAllToDoLists(req)
    },
    // Delete one ToDoList from API
    async deleteToDoListById(req: ToDoListRequest) {
      this.toDoListResp = (await todoService.deleteToDoListById(req)) as ToDoListResponse
      await this.getAllToDoLists(req)
    },
  },
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useToDoListStore, import.meta.hot))
}
