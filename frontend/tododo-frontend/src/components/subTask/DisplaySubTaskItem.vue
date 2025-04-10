<script setup lang="ts">
import Checkbox from 'primevue/checkbox'
import Button from 'primevue/button'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ref, watch } from 'vue'
import { Result } from '@/types/result'
import { ElementType } from '@/types/elementType'
import appEnv from 'app-env'
import { SubTask } from '@/features/subTask/SubTask'
import { useSubTaskStore } from '@/features/subTask/SubTaskStore'
import type { Task } from '@/features/task/Task'
import { useTaskStore } from '@/features/task/TaskStore'
import { SubTaskResponse } from '@/features/subTask/SubTaskResponse'
import type { SubTaskRequest } from '@/features/subTask/SubTaskRequest'

const storeToDoList = useToDoListStore()
const storeSubTask = useSubTaskStore()
const storeTask = useTaskStore()
const localSubTask = ref<SubTask>(new SubTask()) // Local state to hold fetched data
const debug = ref(appEnv.VITE_DEBUG)
// Define props
const props = defineProps({
  idList: {
    type: Number, // Specify the type of the prop
    required: true, // Make it required
  },
  idTask: {
    type: Number, // Specify the type of the prop
    required: true, // Make it required
  },
  subTask: {
    type: Object as () => SubTask, // Specify the type of the prop
    required: true, // Make it required
  },
})

// Watch for changes in the prop and update the local copy
watch(
  () => props.subTask,
  (newSubTask) => {
    // Update the local copy when the prop changes
    localSubTask.value = newSubTask
  },
  { deep: true },
)

// Create a local reactive copy of the prop
localSubTask.value = props.subTask
const idList = props.idList
const idTask = props.idTask

// Handle checkbox change
const onCheckboxChange = async (value: boolean) => {
  let respSubTask: SubTaskResponse = new SubTaskResponse()
  try {
    // Update the local copy
    localSubTask.value.isCompleted = value
    const currentTask = storeToDoList.allToDoListState
      .find((item) => item.id === idList)
      ?.tasks?.find((item) => item.id === idTask) as Task

    const req: SubTaskRequest = {
      idsList: [idList],
      idsTask: [idTask],
      idsSubTask: [localSubTask.value.id as number],
      subTasks: [{ ...localSubTask.value }], // Use the local copy
      isTest: false,
    }

    console.log(
      `Start updating SubTask id : ${localSubTask.value.id} from task id : ${idTask} from todo list id : ${idList}`,
    )
    // Await the response from the store
    respSubTask = await storeSubTask.updateSubTaskById(req)

    // Check if the response is valid and contains the expected data
    if (respSubTask.currentResult === Result.OK) {
      storeSubTask.rearrangeArrayIdsSubTask(idList, idTask)
      // Update the Task with the new SubTask status
      storeTask.checkTaskCompletedState(idList, idTask, currentTask, ElementType.SUBTASK.toString())
    } else console.error(`Failed to update SubTask. Response: ${respSubTask.message}`)
  } catch (error) {
    console.error(
      `(FRONT) Error while updating SubTask id: ${localSubTask.value.id} from Task id : ${idTask} from ToDoList id: ${idList}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while updating SubTask id: ${localSubTask.value.id} from Task id : ${idTask} from ToDoList id: ${idList}, error : ${respSubTask.message}`,
    )
  }
}

// Handle delete button click
const deleteSubTaskFromDisplayList = async () => {
  let resp: SubTaskResponse = new SubTaskResponse()
  try {
    const idToDelete: number = localSubTask.value.id as number
    const req: SubTaskRequest = {
      idsList: [idList],
      idsTask: [idTask],
      idsSubTask: [idToDelete],
      isTest: false,
    }
    const currentTask = storeToDoList.allToDoListState
      .find((item) => item.id === idList)
      ?.tasks?.find((item) => item.id === idTask) as Task

    //const toDoListStore = useToDoListStore()
    // Await the response from the store
    resp = await storeSubTask.deleteSubTaskById(req)

    // Check if the response is valid and contains the expected data
    if (resp.currentResult === Result.OK) {
      // Remove the deleted item from the local list
      storeSubTask.rearrangeArrayIdsSubTask(idList, idTask)

      // Update the ToDoList with the new SubTask status
      storeTask.checkTaskCompletedState(idList, idTask, currentTask, ElementType.SUBTASK.toString())
    } else console.error('Failed to delete todo list. Response:', resp)

    console.log('Finish delete SubTask id : ' + idToDelete)
  } catch (error) {
    console.error(
      `(FRONT) Error while deleting SubTask id: ${localSubTask.value.id} from ToDoList id: ${idList}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while deleting SubTask id: ${localSubTask.value.id} from ToDoList id: ${idList}, error : ${resp.message}`,
    )
  }
}
</script>

<template>
  <div class="m-4">
    <div class="flex flex-row items-center">
      <p v-if="debug" class="mr-5 text-sm">SubTask ID : {{ localSubTask.id }}</p>
      <Checkbox
        size="large"
        :model-value="localSubTask.isCompleted"
        @update:model-value="onCheckboxChange"
        binary
      />
      <Button
        v-if="debug"
        class="ml-1"
        size="small"
        icon="pi pi-times"
        severity="danger"
        aria-label="Cancel"
        raised
        @click="deleteSubTaskFromDisplayList"
      />
      <span
        v-if="localSubTask.icon?.icon !== ''"
        :class="['m-2', 'font-size:20px', 'flex', 'justify-center', localSubTask.icon?.icon]"
      />
      <span v-else class="ml-[32px]" />
      <h3 class="text-lg">{{ localSubTask.taskContent }}</h3>
    </div>
  </div>
</template>

<style scoped></style>
