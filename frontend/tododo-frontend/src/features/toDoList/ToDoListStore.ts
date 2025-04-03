import { acceptHMRUpdate, createPinia, defineStore } from 'pinia'
import todoService from '@/api/services/ToDoListServices'
import type { ToDoListRequest } from './ToDoListRequest'
import type { ToDoListResponse } from './ToDoListResponse'
import type { ToDoList } from './ToDoList'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import { rearrangeArrayIds } from '@/utils/arrayUtils'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

export const useToDoListStore = defineStore('toDoListStore', {
  state: () => ({
    allToDoListState: {} as ToDoList[], // Initialize as an empty array
    toDoListResp: {} as ToDoListResponse, // Define as a plain object
    sizeToDoList: 0, // Initialize sizeToDoList
  }),
  getters: {
    rearrangeArrayIdsList(state) {
      return rearrangeArrayIds(state.allToDoListState)
    },
    sortToDoListById(state) {
      return state.allToDoListState.sort((a, b) => a.id! - b.id!)
    },
  },
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
      const response = await todoService.getToDoListById(req.idsList, req.isTest)
      this.toDoListResp = response.data // Directly assign the response
    },
    // Update one ToDoList from API
    async updateToDoList(req: ToDoListRequest): Promise<ToDoListResponse> {
      const response = await todoService.updateToDoList(req)
      this.toDoListResp = response.data // Directly assign the response
      this.allToDoListState[
        this.allToDoListState.findIndex((item) => item.id === this.toDoListResp.toDoLists[0].id)
      ] = this.toDoListResp.toDoLists[0]
      return this.toDoListResp
    },
    // Add one ToDoList
    async addToDoList(req: ToDoListRequest): Promise<ToDoListResponse> {
      const response = await todoService.addToDoList(req)
      this.toDoListResp = response.data // Directly assign the response
      // Add the new ToDoList to the state
      this.allToDoListState.push(this.toDoListResp.toDoLists[0])
      // increase the size of the list
      this.sizeToDoList = this.allToDoListState.length
      return this.toDoListResp // Return the response
    },
    // Delete one ToDoList from API
    async deleteToDoListById(req: ToDoListRequest): Promise<ToDoListResponse> {
      const response = await todoService.deleteToDoListById(req.idsList, req.isTest)
      this.toDoListResp = response.data // Directly assign the response
      // Find the index of the item to delete
      const index = this.allToDoListState.findIndex((item) => item.id === req.idsList[0])
      if (index !== -1) {
        // Remove the item from the array
        this.allToDoListState.splice(index, 1)
        this.sizeToDoList = this.allToDoListState.length
        console.log(`Deleted item with id ${req.idsList[0]}`)
      } else {
        console.warn(`Item with id ${req.idsList[0]} not found in allToDoListState.`)
      }
      return this.toDoListResp // Return the response
    },
  },
})

if (import.meta.hot) {
  import.meta.hot.accept(acceptHMRUpdate(useToDoListStore, import.meta.hot))
}
