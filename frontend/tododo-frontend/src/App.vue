<script setup lang="ts">
import { onMounted, ref } from 'vue'
import ViewToDoLists from './components/ViewToDoLists.vue'
import { ToDoListRequest } from './features/toDoList/ToDoListRequest'
import { useToDoListStore } from './features/toDoList/ToDoListStore'

const store = useToDoListStore()
const req: ToDoListRequest = new ToDoListRequest()
const isLoading = ref(true)

onMounted(async () => {
  await store.getAllToDoLists(req) // Wait for the API call to complete
  isLoading.value = false // Set loading to false after data is fetched
})
</script>

<template>
  <ViewToDoLists :to-do-lists="store.allToDoListState" :is-loading="isLoading" />
</template>

<style scoped></style>
