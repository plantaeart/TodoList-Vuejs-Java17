<script setup lang="ts">
import Checkbox from 'primevue/checkbox'
import Button from 'primevue/button'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import type { TaskRequest } from '@/features/task/TaskRequest'
import { ref, watch } from 'vue'
import { TaskResponse } from '@/features/task/TaskResponse'
import { Result } from '@/types/result'
import { Task } from '@/features/task/Task'
import { useTaskStore } from '@/features/task/TaskStore'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import type { ToDoList } from '@/features/toDoList/ToDoList'
import { ElementType } from '@/types/elementType'
import appEnv from 'app-env'

const storeToDoList = useToDoListStore()
const storeTask = useTaskStore()
const localTask = ref<Task>(new Task()) // Local state to hold fetched data
const debug = ref(appEnv.VITE_DEBUG)
// Define props
const props = defineProps({
  idList: {
    type: Number, // Specify the type of the prop
    required: true, // Make it required
  },
  task: {
    type: Object as () => Task, // Specify the type of the prop
    required: true, // Make it required
  },
})

// Watch for changes in the prop and update the local copy
watch(
  () => props.task,
  (newTask) => {
    // Update the local copy when the prop changes
    localTask.value = newTask
  },
  { deep: true },
)

// Create a local reactive copy of the prop
localTask.value = props.task
const idList = props.idList

// Handle checkbox change
const onCheckboxChange = async (value: boolean) => {
  let respTask: TaskResponse = new TaskResponse()
  let respToDoList: ToDoListResponse = new ToDoListResponse()
  try {
    // Update the local copy
    localTask.value.isCompleted = value
    const currentToDoList = storeToDoList.allToDoListState.find(
      (item) => item.id === idList,
    ) as ToDoList

    const req: TaskRequest = {
      idsList: [idList],
      idsTask: [localTask.value.id as number],
      tasks: [{ ...localTask.value }], // Use the local copy
      isTest: false,
    }

    console.log(`Start updating Task id : ${localTask.value.id} from todo list id : ${idList}`)

    // Await the response from the store
    respTask = await storeTask.updateTaskById(req)

    // Check if the response is valid and contains the expected data
    if (respTask.currentResult === Result.OK) {
      storeTask.rearrangeArrayIdsTask(idList)
    } else console.error(`Failed to update task. Response: ${respTask.message}`)

    // Update the ToDoList with the new task status
    respToDoList = await storeToDoList.updateToDoListById(
      new ToDoListRequest([idList], [currentToDoList], false),
      ElementType.TASK.toString(),
    )

    // Check if the response is valid and contains the expected data
    if (respToDoList.currentResult !== Result.OK)
      console.error(
        `Failed to update for todo list id: ${idList} - Response: ${respToDoList.message}`,
      )
  } catch (error) {
    console.error(
      `(FRONT) Error while updating Task id: ${localTask.value.id} from ToDoList id: ${idList}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while updating Task id: ${localTask.value.id} from ToDoList id: ${idList}, error : ${respTask.message}`,
    )
  }
}

// Handle delete button click
const deleteTaskFromDisplayList = async () => {
  let resp: TaskResponse = new TaskResponse()
  try {
    const idToDelete: number = localTask.value.id as number
    const req: TaskRequest = {
      idsList: [idList],
      idsTask: [idToDelete],
      isTest: false,
    }
    //const toDoListStore = useToDoListStore()
    // Await the response from the store
    resp = await storeTask.deleteTaskById(req)

    // Check if the response is valid and contains the expected data
    if (resp.currentResult === Result.OK) {
      // Remove the deleted item from the local list
      console.log('here')
      storeTask.rearrangeArrayIdsTask(idList)
    } else console.error('Failed to delete todo list. Response:', resp)
  } catch (error) {
    console.error(
      `(FRONT) Error while deleting Task id: ${localTask.value.id} from ToDoList id: ${idList}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while deleting Task id: ${localTask.value.id} from ToDoList id: ${idList}, error : ${resp.message}`,
    )
  }
}
</script>

<template>
  <div class="m-4">
    <div class="flex flex-row items-center">
      <p v-if="debug" class="mr-5 text-sm">Task ID : {{ localTask.id }}</p>
      <Checkbox
        size="large"
        :model-value="localTask.isCompleted"
        @update:model-value="onCheckboxChange"
        binary
      />
      <Button
        class="ml-1"
        size="small"
        icon="pi pi-times"
        severity="danger"
        aria-label="Cancel"
        @click="deleteTaskFromDisplayList"
      />
      <p class="ml-4">{{ localTask.taskContent }}</p>
    </div>
  </div>
</template>

<style scoped></style>
