import { defineStore } from 'pinia'
import { ref } from 'vue'
import taskService from '@/api/services/TaskServices'
import { useToDoListStore } from '../toDoList/ToDoListStore'
import { TaskRequest } from './TaskRequest'
import { TaskResponse } from './TaskResponse'
import type { Task } from './Task'
import { rearrangeArrayIds } from '@/utils/arrayUtils'
import { ElementType } from '@/types/elementType'
import { Result } from '@/types/result'

export const useTaskStore = defineStore('taskStore', () => {
  //* State
  const taskResp = ref<TaskResponse>({} as TaskResponse)

  //* Getters
  const rearrangeArrayIdsTask = (idList: number) => {
    const toDoListStore = useToDoListStore()
    const indexList = idList - 1
    toDoListStore.allToDoListState[indexList].tasks = rearrangeArrayIds(
      toDoListStore.allToDoListState[indexList].tasks as Array<Task>,
    )
  }

  //* Actions

  const checkTaskCompletedState = async (
    idList: number,
    idTask: number,
    currentTask: Task,
    from: string,
  ) => {
    let respTask: TaskResponse = new TaskResponse()

    // Update the ToDoList with the new SubTask status
    respTask = await updateTaskById(
      new TaskRequest({
        idsList: [idList],
        idsTask: [idTask],
        tasks: [currentTask],
        isTest: false,
      }),
      from,
    )

    // Check if the response is valid and contains the expected data
    if (respTask.currentResult !== Result.OK)
      console.error(
        `Failed to update for task id: ${currentTask.id} - Response: ${respTask.message}`,
      )
  }

  const checkIfAllSubTaskCompleted = (idList: number, idTask: number): boolean => {
    const toDoListStore = useToDoListStore()
    const task = toDoListStore.allToDoListState
      .find((item) => item.id === idList)
      ?.tasks?.find((item) => item.id === idTask)
    if (task && task.subTasks) {
      // Check if all tasks are completed
      return task.subTasks.every((subTask) => subTask.isCompleted == true)
    }
    return false
  }

  const getAllTasksByToDoListId = async (req: TaskRequest) => {
    const response = await taskService.getAllTasksByToDoListId(req.idsList, req.isTest)
    taskResp.value = response.data // Directly assign the response
  }

  const getTaskById = async (req: TaskRequest) => {
    const response = await taskService.getTaskById(req.idsList, req.idsTask, req.isTest)
    taskResp.value = response.data // Directly assign the response
  }

  const updateTaskById = async (req: TaskRequest, fromElement: string): Promise<TaskResponse> => {
    // Check if the request contains a list of ToDoLists
    if (req.tasks && req.tasks.length > 0) {
      // Manage behavior based on the type of element
      switch (fromElement) {
        case ElementType.TASK:
          const populateCompleted = req.tasks[0].isCompleted as boolean
          req.tasks[0].subTasks?.forEach((subTask) => (subTask.isCompleted = populateCompleted))
          break
        case ElementType.SUBTASK:
          const isTasksCompleted = checkIfAllSubTaskCompleted(req.idsList[0], req.idsTask[0])
          req.tasks[0].isCompleted = isTasksCompleted
          break
      }
    }

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

    const indexList = req.idsList[0] - 1
    if (toDoListStore.allToDoListState[indexList].tasks !== undefined) {
      // Add the new task to the state
      toDoListStore.allToDoListState[indexList].tasks.push(taskResp.value.tasks[0])
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
    const indexTask = toDoListStore.allToDoListState[indexList].tasks?.findIndex(
      (item) => item.id === idTask,
    )
    if (indexTask !== -1) {
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
    checkTaskCompletedState,
    checkIfAllSubTaskCompleted,
    rearrangeArrayIdsTask,
    getAllTasksByToDoListId,
    getTaskById,
    updateTaskById,
    addTask,
    deleteTaskById,
  }
})
