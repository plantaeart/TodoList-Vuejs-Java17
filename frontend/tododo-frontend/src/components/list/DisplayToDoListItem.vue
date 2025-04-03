<script setup lang="ts">
import Checkbox from 'primevue/checkbox'
import Button from 'primevue/button'
import type { ToDoList } from '@/features/toDoList/ToDoList'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import type { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { ref } from 'vue'
import dayjs from 'dayjs'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { Result } from '@/types/result'

const store = useToDoListStore()

// Define props
const props = defineProps({
  toDoList: {
    type: Object as () => ToDoList, // Specify the type of the prop
    required: true, // Make it required
  },
})

// Create a local reactive copy of the prop
const localToDoList = ref(props.toDoList)

// Handle checkbox change
const onCheckboxChange = async (value: boolean) => {
  let resp: ToDoListResponse = new ToDoListResponse()
  try {
    // Update the local copy
    localToDoList.value.isCompleted = value
    localToDoList.value.updateDate = dayjs().format('DD/MM/YYYY HH:mm:ss')

    const req: ToDoListRequest = {
      idsList: [localToDoList.value.id as number],
      toDoLists: [{ ...localToDoList.value }], // Use the local copy
      isTest: false,
    }

    console.log('Start updating todo list id : ', localToDoList.value.id)

    // Await the response from the store
    resp = await store.updateToDoList(req)

    // Check if the response is valid and contains the expected data
    if (resp.currentResult !== Result.OK)
      console.error('Failed to update todo list. Response:', resp)
  } catch (error) {
    console.error('(FRONT) Error while adding ToDoList:', error)
    console.error('(BACK) Error while adding ToDoList:', resp.message)
  }
}

// Handle delete button click
const deleteToDoList = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  try {
    const idToDelete: number = localToDoList.value.id as number
    const req: ToDoListRequest = {
      idsList: [idToDelete],
      isTest: false,
    }

    // Await the response from the store
    resp = await store.deleteToDoListById(req)

    // Check if the response is valid and contains the expected data
    if (resp.currentResult === Result.OK) {
      // Remove the deleted item from the local list
      store.allToDoListState = store.rearrangeArrayIdsList
      store.allToDoListState = store.sortToDoListById
    } else console.error('Failed to delete todo list. Response:', resp)
  } catch (error) {
    console.error('(FRONT) Error while adding ToDoList:', error)
    console.error('(BACK) Error while adding ToDoList:', resp.message)
  }
}
</script>

<template>
  <div class="m-4">
    <div class="flex flex-row items-center">
      <Checkbox
        size="large"
        :model-value="localToDoList.isCompleted"
        @update:model-value="onCheckboxChange"
        binary
      />
      <Button
        class="ml-1"
        size="small"
        icon="pi pi-times"
        severity="danger"
        aria-label="Cancel"
        @click="deleteToDoList"
      />
      <p class="ml-4">{{ localToDoList.name }}</p>
    </div>
  </div>
</template>

<style scoped></style>
