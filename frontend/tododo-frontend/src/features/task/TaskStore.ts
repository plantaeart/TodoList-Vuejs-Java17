import { defineStore } from 'pinia'
import { ref } from 'vue'
import taskService from '@/api/services/TaskServices'
import { useToDoListStore } from '../toDoList/ToDoListStore'
import type { TaskRequest } from './TaskRequest'
import type { TaskResponse } from './TaskResponse'
import type { Task } from './Task'
import { rearrangeArrayIds } from '@/utils/arrayUtils'

export const useTaskStore = defineStore('taskStore', () => {
  // State
  const taskResp = ref<TaskResponse>({} as TaskResponse)

  // Getters
  const rearrangeArrayIdsTask = (idList: number) => {
    const toDoListStore = useToDoListStore()
    return rearrangeArrayIds(toDoListStore.allToDoListState[idList].tasks as Array<Task>)
  }

  const sortTaskById = (idList: number) => {
    const toDoListStore = useToDoListStore()
    return toDoListStore.allToDoListState[idList].tasks?.sort((a, b) => a.id! - b.id!)
  }

  // Actions
  const getAllTasksByToDoListId = async (req: TaskRequest) => {
    const response = await taskService.getAllTasksByToDoListId(req.idsList, req.isTest)
    taskResp.value = response.data // Directly assign the response
  }

  const getTaskById = async (req: TaskRequest) => {
    const response = await taskService.getTaskById(req.idsList, req.idsTask, req.isTest)
    taskResp.value = response.data // Directly assign the response
  }

  const updateTaskById = async (req: TaskRequest): Promise<TaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await taskService.updateTaskById(req)
    taskResp.value = response.data // Directly assign the response

    // Update the task in the state
    const indexList = req.idsList[0] - 1
    const idTask = req.idsTask[0]
    if (toDoListStore.allToDoListState[indexList].tasks !== undefined) {
      const indexTask = toDoListStore.allToDoListState[indexList].tasks?.findIndex(
        (item) => item.id === idTask,
      )
      if (indexTask !== -1) {
        // Update the task in the state
        Object.assign(
          toDoListStore.allToDoListState[indexList].tasks[indexTask],
          taskResp.value.tasks[0],
        )
      }
    }

    return taskResp.value
  }

  const addTask = async (req: TaskRequest): Promise<TaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await taskService.addTask(req)
    taskResp.value = response.data // Directly assign the response

    const idList = req.idsList[0]
    if (toDoListStore.allToDoListState[idList].tasks !== undefined) {
      // Add the new task to the state
      toDoListStore.allToDoListState[idList].tasks.push(taskResp.value.tasks[0])
    }

    return taskResp.value // Return the response
  }

  const deleteTaskById = async (req: TaskRequest): Promise<TaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await taskService.deleteTaskById(req.idsList, req.idsTask, req.isTest)
    taskResp.value = response.data // Directly assign the response

    // Find the index of the item to delete
    const indexList = req.idsList[0] - 1
    const idTask = req.idsTask[0]
    console.log('indexList', indexList)
    const indexTask = toDoListStore.allToDoListState[indexList].tasks?.findIndex(
      (item) => item.id === idTask,
    )
    console.log('indexTask', indexTask)
    if (indexTask !== -1) {
      console.log(
        'toDoListStore.allToDoListState[indexList] : ',
        toDoListStore.allToDoListState[indexList],
      )
      // Remove the item from the array
      toDoListStore.allToDoListState[indexList].tasks?.splice(indexTask as number, 1)
      console.log(`Deleted task with id ${idTask} from todo list id ${req.idsList[0]}`)
    } else {
      console.warn(
        `Task with id ${idTask} and todo list id ${req.idsList[0]} not found in allToDoListState.`,
      )
    }

    return taskResp.value // Return the response
  }

  // Return state, getters, and actions
  return {
    taskResp,
    rearrangeArrayIdsTask,
    sortTaskById,
    getAllTasksByToDoListId,
    getTaskById,
    updateTaskById,
    addTask,
    deleteTaskById,
  }
})
