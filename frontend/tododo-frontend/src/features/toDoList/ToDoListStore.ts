import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import toDoListService from '@/api/services/ToDoListServices'
import { ToDoListRequest } from './ToDoListRequest'
import type { ToDoListResponse } from './ToDoListResponse'
import { ToDoList } from './ToDoList'
import dayjs from 'dayjs'
import { ElementType } from '@/types/elementType'

export const useToDoListStore = defineStore('toDoListStore', () => {
  //* State
  const allToDoListState = ref<ToDoList[]>([])
  const toDoListResp = ref<ToDoListResponse>({} as ToDoListResponse) // Define as a plain object
  const sizeToDoList = ref(0) // Initialize sizeToDoList

  //* Getters
  const rearrangeArrayIdsList = computed(() => {
    allToDoListState.value.forEach((item, index) => {
      item.id = index + 1 // Update the id property
    })
    allToDoListState.value.sort((a, b) => a.id! - b.id!)
    return allToDoListState.value
  })

  const refreshAllToDoLists = computed(() => {
    return allToDoListState.value.splice(
      0,
      allToDoListState.value.length,
      ...allToDoListState.value,
    )
  })

  //* Actions

  const checkIfAllTaskCompleted = (idList: number): boolean => {
    const toDoList = allToDoListState.value.find((item) => item.id === idList)
    if (toDoList && toDoList.tasks) {
      // Check if all tasks are completed
      return toDoList.tasks.every((task) => task.isCompleted == true)
    }
    return false
  }

  const getAllToDoLists = async (req: ToDoListRequest) => {
    const response = (await toDoListService.getAllToDoLists(req.isTest)) as ToDoListResponse
    toDoListResp.value = response // Directly assign the response
    allToDoListState.value.splice(0, allToDoListState.value.length, ...toDoListResp.value.toDoLists)
    sizeToDoList.value = toDoListResp.value.toDoLists.length // Update the size
  }

  const getToDoListById = async (req: ToDoListRequest) => {
    const response = await toDoListService.getToDoListById(req.idsList, req.isTest)
    toDoListResp.value = response.data // Directly assign the response
  }

  const updateToDoListById = async (
    req: ToDoListRequest,
    fromElement: string,
  ): Promise<ToDoListResponse> => {
    // Check if the request contains a list of ToDoLists
    if (req.toDoLists && req.toDoLists.length > 0) {
      // Manage behavior based on the type of element
      switch (fromElement) {
        case ElementType.TODOLIST:
          const populateCompleted = req.toDoLists[0].isCompleted as boolean
          req.toDoLists[0].tasks?.forEach((task) => (task.isCompleted = populateCompleted))
          break
        case ElementType.TASK:
          const isTasksCompleted = checkIfAllTaskCompleted(req.idsList[0])
          req.toDoLists[0].isCompleted = isTasksCompleted
          break
      }
      // Update the updateDate property to the current date
      req.toDoLists[0].updateDate = dayjs().format('YYYY-MM-DD HH:mm:ss')
    }

    const response = await toDoListService.updateToDoListById(req)
    toDoListResp.value = response.data // Directly assign the response

    // Update the specific ToDoList in the state
    const index = allToDoListState.value.findIndex(
      (item) => item.id === toDoListResp.value.toDoLists[0].id,
    )
    if (index !== -1) {
      // Replace the array reference to trigger reactivity
      Object.assign(allToDoListState.value[index], toDoListResp.value.toDoLists[0])
    }

    return toDoListResp.value
  }

  const addToDoList = async (req: ToDoListRequest): Promise<ToDoListResponse> => {
    const response = await toDoListService.addToDoList(req)
    toDoListResp.value = response.data // Directly assign the response

    // Add the new ToDoList to the state
    allToDoListState.value.push(toDoListResp.value.toDoLists[0]) // Use spread operator to add the new ToDoList
    sizeToDoList.value = allToDoListState.value.length // Update the size

    return toDoListResp.value
  }

  const deleteToDoListById = async (req: ToDoListRequest): Promise<ToDoListResponse> => {
    const response = await toDoListService.deleteToDoListById(req.idsList, req.isTest)
    toDoListResp.value = response.data // Directly assign the response
    // Find the index of the item to delete
    const index = allToDoListState.value.findIndex((item) => item.id === req.idsList[0])
    if (index !== -1) {
      allToDoListState.value.splice(index, 1) // Remove the item from the array
      sizeToDoList.value = allToDoListState.value.length // Update the size
    } else {
      console.warn(`List with id ${req.idsList[0]} not found in allToDoListState.value.`)
    }

    return toDoListResp.value
  }

  // Return state, getters, and actions
  return {
    allToDoListState,
    toDoListResp,
    sizeToDoList,
    rearrangeArrayIdsList,
    refreshAllToDoLists,
    checkIfAllTaskCompleted,
    getAllToDoLists,
    getToDoListById,
    updateToDoListById,
    addToDoList,
    deleteToDoListById,
  }
})
