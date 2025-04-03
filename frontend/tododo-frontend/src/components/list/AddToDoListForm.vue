<script setup lang="ts">
import { ref } from 'vue'
import InputText from 'primevue/inputtext'
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { ToDoListResponse } from '@/features/toDoList/ToDoListResponse'
import { Result } from '@/types/result'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const toDoListName = ref('')

const addToDoListFromFormInfos = async () => {
  let resp: ToDoListResponse = new ToDoListResponse()
  try {
    req.toDoLists = [{ name: toDoListName.value }] // Prepare the request

    // Await the response from the store
    resp = await store.addToDoList(req)
    // Check if the response is valid and contains the expected data
    if (resp && resp.currentResult !== Result.OK)
      console.error('Failed to add ToDoList. Response:', resp)
  } catch (error) {
    console.error('(FRONT) Error while adding ToDoList:', error)
    console.error('(BACK) Error while adding ToDoList:', resp.message)
  }
}

defineExpose({
  addToDoListFromFormInfos,
})
</script>

<template>
  <div class="flex flex-col items-center m-4">
    <div class="flex flex-col gap-2">
      <label for="toDoListName">Todo list name</label>
      <InputText id="toDoListName" v-model="toDoListName" aria-describedby="todo list name" />
    </div>
  </div>
</template>

<style scoped></style>
