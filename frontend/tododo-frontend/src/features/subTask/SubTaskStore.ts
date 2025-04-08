import { defineStore } from 'pinia'
import { ref } from 'vue'
import subTaskService from '@/api/services/SubTaskServices'
import { useToDoListStore } from '../toDoList/ToDoListStore'
import type { SubTaskRequest } from './SubTaskRequest'
import { rearrangeArrayIds } from '@/utils/arrayUtils'
import type { SubTaskResponse } from './SubTaskResponse'
import type { SubTask } from './SubTask'

export const useSubTaskStore = defineStore('subTaskStore', () => {
  //* State
  const subTaskResp = ref<SubTaskResponse>({} as SubTaskResponse)

  //* Getters
  const rearrangeArrayIdsSubTask = (idList: number, idTask: number) => {
    const toDoListStore = useToDoListStore()
    const indexList = idList - 1
    const indexTask = idTask - 1
    if (
      toDoListStore.allToDoListState[indexList].tasks &&
      toDoListStore.allToDoListState[indexList].tasks[indexTask] !== undefined
    ) {
      toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks = rearrangeArrayIds(
        toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks as Array<SubTask>,
      )
    }
  }

  //* Actions
  const getSubTaskById = async (req: SubTaskRequest) => {
    const response = await subTaskService.getSubTaskById(
      req.idsList,
      req.idsTask,
      req.idsSubTask,
      req.isTest,
    )
    subTaskResp.value = response.data // Directly assign the response
  }

  const updateSubTaskById = async (req: SubTaskRequest): Promise<SubTaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await subTaskService.updateSubTaskById(req)
    subTaskResp.value = response.data // Directly assign the response

    // Update the subTask in the state
    const indexList = req.idsList[0] - 1
    const indexTask = req.idsTask[0] - 1
    const idSubTask = req.idsSubTask[0]
    if (
      toDoListStore.allToDoListState[indexList].tasks &&
      toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks !== undefined
    ) {
      const indexSubTask = toDoListStore.allToDoListState[indexList].tasks[
        indexTask
      ].subTasks.findIndex((item) => item.id === idSubTask)

      if (indexSubTask !== -1) {
        // Update the task in the state
        Object.assign(
          toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks[indexSubTask],
          subTaskResp.value.subTasks[0],
        )
      }
    }

    return subTaskResp.value
  }

  const addSubTask = async (req: SubTaskRequest): Promise<SubTaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await subTaskService.addSubTask(req)
    subTaskResp.value = response.data // Directly assign the response

    const indexList = req.idsList[0] - 1
    const indexTask = req.idsTask[0] - 1
    if (toDoListStore.allToDoListState[indexList].tasks !== undefined) {
      // Add the new subTask to the state
      toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks.push(
        subTaskResp.value.subTasks[0],
      )
    }

    return subTaskResp.value // Return the response
  }

  const deleteSubTaskById = async (req: SubTaskRequest): Promise<SubTaskResponse> => {
    const toDoListStore = useToDoListStore()
    const response = await subTaskService.deleteSubTaskById(
      req.idsList,
      req.idsTask,
      req.idsSubTask,
      req.isTest,
    )
    subTaskResp.value = response.data // Directly assign the response

    // Find the index of the item to delete
    const indexList = req.idsList[0] - 1
    const indexTask = req.idsTask[0] - 1
    const idSubTask = req.idsSubTask[0]
    if (
      toDoListStore.allToDoListState[indexList].tasks &&
      toDoListStore.allToDoListState[indexList].tasks[indexTask] !== undefined
    ) {
      const indexSubTask = toDoListStore.allToDoListState[indexList].tasks[
        indexTask
      ].subTasks?.findIndex((item) => item.id === idSubTask)

      if (indexSubTask !== -1) {
        // Remove the item from the array
        toDoListStore.allToDoListState[indexList].tasks[indexTask].subTasks.splice(
          indexSubTask as number,
          1,
        )
        console.log(
          `Deleted subTask with id ${idSubTask} from task id ${req.idsTask[0]} from todo list id ${req.idsList[0]}`,
        )
      } else {
        console.warn(
          `SubTask with id ${idSubTask} and task id ${req.idsTask[0]} and todo list id ${req.idsList[0]} not found in allToDoListState.`,
        )
      }
    }

    return subTaskResp.value // Return the response
  }

  // Return state, getters, and actions
  return {
    subTaskResp,
    rearrangeArrayIdsSubTask,
    getSubTaskById,
    updateSubTaskById,
    addSubTask,
    deleteSubTaskById,
  }
})
