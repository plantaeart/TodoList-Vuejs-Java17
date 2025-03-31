<script setup lang="ts">
import { useToDoListStore } from '@/features/toDoList/ToDoListStore'
import { ToDoListRequest } from '@/features/toDoList/ToDoListRequest'
import { onMounted, ref } from 'vue'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const isLoading = ref(true) // Track loading state

onMounted(async () => {
  console.log('start to get all todo list')
  await store.getAllToDoLists(req) // Wait for the API call to complete
  console.log('Finished fetching todo lists')
  console.log(store.toDoListResp.toDoLists.length) // Access the data after it is fetched
  isLoading.value = false // Set loading to false after data is fetched
})
</script>

<template>
  <p v-if="isLoading">Loading...</p>
  <p v-else>{{ 'Count the number of lists : ' + store.toDoListResp.toDoLists.length }}</p>
</template>

<style scoped></style>
