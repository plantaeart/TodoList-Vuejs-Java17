<script setup lang="ts">
import Checkbox from 'primevue/checkbox'
import Button from 'primevue/button'
import Panel from 'primevue/panel'
import { ToDoList } from '@/features/toDoList/ToDoList'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import type { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { nextTick, ref, watch } from 'vue'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { Result } from '@/types/result'
import { ElementType } from '@/types/elementType'
import AddToDoListForm from './addToDoList/AddToDoListForm.vue'
import type { Task } from '@/features/task/Task'
import appEnv from 'app-env'

const store = useToDoListStore()
const localToDoList = ref<ToDoList>(new ToDoList()) // Local state to hold fetched data
const isUpdateState = ref(false) // Local state to hold fetched data
const isUpdatePanelVisible = ref('invisible')
const AddToDoListFormRef = ref()
const initialStateToDoListBeforeUpdate = ref(new ToDoList()) // Local state to hold fetched data
let isRevertingState = false // Local state to hold fetched data;

// Define props
const props = defineProps({
  toDoList: {
    type: Object as () => ToDoList, // Specify the type of the prop
    required: true, // Make it required
  },
})

// Watch for changes in the prop and update the local copy
watch(
  () => props.toDoList,
  (newToDoList) => {
    if (!isRevertingState) {
      localToDoList.value = newToDoList // Update the local copy when the prop changes
    }
  },
  { deep: true },
)

// Initialize local state with the prop value
localToDoList.value = props.toDoList
// Handle checkbox change
const onCheckboxChange = async (value: boolean) => {
  let respToDoList: ToDoListResponse = new ToDoListResponse()
  try {
    // Update the local copy
    localToDoList.value.isCompleted = value

    const req: ToDoListRequest = {
      idsList: [localToDoList.value.id as number],
      toDoLists: [{ ...localToDoList.value }], // Use the local copy
      isTest: appEnv.VITE_IS_TEST,
    }

    console.log(`Start updating todo list id : ${localToDoList.value.id}`)

    // Await the response from the store
    respToDoList = await store.updateToDoListById(req, ElementType.TODOLIST)

    // Check if the response is valid and contains the expected data
    if (respToDoList.currentResult !== Result.OK)
      console.error(`Failed to update todo list. Response : ${respToDoList.message}`)
  } catch (error) {
    console.error(
      `(FRONT) Error while updating toDoList id: ${localToDoList.value.id}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while updating toDoList id: ${localToDoList.value.id}, error : ${respToDoList.message}`,
    )
  }
}

// Handle delete button click
const deleteToDoList = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  try {
    const idToDelete: number = localToDoList.value.id as number
    const req: ToDoListRequest = {
      idsList: [idToDelete],
      isTest: appEnv.VITE_IS_TEST,
    }

    // Await the response from the store
    resp = await store.deleteToDoListById(req)

    // Check if the response is valid and contains the expected data
    if (resp.currentResult === Result.OK) {
      // Remove the deleted item from the local list
      Object.assign(store.allToDoListState, store.rearrangeArrayIdsList)
      console.log('After delete, rearrange ids and sorting', store.allToDoListState)
    } else console.error('Failed to delete todo list. Response:', resp)
  } catch (error) {
    console.error(
      `(FRONT) Error while deleting toDoList id: ${localToDoList.value.id}, error : ${error}`,
    )
    console.error(
      `(BACK) Error while deleting toDoList id: ${localToDoList.value.id}, error : ${resp.message}`,
    )
  }
}

// Handle update button click to show the update panel
const ShowUpdateToDoListPanel = async (toDoList?: ToDoList) => {
  // Toggle the update state
  isUpdateState.value = !isUpdateState.value

  // Show or hide the update panel based on the state
  if (isUpdateState.value) {
    // Deep copy tasks and subtasks
    const deepCopyTasks = (tasks: Task[] = []) =>
      tasks.map((task) => ({
        ...task,
        subTasks: task.subTasks ? [...task.subTasks.map((subTask) => ({ ...subTask }))] : [],
      }))

    // Set the initial state for the update panel
    initialStateToDoListBeforeUpdate.value = new ToDoList({
      id: toDoList?.id,
      type: toDoList?.type,
      name: toDoList?.name,
      description: toDoList?.description,
      completionPercentage: toDoList?.completionPercentage,
      icon: toDoList?.icon,
      color: toDoList?.color,
      isCompleted: toDoList?.isCompleted,
      tasks: deepCopyTasks(toDoList?.tasks || []), // Deep copy tasks and subtasks
      updateDate: toDoList?.updateDate,
    })
    isUpdatePanelVisible.value = 'visible'
    AddToDoListFormRef.value?.initUpdateToDoListSate(toDoList, isUpdateState.value)
  }
}

// Handle update button click
const UpdateToDoList = async () => {
  AddToDoListFormRef.value?.addToDoListFromFormInfos()
  AddToDoListFormRef.value?.resetUpdateToDoListSate()
  isUpdatePanelVisible.value = 'invisible'
  isUpdateState.value = !isUpdateState.value
}

// Handle cancel update button click
const CancelUpdate = () => {
  isRevertingState = true // Disable the watch
  Object.assign(localToDoList.value, { ...initialStateToDoListBeforeUpdate.value }) // Revert to the initial state
  isUpdatePanelVisible.value = 'invisible' // Hide the update panel
  isUpdateState.value = !isUpdateState.value // Reset the update state
  AddToDoListFormRef.value?.resetUpdateToDoListSate()
  nextTick(() => {
    isRevertingState = false // Re-enable the watch after the state is reverted
  })
}
</script>

<template>
  <div class="flex flex-col m-4">
    <div class="flex flex-row justify-between">
      <div class="flex flex-row items-center w-3/4">
        <Checkbox
          size="large"
          :model-value="localToDoList.isCompleted"
          @update:model-value="onCheckboxChange"
          binary
        />
        <Button
          :data-cy="`deleteToDoList-${localToDoList.id}`"
          class="ml-1"
          size="small"
          icon="pi pi-times"
          severity="danger"
          aria-label="Cancel"
          raised
          @click="deleteToDoList"
        />
        <span
          :class="['m-2', 'font-size:40px', 'flex', 'justify-center', localToDoList.icon?.icon]"
        />
        <h3 class="text-2xl mb-1">{{ localToDoList.name }}</h3>
      </div>
      <div class="flex justify-center">
        <div>
          <Button
            size="small"
            icon="pi pi-pen-to-square"
            severity="warn"
            aria-label="Update"
            raised
            @click="ShowUpdateToDoListPanel(localToDoList)"
          />
        </div>
      </div>
    </div>
    <Panel v-if="localToDoList.description" header="Description" class="w-full h-30 mt-4">
      <p>
        {{ localToDoList.description }}
      </p>
    </Panel>
  </div>

  <!-- Update panel for editing the todo list -->
  <div
    :class="[isUpdatePanelVisible]"
    class="fixed z-100 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-full h-full bg-gray-200/40 rounded"
  >
    <div
      :class="[!toDoList.isCompleted ? toDoList.color?.color : 'bg-gray-200']"
      class="fixed snap-y overflow-y-auto z-101 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-5/6 h-5/6 rounded-lg"
    >
      <div class="flex flex-row justify-end">
        <Button
          class="m-3"
          size="small"
          icon="pi pi-times"
          severity="danger"
          aria-label="Cancel update"
          label="Cancel update"
          raised
          @click="CancelUpdate"
        />
      </div>
      <div class="flex justify-center">
        <AddToDoListForm ref="AddToDoListFormRef" />
      </div>
      <div v-if="isUpdateState" class="flex flex-row w-full justify-end">
        <Button
          label="Update todo list"
          icon="pi pi-check"
          class="w-1/3 m-3"
          raised
          @click="UpdateToDoList()"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
